package uet.oop.bomberman.collision;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class CheckCollision {
        Entity e1 = null;
        Entity e2 = null;
        public CheckCollision(Entity e1, Entity e2) {
            this.e1 = e1;
            this.e2 = e2;
        }

        public boolean check() {
            int most_leftE1, most_rightE1, most_upE1, most_downE1;
            int most_leftE2, most_rightE2, most_upE2, most_downE2;

            most_leftE1 = e1.getX();
            most_rightE1 = e1.getX() + Sprite.SCALED_SIZE;
            most_leftE2 = e2.getX();
            most_rightE2 = e2.getX() + Sprite.SCALED_SIZE;

            most_upE1 = e1.getY();
            most_downE1 = most_upE1 + Sprite.DEFAULT_SIZE;
            most_upE2 = e2.getY();
            most_downE2 = most_upE2 + Sprite.DEFAULT_SIZE;

            if (Math.abs(most_upE1 - most_upE2) < 30) {
                if (most_leftE1 >= most_leftE2 && most_leftE1 < most_rightE2) {
                    return true;
                }
                if (most_rightE1 > most_leftE2 && most_rightE1 <= most_rightE2) {
                    return true;
                }
            }
            if (Math.abs(most_leftE1 - most_leftE2) < 30) {
                if (most_upE1 <= most_upE2 && most_upE1 > most_downE2) {
                    return true;
                }
                if (most_downE1 >= most_downE2 && most_downE1 < most_upE2) {
                    return true;
                }
            }

            return false;
        }
        public abstract void processAfterCollision();
}
