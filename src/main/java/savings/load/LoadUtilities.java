package savings.load;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Model;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.barriers.BarrierImpl;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.barriers.Barrier.Piece;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.BarriersGraph;
import model.roundenvironment.graph.Graph;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.PlayerImpl;
import model.roundenvironment.powerups.PowerUp;
import model.roundenvironment.powerups.PowerUpImpl;
import savings.save.PathSavings;
import model.roundenvironment.powerups.PowerUp.Type;

/**
 * The Class LoadUtilities.
 * @author Alessandro Becci
 */
public class LoadUtilities {

	/** The path file of the players. */
	private final String pathFilePlayers= PathSavings.MODELPLAYERS.getPath();
	
	/** The path file of the current. */
	private final String pathFileCurrent = PathSavings.MODELCURRENT.getPath();
	
	/** The path file of the barriers. */
	private final String pathFileBarriers = PathSavings.MODELBARRIERS.getPath();
	
	/** The path file of the graph. */
	private final String pathFileGraph = PathSavings.BARRIERGRAPH.getPath();
	
	/** The path file PowerUp. */
	private final String pathFilePUp = PathSavings.POWERUPS.getPath();
	
	/** The file of the model current. */
	private final File fileModelCurrent = new File(pathFileCurrent);
	
	/** The file of the model players. */
	private final File fileModelPlayers = new File(pathFilePlayers);
	
	/** The file of the model barriers. */
	private final File fileModelBarriers = new File(pathFileBarriers);
	
	/** The file of the graph. */
	private final File fileGraph = new File(pathFileGraph);
	
	/** The serializator. */
	private final Gson serializator = new Gson();
	
	/** The file PowerUp. */
	private final File filePUp = new File(pathFilePUp);
	
	/**
	 * Sets the alert exception.
	 */
	public static void setUpAlertException() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error !!");
		alert.setHeaderText("Error Dialog");
		alert.setContentText("Ooops, there was an error in reading/writing files. Check your permissions.!");
		alert.showAndWait();
	}
	
	/**
	 * Verifies if the normalGame file exist.
	 *
	 * @return the boolean
	 */
	protected Boolean fileExistNormal() {
		if(fileModelPlayers.exists() && fileModelCurrent.exists() && fileModelBarriers.exists() && fileGraph.exists()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Verifies if the powerUpGame file exist.
	 *
	 * @return the boolean
	 */
	protected Boolean fileExistPowerUp() {
		boolean normal = fileExistNormal();
		if(normal && filePUp.exists()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the current round and player.
	 *
	 * @return the current round and player
	 */
	protected Pair<Player, Integer> getCurrentRoundAndPlayer() {
		Player currentPlayer = null;
		int numRoundCurrent = -1;
		try{
			BufferedReader readerModelCurrent = new BufferedReader(new FileReader(fileModelCurrent));
			numRoundCurrent = serializator.fromJson(readerModelCurrent.readLine(), Integer.class);
			String nameCurrent = serializator.fromJson(readerModelCurrent.readLine(), String.class);
			Coordinate coordCurrent = serializator.fromJson(readerModelCurrent.readLine(), Coordinate.class);
			int barrLeftCurrent = serializator.fromJson(readerModelCurrent.readLine(), Integer.class);
			int finish_current = serializator.fromJson(readerModelCurrent.readLine(), Integer.class);
			currentPlayer = new PlayerImpl(nameCurrent, coordCurrent, barrLeftCurrent, finish_current);
			readerModelCurrent.close();
		} catch(Exception e) {
			setUpAlertException();
			System.exit(1);
		}
		return new Pair<>(currentPlayer, numRoundCurrent);
	}
	
	/**
	 * Gets the players list.
	 *
	 * @param numRound the num round
	 * @return the players list
	 */
	protected List<Player> getPlayersList(final int numRound) {
		List<Player> playersList = new ArrayList<>();
		try{
			BufferedReader readerModelPlayers = new BufferedReader(new FileReader(fileModelPlayers));
			//i need to go at the right round given the numRound
			for(int i=0; i < (numRound * 8); i++) {
				readerModelPlayers.readLine();
			}
			String player1 = serializator.fromJson(readerModelPlayers.readLine(), String.class);
			Coordinate coord1 = serializator.fromJson(readerModelPlayers.readLine(), Coordinate.class);
			int barrLeft1 = serializator.fromJson(readerModelPlayers.readLine(), Integer.class);
			int finish1 = serializator.fromJson(readerModelPlayers.readLine(), Integer.class);
			String player2 = serializator.fromJson(readerModelPlayers.readLine(), String.class);
			Coordinate coord2 = serializator.fromJson(readerModelPlayers.readLine(), Coordinate.class);
			int barrLeft2 = serializator.fromJson(readerModelPlayers.readLine(), Integer.class);
			int finish2 = serializator.fromJson(readerModelPlayers.readLine(), Integer.class);
			//i need to create a new item for GameRoundEnvironment list.
			playersList.add(new PlayerImpl(player1, coord1, barrLeft1, finish1));
			playersList.add(new PlayerImpl(player2, coord2, barrLeft2, finish2));
			readerModelPlayers.close();
		} catch(Exception e) {
			setUpAlertException();
			System.exit(1);
		}
		return playersList;
	}
	
	/**
	 * Line counter.
	 *
	 * @param file the file
	 * @return the int
	 */
	private int lineCounter(final File file) {
		int counter = 0;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while(reader.readLine() != null) {
				counter++;
			}
			reader.close();
		} catch(Exception e) {
			setUpAlertException();
			System.exit(1);
		}
		return counter + 1;
	}
	
	/**
	 * Gets the barriers and the Graph.
	 *
	 * @param numRound the num round
	 * @return the barriers
	 */
	protected Pair<List<Barrier>, Graph<Coordinate>> getBarriers(final int numRound){
		List<Barrier> barriers = new ArrayList<>();
		List<Pair<Coordinate, Coordinate>> listEdges = new ArrayList<>();
		try {
			BufferedReader readerModelBarriers = new BufferedReader(new FileReader(fileModelBarriers));
			if(Integer.parseInt(readerModelBarriers.readLine()) != numRound){
				readerModelBarriers.close();
				return new Pair<>(barriers, new BarriersGraph<>(Model.BOARD_DIMENSION));
			}
			else {
				for(int k = 0; k < (lineCounter(fileModelBarriers) - 1) / 3; k++) {
					Coordinate coord = serializator.fromJson(readerModelBarriers.readLine(), Coordinate.class);
					Orientation type = serializator.fromJson(readerModelBarriers.readLine(), Orientation.class);
					Piece piece = serializator.fromJson(readerModelBarriers.readLine(), Piece.class);
					barriers.add(new BarrierImpl(coord, type, piece));
				}
				BufferedReader readerGraph = new BufferedReader(new FileReader(fileGraph));
				int numberOfCoords = (lineCounter(fileGraph) / 2) + 1;
				for(int i = 0; i < numberOfCoords; i++) {
					Pair<Coordinate, Coordinate> coord = new Pair<>(serializator.fromJson(readerGraph.readLine(), Coordinate.class),
							serializator.fromJson(readerGraph.readLine(), Coordinate.class));
					if(coord.getX()!=null) {
						listEdges.add(coord);
					}
				}
				readerGraph.close();
			}
			readerModelBarriers.close();
		} catch(Exception e) {
			setUpAlertException();
			System.exit(1);
		}
		return new Pair<List<Barrier>, Graph<Coordinate>>(barriers, new BarriersGraph<Coordinate>(listEdges));
	}
	
	/**
	 * Gets the power up list.
	 *
	 * @param numRound the num round
	 * @return the power up list
	 */
	protected List<PowerUp> getPowerUpList(final int numRound){
		List<PowerUp> list = new ArrayList<>();
		int numPowerUps = (lineCounter(filePUp)-1) / 2;
		try {
			BufferedReader readerPUp = new BufferedReader(new FileReader(filePUp));
			if(Integer.parseInt(readerPUp.readLine()) != numRound) {
				readerPUp.close();
				return list;
			}
			for(int i = 0; i < numPowerUps; i++) {
				Coordinate coord = serializator.fromJson(readerPUp.readLine(), Coordinate.class);
				Type type = serializator.fromJson(readerPUp.readLine(), Type.class);
				list.add(new PowerUpImpl(coord, type));
			}
			readerPUp.close();
		} catch(Exception e) {
			setUpAlertException();
			System.exit(1);
		}
		return list;
	}
}
