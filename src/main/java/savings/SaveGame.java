package savings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import com.google.gson.Gson;

import controllers.UIController;
import model.Model;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.players.Player;

public class SaveGame {
	
	private final Model<RoundEnvironment> model;
	private File dir;
	private File savePlayers;
	private File saveModelCurrent;
	private final String pathDir = PathSavings.DIRECTORY.getPath() ;
	private final String pathFilePlayers = PathSavings.MODELPLAYERS.getPath();
	private final String pathFileCurrent = PathSavings.MODELCURRENT.getPath();
	
	private void createDirAndFiles() {
		if(!dir.exists()) {
			dir.mkdir();
		}
		try {
		if(!savePlayers.exists() && !saveModelCurrent.exists()) {
			savePlayers.createNewFile();
			saveModelCurrent.createNewFile();
		}
		} catch(Exception e) {
			System.out.println("problems creating file");
		}
	}
	//I HAVE TO PUT ALL OBJECTS TO SAVE IN A SINGLE OBJECT
	public SaveGame(final Model<RoundEnvironment> model, final Iterator<RoundEnvironment> iterator) {
		this.model = model;
		try{
			dir = new File(pathDir);
			savePlayers = new File(pathFilePlayers);
			saveModelCurrent = new File(pathFileCurrent);
			createDirAndFiles();
		}catch(Exception e) {
			System.out.println("problem in creating file");
		}
	}
	
	public void save() {
		Gson serializator = new Gson();
		try {
			BufferedWriter roundPlayersWriter = new BufferedWriter(new FileWriter(savePlayers));
			BufferedWriter roundCurrentPlayer = new BufferedWriter(new FileWriter(saveModelCurrent));
			int numRound = -1;
			for(RoundEnvironment env : model.getGameRoundEnvironments()) {
				numRound++;
				if(env.getRoundPlayers().getCurrentPlayer() != null) {
					roundCurrentPlayer.write(Integer.toString(numRound));
					roundCurrentPlayer.newLine();
					roundCurrentPlayer.write(serializator.toJson(env.getRoundPlayers().getCurrentPlayer().getNickname()));
					roundCurrentPlayer.newLine();
					roundCurrentPlayer.write(serializator.toJson(env.getRoundPlayers().getCurrentPlayer().getCoordinate()));
					roundCurrentPlayer.newLine();
					roundCurrentPlayer.write(serializator.toJson(env.getRoundPlayers().getCurrentPlayer().getAvailableBarriers()));
					roundCurrentPlayer.newLine();
					roundCurrentPlayer.write(serializator.toJson(env.getRoundPlayers().getCurrentPlayer().getFinishLine()));
					roundCurrentPlayer.newLine();
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
			roundCurrentPlayer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
	}
	

	
	
	
}
