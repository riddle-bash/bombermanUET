package uet.oop.bomberman.stillobjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private int range, timer = 0;
    public boolean exploded, createFlame;

    public Bomb(int xUnit, int yUnit, Image img, int range) {
        super(xUnit, yUnit, img);
        this.range = range;
        this.exploded = false;
        this.createFlame = true;
    }

    @Override
    public void update() {
        timer++;
        if (timer > 128) {
            this.removed = true;
        }
        else {
            this.img = Sprite.movingSprite(
                    Sprite.bomb,
                    Sprite.bomb_1,
                    Sprite.bomb_2,
                    BombermanGame.framePerSecond, 32).getFxImage();
        }
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
