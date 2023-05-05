package model.characters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import engine.Game;
import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

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
	
	public void attack() throws NotEnoughActionsException{
		if (this.actionsAvailable == 0) {
			throw new NotEnoughActionsException();}

		
		else if (this.getTarget() instanceof Hero) {
			//throw new InvalidTargetException();
		}
		
		
		else if(!this.isAdjacent(this.getTarget())) {
			//throw new InvalidTargetException();
		}
		
		else {
			try {
			super.attack();
			this.actionsAvailable -=1;}
			catch(GameActionException e) {
				
			}
			
		}}
	
	public void cure() throws Exception{ 
        if ((this.getTarget() instanceof Zombie)){
            if(this.isAdjacent(getTarget())==true){
             if(!(this.getActionsAvailable()==0)) {
                if(!(this.getVaccineInventory().isEmpty())) {
                    Point x = this.getTarget().getLocation();
                    this.removeVaccine();
                    //if(!Game.availableHeroes.isEmpty()) { remove
                    int randnum = new Random().nextInt(Game.availableHeroes.size());
                    Hero h = Game.availableHeroes.get(randnum);
                    Game.heroes.add(h);
                    h.setLocation(x);
                    Game.availableHeroes.remove(h);
                    this.setActionsAvailable(getActionsAvailable()-1);
                }
                }
             }
            }
        }
		
	public void useSpecial() throws Exception{ 
        if (this.getSupplyInventory() == null) {
            throw new NoAvailableResourcesException();
    }
        else {
            this.getSupplyInventory().remove(0);
            this.setSpecialAction(true);
            }
    }
		
		
	
	
	public void move(Direction d) throws Exception {
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
			Game.map[y][x] = new CharacterCell(this);
			((CharacterCell)Game.map[(int)this.getLocation().getY()][(int)this.getLocation().getY()]).setCharacter(null);
			this.getLocation().move(y, x);
			this.makeAdjacentVisible();
			
		}
		
		else if ((Game.map[y][x] instanceof TrapCell)) {
			int dmg = ((TrapCell)Game.map[y][x]).getTrapDamage();
			
			this.setCurrentHp(this.getCurrentHp() - dmg);
			this.setActionsAvailable(this.getActionsAvailable() - 1);
			
			Game.map[y][x] = new CharacterCell(this);
			((CharacterCell)Game.map[(int)this.getLocation().getY()][(int)this.getLocation().getY()]).setCharacter(null);
			this.getLocation().move(y, x);
			this.makeAdjacentVisible();
			
		}
		else if ((Game.map[y][x] instanceof CharacterCell) & (((CharacterCell)Game.map[y][x]).getCharacter() != null)){
			throw new MovementException();
				
		}

		
		else {
			this.setActionsAvailable(this.getActionsAvailable() - 1);
			((CharacterCell)Game.map[y][x]).setCharacter(this);
			((CharacterCell)Game.map[(int)this.getLocation().getY()][(int)this.getLocation().getY()]).setCharacter(null);
			this.getLocation().move(y, x);
			this.makeAdjacentVisible();
		}
		
		
		
	}
	
	public void makeAdjacentVisible() {
		Cell[] adj = this.giveAdjacentCells();
		int x = (int)this.getLocation().getY();
		int y = (int)this.getLocation().getX();
		Game.map[y][x].setVisible(true);
		for (int i = 0; i < adj.length;i++) {
			if (adj[i] != null) {
				adj[i].setVisible(true);
			}
		}
		
	}
	



	

	


}
