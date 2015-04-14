package GamePKG;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ghost extends Sprite {

    public Ghost(Pane layer, Image image, double x, double y, double dx, double dy ) {
        super(layer, image, x, y, dx, dy);
    }

}