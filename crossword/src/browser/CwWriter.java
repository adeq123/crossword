package browser;

import model.Crossword;
import model.CwEntry;
import viewer.CwPanel;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import FitedExeptions.NoActualCw;

public class CwWriter implements Writer{
	
	File cwFolder;
	

	/**
	 * Method which writes corssword to the folder
	 * @param cw, Crossword to be written to the folder
	 * @throws FileNotFoundException 
	 */
	
	public CwWriter (String cwFolderPath) throws FileNotFoundException{
		
		this.cwFolder = new File(cwFolderPath);
		
		
		
	}
	

 /**
  * Writes a crossword cw with name to the file with correct formatting that can read afterwards
  * @param cw, crossword to be written
  * @param name, name of crossword
  * @throws IOException
  */
	public void WriteCW(Crossword cw, String name) throws IOException {
		
		String cwFileName = name;
		File cwTxt = new File(cwFolder.getAbsolutePath()+"\\"+cwFileName+".txt");
		cwTxt.createNewFile();
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cwTxt.getAbsolutePath())));
		
		writer.write(cw.getStartegy().toString()+System.lineSeparator());
		writer.write(Integer.toString(cw.getBoard().getWidth()) + " " + Integer.toString(cw.getBoard().getHeight()) + System.lineSeparator());
		writer.write("--------------List of words-----------------"+System.lineSeparator());
		
		ListIterator <CwEntry> entriesIter = cw.getROEntryIter();
		CwEntry tmpEntry;
		while(entriesIter.hasNext()){
			tmpEntry = entriesIter.next();
			writer.write(tmpEntry.getWord() + " "  + tmpEntry.getX() + " " + tmpEntry.getY() + " " + tmpEntry.getD() + " " + System.lineSeparator());
		}
		
		writer.write( "***end***" + System.lineSeparator()+ System.lineSeparator());
		
		String cwInString = cw.printBoard();
		String [] cwLineByLine = cwInString.split("\n");
		for(String word : cwLineByLine)
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
            doc.addAuthor("Adrian Roguski");
            doc.addCreationDate();
            doc.setMargins(0, 0, 0, 0);
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
            		fileName));
            doc.open();

            
            Image iTextImage = Image.getInstance(writer, awtImage, 1);
            iTextImage.setAlignment(0);
            //iTextImage.setAbsolutePosition(0, 0);
            iTextImage.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            doc.add(iTextImage);

            doc.close();

      
    }
	/**
	 * Creates the awt file based on component (ex. JFrame, JPanel)
	 * @param component
	 * @return
	 */
	public static java.awt.Image getImageFromPanel(Component component) {

        BufferedImage image = new BufferedImage(component.getWidth(),
                component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());
        return image;
    }
	
	
	/**
	 * 
	 * @return long, Number of ms since 1970. Unique ID used for file naming
	 */
	public long getUniqueID() {
		return new Date().getTime();
	}
	
	public void changeCwWriterPath(String path){
		cwFolder = new File(path);
	}

}
