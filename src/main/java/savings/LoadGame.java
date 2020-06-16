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
		resource = null;
		in = null;
		if(fileSave.exists()) {
			try {
				in = new ObjectInputStream(new FileInputStream(pathFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			resource = (SaveResource) getData();
		}
		else {
			System.out.println("save file doesn't exist");
		}
	}
	
	private Object getData() {
		try {
			return in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object getResource() {
		return resource;
		
	}
}
