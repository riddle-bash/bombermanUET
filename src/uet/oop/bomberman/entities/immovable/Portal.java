package uet.oop.bomberman.entities.immovable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Entity {
    public boolean opened = false, passed = false;

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        this.img = Sprite.movingSprite(
                Sprite.portal,
                Sprite.portal_1,
                Sprite.portal_2,
                Sprite.portal_3,
                Sprite.portal_2,
                Sprite.portal_1,
                BombermanGame.framePerSecond, 96).getFxImage();
    }
}
