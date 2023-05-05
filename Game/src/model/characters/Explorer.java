package model.characters;

import engine.Game;
import exceptions.*;


public class Explorer extends Hero {

	
	public Explorer(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	public void useSpecial() throws Exception{ 
		super.useSpecial();
		Game.setAllCellVisbility(true);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

