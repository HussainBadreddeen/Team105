package model.characters;

import java.awt.Point;

import engine.Game;
import exceptions.*;
import model.world.*;


public class Zombie extends Character{
    static int ZOMBIES_COUNT = 1; //remov private sol doesnt have private here
	
    public Zombie() {
		super("Zombie " + ZOMBIES_COUNT++, 40, 10);
		
		//ZOMBIES_COUNT++; here in sol but our method works the same
	}

	public static int getZombiesCount() { //probably not needed
		// TODO Auto-generated method stub
		return ZOMBIES_COUNT;
	}


	public static void setZombiesCount() {//should only decrease by 1 //probably not needed
		ZOMBIES_COUNT --;
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException{
		
        Cell[] adj = this.giveAdjacentCells();
        for (int i = 0; i < adj.length; i++ ) {
            if (adj[i] != null) {
            	if (adj[i] instanceof CharacterCell) {
	            	if (((CharacterCell)adj[i]).getCharacter() instanceof Hero) {
	                    this.setTarget(((CharacterCell)adj[i]).getCharacter());
	                    this.getTarget().setCurrentHp(this.getTarget().getCurrentHp() - this.getAttackDmg());
	            		this.getTarget().defend(this);
	                    //this.setTarget(null);
	                    break;
                }
	            	}
            
      

        	   
           }
 
	
	
	
}
        }
	}
	
