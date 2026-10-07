import java.applet.Applet;
import java.awt.Graphics;
import java.awt.*;

public class CarAnimationApplet extends Applet
{
	// Data member to hold main animation thread.

	private CarAnimationThread thread_ = null;


//-------------------------------------------------------------------------init

	public void init()
	{
		// Retrive background color.

		Color background = getColorParameter("background");

		if (background != null) {
			setBackground(background);
		}
	}


//-------------------------------------------------------------------------init

	private Color getColorParameter(String name)
	{
		// Convert parameter from string into base 16 number.

		String value = getParameter(name);

		try {
			return new Color(Integer.parseInt(value, 16));
		}
		catch (Exception e) {
			return null;
		}
	}


//------------------------------------------------------------------------start

	public void start()
	{
		// When start called create our thread
		// and call start on it. This will in turn
		// call run() in Thread object.

		System.out.println("Car Animation Applet Execution started");

		if (thread_ == null) {
			thread_ = new CarAnimationThread(this);
			if (thread_ != null)
				thread_.start();
		}
	}


//-------------------------------------------------------------------------stop

	public void stop()
	{
		// Kill thread.

		System.out.println("Car Animation Applet Execution stopped");
		thread_.stop();
		thread_ = null;
	}


//------------------------------------------------------------------------paint

	public void paint(Graphics g)
	{
	   // Inform thread incase it needs to draw anything.
		
		if (thread_ != null)
			thread_.Draw(g);
	}


//------------------------------------------------------------------------paint

	public void update(Graphics g)
	{
	   // Override default so that it does not clear background.
		
		paint(g);
	}


//--------------------------------------------------------------------mouseDown

	public boolean mouseDown(Event e, int x, int y)
	{
		// Mouse pressed so pass down to thread.

		if (thread_ != null)
			thread_.MouseDown(x, y);
		
		// return that we have handled event.

		return true;
	}
}