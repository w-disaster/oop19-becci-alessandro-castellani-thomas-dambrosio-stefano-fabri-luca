package savings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Model;
import model.ModelFactoryImpl;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundEnvironmentImpl;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.RoundPUpEnvironmentImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.RoundBarriersImpl;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;
import model.roundenvironment.players.RoundPlayersImpl;
import model.roundenvironment.powerups.PowerUp;
import model.roundenvironment.powerups.PowerUp.Type;
import model.roundenvironment.powerups.PowerUpImpl;
import model.roundenvironment.powerups.RoundPowerUps;
import model.roundenvironment.powerups.RoundPowerUpsImpl;

public class LoadGamePUp extends LoadGameImpl<RoundPUpEnvironment> implements LoadGame<RoundPUpEnvironment>{
	
	private File filePUp;
	private String pathFilePUp = PathSavings.POWERUPS.getPath();
	private boolean allFileExists;
	
	public LoadGamePUp() {
		super();
		filePUp = new File(pathFilePUp);
		allFileExists = filePUpExist() ? true : false ;
	}
	
	private boolean filePUpExist() {
		if(filePUp.exists() && fileExist) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private List<PowerUp> getPowerUpList(){
		List<PowerUp> list = new ArrayList<>();
		int numPowerUps = lineCounter(filePUp) / 2;
		try {
			BufferedReader readerPUp = new BufferedReader(new FileReader(filePUp));
			for(int i = 0; i < numPowerUps; i++) {
				Coordinate coord = serializator.fromJson(readerPUp.readLine(), Coordinate.class);
				Type type = serializator.fromJson(readerPUp.readLine(), Type.class);
				list.add(new PowerUpImpl(coord, type));
			}
			readerPUp.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	protected void getData() {
		List<RoundPUpEnvironment> roundEnvironments = new ArrayList<>();
		try {
			Pair<Player, Integer> currents = getCurrentRoundAndPlayer();
			//now i have to get for each roundEnvironment the things i need
			for(int i=0; i < 3; i++) {
				RoundPlayers players = new RoundPlayersImpl(getPlayersList(i));
				RoundBarriers barriers = new RoundBarriersImpl(getBarriers(i).getX(), getBarriers(i).getY());
				RoundPowerUps powerUps = new RoundPowerUpsImpl(getPowerUpList());
				//set current player at the right round.
				if(i==currents.getY()) {
					players.setCurrentPlayer(currents.getX());
				}
				//da aggiungere i powerup nel costruttore
				RoundPUpEnvironment environment = new RoundPUpEnvironmentImpl(barriers, players, powerUps);
				roundEnvironments.add(environment);
			}
			//here i should create the model.
			iterator = roundEnvironments.iterator();
			iterator.next();
			for(int i=0; i < currents.getY(); i++) {
				iterator.next();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		model = new ModelFactoryImpl().buildFromExisting(roundEnvironments, Model.BOARD_DIMENSION);
		loadingChanged = true;
	}
	
	@Override
	public Iterator<RoundPUpEnvironment> getIterator() {
		if(allFileExists) {
			getData();
			return iterator;
		}
		return null;
	}

	@Override
	public Model<RoundPUpEnvironment> getModel() {
		if(allFileExists) {
			getData();
			return model;
		}
		return null;
	}

	@Override
	public boolean saveExist() {
		return allFileExists;
	}
	
	
}
