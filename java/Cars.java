import java.applet.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;

public class Cars
{
	// Two images, for car moving in each direction.

	private Image movingToRightImage_ = null;
	private Image movingToLeftImage_  = null;

	// Image used for double buffering to avoid flicker.

	private Image screenImage_ = null;

	// Okay to start drawing;

	private boolean imagesReady_ = false;

	private URL url_;

	private int rightEdge_ = 0;
	private int leftEdge_  = 0;

	// Size of image.

	private int imageWidth_  = 0;

	// How much to move car by on each timer tick.

	final private int step_ = 3;

	// True if moving to right, false if moving to left.

	private boolean movingToRight_ = true;

	// Current position of car.

	private int position_ = 0;

	private CarAnimationApplet applet_ = null;

	private Graphics screenGraphics_ = null;


//-------------------------------------------------------------------------Cars

	public Cars(CarAnimationApplet creator)
	{
		applet_ = creator;

		// Create image the size of the applet to draw cars into.

		screenImage_ = creator.createImage(applet_.size().width,
													  applet_.size().height);

		// Must inquire sgraphics for image here and not when we need it.

		if (screenImage_ != null)
			screenGraphics_ = screenImage_.getGraphics();

		// Get size of area from applet.

		rightEdge_ = applet_.size().width;

		// Get images for car travelling in left and right directions.

		movingToLeftImage_  = applet_.getImage(applet_.getCodeBase(), "tipo_left.gif");
		movingToRightImage_ = applet_.getImage(applet_.getCodeBase(), "tipo_right.gif");
	}


//----------------------------------------------------------------------MoveCar

	public void MoveCar()
	{
		// Called when a timer ticks to move car to left or right.
		// Set up such that car dissappears off left and right edges
		// rather than just swapping direction.

		if (movingToRight_ == true) {

			// Moving to right.

			if (position_ >= (rightEdge_))
				movingToRight_ = false;
			else
				position_ += step_;
		}
		else {

			// Moving to left.

			if (position_ <= leftEdge_ - imageWidth_)
				movingToRight_ = true;
			else
				position_ -= step_;
		}
	}


//-------------------------------------------------------------------TestImages

	public boolean TestImages()
	{
	   // Test to see if images have been completely loaded and hence
		// have a height and width.

  	   if (!imagesReady_) {
		   if (movingToLeftImage_ != null) {

		      MediaTracker mt;
			
   			mt = new MediaTracker(applet_);

	   		mt.addImage(movingToLeftImage_, 1);

		   	try {mt.waitForID(1);
   			}

      		catch (InterruptedException e) {
	            System.out.println("Can't wait any longer to image to load");
   		   }

	   		imageWidth_ = movingToLeftImage_.getWidth(applet_);

		   	if (imageWidth_ != -1) {

					// Set start position off left of display.

					position_ = -imageWidth_;				
   				imagesReady_ = true;
	   		}
	    	}
		}

		return imagesReady_;
	}


//-----------------------------------------------------------------------Redraw

	public void Redraw()
	{
		// Cause applet to be redrawn.

		if (applet_ != null)
			applet_.repaint();
	}


//------------------------------------------------------------------------paint

	public void Draw(Graphics g)
	{
		// Draw appropriate picture of car at correct location in screen
		// image and then update display with this image. Doing this avoids
		// flickering.

		if (applet_ != null && imagesReady_ && screenImage_ != null) {
			if (movingToRight_) {
				if (movingToRightImage_ != null) {
					Clear();
					screenImage_.getGraphics().drawImage(movingToRightImage_, position_, 0, applet_);
					g.drawImage(screenImage_, 0, 0, applet_);
				}
			}
			else {
				if (movingToLeftImage_ != null) {
					Clear();
					screenImage_.getGraphics().drawImage(movingToLeftImage_, position_, 0, applet_);
					g.drawImage(screenImage_, 0, 0, applet_);
				}
			}
		}
	}


//------------------------------------------------------------------------Clear

	public void Clear()
	{
		// Clear screen image ready for painting car into.

		if (applet_ != null) {
			
			Color background = applet_.getBackground();

			if (background != null) {

				Dimension size = applet_.size();

				if (screenGraphics_ != null && size != null) {
					screenGraphics_.setColor(background);
					screenGraphics_.fillRect(0, 0, size.width, size.height);
				}
			}
		}
	}


//--------------------------------------------------------------------MouseDown

	public void MouseDown(int x, int y)
	{
		// User clicked over applet. Check if over car and if so reverse
		// it's direction.

		// Car takes up whole height of applet area so just check x coordinate.

		if (x > position_ && x < position_ + imageWidth_) {
			movingToRight_ = !movingToRight_;
		}
	}
}