package GUI;

import java.util.ArrayList;

import model.player.Player;
import javafx.scene.shape.Rectangle;

public class Piece {
	  
	    ArrayList <MarbleObj> marbleObjects= new ArrayList <MarbleObj>();
	  
	    public Piece(Player p, Launcher launcher){
	    	
	    	for (int i=0; i<p.getMarbles().size() ; i++){
	    		marbleObjects.add(new MarbleObj(p.getMarbles().get(i), launcher));
	    	}
	    		
	  
	    }

		public ArrayList<MarbleObj> getMarbleObjects() {
			return marbleObjects;
		}

	
	    
	   
}
