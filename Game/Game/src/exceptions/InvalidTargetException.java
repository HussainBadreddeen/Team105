package exceptions;

public class InvalidTargetException extends GameActionException{
	public InvalidTargetException() {
		//sol did not add super(); here eshme3na dee? although no need but consistency?
	}
	
	public InvalidTargetException(String s) {
		super(s);
	}
	
}
