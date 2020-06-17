package savings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import controllers.UIController;
import model.Model;
import model.roundenvironment.RoundEnvironment;

public class SaveGame {
	
	private final SaveResource resource;
	private ObjectOutputStream out;
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
	public SaveGame(final Model<RoundEnvironment> model, final UIController view, final Iterator<RoundEnvironment> iterator) {
		resource = new SaveResource(model, view, iterator);
		try{
			dir = new File(pathDir);
			saveGame = new File(pathFile);
			createDirAndFile();
		}catch(Exception e) {
			System.out.println("problem in creating file");
		}
		try {
			out = new ObjectOutputStream(new FileOutputStream(pathFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			out.writeObject(resource);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
}
