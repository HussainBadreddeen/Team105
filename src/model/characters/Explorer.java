package model.characters;

import engine.Game;
import exceptions.*;


public class Explorer extends Hero {


    public Explorer(String name, int maxHp, int attackDmg, int maxActions) {
        super(name, maxHp, attackDmg, maxActions);
    }

    public void useSpecial() throws InvalidTargetException, NoAvailableResourcesException{ 
        super.useSpecial();
        Game.setAllCellVisbility(true);
        }
    
    public void attack() throws NotEnoughActionsException, InvalidTargetException {
		  super.attack();
		  this.setActionsAvailable(getActionsAvailable() - 1);
	  }

    }
