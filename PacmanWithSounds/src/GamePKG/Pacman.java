package GamePKG;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Pacman extends Sprite {

    private Input input;
    private double speed;
    MySounds mySounds;

    public Pacman(Pane layer, Image image, double x, double y, double dx, double dy, double speed, Input input, MySounds ms) {

        super(layer, image, x, y,  dx, dy);

        this.speed = speed;
        this.input = input;
        mySounds = ms;
    }

    // based on what arrow keys are pressed set the dx,dy variables to appropriate values
    // so the sprite will move in the proper direction.
    public void processInput() {

        // move in vertical direction
        if( input.isMoveUp()) {
            dy = -speed;
        } else if( input.isMoveDown()) {
            dy = speed;
        } else {
            dy = 0d;
        }

        // move in horizontal direction
        if( input.isMoveLeft()) {
            dx = -speed;
        } else if( input.isMoveRight()) {
            dx = speed;
        } else {
            dx = 0d;
        }

    }

    @Override
    public void move() { // move the sprite
    	if ((dx == 0) && (dy == 0))
    		mySounds.stopClip(2);
    	else
    		mySounds.playClip(2);
    	
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