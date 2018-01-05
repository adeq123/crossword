package viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import FitedExeptions.NoActualCw;
import model.Crossword;
import model.CwEntry;
import model.*;
public class CwPanel extends JPanel{

    public final int SQUARE_SIDE = 40;
    private Crossword actualCw;
    Iterator<CwEntry> iter ;
    CwEntry tmp;
    LinkedList <JFormattedTextField> listOfTextFields;
    JScrollPane pane;
    JPanel newPanel;
    private static final long serialVersionUID = 1L;
    int clueSpacing = 15;


    /**
     * Paints all the componnets on the CwPanel
     */
    public void paintComponent(Graphics g){

	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;

	ListIterator<CwEntry> cwEntryIter;
	CwEntry tmp;

	int vertCounter = 1;
	int horCounter = 1;


	//print numbers by the entries
	if(actualCw != null){
	    cwEntryIter = actualCw.getROEntryIter();
	    while(cwEntryIter.hasNext()){
		tmp = cwEntryIter.next();
		if(tmp.getD() == Direction.HORIZ){
		    g2.drawString(Integer.toString(vertCounter), (tmp.getX() + 1)*SQUARE_SIDE + CrosswordView.FRAME_WIDTH/2 - SQUARE_SIDE*actualCw.getBoard().getWidth()/2 - SQUARE_SIDE/2 + SQUARE_SIDE/6,
			    (tmp.getY() + 1)*SQUARE_SIDE + SQUARE_SIDE*2/3);
		    vertCounter++;


		}else{

		    g2.drawString(Integer.toString(horCounter), (tmp.getX() + 1)*SQUARE_SIDE + CrosswordView.FRAME_WIDTH/2 - SQUARE_SIDE*actualCw.getBoard().getWidth()/2 + SQUARE_SIDE/2,
			    (tmp.getY() + 1)*SQUARE_SIDE - SQUARE_SIDE/6);
		    horCounter++;			

		}		
	    }

	    drawCwSquares(g2);
	    printOutClues(g2, cwEntryIter);


	}
    }

    /**
     * This method prints the crosswords' squares on the panel
     * @param g2, the object used to print the squares on the 
     */
    public void drawCwSquares(Graphics2D g2){

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

    public void printOutClues(Graphics2D g2, ListIterator <CwEntry> cwEntryIter){

	int horIncrement = 1;
	int verIncrement = 1;

	if(actualCw.noOfHorEntries() > 0)
	    g2.drawString("Horizontal", 25, actualCw.getBoardCopy().getHeight()*SQUARE_SIDE + SQUARE_SIDE*4); 	

	if(actualCw.noOfVerEntries() > 0)
	    g2.drawString("Vertical", 25, actualCw.getBoardCopy().getHeight()*SQUARE_SIDE + SQUARE_SIDE*4 + actualCw.noOfHorEntries()*clueSpacing+10); 

	cwEntryIter = actualCw.getROEntryIter();
	while(cwEntryIter.hasNext()){
	    tmp = cwEntryIter.next();
	    if(tmp.getD() == Direction.HORIZ){
		g2.drawString(Integer.toString(horIncrement)+". "+tmp.getClue(), 25, actualCw.getBoardCopy().getHeight()*SQUARE_SIDE + SQUARE_SIDE*4 + horIncrement*clueSpacing);
		horIncrement++;
	    }else{
		g2.drawString(Integer.toString(verIncrement)+". "+tmp.getClue(), 25, actualCw.getBoardCopy().getHeight()*SQUARE_SIDE + SQUARE_SIDE*4 + verIncrement*clueSpacing + actualCw.noOfHorEntries()*clueSpacing+10);
		verIncrement++;
	    }

	}

    }

    public CwPanel(){


	this.setBorder(BorderFactory.createTitledBorder("Generated crossword."));
	this.setLayout(null);
	this.setBackground(Color.WHITE);

    }

    public void printSolvable() throws ParseException{
	if(listOfTextFields != null)
	    this.removeAll();
	listOfTextFields = new LinkedList <JFormattedTextField>();
	JFormattedTextField tmpTextField;
	if(actualCw != null){
	    for(int col = 0; col < actualCw.getBoard().getWidth(); col ++)
		for(int row = 0; row < actualCw.getBoard().getHeight(); row ++){
		    if(actualCw.getBoard().getCell(row, col).checkContent()){
			tmpTextField = new JFormattedTextField(new MaskFormatter("U"));
			listOfTextFields.add(tmpTextField);
			tmpTextField.setEditable(true);
			tmpTextField.setBounds( (col + 1)*SQUARE_SIDE + CrosswordView.FRAME_WIDTH/2 - SQUARE_SIDE*actualCw.getBoard().getWidth()/2,
				(row + 1)*SQUARE_SIDE, SQUARE_SIDE, SQUARE_SIDE);
			tmpTextField.setHorizontalAlignment(SwingConstants.CENTER);

			this.add(tmpTextField);

			this.repaint();

		    }
		}
	}
    }

    public void printSolved(){
	ListIterator <JFormattedTextField> txtFieldsIter = listOfTextFields.listIterator();
	JFormattedTextField tmpTextField;

	if(actualCw != null){
	    for(int col = 0; col < actualCw.getBoard().getWidth(); col ++)
		for(int row = 0; row < actualCw.getBoard().getHeight(); row ++){
		    if(actualCw.getBoard().getCell(row, col).checkContent()){
			tmpTextField = txtFieldsIter.next();
			if(actualCw.getBoardCopy().getCell(row, col).getContent().equalsIgnoreCase(tmpTextField.getText())){
			    tmpTextField.setForeground(Color.GREEN);
			}else{
			    tmpTextField.setValue(actualCw.getBoardCopy().getCell(row, col).getContent());
			    tmpTextField.setForeground(Color.RED);
			}

			tmpTextField.setEditable(false);


		    }
		}
	}
    }
    public void setActualCw( Crossword actualCw){

	this.actualCw = actualCw;
	int newHeight = actualCw.getBoardCopy().getHeight()*SQUARE_SIDE + SQUARE_SIDE*4 + (actualCw.noOfHorEntries() + actualCw.noOfVerEntries())*clueSpacing 
		+ actualCw.noOfHorEntries()*clueSpacing+100;
	setPreferredSize(new Dimension(this.getPreferredSize().width, newHeight));
	revalidate();
	repaint();

    }



}
