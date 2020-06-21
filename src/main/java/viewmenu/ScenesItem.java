package viewmenu;

import java.io.File;

public enum ScenesItem {
	
	MENU("layouts/menu/MainMenu.fxml"),
	MENUTITLE("Quoridor2D-Menu"),
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

