package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import viewmenu.ScenesItem;
import viewmenu.SceneBuilder;
import viewmenu.SceneBuilderImpl;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

/**
 * This class represent the Main class of the JavaFX-based application.
 */
public final class Main extends Application {
	
	//if your screen is 1920x1080, scaling by 1.5 mean that the application will be 1280x720
    public final static double SCALING_RATE = 1.5;
    
    private SceneChanger sceneChange;
    public static Stage STAGE;

    @Override
    public void start(final Stage stage) throws Exception {
    	Main.STAGE = stage;
    	sceneChange = new SceneChangerImpl();
    	sceneChange.change(ScenesItem.MENU.get(), ScenesItem.MENUTITLE.get());
    	stage.getIcons().add((new Image(this.getClass().getResourceAsStream("/logo/logo.png"))));
    }
    
    
    
    /**
     * 
     * @param args unused
     */
    public static void main(final String[] args) {
        launch();
    }

}
