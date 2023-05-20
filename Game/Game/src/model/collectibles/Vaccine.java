package model.collectibles;
import java.awt.Point;
import java.util.Random;

import engine.Game;
import exceptions.*;
import model.characters.*; 
import model.world.*;

public class Vaccine implements Collectible{ 
    public Vaccine() {
		
	}
		
	public void pickUp(Hero h) { 
		h.addVaccine(this);
		
		
	}
		

	@Override
	public void use(Hero h) throws NoAvailableResourcesException{
		
		if (h.getVaccineInventory().size() > 0) {
			h.removeVaccine();
			Game.zombies.remove(h.getTarget());
			Point p = h.getTarget().getLocation();
			h.setTarget(null);
			
	        
	        Zombie.setZombiesCount();
	 
	        int randnum = new Random().nextInt(Game.availableHeroes.size());
	        Hero hero = Game.availableHeroes.get(randnum);
	        hero.setLocation(p);
	        ((CharacterCell)Game.map[(int)p.getX()][(int)p.getY()]).setCharacter(hero);  
	        Game.heroes.add(hero);
	        Game.availableHeroes.remove(hero);
	     
	        h.setActionsAvailable(h.getActionsAvailable()-1);
			}
		
		else
			throw new NoAvailableResourcesException("You dont have enough actions to cure!");
		
	}

}
