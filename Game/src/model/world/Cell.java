package model.world;

abstract public class Cell {
	private boolean isVisible;
	public Cell() {
		//throw new UnsupportedOperationException("Class Instantiation not supported");
	}
	
	public boolean isVisible() {
		return this.isVisible;
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	
}
