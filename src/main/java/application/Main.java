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
	
	//if your screen is 1920x1080, scaling by 1.5 mean that the application will be 1280x720
    public final static double SCALING_RATE = 1.5;
    
    
    public static Stage STAGE;

    @Override
    public void start(final Stage stage) throws Exception {
     	SceneBuilder sceneBuild = new SceneBuilderImpl(0.3,"layouts/menu/MainMenu.fxml");

        // Stage configuration

        stage.setTitle("Quoridor2D - Menu");
        stage.setScene(sceneBuild.getScene());
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setMaxHeight(sceneBuild.getMaxResizable().getValue());
        stage.setMaxWidth(sceneBuild.getMaxResizable().getKey());
        Main.STAGE = stage;
        
    }
    
    
    
    /**
     * 
     * @param args unused
     */
    public static void main(final String[] args) {
        launch();
    }

}
