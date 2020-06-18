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
	private File saveModel;
	private final String pathDir = PathSavings.DIRECTORY.getPath() ;
	private final String pathFileModel = PathSavings.MODEL.getPath();
	
	private void createDirAndFiles() {
		if(!dir.exists()) {
			dir.mkdir();
		}
		try {
		if(!saveModel.exists()) {
			saveModel.createNewFile();
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
			saveModel = new File(pathFileModel);
			createDirAndFiles();
		}catch(Exception e) {
			System.out.println("problem in creating file");
		}
	}
	
	public void save() {
		Gson serializator = new Gson();
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(saveModel));
			out.write(serializator.toJson(model.getGameRoundEnvironments()));
			out.newLine();
			out.write(model.getBoardDimension());
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        LoadGame load = new LoadGame();
        System.out.println(load.getModel().getBoardDimension());
	}
	

	
	
	
}
