package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;

public class Medic extends Hero {
	//private int healAmount;
		//Heal amount  attribute - quiz idea taken from sol

	public Medic(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	
	  public void useSpecial() throws Exception{ //come back later bullshit exception failure
	        super.useSpecial();
	        if(this.getTarget() instanceof Zombie) {
	            throw new InvalidTargetException();
	        }
	        else if(this.getSupplyInventory().isEmpty()) {
	                throw new NoAvailableResourcesException();
	            }
	            else {//if character and med has enough supply
	                this.getTarget().setCurrentHp(this.getTarget().getMaxHp());

	            }
	        }

	    }
	    