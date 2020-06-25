package view.scenechanger;

/**
 * The Interface SceneChanger.
 */
public interface SceneChanger {

	/**
	 * Changes the scene in the stage with the given one.
	 *
	 * @param pathToFXML the path to FXML
	 * @param title the title
	 */
	void change(String pathToFXML, String title);

	
}
