package model.collectibles;
import exceptions.NoAvailableResourcesException;
import model.characters.*; // to use for hero


public class Supply implements Collectible {
	//removed the point variable, methods, constructor
	
    public Supply() {
	 
    }
	
	public void pickUp(Hero h) {
		h.addSupply(this);
		
	}
	
	public void use(Hero h) throws NoAvailableResourcesException {
		h.removeSupply();
	}
}



