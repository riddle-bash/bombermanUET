package uet.oop.bomberman.stillobjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private int timer = 0;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (timer > 32) this.removed = true;
    }

    public void destroy() {
        timer++;
        this.img = Sprite.movingSprite(
                Sprite.brick_exploded,
                Sprite.brick_exploded1,
                Sprite.brick_exploded2,
                BombermanGame.framePerSecond, 32).getFxImage();
    }
}
