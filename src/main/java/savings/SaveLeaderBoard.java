package savings;

import java.io.File;
import java.io.IOException;

public class SaveLeaderBoard {

	private final String pathDir = System.getProperty("user.home") + File.separator + ".quoridor2D" ;
	private File dir;
	private File leaderBoard;
	
	private void createDirAndFile() throws IOException {
		dir.mkdir();
		leaderBoard = new File(pathDir + File.separator + "leaderBoard");
		leaderBoard.createNewFile();
	}
	
	public void updateLeaderBoard(final String winner) {
		System.out.println("this should update the leaderBoard with new information");
		//updates the file in LeaderBoard
	}
	/**
	 * TODO
	 * @throws IOException 
	 */
	public SaveLeaderBoard() throws IOException {
		dir = new File(pathDir);
		if(!dir.exists()) {
			System.out.println("creating file and directory");
			createDirAndFile();
		}
	} 

}
