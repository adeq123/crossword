package model;

public abstract class Strategy {
	
	public enum strategyID{
		Easy,Hard;
	}
	public abstract CwEntry findEntry(Crossword cw) throws Exception;
	public abstract void updateBoard(Board b, CwEntry e);
}
