package uet.oop.bomberman.entities.immovable;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class SpeedItem extends Entity {
    public SpeedItem(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (broken) removed = true;
    }
}
