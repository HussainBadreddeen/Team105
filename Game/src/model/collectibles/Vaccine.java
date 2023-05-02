package model.collectibles;

import java.awt.Point;

import exceptions.NoAvailableResourcesException;
import model.characters.*; // to use for hero 

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
	public void use(Hero h) throws NoAvailableResourcesException {//player can only win the game once all vaccines have been collected and used
		h.removeVaccine(this);// cure zombies and recruit new heroes here?
		this.useCount += 1;
		//System.out.println("choose: FIGH,MED,EXP");//player by choose akeed sah? N:takes from availableHeroes probably
		//create enum w y choose meno?
		//Zombie.setZombiesCount();//this dec by 1 or change count public 
//Zombie.ZOMBIES_COUNT--;//mynf3sh 3shan zombies_count private y2ma ne3mllo public hmm?
		//
		//a hero of chosen type added here
		//code for instantiating desired hero
	
		//logic implemented here not hero class
		
	}

	public static int getUseCount() {
		return useCount;
	}

	

	
	





}
