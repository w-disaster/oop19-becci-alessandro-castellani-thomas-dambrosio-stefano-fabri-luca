package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HboxTextController {
	@FXML private Label title1;
	@FXML private Label title2;
	private int number;
	
	public void setData(final int num) {
		number=num;
	}
	public void initialize() {
		title1.setStyle("-fx-text-fill:red;");
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				title1.setText(Integer.toString(number));
			}
			
		});
	}
}
