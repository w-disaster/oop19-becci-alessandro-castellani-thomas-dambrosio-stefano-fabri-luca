package savings.save;

import java.io.File;

public enum PathSavings {
	
	DIRECTORY(System.getProperty("user.home") + File.separator + ".quoridor2D"),
	
	LEADERBOARD(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "leaderBoard"),
	
	MODELPLAYERS(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "modelPlayers"),
	
	MODELCURRENT(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "modelCurrent"),
	
	MODELBARRIERS(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "modelBarriers"),
	
	BARRIERGRAPH(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "barrierGraph"),
	
	POWERUPS(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "powerUps"),
	
	GAMETYPE(System.getProperty("user.home") + File.separator + ".quoridor2D" + File.separator + "gameType"),
	
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
