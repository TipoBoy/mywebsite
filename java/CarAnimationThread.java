import java.awt.Graphics;

public class CarAnimationThread extends Thread
{
	// Time in 1000's of second to wait between each animation.

	final private int delay_ = 35;

	private Cars cars_ = null;

//-----------------------------------------------------------CarAnimationThread

	public CarAnimationThread(CarAnimationApplet creator)
	{
		// Create the cars object passing in the applet it needs to
		// know about when drawing.

		cars_ = new Cars(creator);
	}


//--------------------------------------------------------------------------run

	public void run()
	{
		// Called when this thread is called with start().

		while (true) {

			if (cars_ != null && cars_.TestImages()) {

				// Move car to new position and then redraw it.

				cars_.MoveCar();

				cars_.Redraw();
			}

			try {

				// Wait for specified length of time.

				Thread.sleep(delay_);
			}

			catch(InterruptedException e) {
				System.out.println("Car Animation Thread Execution stopped");
			}
		}
	}


//-------------------------------------------------------------------------Draw

	public void Draw(Graphics g)
	{
	   // Pass drawing onto car object.

		if (cars_ != null)
			cars_.Draw(g);
	}


//--------------------------------------------------------------------MouseDown

	public void MouseDown(int x, int y)
	{
		// Mouse pressed so pass down to car object.

		if (cars_ != null)
			cars_.MouseDown(x, y);
	}
}