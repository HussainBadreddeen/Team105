package model.characters;

import java.util.ArrayList;

import model.collectibles.Supply;
import model.collectibles.Vaccine;

public class Hero extends Character{
	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;
	protected Hero(){
		throw new UnsupportedOperationException("Class Instantiation not supported");
	}
	
	protected Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
	}
	
	public int getActionsAvailable() {
		return this.actionsAvailable;
	}
	
	public void setActionsAvailable(int actionsAvailable) {
		this.actionsAvailable = actionsAvailable;
	}
	
	public int getMaxActions() {
		return this.maxActions;
	}
	
	public boolean getSpecialAction() {
		return this.specialAction;
	}
	
	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}
	
	public ArrayList<Vaccine> getVaccineInventory(){
		return this.vaccineInventory;
	}
	
	public ArrayList<Supply> getSupplyInventory(){
		return this.supplyInventory;
	}
}
