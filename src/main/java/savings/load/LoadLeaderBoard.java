package savings.load;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;
import savings.save.PathSavings;
import view.leaderboard.HboxTextController;

public class LoadLeaderBoard {
	
	private List<Pair<String, Integer>> listEntries;
	private int numPages;
	private int totalEntries;
	private final String pathFile = PathSavings.LEADERBOARD.getPath();
	private Map<Integer, List<String>> indexToPlayer;
	private Map<Integer, List<Integer>> indexToScore;
	private final int NUM_PER_PAG = HboxTextController.NUM_ENTRIES_PAG;
	
	private void readFileAndSort() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(pathFile));
			String line = reader.readLine();
			while (line != null) {
				totalEntries++;
				String name = line.split("//")[0];
				int score = Integer.parseInt(line.split("//")[1]);
				Pair<String, Integer> entry = new Pair<>(name, score);
				listEntries.add(entry);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			LoadUtilities.setUpAlertException();
			System.exit(1);
		}
		listEntries.sort(((o1, o2) -> o2.getValue() - o1.getValue()));
	}
	
	
	private void calculateNumberPages() {
		Double numPagesDouble = (double)totalEntries / 3;
		numPages = numPagesDouble.intValue();
		if(numPagesDouble - numPages != 0) {
			numPages += 1;
		}
	}
	
	private void assignPagesToEntries() {
		for(int i=0; i < numPages; i++) {
			if(totalEntries >= NUM_PER_PAG) {
				//put NUM_PER_PAG ELEM IN PAGE i
				List<String> players = new ArrayList<>();
				List<Integer> scores = new ArrayList<>();
				for(int k=0; k < NUM_PER_PAG; k++) {
					players.add(listEntries.get(0).getKey());
					scores.add(listEntries.get(0).getValue());
					listEntries.remove(0);
				}
				indexToPlayer.put(i, players);
				indexToScore.put(i, scores);
				totalEntries -= NUM_PER_PAG;
			}
			else {
				List<String> players = new ArrayList<>();
				List<Integer> scores = new ArrayList<>();
				for(int k=0; k < totalEntries; k++) {
					//PUT totalEntries ELEM left in page i
					players.add(listEntries.get(0).getKey());
					scores.add(listEntries.get(0).getValue());
					listEntries.remove(0);
				}
				indexToPlayer.put(i, players);
				indexToScore.put(i, scores);
				totalEntries = 0;
			}
		}
	}
	
	public LoadLeaderBoard() {
		listEntries = new ArrayList<>();
		indexToPlayer = new HashMap<>();
		indexToScore = new HashMap<>();
		readFileAndSort();
		calculateNumberPages();
		assignPagesToEntries();
	}
	
	public Map<Integer, List<String>> getIndexToPlayerMap(){
		return indexToPlayer;
	}
	
	public Map<Integer, List<Integer>> getIndexToScoreMap(){
		return indexToScore;
	}

	public int getNumPages() {
		return numPages;
	}
	
}