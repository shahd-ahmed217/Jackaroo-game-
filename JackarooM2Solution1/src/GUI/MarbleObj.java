package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.Colour;
import model.player.Marble;


	public class MarbleObj extends Launcher {
		Marble marble;
		Button button ;
		boolean selected;
		Launcher launcher;
		public MarbleObj(Marble marble, Launcher launcher) {
			this.launcher= launcher;
			this.marble = marble;
			Color c = convertToColor(marble.getColour());
			Image image = new Image("transparent-red-circle-empty-red-circle-in-monochromatic-design-unclear662606c62a0cb0.32396425.webp");
			ImageView v= new ImageView(image);
			button = new Button();
			if(marble.getColour()==Colour.RED){
				button.setStyle("-fx-background-color:red;");
				
			}
			if(marble.getColour()==Colour.BLUE){
				button.setStyle("-fx-background-color:royalblue;");
				
			}
			if(marble.getColour()==Colour.GREEN){
				button.setStyle("-fx-background-color:green;");
				
			}
			if(marble.getColour()==Colour.YELLOW){
				button.setStyle("-fx-background-color:yellow;");
				
			}
		button.setPrefWidth(10);
			button.setPrefHeight(15);

			button.setGraphic(v);
			selected= false;
			
			
			button.setOnAction(new EventHandler <ActionEvent> (){
				
				public void handle (ActionEvent event){
							launcher.selectMarble(getMarbleObject()); 
//									layout.getChildren().add(play);
							}
			   });
		}
		public MarbleObj getMarbleObject(){
			return this;
		}
		public Button getButton() {
			return button;
		}
		public void setButton(Button b){
			button = b;
		}
		public void setSelected(boolean f){
			selected = f;
		}
		public boolean isSelected(){
			return selected;
		}
		public Marble getMarble(){
			return marble;
		}
		
		
}
