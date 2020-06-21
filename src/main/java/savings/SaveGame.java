package savings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

import controllers.UIController;
import model.Model;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.players.Player;

public class SaveGame {
	
	private final Model<RoundEnvironment> model;
	private final Iterator<RoundEnvironment> iterator;
	private File dir;
	private File savePlayers;
	private File saveModelCurrent;
	private File saveBarriers;
	private final String pathDir = PathSavings.DIRECTORY.getPath() ;
	private final String pathFilePlayers = PathSavings.MODELPLAYERS.getPath();
	private final String pathFileCurrent = PathSavings.MODELCURRENT.getPath();
	private final String pathFileBarriers = PathSavings.MODELBARRIERS.getPath();
	private Gson serializator;
	
	private void createDirAndFiles() {
		if(!dir.exists()) {
			dir.mkdir();
		}
		try {
		if(!savePlayers.exists() && !saveModelCurrent.exists() && !saveBarriers.exists()) {
			savePlayers.createNewFile();
			saveModelCurrent.createNewFile();
			saveBarriers.createNewFile();
		}
		} catch(Exception e) {
			System.out.println("problems creating file");
		}
	}
	
	public SaveGame(final Model<RoundEnvironment> model, final Iterator<RoundEnvironment> iterator) {
		this.model = model;
		this.iterator = iterator;
		serializator = new Gson();
		try{
			dir = new File(pathDir);
			savePlayers = new File(pathFilePlayers);
			saveModelCurrent = new File(pathFileCurrent);
			saveBarriers = new File(pathFileBarriers);
			createDirAndFiles();
		}catch(Exception e) {
			System.out.println("problem in creating file");
		}
	}
	
	private void saveCurrentPlayer(final int numRound, Player currentPlayer) {
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
	
	private void saveBarriers(final int numRound, List<Barrier> list) {
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
	
	public void save() {
		try {
			BufferedWriter roundPlayersWriter = new BufferedWriter(new FileWriter(savePlayers));
			int numRound = -1;
			for(RoundEnvironment env : model.getGameRoundEnvironments()) {
				numRound++;
				if(env.getRoundPlayers().getCurrentPlayer() != null) {
					saveCurrentPlayer(numRound, env.getRoundPlayers().getCurrentPlayer());
					//System.out.println("current player Saved");
					saveBarriers(numRound, env.getRoundBarriers().getBarriersAsList());
					//System.out.println("barriers Saved");
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
			//System.out.println("players Saved");
			roundPlayersWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
	}
	

	
	
	
}
