package model.world;

import model.characters.Character;

public class CharacterCell extends Cell {
	private Character character;
	private boolean isSafe;
	
	public CharacterCell(Character character,boolean isSafe) {
		
		this.character = character;
		
		this.isSafe = isSafe;
	
	}public CharacterCell(Character character) {
		this.character = character;
		this.isSafe = false;
	}
	
	public Character getCharacter() {
		return this.character;
	}
	
	
	public void setCharacter(Character character) {
		this.character = character;
	}
	
	public boolean isSafe() {
		return this.isSafe;
	}
	
	public void setSafe(boolean isSafe) {
		this.isSafe = isSafe;
	}
	
}
