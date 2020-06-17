package savings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadGame {
	private ObjectInputStream in;
	private final String pathDir = System.getProperty("user.home") + File.separator + ".quoridor2D" ;
	private final String pathFile = pathDir + File.separator + "saveGame";
	private final File fileSave;
	private SaveResource resource;
	
	public LoadGame() {
		fileSave = new File(pathFile);
	}
	
	private void getData() {
		try {
			in = new ObjectInputStream(new FileInputStream(pathFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			resource = (SaveResource) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public SaveResource getResource() {
		if(fileSave.exists()) {
			getData();
			return resource;
		}
		return null;
	}
	
	public boolean saveExist() {
		getResource();
		if(resource.getModel() != null && resource.getRoundIterator() != null &&
				resource.getView() != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
