package savings.save;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import model.Model;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.Graph;
import model.roundenvironment.players.Player;
import savings.load.LoadUtilities;
import view.menu.MenuController.GameStatus;

/**
 * The Class SaveGameImpl.
 *
 * @param <X> the generic type
 * @author Alessandro Becci
 */
public class SaveGameImpl<X extends RoundEnvironment> implements SaveGame{
	
	/** The model. */
	protected final Model<X> model;
	
	/** The dir. */
	private File dir;
	
	/** The save players. */
	protected File savePlayers;
	
	/** The save model current. */
	private File saveModelCurrent;
	
	/** The save barriers. */
	private File saveBarriers;
	
	/** The save graph. */
	private File saveGraph;
	
	/** The path dir. */
	private final String pathDir = PathSavings.DIRECTORY.getPath() ;
	
	/** The path file players. */
	private final String pathFilePlayers = PathSavings.MODELPLAYERS.getPath();
	
	/** The path file current. */
	private final String pathFileCurrent = PathSavings.MODELCURRENT.getPath();
	
	/** The path file barriers. */
	private final String pathFileBarriers = PathSavings.MODELBARRIERS.getPath();
	
	/** The path file graph. */
	private final String pathFileGraph = PathSavings.BARRIERGRAPH.getPath();
	
	/** The serializator. */
	protected final Gson serializator;
	
	/**
	 * Creates the dir and files.
	 */
	private void createDirAndFiles() {
		if(!dir.exists()) {
			dir.mkdir();
		}
		try {
			if(!savePlayers.exists()) {
				savePlayers.createNewFile();
			}
			if(!saveModelCurrent.exists()) {
				saveModelCurrent.createNewFile();
			}
			if(!saveBarriers.exists()) {
				saveBarriers.createNewFile();
			}
			if(!saveGraph.exists()) {
				saveGraph.createNewFile();
			}
		} catch(Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
	}
	
	/**
	 * Instantiates a new SaveGameImpl.
	 *
	 * @param model the model
	 */
	public SaveGameImpl(final Model<X> model) {
		this.model = model;
		serializator = new Gson();
		try{
			dir = new File(pathDir);
			savePlayers = new File(pathFilePlayers);
			saveModelCurrent = new File(pathFileCurrent);
			saveBarriers = new File(pathFileBarriers);
			saveGraph = new File(pathFileGraph);
			createDirAndFiles();
		}catch(Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
	}
	

	/**
	 * Saves current player.
	 *
	 * @param numRound the num round
	 * @param currentPlayer the current player
	 */
	protected void saveCurrentPlayer(final int numRound, Player currentPlayer) {
		try{
			BufferedWriter roundCurrentPlayer = new BufferedWriter(new FileWriter(saveModelCurrent));
			roundCurrentPlayer.write(Integer.toString(numRound));
			roundCurrentPlayer.newLine();
			roundCurrentPlayer.write(serializator.toJson(currentPlayer.getNickname()));
			roundCurrentPlayer.newLine();
			roundCurrentPlayer.write(serializator.toJson(currentPlayer.getCoordinate()));
			roundCurrentPlayer.newLine();
			roundCurrentPlayer.write(serializator.toJson(currentPlayer.getAvailableBarriers()));
			roundCurrentPlayer.newLine();
			roundCurrentPlayer.write(serializator.toJson(currentPlayer.getFinishLine()));
			roundCurrentPlayer.newLine();
			roundCurrentPlayer.close();
		} catch(Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
	}
	
	/**
	 * Save the barriers.
	 *
	 * @param numRound the num round
	 * @param list the list of barriers
	 */
	protected void saveBarriers(final int numRound, List<Barrier> list) {
		try{
			BufferedWriter roundBarriersWriter = new BufferedWriter(new FileWriter(saveBarriers));
			roundBarriersWriter.write(Integer.toString(numRound));
			roundBarriersWriter.newLine();
			for(Barrier br : list) {
				roundBarriersWriter.write(serializator.toJson(br.getCoordinate()));
				roundBarriersWriter.newLine();
				roundBarriersWriter.write(serializator.toJson(br.getOrientation()));
				roundBarriersWriter.newLine();
				roundBarriersWriter.write(serializator.toJson(br.getPiece()));
				roundBarriersWriter.newLine();
			}
			roundBarriersWriter.close();
		} catch(Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
	}
	
	/**
	 * Save the graph.
	 *
	 * @param graph the graph
	 */
	protected void saveGraph(final Graph<Coordinate> graph) {
		try {
			BufferedWriter roundGraphWriter = new BufferedWriter(new FileWriter(saveGraph));
			for(Pair<Coordinate, Coordinate> edge : graph.getEdges()) {
				roundGraphWriter.write(serializator.toJson(edge.getX()));
				roundGraphWriter.newLine();
				roundGraphWriter.write(serializator.toJson(edge.getY()));
				roundGraphWriter.newLine();
			}
			roundGraphWriter.close();
		} catch(Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
	}
	
	/**
	 * Saves GameType.
	 */
	private void writeGameType() {
		File fileGameType = new File(PathSavings.GAMETYPE.getPath());
		if(!fileGameType.exists()) {
			fileGameType.exists();
		}
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileGameType));
			writer.write(serializator.toJson(GameStatus.NORMAL));
			writer.close();
		} catch(Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
	}
	
	/**
	 * Saves all the game.
	 */
	public void save() {
		try {
			BufferedWriter roundPlayersWriter = new BufferedWriter(new FileWriter(savePlayers));
			int numRound = -1;
			for(RoundEnvironment env : model.getGameRoundEnvironments()) {
				numRound++;
				if(env.getRoundPlayers().getCurrentPlayer() != null) {
					saveCurrentPlayer(numRound, env.getRoundPlayers().getCurrentPlayer());
					//i only care about current Barriers.
					saveBarriers(numRound, env.getRoundBarriers().getBarriersAsList());
					//i'll save the graph here.
					saveGraph(env.getRoundBarriers().getBarriersAsGraph());
				}
				for(Player pl : env.getRoundPlayers().getPlayers()) {
					roundPlayersWriter.write(serializator.toJson(pl.getNickname()));
					roundPlayersWriter.newLine();
					roundPlayersWriter.write(serializator.toJson(pl.getCoordinate()));
					roundPlayersWriter.newLine();
					roundPlayersWriter.write(serializator.toJson(pl.getAvailableBarriers()));
					roundPlayersWriter.newLine();
					roundPlayersWriter.write(serializator.toJson(pl.getFinishLine()));
					roundPlayersWriter.newLine();
				}
			}
			roundPlayersWriter.close();
			writeGameType();
		} catch (IOException e1) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
	}

	
	
	
	
	
}
