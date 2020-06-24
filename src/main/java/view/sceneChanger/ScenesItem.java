package view.sceneChanger;

/**
 * This is an Enum for storing path and names used for scene changes.
 * @author Alessandro Becci
 *
 */
public enum ScenesItem {
	
	MENU("layouts/menu/MainMenu.fxml"),
	MENUTITLE("Quoridor2D-Menu"),
	GAME("layouts/main/scene.fxml"),
	GAMETITLE("Quoridor2D-Game"),
	LEADERBOARD("layouts/leaderboard/LeaderBoard.fxml"),
	PAGLEADERBOARD("layouts/leaderboard/hBoxText.fxml"),
	LEADERBOARDTITLE("Leaderboard"),
	LEADERBOARDSTYLE("layouts/leaderboard/style.css"),
	LOGO("/logo/logo.png"),
	BARRIERPUP("/layouts/main/barrierPowerUp.png"),
	DOUBLEPUP("/layouts/main/doubleMovePowerUp.png"),
	;
	
	
	private final String pathFile;

    /**
     * @param scene The path of the scene.
     */
    ScenesItem(final String pathFile) {
        this.pathFile = pathFile;
    }

    /**
     * @return the path of the scene.
     */
    public String get() {
        return this.pathFile;
    }
}

