package GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import engine.Game;
import engine.board.Cell;
import exception.CannotFieldException;
import exception.GameException;
import exception.IllegalDestroyException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import exception.SplitOutOfRangeException;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Colour;
import model.card.Card;
import model.card.standard.Seven;
import model.card.standard.Standard;
import model.card.wild.Burner;
import model.card.wild.Saver;
import model.player.Player;

public class Launcher extends Application {

    ArrayList<CardObj> cardobjects = new ArrayList<CardObj>();
	Game game;
	StackPane layout;
	ArrayList<Button> buttons = new ArrayList<Button> ();
	ArrayList <Piece> pieces= new ArrayList <Piece>();
	ArrayList <Pane> cards= new ArrayList <Pane>();
	StackPane board;
	Button play = new Button ("Play!");
	Button pass = new Button ("Pass");

	int globalMarbleCounter =0;
	int globalCardCounter=0;
	//the physical representation of pieces;
	Group rectangles;
	Group profiles;
	HBox p0cards, p3cards = new HBox(10);
	VBox p1cards, p2cards= new VBox(10);
	ArrayList <MarbleObj> hz1, hz2, hz3, hz4= new ArrayList<MarbleObj>();
	

	public void start(Stage primaryStage) throws Exception {
	
		//Prompt player's name
		
		Button submitButton = new Button("Submit");
		TextField inputField = new TextField();
		Label messageLabel = new Label("please enter your name");
		VBox nameEntry = new VBox(10, inputField, submitButton, messageLabel);
		Scene scene = new Scene(nameEntry);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//start the game;
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
	        	 String name = inputField.getText();
	             messageLabel.setText("Welcome, " + name + "!");
	             try {
					game = new Game (name);
					layout = new StackPane();
					rectangles = new Group();
					profiles = new Group();
					Colour currentplayercolour = game.getActivePlayerColour();
					Colour nextplayercolor = game.getNextPlayerColour();
					updateScene();
					Scene s = new Scene(layout);
					s.setFill(Color.GOLD);
					primaryStage.setScene(s);		
					primaryStage.show();
				
					
					
					s.setOnKeyPressed(event -> {
					    if (event.isControlDown() && event.getCode() == KeyCode.S) {
					    
					        try {
					        game.fieldMarble();
					        updateScene();

							s.setRoot(layout);
							s.setFill(Color.GOLD);
							

							
							primaryStage.setScene(s);		
							primaryStage.show();
					        
					        
					        
					        } 
					        catch (Exception f ){
					        	
					        }
					        // Perform save action
					    }
					});
					

					play.setOnAction(new EventHandler <ActionEvent> (){
						
						public void handle (ActionEvent event){
							try {
								if (game.canPlayTurn() && game.getPlayers().get(0).getHand().size()!=0)
								{
									game.playPlayerTurn();
									game.endPlayerTurn();
									
									}
								else {
									game.endPlayerTurn();
								}
								updateScene();
								s.setRoot(layout);	
								s.setFill(Color.GOLD);
								primaryStage.setScene(s);	
								primaryStage.show();	
								

								if (game.checkWin()!=null){

							    displayAlert("WINNER!!", "Player"+game.checkWin()+"Wins!");
							    
									
								}

								playTurns(1,s,primaryStage);
							}	
							catch(GameException e ){	
								
								displayAlert("Game Exception", e.getMessage());
								if (game.getPlayers().get(0).getSelectedCard()!=null){
									game.discardSpecificCard(game.getPlayers().get(0).getSelectedCard());
								game.endPlayerTurn();
									updateScene();
								
									s.setRoot(layout);	
								
								s.setFill(Color.GOLD);
								primaryStage.setScene(s);	
								primaryStage.show();	
								playTurns(1,s,primaryStage);
									
								}

								}
							
							
							

						}
					}); 
					
				pass.setOnAction(new EventHandler <ActionEvent> (){
					public void handle(ActionEvent event){
						game.passTurn();
						game.endPlayerTurn();
						updateScene();
						s.setRoot(layout);
						primaryStage.setScene(s);	
						primaryStage.show();
						try{
							playTurns(1,s,primaryStage);
						}
						catch (Exception e){
							
						}
				} }); }
				
				catch (IOException e1) {
				
				}
	     } } ) ;
	}
	
	public void updateScene(){
		layout.getChildren().clear();
		

		if (game.getFirePit().size()>0){
		if (game.getFirePit().get(game.getFirePit().size()-1) instanceof Saver){
			displayQuickAlert("Saver Card in Action!", " A marble has been saved!!");
		}
		if (game.getFirePit().get(game.getFirePit().size()-1) instanceof Burner){
			displayQuickAlert("Burner Card in Action!", " A marble has been returned to its homezone!!");
		}
		
		
		
		
		
		
		
		
		
		
		}
		
		board = board(game.getFirePit(),game.getPlayers());
		
		updateRectangles();
		updateProfiles();
		updateHomeZones();
		//updateHumanPlayerCardObjects();
		layout.getChildren().addAll(board,rectangles,profiles, play, pass);
		layout.setStyle("-fx-background-color: gold;");

		play.setTranslateY(150);
		pass.setTranslateY(180);


		
	}
	public StackPane createPiece(double width, double height,Color marble,Color now,Color next, int i) {
		
	    Rectangle rect = new Rectangle(width, height);

	    Piece piece = new Piece(game.getPlayers().get(i),this);
	    pieces.add(piece);
	    if (game.getPlayers().get(i).getColour()==Colour.RED)
	    	rect.setFill(Color.web("#FADBD8"));
	    if (game.getPlayers().get(i).getColour()==Colour.BLUE)
	    	rect.setFill(Color.web("#D6EAF8"));
	    if (game.getPlayers().get(i).getColour()==Colour.YELLOW)
	    	rect.setFill(Color.web("#FCF3CF"));
	    if (game.getPlayers().get(i).getColour()==Colour.GREEN)
	    	rect.setFill(Color.web("#D5F5E3"));
	    

	    VBox v = new VBox (2);
	    HBox h1 = new HBox(2);
	    HBox h2 = new HBox(2);
	    
	    for (int j =0; j<piece.getMarbleObjects().size(); j++){
	    	if (j<2)
	    		h2.getChildren().add(piece.getMarbleObjects().get(j).getButton());
	    	else
	    		h1.getChildren().add(piece.getMarbleObjects().get(j).getButton());	
	    }

		 v.getChildren().add(h1);
		 v.getChildren().add(h2);
		 v.setAlignment(Pos.CENTER);
		 h1.setAlignment(Pos.CENTER);
		 h2.setAlignment(Pos.CENTER);

		
    
		 Circle activeplayer = new Circle (15);
		 activeplayer.setFill(Color.WHITE);
    
		 Circle nextplayer = new Circle (15);
		 nextplayer.setFill(Color.CHOCOLATE);
    
		 StackPane stack = new StackPane(rect, v);
		 if (now==marble) {
			 stack.getChildren().add(activeplayer);
			 Label l1=new Label ("‖");
			 l1.setFont(Font.font(24));
			 l1.setTextFill(Color.GRAY);
			 stack.getChildren().add(l1);
		 }
		 if (next==marble) {
			 stack.getChildren().add(nextplayer);
			 Label l2=new Label ("⏵");
			 l2.setFont(Font.font(20));
			 l2.setTextFill(Color.BROWN);

			 stack.getChildren().add(l2);

		 }
		 return stack;
	}
	
	
	public StackPane Profile(double width, double height,Color c , String s) {
		Label p1 = new Label (" "+s+" ");
		p1.setTextFill(c);
		 BorderStroke borderStroke = new BorderStroke(c,BorderStrokeStyle.SOLID,new CornerRadii(5),  new BorderWidths(2));
	        // Set the border on the label
	    p1.setBorder(new Border(borderStroke));
		p1.setBorder(new Border(borderStroke));
		p1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 21));
		StackPane out = new StackPane();
		out.getChildren().add(p1);
	return out;
	}
	
	public void selectMarble (MarbleObj m){
		if (!m.isSelected()){
			try {
			game.getPlayers().get(0).selectMarble(m.getMarble());
			m.getButton().setStyle("-fx-background-color:brown;");
			m.setSelected(true);
			m.getButton().setPrefWidth(20);
			m.getButton().setPrefHeight(20);
			}
			catch (InvalidMarbleException e){
				//System.out.print("Invalid Marble");
			    displayAlert("Invalid Marble!", e.getMessage());
				updateScene();
				Scene scene= new Scene(layout);	
				scene.setFill(Color.GOLD);
				Stage primaryStage = new Stage();
				primaryStage.setScene(scene);	
				primaryStage.show();	
				
			} 
		}
		else{
			game.getPlayers().get(0).deselectMarble(m.getMarble());
			if(m.getMarble().getColour()==Colour.RED){
				m.getButton().setStyle("-fx-background-color:red;");
				
			}
			if(m.getMarble().getColour()==Colour.BLUE){
				m.getButton().setStyle("-fx-background-color:royalblue;");
				
			}
			if(m.getMarble().getColour()==Colour.GREEN){
				m.getButton().setStyle("-fx-background-color:green;");
				
			}
			if(m.getMarble().getColour()==Colour.YELLOW){
				m.getButton().setStyle("-fx-background-color:yellow;");
				
			}
			//m.getButton().setStyle("");
			m.getButton().setPrefWidth(10);
			m.getButton().setPrefHeight(15);
			m.setSelected(false);
		}
	} 
	
	public void selectCard (CardObj m) {
		if (!m.isSelected()){
			try {

				game.getPlayers().get(0).selectCard(m.getCard());
				m.getButton().setStyle("-fx-background-color:lightblue;");

				if (m.getCard() instanceof Seven){  
					  Stage alertStage = new Stage();
					  TextField split= new TextField();
				        alertStage.setTitle("SetSplitDistance");
				        Button setButton = new Button("Set");
				        setButton.setOnAction(event -> {
				        try{
				        	game.editSplitDistance(Integer.parseInt(split.getText()));
				        }
				      catch(Exception e){
			        
			        }
				        
				        alertStage.close();});

				        BorderPane pane = new BorderPane();
				        pane.setCenter(split);
				        pane.setBottom(setButton);

				        Scene scene = new Scene(pane, 500, 100);
				        alertStage.setScene(scene);
				        alertStage.showAndWait();
				}
				m.setSelected(true);
			}
				
			catch (InvalidCardException e){

			} 
		}
		else{
			game.deselectCard();
			m.getButton().setStyle("");
			m.setSelected(false);
		}
						
	}

	public void updateRectangles(){
		rectangles= new Group();
		for ( int i =0 ;i <4 ;i++) {
			Color c = convertToColor(game.getPlayers().get(i).getColour());
		
		
			StackPane r = createPiece(100,100,c,convertToColor(game.getActivePlayerColour()),convertToColor(game.getNextPlayerColour()),i);
		
			if (i==1) {
				r.setTranslateX(750);
			}
			if (i==2) {
				r.setTranslateY(750);
			}
			if (i==3) {
				r.setTranslateX(750);
				r.setTranslateY(750);
			}
			rectangles.getChildren().add(r);
		}
	}
	public void updateProfiles(){
		profiles = new Group();
		for ( int i =0 ; i < 4 ;i++) {
			
			String n = (game.getPlayers().get(i).getName());
			Color c = convertToColor(game.getPlayers().get(i).getColour());
			StackPane profile = Profile(100,50,c,n);
			if (i==0) {
				profile.setTranslateX(100);
			}
			if (i==1) {
				profile.setTranslateX(650);
			}
			if (i==2) {
				profile.setTranslateX(100);
				profile.setTranslateY(750);
			}
			if (i==3) {
				profile.setTranslateX(650);
				profile.setTranslateY(750);
			}
			profiles.getChildren().add(profile);
			
		}
	}
	
	public void updateHomeZones(){
		if (pieces.size()!=0){
			hz1= pieces.get(0).getMarbleObjects();
			hz2= pieces.get(1).getMarbleObjects();
			hz3= pieces.get(2).getMarbleObjects();
			hz4= pieces.get(3).getMarbleObjects(); 
		}
	}
	
	public StackPane board(ArrayList<Card> display,ArrayList<Player> players) {
		StackPane board =  new StackPane();

		//The board itself
		Rectangle rsmall = new Rectangle();
		rsmall.setWidth(500);
		rsmall.setHeight(500);
		rsmall.setFill(Color.ORANGE);
		
		//The firepit 
		Rectangle firepitcard = new Rectangle(100,100);
		firepitcard.setFill(Color.PINK);
		Button looks= new Button();
		looks.setPrefWidth(50);
		looks.setPrefHeight(90);

	
		looks.setStyle("-fx-background-color:hotpink");
		Label cardname = new Label();
		int size = display.size();
		if (size-1!=-1) {
			cardname.setText(display.get(size-1).getName());
		}
		
		//The cells
		ArrayList<CellObj> cellobjects =  new ArrayList<CellObj>();
		VBox vleft = new VBox(5);
		VBox vright = new VBox(5);
		HBox htop = new HBox(5);
		HBox hbottom = new HBox(5);
		ArrayList<Cell> track = game.getBoard().getTrack();
		int j =0;
		for(int i = 0 ; i <25 ;i++) {
			CellObj left = new CellObj(track.get(j), this);
			CellObj right = new CellObj(track.get(74-j), this);
			CellObj top = new CellObj(track.get(99-j), this);
			CellObj bottom = new CellObj(track.get(j+25), this);

			
			j++;
			
			
			if (left.getCircle()!=null) {
				vleft.getChildren().add(left.getCircle());
			}
			else {
				if (left.getCell()!=null && left.getCell().isTrap()){
			       displayAlert("TrapCell", "Marble Has Been Fielded");
			       updateScene();
					Scene scene= new Scene(layout);	
					scene.setFill(Color.GOLD);
					Stage primaryStage = new Stage();
					primaryStage.setScene(scene);	
					primaryStage.show();	

					}
					else
				vleft.getChildren().add(left.getMarbleobj().getButton());
			}
			if (right.getCircle()!=null) {
				vright.getChildren().add(right.getCircle());
			}
			else {	if (right.getCell()!=null &&right.getCell().isTrap()){
			       displayAlert("TrapCell", "Marble Has Been Fielded");
			       updateScene();
					Scene scene= new Scene(layout);	
					scene.setFill(Color.GOLD);
					Stage primaryStage = new Stage();
					primaryStage.setScene(scene);	
					primaryStage.show();	


				}
				else
				vright.getChildren().add(right.getMarbleobj().getButton());
			}
			if (top.getCircle()!=null) {
				htop.getChildren().add(top.getCircle());
			}
			else {
				if (top.getCell()!=null &&top.getCell().isTrap()){
				       displayAlert("TrapCell", "Marble Has Been Fielded");
				       updateScene();
						Scene scene= new Scene(layout);	
						scene.setFill(Color.GOLD);
						Stage primaryStage = new Stage();
						primaryStage.setScene(scene);	
						primaryStage.show();	


					}
					else
				htop.getChildren().add(top.getMarbleobj().getButton());
			}
			if (bottom.getCircle()!=null) {
				hbottom.getChildren().add(bottom.getCircle());
			}
			else {
				
				if (bottom.getCell()!=null && bottom.getCell().isTrap()){
				       displayAlert("TrapCell", "Marble Has Been Fielded");
				       updateScene();
						Scene scene= new Scene(layout);	
						scene.setFill(Color.GOLD);
						Stage primaryStage = new Stage();
						primaryStage.setScene(scene);	
						primaryStage.show();	


					}
					else
				hbottom.getChildren().add(bottom.getMarbleobj().getButton());
			}
		}
					
		HBox player1sz= new HBox(6);
		VBox player2sz= new VBox(6);
		HBox player4sz= new HBox(6);
		VBox player3sz= new VBox(6);
		ArrayList <Cell> safeZone1= game.getBoard().getSafeZones().get(0).getCells();
		ArrayList <Cell> safeZone2= game.getBoard().getSafeZones().get(1).getCells();
		ArrayList <Cell> safeZone3= game.getBoard().getSafeZones().get(2).getCells();
		ArrayList <Cell> safeZone4= game.getBoard().getSafeZones().get(3).getCells();
		j=0;
		for(int i = 0 ; i <4 ;i++) {
			CellObj safe1 = new CellObj(safeZone1.get(j), this);
			CellObj safe2 = new CellObj(safeZone2.get(j), this);
			CellObj safe3 = new CellObj(safeZone3.get(j), this);
			CellObj safe4 = new CellObj(safeZone4.get(j), this);
			j++;
			
			
			if (safe1.getCircle()!=null) {
				player1sz.getChildren().add(safe1.getCircle());
				safe1.getCircle().setFill(convertToColor(game.getPlayers().get(0).getColour()));

			}
			else {
				player1sz.getChildren().add(safe1.getMarbleobj().getButton());
			}
			if (safe2.getCircle()!=null) {
				player2sz.getChildren().add(safe2.getCircle());
				safe2.getCircle().setFill(convertToColor(game.getPlayers().get(1).getColour()));



			}
			else {
					player2sz.getChildren().add(safe2.getMarbleobj().getButton());			}
			if (safe3.getCircle()!=null) {
				player3sz.getChildren().add(safe3.getCircle());
				safe3.getCircle().setFill(convertToColor(game.getPlayers().get(2).getColour()));




			}
			else {
				
					player3sz.getChildren().add(safe3.getMarbleobj().getButton());			}
			if (safe4.getCircle()!=null) {
				player4sz.getChildren().add(safe4.getCircle());
				safe4.getCircle().setFill(convertToColor(game.getPlayers().get(3).getColour()));

			}
			else {
				
					player4sz.getChildren().add(safe4.getMarbleobj().getButton());				}
		}
		player1sz.setTranslateY(210);
		player1sz.setTranslateX(255);
		player2sz.setTranslateX(665);
		player2sz.setTranslateY(230);
		player3sz.setTranslateX(270);
		player3sz.setTranslateY(580);
//		player3sz.setTranslateX(230);
		player4sz.setTranslateY(630);
		player4sz.setTranslateX(620);
		Group safes= new Group();
		safes.getChildren().addAll(player1sz,player2sz,player4sz,player3sz);
		Group cells = new Group();
		cells.getChildren().addAll(vleft,vright,htop,hbottom);
		vleft.setTranslateX(175);
		vleft.setTranslateY(190);
		vright.setTranslateX(665);
		vright.setTranslateY(190);
		htop.setTranslateY(170);
		htop.setTranslateX(190);
		hbottom.setTranslateY(670);
		hbottom.setTranslateX(190);
		Group cards = new Group();
		HBox p0cards = new HBox(10);
		p0cards.setTranslateY(0);
		p0cards.setTranslateX(230);
		cardobjects.clear();
		
		//The cards
		if(players.get(0).getHand().size()==0){
			p0cards.getChildren().add(new Label ("Oops! You're out of cards. Pass till you're filled in!"));

		}
		else{
		for ( int i =0 ; i < players.get(0).getHand().size();i++) {
			Card c = players.get(0).getHand().get(i);
			CardObj cardobj = new CardObj(c, this);
			p0cards.getChildren().add(cardobj.getButton());
			cardobjects.add(cardobj);
		}}
		VBox p1cards = new VBox(10);
		p1cards.setTranslateX(765);
		p1cards.setTranslateY(200);
		
		for ( int i =0 ; i < players.get(1).getHand().size();i++) {
			Card c = players.get(1).getHand().get(i);
			if (c!=null) {
				Button b=new Button("🔒");
				b.setStyle(getColourFill(players.get(1).getColour()));
				b.setTextFill(Color.WHITE);

		//		Label label = new Label("serr");
				p1cards.getChildren().add(b);
			}
		}
		VBox p2cards = new VBox(10);
		p2cards.setTranslateX(75);
		p2cards.setTranslateY(200);
		
		for ( int i =0 ; i < players.get(2).getHand().size();i++) {
			Card c = players.get(2).getHand().get(i);
			if (c!=null) {
				//Label label = new Label("m4 ha2olak");
				Button b=new Button("🔒");
				b.setStyle(getColourFill(players.get(2).getColour()));
				b.setTextFill(Color.WHITE);

				p2cards.getChildren().add(b);
			}
		}
		HBox p3cards = new HBox(10);
		p3cards.setTranslateY(700);
		p3cards.setTranslateX(160);
		
		for ( int i =0 ; i < players.get(3).getHand().size();i++) {
			Card c = players.get(3).getHand().get(i);
			if (c!=null) {
			
				Button b=new Button("🔒");
				b.setStyle(getColourFill(players.get(3).getColour()));
				b.setTextFill(Color.WHITE);
				p3cards.getChildren().add(b);
			}
		}
		
		cards.getChildren().addAll(p0cards,p1cards,p2cards,p3cards);
	
		board.getChildren().addAll(rsmall, cells,firepitcard,looks, cardname,cards, safes);
		
	
		return board;
	}
	
	public static Color convertToColor (Colour c){
		switch (c){
		case RED:
			return Color.RED;
		case BLUE:
			return Color.BLUE;
		case GREEN:
			return Color.GREEN;
		case YELLOW:
			return Color.YELLOW;
		default:
			return Color.ALICEBLUE;
		}
	}
	
	public static String getColourFill (Colour c){
		switch (c){
		case RED:
			return "-fx-background-color:maroon";
		case BLUE:
			return "-fx-background-color:navy";
		case GREEN:
			return "-fx-background-color:darkgreen";
		case YELLOW:
			return "-fx-background-color:orange";
		default:
			return "-fx-background-color:darkgray";
		}
	}
	
	public static void main(String[]args){
			launch(args);
	}

	private void playTurns(int i, Scene s , Stage primaryStage) {
	    if (i < 4) {
	        PauseTransition pause = new PauseTransition(Duration.seconds(2)); // pause of 2 seconds
	        pause.setOnFinished(e -> {
	            try {
					game.playPlayerTurn();
				} catch (GameException e1) {
				    displayAlert("Invalid Action!", e1.getMessage());
				    updateScene();
					Scene scene= new Scene(layout);	
					scene.setFill(Color.GOLD);
					//primaryStage = new Stage();
					primaryStage.setScene(scene);	
					primaryStage.show();	
				}
	            game.endPlayerTurn();
	            updateScene();
	            s.setRoot(layout);
	            s.setFill(Color.GOLD);
	            primaryStage.setScene(s);
	            primaryStage.show();

	            if (game.checkWin() != null) {
				    displayAlert("WINNER!!", "Player"+game.checkWin()+"Wins!");
				    updateScene();
					Scene scene= new Scene(layout);	
					scene.setFill(Color.GOLD);
					//Stage primaryStage = new Stage();
					primaryStage.setScene(scene);	
					primaryStage.show();	

	            } else {
	                playTurns(i + 1,s,primaryStage); // recurse for next player after the pause
	            }
	        });
	        pause.play();
	    }
	}
	
	 private void displayAlert(String title, String message) {
	        Stage alertStage = new Stage();
	        alertStage.setTitle(title);

	        Label label = new Label(message);
	        Button closeButton = new Button("Close");
	        closeButton.setOnAction(event -> alertStage.close());

	        BorderPane pane = new BorderPane();
	        pane.setTop(label);
	        pane.setCenter(closeButton);

	        Scene scene = new Scene(pane, 500, 100);
	        alertStage.setScene(scene);
	        alertStage.show();
	    }
	 private void displayQuickAlert(String title, String message) {
	        Stage alertStage = new Stage();
	        alertStage.setTitle(title);

	        Label label = new Label(message);
	      
	        BorderPane pane = new BorderPane();
	        pane.setCenter(label);
	 
	        Scene scene = new Scene(pane, 500, 100);
	        alertStage.setScene(scene);
	        alertStage.show();
	    }

}

