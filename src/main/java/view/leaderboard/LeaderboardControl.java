package view.leaderboard;



import java.io.File;
import java.util.Optional;

import application.Main;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import savings.load.LoadLeaderBoard;
import savings.load.LoadLeaderBoardImpl;
import savings.load.LoadUtilities;
import savings.save.PathSavings;
import view.scenechanger.SceneChanger;
import view.scenechanger.SceneChangerImpl;
import view.scenechanger.ScenesItem;

/**
 * The Class LeaderboardControl.
 */
public class LeaderboardControl {
	
	/** The SceneChanger. */
	private SceneChanger sceneChange = new SceneChangerImpl();
	
	/** The root pane. */
	@FXML private BorderPane borderPane;
	
	/** The pagination. */
	@FXML private Pagination pag;
	
	/** The back menu button. */
	@FXML private Button backMenuButton;
	
	/** The clean button. */
	@FXML private Button cleanButton;
	
	/** The hBox where the text stays. */
	private HBox hBoxText;
	
	/** The stage. */
	private final Stage stage = Main.getStage();
	
	/** The index page. */
	private static int indexPage;
	
	private final int buttonResize = 40;
	
	/** The loadLeaderBoard. */
	private final LoadLeaderBoard load = new LoadLeaderBoardImpl();
	
	/** The ChangeListener. */
	private final ChangeListener<Number> changeListener = new ChangeListener<Number>() {

		@Override
		public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
			String styleButton = "-fx-font-size:" + newValue.doubleValue() / buttonResize + ";" + "-fx-font-family:monospace;" 
			        + "-fx-background-color:#1A1B28;" + "-fx-text-fill: #FFFFFF;" + "-fx-border-color: white;";
				backMenuButton.setStyle(styleButton);
				cleanButton.setStyle(styleButton);
		}
	};
	
	/**
	 * Removes the listener.
	 */
	private void removeListener() {
		stage.widthProperty().removeListener(changeListener);
	}
	
	/**
	 * Cleans leaderboard.
	 */
	@FXML private void cleanLeaderboard() {
		 boolean delete = false;
		 Alert alert = new Alert(AlertType.CONFIRMATION);
	     alert.setTitle("You are deleting the LeaderBoard!");
	     alert.setHeaderText("Leaderboard elimination");
	     alert.setContentText("Are you sure ?");
	     alert.getDialogPane().setStyle("-fx-background-color: #2B2D42; -fx-fill: #FFFFFF;");
	     // Set the icon 
	     ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(this.getClass().getResourceAsStream("/logo/logo.png")));
	     ButtonType buttonTypeOne = new ButtonType("Yes");
	     ButtonType buttonTypeTwo = new ButtonType("No");
	     ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
	     alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
		 Optional<ButtonType> result = alert.showAndWait();
		 if (result.get() == buttonTypeOne) {
			 delete = true;
		 } else if (result.get() == buttonTypeTwo) {
			 delete = false;
		 } else {
			 delete = false;
		 }
		if (delete) {
			File leaderBoardFile = new File(PathSavings.LEADERBOARD.getPath());
			try {
			    leaderBoardFile.delete();
			} catch (Exception e) {
			    LoadUtilities.setUpAlertException();
			    System.exit(1);
			}
			removeListener();
			sceneChange.change(ScenesItem.MENU.get(), ScenesItem.MENUTITLE.get());
		}
	}
	
	/**
	 * Back to menu.
	 */
	@FXML private void backToMenu() {
		removeListener();
		sceneChange.change(ScenesItem.MENU.get(), ScenesItem.MENUTITLE.get());
	}
	
	/**
	 * Load the box with Text.
	 *
	 * @param pageIndex the page index
	 */
	private void loadTextBox(final int pageIndex) {
		try {
			indexPage = pageIndex;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ClassLoader.getSystemResource(ScenesItem.PAGLEADERBOARD.get()));
			hBoxText = loader.load();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("An error occured");
			alert.setContentText("Error in loading fxml.!");
			alert.showAndWait();
			System.exit(1);
		}
	}
	
	/**
	 * Creates the new page of the pagination.
	 *
	 * @param pageIndex the page index
	 * @return the node
	 */
	private Node createNewPage(final int pageIndex) {
		loadTextBox(pageIndex);
		return hBoxText;
	}
	
	/**
	 * @FXML initialize.
	 */
	public void initialize() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				borderPane.getScene().getStylesheets().add(ScenesItem.LEADERBOARDSTYLE.get());
				setResizeEvents();
				pag.setMaxPageIndicatorCount(load.getNumPages());
				pag.setPageCount(load.getNumPages());
				pag.setCurrentPageIndex(1);
				pag.setPageFactory(new Callback<Integer, Node>() {
					@Override
					public Node call(final Integer param) {
						return createNewPage(param);
					}
				});
			}

		});
	}
	
	/**
	 * Sets the resize events.
	 */
	private void setResizeEvents() {
		stage.widthProperty().addListener(changeListener);
	}

    public static int getIndexPage() {
        return indexPage;
    }
}
