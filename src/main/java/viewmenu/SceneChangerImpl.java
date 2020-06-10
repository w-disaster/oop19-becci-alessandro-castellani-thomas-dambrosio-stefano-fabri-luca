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
	
	private void setScene(final String title) throws IOException {
		stage.setTitle(title);
		stage.setScene(sceneBuild.getScene());
	    stage.sizeToScene();
	    stage.show();
	    stage.setMinWidth(stage.getWidth());
	    stage.setMinHeight(stage.getHeight());
	}
	
	private void setMaximumSize() {
		stage.setMaxHeight(sceneBuild.getMaxResizable().getKey());
		stage.setMaxWidth(sceneBuild.getMaxResizable().getValue());
	}
	
	//Resetting to default if the scene doesn't need
	private void unsetMaximumSize() {
		System.out.println("RESET DEFAULT");
		stage.setMaxHeight(Double.MAX_VALUE);
		stage.setMaxWidth(Double.MIN_VALUE);
	}
	
	@Override
	public void change(final String pathToFXML, final String title) throws IOException {
		sceneBuild = new SceneBuilderImpl(pathToFXML);
		setScene(title);
		unsetMaximumSize();
	}
	

	@Override
	public void change(final double maxResizeScaling, final String pathToFXML, final String title) throws IOException {
		sceneBuild = new SceneBuilderImpl(maxResizeScaling, pathToFXML);
		setScene(title);
		setMaximumSize();
	}

	

	
}
