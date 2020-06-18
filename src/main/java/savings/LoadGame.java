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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Model;
import model.ModelImpl;
import model.roundenvironment.RoundEnvironment;

public class LoadGame {
	private final String pathFileModelEnv = PathSavings.MODELENV.getPath();
	private final String pathFileModelDim = PathSavings.MODELDIM.getPath();
	private final File fileModelEnv;
	private final File fileModelDim;
	private Model<RoundEnvironment> model;
	private boolean fileModelExist;
	
	public LoadGame() {
		fileModelEnv = new File(pathFileModelEnv);
		fileModelDim = new File(pathFileModelDim);
		if(fileModelEnv.exists() && fileModelDim.exists()) {
			fileModelExist = true;
			getData();
		}
		else {
			fileModelExist = false;
			System.out.println("files doesnt exist");
		}
	}
	
	private <X> void getData() {
		Gson serializator = new Gson();
		TypeToken<ArrayList<X>> token = new TypeToken<ArrayList<X>>() {};
		try {
			BufferedReader readerModelEnv = new BufferedReader(new FileReader(fileModelEnv));
			BufferedReader readerModelDim = new BufferedReader(new FileReader(fileModelDim));
			String serializedEnv = readerModelEnv.readLine();
			String serializedDim = readerModelDim.readLine();
			readerModelEnv.close();
			readerModelDim.close();
			List<RoundEnvironment> gameRoundEnvironments = serializator.fromJson(serializedEnv, token.getType());
			int boardDimension = serializator.fromJson(serializedDim, Integer.class);
			model = new ModelImpl<RoundEnvironment>(gameRoundEnvironments, boardDimension, null, null); 
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
