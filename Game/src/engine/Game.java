package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import model.characters.*; //we can use this instead of importing each indvidually
import model.characters.Character;
import model.collectibles.*;
import model.world.*;
import exceptions.*;


public class Game {
	public Game() {
		
	}
	
	public static ArrayList<Hero> availableHeroes =  new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static Cell map [][];
			
	//Arrays.fill(new CharacterCell(null));
	
	
	
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
			
			
			
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					map[i][j] = new CharacterCell(null);
				}
			}
			
			heroes.add(h);
			availableHeroes.remove(h);
			h.setLocation(new Point(0, 0));
			map[0][0] = new CharacterCell(h);
			
			for (int i = 0; i < 5; i++) {
				int x;
				int y;
				boolean flag = true;
				do {
					x =  randomPosition();
					y = randomPosition();
					if (map[y][x] instanceof CharacterCell) {
						if (((CharacterCell)(map[y][x])).getCharacter() == null)
							flag = false;
						
					}}
				while (flag);/*|| (y == 0 && x == 0)*/ //hero pos can be place in while condition
				map[y][x] = new CollectibleCell(new Vaccine());
				
			}
			
			for (int i = 0; i < 5; i++) {
				int x;
				int y;
				boolean flag = true;
				do {
					x =  randomPosition();
					y = randomPosition();
					if (map[y][x] instanceof CharacterCell) {
						if (((CharacterCell)(map[y][x])).getCharacter() == null)
							flag = false;
						
					}}
				while (flag);
				map[y][x] = new CollectibleCell(new Supply());
				
			}
			
			for (int i = 0; i < 5; i++) {
				int x;
				int y;
				boolean flag = true;
				do {
					x =  randomPosition();
					y = randomPosition();
					if (map[y][x] instanceof CharacterCell) {
						if (((CharacterCell)(map[y][x])).getCharacter() == null)
							flag = false;
					}	
				}
				while (flag);
				
				map[y][x] = new TrapCell();
				
			}
			
			for (int i = 0; i < 10; i++) {
				int x;
				int y;
				boolean flag = true;
				do {
					x =  randomPosition();
					y = randomPosition();
					if (map[y][x] instanceof CharacterCell) {
						if (((CharacterCell)(map[y][x])).getCharacter() == null)
							flag = false;
						
					}}
				while (flag);
				
				Zombie z = new Zombie();
				z.setLocation(new Point(y, x));
				zombies.add(z);
				map[y][x] = new CharacterCell(z);
				
				}
			
		
			
			
		} 
		
		public static boolean checkWin() {
			return (Vaccine.getUseCount() == 5) && (heroes.size() >= 5);
			
		}
		
		public static boolean checkGameOver() {
			return (checkWin() || (heroes.size() == 0));
			
		}
		public static void endTurn() {
            for (int i = 0; i < zombies.size();i++) {
                Zombie z = zombies.get(i);
                
                try {
					z.attack();
				} catch (InvalidTargetException e) {
					
				} catch (NotEnoughActionsException e) {
					
				}
                z.setTarget(null);
                

            }

            if (zombies.size() < 10) {
                int x;
                int y;
                boolean flag = true;
                do {
                    x = Game.randomPosition();
                    y = Game.randomPosition();
                    if (Game.map[y][x] instanceof CharacterCell) {
                        if (((CharacterCell)(Game.map[y][x])).getCharacter() == null)
                            flag = false;

                    }}
                while (flag);

                Zombie z = new Zombie();
                z.setLocation(new Point(y, x));
                Game.zombies.add(z);
                Game.map[y][x] = new CharacterCell(z);
            }

            setAllCellVisbility(false);

            for (int i = 0; i < heroes.size();i++) {
                Hero h = heroes.get(i);
                h.setSpecialAction(false);
                h.setActionsAvailable(h.getMaxActions());
                h.setTarget(null);
                h.makeAdjacentVisible();
            }



        }
		
		public static void setAllCellVisbility(boolean isVisible) {
            for (int i = 0; i< 15; i++) {
                for (int j = 0; j< 15; j++) {
                    map[i][j].setVisible(isVisible);
                }
            }
        }
		  
			 
			public static void main(String args[]) {
				Character c =  new Fighter("n", 200, 10, 5);
				Character z =  new Zombie();
				startGame(new Fighter("n", 200, 10, 5));
				Point l = new Point(0, 0);
				Point l2 = new Point(1, 0);
				c.setLocation(l);
				z.setLocation(l2);
				z.setTarget(c);
			
				//map[03][0] = new CharacterCell(z);
				/*Cell[] adj = c.giveAdjacentCells();
				int count = 0;
				for (int i = 0; i < adj.length; i++) {
					if (adj[i] != null) 
						count+=1;
					
				}
					
				System.out.println(count);*/
				
				//((Hero)c).move(Direction.DOWN);
				//System.out.println(c.getLocation().getX() + " " + c.getLocation().getY());
				
				
				
			}
	
	
		
	}