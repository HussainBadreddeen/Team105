package model.characters;

import java.util.ArrayList;

import exceptions.NotEnoughActionsException;
import model.collectibles.Collectible;
import model.collectibles.Supply;
import model.collectibles.Vaccine;

abstract public class Hero extends Character{
	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory=new ArrayList<Vaccine>();//sol =new done under super
	private ArrayList<Supply> supplyInventory=new ArrayList<Supply>();//in sol =new done under super
	
	
	public Hero(String name, int maxHp, int attackDmg, int maxActions) {
		super(name, maxHp, attackDmg);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
		this.specialAction = false;//def is false why added?
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
	
	public boolean isSpecialAction() {     //if boolean, use is instead of get before method
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
	
	public void addVaccine(Vaccine vaccine) {
	    this.vaccineInventory.add(vaccine);
	}
	
	public void addSupply(Supply supply) {//arraylist full? def =10 but it auto resizes
		this.supplyInventory.add(supply);
	}
	
	public void removeVaccine(Vaccine vaccine) {
		this.vaccineInventory.remove(0); //check this which to remove N:should be fine to leave it like that
		
	}
	
	public void removeSupply(Supply supply) {
		this.supplyInventory.remove(0);//check this which to remove
	}
	
	public void attack(){
		if (this.actionsAvailable == 0) {
			//NotEnoughActionsException
		}
		
		else if (this.getTarget() instanceof Hero) {
			//InvalidTargetException
		}
		else {
			super.attack();
			this.actionsAvailable -=1;
		}}
		
	public void useSpecial() {
		if (this.getSupplyInventory() == null) {
			//throw NoAvaialableResourcesException
	}
		else {
			//code for using it. case for each hero
			this.specialAction = false;//no longer has special action after using
		}
		
		
		
	}


	

	


}
