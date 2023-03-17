package model.world;


public class TrapCell extends Cell {
	private int trapDamage;
	
	public TrapCell(boolean isVisible) {
		this.setIsVisible(isVisible);
		
		this.trapDamage = ((int)(Math.random() * 2) + 1) * 10 ;
	}
	
	public int getTrapDamage() {
		return this.trapDamage;
	}
	
	
	
}
