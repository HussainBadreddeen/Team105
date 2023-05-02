package model.characters;

import java.awt.Point;
import engine.Game;
import model.world.Cell;

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
	
	public void attack() {
		this.target.setCurrentHp(this.target.getCurrentHp() - this.attackDmg);
		if (this.target.currentHp <= 0) {
			this.target.onCharacterDeath();
		}
		//else
			//this.target.defend(this);  //check if defending after death
	}
	
	public void defend(Character c) {
		this.target = c;
		this.target.setCurrentHp(this.target.getCurrentHp() - (this.attackDmg/2));
	}
	
	public void onCharacterDeath() {
		if (this instanceof Hero)
			Game.heroes.remove(this);
		if (this instanceof Zombie)
			Game.zombies.remove(this);
		
		Point l = this.getLocation();
		int x = (int)(l.getX());
		int y = (int)(l.getY());
		Game.map[14 - y][x] = null;
		}
		
	
	
	
	public Cell[] giveAdjacentCells(Character c) {
		Cell[] adjCells = new Cell[9];
		Point l = c.getLocation();
		int x = (int)l.getX() - 1; //13
		int y = (int)l.getY() + 1; //1
		int count = 0;
		
		for (int i = 0; i < 3;i++) {
			if (y - i < 15 && y - i >= 0) {
				for (int j = 0; i < 3; j++) {
			
					if (x + j < 15 && x + j >= 0) {
						adjCells[count] = Game.map[14 - y - i][x + j];
						count++;}
					if (i == 1)
						j ++;
				
			}
		}
		
	}
		return adjCells;
	}
	
	}
	

