package uet.oop.bomberman.entities.immovable;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {
    public int r, l, u, d;
    private int range;
    private int timer = 0;

    public Flame(int xUnit, int yUnit, Image img, int range) {
        super(xUnit, yUnit, img);
        this.range = range;
        solid = false;
        r = range; l = range; u = range; d = range;
    }

    @Override
    public void update() {
        timer++;

        if (timer > 32) {
            this.removed = true;
        }
    }

    public void check(Entity e) {
        if (this.y == e.getY() && e.getX() - this.x <= r * Sprite.SCALED_SIZE && e.getX() - this.x > 0) {
            r = ((e.getX() - this.x) / Sprite.SCALED_SIZE) - 1;
        }
        if (this.y == e.getY() && this.x - e.getX() <= l * Sprite.SCALED_SIZE && this.x - e.getX() > 0) {
            l = ((this.x - e.getX()) / Sprite.SCALED_SIZE) - 1;
        }
        if (this.x == e.getX() && e.getY() - this.y <= d * Sprite.SCALED_SIZE && e.getY() - this.y > 0) {
            d = ((e.getY() - this.y) / Sprite.SCALED_SIZE) - 1;
        }
        if (this.x == e.getX() && this.y - e.getY() <= u * Sprite.SCALED_SIZE && this.y - e.getY() > 0) {
            u = ((this.y - e.getY()) / Sprite.SCALED_SIZE) - 1;
        }
    }

    public void destroyS(Entity e) {
        if (this.y == e.getY() && e.getX() - this.x - Sprite.SCALED_SIZE == r * Sprite.SCALED_SIZE && e.getX() - this.x > 0 && r != range) {
            e.broken = true;
        }
        if (this.y == e.getY() && this.x - e.getX() - Sprite.SCALED_SIZE == l * Sprite.SCALED_SIZE && this.x - e.getX() > 0 && l != range) {
            e.broken = true;
        }
        if (this.x == e.getX() && e.getY() - this.y - Sprite.SCALED_SIZE == d * Sprite.SCALED_SIZE && e.getY() - this.y > 0 && d != range) {
            e.broken = true;
        }
        if (this.x == e.getX() && this.y - e.getY() - Sprite.SCALED_SIZE == u * Sprite.SCALED_SIZE && this.y - e.getY() > 0 && u != range) {
            e.broken = true;
        }
    }

    public void destroyE(Entity e) {
        if (this.y == e.getY() && e.getX() - this.x < Sprite.SCALED_SIZE && e.getX() - this.x >= 0) {
            e.broken = true;
        }
        if (this.y == e.getY() && this.x - e.getX() < Sprite.SCALED_SIZE && this.x - e.getX() >= 0) {
            e.broken = true;
        }
        if (this.x == e.getX() && e.getY() - this.y < Sprite.SCALED_SIZE && e.getY() - this.y >= 0) {
            e.broken = true;
        }
        if (this.x == e.getX() && this.y - e.getY() < Sprite.SCALED_SIZE && this.y - e.getY() >= 0) {
            e.broken = true;
        }
    }

    public void explode() {
        this.img = Sprite.movingSprite(
                Sprite.bomb_exploded,
                Sprite.bomb_exploded1,
                Sprite.bomb_exploded2,
                BombermanGame.framePerSecond, 32).getFxImage();
    }

    public void explodeMostRight() {
        this.img = Sprite.movingSprite(
                Sprite.explosion_horizontal_right_last,
                Sprite.explosion_horizontal_right_last1,
                Sprite.explosion_horizontal_right_last2,
                BombermanGame.framePerSecond, 32).getFxImage();
    }
    public void explodeMostLeft() {
        this.img = Sprite.movingSprite(
                Sprite.explosion_horizontal_left_last,
                Sprite.explosion_horizontal_left_last1,
                Sprite.explosion_horizontal_left_last2,
                BombermanGame.framePerSecond, 32).getFxImage();
    }
    public void explodeMostUp() {
        this.img = Sprite.movingSprite(
                Sprite.explosion_vertical_top_last,
                Sprite.explosion_vertical_top_last1,
                Sprite.explosion_vertical_top_last2,
                BombermanGame.framePerSecond, 32).getFxImage();
    }
    public void explodeMostDown() {
        this.img = Sprite.movingSprite(
                Sprite.explosion_vertical_down_last,
                Sprite.explosion_vertical_down_last1,
                Sprite.explosion_vertical_down_last2,
                BombermanGame.framePerSecond, 32).getFxImage();
    }
    public void explodeHorizon() {
        this.img = Sprite.movingSprite(
                Sprite.explosion_horizontal,
                Sprite.explosion_horizontal1,
                Sprite.explosion_horizontal2,
                BombermanGame.framePerSecond, 32).getFxImage();
    }
    public void explodeVertical() {
        this.img = Sprite.movingSprite(
                Sprite.explosion_vertical,
                Sprite.explosion_vertical1,
                Sprite.explosion_vertical2,
                BombermanGame.framePerSecond, 32).getFxImage();
    }
}
