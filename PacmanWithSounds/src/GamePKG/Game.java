// Basic Pacman Game setup with maze, Pacman and a ghost.
// You can expand the Sprite classes to add more features.
// Zareh Gorjian
// 4/4/2015
//
// Based on code from StackOverflow from RolandC

package GamePKG;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;


public class Game extends Application {

    Random rnd = new Random();

    Pane playfieldLayer;
    Pane scoreLayer;

    Image playerImage;
    Image enemyImage;
    Image BG_Maze;
    
    MySounds mySounds;

    Pacman player;
    List<Ghost> enemies = new ArrayList<>();

    Text collisionText = new Text();
    boolean collision = false;

    Scene scene;
    
    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();

        // create layers
        playfieldLayer = new Pane();
        scoreLayer = new Pane();
        
        // note in the Settings class, SCENE_WIDTH and SCENE_HEIGHT are set to the size of the image above 448x576.
        BG_Maze = new Image ("images/Pac-ManMaze_448x576.png");  // read the image in
        ImageView imageView = new ImageView (BG_Maze); // create an ImageView object to hold this image.
		playfieldLayer.getChildren().add(imageView); // put the image inside the pane.
        
        root.getChildren().add(playfieldLayer);
        root.getChildren().add(scoreLayer);

        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.setWidth(464); // need 8 pixels for each side (16 total). Padding horizontally for window borders.
        primaryStage.setHeight(614); // need 38 pixels for padding vertically for top/bottom window borders.
        primaryStage.show();

        mySounds = new MySounds();
        mySounds.playClip(1);
        
        loadGame(); // create sprites
        createScoreLayer();

        // create the main game loop.
        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) { // this is the main loop of the game. This method is repeatedly called.

                // player input
                player.processInput();

                // move Pacman and ghost sprites.
                player.move();
                enemies.forEach(sprite -> sprite.move());

                // check for collisions
                checkCollisions();

                // update Pacman and ghost sprites in scene
                player.updateUI();
                enemies.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed and remove it if needed

                // update score, health, etc
                updateScore();
            }
        };
        gameLoop.start();
    }

    private void loadGame() {
        playerImage = new Image("Images/PacmanSprite_24x24_1Frame.png");
        enemyImage  = new Image("Images/Pinky_PinkGhost_16x16_1Frame.png" );
        
        // player input
        Input input = new Input(scene);

        // register input listeners
        input.addListeners(); // TODO: remove listeners on game over

        Image image = playerImage;

        // center Pacman horizontally, position at 412 vertically
        double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = 412;

        // create Pacman sprite
        player = new Pacman(playfieldLayer, image, x, y, 0, 0, 1, input,mySounds);
        
        // create ghost sprite
        Image image2 = enemyImage;
        Ghost ghost1 = new Ghost( playfieldLayer, image2, x, 228, 0, 0);
        enemies.add(ghost1);
    }

    private void createScoreLayer() {
    	
        collisionText.setFont( Font.font( null, FontWeight.BOLD, 64));
        collisionText.setStroke(Color.BLACK);
        collisionText.setFill(Color.RED);

        scoreLayer.getChildren().add( collisionText);

        // TODO: quick-hack to ensure the text is centered; usually you don't have that; instead you have a health bar on top
        collisionText.setText("Collision");
        double x = (Settings.SCENE_WIDTH - collisionText.getBoundsInLocal().getWidth()) / 2;
        double y = (Settings.SCENE_HEIGHT - collisionText.getBoundsInLocal().getHeight()) / 2;
        collisionText.relocate(x, y);
        collisionText.setText("");

        collisionText.setBoundsType(TextBoundsType.VISUAL);
    }
    
    private void checkCollisions() {

        collision = false;
        
        for( Ghost enemy: enemies) {
            if( player.collidesWith(enemy)) {
                collision = true;
            }
        }
     }

    private void updateScore() {
        if( collision) {
            collisionText.setText("Collision");
        } else {
            collisionText.setText("");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
} // Game