package model.collectibles;

import java.awt.Point;

import exceptions.NoAvailableResourcesException;
import model.characters.*; // to use for hero
import model.world.*;// to use for collectiblecell

public class Supply implements Collectible {
	//removed the point variable, methods, constructor
	
	 
    public Supply() {
	 
    }
	
	@Override
	public void pickUp(Hero h) {//implement location of collectible here?
		h.addSupply(this);
		//made a method in hero class and used it here. auto pickup?
		//logic implemented here not hero class
		
	}
	
	@Override
	public void use(Hero h) throws NoAvailableResourcesException {
		h.removeSupply();
		
		
		/*if(h.isSpecialAction()== true){//represents if the hero has used his special action.
			if(!h.getSupplyInventory().isEmpty()){
				h.removeSupply(this); //enable the carrying hero to use their special action
				h.setSpecialAction(true);}*/
			//end of 2nd if statement
			//law true yb2a est5dmha w y2dar yst5dm tany? i think ah 
			//law false mst5dmhash yb2a m3a special action
		
		//more handling required akeed if true eh el nzam not used
		
		// end of 1st if statement

		//made a method in hero class and used it here. auto pickup?
				//logic implemented here not hero class
	}
	}



