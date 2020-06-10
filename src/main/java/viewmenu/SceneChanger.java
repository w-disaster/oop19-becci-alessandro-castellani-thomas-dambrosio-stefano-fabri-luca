package viewmenu;

import java.io.IOException;

public interface SceneChanger {

	/**
	 * Changes the scene in the stage with the given one
	 * @throws IOException 
	 */
	public void change(final String pathToFXML, final String title);

	/**
	 * Changes the scene in the stage with the given one , setting a maximum resize for the scene created
	 * @param maxResizeScaling
	 * @param pathToFXML
	 * @param title
	 * @throws IOException 
	 */
	public void change(double maxResizeScaling, String pathToFXML, String title);
	
}
