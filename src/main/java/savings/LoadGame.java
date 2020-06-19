package savings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Model;
import model.ModelImpl;
import model.roundenvironment.RoundEnvironment;
import model.roundenvironment.coordinate.Coordinate;
import model.roundenvironment.players.Player;
import model.roundenvironment.players.PlayerImpl;

public class LoadGame {
	private final String pathFilePlayers= PathSavings.MODELPLAYERS.getPath();
	private final String pathFileModelDim = PathSavings.MODELDIM.getPath();
	private final File fileModelPlayers;
	private final File fileModelDim;
	private Model<RoundEnvironment> model;
	private boolean fileModelExist;
	
	public LoadGame() {
		fileModelPlayers = new File(pathFilePlayers);
		fileModelDim = new File(pathFileModelDim);
		if(fileModelPlayers.exists() && fileModelDim.exists()) {
			fileModelExist = true;
			getData();
		}
		else {
			fileModelExist = false;
			System.out.println("files doesnt exist");
		}
	}
	
	private int lineCounter(File file) {
		int numLines = 0;
		try{
			BufferedReader readerModelPlayers = new BufferedReader(new FileReader(file));
			while(readerModelPlayers.readLine()!=null) {
				readerModelPlayers.readLine();
				numLines++;
			}
			readerModelPlayers.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return numLines;
	}
	
	private <X> void getData() {
		Gson serializator = new Gson();
		List<Player> playersList = new ArrayList<>();
		TypeToken<ArrayList<X>> token = new TypeToken<ArrayList<X>>() {};
		try {
			int linePlayersFile = lineCounter(fileModelPlayers);
			System.out.println(linePlayersFile);
			BufferedReader readerModelPlayers = new BufferedReader(new FileReader(fileModelPlayers));
			BufferedReader readerModelDim = new BufferedReader(new FileReader(fileModelDim));
			for(int i=0; i < linePlayersFile / 3; i++) {
				String nickname1 = serializator.fromJson(readerModelPlayers.readLine(), String.class);
				Coordinate coord1 = serializator.fromJson(readerModelPlayers.readLine(), Coordinate.class);
				int numBarriers1 = serializator.fromJson(readerModelPlayers.readLine(), Integer.class);
				String nickname2 = serializator.fromJson(readerModelPlayers.readLine(), String.class);
				Coordinate coord2 = serializator.fromJson(readerModelPlayers.readLine(), Coordinate.class);
				int numBarriers2 = serializator.fromJson(readerModelPlayers.readLine(), Integer.class);
				Player player1 = new PlayerImpl(nickname1, coord1, numBarriers1, 0);
				Player player2 = new PlayerImpl(nickname1, coord1, numBarriers1, 0);
			}
			//String serializedDim = readerModelDim.readLine();
			readerModelPlayers.close();
			readerModelDim.close();
			
			//int boardDimension = serializator.fromJson(serializedDim, Integer.class);
			//model = new ModelImpl<RoundEnvironment>(gameRoundEnvironments, boardDimension, null, null); 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public Model<RoundEnvironment> getModel() {
		if(fileModelExist) {
			return model;
		}
		return null;
	}
	
	public boolean saveExist() {
		return fileModelExist;
	}
}
