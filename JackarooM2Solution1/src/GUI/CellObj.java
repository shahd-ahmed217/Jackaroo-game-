package GUI;

import model.Colour;

import com.sun.javafx.geom.Rectangle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import engine.board.Cell;
import engine.board.CellType;

public class CellObj {
	Cell cell;
	Circle circle;
	//Rectangle square;
	MarbleObj marbleobj ;
	public CellObj(Cell cell, Launcher launcher) {
		this.cell=cell;
		if (cell.getMarble()!=null) {
			marbleobj = new MarbleObj(cell.getMarble(), launcher);
		}
		else {
			circle = new Circle(7, Color.BLACK);
			///square = new Rectangle(30,30);
//			if (cell.getCellType()==CellType.SAFE)
//				circle.setFill(convertToColor(cell.getMarble().getColour()));
		}
	}
	public Circle getCircle() {
		return circle;
	}
	/*public Rectangle getSquare(){
		return square;
	}*/
	/*public void setCircle(Circle circle) {
		this.circle = circle;
	}*/
	public MarbleObj getMarbleobj() {
		return marbleobj;
	}
	public void setMarbleobj(MarbleObj marbleobj) {
		this.marbleobj = marbleobj;
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
	public Cell getCell() {
		// TODO Auto-generated method stub
		return cell;
	}
	
}
