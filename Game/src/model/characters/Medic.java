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
	
	
	  public void useSpecial() throws Exception{ 
		 // super.useSpecial(); //when i use it here it outputs You should throw an exception of type: InvalidTargetException when the target is a zombie expected:<class exceptions.[InvalidTarget]Exception> but was:<class exceptions.[NoAvailableResources]Exception>
	        if(this.getTarget() instanceof Zombie) {//if super used above it outputs (testUseSpecialMethodLogicInMedic) with description above
	           throw new InvalidTargetException("Cannot heal a zombie!");
	        }
	        else if(!(this.getTarget().isAdjacent(this))) {
	        	throw new InvalidTargetException("Target isn't close enough!");
	        }
	       else if(this.getSupplyInventory().isEmpty()) {
	                throw new NoAvailableResourcesException("You dont have enough Supplies!");
	            }
	            else {//if hero and adjacent and med has enough supply
	            	super.useSpecial();//used here instead. 
	                this.getTarget().setCurrentHp(this.getTarget().getMaxHp());

	            }
	        }
	  
	  public void attack() throws NotEnoughActionsException, InvalidTargetException {
		  super.attack();
		  this.setActionsAvailable(getActionsAvailable() - 1);
	  }

	    }
	    