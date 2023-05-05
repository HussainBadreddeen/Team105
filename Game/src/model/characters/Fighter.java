package model.characters;

import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public class Fighter extends Hero{

	
	public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	
	/*public void attack() throws NotEnoughActionsException {
		
		try {
			super.attack();
			if (this.isSpecialAction())
				this.setActionsAvailable(this.getActionsAvailable() + 1);
			}
		catch(GameActionException e) {
				
			}
		
		}*/
	
	public void attack() throws NotEnoughActionsException { //for future use if necessary
		if (this.isSpecialAction()) {
			if (this.getActionsAvailable() == 0) {
				throw new NotEnoughActionsException();
			}
			
			else {
				try {
				super.attack();}
				catch(GameActionException e) {
					
				}
			}
		}
		
	}
	
	
	
}
