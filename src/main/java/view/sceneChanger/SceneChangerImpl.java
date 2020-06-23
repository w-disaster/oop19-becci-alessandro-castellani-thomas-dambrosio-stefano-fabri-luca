package view.sceneChanger;



import application.Main;
import javafx.stage.Stage;
/**
 * This class changes the Scene.
 * @author Alessandro Becci
 *
 */
public class SceneChangerImpl implements SceneChanger{
	
	private Stage stage;
	private SceneBuilder sceneBuild;
	
	public SceneChangerImpl() {
		stage = Main.STAGE;
	}
	
	/**
	 * Sets the Scene on the Stage
	 * @param title
	 */
	private void setScene(final String title) {
		try{
			stage.setTitle(title);
			stage.setScene(sceneBuild.getScene());
		    stage.sizeToScene();
		    stage.show();
		    stage.setMinWidth(stage.getWidth());
		    stage.setMinHeight(stage.getHeight());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("problems with stage settings");
		}
		
	}
	
	/**
	 * This is the public method, it builds the scene with @SceneBuilder class and sets it with the private method setScene
	 * @param pathToFXML
	 * @param title
	 */
	@Override
	public void change(final String pathToFXML, final String title) {
		try{
			sceneBuild = new SceneBuilderImpl(pathToFXML);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("");
		}
		setScene(title);
	}
	
	
}
