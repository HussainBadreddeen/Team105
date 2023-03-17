package model.characters;

import java.awt.Point;

abstract public class Character {
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
	}
	
	public Character() {
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public Point getLocation() {
		return this.location;
	}
	
	public void setLocation(int x, int y) {
		location.setLocation(x, y);
	}
	
	public int getMaxHp() {
		return this.maxHp;
	}
	
	public int getCurrentHp() {
		return this.currentHp;
	}
	
	public void setCurrentHp(int currentHp) {
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
	
	

	
}
