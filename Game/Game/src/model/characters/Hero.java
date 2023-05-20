package model.characters;
import java.awt.Point;
import java.util.ArrayList;

import engine.Game;
import exceptions.*;
import model.collectibles.*;
import model.world.*;


public abstract class Hero extends Character{ //changed abstract pub to pub abstract for convention
	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory=new ArrayList<Vaccine>();//sol =new done under super
	private ArrayList<Supply> supplyInventory=new ArrayList<Supply>();//in sol =new done under super
	
	
	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
		this.specialAction = false;//def is false why added?
	}
	
	public int getActionsAvailable() {
		return this.actionsAvailable;
	}
	
	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}
	
	public int getMaxActions() {
		return this.maxActions;
	}
	
	public boolean isSpecialAction() {     //if boolean, use is instead of get before method
		return this.specialAction;
	}
	
	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}
	
	public ArrayList<Vaccine> getVaccineInventory(){
		return this.vaccineInventory;
	}
	
	public ArrayList<Supply> getSupplyInventory(){
		return this.supplyInventory;
	}
	
	public void addVaccine(Vaccine vaccine) {
	    this.vaccineInventory.add(vaccine);
	}
	
	public void addSupply(Supply supply) {//arraylist full? def =10 but it auto resizes
		this.supplyInventory.add(supply);
	}
	
	public void removeVaccine() {
		this.vaccineInventory.remove(0); //check this which to remove N:should be fine to leave it like that
		
	}
	
	public void removeSupply() {
		this.supplyInventory.remove(0);//check this which to remove
	}
	
	public void attack() throws NotEnoughActionsException, InvalidTargetException{
		if (this.getTarget() instanceof Hero || this.getTarget() == null)
			throw new InvalidTargetException("This isn't a zombie dude");
		
		if (this.actionsAvailable <= 0) {
			throw new NotEnoughActionsException("You dont have enough actions");}
		
		if(!this.isAdjacent(this.getTarget())) {
			throw new InvalidTargetException("Target isn't close enough");
		}
		
		super.attack();
		
		}
		
	
	public void cure() throws NotEnoughActionsException, InvalidTargetException, NoAvailableResourcesException{ 
		 if (this.getTarget() == null)
	        	throw new InvalidTargetException();
        if (!this.isAdjacent(this.getTarget()) || !(this.getTarget() instanceof Zombie))
        	throw new InvalidTargetException("Target isn't close enough or target is not hero!");
        if (this.getActionsAvailable() <= 0)
        	throw new NotEnoughActionsException("You dont have enough actions to cure!");
//    	if (this.getCurrentHp() <=0) {//added this
//        	this.onCharacterDeath();//added this
//        }//added this
    	//else {//added this
    	Vaccine v = this.vaccineInventory.get(0);//added this
    	v.use(this);//added this
    	//}//added this
       
  
    }
		
	
	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException{ //changed from NoAvailableResourcesException to Exception
		if (this.getSupplyInventory().isEmpty()) {//was == null changed to .isEmpty()
            throw new NoAvailableResourcesException("You dont have enough Supplies or Vaccines!");
    }
        else {
            this.getSupplyInventory().get(0).use(this);;
            this.setSpecialAction(true);
            }
    }
		
	
	public void move(Direction d) throws MovementException, NotEnoughActionsException {
		if(this.getActionsAvailable() <= 0) 
			throw new NotEnoughActionsException("You dont have enough actions to move!");
		
		int moveNum = 0;
		boolean moveYAxis = false;
		if (d == Direction.LEFT) {
			moveNum = -1;
			moveYAxis = false;
		}
		
		else if (d == Direction.RIGHT) {
			moveNum = 1;
			moveYAxis = false;
		}
		
		else if (d == Direction.UP) {
			moveNum = 1;   
			moveYAxis = true;
		}
		
		else if (d == Direction.DOWN) {
			moveNum = -1; 
			moveYAxis = true;
		}
		
		
		int h = (int)this.getLocation().getX();
		int w = (int)this.getLocation().getY();

		
		if (moveYAxis) 
			h += moveNum;
		else 
			w += moveNum;
		
		
		if (moveYAxis && ((h < 0) || (h >= 15))) {
			throw new MovementException("You can't go outside the map! (right or left)");  

		}
		else if (!moveYAxis && ((w < 0) || (w >= 15))) {
			throw new MovementException("You can't go outside the map! (up or down)");  
			
		}
		
		
		if ((Game.map[h][w] instanceof CollectibleCell)) {
			((CollectibleCell)Game.map[h][w]).getCollectible().pickUp(this);
		
		}
		
		else if ((Game.map[h][w] instanceof TrapCell)) {
			int dmg = ((TrapCell)Game.map[h][w]).getTrapDamage();
			this.setCurrentHp(this.getCurrentHp() - dmg);
			
		}
		
		if ((Game.map[h][w] instanceof CharacterCell))
			if (((CharacterCell)Game.map[h][w]).getCharacter() != null)
				throw new MovementException("Cell occupied can't move there!");

		
			
		this.setActionsAvailable(this.getActionsAvailable() - 1);
		if (this.getCurrentHp() > 0) {
			Game.map[h][w] = new CharacterCell(this, true);
			this.makeAdjacentVisible();
			}
	
		int oldH = (int)this.getLocation().getX();
		int oldY = (int)this.getLocation().getY();
		
		this.setLocation(new Point(h, w));
		((CharacterCell)Game.map[oldH][oldY]).setCharacter(null);
	}
		
			
		
	
	
	
	public void makeAdjacentVisible() {

		Cell[] adj = this.giveAdjacentCells();
		int w = (int)this.getLocation().getY();
		int h = (int)this.getLocation().getX();
		Game.map[h][w].setVisible(true);
		for (int i = 0; i < adj.length;i++) {
			if (adj[i] != null) {
				adj[i].setVisible(true);
				
			}
		}
			
	}
	
}
