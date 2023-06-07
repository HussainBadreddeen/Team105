package exceptions;

public class MovementException extends GameActionException{
	public MovementException() {
		super(); //sol added this but no need for it really
	}
	
	public MovementException(String s) {
		super(s);
	}
	

}
