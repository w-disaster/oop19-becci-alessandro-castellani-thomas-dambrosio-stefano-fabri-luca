package viewmenu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneBuilderImpl implements SceneBuilder{

	private Scene scene;
	private final Dimension screenSize;
	private final double width;
	private final double height;
	private final String pathToFXML;
	
	public SceneBuilderImpl(final double scalingRate, final String pathToFXML) throws IOException {
		this.pathToFXML = pathToFXML;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth() / scalingRate;
		height = screenSize.getHeight() / scalingRate;
		
	}
	
	private void build() throws IOException {
		final Parent root = FXMLLoader.load(ClassLoader.getSystemResource(pathToFXML));
		scene = new Scene(root, width, height);
	}
	
	@Override
	public Scene getScene() throws IOException {
		System.out.println("setted resolution to " + width + "x" + height);
		build();
		return scene;
	}

	
	

}
