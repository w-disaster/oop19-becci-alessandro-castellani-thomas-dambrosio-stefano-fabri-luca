package savings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Model;
import model.ModelFactory;
import model.ModelFactoryImpl;
import model.ModelImpl;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.RoundEnvironmentImpl;
import model.roundenvironment.barriers.Barrier;
import model.roundenvironment.barriers.Barrier.Orientation;
import model.roundenvironment.barriers.Barrier.Piece;
import model.roundenvironment.barriers.BarrierImpl;
import model.roundenvironment.barriers.RoundBarriers;
import model.roundenvironment.barriers.RoundBarriersImpl;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.coordinate.Pair;
import model.roundenvironment.graph.BarriersGraph;
import model.roundenvironment.graph.Graph;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.PlayerImpl;
import model.roundenvironment.players.RoundPlayers;
import model.roundenvironment.players.RoundPlayersImpl;

public class LoadGameImpl implements LoadGame{
	private final String pathFilePlayers= PathSavings.MODELPLAYERS.getPath();
	private final String pathFileCurrent = PathSavings.MODELCURRENT.getPath();
	private final String pathFileBarriers = PathSavings.MODELBARRIERS.getPath();
	private final String pathFileGraph = PathSavings.BARRIERGRAPH.getPath();
	private final File fileModelCurrent;
	private final File fileModelPlayers;
	private final File fileModelBarriers;
	private final File fileGraph;
	private Model<RoundEnvironment> model;
	private Iterator<RoundEnvironment> iterator;
	private boolean fileModelExist;
	private Gson serializator;
	public static boolean loadingChanged;
	
	public LoadGameImpl() {
		//starts from true because we have to do loading at least one time.
		loadingChanged = true;
		serializator = new Gson();
		fileModelPlayers = new File(pathFilePlayers);
		fileModelCurrent = new File(pathFileCurrent);
		fileModelBarriers = new File(pathFileBarriers);
		fileGraph = new File(pathFileGraph);
		if(fileModelPlayers.exists() && fileModelCurrent.exists() && fileModelBarriers.exists() && fileGraph.exists()) {
			fileModelExist = true;
		}
		else {
			fileModelExist = false;
			System.out.println("files doesnt exist");
		}
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
		return counter - 1;
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
				for(int k = 0; k < lineCounter(fileModelBarriers) / 3; k++) {
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
	
	private void getData() {
		List<RoundEnvironment> roundEnvironments = new ArrayList<>();
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
				RoundEnvironment environment = new RoundEnvironmentImpl(barriers,players);
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
	
	public Iterator<RoundEnvironment> getIterator(){
		if(loadingChanged) {
			getData();
		}
		if(fileModelExist) {
			return iterator;
		}
		return null;
	}
	
	public Model<RoundEnvironment> getModel() {
		if(loadingChanged) {
			getData();
		}
		if(fileModelExist) {
			return model;
		}
		return null;
	}
	
	public boolean saveExist() {
		return fileModelExist;
	}
}
