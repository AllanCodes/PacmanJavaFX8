package GamePKG;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Sprite {

    protected Image image; // the image of the sprite, created outside this class and passed in.
    private ImageView imageView; // where the sprite image is stored.
    private Pane layer;  // the pane where the sprite is drawn on, it's created outside this class and passed in.
    protected double x;  // x location of sprite
    protected double y;  // y location of sprite
    protected double dx; // how much to move sprite in the x axis
    protected double dy; // how much to move sprite in the y axis
    protected double w;  // width of the sprite image
    protected double h;  // height of the sprite image

    public Sprite(Pane layer, Image image, double x, double y, double dx, double dy) {

        this.layer = layer;
        this.image = image;
        this.x = x;
        this.y = y;

        this.dx = dx;
        this.dy = dy;

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);

        this.w = image.getWidth(); // imageView.getBoundsInParent().getWidth();
        this.h = image.getHeight(); // imageView.getBoundsInParent().getHeight();

        addToLayer(); // add the sprite image to the Pane called "layer"
    }

    // move the sprite by dx,dy
    public void move() {
        x += dx;
        y += dy;
    }
    
    // update the sprite's position to the new x,y
    public void updateUI() {
        imageView.relocate(x, y);
    }

    // check to see of this sprite collides with "otherSprite"
    public boolean collidesWith( Sprite otherSprite) {
        return ( otherSprite.x + otherSprite.w >= x && otherSprite.y + otherSprite.h >= y && otherSprite.x <= x + w && otherSprite.y <= y + h);
    }
    
    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
    }
    
} // SpriteBase