package browser;

import model.Crossword;
import model.CwEntry;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
/**
 * This class model crossword writer which is used to write the crossword to TXT file or generate the PDF file. 
 * @author ADRO
 */
public class CwWriter implements Writer{

    private File cwFolder; 

    /**
     * Constructs new crossword writer.
     * If folder doesn't exist it will be created (including subfolders).
     * @param cwFolderPath, path to folder where crosswords are written. 
     * @throws FileNotFoundException
     */
    public CwWriter (String cwFolderPath) throws FileNotFoundException{
	this.cwFolder = new File(cwFolderPath);
    }

    /**
     * Writes a crossword, with given name, to the file with correct formatting that can read afterwards. 
     * If folder doesn't exist it will be created (including subfolders).
     * @param cw, crossword to be written
     * @param name, name of crossword
     * @throws IOException
     */
    public void WriteCW(Crossword oneCrossword, String name) throws IOException {
	String cwFileName = name;

	if(!cwFolder.exists()){
	    cwFolder.mkdirs();
	}

	File cwTxt = new File(cwFolder.getAbsolutePath()+"\\"+cwFileName+".txt");
	cwTxt.createNewFile();
	BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cwTxt.getAbsolutePath())));
	//write heading  - first line strategy, second size
	writer.write(oneCrossword.getStartegy().toString()+System.lineSeparator());
	writer.write(Integer.toString(oneCrossword.getBoard().getWidth()) + " " + Integer.toString(oneCrossword.getBoard().getHeight()) + System.lineSeparator());
	writer.write("--------------List of words-----------------"+System.lineSeparator());

	ListIterator <CwEntry> entriesIter = oneCrossword.getROEntryIter();
	CwEntry tmpEntry;
	//write the list of words line by line
	while(entriesIter.hasNext()){
	    tmpEntry = entriesIter.next();
	    writer.write(tmpEntry.getWord() + " "  + tmpEntry.getX() + " " + tmpEntry.getY() + " " + tmpEntry.getD() + " " + System.lineSeparator());
	}

	writer.write( "***end***" + System.lineSeparator()+ System.lineSeparator());
	//write the board as it is
	String crosswordBoard = oneCrossword.printBoard();
	String [] boardLineByLine = crosswordBoard.split("\n");
	for(String word : boardLineByLine)
	    writer.write(word+System.lineSeparator());
	writer.close();


    }

    /**
     * Prints the awtImage to pdf file with name fileName
     * @param awtImage awtImage to be printed
     * @param fileName of pdf where image is printed
     * @throws DocumentException 
     * @throws IOException 
     */
    public void printCwToPdf (java.awt.Image awtImage, String fileName) throws DocumentException, IOException{

	Document doc = new Document();
	doc.addTitle("Krzyzowka");
	doc.addCreationDate();
	doc.setMargins(0, 0, 0, 0);
	PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
		fileName));
	doc.open();
	Image iTextImage = Image.getInstance(writer, awtImage, 1);
	iTextImage.setAlignment(0);
	iTextImage.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
	doc.add(iTextImage);
	doc.close();


    }
    
    /**
     * Creates the awt file based on component (ex. JFrame, JPanel)
     * @param component, Component to be printed
     * @return
     */
    public static java.awt.Image getImageFromPanel(Component component) {

	BufferedImage image = new BufferedImage(component.getWidth(),
		component.getHeight(), BufferedImage.TYPE_INT_RGB);
	component.paint(image.getGraphics());
	return image;
    }


    /**
     * Generates the unique ID based on time. 
     * @return long, Number of ms since 1970. Unique ID used for file naming
     */
    public long getUniqueID() {
	return new Date().getTime();
    }

    public void changeCwWriterPath(String path){
	cwFolder = new File(path);
    }

}
