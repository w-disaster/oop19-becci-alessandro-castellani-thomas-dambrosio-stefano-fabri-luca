package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import viewmenu.SceneBuilder;
import viewmenu.SceneBuilderImpl;

/**
 * This class represent the Main class of the JavaFX-based application.
 */
public final class Main extends Application {
	
	//if your screen is 1920x1080, scaling by 2.0 mean that the application will be 1280x720
    private static final double SCALING_RATE = 2.0;

    @Override
    public void start(final Stage stage) throws Exception {
     	SceneBuilder sceneBuild = new SceneBuilderImpl(SCALING_RATE, "layouts/menu/MainMenu.fxml");

        // Stage configuration

        stage.setTitle("Quoridor2D - Menu");
        stage.setScene(sceneBuild.getScene());
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    /**
     * 
     * @param args unused
     */
    public static void main(final String[] args) {
        launch();
    }

}
