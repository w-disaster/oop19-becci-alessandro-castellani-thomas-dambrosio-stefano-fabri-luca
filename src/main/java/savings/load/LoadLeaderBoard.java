package savings.load;

import java.util.List;
import java.util.Map;

/**
 * Interface LoadLeaderBoard.
 */
public interface LoadLeaderBoard {

	/**
	 * Gets the index to player map.
	 *
	 * @return the index to player map
	 */
	Map<Integer, List<String>> getIndexToPlayerMap();
	
	/**
	 * Gets the index to score map.
	 *
	 * @return the index to score map
	 */
	Map<Integer, List<Integer>> getIndexToScoreMap();
	
	/**
	 * Gets the number of the pages.
	 *
	 * @return the number of the pages
	 */
	int getNumPages();
	
}
