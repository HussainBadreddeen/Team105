package model.characters;

import java.awt.Point;


import engine.Game;
import exceptions.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import model.world.*;
import views.SceneController;


public abstract class Character { //changed to convention which is public abstract not abstract public
	private String name;
	private Point location;
	private int maxHp;
	private int currentHp;
	private int attackDmg;
	private Character target;
	private ImageView image;


	public Character(String name, int maxHp, int attackDmg) {
		this.name = name;
		this.maxHp = maxHp;
		this.attackDmg = attackDmg;
		this.currentHp=maxHp;
	}

	public Character() {

	}

	public String getName() {
		return this.name;
	}

	public Point getLocation() {
		return this.location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public int getMaxHp() {
		return this.maxHp;
	}

	public int getCurrentHp() {
		return this.currentHp;
	}

	public void setCurrentHp(int currentHp) {
		if(currentHp <= 0) {
			this.currentHp = 0;
			this.onCharacterDeath();
			}
		else if(currentHp > maxHp)
			this.currentHp = maxHp;
		else
			this.currentHp = currentHp;
	}

	public int getAttackDmg() {
		return this.attackDmg;
	}

	public Character getTarget() {
		return this.target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public void attack() throws NotEnoughActionsException, InvalidTargetException{
		this.target.setCurrentHp(this.target.getCurrentHp() - this.attackDmg);
		this.target.defend(this);

		if (this.target.getCurrentHp() == 0)
			this.target = null;

		}


	public void defend(Character c) { //Ex:this is zombie. c is attacker.
		this.target = c;
		c.setCurrentHp(c.getCurrentHp() - (int)(this.getAttackDmg()/2));//we set attacker currHp with half of Zombie's Dmg when zombie def
		if (c.currentHp == 0)   //logic out of the window
			this.target = null;
	}

	public void onCharacterDeath() {
		Point l = this.getLocation();

		int h = (int)(l.getX());
		int w = (int)(l.getY());

		((CharacterCell)Game.map[h][w]).setCharacter(null);

		if (this instanceof Hero) {
			Game.heroes.remove(this);
			}


		if (this instanceof Zombie) {
			Game.zombies.remove(this);
			Game.spawnZombie();
		}

	}




	public Cell[] giveAdjacentCells() {
		Cell[] adjCells = new Cell[8];
		Point l = this.getLocation();
		int h = (int)l.getX() + 1; //13
		int w = (int)l.getY() - 1; //1
		int count = 0;

		for (int i = 0; i < 3;i++) {
			if (h - i < 15 && h - i >= 0) {
				for (int j = 0; j < 3; j++) {
					if (w + j < 15 && w + j >= 0) {
						adjCells[count] = Game.map[h - i][w + j];
						int a = h - i;
						int b = w + j;
						count++;}
					if (i == 1)
						j++;
			}
		}
	}
		return adjCells;
	}
//removed old commented isAdjacent method

	public boolean isAdjacent(Character c) {
		int x1 = (int)this.getLocation().getY();
		int y1 = (int)this.getLocation().getX();

		int x2 = (int)c.getLocation().getY();
		int y2 = (int)c.getLocation().getX();


		if (x1 - x2 > 1 || x1 - x2 < -1 || y1 - y2 > 1 || y1 - y2 < -1)
			return false;
		return true;

	}

	public void setImage(ImageView image){
		this.image = image;
		Character c = this;
		image.setOnMouseClicked(new EventHandler <Event>(){
			public void handle(Event e){
				SceneController.controlHero.setTarget(c);

			}

		});

	}

	public ImageView getImage(){
		return this.image;

	}

	}


