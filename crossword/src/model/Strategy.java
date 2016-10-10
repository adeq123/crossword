package model;

import java.io.IOException;

import FitedExeptions.NoMatchingWords;
import FitedExeptions.WrongCoordinatesException;

public abstract class Strategy {
	
	public enum strategyID{
		Easy,Hard;
	}
	public abstract CwEntry findEntry(Crossword cw) throws NoMatchingWords, WrongCoordinatesException;
	public abstract void updateBoard(Board b, CwEntry e);
}
