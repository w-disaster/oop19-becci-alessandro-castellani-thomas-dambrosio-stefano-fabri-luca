package viewmenu;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SceneChangerImpl implements SceneChanger{
	
	private Stage stage;
	
	public SceneChangerImpl(final ActionEvent event) {
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	}
	
	@Override
	public void change(final String pathToFXML, final String title) throws IOException {
		SceneBuilder sceneBuild = new SceneBuilderImpl(Main.SCALING_RATE, pathToFXML);
		stage.setTitle("Quoridor2D - Game");
		stage.setScene(sceneBuild.getScene());
	    stage.sizeToScene();
	    stage.show();
	    stage.setMinWidth(stage.getWidth());
	    stage.setMinHeight(stage.getHeight());
	}

	
}
