package application;


import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.scenechanger.SceneChanger;
import view.scenechanger.SceneChangerImpl;
import view.scenechanger.ScenesItem;

/**
 * This class represent the Main class of the JavaFX-based application.
 */
public final class Main extends Application {
	
	//if your screen is 1920x1080, scaling by 1.5 mean that the application will be 1280x720

    /** The Constant SCALING_RATE. */
	public static final double SCALING_RATE = 1.5;

    private static Stage stage;

    private void setStage(final Stage stage) {
        Main.stage = stage;
    }

    @Override
    public void start(final Stage stage) throws Exception {
    	setStage(stage);
    	SceneChanger sceneChange = new SceneChangerImpl();
    	sceneChange.change(ScenesItem.MENU.get(), ScenesItem.MENUTITLE.get());
    	stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/logo/logo.png")));
    }

    public static Stage getStage() {
        return Main.stage;
    }
    /**
     * 
     * @param args unused
     */
    public static void main(final String[] args) {
        launch();
    }

}
