package model.world;

import model.characters.Hero;

public class CharacterCell extends Cell {
	private Character character;
	private boolean isSafe;
	
	public CharacterCell() {
		
		
	}
	
	public CharacterCell(boolean isVisible,boolean isSafe) {
		this.setIsVisible(isVisible);
		this.isSafe = isSafe;
	}
	
	public Character getCharacter() {
		return this.character;
	}
	
	
	public void setCharacter(Character character) {
		this.character = character;
	}
	
	public boolean getIsSafe() {
		return this.isSafe;
	}
	
	public void setIsSafe(boolean isSafe) {
		this.isSafe = isSafe;
	}
	
}
