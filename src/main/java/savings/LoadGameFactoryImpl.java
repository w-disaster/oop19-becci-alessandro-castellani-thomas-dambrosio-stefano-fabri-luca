package savings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

import model.Model;
import model.ModelFactoryImpl;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundEnvironmentImpl;
import model.roundenvironment.RoundPUpEnvironment;
import model.roundenvironment.RoundPUpEnvironmentImpl;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.barriers.BarrierImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.RoundBarriersImpl;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.barriers.Barrier.Piece;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.BarriersGraph;
import model.roundenvironment.graph.Graph;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.PlayerImpl;
import model.roundenvironment.players.RoundPlayers;
import model.roundenvironment.players.RoundPlayersImpl;
import model.roundenvironment.powerups.PowerUp;
import model.roundenvironment.powerups.PowerUpImpl;
import model.roundenvironment.powerups.RoundPowerUps;
import model.roundenvironment.powerups.RoundPowerUpsImpl;
import model.roundenvironment.powerups.PowerUp.Type;

public class LoadGameFactoryImpl implements LoadGameFactory{
	
	
	private final String pathFilePlayers= PathSavings.MODELPLAYERS.getPath();
	private final String pathFileCurrent = PathSavings.MODELCURRENT.getPath();
	private final String pathFileBarriers = PathSavings.MODELBARRIERS.getPath();
	private final String pathFileGraph = PathSavings.BARRIERGRAPH.getPath();
	private final String pathFilePUp = PathSavings.POWERUPS.getPath();
	private final File fileModelCurrent = new File(pathFileCurrent);
	private final File fileModelPlayers = new File(pathFilePlayers);
	private final File fileModelBarriers = new File(pathFileBarriers);
	private final File fileGraph = new File(pathFileGraph);
	private final Gson serializator = new Gson();
	private File filePUp = new File(pathFilePUp);
	
	
	private Boolean fileExistNormal() {
		if(fileModelPlayers.exists() && fileModelCurrent.exists() && fileModelBarriers.exists() && fileGraph.exists()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private Boolean fileExistPowerUp() {
		boolean normal = fileExistNormal();
		if(normal && filePUp.exists()) {
			return true;
		}
		return false;
	}
	
	private Pair<Player, Integer> getCurrentRoundAndPlayer() {
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
			e.printStackTrace();
		}
		return new Pair<>(currentPlayer, numRoundCurrent);
	}
	
	private List<Player> getPlayersList(final int numRound) {
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
			e.printStackTrace();
		}
		return playersList;
	}
	
	private int lineCounter(File file) {
		int counter = 0;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while(reader.readLine() != null) {
				counter++;
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return counter + 1;
	}
	
	private Pair<List<Barrier>, Graph<Coordinate>> getBarriers(final int numRound){
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
			e.printStackTrace();
		}
		return new Pair<List<Barrier>, Graph<Coordinate>>(barriers, new BarriersGraph<Coordinate>(listEdges));
	}
	
	@Override
	public LoadGame<RoundEnvironment> buildNormal() {
		return new LoadGame<RoundEnvironment>() {

			private Iterator<RoundEnvironment> iterator;
			private Model<RoundEnvironment> model;
			private boolean fileExist = fileExistNormal();

			@Override
			public void getData() {
				List<RoundEnvironment> roundEnvironments = new ArrayList<>();
				try {
					Pair<Player, Integer> currents = getCurrentRoundAndPlayer();
					//now i have to get for each roundEnvironment the things i need
					for(int i=0; i < 3; i++) {
						RoundPlayers players = new RoundPlayersImpl(getPlayersList(i));
						RoundBarriers barriers = new RoundBarriersImpl(getBarriers(i).getX(), getBarriers(i).getY());
						//set current player at the right round.
						if(i==currents.getY()) {
							players.setCurrentPlayer(currents.getX());
						}
						RoundEnvironment environment = new RoundEnvironmentImpl(barriers,players);
						roundEnvironments.add(environment);
					}
					//here i should create the model.
					iterator = roundEnvironments.iterator();
					iterator.next();
					//set the iterator to the current round.
					for(int i=0; i < currents.getY(); i++) {
						iterator.next();
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				model = new ModelFactoryImpl().buildFromExisting(roundEnvironments, Model.BOARD_DIMENSION);
			}

			@Override
			public Iterator<RoundEnvironment> getIterator() {
				if(fileExist) {
					getData();
					return iterator;
				}
				return null;
			}

			@Override
			public Model<RoundEnvironment> getModel() {
				if(fileExist) {
					getData();
					return model;
				}
				return null;
			}

			@Override
			public boolean saveExist() {
				return fileExist;
			}
			
		};
	}

	@Override
	public LoadGame<RoundPUpEnvironment> buildPowerup() {
		return new LoadGame<RoundPUpEnvironment>() {
			
			private Iterator<RoundPUpEnvironment> iterator;
			private Model<RoundPUpEnvironment> model;
			private boolean fileExist = fileExistPowerUp();
			
			private List<PowerUp> getPowerUpList(final int numRound){
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
					e.printStackTrace();
				}
				return list;
			}
			
			@Override
			public void getData() {
				List<RoundPUpEnvironment> roundEnvironments = new ArrayList<>();
				try {
					Pair<Player, Integer> currents = getCurrentRoundAndPlayer();
					//now i have to get for each roundEnvironment the things i need
					for(int i=0; i < 3; i++) {
						RoundPlayers players = new RoundPlayersImpl(getPlayersList(i));
						RoundBarriers barriers = new RoundBarriersImpl(getBarriers(i).getX(), getBarriers(i).getY());
						RoundPowerUps powerUps = new RoundPowerUpsImpl(getPowerUpList(i));
						//set current player at the right round.
						if(i==currents.getY()) {
							players.setCurrentPlayer(currents.getX());
						}
						RoundPUpEnvironment environment = new RoundPUpEnvironmentImpl(barriers,players, powerUps);
						roundEnvironments.add(environment);
					}
					//here i should create the model.
					iterator = roundEnvironments.iterator();
					iterator.next();
					//set the iterator to the current round.
					for(int i=0; i < currents.getY(); i++) {
						iterator.next();
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				model = new ModelFactoryImpl().buildFromExisting(roundEnvironments, Model.BOARD_DIMENSION);
			}

			@Override
			public Iterator<RoundPUpEnvironment> getIterator() {
				if(fileExist) {
					getData();
					return iterator;
				}
				return null;
			}

			@Override
			public Model<RoundPUpEnvironment> getModel() {
				if(fileExist) {
					getData();
					return model;
				}
				return null;
			}

			@Override
			public boolean saveExist() {
				return fileExist;
			}
			
		};
	}

}
