package savings;

import java.io.File;
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
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.RoundPlayers;
import model.roundenvironment.players.RoundPlayersImpl;

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
	
	@Override
	protected void getData() {
		List<RoundPUpEnvironment> roundEnvironments = new ArrayList<>();
		try {
			Pair<Player, Integer> currents = getCurrentRoundAndPlayer();
			//now i have to get for each roundEnvironment the things i need
			for(int i=0; i < 3; i++) {
				RoundPlayers players = new RoundPlayersImpl(getPlayersList(i));
				//this is obvious. because i don't pass it the graph. I'll get that in the getBarrier
				RoundBarriers barriers = new RoundBarriersImpl(getBarriers(i).getX(), getBarriers(i).getY());
				//set current player at the right round.
				if(i==currents.getY()) {
					players.setCurrentPlayer(currents.getX());
				}
				//da aggiungere i powerup nel costruttore
				RoundPUpEnvironment environment = new RoundPUpEnvironmentImpl(barriers,players);
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
		
		//System.out.println("current Player BF: " + roundEnvironments.get(0).getRoundPlayers().getCurrentPlayer().getNickname());
		//System.out.println("current Player BF: " + roundEnvironments.get(0).getRoundPlayers().getCurrentPlayer().getCoordinate());
		model = new ModelFactoryImpl().buildFromExisting(roundEnvironments, Model.BOARD_DIMENSION);
		//System.out.println("current Player AF: " + model.getCurrentRoundEnvironment().getRoundPlayers().getCurrentPlayer().getNickname());
		//System.out.println("current Player AF: " + model.getCurrentRoundEnvironment().getRoundPlayers().getCurrentPlayer().getCoordinate());
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
