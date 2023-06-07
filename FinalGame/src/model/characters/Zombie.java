package model.characters;
import exceptions.*;
import model.world.*;


public class Zombie extends Character{
    static int ZOMBIES_COUNT = 1; //removed private solution doesn't have private here
	
    public Zombie() {
		super("Zombie " + ZOMBIES_COUNT++, 40, 10);
		
		//ZOMBIES_COUNT++; here in sol but our method works the same
	}
//Removed GetZombiesCount since we do not use it
	public static void setZombiesCount() {//should only decrease by 1 //probably not needed //We're using it in Game Engine.
		ZOMBIES_COUNT --;
	}

	public void attack() throws InvalidTargetException, NotEnoughActionsException{
		
        Cell[] adj = this.giveAdjacentCells();
        for (int i = 0; i < adj.length; i++ ) {
        	if (adj[i] instanceof CharacterCell) {
            	if (((CharacterCell)adj[i]).getCharacter() instanceof Hero) {
                    this.setTarget(((CharacterCell)adj[i]).getCharacter());
                    super.attack();
                    //this.setTarget(null); in endTurn method
                    break;
                    }
            	}
        	}
        }
    }

	
