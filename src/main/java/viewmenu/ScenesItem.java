package viewmenu;


public enum ScenesItem {
	
	MENU("layouts/menu/MainMenu.fxml"),
	MENUTITLE("Quoridor2D-Menu"),
	GAME("layouts/main/scene.fxml"),
	GAMETITLE("Quoridor2D-Game"),
	LEADERBOARD("layouts/leaderboard/LeaderBoard.fxml"),
	LEADERBOARDTITLE("Leaderboard"),
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

