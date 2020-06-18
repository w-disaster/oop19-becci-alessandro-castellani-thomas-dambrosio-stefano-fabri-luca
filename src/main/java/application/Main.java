package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
     	SceneBuilder sceneBuild = new SceneBuilderImpl("layouts/menu/MainMenu.fxml");
     	Image logo = new Image(this.getClass().getResourceAsStream("/logo/logo.png"));

        // Stage configuration
        stage.setTitle("Quoridor2D - Menu");
        stage.getIcons().add(logo);
        stage.setScene(sceneBuild.getScene());
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
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
