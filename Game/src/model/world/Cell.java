package model.world;

public class Cell {
	private boolean isVisible;
	protected Cell() {
		throw new UnsupportedOperationException("Class Instantiation not supported");
	}
	
	public boolean getIsVisible() {
		return this.isVisible;
	}
	
	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	
}
