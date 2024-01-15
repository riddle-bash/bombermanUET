package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.stillobjects.Bomb;

public class Oneal extends Entity {
    protected int timer = 0;
    protected int x_goal; //pixel
    protected int y_goal; //pixel
    //private int count = 0;

    public Oneal(int x, int y, Image img) {
        super( x, y, img);
    }

    public void setX_goal(int x_goal) {
        this.x_goal = x_goal;
    }

    public void setY_goal(int y_goal) {
        this.y_goal = y_goal;
    }

    public boolean isNext() {
        if (x_goal == x && y_goal == y) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
//        count ++;
//        if (count % 2 != 0) {
//            return;
//        }
        int temp_x, temp_y;
        if (!broken) {
            if (x < x_goal) {
                temp_x = x + 1;
                if (!checkCollisionOneal(temp_x, y)) {
                    x ++;
                    this.img = Sprite.movingSprite(
                            Sprite.oneal_right1,
                            Sprite.oneal_right2,
                            Sprite.oneal_right3,
                            BombermanGame.framePerSecond, 48).getFxImage();
                    return;
                }
            } else if (x > x_goal) {
                temp_x = x - 1;
                if (!checkCollisionOneal(temp_x, y)) {
                    x --;
                    this.img = Sprite.movingSprite(
                            Sprite.oneal_left1,
                            Sprite.oneal_left2,
                            Sprite.oneal_left3,
                            BombermanGame.framePerSecond, 48).getFxImage();
                    return;
                }

            }
            if (y < y_goal) {
                temp_y = y + 1;
                if (!checkCollisionOneal(x, temp_y)) {
                    y ++;
                    this.img = Sprite.movingSprite(
                            Sprite.oneal_right1,
                            Sprite.oneal_right2,
                            Sprite.oneal_right3,
                            BombermanGame.framePerSecond, 48).getFxImage();
                    return;
                }
            } else if (y > y_goal) {
                temp_y = y - 1;
                if (!checkCollisionOneal(x, temp_y)) {
                    y --;
                    this.img = Sprite.movingSprite(
                            Sprite.oneal_left1,
                            Sprite.oneal_left2,
                            Sprite.oneal_left3,
                            BombermanGame.framePerSecond, 48).getFxImage();
                    return;
                }
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

    public boolean checkCollisionOneal(int oneal_x, int oneal_y) {
        int most_leftE2, most_rightE2, most_upE2, most_downE2;
        int most_leftE1, most_rightE1, most_upE1, most_downE1;
        for (Entity e : BombermanGame.stillObjects) {
            if (e instanceof Bomb) {
                most_leftE2 = e.getX();
                most_rightE2 = most_leftE2 + Sprite.SCALED_SIZE;
                most_upE2 = e.getY();
                most_downE2 = most_upE2 + Sprite.SCALED_SIZE;

                most_leftE1 = oneal_x;
                most_rightE1 = most_leftE1 + Sprite.SCALED_SIZE;
                most_upE1 = oneal_y;
                most_downE1 = most_upE1 + Sprite.SCALED_SIZE;

                if (Math.abs(most_upE1 - most_upE2) < 30) {
                    if (most_leftE1 >= most_leftE2 && most_leftE1 < most_rightE2) {
                        return true;
                    }
                    if (most_rightE1 > most_leftE2 && most_rightE1 <= most_rightE2) {
                        return true;
                    }
                }
                else if (Math.abs(most_leftE1 - most_leftE2) < 30) {
                    if (most_upE1 <= most_upE2 && most_upE1 > most_downE2) {
                        return true;
                    }
                    if (most_downE1 >= most_downE2 && most_downE1 < most_upE2) {
                        return true;
                    }
                }
            }
        }
        if (timer > 72) this.removed = true;
        return false;
    }

    public void destroy() {
        timer++;
        this.img = Sprite.oneal_dead.getFxImage();
        if (timer > 36)
            this.img = Sprite.movingSprite(
                    Sprite.mob_dead1,
                    Sprite.mob_dead2,
                    Sprite.mob_dead3,
                    BombermanGame.framePerSecond, 32).getFxImage();
    }
}
