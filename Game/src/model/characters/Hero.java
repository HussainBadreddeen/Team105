package model.characters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import engine.Game;
import exceptions.*;
import model.collectibles.*;
import model.world.*;
import model.characters.*;


abstract public class Hero extends Character{
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
		if (this.actionsAvailable <= 0) {
			throw new NotEnoughActionsException();}
		
		
		if (this.getTarget() instanceof Hero) {
			throw new InvalidTargetException();
		}
		
		
		if(!this.isAdjacent(this.getTarget())) {
			throw new InvalidTargetException();
		}
		
		super.attack();
		this.actionsAvailable -=1;
		}
		
	
			
			
		
	
	public void cure() throws NotEnoughActionsException, InvalidTargetException, NoAvailableResourcesException{ 
		 if (this.getTarget() == null)
	        	throw new InvalidTargetException();
        if (!this.isAdjacent(this.getTarget()) || !(this.getTarget() instanceof Zombie))
        	throw new InvalidTargetException();
        if (this.getActionsAvailable() <= 0)
        	throw new NotEnoughActionsException();
        
        Vaccine v = this.vaccineInventory.get(0);
        v.use(this);

       /* Point p = this.getTarget().getLocation();
        
        //v.use(this);
        Zombie.setZombiesCount();//decreases zombie count by 1
        Zombie q = (Zombie) this.getTarget();//trying to solve my question law la2 change back to index 0
        //Game.zombies.remove(q);//removes 1 zombie from zombies Arraylist. //question which zombie?bec. they have names
        //if(!Game.availableHeroes.isEmpty()) { // remove hgame haye5lass msh m7tag a3mlha hena
        int randnum = new Random().nextInt(Game.availableHeroes.size());
        Hero h = Game.availableHeroes.get(randnum);
        Game.heroes.add(h);
        h.setLocation(p);
        ((CharacterCell)Game.map[(int)p.getX()][(int)p.getY()]).setCharacter(h);  //we put created hero in cured zombie's location
        Game.availableHeroes.remove(h);
     //   this.setTarget(null); // You should remove the hero's target from the Zombies array in Game expected:<0> but was:<1>
        this.setActionsAvailable(this.getActionsAvailable()-1);*/
                
           
        }
		
	public void useSpecial() throws Exception{ //changed from NoAvailableResourcesException to Exception
	 //   if(this.getTarget() instanceof Zombie) { //brings 4 errors
	 //  	throw new InvalidTargetException();
	//}
	/*else*/if (this.getSupplyInventory().isEmpty()) {//was == null changed to .isEmpty()
            throw new NoAvailableResourcesException();
    }
        else {
            this.getSupplyInventory().remove(0);
            this.setSpecialAction(true);
            }
    }
		
		
	
	
	public void move(Direction d) throws MovementException, NotEnoughActionsException {
		if(this.getActionsAvailable()>0) {
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
		
		
		int x = (int)this.getLocation().getY();
		int y = (int)this.getLocation().getX();
		
		if (moveYAxis) 
			y += moveNum;
		else 
			x += moveNum;
		
		
		Point newLocation = new Point(y, x);
		
		if (moveYAxis && ((y < 0) || (y >= 15))) {
			throw new MovementException();  

		}
		else if (!moveYAxis && ((x < 0) || (x >= 15))) {
			throw new MovementException();  
			
		}
		
		else if ((Game.map[y][x] instanceof CollectibleCell)) {
			((CollectibleCell)Game.map[y][x]).getCollectible().pickUp(this);
			this.setActionsAvailable(this.getActionsAvailable() - 1);
			Game.map[y][x] = new CharacterCell(this, true);
			this.getLocation().move(y, x);
			((CharacterCell)Game.map[(int)this.getLocation().getY()][(int)this.getLocation().getX()]).setCharacter(null);//kont 3amel .getY() in both index
			this.getLocation().move(y, x);
			this.makeAdjacentVisible();
			
		}
		
		else if ((Game.map[y][x] instanceof TrapCell)) {
			int dmg = ((TrapCell)Game.map[y][x]).getTrapDamage();
			
			this.setCurrentHp(this.getCurrentHp() - dmg);
			this.setActionsAvailable(this.getActionsAvailable() - 1);
			Game.map[y][x] = new CharacterCell(this, true);// should be this but failure says empty
			((CharacterCell)Game.map[(int)this.getLocation().getY()][(int)this.getLocation().getX()]).setCharacter(null);//kont 3amel .getY() in both index
			this.getLocation().move(y, x);//above .setCharachter(this) and not null?
			this.makeAdjacentVisible();
			
		}
		else if ((Game.map[y][x] instanceof CharacterCell) & (((CharacterCell)Game.map[y][x]).getCharacter() != null)){
			throw new MovementException();
				
		}

		
		else {//not trap or collectible not out of map. should move
			if(this.getActionsAvailable()>0) {
				this.setActionsAvailable(this.getActionsAvailable() - 1);
				((CharacterCell)Game.map[y][x]).setCharacter(this);
				//((CharacterCell)Game.map[(int)this.getLocation().getY()][(int)this.getLocation().getY()]).setCharacter(null);
				Game.map[(int)this.getLocation().getY()][(int)this.getLocation().getX()] = new CharacterCell(null);//kont 3amel .getY() in both index
				this.getLocation().move(y, x);
				this.makeAdjacentVisible();
			}
			else {
				throw new NotEnoughActionsException();
			}
		}
		
		
		
	}
		else {//mafeesh actions kfaya yt7rk
			throw new NotEnoughActionsException();
		}
	}
	
	public void makeAdjacentVisible() {

		Cell[] adj = this.giveAdjacentCells();
		int x = (int)this.getLocation().getY();
		int y = (int)this.getLocation().getX();
		Game.map[x][y].setVisible(true);
		for (int i = 0; i < adj.length;i++) {
			if (adj[i] != null) {
				adj[i].setVisible(true);
				
			}
		}
		
		
	}
	



	

	


}
