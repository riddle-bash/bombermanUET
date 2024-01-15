package uet.oop.bomberman.entities.immovable;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class LifeItem extends Entity {
    public LifeItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (broken) removed = true;
    }
}
