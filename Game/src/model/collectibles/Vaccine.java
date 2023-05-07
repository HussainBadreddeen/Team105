package model.collectibles;

import java.awt.Point;
import java.util.Random;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.*; // to use for hero 
import model.world.CharacterCell;

public class Vaccine implements Collectible{
	//removed the point variable, methods, constructor
	static int useCount;   
    public Vaccine() {
		
	}
    
	/*public boolean isHeroAtCollectible(Collectible collectible, Hero hero) {
	    int collectibleX = (int)collectible.getLocation().getX();
	    int collectibleY = (int)collectible.getLocation().getY();

	    int heroX = (int) hero.getLocation().getX();
	    int heroY = (int)hero.getLocation().getY();

	    return (heroX == collectibleX) && (heroY == collectibleY);//true =same location
	}*/ //not necessary, will be implemented in the game class

    //Old PickUp Method
    
	@Override
	/*public void pickUp(Hero h) {//implement location of collectible here?
		if(isHeroAtCollectible(this, h)== true) {
			h.addVaccine(this);//no handling 3shan already el size ad el fl le3ba?
		}//end of if statement
		//made a method in hero class and used it here. auto pickup?
	    //logic implemented here not hero class	
		}*/
		
	public void pickUp(Hero h) { //new pickUp Method
		h.addVaccine(this);
		
		
	}
		

	@Override
	public void use(Hero h) throws NoAvailableResourcesException{//player can only win the game once all vaccines have been collected and used
		//h.removeVaccine();// cure zombies and recruit new heroes here?
		if (h.getVaccineInventory().size() > 0) {
			h.removeVaccine();
			Vaccine.useCount += 1;
			Game.zombies.remove(h.getTarget());
			Point p = h.getTarget().getLocation();
	        
	        Zombie.setZombiesCount();
	 
	        int randnum = new Random().nextInt(Game.availableHeroes.size());
	        Hero hero = Game.availableHeroes.get(randnum);
	        Game.heroes.add(hero);
	        hero.setLocation(p);
	        ((CharacterCell)Game.map[(int)p.getX()][(int)p.getY()]).setCharacter(hero);  //we put created hero in cured zombie's location
	        Game.availableHeroes.remove(hero);
	     //   this.setTarget(null); // You should remove the hero's target from the Zombies array in Game expected:<0> but was:<1>
	        h.setActionsAvailable(h.getActionsAvailable()-1);
			}
		
		else
			throw new NoAvailableResourcesException();
		
	}

	public static int getUseCount() {
		return useCount;
	}

	

	

	
	





}
