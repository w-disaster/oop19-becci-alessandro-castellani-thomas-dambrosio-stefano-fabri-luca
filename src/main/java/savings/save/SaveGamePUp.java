package savings.save;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import model.Model;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.players.Player;
import model.roundenvironment.powerups.PowerUp;
import savings.load.LoadUtilities;
import view.menu.MenuController.GameStatus;

/**
 * The Class SaveGamePUp.
 */
public class SaveGamePUp extends SaveGameImpl<RoundPUpEnvironment> implements SaveGame {
	
	/** The path power ups. */
	private final String pathPowerUps;
	
	/** The save power ups. */
	private final File savePowerUps;
	
	/**
	 * Instantiates a new SaveGamePUp.
	 *
	 * @param model the model
	 */
	public SaveGamePUp(final Model<RoundPUpEnvironment> model) {
		//other files are created
		super(model);
		pathPowerUps = PathSavings.POWERUPS.getPath();
		savePowerUps = new File(pathPowerUps);
		createFile();
	}
	
	/**
	 * Creates the file.
	 */
	private void createFile() {
		try {
			if (!savePowerUps.exists()) {
				savePowerUps.createNewFile();
			}
		} catch (Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
	}
	
	/**
	 * Saves power ups.
	 *
	 * @param numRound the num round
	 * @param list the list
	 */
	private void savePowerUps(final int numRound, final List<PowerUp> list) {
		try {
			BufferedWriter writerPUps = new BufferedWriter(new FileWriter(savePowerUps));
			writerPUps.write(Integer.toString(numRound));
			writerPUps.newLine();
			for (final PowerUp powerUp : list) {
				writerPUps.write(serializator.toJson(powerUp.getCoordinate()));
				writerPUps.newLine();
				writerPUps.write(serializator.toJson(powerUp.getType()));
				writerPUps.newLine();
			}
			writerPUps.close();
		} catch (Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
	}
	
	/**
	 * Saves GameType.
	 */
	private void writeGameType() {
		File fileGameType = new File(PathSavings.GAMETYPE.getPath());
		if (!fileGameType.exists()) {
			fileGameType.exists();
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileGameType));
			writer.write(serializator.toJson(GameStatus.POWERUP));
			writer.close();
		} catch (Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
	}
	
	/**
	 * Saves all the game.
	 */
	@Override
	public void save() {
		try {
			BufferedWriter roundPlayersWriter = new BufferedWriter(new FileWriter(savePlayers));
			int numRound = -1;
			for (final RoundPUpEnvironment env : model.getGameRoundEnvironments()) {
				numRound++;
				if (env.getRoundPlayers().getCurrentPlayer() != null) {
					saveCurrentPlayer(numRound, env.getRoundPlayers().getCurrentPlayer());
					//i only care about current Barriers.
					saveBarriers(numRound, env.getRoundBarriers().getBarriersAsList());
					//i'll save the graph here.
					saveGraph(env.getRoundBarriers().getBarriersAsGraph());
					savePowerUps(numRound, env.getRoundPowerUps().getPowerUpsAsList());
				}
				for (final Player pl : env.getRoundPlayers().getPlayers()) {
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
