package view.scenechanger;



import application.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
/**
 * This class changes the Scene.
 *
 */
public class SceneChangerImpl implements SceneChanger {
	
	private final Stage stage;
	private SceneBuilder sceneBuild;
	
	public SceneChangerImpl() {
		stage = Main.getStage();
	}
	
	/**
	 * Sets the Scene on the Stage
	 * @param title
	 */
	private void setScene(final String title) {
		try {
			stage.setTitle(title);
			stage.setScene(sceneBuild.getScene());
		    stage.sizeToScene();
		    stage.show();
		    stage.setMinWidth(stage.getWidth());
		    stage.setMinHeight(stage.getHeight());
		    stage.setResizable(true);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("An error occured");
			alert.setContentText("Error in Stage setting!");
			alert.showAndWait();
			System.exit(1);
		}
	}
	
	/**
	 * This is the public method, it builds the scene with @SceneBuilder class and sets it with the private method setScene
	 * @param pathToFXML
	 * @param title
	 */
	@Override
	public void change(final String pathToFXML, final String title) {
		try {
			sceneBuild = new SceneBuilderImpl(pathToFXML);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("An error occured");
			alert.setContentText("Error in building scene!");
			alert.showAndWait();
			System.exit(1);
		}
		setScene(title);
	}
	
	
}
