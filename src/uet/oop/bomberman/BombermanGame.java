package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    AnimationTimer gameLoop;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> enemies = new ArrayList<>();
    public static char [][] mapMatrix = new char[WIDTH][HEIGHT];
    static Bomber bomberman;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case RIGHT: {
                    bomberman.moveRight();
                    break;
                }
                case LEFT: {
                    bomberman.moveLeft();
                    break;
                }
                case UP: {
                    bomberman.moveUp();
                    break;
                }
                case DOWN: {
                    bomberman.moveDown();
                    break;
                }
            }
        });

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                checkCollision();
            }
        };
        timer.start();

        createMap();

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                entities.forEach(entity -> {
                    if (entity instanceof Balloon) {
                        entity.update();
                    }
                });
            }
        };

        playMusic("C:\\Users\\admin\\OneDrive\\Desktop\\tet.game\\res\\soundtrack\\stage_theme.mp3");
    }

    public void createMap() {
        createMapFromFile();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity grass = new Grass(i, j, Sprite.grass.getFxImage());
                stillObjects.add(grass);

                switch (mapMatrix[j][i]) {
                    case '#': {
                        Entity object = new Wall(i, j, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case '*': {
                        Entity object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case '1': {
                        Entity object = new Balloon(i, j, Sprite.balloom_left1.getFxImage());
                        enemies.add(object);
                        break;
                    }
                    default: {
                        Entity object = new Grass(i, j, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                }
            }
        }
    }

    private void createMapFromFile() {
        String filePath = "C:\\Users\\admin\\OneDrive\\Desktop\\tet.game\\res\\levels\\Level1.txt";
        try {
            File file = new File (filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            int numberLevel = 0;
            int numberRow = 0;
            int numberColumn = 0;
            if ((line = bufferedReader.readLine()) != null) {
                String[] token = line.split("\\s");
                numberLevel = Integer.parseInt(token[0]);
                numberRow = Integer.parseInt(token[1]);
                numberColumn = Integer.parseInt(token[2]);
            }
            if (numberLevel < 1 || (numberRow < 1 && numberColumn < 1)) return;

            mapMatrix = new char[numberRow][numberColumn];
            int countRow = -1;
            while ((line = bufferedReader.readLine()) != null) {
                countRow = countRow + 1;
                for (int i = 0; i < line.length(); i++) {
                    mapMatrix[countRow][i] = line.charAt(i);
                }
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        entities.forEach(Entity::update);
        enemies.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
    }

    public void checkCollision() {
        int x = bomberman.getX();
        int y = bomberman.getY();
        enemies.forEach(enemy -> {
            if (Math.abs(x - enemy.getX()) < 15 && Math.abs(y - enemy.getY()) < 30) {
                System.out.println("x: " + x + " y: " + y);
                System.out.println("Co va cham x: " + enemy.getX() + " y: " + enemy.getY());
                mediaPlayer.stop();
                playMusic("");
            }
        });
    }
    MediaPlayer mediaPlayer;
    public void playMusic(String file) {
        Media media = new Media(new File(file).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(10);
        mediaPlayer.setStartTime(Duration.ZERO);
        mediaPlayer.setStopTime(Duration.minutes(10));
        mediaPlayer.play();
    }
}
