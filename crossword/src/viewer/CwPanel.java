package viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Crossword;
import model.CwEntry;

public class CwPanel extends JPanel{
	
	public final int SQUARE_SIDE = 25;
	private Crossword actualCw;
	Iterator<CwEntry> iter ;
	CwEntry tmp;
	L
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * Paints all the componnets on the CwPanel
	 */
public void paintComponent(Graphics g){
		
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	
/*	if(this.actualCw != null){		
		this.iter = actualCw.getROEntryIter();
		while (iter.hasNext()){
			tmp = iter.next();
			
			g2.drawRect( tmp.getX()*SQUARE_SIDE + CrosswordView.FRAME_WIDTH/2, tmp.getY()*SQUARE_SIDE + CrosswordView.FRAME_HIGHT/2, SQUARE_SIDE, SQUARE_SIDE);
			g2.drawRect( tmp.getX()*SQUARE_SIDE , tmp.getY()*SQUARE_SIDE , SQUARE_SIDE, SQUARE_SIDE);
		}
	}*/
	if(actualCw != null){
	for(int col = 0; col < actualCw.getBoard().getWidth(); col ++)
		for(int row = 0; row < actualCw.getBoard().getHeight(); row ++){
			if(actualCw.getBoard().getCell(row, col).checkContent()){
				g2.setColor(Color.BLACK);
				g2.drawRect( (col + 1)*SQUARE_SIDE + CrosswordView.FRAME_WIDTH/2 - SQUARE_SIDE*actualCw.getBoard().getWidth()/2,
						(row + 1)*SQUARE_SIDE, SQUARE_SIDE, SQUARE_SIDE);
				this.repaint();

			}
		}
	}
}
	
public CwPanel(){
		
		this.setBorder(BorderFactory.createTitledBorder("Wygenerowana krzyzowka..."));
		this.setLayout(null);
		
	}
	
public void printSolvable(){
	if(actualCw != null){
		for(int col = 0; col < actualCw.getBoard().getWidth(); col ++)
			for(int row = 0; row < actualCw.getBoard().getHeight(); row ++){
				if(actualCw.getBoard().getCell(row, col).checkContent()){
					//g2.drawRect( (col + 1)*SQUARE_SIDE + CrosswordView.FRAME_WIDTH/2 - SQUARE_SIDE*actualCw.getBoard().getWidth()/2,
						//(row + 1)*SQUARE_SIDE, SQUARE_SIDE, SQUARE_SIDE);
					this.repaint();

				}
			}
		}
}
public void setActualCw( Crossword actualCw){
	
	this.actualCw = actualCw;
	
}
	
}
