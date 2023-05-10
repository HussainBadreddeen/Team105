package model.characters;
import exceptions.*;


public class Fighter extends Hero{

	
	public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
      //removed commented attacks, new one is more efficient 
	  public void attack() throws NotEnoughActionsException, InvalidTargetException {
		  super.attack();//usespecial? hye3rf mnein enno zabat b2a true
		  if (!this.isSpecialAction())
			  this.setActionsAvailable(getActionsAvailable() - 1); 
	  }
}
