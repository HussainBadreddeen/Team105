package model.collectibles;
import exceptions.NoAvailableResourcesException;
import model.characters.*; // to use for hero


public class Supply implements Collectible {
	//removed the point variable, methods, constructor
	
    public Supply() {
	 
    }
	
	@Override
	public void pickUp(Hero h) {//implement location of collectible here?
		h.addSupply(this);
		//made a method in hero class and used it here. auto pickup?yes
	}
	//removed old commented implementation of pickup
	@Override
	public void use(Hero h) throws NoAvailableResourcesException {
		h.removeSupply();
	}
}



