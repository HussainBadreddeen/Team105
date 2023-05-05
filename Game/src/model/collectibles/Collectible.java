package model.collectibles;

import exceptions.*;//looks cleaner with *
import model.characters.*;

public interface Collectible {
	
	public void pickUp(Hero h);
			
	public void use(Hero h) throws NoAvailableResourcesException;

	// removed getLocation
			

}
