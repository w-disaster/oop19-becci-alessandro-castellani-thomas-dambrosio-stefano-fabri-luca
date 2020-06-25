package view.scenechanger;

// TODO: Auto-generated Javadoc
/**
 * This is an Enum for storing path and names used for scene changes.
 *
 */
public enum ScenesItem {
	
	/** The menu. */
	MENU("layouts/menu/MainMenu.fxml"),
	
	/** The Menu Title. */
	MENUTITLE("Quoridor2D-Menu"),
	
	/** The game. */
	GAME("layouts/main/scene.fxml"),
	
	/** The Game Title. */
	GAMETITLE("Quoridor2D-Game"),
	
	/** The Leaderboard. */
	LEADERBOARD("layouts/leaderboard/LeaderBoard.fxml"),
	
	/** The Leaderboard Box . */
	PAGLEADERBOARD("layouts/leaderboard/hBoxText.fxml"),
	
	/** The Leaderboard Title. */
	LEADERBOARDTITLE("Leaderboard"),
	
	/** The Leaderboard Style. */
	LEADERBOARDSTYLE("layouts/leaderboard/style.css"),
	
	/** The logo. */
	LOGO("/logo/logo.png"),
	
	/** The barrierPowerUp. */
	BARRIERPUP("/layouts/main/barrierPowerUp.png"),
	
	/** The doubleMovePup. */
	DOUBLEPUP("/layouts/main/doubleMovePowerUp.png");
	
	
	/** The path file. */
	private final String pathFile;

    /**
     * Instantiates a new scenes item.
     *
     * @param pathFile the path file
     */
    ScenesItem(final String pathFile) {
        this.pathFile = pathFile;
    }

    /**
     * Gets the.
     *
     * @return the path of the scene.
     */
    public String get() {
        return this.pathFile;
    }
}

