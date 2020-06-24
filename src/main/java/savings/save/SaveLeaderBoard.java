package savings.save;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import savings.load.LoadUtilities;

public class SaveLeaderBoard {

	private final String pathDir = System.getProperty("user.home") + File.separator + ".quoridor2D" ;
	private final String pathFile = pathDir + File.separator + "leaderBoard";
	private File dir;
	private File leaderBoard;
	private String winner;
	private String numVictories;
	
	/**
	 * If the directory or the files doesn't exist, they're created
	 * @throws IOException
	 */
	private void createDirAndFile() {
		if(!dir.exists()) {
			dir.mkdir();
		}
		try {
		if(!leaderBoard.exists()) {
			leaderBoard.createNewFile();
		}
		} catch(Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
	}
	
	/**
	 * This method tells if the name is on the list, otherwise, we'll need to add
	 * @return True if it present, False if it isn't
	 * @throws IOException 
	 */
	private Boolean nameIsPresent() {
		Boolean exist = false;
		try{
			// Construct BufferedReader from FileReader
			BufferedReader br = new BufferedReader(new FileReader(leaderBoard));
			String line = null;
			while ((line = br.readLine()) != null) {
				if(line.split("//")[0].toLowerCase().equals(winner)) {
					exist = true;
					numVictories = line.split("//")[1];
				}
			}
			br.close();
		} catch(Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
		
		return exist;
	}
	
	private void updateNameVictories() {
		try {
	        // StringBuffer takes the line to modify, it is like a String, but immutable
			StringBuffer inputBuffer = new StringBuffer();
			BufferedReader file = new BufferedReader(new FileReader(pathFile));
	        String line;

	        while ((line = file.readLine()) != null) {
	            if(line.split("//")[0].toLowerCase().equals(winner)) {
	            	line = winner + "//" + (Integer.parseInt(numVictories) + 1) ;
	            }
	            //if the line doesnt have to be modified, the inputBuffer takes the original one
	            inputBuffer.append(line);
	            inputBuffer.append(System.lineSeparator());
	        }
	        file.close();

	        // write the new string with the replaced line OVER the same file
	        FileOutputStream fileOut = new FileOutputStream(pathFile);
	        fileOut.write(inputBuffer.toString().getBytes());
	        fileOut.close();

	    } catch (Exception e) {
	    	LoadUtilities.setUpAlertException();
			System.exit(1);
	    }
	}
	
	private void writeNewName() {
		//setting to true for append mode
		try{
			FileWriter fout = new FileWriter(pathFile, true);
			fout.write(winner.toLowerCase() + "//" + Integer.toString(1) + System.lineSeparator());
			fout.close();
		}catch (Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
		
	}
	
	public void updateLeaderBoard(final String winner) {
		//updates the file in LeaderBoard
		this.winner = winner.toLowerCase();
		try{
			if(nameIsPresent()) {
				updateNameVictories();
			}
			else {
				writeNewName();
			}
		}catch(Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
		
	}

	/**
	 * TODO
	 * @throws IOException 
	 */
	public SaveLeaderBoard() {
		try{
			dir = new File(pathDir);
			leaderBoard = new File(pathFile);
			createDirAndFile();
		}catch(Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
		
	}

	

}
