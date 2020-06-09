package savings;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveLeaderBoard {

	private ObjectOutputStream out;
	private final String pathDir = System.getProperty("user.home") + File.separator + "quoridor2D" ;
	private File dir;
	private File leaderBoard;
	
	private void createDirAndFile() throws IOException {
		dir.mkdir();
		leaderBoard = new File(pathDir + File.separator + "leaderBoard");
		leaderBoard.createNewFile();
	}
	
	private void updateLeaderBoard() {
		System.out.println("this should update the leaderBoard with new information");
	}
	/**
	 * TODO
	 * @throws IOException 
	 */
	public SaveLeaderBoard() throws IOException {
		dir = new File(pathDir);
		if(dir.exists()) {
			updateLeaderBoard();
		}
		else {
			System.out.println("creating file and directory");
			createDirAndFile();
		}
	}
}
