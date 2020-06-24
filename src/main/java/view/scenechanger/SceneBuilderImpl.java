package view.scenechanger;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Pair;
// TODO: Auto-generated Javadoc
/**
 * This class creates a scene.
 * @author Alessandro Becci
 *
 */
public class SceneBuilderImpl implements SceneBuilder{

	/** The scene. */
	private Scene scene;
	
	/** The screen size. */
	private final Dimension screenSize;
	
	/** The width. */
	private final double width;
	
	/** The height. */
	private final double height;
	
	/** The path to FXML. */
	private final String pathToFXML;
	
	/**
	 * This constructor initialize the dimension of the Scene with the SCALING_RATE constanct.
	 *
	 * @param pathToFXML the path to FXML
	 */
	public SceneBuilderImpl(final String pathToFXML) {
		this.pathToFXML = pathToFXML;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth() / Main.SCALING_RATE;
		height = screenSize.getHeight() / Main.SCALING_RATE;
	}

	/**
	 * Builds the Scene.
	 */
	private void build(){
		try{
			final Parent root = FXMLLoader.load(ClassLoader.getSystemResource(pathToFXML));
			scene = new Scene(root, width, height);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("An error occured");
			alert.setContentText("Error in loading fxml.!");
			alert.showAndWait();
			System.exit(1);
		}
		
	}
	
	/**
	 * Gets the scene.
	 *
	 * @return the scene
	 */
	@Override
	public Scene getScene(){
		System.out.println("setted resolution to " + width + "x" + height);
		build();
		return scene;
	}
	

	
	

}
