package frog.util;

/**
 * Button event listener. Add the ButtonListener to the Button and override the buttonPressed method in order to use.
 * @author Justin Hwang
 *
 */
public interface ButtonListener {

	/**
	 * Is called when a Button is pressed
	 * @param button The Button that is pressed that calls this method
	 */
	public void buttonPressed(Button button);
}
