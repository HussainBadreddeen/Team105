package model.characters;

import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;

public class Fighter extends Hero{

	
	public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	
	/*public void attack() throws NotEnoughActionsException, InvalidTargetException {
		if (this.getTarget() instanceof Hero || this.getTarget() == null)
			throw new InvalidTargetException();
		
		
		if (!this.isAdjacent(this.getTarget())) {
			throw new InvalidTargetException();
		}
		
		
		if (this.getActionsAvailable() <= 0)
			throw new NotEnoughActionsException();
		
	
		if (!this.isSpecialAction())
			this.setActionsAvailable(getActionsAvailable() - 1);
		this.getTarget().setCurrentHp(this.getTarget().getCurrentHp() - this.getAttackDmg());
		this.getTarget().defend(this);
	
		if (this.getTarget().getCurrentHp() == 0) {
			//this.getTarget().onCharacterDeath();
			//this.setTarget(null); //logic out of the window
		}
	}*/
	
	  public void attack() throws NotEnoughActionsException, InvalidTargetException {
		  super.attack();
		  if (!this.isSpecialAction())
			  this.setActionsAvailable(getActionsAvailable() - 1);
		  
	  }
			
		
		
			
			
		
		
		
			
		
					
			
		
		
	
	/*public void attack() throws NotEnoughActionsException {             //for future use if necessary
		if (this.isSpecialAction()) 
			if (this.getActionsAvailable() == 0) {
				throw new NotEnoughActionsException();
			
			
			else {
				try {
				super.attack();}
				catch(GameActionException e) {
					
				}
			}
		}
		
	}*/
	
	
	
	}
