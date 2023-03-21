package exceptions;

abstract public class GameActionException extends Exception{
	public GameActionException() {
	}
	
	public GameActionException(String s) {
		super(s);
	}
}
