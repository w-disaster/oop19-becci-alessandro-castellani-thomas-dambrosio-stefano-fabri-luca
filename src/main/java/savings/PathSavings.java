package savings;

import java.io.File;

public enum PathSavings {
	
	DIRECTORY(System.getProperty("user.home") + File.separator + ".quoridor2D"),
	
	LEADERBOARD(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "leaderBoard"),
	
	MODELPLAYERS(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "modelPlayers"),
	
	MODELDIM(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "model"),
	
	ITERATOR(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "iteratorSave");
	
	
	private final String pathFile;

    /**
     * @param scene The path of the scene.
     */
    PathSavings(final String pathFile) {
        this.pathFile = pathFile;
    }

    /**
     * @return the path of the scene.
     */
    public String getPath() {
        return this.pathFile;
    }
}
