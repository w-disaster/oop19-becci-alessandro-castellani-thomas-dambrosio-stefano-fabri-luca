package viewmenu;

import java.io.IOException;

public interface SceneChanger {

	/**
	 * Changes the scene in the stage with the given one
	 * @throws IOException 
	 */
	public void change(final String pathToFXML, final String title);

	
}
