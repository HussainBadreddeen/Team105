package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import model.characters.*; //we can use this instead of importing each indvidually
import model.characters.Character;//for some reason we need this bec. the one above isnt working 
import model.characters.Hero;
import model.collectibles.*;
import model.world.*;
import exceptions.*;


public class Game {
	
	
	public static ArrayList<Hero> availableHeroes =  new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static Cell[][] map;//No longer bayza i think

		
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
			map = new Cell[15][15]; //MAP BAYZAAAAAAAAAAAAAAAAAAAAAAAAAAAAA IF I REMOVE THIS IT STILL PRODUCES THE SAME ERRORS AND FAILURES
			heroes.add(h);
			availableHeroes.remove(h);
			Point l =new Point(0, 0);
			h.setLocation(l);
			map[0][0] = new CharacterCell(h);
			//h.makeAdjacentVisible(); // did this after intializing everything f a5eir startgame and it works
			

			for (int i = 0; i < 5; i++) {//5 vaccines rnd locations
				int x;
				int y;
					x = randomPosition();
					y = randomPosition();
				
					if(map[y][x]==null) {// if cell is null then mfeehash haga
						map[y][x] = new CollectibleCell(new Vaccine());
					}
		}
			
			for (int i = 0; i < 5; i++) {//5 supplies rnd locations
				int x;
				int y;
					x = randomPosition();
					y = randomPosition();
		
				if(map[y][x]==null) {// if cell is null then mfeehash haga
					map[y][x] = new CollectibleCell(new Supply());
				}
			}
			
			for (int i = 0; i < 5; i++) { //5 traps rnd locations
				int x;
				int y;
					x = randomPosition();
					y = randomPosition();
		
				if(map[y][x]==null) {// if cell is null then mfeehash haga
					map[y][x] = new TrapCell();
				}
			}
			
			for (int i = 0; i < 10; i++) {//5 Zombies rnd locations
				int x;
				int y;
				x = randomPosition();
				y = randomPosition();
				if(map[y][x]==null) {// if cell is null then mfeehash haga
					Zombie z = new Zombie();
					map[y][x] = new CharacterCell(z);
					z.setLocation(new Point(y, x));
					zombies.add(z);
				}
			}
			
			for (int i = 0; i < 15; i++) {// anything else intialize charachter cell
				for (int j = 0; j < 15; j++) {
					if(map[i][j]==null) {
					map[i][j] = new CharacterCell(null);
				}
					}
			}
			
			h.makeAdjacentVisible();//hero should see adjacent cells after everything is initialized 	
		} 
		
		public static boolean checkWin() {
			return (Vaccine.getUseCount() == 5) && (heroes.size() >= 5);// && () ;//The player only wins if he has successfully collected and used all vaccines 
			//and has 5 or more heroes alive
		}
		
		public static boolean checkGameOver() {
			return (checkWin() || (heroes.size()==0) || availableHeroes.isEmpty());// added || availableHeroes.isEmpty()
			
		}
		public static void setAllCellVisbility(boolean isVisible) {
            for (int i = 0; i< 15; i++) {
                for (int j = 0; j< 15; j++) {
                    map[i][j].setVisible(isVisible);
                }
            }
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
//we only spawn new zombie in 2 cases: if a zombie is dead or when a turn ends
            if (zombies.size() < 10) {//tab ma momken el hero my3mlsh cure bsor3a enough so zombies ykoon >10. s3tha msh hy spawn more zombies
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
                h.makeAdjacentVisible();//kda adj to hero vis
                //int x =  (int)h.getLocation().getX();
                //int y =  (int)h.getLocation().getY();
                //map[y][x].setVisible(true);  //hero cells nafsha visble? this doesnt change anyth for testing
            }
 
               



        }
		
	
		  
			 
			public static void main(String args[]) {
				
				Character c =  new Fighter("n", 200, 10, 5);
				
				Character z =  new Zombie();
				startGame(new Fighter("n", 200, 10, 5));
				
				Point l = new Point(0, 0);//
				c.isCharacterAtLocation(l);
				Point l2 = new Point(0, 2);
				c.setLocation(l);
				z.setLocation(l2);
				//map[03][0] = new CharacterCell(z);
				Cell[] adj = c.giveAdjacentCells();
				int count = 0;
				for (int i = 0; i < adj.length; i++) {
					if (adj[i] != null) 
						count+=1;
					
				}
					
				System.out.println(c.isCharacterAtLocation(l));//true y3ny f el makan el sa7 ehhhh b2aaaa
				System.out.println(c.isAdjacent(z));//adjacernt works?yes fee eh b2a
			Cell[] x =(c.giveAdjacentCells());//
			for(int i =0; i< x.length;i++) {
				if(x[i]==null) {
					System.out.println("balabeezo");
				}
				else {
					System.out.println("works");
				}
			}
		
				
				//((Hero)c).move(Direction.DOWN);
				//System.out.println(c.getLocation().getX() + " " + c.getLocation().getY());
				
				
			}
	
	
		
	}