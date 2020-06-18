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
	
	private final SaveResource resource;
	private File dir;
	private File saveGame;
	private final String pathDir = System.getProperty("user.home") + File.separator + ".quoridor2D" ;
	private final String pathFile = pathDir + File.separator + "saveGame";
	
	private void createDirAndFile() {
		if(!dir.exists()) {
			dir.mkdir();
		}
		try {
		if(!saveGame.exists()) {
			saveGame.createNewFile();
		}
		} catch(Exception e) {
			System.out.println("problems creating file");
		}
	}
	//I HAVE TO PUT ALL OBJECTS TO SAVE IN A SINGLE OBJECT
	public SaveGame(final Model<RoundEnvironment> model, final Iterator<RoundEnvironment> iterator) {
		resource = new SaveResource(model, iterator);
		try{
			dir = new File(pathDir);
			saveGame = new File(pathFile);
			createDirAndFile();
		}catch(Exception e) {
			System.out.println("problem in creating file");
		}
	}
	
	public void save() {
		Gson serializator = new Gson();
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(saveGame));
			out.write(serializator.toJson(resource));
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
	}

	
	
	
}
