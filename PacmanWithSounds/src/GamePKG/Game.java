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

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game extends Application {

    Random rnd = new Random();

    Pane playfieldLayer;
    Pane scoreLayer;

    Image playerImage;
    Image enemyImage, enemyImage2, enemyImage3, enemyImage4;
    Image BG_Maze;
    
    MySounds mySounds;

    Pacman player;
    List<Ghost> enemies = new ArrayList<>();
    
    Ghost ghost1,ghost2,ghost3,ghost4;
    
    ImageView enemyImageIV;
    
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
        drawRectangles();
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
        enemyImage  = new Image("Images/Pinky_PinkGhost_16x16_1Frame.png");
        enemyImage2 = new Image("Images/Blinky_RedGhost_16x16_1Frame.png");
        enemyImage3 = new Image("Images/Clyde_OrangeGhost_16x16_1Frame.png");
        enemyImage4 = new Image("Images/Inky_CyanGhost_16x16_1Frame.png");
        
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
       
        ghost1 = new Ghost( playfieldLayer, enemyImage, x, 228, 0, 0);
        ghost2 = new Ghost(playfieldLayer, enemyImage2, x + 32, 228, 0, 0);
        ghost3 = new Ghost(playfieldLayer, enemyImage3, x-32, 228,0,0);
        ghost4 = new Ghost(playfieldLayer,enemyImage4,x,320,0,0);
      
        enemies.add(ghost1);
        enemies.add(ghost2);
        enemies.add(ghost3);
        enemies.add(ghost4);
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
    
    
    
    private void drawRectangles() { 
    	ArrayList<Rectangle> recArray = new ArrayList<Rectangle>();
    	recArray.add(new Rectangle(39,87,51,35));
    	recArray.add(new Rectangle(118,87,68,35));
    	for(Rectangle i : recArray) {
    		i.setFill(Color.TRANSPARENT);
    	}
    	
    	playfieldLayer.getChildren().addAll(recArray);
    }

    public static void main(String[] args) {
        launch(args);
    }
    
} // Game