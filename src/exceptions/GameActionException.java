package exceptions;

abstract public class GameActionException extends Exception{ //convention public abstract not abstract public
	public GameActionException() {
		super();//sol added this but no need for it really. only added to this & MovExcep
	}
	
	public GameActionException(String s) {
		super(s);
	}
}
