package viewmenu;

import java.io.IOException;

import javafx.scene.Scene;
import javafx.util.Pair;

public interface SceneBuilder {
	
	/**
	 * Builds the scene
	 * 
	 * @return the constructed scene
	 * @throws IOException 
	 */
	public Scene getScene() throws IOException;
	
	/**
	 * Gets the maximum size that the screen can resize
	 * @return pair of coordinates
	 */
	public Pair<Double, Double> getMaxResizable();
	
}
