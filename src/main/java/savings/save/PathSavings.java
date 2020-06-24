package savings.save;

import java.io.File;

/**
 * The Enum PathSavings.
 */
public enum PathSavings {
	
	/** The directory. */
	DIRECTORY(System.getProperty("user.home") + File.separator + ".quoridor2D"),
	
	/** The leaderboard. */
	LEADERBOARD(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "leaderBoard"),
	
	/** The modelplayers. */
	MODELPLAYERS(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "modelPlayers"),
	
	/** The modelcurrent. */
	MODELCURRENT(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "modelCurrent"),
	
	/** The modelbarriers. */
	MODELBARRIERS(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "modelBarriers"),
	
	/** The barriergraph. */
	BARRIERGRAPH(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "barrierGraph"),
	
	/** The powerups. */
	POWERUPS(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "powerUps"),
	
	/** The gametype. */
	GAMETYPE(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "gameType"),
	
	/** The iterator. */
	ITERATOR(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "iteratorSave");
	
	
	/** The path file. */
	private final String pathFile;

    /**
     * Instantiates a new path savings.
     *
     * @param pathFile the path file
     */
    PathSavings(final String pathFile) {
        this.pathFile = pathFile;
    }

    /**
     * Gets the path.
     *
     * @return the path of the file.
     */
    public String getPath() {
        return this.pathFile;
    }
}
