package model;

import FitedExeptions.NoMatchingWords;
import FitedExeptions.WrongCoordinatesException;

/**
 * Abstract class modeling the algorithms used to generate the crossword.
 * @author ADRO
 * @see EasyStrategy
 * @see HardStrategy
 */
public abstract class Strategy {

    public enum strategyID{
	Easy,Hard;
    }
    public abstract CwEntry findEntry(Crossword cw) throws NoMatchingWords, WrongCoordinatesException;
    public abstract void updateBoard(Board b, CwEntry e);
}
