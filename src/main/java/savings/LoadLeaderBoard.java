package savings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;

public class LoadLeaderBoard {
	
	private List<Pair<String, Integer>> listEntries;
	private Map<Integer, Integer> indexToEntries;
	private int numPages;
	private int totalEntries;
	private final String pathDir = System.getProperty("user.home") + File.separator + ".quoridor2D" ;
	private final String pathFile = pathDir + File.separator + "leaderBoard";
	
	private void readFile() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(pathFile));
			String line = reader.readLine();
			while (line != null) {
				//System.out.println(line);
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
			System.out.println("problems reading file");
			e.printStackTrace();
		}
	}
	
	private void calculateNumberPages() {
		Double numPagesDouble = (double)totalEntries / 3;
		numPages = numPagesDouble.intValue();
		if(numPagesDouble - numPages != 0) {
			numPages += 1;
		}
	}
	
	private void assignEntriesToPages() {
		for(int i=0; i<numPages; i++) {
			if(totalEntries >= 3) {
				indexToEntries.put(i, 3);
				totalEntries -= 3;
			}else {
				indexToEntries.put(i, totalEntries);
				totalEntries = 0;
			}
		}
	}
	
	public LoadLeaderBoard() {
		listEntries = new ArrayList<>();
		indexToEntries = new HashMap<>();
		readFile();
		calculateNumberPages();
		assignEntriesToPages();
	}
	
	public List<Pair<String, Integer>> getSortedEntries() {
		listEntries.sort(new Comparator<Pair<String, Integer>>(){

			@Override
			public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
				if(o1.getValue() > o2.getValue()) {
					return -1;
				}
				else {
					if(o1.getValue() == o2.getValue()) {
						return 0;
					}
					else {
						return 1;
					}
				}
			}
			
		});
		return listEntries;
	}
	
	public Map<Integer, Integer> getIndexToNumEntries(){
		return indexToEntries;
	}

	public int getNumPages() {
		return numPages;
	}
	
}
