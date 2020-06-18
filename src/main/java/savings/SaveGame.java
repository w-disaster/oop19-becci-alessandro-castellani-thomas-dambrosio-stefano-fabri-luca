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

public class SaveGame {
	
	private final Model<RoundEnvironment> model;
	private File dir;
	private File saveRoundEnvironment;
	private File saveModelDimension;
	private final String pathDir = PathSavings.DIRECTORY.getPath() ;
	private final String pathFileModelEnv = PathSavings.MODELENV.getPath();
	private final String pathFileModelDim = PathSavings.MODELDIM.getPath();
	
	private void createDirAndFiles() {
		if(!dir.exists()) {
			dir.mkdir();
		}
		try {
		if(!saveRoundEnvironment.exists() && !saveModelDimension.exists()) {
			saveRoundEnvironment.createNewFile();
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
			saveRoundEnvironment = new File(pathFileModelEnv);
			saveModelDimension = new File(pathFileModelDim);
			createDirAndFiles();
		}catch(Exception e) {
			System.out.println("problem in creating file");
		}
	}
	
	public void save() {
		Gson serializator = new Gson();
		try {
			BufferedWriter roundEnvWriter = new BufferedWriter(new FileWriter(saveRoundEnvironment));
			BufferedWriter roundDimWriter = new BufferedWriter(new FileWriter(saveModelDimension));
			roundEnvWriter.write(serializator.toJson(model.getGameRoundEnvironments()));
			roundDimWriter.write(serializator.toJson(model.getBoardDimension()));
			roundEnvWriter.close();
			roundDimWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
	}
	

	
	
	
}
