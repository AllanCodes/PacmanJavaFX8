package GamePKG;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Pacman extends Sprite {

    private Input input;
    private double speed;
    MySounds mySounds;
    boolean timer;
    Animation animation;
    protected int movement;
    
    public Pacman(Pane layer, Image image, double x, double y, double dx, double dy, double speed, Input input, MySounds ms) {

        super(layer, image, x, y,  dx, dy);

        this.speed = speed;
        this.input = input;
        mySounds = ms;
        
        
    }

    public void setSpeed(double speed) {
    	this.speed = speed;
    }
    
    // based on what arrow keys are pressed set the dx,dy variables to appropriate values
    // so the sprite will move in the proper direction.
    
    public void processInput() {
    	
        // move in vertical direction
        if( input.isMoveUp()) {
        	movement = 1;
        	dx = 0;
            dy = -speed;
         imageView.setImage(new Image("Images/PacmanSprite_24x24_1FrameTOP.png"));
            
        } else if( input.isMoveDown()) {
        	movement = 2;
            dy = speed;
            dx = 0;
            imageView.setImage(new Image("Images/PacmanSprite_24x24_1FrameBOT.png"));
        } 
//        else {
//            dy = 0d;
//        }

        // move in horizontal direction
        
        if( input.isMoveLeft()) {     
        	movement = 3;
            dx = -speed;
            dy = 0;
            imageView.setImage(new Image("Images/PacmanSprite_24x24_1FrameLeft.png"));
          
        } else if( input.isMoveRight()) {
        	movement = 4;
            dx = speed;
            dy = 0;
            imageView.setImage(new Image("Images/PacmanSprite_24x24_1Frame.png"));
           
            
        } 
//        else {
//            dx = 0d;
//        }

        
    }
    
    public boolean imageTimer() {
    	new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                timer = true;
		            }
		        }, 
		        50 
		);
    	
    	return timer;
    }

    @Override
    public void move() { // move the sprite
        super.move();
        
        checkBounds(); // ensure the sprite can't move outside of the screen
    }
    


    // check to see if Pacman is hitting the 4 sides of the window.
    private void checkBounds() {
    	
        // vertical
        if( y < 0 ) {
            y = 0;
        } else if( (y+h) > Settings.SCENE_HEIGHT ) {
            y = Settings.SCENE_HEIGHT-h;
        }

        // horizontal
        if( x < 0) {
            x = 0;
        } else if( (x+w) > Settings.SCENE_WIDTH ) {
            x = Settings.SCENE_WIDTH-w;
        }
    }

} // Player