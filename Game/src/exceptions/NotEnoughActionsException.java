package exceptions;

public class NotEnoughActionsException extends GameActionException {
	public NotEnoughActionsException() {
		//sol did not add super(); here eshme3na dee? although no need but consistency?		
	}
	
	public NotEnoughActionsException(String s) {
		super(s);
	}
}
