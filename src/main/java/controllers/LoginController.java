package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import viewmenu.SceneChanger;
import viewmenu.SceneChangerImpl;

public class LoginController {

	private @FXML TextField textField1;
	
	private @FXML TextField textField2;
	
	private String nickname1;
	private String nickname2;
	
	private SceneChanger sceneChange;
	
	@FXML
	public void enterPressHandler(ActionEvent event) throws IOException {
		nickname1 = textField1.getText();
		nickname2 = textField2.getText();
		textField1.deleteText(0, textField1.getLength());
		textField2.deleteText(0, textField2.getLength());
		sceneChange = new SceneChangerImpl(event);
		sceneChange.change("layouts/main/scene.fxml", "Quoridor2D - Game");
		UIController controller = new UIController();
		controller.startGame(nickname1, nickname2);
	}
}
