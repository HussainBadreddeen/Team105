package model.world;

import model.collectibles.Collectible;

public class CollectibleCell extends Cell {
	private Collectible collectible;
	
	public CollectibleCell(boolean isVisible, Collectible collectible) {
		this.setIsVisible(isVisible);
		this.collectible = collectible;
	}
	
	public Collectible getCollectible() {
		return this.collectible;
	}
}
