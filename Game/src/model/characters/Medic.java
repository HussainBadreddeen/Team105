package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.world.*;

public class Medic extends Hero {
	//private int healAmount;
		//Heal amount  attribute - quiz idea taken from sol

	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	
	  public void useSpecial() throws InvalidTargetException, NoAvailableResourcesException{ 
	        if(this.getTarget() instanceof Zombie) {
	           throw new InvalidTargetException("Cannot heal a zombie!");
	        }
	        if(!(this.getTarget().isAdjacent(this))) {
	        	throw new InvalidTargetException("Target isn't close enough!");
	        }

	       super.useSpecial();
	       this.getTarget().setCurrentHp(this.getTarget().getMaxHp());

	            
	        }
	  
	  public void attack() throws NotEnoughActionsException, InvalidTargetException {
		  super.attack();
		  this.setActionsAvailable(getActionsAvailable() - 1);
	  }

	    }
	    