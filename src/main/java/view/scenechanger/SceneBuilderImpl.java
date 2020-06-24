package view.scenechanger;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;
/**
 * This class creates a scene.
 * @author Alessandro Becci
 *
 */
public class SceneBuilderImpl implements SceneBuilder{

	private Scene scene;
	private final Dimension screenSize;
	private final double width;
	private final double height;
	private final String pathToFXML;
	
	/**
	 * This constructor doesn't creates a maximum size, it just creates the Scene
	 * @param pathToFXML
	 */
	public SceneBuilderImpl(final String pathToFXML) {
		this.pathToFXML = pathToFXML;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth() / Main.SCALING_RATE;
		height = screenSize.getHeight() / Main.SCALING_RATE;
	}

	private void build(){
		try{
			final Parent root = FXMLLoader.load(ClassLoader.getSystemResource(pathToFXML));
			scene = new Scene(root, width, height);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("problems loading fxml");
		}
		
	}
	
	@Override
	public Scene getScene(){
		//System.out.println("setted resolution to " + width + "x" + height);
		build();
		return scene;
	}
	

	
	

}
