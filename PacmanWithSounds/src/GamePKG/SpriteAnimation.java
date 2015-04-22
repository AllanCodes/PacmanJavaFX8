package GamePKG;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
	
	  private ImageView imageView;
	  private int count = 0, count1 = 0, count2 = 0, count3 = 0;
	  Pacman pac;
	  public SpriteAnimation(ImageView imageView, Duration duration, Pacman pac)
	  {
		  this.imageView = imageView;
		  this.pac = pac;
		  setCycleDuration(duration);
		  setInterpolator(Interpolator.LINEAR);
	  }

	@Override
	protected void interpolate(double arg0) {
		// TODO Auto-generated method stub
		if (pac.movement == 4) {
			if (count <= 20) {
				imageView.setImage(new Image("Images/pacRIGHT1.png"));
			}
			else if (count >= 21) {
				imageView.setImage(new Image("Images/pacRIGHT2.png"));
				if (count >= 50) {
				count = 0;
				}
			}
			count++;
		}
		else if (pac.movement == 3) {
			if (count1 <= 20) {
				imageView.setImage(new Image("Images/pacLEFT1.png"));
			}
			else if (count1 >= 21) {
				imageView.setImage(new Image("Images/pacLEFT2.png"));
				if (count1 >= 50) {
				count1 = 0;
				}
			}
			count1++;
		}
		else if (pac.movement == 2) {
			if (count2 <= 20) {
				imageView.setImage(new Image("Images/pacDOWN1.png"));
			}
			else if (count2 >= 21) {
				imageView.setImage(new Image("Images/pacDOWN2.png"));
				if (count2 >= 50) {
				count2 = 0;
				}
			}
			count2++;
		}
		else if (pac.movement == 1) {
			if (count3 <= 20) {
				imageView.setImage(new Image("Images/pacTOP1.png"));
			}
			else if (count3 >= 21) {
				imageView.setImage(new Image("Images/pacTOP2.png"));
				if (count3 >= 50) {
				count3 = 0;
				}
			}
			count3++;
		}
		
		
	
		
	
	}

}
