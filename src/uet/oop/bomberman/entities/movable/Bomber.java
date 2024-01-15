package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.immovable.*;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    public int bombRange;
    public int bombNumber;
    private int speed;
    private int timer = 0;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        speed = 6;
        bombNumber = 1;
        bombRange = 1;
    }

    public Bomber(Bomber another) {
        this.x = another.x;
        this.y = another.y;
        this.img = another.img;
        broken = another.broken;
        solid = another.solid;
        exploded = another.exploded;
        removed = another.removed;
        speed = another.speed;
        bombNumber = another.bombNumber;
        bombRange = another.bombRange;
    }

    @Override
    public void update() {
        if (timer > 72) removed = true;
    }

    public void right() {
        this.img = Sprite.movingSprite(
                Sprite.player_right,
                Sprite.player_right_1,
                Sprite.player_right_2,
                Sprite.player_right_3,
                Sprite.player_right_4,
                Sprite.player_right_5,
                BombermanGame.framePerSecond, 256 / speed).getFxImage();
    }

    public void left() {
        this.img = Sprite.movingSprite(
                Sprite.player_left,
                Sprite.player_left_1,
                Sprite.player_left_2,
                Sprite.player_left_3,
                Sprite.player_left_4,
                Sprite.player_left_5,
                BombermanGame.framePerSecond, 256 / speed).getFxImage();
    }

    public void up() {
        this.img = Sprite.movingSprite(
                Sprite.player_up,
                Sprite.player_up_1,
                Sprite.player_up_2,
                Sprite.player_up_3,
                Sprite.player_up_4,
                Sprite.player_up_5,
                BombermanGame.framePerSecond, 256 / speed).getFxImage();
    }

    public void down() {
        this.img = Sprite.movingSprite(
                Sprite.player_down,
                Sprite.player_down_1,
                Sprite.player_down_2,
                Sprite.player_down_3,
                Sprite.player_down_4,
                Sprite.player_down_5,
                BombermanGame.framePerSecond, 256 / speed).getFxImage();
    }

    public void moveRight() {
        if (!broken) {
            this.x += speed;
        }
    }

    public void moveLeft() {
        if (!broken) {
            this.x -= speed;
        }
    }

    public void moveDown() {
        if (!broken) {
            this.y += speed;
        }
    }

    public void moveUp() {
        if (!broken) {
            this.y -= speed;
        }
    }

    public void destroy() {
        timer++;
        this.img = Sprite.movingSprite(
                Sprite.player_dead2,
                Sprite.player_dead3,
                Sprite.player_dead3,
                timer, 98).getFxImage();
    }

    public void checkEntity(Entity e) {
        if (
        (this.y == e.getY() && e.getX() - this.x < Sprite.SCALED_SIZE && e.getX() - this.x >= 0) ||
        (this.y == e.getY() && this.x - e.getX() < Sprite.SCALED_SIZE && this.x - e.getX() >= 0) ||
        (this.x == e.getX() && e.getY() - this.y < Sprite.SCALED_SIZE && e.getY() - this.y >= 0) ||
        (this.x == e.getX() && this.y - e.getY() < Sprite.SCALED_SIZE && this.y - e.getY() >= 0)
        ) {
            if (e instanceof BombItem) {
                timer++;
                if (timer % 2 == 0) {
                    this.bombNumber++;
                    BombermanGame.playMusic("res/soundtracks/power.mp3");
                    timer = 0;
                }
                e.broken = true;
            }
            if (e instanceof FlameItem) {
                timer++;
                if (timer % 2 == 0) {
                    this.bombRange++;
                    BombermanGame.playMusic("res/soundtracks/power.mp3");
                    timer = 0;
                }
                e.broken = true;
            }
            if (e instanceof SpeedItem) {
                timer++;
                if (timer % 2 == 0) {
                    this.speed += 0;
                    BombermanGame.playMusic("res/soundtracks/power.mp3");
                    timer = 0;
                }
                e.broken = true;
            }
            if (e instanceof LifeItem) {
                timer++;
                if (timer % 2 == 0) {
                    BombermanGame.playerLives++;
                    BombermanGame.playMusic("res/soundtracks/power.mp3");
                    timer = 0;
                }
                e.broken = true;
            }
            if (e instanceof Portal) {
                if (((Portal) e).opened) {
                    ((Portal) e).passed = true;
                }
            }
        }
    }

    public boolean checkStillObjects (Entity e) {
        int most_leftE1, most_rightE1, most_upE1, most_downE1;
        int most_leftE2, most_rightE2, most_upE2, most_downE2;

        most_leftE1 = this.x;
        most_rightE1 = most_leftE1 + Sprite.SCALED_SIZE;
        most_leftE2 = e.getX();
        most_rightE2 = most_leftE2 + Sprite.SCALED_SIZE;

        most_upE1 = this.y;
        most_downE1 = most_upE1 + Sprite.DEFAULT_SIZE;
        most_upE2 = e.getY();
        most_downE2 = most_upE2 + Sprite.DEFAULT_SIZE;

        if (Math.abs(most_upE1 - most_upE2) < 66) {
            if (most_leftE1 >= most_leftE2 && most_leftE1 < most_rightE2) {
                return true;
            }
            if (most_rightE1 > most_leftE2 && most_rightE1 <= most_rightE2) {
                return true;
            }
        }
        else if (Math.abs(most_leftE1 - most_leftE2) < 40) {
            if (most_upE1 <= most_upE2 && most_upE1 > most_downE2) {
                return true;
            }
            if (most_downE1 >= most_downE2 && most_downE1 < most_upE2) {
                return true;
            }
        }
        return false;
    }
}
