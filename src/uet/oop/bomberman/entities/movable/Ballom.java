package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Ballom extends Entity {
    private int timer = 0;
    public Ballom(int x, int y, Image img) {
        super( x, y, img);
    }
    public int k_x = 1;

    @Override
    public void update() {
        if (!broken) {
            x += k_x;
            if (k_x < 0) {
                this.img = Sprite.movingSprite(
                        Sprite.balloom_left1,
                        Sprite.balloom_left2,
                        Sprite.balloom_left3,
                        BombermanGame.framePerSecond, 48).getFxImage();
            }
            else {
                this.img = Sprite.movingSprite(
                        Sprite.balloom_right1,
                        Sprite.balloom_right2,
                        Sprite.balloom_right3,
                        BombermanGame.framePerSecond, 48).getFxImage();
            }
        }

        if (timer > 72) this.removed = true;
    }

    public void checkEntity(Entity e) {
        if (
            (this.y == e.getY() && e.getX() - this.x < 32 && e.getX() - this.x >= 0) ||
            (this.y == e.getY() && this.x - e.getX() < 32 && this.x - e.getX() >= 0) ||
            (this.x == e.getX() && e.getY() - this.y < 32 && e.getY() - this.y >= 0) ||
            (this.x == e.getX() && this.y - e.getY() < 32 && this.y - e.getY() >= 0)
        ) {
            if (e instanceof Bomber) {
                e.broken = true;
            }
        }
    }

    public void checkCollision(Entity e) {
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

        if (Math.abs(most_upE1 - most_upE2) < 30) {
            if (most_leftE1 >= most_leftE2 && most_leftE1 < most_rightE2) {
                k_x *= -1;
            }
            if (most_rightE1 > most_leftE2 && most_rightE1 <= most_rightE2) {
                k_x *= -1;
            }
        }
        else if (Math.abs(most_leftE1 - most_leftE2) < 30) {
            if (most_upE1 <= most_upE2 && most_upE1 > most_downE2) {
                k_x *= -1;
            }
            if (most_downE1 >= most_downE2 && most_downE1 < most_upE2) {
                k_x *= -1;
            }
        }
    }

    public void destroy() {
        timer++;
        this.img = Sprite.balloom_dead.getFxImage();
        if (timer > 36)
        this.img = Sprite.movingSprite(
                Sprite.mob_dead1,
                Sprite.mob_dead2,
                Sprite.mob_dead3,
                BombermanGame.framePerSecond, 32).getFxImage();
    }
}
