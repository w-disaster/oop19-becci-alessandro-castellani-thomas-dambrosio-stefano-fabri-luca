package view.leaderboard;



import application.Main;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import savings.load.LoadLeaderBoard;
import view.sceneChanger.SceneChanger;
import view.sceneChanger.SceneChangerImpl;
import view.sceneChanger.ScenesItem;

public class LeaderboardControl {
	
	private SceneChanger sceneChange = new SceneChangerImpl();
	@FXML private BorderPane borderPane;
	@FXML private Pagination pag;
	@FXML private Label leaderBoardLabel;
	@FXML private Button backMenuButton;
	private HBox hBoxText;
	private Stage stage = Main.STAGE;
	public static int indexPage;
	private LoadLeaderBoard load = new LoadLeaderBoard();
	
	private ChangeListener<Number> changeListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			String styleButton = "-fx-font-size:" + newValue.doubleValue()/40 + ";" + "-fx-font-family:monospace;" + 
					"-fx-background-color:#1A1B28;" + "-fx-text-fill: #FFFFFF;" + "-fx-border-color: white;";
				backMenuButton.setStyle(styleButton);
		}
		
	};
	
	@FXML private void backToMenu() {
		stage.widthProperty().removeListener(changeListener);
		sceneChange.change(ScenesItem.MENU.get(), ScenesItem.MENUTITLE.get());
	}
	
	private void loadTextBox(int pageIndex) {
		try {
			indexPage = pageIndex;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ClassLoader.getSystemResource(ScenesItem.PAGLEADERBOARD.get()));
			hBoxText = loader.load();
		} catch(Exception e) {
			//System.out.println(e.getMessage());
			System.out.println("problems loading fxml");
		}
	}
	
	private Node createNewPage(int pageIndex) {
		loadTextBox(pageIndex);
		return hBoxText;
	}
	
	public void initialize() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				borderPane.getScene().getStylesheets().add(ScenesItem.LEADERBOARDSTYLE.get());
				setResizeEvents();
				pag.setMaxPageIndicatorCount(load.getNumPages());
				pag.setPageCount(load.getNumPages());
				
				pag.setCurrentPageIndex(1);
				pag.setPageFactory(new Callback<Integer, Node>(){
					@Override
					public Node call(Integer param) {
						return createNewPage(param);
					}
				});
				
			}

			
		});
	}
	
	private void setResizeEvents() {
		stage.widthProperty().addListener(changeListener);
	}
	
	
		
}
