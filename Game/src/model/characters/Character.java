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
		if(currentHp < 0) 
			this.currentHp = 0;
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
	
	public void attack() throws GameActionException{
		//include invalid target exception
		if (this.target != null) {
			this.target.setCurrentHp(this.target.getCurrentHp() - this.attackDmg);
			this.target.defend(this);
		
			if (this.target.currentHp <= 0) {
			this.target.onCharacterDeath();
			}
	}
		else {//target = null
			//throw new InvalidTargetException();//btgeeb error (& / or) failure for now 3shan tests bayza
		}
		//Include exceptions somehow for all attack methods
		}
		
	
	public void defend(Character c) {
		this.target = c;
		this.target.setCurrentHp(this.target.getCurrentHp() - (this.attackDmg/2));
	}
	
	public void onCharacterDeath() {
		if (this instanceof Hero) {
			Game.heroes.remove(this);}
		if (this instanceof Zombie) {
			Game.zombies.remove(this);}
		
		Point l = this.getLocation();
		int x = (int)(l.getX());
		int y = (int)(l.getY());
		Game.map[y][x] = null;
			
			boolean flag = true;
			do {
				x = Game.randomPosition();//for zombie
				y = Game.randomPosition();//for zombie
				if (Game.map[y][x] instanceof CharacterCell) {
					if (((CharacterCell)(Game.map[y][x])).getCharacter() == null)
						flag = false;
					
				}}
			while (flag);
			
			Zombie z = new Zombie();
			z.setLocation(new Point(y, x));
			Game.zombies.add(z);
			Game.map[y][x] = new CharacterCell(z);
			
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
	

