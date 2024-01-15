package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.immovable.*;
import uet.oop.bomberman.entities.movable.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.mapGame.Convert2DToAdjMatrix;
import uet.oop.bomberman.stillobjects.Bomb;
import uet.oop.bomberman.stillobjects.Brick;
import uet.oop.bomberman.stillobjects.Grass;
import uet.oop.bomberman.stillobjects.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 28;
    public static final int HEIGHT = 13;
    
    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> backgrounds = new ArrayList<>();
    public static boolean[][] mapGame = new boolean[HEIGHT][WIDTH];
    public static boolean[][] adjMatrix = new boolean[HEIGHT*WIDTH][HEIGHT*WIDTH];
    public static HashMap<Entity, Integer> nextLocalOneal = new HashMap<>();
    public static HashMap<Entity, Integer> nextLocalDoll = new HashMap<>();

    private Bomber bomberman;
    private boolean walking = false;
    public static int framePerSecond;
    public static MediaPlayer mediaPlayer;
    public MediaPlayer BGMp;
    private Canvas canvas2;
    private GraphicsContext gc2;

    public long startTime, currentTime, leftTime, fps;
    public static int playerLives = 3, gamePoints = 0, gameLevel = 1, enemiesNumber = 0;
    public boolean gamePaused = false, gameOver = false, gameComplete = false, gamePassed = false, gameCredit = false;
    public boolean sldSetting = false, sldMute = false, sldPause = false, gameStart = true, gameSetting = false;
    public boolean sldYes = false, sldNo = false, sldPlay = false, sldContinue = false, sldCredit = false, gameEnd = false;
    public String currentBGMusic;
    public static String currentMusic;

    public BombermanGame() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                mapGame[i][j] = false;
            }
        }
    }

    public Bomber getBomberman() {
        return bomberman;
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        canvas2 = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 1));
        gc2 = canvas2.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(canvas2);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        startTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (!gameStart && !gameCredit && !gameComplete && !gameOver && !gamePassed) {
                walking = true;
                Bomber hitBox = new Bomber(bomberman);
                switch (keyEvent.getCode()) {
                    case RIGHT: {
                        if (!bomberman.broken) {
                            bomberman.right();
                            if (framePerSecond % 10 > 8)
                                playMusic("res/soundtracks/footstep.mp3");
                            hitBox.moveRight();
                            for (Entity e : stillObjects) {
                                if (hitBox.checkStillObjects(e)) {
                                    if (e instanceof Wall
                                            || e instanceof Brick) walking = false;
                                    if (!walking) break;

                                }
                            }
                            if (walking) bomberman.moveRight();
                        }
                        break;
                    }
                    case LEFT: {
                        if (!bomberman.broken) {
                            bomberman.left();
                            if (framePerSecond % 10 > 8)
                                playMusic("res/soundtracks/footstep.mp3");
                            hitBox.moveLeft();
                            for (Entity e : stillObjects) {
                                if (hitBox.checkStillObjects(e)) {
                                    if (e instanceof Wall
                                            || e instanceof Brick) walking = false;
                                    if (!walking) break;
                                }
                            }
                            if (walking) bomberman.moveLeft();
                        }
                        break;
                    }
                    case DOWN: {
                        if (!bomberman.broken) {
                            bomberman.up();
                            if (framePerSecond % 10 > 8)
                                playMusic("res/soundtracks/footstep.mp3");
                            hitBox.moveDown();
                            for (Entity e : stillObjects) {
                                if (hitBox.checkStillObjects(e)) {
                                    if (e instanceof Wall
                                            || e instanceof Brick) walking = false;
                                    if (!walking) break;
                                }
                            }
                            if (walking) bomberman.moveDown();
                        }
                        break;
                    }
                    case UP: {
                        if (!bomberman.broken) {
                            bomberman.down();
                            if (framePerSecond % 10 > 8)
                                playMusic("res/soundtracks/footstep.mp3");
                            hitBox.moveUp();
                            for (Entity e : stillObjects) {
                                if (hitBox.checkStillObjects(e)) {
                                    if (e instanceof Wall
                                            || e instanceof Brick) walking = false;
                                    if (!walking) break;
                                }
                            }
                            if (walking) bomberman.moveUp();
                        }
                        break;
                    }
                    case SPACE: {
                        if (bomberman.bombNumber > 0 && !bomberman.broken) {
                            playMusic("res/soundtracks/planted.mp3");
                            int xUnit = (int)(Math.round((float)bomberman.getX() / Sprite.SCALED_SIZE));
                            int yUnit = (int)(Math.round((float)bomberman.getY() / Sprite.SCALED_SIZE));
                            stillObjects.add(new Bomb(
                                    xUnit, yUnit, Sprite.bomb.getFxImage(), bomberman.bombRange ));
                            //update map for oneal
                            int local = yUnit * WIDTH + xUnit;

                            for (int k = 0; k < WIDTH * HEIGHT; k ++) {
                                adjMatrix[local][k] = false;
                                adjMatrix[k][local] = false;
                                Convert2DToAdjMatrix.AdjMatrixBackUp_copy[local][k] = false;
                                Convert2DToAdjMatrix.AdjMatrixBackUp_copy[k][local] = false;
                            }
                            bomberman.bombNumber--;
                        }
                        break;
                    }
                    case F1: {
                        gamePaused = !gamePaused;
                        if (gamePaused) BGMp.pause();
                        else BGMp.play();
                        break;
                    }
                    case F2: {
                        gameComplete = true;
                        if (gameLevel == 10) {
                            gameEnd = true;
                        } else gameLevel++;
                        break;
                    }
                    case ESCAPE: {
                        gameSetting = !gameSetting;
                        break;
                    }
                }
            }

        });

        scene.addEventFilter(MouseEvent.ANY, mouseEvent -> {
            if (gameStart) {
                if (mouseEvent.getX() > canvas2.getWidth() / 2 - 150 && mouseEvent.getY() > 410
                        && mouseEvent.getX() < canvas2.getWidth() / 2 + 150 && mouseEvent.getY() < 550) {
                    sldPlay = true;
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                        currentTime = System.currentTimeMillis();
                        gameLevel = 1;
                        leftTime = 0;
                        gamePoints = 0;
                        gameStart = false;
                        gameComplete = true;
                    }
                } else sldPlay = false;
                if (mouseEvent.getX() > canvas2.getWidth() / 2 - 150 && mouseEvent.getY() > 580
                        && mouseEvent.getX() < canvas2.getWidth() / 2 + 150 && mouseEvent.getY() < 720) {
                    sldContinue = true;
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                        currentTime = System.currentTimeMillis();
                        leftTime = 0;
                        gameStart = false;
                        gameComplete = true;
                    }
                } else sldContinue = false;
                if (mouseEvent.getX() > canvas2.getWidth() / 2 - 150 && mouseEvent.getY() > 750
                        && mouseEvent.getX() < canvas2.getWidth() / 2 + 150 && mouseEvent.getY() < 890) {
                    sldCredit = true;
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                        gameCredit = !gameCredit;
                    }
                } else sldCredit = false;
            }
            else {
                if (mouseEvent.getX() > 33 && mouseEvent.getY() > canvas.getHeight() + 17
                        && mouseEvent.getX() < 63 && mouseEvent.getY() < canvas.getHeight() + 47) {
                    sldSetting = true;
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                        gameSetting = !gameSetting;
                    }
                } else sldSetting = false;
                if (mouseEvent.getX() > 66 && mouseEvent.getY() > canvas.getHeight() + 16
                        && mouseEvent.getX() < 96 && mouseEvent.getY() < canvas.getHeight() + 47) {
                    sldMute = true;
                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                        BGMp.setMute(!BGMp.isMute());
                    }
                } else sldMute = false;
                if (gameSetting) {
                    if (mouseEvent.getX() > canvas2.getWidth() / 2 + 66 && mouseEvent.getY() > canvas.getHeight() + 14
                            && mouseEvent.getX() < canvas2.getWidth() / 2 + 132 && mouseEvent.getY() < canvas.getHeight() + 47) {
                        sldYes = true;
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                            gameStart = true;
                            BGMp.setMute(!BGMp.isMute());
                        }
                    } else sldYes = false;
                    if (mouseEvent.getX() > canvas2.getWidth() / 2 + 66 * 3 && mouseEvent.getY() > canvas.getHeight() + 14
                            && mouseEvent.getX() < canvas2.getWidth() / 2 + 66 * 3 + 50 && mouseEvent.getY() < canvas.getHeight() + 47) {
                        sldNo = true;
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                            gameSetting = false;
                        }
                    } else sldNo = false;
                } else {
                    if (mouseEvent.getX() > 99 && mouseEvent.getY() > canvas.getHeight() + 14
                            && mouseEvent.getX() < 129 && mouseEvent.getY() < canvas.getHeight() + 47) {
                        sldPause = true;
                        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                            gamePaused = !gamePaused;
                            if (gamePaused) BGMp.pause();
                            else BGMp.play();
                        }
                    } else sldPause = false;
                }
            }

        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
                render();
                stage.setTitle("Bomberman Alpha | " + fps + " fps");
            }
        };
        timer.start();
    }

    public void createMap(int level) {
        entities.clear();
        stillObjects.clear();
        String path = "res/levels/Level" + level + ".txt";
        int rows;
        List<String> map_game = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(path));
            String temp = scanner.nextLine();
            String[] info_level = temp.split(" ");

            rows = Integer.parseInt(info_level[1]);

            for (int i = 0; i < rows; i++) {
                map_game.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                mapGame[i][j] = false;
            }
        }
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                Entity cover;
                Entity background;
                background = new Grass(j, i, Sprite.grass.getFxImage());
                backgrounds.add(background);

                switch (map_game.get(i).charAt(j)) {
                    case 'p':
                        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
                        entities.add(bomberman);
                        break;
                    case '#':
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        mapGame[i][j] = true;
                        break;
                    case '*':
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        mapGame[i][j] = true;
                        break;
                    case 'x':
                        object = new Portal(j, i, Sprite.portal.getFxImage());
                        entities.add(object);
                        cover = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(cover);
                        mapGame[i][j] = true;
                        break;
                    case '1':
                        object = new Ballom(j, i, Sprite.balloom_left1.getFxImage());
                        entities.add(object);
                        enemiesNumber++;
                        break;
                    case '2':
                        object = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                        nextLocalOneal.put (object, Integer.valueOf(i * WIDTH + j));
                        entities.add(object);
                        enemiesNumber++;
                        break;
                    case '3':
                        object = new Doll(j, i, Sprite.doll_left1.getFxImage());
                        nextLocalDoll.put (object, Integer.valueOf(i * WIDTH + j));
                        entities.add(object);
                        enemiesNumber++;
                        break;
                    case 'b':
                        object = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                        entities.add(object);
                        cover = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(cover);
                        mapGame[i][j] = true;
                        break;
                    case 'f':
                        object = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        entities.add(object);
                        cover = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(cover);
                        mapGame[i][j] = true;
                        break;
                    case 's':
                        object = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                        entities.add(object);
                        cover = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(cover);
                        mapGame[i][j] = true;
                        break;
                    case 'l':
                        object = new LifeItem(j, i, Sprite.powerup_life.getFxImage());
                        entities.add(object);
                        cover = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(cover);
                        mapGame[i][j] = true;
                        break;
                }
            }
        }
        Convert2DToAdjMatrix.convert(mapGame, adjMatrix);
        for (Entity e : entities) {
            int local_bomber = 1 * WIDTH + 1;
            if (e instanceof Oneal) {
                int local_enemy = (e.getY() / Sprite.SCALED_SIZE) * WIDTH + (e.getX() / Sprite.SCALED_SIZE); //local pixel -> local unit ->local adjMatrix
                ArrayList<Integer> way = Convert2DToAdjMatrix.findWay(adjMatrix, local_bomber, local_enemy);
                if (way.size() > 0) {
                    nextLocalOneal.put(e, Integer.valueOf(Convert2DToAdjMatrix.findWay(adjMatrix, local_bomber, local_enemy).get(0)));
                }
                ((Oneal) e).setY_goal((nextLocalOneal.get(e) / WIDTH) * Sprite.SCALED_SIZE);
                ((Oneal) e).setX_goal((nextLocalOneal.get(e) - (nextLocalOneal.get(e) / WIDTH) * WIDTH) * Sprite.SCALED_SIZE);
            }
            if (e instanceof Doll) {
                int local_enemy = (e.getY() / Sprite.SCALED_SIZE) * WIDTH + (e.getX() / Sprite.SCALED_SIZE); //local pixel -> local unit ->local adjMatrix
                ArrayList<Integer> way = Convert2DToAdjMatrix.findWay(Convert2DToAdjMatrix.AdjMatrixBackUp_copy, local_bomber, local_enemy);
                if (way.size() > 0) {
                    if (way.size() < 10) {
                        nextLocalDoll.put(e, Integer.valueOf(Convert2DToAdjMatrix.findWay(Convert2DToAdjMatrix.AdjMatrixBackUp_copy, local_bomber, local_enemy).get(0)));
                    }
                }
                ((Doll) e).setY_goal((nextLocalDoll.get(e) / WIDTH) * Sprite.SCALED_SIZE);
                ((Doll) e).setX_goal((nextLocalDoll.get(e) - (nextLocalDoll.get(e) / WIDTH) * WIDTH) * Sprite.SCALED_SIZE);
            }
        }
    }

    public void update() {
        if (!gamePaused && !gameOver && !gameComplete && !gamePassed && !gameStart && !gameSetting && !gameEnd) {
            framePerSecond++;
            currentTime = System.currentTimeMillis();
            leftTime = 200 - (currentTime - startTime) / 1000;

            if (leftTime < 0) gameOver = true;

            fps = framePerSecond * 1000L / (currentTime - startTime);

            if (playerLives == 0) gameOver = true;
            if (leftTime < 61) playBGMusic("res/soundtracks/find_door.mp3", 10);

            for (int i = 0; i < entities.size(); i++) {
                Entity e = entities.get(i);
                if (e instanceof Ballom) {
                    if (e.removed) {
                        enemiesNumber--;
                        playMusic("res/soundtracks/mob.mp3");
                        gamePoints += 200;
                    }
                    for (Entity s : stillObjects) {
                        ((Ballom) e).checkCollision(s);
                    }
                    ((Ballom) e).checkEntity(bomberman);
                }
                if (e instanceof Bomber) {
                    for (Entity s : entities) {
                        ((Bomber) e).checkEntity(s);
                    }
                    if (e.removed) {
                        playMusic("res/soundtracks/die.mp3");
                        playerLives--;
                        entities.remove(e);
                        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
                        entities.add(bomberman);
                    }
                }
                if (e instanceof Oneal) {
                    if (e.removed) {
                        enemiesNumber--;
                        playMusic("res/soundtracks/mob.mp3");
                        gamePoints += 500;
                    }
                    if (((Oneal) e).isNext()) {
                        //get next local
                        int local_bomber = (bomberman.getY() / Sprite.SCALED_SIZE) * WIDTH + bomberman.getX() / Sprite.SCALED_SIZE;
                        int local_oneal = nextLocalOneal.get(e);
                        ArrayList<Integer> way = Convert2DToAdjMatrix.findWay(adjMatrix, local_bomber, local_oneal);
                        if (way.size() > 0) {
                            nextLocalOneal.put(e, Integer.valueOf(Convert2DToAdjMatrix.findWay(adjMatrix, local_bomber, local_oneal).get(0)));
                        }
                        ((Oneal) e).setY_goal((nextLocalOneal.get(e) / WIDTH) * Sprite.SCALED_SIZE);
                        ((Oneal) e).setX_goal((nextLocalOneal.get(e) - (nextLocalOneal.get(e) / WIDTH) * WIDTH) * Sprite.SCALED_SIZE);
                    }
                    ((Oneal) e).checkEntity(bomberman);
                    //System.out.printf("oneal");
                }
                if (e instanceof Doll) {
                    if (e.removed) {
                        enemiesNumber--;
                        playMusic("res/soundtracks/mob.mp3");
                        gamePoints += 1000;
                    }
                    if (((Doll) e).isNext()) {
                        //get next local
                        int local_bomber = (bomberman.getY() / Sprite.SCALED_SIZE) * WIDTH + bomberman.getX() / Sprite.SCALED_SIZE;
                        int local_doll = nextLocalDoll.get(e);
                        ArrayList<Integer> way = Convert2DToAdjMatrix.findWay(Convert2DToAdjMatrix.AdjMatrixBackUp_copy, local_bomber, local_doll);
                        if (way.size() > 0) {
                            if (way.size() < 10) {
                                nextLocalDoll.put(e, Integer.valueOf(Convert2DToAdjMatrix.findWay(Convert2DToAdjMatrix.AdjMatrixBackUp_copy, local_bomber, local_doll).get(0)));
                            }
                        }
                        ((Doll) e).setY_goal((nextLocalDoll.get(e) / WIDTH) * Sprite.SCALED_SIZE);
                        ((Doll) e).setX_goal((nextLocalDoll.get(e) - (nextLocalDoll.get(e) / WIDTH) * WIDTH) * Sprite.SCALED_SIZE);
                    }
                    ((Doll) e).checkEntity(bomberman);
                }
                if (e instanceof Flame) {
                    for (Entity s : entities) {
                        ((Flame) e).destroyE(s);
                    }
                    if (e.removed) {
                        entities.remove(e);
                    }
                }
                if (e instanceof Portal) {
                    if (enemiesNumber == 0) {
                        ((Portal) e).opened = true;
                    }
                    if (((Portal) e).passed) {
                        gamePassed = true;
                    }
                }
                if (e.broken) e.destroy();
                if (e.removed) entities.remove(e);
                e.update();
            }

            for (int i = 0; i < stillObjects.size(); i++) {
                Entity e = stillObjects.get(i);
                if (e instanceof Bomb) {
                    if (e.broken) e.removed = true;
                    if (e.removed) {
                        playMusic("res/soundtracks/exploded.mp3");
                        Flame flameMiddle = new Flame(
                                e.getX() / Sprite.SCALED_SIZE,
                                e.getY() / Sprite.SCALED_SIZE,
                                Sprite.bomb_exploded.getFxImage(), ((Bomb) e).getRange());
                        flameMiddle.explode();
                        entities.add(flameMiddle);

                        for (Entity entity : stillObjects) {
                            flameMiddle.check(entity);
                        }
                        for (Entity entity : stillObjects) {
                            flameMiddle.destroyS(entity);
                        }

                        for (int j = 1; j < flameMiddle.r; j++) {
                            Flame flame = new Flame(
                                    e.getX() / Sprite.SCALED_SIZE + j,
                                    e.getY() / Sprite.SCALED_SIZE,
                                    Sprite.explosion_horizontal.getFxImage(),
                                    ((Bomb) e).getRange());
                            flame.explodeHorizon();
                            entities.add(flame);
                        }

                        for (int j = 1; j < flameMiddle.l; j++) {
                            Flame flame = new Flame(
                                    e.getX() / Sprite.SCALED_SIZE - j,
                                    e.getY() / Sprite.SCALED_SIZE,
                                    Sprite.explosion_horizontal.getFxImage(),
                                    ((Bomb) e).getRange());
                            flame.explodeHorizon();
                            entities.add(flame);
                        }

                        for (int j = 1; j < flameMiddle.u; j++) {
                            Flame flame = new Flame(
                                    e.getX() / Sprite.SCALED_SIZE,
                                    e.getY() / Sprite.SCALED_SIZE - j,
                                    Sprite.explosion_vertical.getFxImage(),
                                    ((Bomb) e).getRange());
                            flame.explodeVertical();
                            entities.add(flame);
                        }

                        for (int j = 1; j < flameMiddle.d; j++) {
                            Flame flame = new Flame(
                                    e.getX() / Sprite.SCALED_SIZE,
                                    e.getY() / Sprite.SCALED_SIZE + j,
                                    Sprite.explosion_vertical.getFxImage(),
                                    ((Bomb) e).getRange());
                            flame.explodeVertical();
                            entities.add(flame);
                        }

                        if (flameMiddle.r > 0) {
                            Flame flameRight = new Flame(
                                    e.getX() / Sprite.SCALED_SIZE + flameMiddle.r,
                                    e.getY() / Sprite.SCALED_SIZE,
                                    Sprite.bomb.getFxImage(),
                                    ((Bomb) e).getRange());
                            flameRight.explodeMostRight();
                            entities.add(flameRight);
                        }

                        if (flameMiddle.l > 0) {
                            Flame flameLeft = new Flame(
                                    e.getX() / Sprite.SCALED_SIZE - flameMiddle.l,
                                    e.getY() / Sprite.SCALED_SIZE,
                                    Sprite.explosion_horizontal_left_last.getFxImage(),
                                    ((Bomb) e).getRange());
                            flameLeft.explodeMostLeft();
                            entities.add(flameLeft);
                        }

                        if (flameMiddle.u > 0) {
                            Flame flameUp = new Flame(
                                    e.getX() / Sprite.SCALED_SIZE,
                                    e.getY() / Sprite.SCALED_SIZE - flameMiddle.u,
                                    Sprite.explosion_vertical_top_last.getFxImage(),
                                    ((Bomb) e).getRange());
                            flameUp.explodeMostUp();
                            entities.add(flameUp);
                        }

                        if (flameMiddle.d > 0) {
                            Flame flameDown = new Flame(
                                    e.getX() / Sprite.SCALED_SIZE,
                                    e.getY() / Sprite.SCALED_SIZE + flameMiddle.d,
                                    Sprite.explosion_vertical_down_last.getFxImage(),
                                    ((Bomb) e).getRange());
                            flameDown.explodeMostDown();
                            entities.add(flameDown);
                        }

                        bomberman.bombNumber++;
                        stillObjects.remove(e);
                        int xUnit = e.getX() / Sprite.SCALED_SIZE;
                        int yUnit = e.getY() / Sprite.SCALED_SIZE;
                        //update map for oneal
                        int local = yUnit * WIDTH + xUnit;

                        for (int u = 1; u < WIDTH*HEIGHT - 1; u++) {
                            adjMatrix[local][u] = Convert2DToAdjMatrix.AdjMatrixBackUp[local][u];
                            adjMatrix[u][local] = Convert2DToAdjMatrix.AdjMatrixBackUp[u][local];
                        }
                    }
                }
                if (e instanceof Brick) {
                    int temp_x = e.getX() / Sprite.SCALED_SIZE;
                    int temp_y = e.getY() / Sprite.SCALED_SIZE;
                    if (e.broken) {
                        e.destroy();
                    }
                    if (e.removed) {
                        stillObjects.remove(e);
                        //update map for oneal
                        int local = temp_y * WIDTH + temp_x;

                        for (int u = 1; u < WIDTH*HEIGHT - 1; u++) {
                            adjMatrix[local][u] = Convert2DToAdjMatrix.AdjMatrixBackUp[local][u];
                            adjMatrix[u][local] = Convert2DToAdjMatrix.AdjMatrixBackUp[u][local];
                            Convert2DToAdjMatrix.AdjMatrixBackUp_copy[local][u] = Convert2DToAdjMatrix.AdjMatrixBackUp[local][u];
                            Convert2DToAdjMatrix.AdjMatrixBackUp_copy[u][local] = Convert2DToAdjMatrix.AdjMatrixBackUp[u][local];
                        }
                    }
                }
                e.update();
            }
        }
        if (gamePaused) {
            startTime = System.currentTimeMillis() - (200 - leftTime) * 1000 ;
        }
        if (gamePassed) {
            playBGMusic("res/soundtracks/level_complete.mp3", 1);
            if ((System.currentTimeMillis() - currentTime) / 1000 > 3) {
                currentTime = System.currentTimeMillis();
                gameComplete = true;
                gamePassed = false;
                if (gameLevel == 10) {
                    gameEnd = true;
                } else gameLevel++;
                System.out
                        .println(gameLevel);
            }
        }
        if (gameOver) {
            gamePaused = true;
        }
        if (gameComplete) {
            gamePaused = true;
        }
        if (gameEnd) {
            gamePaused = true;
        }
    }

    public void render() {
        if (!gameOver && !gameComplete && !gamePassed && !gameStart && !gameEnd) {
            gc2.clearRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
            gc2.setFill(Color.BLACK);
            gc2.fillRect(0, canvas.getHeight(), canvas2.getWidth(), canvas2.getHeight());
            gc2.setFill(Color.WHITE);

            if (sldSetting) {
                gc2.setFont(new Font("Arial", 38));
            } else {
                gc2.setFont(new Font("Arial", 30));
            }
            gc2.fillText("⚙", 33, canvas.getHeight() + 47);

            if (sldMute) {
                gc2.setFont(new Font("Arial", 38));
            } else {
                gc2.setFont(new Font("Arial", 30));
            }
            if (BGMp.isMute()) {
                gc2.fillText("🔈", 68, canvas.getHeight() + 46);
            } else {
                gc2.fillText("🔊", 66, canvas.getHeight() + 46);
            }

            if (!gamePaused && !gameSetting) {
                if (sldPause) gc2.setFont(new Font("Arial", 38));
                else gc2.setFont(new Font("Arial", 30));
                gc2.fillText("⏸", 99, canvas.getHeight() + 44);

                gc2.setFont(new Font("Arial", 30));
                gc2.fillText("♥ " + playerLives, canvas2.getWidth() - 66 * 4, canvas.getHeight() + 44);
                gc2.fillText("⏱ " + leftTime, canvas2.getWidth() - 66 * 3, canvas.getHeight() + 44);
                gc2.fillText("Points: " + gamePoints, canvas2.getWidth() / 2 - 66 * 2, canvas.getHeight() + 44);

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                backgrounds.forEach(g -> g.render(gc));
                for (Entity e : entities) {
                    if (!(e instanceof Doll || e instanceof Bomber)) {
                        e.render(gc);
                    }
                }
                stillObjects.forEach(g -> g.render(gc));
                for (Entity e : entities) {
                    if (e instanceof Doll || e instanceof Bomber) {
                        e.render(gc);
                    }
                }
            }
            if (gamePaused) {
                if (sldPause) {
                    gc2.setFont(new Font("Arial", 38));
                } else {
                    gc2.setFont(new Font("Arial", 30));
                }
                gc2.fillText("⏯", 102, canvas.getHeight() + 44);
                gc.setFill(Color.WHITE);
                gc.setFont(new Font("Arial", 30));
                gc.fillText("GAME PAUSED\n-F1 to unpause-", canvas.getWidth() / 2 - 99, canvas.getHeight() / 2);
            }
            if (gameSetting) {
                gc2.setFont(new Font("Arial", 30));
                gc2.fillText("Return to the Main Menu?", canvas2.getWidth() / 2 - 66 * 5, canvas.getHeight() + 44);
                if (sldYes) {
                    gc2.setFont(new Font("Arial", 38));
                } else {
                    gc2.setFont(new Font("Arial", 30));
                }
                gc2.fillText("Yes", canvas2.getWidth() / 2 + 66, canvas.getHeight() + 44);
                if (sldNo) {
                    gc2.setFont(new Font("Arial", 38));
                } else {
                    gc2.setFont(new Font("Arial", 30));
                }
                gc2.fillText("No", canvas2.getWidth() / 2 + 66 * 3, canvas.getHeight() + 44);
            }
        }
        if (gamePassed) {
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Arial", 30));
            gc.fillText("LEVEL COMPLETE\nTime bonus: " + String.format("%03d", leftTime), canvas.getWidth() / 2 - 99, canvas.getHeight() / 2);
        }
        if (gameOver) {
            gc2.setFill(Color.BLACK);
            gc2.fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
            gc2.setFill(Color.WHITE);
            gc2.setFont(new Font("Arial", 30));
            gc2.fillText("GAME OVER", canvas2.getWidth() / 2 - 99, canvas2.getHeight() / 2);
            gc2.fillText("Total Points: " + String.format("%07d", gamePoints), canvas2.getWidth() / 2 - 158, canvas2.getHeight() / 2 + 33);
            if (mediaPlayer != null) {
                mediaPlayer.dispose();
            }
            playBGMusic("res/soundtracks/game_over.mp3", 1);

            if ((System.currentTimeMillis() - currentTime) / 1000 > 6) {
                gameComplete = true;
                gameOver = false;
                gameLevel = 1;
                gamePoints = 0;
                leftTime = 0;
                playerLives = 3;
                currentTime = System.currentTimeMillis();
            }
        }
        if (gameComplete) {
            gc2.setFill(Color.BLACK);
            gc2.fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
            gc2.setFill(Color.WHITE);
            gc2.setFont(new Font("Arial", 30));
            gc2.fillText("STAGE " + gameLevel, canvas2.getWidth() / 2 - 66, canvas2.getHeight() / 2);
            playBGMusic("res/soundtracks/level_start.mp3", 1);

            if ((System.currentTimeMillis() - currentTime) / 1000 > 2) {
                gameComplete = false;
                gamePaused = false;
                gameOver = false;
                gamePassed = false;
                gameStart = false;
                gameSetting = false;
                enemiesNumber = 0;
                framePerSecond = 0;
                startTime = System.currentTimeMillis();
                gamePoints += leftTime * 100;
                createMap(gameLevel);
                playBGMusic("res/soundtracks/stage_theme.mp3", 10);
            }
        }
        if (gameStart) {
            gc2.setFill(Color.BLACK);
            gc2.fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
            gc2.setFill(Color.WHITE);
            gc2.setFont(new Font("Arial", 30));
            gc2.fillText("PROUDLY PRESENT..", canvas2.getWidth() / 2 - 132, canvas2.getHeight() / 2);

            if ((System.currentTimeMillis() - currentTime) / 1000 > 2) {
                gc2.drawImage(new Image("file:res/textures/bg.png", false), 0 ,0);
                if (gameCredit) {
                    gc2.drawImage(new Image("file:res/textures/Credit.png", false), canvas2.getWidth() / 2 - 250, 410);
                }
                else {
                    if (sldPlay) {
                        gc2.drawImage(new Image("file:res/textures/Start-btn.png", false),
                            canvas2.getWidth() / 2 - 163, 410);
                    } else {
                        gc2.drawImage(new Image("file:res/textures/Start-btn-small.png", false),
                            canvas2.getWidth() / 2 - 150, 410);
                    }
                    if (sldContinue) {
                        gc2.drawImage(new Image("file:res/textures/Continue-btn.png", false),
                            canvas2.getWidth() / 2 - 163, 580);
                    } else {
                        gc2.drawImage(new Image("file:res/textures/Continue-btn-small.png", false),
                            canvas2.getWidth() / 2 - 150, 580);
                    }
                }

                if (sldCredit) {
                    gc2.drawImage(new Image("file:res/textures/Credit-btn.png", false),
                        canvas2.getWidth() / 2 - 163, 750);
                } else {
                    gc2.drawImage(new Image("file:res/textures/Credit-btn-small.png", false),
                        canvas2.getWidth() / 2 - 150, 750);
                    playBGMusic("res/soundtracks/invincible.mp3", 10);
                }
            }
        }
        if (gameEnd) {
            gc2.setFill(Color.BLACK);
            gc2.fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
            gc2.setFill(Color.WHITE);
            gc2.setFont(new Font("Arial", 30));
            gc2.fillText("Well done, the game is completed.", canvas2.getWidth() / 2 - 220, canvas2.getHeight() / 2);

            if (mediaPlayer != null) mediaPlayer.dispose();
            playBGMusic("res/soundtracks/end_theme.mp3", 1);

            if ((System.currentTimeMillis() - currentTime) / 1000 > 3) {
                gc2.fillText("I hope you had a great time playing, from Nam!", canvas2.getWidth() / 2 - 300, canvas2.getHeight() / 2 + 33);
            }

            if ((System.currentTimeMillis() - currentTime) / 1000 > 5) {
                gc2.fillText("Total Points: " + String.format("%07d", gamePoints), canvas2.getWidth() / 2 - 158, canvas2.getHeight() / 2 + 99);
            }

            if ((System.currentTimeMillis() - currentTime) / 1000 > 17) {
                gameEnd = false;
                gameStart = true;
                currentTime = System.currentTimeMillis();
            }
        }
    }

    public void playBGMusic(String file, int loop) {
        if (!Objects.equals(currentBGMusic, file)) {
            if (BGMp != null) {
                BGMp.dispose();
            }
            currentBGMusic = file;
            Media media = new Media(new File(file).toURI().toString());
            BGMp = new MediaPlayer(media);
            BGMp.setCycleCount(loop);
            BGMp.setVolume(0.2);
            BGMp.play();
        }
    }

    public static void playMusic(String file) {
        currentMusic = file;
        Media media = new Media(new File(file).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}