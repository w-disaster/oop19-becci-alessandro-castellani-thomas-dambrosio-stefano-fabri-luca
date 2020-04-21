package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import viewmenu.SceneBuilder;
import viewmenu.SceneBuilderImpl;

/**
 * This class represent the Main class of the JavaFX-based application.
 */
public final class Main extends Application {

    private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 400;

    @Override
    public void start(final Stage stage) throws Exception {
    	SceneBuilder sceneBuild = new SceneBuilderImpl(SCENE_WIDTH, SCENE_HEIGHT, "layouts/main.fxml");
        // Stage configuration
        stage.setTitle("Quoridor2D - Menu");
        stage.setScene(sceneBuild.getScene());
        stage.setMinWidth(SCENE_WIDTH);
        stage.setMinHeight(SCENE_HEIGHT);
        stage.show();
    }

    /**
     * 
     * @param args unused
     */
    public static void main(final String[] args) {
        launch();
    }

}
