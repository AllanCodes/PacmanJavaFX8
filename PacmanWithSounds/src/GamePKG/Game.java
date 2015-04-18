
/**
 * Allan Eivazian
 * Basic Pacman Game
 * CIS 016
 * Assignment 13
 * Collision, sound, single ghost collision 
 *
 */
package GamePKG;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

    ArrayList<Rectangle> r;
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
        drawRectangles();

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
                boxCollide();

                // update Pacman and ghost sprites in scene
                player.updateUI();
                enemies.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed and remove it if needed

                // update score, health, etc
                //updateScore();
            }
        };
        gameLoop.start();
    }
    
    public void drawRectangles() {
    	r = new ArrayList<Rectangle>();
		r.add(new Rectangle(5, 43, 440,12));
		r.add(new Rectangle(216, 56, 15,63));
		r.add(new Rectangle(1, 46, 7,162));
		r.add(new Rectangle(439, 46, 8,157));
		r.add(new Rectangle(43,88,43,29));
		r.add(new Rectangle(362, 88, 43,29));
		r.add(new Rectangle(123, 88, 58,30));
		r.add(new Rectangle(266, 88, 58,30));
		r.add(new Rectangle(42, 151, 42,17));
		r.add(new Rectangle(362, 151, 42,17));
		r.add(new Rectangle(171, 152, 106,14));
		r.add(new Rectangle(171, 343, 106,14));
		r.add(new Rectangle(171, 440, 106,14));
		r.add(new Rectangle(122, 151, 15,108));
		r.add(new Rectangle(312, 151, 15,108));
		r.add(new Rectangle(139, 200, 41,16));
		r.add(new Rectangle(266, 200, 41,16));
		r.add(new Rectangle(1, 198, 82,61));
		r.add(new Rectangle(1, 299, 82,61));
		r.add(new Rectangle(364, 201, 82,59));
		r.add(new Rectangle(364, 299, 82,59));
		r.add(new Rectangle(120, 298, 15,59));
		r.add(new Rectangle(312, 298, 15,59));
		r.add(new Rectangle(124, 392, 55,15));
		r.add(new Rectangle(266, 392, 55,15));
		r.add(new Rectangle(440, 357, 7,188));
		r.add(new Rectangle(1, 357, 7,188));
		r.add(new Rectangle(217, 169, 15,43));
		r.add(new Rectangle(217, 362, 15,43));
		r.add(new Rectangle(217, 454, 15,43));
		r.add(new Rectangle(9, 440, 30,16));
		r.add(new Rectangle(410, 440, 30,16));
		r.add(new Rectangle(43, 488, 137,16));
		r.add(new Rectangle(267, 488, 137,16));
		r.add(new Rectangle(122, 442, 14,45));
		r.add(new Rectangle(312, 442, 14,45));
		r.add(new Rectangle(1, 535, 442,8));
		r.add(new Rectangle(44, 393, 39,16));
		r.add(new Rectangle(362, 393, 39,16));
		r.add(new Rectangle(361, 393, 15,61));
		r.add(new Rectangle(73, 393, 15,61));
		r.add(new Rectangle(170, 247, 37,11));
		r.add(new Rectangle(241, 247, 37,11));
		r.add(new Rectangle(170, 301, 108,9));
		r.add(new Rectangle(168, 248, 9,61));
		r.add(new Rectangle(271, 248, 9,61));
		for(Rectangle block : r){
			block.setFill(Color.TRANSPARENT);;
			block.setStroke(Color.TRANSPARENT);
		}
		playfieldLayer.getChildren().addAll(r);
    }

    private void boxCollide() { // Added this for collision detection with rectangles
    	collision = false;
    	for ( Rectangle block : r) { //r is the name of my rectangle array
    		if(player.collidesWith(block)){
    			collision = true;
    			player.freeze();
    			mySounds.stopClip(2);
    		}
    	}
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
                enemy.removeFromLayer();
                enemies.remove(enemy);
                mySounds.playClip(3);
            }
        }
        
     }
    

    public static void main(String[] args) {
        launch(args);
    }
    
} // Game