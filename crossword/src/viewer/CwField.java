package viewer;

import java.awt.Rectangle;

import javax.swing.JTextField;

public class CwField{

	private Rectangle.Double rect;
	private JTextField input;
	private boolean filling;
	private int boardRow, boardCol;
	
	public CwField(double x, double y, double ss, boolean filling, int boardRow, int boardCol){
		
		rect = new Rectangle.Double(x, y, ss, ss);
		input = new JTextField(1);
		this.boardRow = boardRow;
		this.boardCol = boardCol;
		
	}
	
	public boolean isFiled(){
		return filling;
	}
	
	public Rectangle.Double getRectangle(){
		return rect;
	}
	
	public JTextField getTextField(){
		return input;
	}
}
