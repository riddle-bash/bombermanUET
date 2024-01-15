package uet.oop.bomberman.graphics;

import javafx.scene.image.*;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

/**
 * Lưu trữ thông tin các pixel của 1 sprite (hình ảnh game)
 */
public class Sprite {
	
	public static final int DEFAULT_SIZE = 66;
	public static final int SCALED_SIZE = DEFAULT_SIZE;
    private static final int TRANSPARENT_COLOR = 0xffff00ff;
	public final int SIZE;
	private int _x, _y;
	public int[] _pixels;
	protected int _realWidth;
	protected int _realHeight;
	private SpriteSheet _sheet;

	/*
	|--------------------------------------------------------------------------
	| Board sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite grass = new Sprite(DEFAULT_SIZE, 6, 6, SpriteSheet.tiles, 66, 66);
	public static Sprite brick = new Sprite(DEFAULT_SIZE, 0, 7, SpriteSheet.tiles, 66, 66);
	public static Sprite wall = new Sprite(DEFAULT_SIZE, 0, 6, SpriteSheet.tiles, 66, 66);
	public static Sprite portal = new Sprite(DEFAULT_SIZE, 6, 5, SpriteSheet.tiles, 66, 66);
	public static Sprite portal_1 = new Sprite(DEFAULT_SIZE, 7, 5, SpriteSheet.tiles, 66, 66);
	public static Sprite portal_2 = new Sprite(DEFAULT_SIZE, 8, 5, SpriteSheet.tiles, 66, 66);
	public static Sprite portal_3 = new Sprite(DEFAULT_SIZE, 9, 5, SpriteSheet.tiles, 66, 66);
	public static Sprite portal_4 = new Sprite(DEFAULT_SIZE, 10, 5, SpriteSheet.tiles, 66, 66);
	public static Sprite portal_5 = new Sprite(DEFAULT_SIZE, 11, 5, SpriteSheet.tiles, 66, 66);
	
	/*
	|--------------------------------------------------------------------------
	| Bomber Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite player_up = new Sprite(DEFAULT_SIZE, 3, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_down = new Sprite(DEFAULT_SIZE, 3, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_left = new Sprite(DEFAULT_SIZE, 3, 0, SpriteSheet.tiles, 66, 66);
	public static Sprite player_right = new Sprite(DEFAULT_SIZE, 3, 1, SpriteSheet.tiles, 66, 66);

	public static Sprite player_up_1 = new Sprite(DEFAULT_SIZE, 4, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_up_2 = new Sprite(DEFAULT_SIZE, 5, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_up_3 = new Sprite(DEFAULT_SIZE, 6, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_up_4 = new Sprite(DEFAULT_SIZE, 7, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_up_5 = new Sprite(DEFAULT_SIZE, 8, 1, SpriteSheet.tiles, 66, 66);

	public static Sprite player_down_1 = new Sprite(DEFAULT_SIZE, 4, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_down_2 = new Sprite(DEFAULT_SIZE, 5, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_down_3 = new Sprite(DEFAULT_SIZE, 6, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_down_4 = new Sprite(DEFAULT_SIZE, 7, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_down_5 = new Sprite(DEFAULT_SIZE, 8, 1, SpriteSheet.tiles, 66, 66);

	public static Sprite player_left_1 = new Sprite(DEFAULT_SIZE, 4, 0, SpriteSheet.tiles, 66, 66);
	public static Sprite player_left_2 = new Sprite(DEFAULT_SIZE, 5, 0, SpriteSheet.tiles, 66 ,66);
	public static Sprite player_left_3 = new Sprite(DEFAULT_SIZE, 6, 0, SpriteSheet.tiles, 66 ,66);
	public static Sprite player_left_4 = new Sprite(DEFAULT_SIZE, 7, 0, SpriteSheet.tiles, 66 ,66);
	public static Sprite player_left_5 = new Sprite(DEFAULT_SIZE, 8, 0, SpriteSheet.tiles, 66 ,66);

	public static Sprite player_right_1 = new Sprite(DEFAULT_SIZE, 4, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_right_2 = new Sprite(DEFAULT_SIZE, 5, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_right_3 = new Sprite(DEFAULT_SIZE, 6, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_right_4 = new Sprite(DEFAULT_SIZE, 7, 1, SpriteSheet.tiles, 66, 66);
	public static Sprite player_right_5 = new Sprite(DEFAULT_SIZE, 8, 1, SpriteSheet.tiles, 66, 66);

	public static Sprite player_dead1 = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.tiles, 66, 66);
	public static Sprite player_dead2 = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.tiles, 66, 66);
	public static Sprite player_dead3 = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.tiles, 66, 66);

	/*
	|--------------------------------------------------------------------------
	| Character
	|--------------------------------------------------------------------------
	 */
	//BALLOM
	public static Sprite balloom_left1 = new Sprite(DEFAULT_SIZE, 10, 0, SpriteSheet.tiles, 66, 66);
	public static Sprite balloom_left2 = new Sprite(DEFAULT_SIZE, 11, 0, SpriteSheet.tiles, 66, 66);
	public static Sprite balloom_left3 = new Sprite(DEFAULT_SIZE, 10, 1, SpriteSheet.tiles, 66, 66);

	public static Sprite balloom_right1 = new Sprite(DEFAULT_SIZE, 10, 0, SpriteSheet.tiles, 66, 66);
	public static Sprite balloom_right2 = new Sprite(DEFAULT_SIZE, 11, 0, SpriteSheet.tiles, 66, 66);
	public static Sprite balloom_right3 = new Sprite(DEFAULT_SIZE, 10, 1, SpriteSheet.tiles, 66, 66);

	public static Sprite balloom_dead = new Sprite(DEFAULT_SIZE, 9, 0, SpriteSheet.tiles, 66, 66);

	//ONEAL
	public static Sprite oneal_left1 = new Sprite(DEFAULT_SIZE, 1, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite oneal_left2 = new Sprite(DEFAULT_SIZE, 2, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite oneal_left3 = new Sprite(DEFAULT_SIZE, 3, 4, SpriteSheet.tiles, 66, 66);

	public static Sprite oneal_right1 = new Sprite(DEFAULT_SIZE, 1, 5, SpriteSheet.tiles, 66, 66);
	public static Sprite oneal_right2 = new Sprite(DEFAULT_SIZE, 2, 5, SpriteSheet.tiles, 66, 66);
	public static Sprite oneal_right3 = new Sprite(DEFAULT_SIZE, 3, 5, SpriteSheet.tiles, 66, 66);

	public static Sprite oneal_dead = new Sprite(DEFAULT_SIZE, 0, 4, SpriteSheet.tiles, 66, 66);

	//Doll
	public static Sprite doll_left1 = new Sprite(DEFAULT_SIZE, 8, 2, SpriteSheet.tiles, 66, 66);
	public static Sprite doll_left2 = new Sprite(DEFAULT_SIZE, 9, 2, SpriteSheet.tiles, 66, 66);
	public static Sprite doll_left3 = new Sprite(DEFAULT_SIZE, 10, 2, SpriteSheet.tiles, 66, 66);

	public static Sprite doll_right1 = new Sprite(DEFAULT_SIZE, 8, 3, SpriteSheet.tiles, 66, 66);
	public static Sprite doll_right2 = new Sprite(DEFAULT_SIZE, 9, 3, SpriteSheet.tiles, 66, 66);
	public static Sprite doll_right3 = new Sprite(DEFAULT_SIZE, 10, 3, SpriteSheet.tiles, 66, 66);

	public static Sprite doll_dead = new Sprite(DEFAULT_SIZE, 6, 2, SpriteSheet.tiles, 66, 66);

	//Minvo
	public static Sprite minvo_left1 = new Sprite(DEFAULT_SIZE, 8, 5, SpriteSheet.tiles, 66, 66);
	public static Sprite minvo_left2 = new Sprite(DEFAULT_SIZE, 8, 6, SpriteSheet.tiles, 66, 66);
	public static Sprite minvo_left3 = new Sprite(DEFAULT_SIZE, 8, 7, SpriteSheet.tiles, 66, 66);

	public static Sprite minvo_right1 = new Sprite(DEFAULT_SIZE, 9, 5, SpriteSheet.tiles, 66, 66);
	public static Sprite minvo_right2 = new Sprite(DEFAULT_SIZE, 9, 6, SpriteSheet.tiles, 66, 66);
	public static Sprite minvo_right3 = new Sprite(DEFAULT_SIZE, 9, 7, SpriteSheet.tiles, 66, 66);

	public static Sprite minvo_dead = new Sprite(DEFAULT_SIZE, 8, 8, SpriteSheet.tiles, 66, 66);

	//Kondoria
	public static Sprite kondoria_left1 = new Sprite(DEFAULT_SIZE, 10, 5, SpriteSheet.tiles, 66, 66);
	public static Sprite kondoria_left2 = new Sprite(DEFAULT_SIZE, 10, 6, SpriteSheet.tiles, 66, 66);
	public static Sprite kondoria_left3 = new Sprite(DEFAULT_SIZE, 10, 7, SpriteSheet.tiles, 66, 66);

	public static Sprite kondoria_right1 = new Sprite(DEFAULT_SIZE, 66, 5, SpriteSheet.tiles, 66, 66);
	public static Sprite kondoria_right2 = new Sprite(DEFAULT_SIZE, 66, 6, SpriteSheet.tiles, 66, 66);
	public static Sprite kondoria_right3 = new Sprite(DEFAULT_SIZE, 66, 7, SpriteSheet.tiles, 66, 66);

	public static Sprite kondoria_dead = new Sprite(DEFAULT_SIZE, 10, 8, SpriteSheet.tiles, 66, 66);

	//ALL
	public static Sprite mob_dead1 = new Sprite(DEFAULT_SIZE, 9, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite mob_dead2 = new Sprite(DEFAULT_SIZE, 10, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite mob_dead3 = new Sprite(DEFAULT_SIZE, 11, 4, SpriteSheet.tiles, 66, 66);

	/*
	|--------------------------------------------------------------------------
	| Bomb Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite bomb = new Sprite(DEFAULT_SIZE, 9, 7, SpriteSheet.tiles, 66, 66);
	public static Sprite bomb_1 = new Sprite(DEFAULT_SIZE, 10, 7, SpriteSheet.tiles, 66, 66);
	public static Sprite bomb_2 = new Sprite(DEFAULT_SIZE, 11, 7, SpriteSheet.tiles, 66, 66);

	/*
	|--------------------------------------------------------------------------
	| FlameSegment Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite bomb_exploded = new Sprite(DEFAULT_SIZE, 8, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite bomb_exploded1 = new Sprite(DEFAULT_SIZE, 7, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite bomb_exploded2 = new Sprite(DEFAULT_SIZE, 6, 4, SpriteSheet.tiles, 66, 66);

	public static Sprite explosion_vertical = new Sprite(DEFAULT_SIZE, 8, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_vertical1 = new Sprite(DEFAULT_SIZE, 7, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_vertical2 = new Sprite(DEFAULT_SIZE, 6, 4, SpriteSheet.tiles, 66, 66);

	public static Sprite explosion_horizontal = new Sprite(DEFAULT_SIZE, 8, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_horizontal1 = new Sprite(DEFAULT_SIZE, 7, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_horizontal2 = new Sprite(DEFAULT_SIZE, 6, 4, SpriteSheet.tiles, 66, 66);

	public static Sprite explosion_horizontal_left_last = new Sprite(DEFAULT_SIZE, 8, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_horizontal_left_last1 = new Sprite(DEFAULT_SIZE, 7, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_horizontal_left_last2 = new Sprite(DEFAULT_SIZE, 6, 4, SpriteSheet.tiles, 66, 66);

	public static Sprite explosion_horizontal_right_last = new Sprite(DEFAULT_SIZE, 8, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_horizontal_right_last1 = new Sprite(DEFAULT_SIZE, 7, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_horizontal_right_last2 = new Sprite(DEFAULT_SIZE, 6, 4, SpriteSheet.tiles, 66, 66);

	public static Sprite explosion_vertical_top_last = new Sprite(DEFAULT_SIZE, 8, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_vertical_top_last1 = new Sprite(DEFAULT_SIZE, 7, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_vertical_top_last2 = new Sprite(DEFAULT_SIZE, 6, 4, SpriteSheet.tiles, 66, 66);

	public static Sprite explosion_vertical_down_last = new Sprite(DEFAULT_SIZE, 8, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_vertical_down_last1 = new Sprite(DEFAULT_SIZE, 7, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite explosion_vertical_down_last2 = new Sprite(DEFAULT_SIZE, 6, 4, SpriteSheet.tiles, 66, 66);

	/*
	|--------------------------------------------------------------------------
	| Brick FlameSegment
	|--------------------------------------------------------------------------
	 */
	public static Sprite brick_exploded = new Sprite(DEFAULT_SIZE, 9, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite brick_exploded1 = new Sprite(DEFAULT_SIZE, 10, 4, SpriteSheet.tiles, 66, 66);
	public static Sprite brick_exploded2 = new Sprite(DEFAULT_SIZE, 11, 4, SpriteSheet.tiles, 66, 66);

	/*
	|--------------------------------------------------------------------------
	| Powerups
	|--------------------------------------------------------------------------
	 */
	public static Sprite powerup_bombs = new Sprite(DEFAULT_SIZE, 6, 7, SpriteSheet.tiles, 66, 66);
	public static Sprite powerup_flames = new Sprite(DEFAULT_SIZE, 4, 7, SpriteSheet.tiles, 66, 66);
	public static Sprite powerup_speed = new Sprite(DEFAULT_SIZE, 7, 7, SpriteSheet.tiles, 66, 66);
	public static Sprite powerup_life = new Sprite(DEFAULT_SIZE, 5, 7, SpriteSheet.tiles, 66, 66);
	public static Sprite powerup_wallpass = new Sprite(DEFAULT_SIZE, 3, 10, SpriteSheet.tiles, 66, 66);
	public static Sprite powerup_detonator = new Sprite(DEFAULT_SIZE, 4, 10, SpriteSheet.tiles, 66, 66);
	public static Sprite powerup_bombpass = new Sprite(DEFAULT_SIZE, 5, 10, SpriteSheet.tiles, 66, 66);
	public static Sprite powerup_flamepass = new Sprite(DEFAULT_SIZE, 6, 10, SpriteSheet.tiles, 66, 66);
	
	public Sprite(int size, int x, int y, SpriteSheet sheet, int rw, int rh) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		_x = x * SIZE;
		_y = y * SIZE;
		_sheet = sheet;
		_realWidth = rw;
		_realHeight = rh;
		load();
	}
	
	public Sprite(int size, int color) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	private void setColor(int color) {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = color;
		}
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				_pixels[x + y * SIZE] = _sheet._pixels[(x + _x) + (y + _y) * _sheet.SIZE];
			}
		}
	}
	
	public static Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2, int animate, int time) {
		int calc = animate % time;
		int diff = time / 3;
		
		if(calc < diff) {
			return normal;
		}
			
		if(calc < diff * 2) {
			return x1;
		}
			
		return x2;
	}

	public static Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2, Sprite x3, Sprite x4, Sprite x5, int animate, int time) {
		int calc = animate % time;
		int diff = time / 6;

		if (calc < diff) return normal;
		if (calc < diff * 2) return x1;
		if (calc < diff * 3) return x2;
		if (calc < diff * 4) return x3;
		if (calc < diff * 5) return x4;
		return x5;
	}
	
	public static Sprite movingSprite(Sprite x1, Sprite x2, int animate, int time) {
		int diff = time / 2;
		return (animate % time > diff) ? x1 : x2; 
	}
	
	public int getSize() {
		return SIZE;
	}

	public int getPixel(int i) {
		return _pixels[i];
	}

	public Image getFxImage() {
        WritableImage wr = new WritableImage(SIZE, SIZE);
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if ( _pixels[x + y * SIZE] == TRANSPARENT_COLOR) {
                    pw.setArgb(x, y, 0);
                }
                else {
                    pw.setArgb(x, y, _pixels[x + y * SIZE]);
                }
            }
        }
        Image input = new ImageView(wr).getImage();
        return resample(input, SCALED_SIZE / DEFAULT_SIZE);
    }

	private Image resample(Image input, int scaleFactor) {
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final int S = scaleFactor;

		WritableImage output = new WritableImage(
				W * S,
				H * S
		);

		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				final int argb = reader.getArgb(x, y);
				for (int dy = 0; dy < S; dy++) {
					for (int dx = 0; dx < S; dx++) {
						writer.setArgb(x * S + dx, y * S + dy, argb);
					}
				}
			}
		}

		return output;
	}
}
