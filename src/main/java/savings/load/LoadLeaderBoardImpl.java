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

/**
 * Class LoadLeaderBoardImpl.
 */
public class LoadLeaderBoardImpl implements LoadLeaderBoard {
	
	/** The list entries. */
	private final List<Pair<String, Integer>> listEntries;
	
	/** The number of pages. */
	private int numPages;
	
	/** The total entries. */
	private int totalEntries;
	
	/** The path file. */
	private final String pathFile = PathSavings.LEADERBOARD.getPath();
	
	/** The index to player. */
	private final Map<Integer, List<String>> indexToPlayer;
	
	/** The index to score. */
	private final Map<Integer, List<Integer>> indexToScore;
	
	/** The number of elements for page */
	private final int numPerPag = HboxTextController.NUM_ENTRIES_PAG;
	
	/**
	 * Read file and sort.
	 */
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
		listEntries.sort((o1, o2) -> o2.getValue() - o1.getValue());
	}
	
	
	/**
	 * Calculate number pages.
	 */
	private void calculateNumberPages() {
		Double numPagesDouble = (double) totalEntries / 3;
		numPages = numPagesDouble.intValue();
		if (numPagesDouble - numPages != 0) {
			numPages += 1;
		}
	}
	
	/**
	 * Assign pages to entries.
	 */
	private void assignPagesToEntries() {
		for (int i = 0; i < numPages; i++) {
			if (totalEntries >= numPerPag) {
				//put NUM_PER_PAG ELEM IN PAGE i
				List<String> players = new ArrayList<>();
				List<Integer> scores = new ArrayList<>();
				for (int k = 0; k < numPerPag; k++) {
					players.add(listEntries.get(0).getKey());
					scores.add(listEntries.get(0).getValue());
					listEntries.remove(0);
				}
				indexToPlayer.put(i, players);
				indexToScore.put(i, scores);
				totalEntries -= numPerPag;
			}
			else {
				List<String> players = new ArrayList<>();
				List<Integer> scores = new ArrayList<>();
				for (int k = 0; k < totalEntries; k++) {
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
	
	/**
	 * Instantiates a new loadLeaderBoardImpl.
	 */
	public LoadLeaderBoardImpl() {
		listEntries = new ArrayList<>();
		indexToPlayer = new HashMap<>();
		indexToScore = new HashMap<>();
		readFileAndSort();
		calculateNumberPages();
		assignPagesToEntries();
	}
	
	/**
	 * Gets the index to player map.
	 *
	 * @return the index to player map
	 */
	public Map<Integer, List<String>> getIndexToPlayerMap() {
		return indexToPlayer;
	}
	
	/**
	 * Gets the index to score map.
	 *
	 * @return the index to score map
	 */
	public Map<Integer, List<Integer>> getIndexToScoreMap() {
		return indexToScore;
	}

	/**
	 * Gets the number of pages.
	 *
	 * @return the number of pages
	 */
	public int getNumPages() {
		return numPages;
	}
	
}
