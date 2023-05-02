package model.characters;

public class Fighter extends Hero{

	
	public Fighter(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg, maxActions);
	}
	
	
	public void attack() {
		super.attack();
		this.setActionsAvailable(this.getActionsAvailable() + 1);
		}
	
	/*public void attack() {             //for future use if necessary
		if (this.isSpecialAction()) {
			if (this.getActionsAvailable() == 0) {
				//NotEnoughActionsException
			}
			
			else if (this.getTarget() instanceof Hero) {
				//InvalidTargetException
			}
			else {
				super.attack();
			}
		}
		
		else {
			super.attack();
		}
	}*/
	
	
	
}
