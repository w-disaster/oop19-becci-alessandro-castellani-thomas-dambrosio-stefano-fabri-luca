package viewmenu;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneBuilderImpl implements SceneBuilder{

	private final Scene scene;
	
	public SceneBuilderImpl(final int SCENE_WIDTH, final int SCENE_HEIGHT, final String pathToFXML) throws IOException {
		final Parent root = FXMLLoader.load(ClassLoader.getSystemResource(pathToFXML));
		this.scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
	}
	
	@Override
	public Scene getScene() {
		return this.scene;
	}

}
