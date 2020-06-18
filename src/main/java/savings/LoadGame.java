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
	private final String pathDir = PathSavings.MODEL.getPath();
	private final String pathFileModel = PathSavings.MODEL.getPath();
	private final File fileSave;
	private Model<RoundEnvironment> model;
	private boolean fileModelExist;
	
	public LoadGame() {
		fileSave = new File(pathFileModel);
		if(fileSave.exists()) {
			fileModelExist = true;
			getData();
		}
		else {
			fileModelExist = false;
			System.out.println("file dont exist");
		}
	}
	
	private <X> void getData() {
		Gson serializator = new Gson();
		TypeToken<ArrayList<X>> token = new TypeToken<ArrayList<X>>() {};
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileSave));
			String serialized = reader.readLine();
			reader.close();
			String gameRoundEnvironmentsSer = serialized.split("//")[0];
			String boardDimensionSer = serialized.split("//")[1];
			List<RoundEnvironment> gameRoundEnvironments = serializator.fromJson(gameRoundEnvironmentsSer, token.getType());
			int boardDimension = serializator.fromJson(boardDimensionSer, Integer.class);
			model = new ModelImpl<RoundEnvironment>(gameRoundEnvironments, boardDimension); 
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
