package viewmenu;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SceneChangerImpl implements SceneChanger{
	
	private Stage stage;
	private SceneBuilder sceneBuild;
	
	public SceneChangerImpl() {
		stage = Main.STAGE;
	}
	
	private void setScene(final String title) {
		try{
			stage.setTitle(title);
			stage.setScene(sceneBuild.getScene());
		    stage.sizeToScene();
		    stage.show();
		    stage.setMinWidth(stage.getWidth());
		    stage.setMinHeight(stage.getHeight());
		} catch (Exception e) {
			System.out.println("problems with stage settings");
		}
		
	}
	
	@Override
	public void change(final String pathToFXML, final String title) {
		try{
			sceneBuild = new SceneBuilderImpl(pathToFXML);
		} catch (Exception e) {
			System.out.println("");
		}
		setScene(title);
	}
	
	
}
