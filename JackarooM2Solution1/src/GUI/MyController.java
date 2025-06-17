package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MyController {

	  @FXML
	    private Button submit;

	    @FXML
	    private TextField playerNameField;

	    @FXML
	    private Label enterNameLabel;

	    @FXML
	    private void onButtonClick() {
	        String input = playerNameField.getText();
	        enterNameLabel.setText("Hello, " + input + "!");
	    }
	    
	
	
	
	
	
}
