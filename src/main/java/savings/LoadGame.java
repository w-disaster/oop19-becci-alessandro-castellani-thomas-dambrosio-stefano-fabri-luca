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
		if(saveExist()) {
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
		else {
			System.out.println("save file doesn't exist");
		}
	}
	
	public SaveResource getResource() {
		getData();
		return resource;
	}
	
	public boolean saveExist() {
		return fileSave.exists();
	}
}
