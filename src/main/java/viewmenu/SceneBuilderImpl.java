package viewmenu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;

public class SceneBuilderImpl implements SceneBuilder{

	private Scene scene;
	private final Dimension screenSize;
	private final double width;
	private final double height;
	private final String pathToFXML;
	private final Pair<Double, Double> maxResizable;
	
	/**
	 * This construct a scene and creates a maximum Size over the scene can't be resized without losing layouts in a significant way
	 * @param maxResizeScaling
	 * @param pathToFXML
	 * @throws IOException
	 */
	public SceneBuilderImpl(final double maxResizeScaling, final String pathToFXML) throws IOException {
		this.pathToFXML = pathToFXML;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth() / Main.SCALING_RATE;
		height = screenSize.getHeight() / Main.SCALING_RATE;
		maxResizable = new Pair<>(screenSize.getWidth() / (Main.SCALING_RATE-maxResizeScaling), screenSize.getHeight() / (Main.SCALING_RATE-maxResizeScaling));
		
	}
	/**
	 * This constructor doesn't creates a maximum size, it just creates the Scene
	 * @param pathToFXML
	 */
	public SceneBuilderImpl(final String pathToFXML) {
		this.pathToFXML = pathToFXML;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth() / Main.SCALING_RATE;
		height = screenSize.getHeight() / Main.SCALING_RATE;
		maxResizable = null;
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
	
	public Pair<Double, Double> getMaxResizable(){
		System.out.println(maxResizable.getKey());
		System.out.println(maxResizable.getValue());
		return maxResizable;
	}

	
	

}
