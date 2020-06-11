package viewmenu;

import java.io.IOException;

import javafx.scene.Scene;

public interface SceneBuilder {
	
	/**
	 * Builds the scene
	 * 
	 * @return the constructed scene
	 * @throws IOException 
	 */
	public Scene getScene() throws IOException;
	
	
}
