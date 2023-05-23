package exceptions;

public class NoAvailableResourcesException extends GameActionException{
	public NoAvailableResourcesException() {
		//sol did not add super(); here eshme3na dee? although no need but consistency?
	}
		
	public NoAvailableResourcesException(String s) {
		super(s);
	}
}


