package view.game;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import controller.GameController;
import controller.PowerUpGameController;
import controller.PowerUpGameControllerImpl;
import controller.StandardGameController;
import controller.StandardGameControllerImpl;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.powerups.PowerUp;
import view.menu.MenuController;
import view.menu.MenuController.GameStatus;
import view.scenechanger.ScenesItem;

/**
 * The Logic implementation for controlling the view.
 *
 * @author Stefano
 */
public class ViewLogicImpl implements ViewLogic{

	private GameController controller;

	private final ViewController view;

	private final Map<Coordinate, BorderPane> gridMap;

	private Optional<Integer> selectedBarrier;

	private String player1;
	private String player2;

	public ViewLogicImpl(final ViewController viewController) {
		this.view = viewController;
		if (MenuController.getGameStatus().equals(GameStatus.NORMAL)
				|| MenuController.getGameStatus().equals(GameStatus.LOADNORMAL)) {
			this.controller = new StandardGameControllerImpl(this);
		} else if (MenuController.getGameStatus().equals(GameStatus.POWERUP)
				|| MenuController.getGameStatus().equals(GameStatus.LOADPOWERUP)) {
			this.controller = new PowerUpGameControllerImpl(this);
		}
    	this.gridMap = new HashMap<Coordinate, BorderPane>();
    	this.selectedBarrier = Optional.empty();
	}

	public final void startGame() {
	    switch (MenuController.getGameStatus()) {
		case LOADNORMAL:
			this.controller.loadGame();
			break;
		case LOADPOWERUP:
			this.controller.loadGame();
			break;
		case NORMAL:
			((StandardGameController) this.controller).newStandardGame(player1, player2);
			break;
		case POWERUP:
			((PowerUpGameController) this.controller).newPowerUpGame(player1, player2);
			break;
		default:
			break;
	    }
	}

	@Override
    public final void setPlayer(final Optional<Pair<String, String>> players) {
		// If you leave it empty it automatically set default nicknames
    	if (players.get().getKey().equals("")) {
    		this.player1 = "Player 1";
    	} else {
    		this.player1 = players.get().getKey();
    	}

    	if (players.get().getValue().equals("")) {
    		this.player2 = "Player 2";
    	} else {
    		this.player2 = players.get().getValue();
    	}

    	// Nicknames can't be the same
    	if (player1.equals(player2)) {
    		final Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("ERROR");
    		alert.setContentText("You can't use the same name!");
    		alert.showAndWait();
    		this.view.returnToMainMenu();
    	}
    	this.view.setNicknames(player1, player2);
	}

	@Override
    public final BorderPane addPaneLogic(final Coordinate position) {
		final BorderPane pane = new BorderPane();
        pane.setOnMouseClicked(e -> {
        	this.setUpClickHandler(position);
        });
        this.gridMap.put(position, pane);
        return pane;
	}	

	@Override
    public final void setUpClickHandler(final Coordinate position) {
        if (this.selectedBarrier.isEmpty()) {
        	this.controller.invokeMove(position);
        } else {
        	if (this.selectedBarrier.get().equals(0)) {
        		this.controller.invokePlace(position, Orientation.VERTICAL);
        	} else {
        		this.controller.invokePlace(position, Orientation.HORIZONTAL);
        	}
        	this.selectedBarrier = Optional.empty();
        }
	}

	public final void setupGrid(final Coordinate player1pos, final Coordinate player2pos, final int barriersP1, final int barriersP2) {
		this.clearGrid();
		this.view.setPlayerInPane(this.gridMap.get(player1pos), this.player1);
		this.view.setPlayerInPane(this.gridMap.get(player2pos), this.player2);
		this.view.updateBarriersNumber(player1, barriersP1);
		this.view.updateBarriersNumber(player2, barriersP2);
	}

	public final void setupGrid(final Coordinate player1pos, final Coordinate player2pos, final int barriersP1, final int barriersP2, final List<Barrier> barrierList) {
		this.setupGrid(player1pos, player2pos, barriersP1, barriersP2);
		this.drawBarriersOnLoad(barrierList);
	}

	/**
	 * Clears the grid.
	 */
	private void clearGrid() {
		this.gridMap.entrySet().forEach(e -> e.getValue().getChildren().remove(0, e.getValue().getChildren().size()));
	}

    public final void move(final Coordinate position, final String player) {
    	this.view.setPlayerInPane(this.gridMap.get(position), player);
    	this.drawTextLogic("move");
    }

    @Override
    public final void changeSelectedLabel(final String player) {
    	this.view.changeSelectedLabel(player);
    }

    public final void setSelectedBarrier(final String type) {
    	if (type.equals("vertical")) {
    		this.selectedBarrier = Optional.of(0);
    		this.drawTextLogic("verticalBarrier");
    	} else if (type.equals("horizontal")) {
    		this.selectedBarrier = Optional.of(1);
    		this.drawTextLogic("horizontalBarrier");
    	}
    }

    public final void drawBarrier(final Barrier barrier) {
    	final BorderPane selected = this.gridMap.get(barrier.getCoordinate());
    	// barrier styling
    	final Rectangle verticalBarrier = new Rectangle();
    	verticalBarrier.getStyleClass().add("Barrier");
    	verticalBarrier.setFill(Color.ORANGE);
    	final Rectangle horizontalBarrier = new Rectangle();
    	horizontalBarrier.getStyleClass().add("Barrier");
    	horizontalBarrier.setFill(Color.ORANGE);

    	if (barrier.getOrientation().equals(Orientation.HORIZONTAL)) {
    		selected.setBottom(horizontalBarrier);
    		BorderPane.setAlignment(horizontalBarrier, Pos.CENTER);
    		this.view.getHorizontalBarrierList().add(horizontalBarrier);
    	} else if (barrier.getOrientation().equals(Orientation.VERTICAL)) {
    		selected.setRight(verticalBarrier);
    		BorderPane.setAlignment(verticalBarrier, Pos.CENTER);
    		this.view.getVerticalBarrierList().add(verticalBarrier);
    	}
    	this.view.setCorrectSize();
    }

    /**
     * Draw barriers on load.
     *
     * @param barrierList the barrier list to draw
     */
    private void drawBarriersOnLoad(final List<Barrier> barrierList) {
    	for (Barrier barrier : barrierList) {
    		this.drawBarrier(barrier);
    	}
    }

    @Override
    public final void updateBarriersNumber(final String player, final int barriersNumber) {
    	this.view.updateBarriersNumber(player, barriersNumber);
    }

    @Override
    public final void drawPowerUps(final List<PowerUp> powerUpsAsList) {
		for (final PowerUp p : powerUpsAsList) {
			switch (p.getType()) {
			case PLUS_ONE_MOVE:
				final ImageView doubleMoveIcon = new ImageView(new Image(this.getClass()
						.getResourceAsStream(ScenesItem.DOUBLEPUP.get())));
				this.view.drawPowerUp(this.gridMap.get(p.getCoordinate()), doubleMoveIcon);
				break;
			case PLUS_ONE_BARRIER:
				final ImageView plusOneBarrierIcon = new ImageView(new Image(this.getClass()
						.getResourceAsStream(ScenesItem.BARRIERPUP.get())));
				this.view.drawPowerUp(this.gridMap.get(p.getCoordinate()), plusOneBarrierIcon);
				break;
			default:
				break;
			}
		}
    }

    public final void deletePowerUp(final PowerUp p) {
    	final List<Node> toRemove = this.gridMap.get(p.getCoordinate()).getChildren().stream()
				.filter(e -> e.getClass().equals(ImageView.class))
				.collect(Collectors.toList()); 
		this.gridMap.get(p.getCoordinate()).getChildren().removeAll(toRemove);
    }

    public final void endRound(final String winner) {
    	this.view.endRound(winner);
    	this.controller.nextRound();
    }

    public final void endGame(final String winner) {
    	this.view.endGame(winner);
    }

    @Override
    public final void saveGame() {
    	this.controller.saveGame();
    }

	@Override
    public final void drawTextLogic(final String textToDisplay) {
    	final String start = "- Welcome to Quoridor! \n";
    	final String moveTutorial = "- Click on a cell to move the player\n"
    			+ "- Click on a barrier to place it\n"
    			+ "- You can jump over the other player when he is in front of you\n";
    	final String barrierTutorial = "To place a barrier, click on a cell: \n"
    			+ "- The vertical barrier will be placed right and in the cell below\n"
    			+ "- The horizontal barrier will be placed below and in the cell to the right\n";
    	final String verticalBarrierSelected = "Selected barrier: Vertical\n\n";
    	final String horizontalBarrierSelected = "Selected barrier: Horizontal\n\n";
    	final String badMove = "Bad move! Still your turn";
    	switch (textToDisplay) {
    	case "start" :
    		this.view.drawText(start);
    		this.view.appendText(moveTutorial);
    		break;
    	case "move" :
    		this.view.drawText(moveTutorial);
    		break;
    		case "verticalBarrier" :
    			this.view.drawText(verticalBarrierSelected);
    			this.view.appendText(barrierTutorial);
    			break;
    		case "horizontalBarrier" :
    			this.view.drawText(horizontalBarrierSelected);
    			this.view.appendText(barrierTutorial);
    			break;
    		case "badMove":
    			this.view.drawText(badMove);
    		default :
    			break;
    	}
	}
}
