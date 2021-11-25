package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber extends Entity {

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    public void moveRight() {
        this.x = this.x + 5;
    }

    public void moveLeft() {
        this.x = this.x - 5;
    }

    public void moveUp() {
        this.y = this.y - 5;
    }

    public void moveDown(){
        this.y = this.y + 5;
    }
}
