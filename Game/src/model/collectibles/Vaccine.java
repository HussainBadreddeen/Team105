package model.collectibles;
import java.awt.Point;
import java.util.Random;

import engine.Game;
import exceptions.*;
import model.characters.*; 
import model.world.*;

public class Vaccine implements Collectible{
	//removed the point variable, methods, constructor
	static int useCount;   
    public Vaccine() {
		
	}
  //removed old commented hero at collectible method and old commented pickup method
		
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
			throw new NoAvailableResourcesException("You dont have enough actions to cure!");
		
	}

	public static int getUseCount() {
		return useCount;
	}

	

	

	
	





}
