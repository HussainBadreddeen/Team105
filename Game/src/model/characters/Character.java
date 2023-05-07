package model.characters;

import java.awt.Point;
import engine.Game;
import exceptions.*;
import model.world.*;


abstract public class Character { //convention public abstract not abstract public
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;
	
	public Character(String name, int maxHp, int attackDmg) {
		this.name = name;
		this.maxHp = maxHp;
		this.attackDmg = attackDmg;
		this.currentHp=maxHp;
	}
	
	public Character() {
		
	}
	
	public String getName() {
		return this.name;
	}
		
	public Point getLocation() {
		return this.location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getMaxHp() {
		return this.maxHp;
	}
	
	public int getCurrentHp() {
		return this.currentHp;
	}
	
	public void setCurrentHp(int currentHp) {
		if(currentHp <= 0) {
			this.currentHp = 0;
			this.onCharacterDeath(); 
			}
		else if(currentHp > maxHp) 
			this.currentHp = maxHp;
		else 
			this.currentHp = currentHp;
	}
	
	public int getAttackDmg() {
		return this.attackDmg;
	}
	
	public Character getTarget() {
		return this.target;
	}
	
	public void setTarget(Character target) {
		this.target = target;
	}
	
	public void attack() throws NotEnoughActionsException, InvalidTargetException{ // when changed to
		if (this.target != null) {
			this.target.setCurrentHp(this.target.getCurrentHp() - this.attackDmg);
			this.target.defend(this);
			if (this.target.getCurrentHp() == 0) {
				//this.target.onCharacterDeath();
				this.target = null;
			}
		}
		
		else {
			throw new InvalidTargetException();//both have common invtrgtException but NotEnoughActionsException is specifc to hero so implemted in hero
		}
	
		
		//Include exceptions somehow for all attack methods
		}
		
	
	public void defend(Character c) { //this is zombie c is attacker
		this.setTarget(c); 
		c.setCurrentHp(c.getCurrentHp() - (int)(this.getAttackDmg()/2));
		/*if (this.currentHp == 0)   //logic out of the window
			c.target = null;*/
	}
	
	public void onCharacterDeath() {
		
		if (this instanceof Hero) {
			Game.heroes.remove(this);
			}
		
		Point l = this.getLocation();
		int x = (int)(l.getY());
		int y = (int)(l.getX());
		Game.map[y][x] = new CharacterCell(null, true);
		
		
		if (this instanceof Zombie) {
			Game.zombies.remove(this);
			boolean flag = true;
			do {
				x = Game.randomPosition();
				y = Game.randomPosition();
				if (Game.map[y][x] instanceof CharacterCell) {
					if (((CharacterCell)(Game.map[y][x])).getCharacter() == null)
						flag = false;
				}	
			}
			while (flag);
			
			Zombie z = new Zombie();
			z.setLocation(new Point(y, x));
			Game.zombies.add(z);
			Game.map[y][x] = new CharacterCell(z);
		}
	}	
		
	
	
	
	public Cell[] giveAdjacentCells() {
		Cell[] adjCells = new Cell[8];
		Point l = this.getLocation();
		int x = (int)l.getX() - 1; //13
		int y = (int)l.getY() + 1; //1
		int count = 0;

		for (int i = 0; i < 3;i++) {
			if (y - i < 15 && y - i >= 0) {
				for (int j = 0; j < 3; j++) {
					if (x + j < 15 && x + j >= 0) {
						adjCells[count] = Game.map[y - i][x + j];
						count++;}
					if (i == 1)
						j++;
			}		
		}	
	}
		return adjCells;
	}
	
	/*public boolean isAdjacent(Character c) {
		Cell[] adj = this.giveAdjacentCells();
		for (int i = 0; i < adj.length; i++) {
			if (((CharacterCell)adj[i]).getCharacter().equals(c))
				return true;
		}
		return false;
	}*/
	
	public boolean isAdjacent(Character c) {
		int x1 = (int)this.getLocation().getY();
		int y1 = (int)this.getLocation().getX();
		
		int x2 = (int)c.getLocation().getY();
		int y2 = (int)c.getLocation().getX();
		
		
		if (x1 - x2 > 1 || x1 - x2 < -1 || y1 - y2 > 1 || y1 - y2 < -1)
			return false;
		return true;
	
	}
	public static boolean isCharacterAtLocation(Point l) {
		boolean CharacterAtLocation = false;
		for (int i = 0 ; i < (Game.heroes).size(); i++) {
			
			if (Game.heroes.get(i).getLocation().equals(l)) {
				CharacterAtLocation =  true;
			}
			
			if (Game.zombies.get(i).getLocation().equals(l)) {
				CharacterAtLocation =  true;
			}
			
		}
		return CharacterAtLocation;
	}
	
	}
	

