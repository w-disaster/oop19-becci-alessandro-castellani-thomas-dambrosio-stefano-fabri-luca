package savings;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.Model;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.players.Player;
import model.roundenvironment.powerups.PowerUp;

public class SaveGamePUp extends SaveGameImpl<RoundPUpEnvironment> implements SaveGame{
	
	private String pathPowerUps;
	private File savePowerUps;
	
	public SaveGamePUp(Model<RoundPUpEnvironment> model) {
		//other files are created
		super(model);
		pathPowerUps = PathSavings.POWERUPS.getPath();
		savePowerUps = new File(pathPowerUps);
		createFile();
	}
	
	private void createFile() {
		try {
			if(!savePowerUps.exists()) {
				savePowerUps.createNewFile();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void savePowerUps(final List<PowerUp> list) {
		try {
			BufferedWriter writerPUps = new BufferedWriter(new FileWriter(savePowerUps));
			for(PowerUp powerUp : list) {
				writerPUps.write(serializator.toJson(powerUp.getCoordinate()));
				writerPUps.newLine();
				writerPUps.write(serializator.toJson(powerUp.getType()));
				writerPUps.newLine();
			}
			writerPUps.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save() {
		try {
			BufferedWriter roundPlayersWriter = new BufferedWriter(new FileWriter(savePlayers));
			int numRound = -1;
			for(RoundPUpEnvironment env : model.getGameRoundEnvironments()) {
				numRound++;
				if(env.getRoundPlayers().getCurrentPlayer() != null) {
					saveCurrentPlayer(numRound, env.getRoundPlayers().getCurrentPlayer());
					//i only care about current Barriers.
					saveBarriers(numRound, env.getRoundBarriers().getBarriersAsList());
					//i'll save the graph here.
					saveGraph(env.getRoundBarriers().getBarriersAsGraph());
					savePowerUps(env.getRoundPowerUps().getPowerUpsAsList());
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
			LoadGameImpl.loadingChanged = true;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}