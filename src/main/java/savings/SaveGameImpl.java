package savings;

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

public class SaveGameImpl<X extends RoundEnvironment> implements SaveGame{
	
	protected final Model<X> model;
	private File dir;
	protected File savePlayers;
	private File saveModelCurrent;
	private File saveBarriers;
	private File saveGraph;
	private final String pathDir = PathSavings.DIRECTORY.getPath() ;
	private final String pathFilePlayers = PathSavings.MODELPLAYERS.getPath();
	private final String pathFileCurrent = PathSavings.MODELCURRENT.getPath();
	private final String pathFileBarriers = PathSavings.MODELBARRIERS.getPath();
	private final String pathFileGraph = PathSavings.BARRIERGRAPH.getPath();
	protected Gson serializator;
	protected enum gameType{
		NORMAL,
		POWERUP;
	}
	
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
			System.out.println("problems creating file");
		}
	}
	
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
			System.out.println("problem in creating file");
		}
	}
	

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
			e.printStackTrace();
		}
	}
	
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
			e.printStackTrace();
		}
	}
	
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
			e.printStackTrace();
		}
	}
	
	private void writeGameType() {
		File fileGameType = new File(PathSavings.GAMETYPE.getPath());
		if(!fileGameType.exists()) {
			fileGameType.exists();
		}
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileGameType));
			writer.write(serializator.toJson(gameType.NORMAL));
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
	
	
	
	
}
