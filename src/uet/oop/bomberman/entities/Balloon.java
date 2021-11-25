package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN
}

public class Balloon extends Entity {
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
    }

    Direction direction = Direction.RIGHT;

    @Override
    public void update() {
        if (canMove(this, BombermanGame.mapMatrix) == Direction.RIGHT) {
            this.x = this.x + 1;
            this.direction = Direction.RIGHT;
            this.img = Sprite.balloom_right1.getFxImage();
        } else if (canMove(this, BombermanGame.mapMatrix) == Direction.LEFT) {
            this.x = this.x - 1;
            this.direction = Direction.LEFT;
            this.img = Sprite.balloom_left1.getFxImage();
        } else if (canMove(this, BombermanGame.mapMatrix) == Direction.UP) {
            this.y = this.y - 1;
            this.direction = Direction.UP;
            this.img = Sprite.balloom_right1.getFxImage();
        } else if (canMove(this, BombermanGame.mapMatrix) == Direction.DOWN) {
            this.y = this.y + 1;
            this.direction = Direction.DOWN;
            this.img = Sprite.balloom_left1.getFxImage();
        }
    }

    public static Direction canMove(Balloon balloon, char[][] mapMatrix) {
        return Direction.LEFT;
    }
}
