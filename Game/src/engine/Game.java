package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import model.characters.*; //we can use this instead of importing each indvidually
import model.collectibles.*;
import model.world.*;


public class Game {
	
	public static ArrayList<Hero> availableHeroes =  new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static Cell [][] map;
	
	public static void loadHeroes(String filePath) throws Exception { //check order of parameters //DONE WE ARE CORRECT IT IS MA3KOOSA
		//parsing a CSV file into Scanner class constructor
		File f = new File(filePath);
		BufferedReader csvReader = new BufferedReader(new FileReader(f));//citaion:https://www.javatpoint.com/how-to-read-csv-file-in-java
		String row;
		while ((row = csvReader.readLine()) != null) {
			String[] hero = row.split(",");
			Hero h;
			switch (hero[1]){
			case "FIGH":
				h = new Fighter(hero[0], Integer.parseInt(hero[2]), Integer.parseInt(hero[4]), Integer.parseInt(hero[3]));
				availableHeroes.add(h);
				break;
				
			case "MED":
				h = new Medic(hero[0], Integer.parseInt(hero[2]), Integer.parseInt(hero[4]), Integer.parseInt(hero[3]));
				availableHeroes.add(h);
				break;
			
			case "EXP":
				h = new Explorer(hero[0], Integer.parseInt(hero[2]), Integer.parseInt(hero[4]), Integer.parseInt(hero[3]));
				availableHeroes.add(h);
				break;	
			}
			
			}
		
		csvReader.close();
		}
		 
	
		public static int randomPosition() {
			return (int)(Math.random() * 15);
		}
		
		
		public static void startGame(Hero h) {
			map = new Cell[15][15];
			h.setLocation(new Point(0, 0));
			map[14][0] = new CharacterCell(h);
			
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					map[i][j] = new CharacterCell(null);
				}
			}
			
			for (int i = 0; i < 5; i++) {
				int x;
				int y;
				do {
					x =  randomPosition();
					y = randomPosition();}
				while (map[y][x] != null /*|| (y == 0 && x == 0)*/); //hero pos can be place in while condition
				map[y][x] = new CollectibleCell(new Vaccine());
				
			}
			
			for (int i = 0; i < 5; i++) {
				int x;
				int y;
				do {
					x =  randomPosition();
					y = randomPosition();}
				while (map[y][x] != null);
				map[y][x] = new CollectibleCell(new Supply());
				
			}
			
			for (int i = 0; i < 5; i++) {
				int x;
				int y;
				do {
					x =  randomPosition();
					y = randomPosition();}
				while (map[y][x] != null);
				map[y][x] = new TrapCell();
				
			}
			
			for (int i = 0; i < 10; i++) {
				int x;
				int y;
				do {
					x =  randomPosition();
					y = randomPosition();}
				while (map[y][x] != null);
				Zombie z = new Zombie();
				z.setLocation(new Point(y, x));
				zombies.add(z);
				map[14 - y][x] = new CharacterCell(z);
				
			}
			
			
		}
		
		public static boolean checkWin() {
			return (Vaccine.getUseCount() == 5) && (heroes.size() >= 5);
			
		}
		
		public static boolean checkGameOver() {
			return (checkWin() || (heroes.size() == 0));
			
		}
		  
			 
			public static void main(String args[]) {
				
				
			}
	
	
		
	}

