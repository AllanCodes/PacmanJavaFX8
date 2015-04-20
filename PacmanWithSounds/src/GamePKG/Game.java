
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
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    Image enemyImage, enemyImage2, enemyImage3, enemyImage4;
    Image BG_Maze;
    
    MySounds mySounds;

    ArrayList<Rectangle> r;
    Pacman player;
    List<Ghost> enemies = new ArrayList<>();

    ArrayList<Circle> dots;
    int score = 0;
    Label scoreLabel;
    Label gameOver;
    boolean collision = false;
    Group root = new Group();
    Scene scene;
    
    @Override
    public void start(Stage primaryStage) {


        // create layers
        playfieldLayer = new Pane();
        gameOver = new Label();
        
        scoreLabel = new Label("Score: " + Integer.toString(score));
        scoreLabel.setLayoutX(350);
        scoreLabel.setLayoutY(25);
        scoreLabel.setScaleX(2);
        scoreLabel.setScaleY(2);
        scoreLabel.setTextFill(Color.YELLOW);
        
        
        
        
        
        // note in the Settings class, SCENE_WIDTH and SCENE_HEIGHT are set to the size of the image above 448x576.
       BG_Maze = new Image ("images/Pac-ManMaze_448x576.png");  // read the image in
       ImageView imageView = new ImageView (BG_Maze); // create an ImageView object to hold this image.
       //playfieldLayer.getChildren().add(scoreLabel);
		playfieldLayer.getChildren().add(imageView); // put the image inside the pane.
       root.getChildren().add(playfieldLayer);
       root.getChildren().add(scoreLabel);
       root.getChildren().add(gameOver);
        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setWidth(464); // need 8 pixels for each side (16 total). Padding horizontally for window borders.
        primaryStage.setHeight(614); // need 38 pixels for padding vertically for top/bottom window borders.
        primaryStage.show();

        mySounds = new MySounds();
        mySounds.playClip(1);
        
        loadGame(); // create sprites
      //  createScoreLayer();
        drawRectangles();
        drawDots();
        
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
                dotCollide();

                // update Pacman and ghost sprites in scene
                player.updateUI();
                enemies.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed and remove it if needed

                // update score, health, etc
                drawScore();
                checkGameOver();
                
                
            }
        };
        gameLoop.start();
    }
    
    public void checkGameOver() {
    	
        gameOver = new Label();
        gameOver.setLayoutX(190);
        gameOver.setLayoutY(150);
        gameOver.setScaleX(2);
        gameOver.setScaleY(2);
        gameOver.setTextFill(Color.RED);
        
    	if (dots.size() == 0) {
    		gameOver.setText("GAME OVER");
    		player.freeze();
    		root.getChildren().add(gameOver);
    	}
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

    private void boxCollide() { 
    	collision = false;
    	for ( Rectangle block : r) { 
    		if(player.collidesWith(block)){
    			collision = true;
    			player.freeze();
    		}
    	}
    }
   
    
    Circle temp; //stores the temporary dot that has to be removed
    
    private void dotCollide() {
    	collision = false;
    	for (Circle dot : dots) {
    		if(player.collidesWith(dot)) {
    			collision = true;
    			temp = dot;
    			removeDot(dot);
    			score += 100;
    			mySounds.playClip(2);
    			
    		}
    	}
    	if(collision == true) {
    		dots.remove(temp);
    	}
    }
    
    public void removeDot(Circle dot) {
    	playfieldLayer.getChildren().remove(dot);
    }
			
    private void loadGame() {
        playerImage = new Image("Images/PacmanSprite_24x24_1Frame.png");
        enemyImage  = new Image("Images/Pinky_PinkGhost_16x16_1Frame.png" );
        enemyImage2 = new Image("Images/Clyde_OrangeGhost_16x16_1Frame.png");
        enemyImage3 = new Image("Images/Inky_CyanGhost_16x16_1Frame.png");
        enemyImage4 = new Image("Images/Blinky_RedGhost_16x16_1Frame.png");
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
        Image image3 = enemyImage2;
        Image image4 = enemyImage3;
        Image image5 = enemyImage4;
        Ghost ghost1 = new Ghost( playfieldLayer, image2, x, 280, 0, 0);
        Ghost ghost2 = new Ghost(playfieldLayer, image3, x + 20, 280, 0, 0);
        Ghost ghost3 = new Ghost(playfieldLayer,image4, x - 20, 280, 0,0);
        Ghost ghost4 = new Ghost(playfieldLayer, image5, x, 260, 0,0);
        
        enemies.add(ghost1);
        enemies.add(ghost2);
        enemies.add(ghost3);
        enemies.add(ghost4);
    }

    
    public void drawScore() { 
    	scoreLabel.setText("Score: " + Integer.toString(score));
    }
    
    
    Ghost ghostTemp;
    
    private void checkCollisions() {

        collision = false;
       
        
        for( Ghost enemy: enemies) {
            if( player.collidesWith(enemy)) {
                collision = true;
                enemy.removeFromLayer();
                ghostTemp = enemy;
                mySounds.playClip(3);
            }
        }
        if (collision == true) {
        	enemies.remove(ghostTemp);
        }
        
     }
    
    public void drawDots() {
    	dots = new ArrayList<Circle>();
    	//left vertical
    	//dots.add(new Circle(20,75,4,Color.YELLOW)); big dot
    	dots.add(new Circle(20,95,4,Color.YELLOW));
    	dots.add(new Circle(20,115,4,Color.YELLOW));
    	dots.add(new Circle(20,135,4,Color.YELLOW));
    	dots.add(new Circle(20,155,4,Color.YELLOW));
    	dots.add(new Circle(20,180,4,Color.YELLOW));
    	dots.add(new Circle(20,375,4,Color.YELLOW));
    	dots.add(new Circle(20,395,4,Color.YELLOW));
    	dots.add(new Circle(20,415,4,Color.YELLOW));
    	dots.add(new Circle(20,470,4,Color.YELLOW));
    	dots.add(new Circle(20,490,4,Color.YELLOW));
    	dots.add(new Circle(20,510,4,Color.YELLOW));
    	//dots.add(new Circle(20,530,4,Color.YELLOW));
    	
    	//top horizontal
    	dots.add(new Circle(40,75,4,Color.YELLOW));
    	dots.add(new Circle(60,75,4,Color.YELLOW));
    	dots.add(new Circle(80,75,4,Color.YELLOW));
    	dots.add(new Circle(100,75,4,Color.YELLOW));
    	dots.add(new Circle(120,75,4,Color.YELLOW));
    	dots.add(new Circle(140,75,4,Color.YELLOW));
    	dots.add(new Circle(160,75,4,Color.YELLOW));
    	dots.add(new Circle(180,75,4,Color.YELLOW));
    	dots.add(new Circle(200,75,4,Color.YELLOW));
    	dots.add(new Circle(250,75,4,Color.YELLOW));
    	dots.add(new Circle(270,75,4,Color.YELLOW));
    	dots.add(new Circle(290,75,4,Color.YELLOW));
    	dots.add(new Circle(310,75,4,Color.YELLOW));
    	dots.add(new Circle(330,75,4,Color.YELLOW));
    	dots.add(new Circle(350,75,4,Color.YELLOW));
    	dots.add(new Circle(370,75,4,Color.YELLOW));
    	dots.add(new Circle(390,75,4,Color.YELLOW));
    	dots.add(new Circle(410,75,4,Color.YELLOW));
    //	dots.add(new Circle(430,75,4,Color.YELLOW)); This will be big dot
    	dots.add(new Circle(40,75,4,Color.YELLOW));
    	
    	//right vertical
    	
    	//big dot here
    	dots.add(new Circle(425,95,4,Color.YELLOW));
    	dots.add(new Circle(425,95,4,Color.YELLOW));
    	dots.add(new Circle(425,115,4,Color.YELLOW));
    	dots.add(new Circle(425,135,4,Color.YELLOW));
    	dots.add(new Circle(425,155,4,Color.YELLOW));
    	dots.add(new Circle(425,175,4,Color.YELLOW));
    	dots.add(new Circle(425,375,4,Color.YELLOW));
    	dots.add(new Circle(425,395,4,Color.YELLOW));
    	//dots.add(new Circle(425,415,4,Color.YELLOW)); big dot
    	dots.add(new Circle(425,470,4,Color.YELLOW));
    	dots.add(new Circle(425,490,4,Color.YELLOW));
    	dots.add(new Circle(425,510,4,Color.YELLOW));
    	
    	dots.add(new Circle(40,135,4,Color.YELLOW));
    	dots.add(new Circle(60,135,4,Color.YELLOW));
    	dots.add(new Circle(80,135,4,Color.YELLOW));
    	dots.add(new Circle(100,135,4,Color.YELLOW));
    	dots.add(new Circle(120,135,4,Color.YELLOW));
    	dots.add(new Circle(140,135,4,Color.YELLOW));
    	dots.add(new Circle(160,135,4,Color.YELLOW));
    	dots.add(new Circle(180,135,4,Color.YELLOW));
    	dots.add(new Circle(200,135,4,Color.YELLOW));
    	dots.add(new Circle(220,135,4,Color.YELLOW));
    	dots.add(new Circle(240,135,4,Color.YELLOW));
    	dots.add(new Circle(260,135,4,Color.YELLOW));
    	dots.add(new Circle(280,135,4,Color.YELLOW));
    	dots.add(new Circle(300,135,4,Color.YELLOW));
    	dots.add(new Circle(320,135,4,Color.YELLOW));
    	dots.add(new Circle(340,135,4,Color.YELLOW));
    	dots.add(new Circle(360,135,4,Color.YELLOW));
    	dots.add(new Circle(380,135,4,Color.YELLOW));
    	dots.add(new Circle(400,135,4,Color.YELLOW));
    	
    	dots.add(new Circle(40,180,4,Color.YELLOW));
    	dots.add(new Circle(60,180,4,Color.YELLOW));
    	dots.add(new Circle(80,180,4,Color.YELLOW));
    	dots.add(new Circle(100,180,4,Color.YELLOW));
    	dots.add(new Circle(100,160,4,Color.YELLOW));
    	dots.add(new Circle(155,185,4,Color.YELLOW));
    	dots.add(new Circle(175,185,4,Color.YELLOW));
    	dots.add(new Circle(195,185,4,Color.YELLOW));
    	dots.add(new Circle(155,160,4,Color.YELLOW));
    	dots.add(new Circle(295,160,4,Color.YELLOW));
    	dots.add(new Circle(255,185,4,Color.YELLOW));
    	dots.add(new Circle(275,185,4,Color.YELLOW));
    	dots.add(new Circle(295,185,4,Color.YELLOW));
    	dots.add(new Circle(340,160,4,Color.YELLOW));
    	
    	dots.add(new Circle(100,95,4,Color.YELLOW));
    	dots.add(new Circle(100,115,4,Color.YELLOW));
    	dots.add(new Circle(100,200,4,Color.YELLOW));
    	dots.add(new Circle(100,220,4,Color.YELLOW));
    	dots.add(new Circle(100,240,4,Color.YELLOW));
    	dots.add(new Circle(100,260,4,Color.YELLOW));
    	dots.add(new Circle(100,280,4,Color.YELLOW));
    	dots.add(new Circle(100,300,4,Color.YELLOW));
    	dots.add(new Circle(100,320,4,Color.YELLOW));
    	dots.add(new Circle(100,340,4,Color.YELLOW));
    	dots.add(new Circle(100,360,4,Color.YELLOW));
    	dots.add(new Circle(100,380,4,Color.YELLOW));
    	dots.add(new Circle(100,400,4,Color.YELLOW));
    	dots.add(new Circle(100,420,4,Color.YELLOW));
    	dots.add(new Circle(100,440,4,Color.YELLOW));
    	dots.add(new Circle(100,460,4,Color.YELLOW));
    	
    	dots.add(new Circle(80,470,4,Color.YELLOW));
    	dots.add(new Circle(60,470,4,Color.YELLOW));
    	dots.add(new Circle(40,470,4,Color.YELLOW));
    	
    	dots.add(new Circle(40,525,4,Color.YELLOW));
    	dots.add(new Circle(60,525,4,Color.YELLOW));
    	dots.add(new Circle(80,525,4,Color.YELLOW));
    	dots.add(new Circle(100,525,4,Color.YELLOW));
    	dots.add(new Circle(120,525,4,Color.YELLOW));
    	dots.add(new Circle(140,525,4,Color.YELLOW));
    	dots.add(new Circle(160,525,4,Color.YELLOW));
    	dots.add(new Circle(180,525,4,Color.YELLOW));
    	dots.add(new Circle(200,525,4,Color.YELLOW));
    	dots.add(new Circle(220,525,4,Color.YELLOW));
    	dots.add(new Circle(240,525,4,Color.YELLOW));
    	dots.add(new Circle(260,525,4,Color.YELLOW));
    	dots.add(new Circle(280,525,4,Color.YELLOW));
    	dots.add(new Circle(300,525,4,Color.YELLOW));
    	dots.add(new Circle(320,525,4,Color.YELLOW));
    	dots.add(new Circle(340,525,4,Color.YELLOW));
    	dots.add(new Circle(360,525,4,Color.YELLOW));
    	dots.add(new Circle(380,525,4,Color.YELLOW));
    	dots.add(new Circle(400,525,4,Color.YELLOW));
    	
    	dots.add(new Circle(120,425,4,Color.YELLOW));
    	dots.add(new Circle(140,425,4,Color.YELLOW));
    	dots.add(new Circle(160,425,4,Color.YELLOW));
    	dots.add(new Circle(180,425,4,Color.YELLOW));
    	dots.add(new Circle(200,425,4,Color.YELLOW));
    	dots.add(new Circle(240,425,4,Color.YELLOW));
    	dots.add(new Circle(260,425,4,Color.YELLOW));
    	dots.add(new Circle(280,425,4,Color.YELLOW));
    	dots.add(new Circle(300,425,4,Color.YELLOW));
    	dots.add(new Circle(320,425,4,Color.YELLOW));
    	dots.add(new Circle(340,425,4,Color.YELLOW));

    	dots.add(new Circle(390,425,4,Color.YELLOW));
    	dots.add(new Circle(410,425,4,Color.YELLOW));
    	dots.add(new Circle(425,415,6,Color.YELLOW));  //big dot
    	dots.add(new Circle(390,445,4,Color.YELLOW));
    	
    	dots.add(new Circle(340,95,4,Color.YELLOW));
    	dots.add(new Circle(340,115,4,Color.YELLOW));
    	dots.add(new Circle(340,185,4,Color.YELLOW));
    	dots.add(new Circle(340,205,4,Color.YELLOW));
    	dots.add(new Circle(340,225,4,Color.YELLOW));
    	dots.add(new Circle(340,245,4,Color.YELLOW));
    	dots.add(new Circle(340,265,4,Color.YELLOW));
    	dots.add(new Circle(340,285,4,Color.YELLOW));
    	dots.add(new Circle(340,305,4,Color.YELLOW));
    	dots.add(new Circle(340,325,4,Color.YELLOW));
    	dots.add(new Circle(340,345,4,Color.YELLOW));
    	dots.add(new Circle(340,365,4,Color.YELLOW));
    	dots.add(new Circle(340,385,4,Color.YELLOW));
    	dots.add(new Circle(340,405,4,Color.YELLOW));
    	dots.add(new Circle(340,425,4,Color.YELLOW));
    	dots.add(new Circle(340,445,4,Color.YELLOW));
    	dots.add(new Circle(340,465,4,Color.YELLOW));
    	
    	dots.add(new Circle(360,470,4,Color.YELLOW));
    	dots.add(new Circle(380,470,4,Color.YELLOW));
    	dots.add(new Circle(400,470,4,Color.YELLOW));

    	
    	
    	
    	
    	
    	
    	
    	playfieldLayer.getChildren().addAll(dots);
    	
    }
    

    public static void main(String[] args) {
        launch(args);
    }
    
} // Game