package model.player;

import java.util.ArrayList;


import exception.GameException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import model.Colour;
import model.card.Card;

@SuppressWarnings("unused")
public class Player {
    private final String name;
    private final Colour colour;
    private ArrayList<Card> hand;
    private final ArrayList<Marble> marbles;
    private Card selectedCard;
	private final ArrayList<Marble> selectedMarbles;

    public Player(String name, Colour colour) {
        this.name = name;
        this.colour = colour;
        this.hand = new ArrayList<>();
        this.selectedMarbles = new ArrayList<>();
        this.marbles = new ArrayList<>();
        
        for (int i = 0; i < 4; i++) {
            this.marbles.add(new Marble(colour));
        }
        
        //default value
        this.selectedCard = null;
    }

    public String getName() {
        return name;
    }

    public Colour getColour() {
        return colour;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
    
    public ArrayList<Marble> getMarbles() {
		return marbles;
	}
    
    public Card getSelectedCard() {
        return selectedCard;
    }
    
    public void regainMarble(Marble marble) {
        this.marbles.add(marble);
    }

    public Marble getOneMarble() {
        if(marbles.isEmpty())
            return null;

        return this.marbles.get(0);
    }

    public void selectCard(Card card) throws InvalidCardException {
        if (!this.hand.contains(card)) {

  			InvalidCardException e= new InvalidCardException("Card not in hand.");


            throw new InvalidCardException("Card not in hand.");


        }
        
        this.selectedCard = card;
    }

    public void selectMarble(Marble marble) throws InvalidMarbleException {
        if (!this.selectedMarbles.contains(marble)) {
            if(this.selectedMarbles.size() > 1){
           	InvalidMarbleException e= new InvalidMarbleException("Cannot select more than 2 marbles.");

            	throw  new InvalidMarbleException("Cannot select more than 2 marbles.");

            }
            
            selectedMarbles.add(marble);
        }
    }
    public void deselectMarble(Marble marble) {
        if (this.selectedMarbles.contains(marble)) {
            for (int i=0; i<selectedMarbles.size(); i++){
            	if (selectedMarbles.get(i)==marble)
            		selectedMarbles.remove(i);
            }
        }
    }
   

    public void deselectAll() {
        this.selectedCard = null;
        this.selectedMarbles.clear();
    }
    public void deselectCard (){
    	
    		selectedCard= null;
    	
    }

    public void play() throws GameException {
        if(selectedCard == null){
        

            InvalidCardException e = new InvalidCardException("Must select a card to play.");


        	throw new InvalidCardException("Must select a card to play.");



        	
        }

        
        if(!this.selectedCard.validateMarbleSize(this.selectedMarbles)){
       

     		InvalidMarbleException e= new InvalidMarbleException("Invalid number of marbles selected for " + selectedCard.getName() + ".");

             throw   new InvalidMarbleException("Invalid number of marbles selected for " + selectedCard.getName() + ".");

        	
        }

        
        if(!this.selectedCard.validateMarbleColours(this.selectedMarbles)){
       

    	InvalidMarbleException e= new InvalidMarbleException("Invalid marble colours selected for " + selectedCard.getName() + ".");

            throw new InvalidMarbleException("Invalid marble colours selected for " + selectedCard.getName() + ".");

       	
        	
        }
      

        
        this.selectedCard.act(this.selectedMarbles);
  
        
 
    	
    		
    }
    
  

}
