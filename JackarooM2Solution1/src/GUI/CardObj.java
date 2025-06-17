package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.card.Card;
import model.card.standard.Standard;
import model.card.standard.Suit;

public class CardObj {
	Button button;
	Card card;
boolean selected;
Launcher launcher;
	public CardObj (Card c, Launcher launcher) {
		this.launcher=launcher;
		selected = false;
		if (c instanceof Standard) {
			Standard s = (Standard)c;
			//button = new Button(s.getName()+" , "+s.getSuit()); 
			button = new Button(getCardRep(s)+" "+getSuitSymbol(s)); 
			button.setPrefWidth(90);
			button.setPrefHeight(50);
			button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
			button.setTextFill(Color.BLACK);

			
		}
		
		
		
		else {
			button = new Button(c.getName()); 
		}
		this.card=c;
		CardObj object= this;
		button.setOnAction(new EventHandler <ActionEvent> (){
		public void handle (ActionEvent event){
			
				launcher.selectCard(getCardObject());
				
		} 
		});
		
		
	}
	
	public String getSuitSymbol (Standard s){
		if (s.getSuit()==Suit.HEART)
			return "♥";
		if (s.getSuit()==Suit.SPADE)
			return "♠";
		if (s.getSuit()==Suit.DIAMOND)
			return "♦";
		if (s.getSuit()==Suit.CLUB)
			return "♣";
		return "";
			
	}
	
	
	public CardObj getCardObject(){
		return this;
	}
	public Button getButton() {
		return button;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public void setButton(Button button) {
		this.button = button;
	}
	public Button updateButton(){
		if (card instanceof Standard) {
			Standard s = (Standard)card;
			button.setText(s.getName()+" , "+s.getSuit()); 
		}
		else {
			button.setText(card.getName()); 
		}
		return button;
	}
	
	public String getCardRep (Standard s){
		if (s.getRank()==11)
			return "J";
		if (s.getRank()==12)
			return "Q";
		if (s.getRank()==13)
			return "K";
		return s.getRank()+"";
			
	}
	
}
