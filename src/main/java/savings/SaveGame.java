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
	private File saveModelDimension;
	private final String pathDir = PathSavings.DIRECTORY.getPath() ;
	private final String pathFilePlayers = PathSavings.MODELPLAYERS.getPath();
	private final String pathFileModelDim = PathSavings.MODELDIM.getPath();
	
	private void createDirAndFiles() {
		if(!dir.exists()) {
			dir.mkdir();
		}
		try {
		if(!savePlayers.exists() && !saveModelDimension.exists()) {
			savePlayers.createNewFile();
			saveModelDimension.createNewFile();
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
			saveModelDimension = new File(pathFileModelDim);
			createDirAndFiles();
		}catch(Exception e) {
			System.out.println("problem in creating file");
		}
	}
	
	public void save() {
		Gson serializator = new Gson();
		try {
			BufferedWriter roundPlayersWriter = new BufferedWriter(new FileWriter(savePlayers));
			BufferedWriter roundDimWriter = new BufferedWriter(new FileWriter(saveModelDimension));
			for(RoundEnvironment env : model.getGameRoundEnvironments()) {
				for(Player pl : env.getRoundPlayers().getPlayers()) {
					roundPlayersWriter.write(serializator.toJson(pl.getNickname()));
					roundPlayersWriter.newLine();
					roundPlayersWriter.write(serializator.toJson(pl.getCoordinate()));
					roundPlayersWriter.newLine();
					roundPlayersWriter.write(serializator.toJson(pl.getAvailableBarriers()));
					roundPlayersWriter.newLine();
					//i'll need also the finish line.
				}
			}
			//roundEnvWriter.write(serializator.toJson(model.getGameRoundEnvironments().get(index)));
			roundDimWriter.write(serializator.toJson(model.getBoardDimension()));
			roundPlayersWriter.close();
			roundDimWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
	}
	

	
	
	
}
