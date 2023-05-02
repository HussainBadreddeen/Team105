package model.world;

abstract public class Cell {
	private boolean isVisible; //default already false
	
	public Cell() {
		isVisible = false; //sol has this for some reason lol
	}
	
	public boolean isVisible() {
		return this.isVisible;
	}
	
	public void setVisible(boolean isVisible) { //if boolean has Is, remove Is when naming setter
		this.isVisible = isVisible;
	}
	
	
}
