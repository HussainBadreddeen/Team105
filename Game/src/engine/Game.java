package engine;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import model.characters.*; //we can use * instead of importing each indvidually
import model.characters.Character;//for some reason we need this bec. the one above isnt working .how? * works for hero!!!
import model.collectibles.*;
import model.world.*;
import exceptions.*;


public class Game {
	
	
	public static ArrayList<Hero> availableHeroes =  new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static Cell[][] map = new Cell[15][15];
	//No longer bayza 

	
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
			//map = new Cell[15][15]; //intialized fo2 khlass when it solved zombieAttackDirections
			
			
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
			h.makeAdjacentVisible();//3shan hero yshoof el 7wleih lmma y spawn
		} 
		
		
		public static boolean checkWin() {
			int count = 0;
			for (int i = 0; i < heroes.size(); i++) {
				count += heroes.get(i).getVaccineInventory().size();
			}
			return getRemainingVaccines() == 0 && (count == 0) && (heroes.size() >= 5);// && () ;//The player only wins if he has successfully collected and used all vaccines 
			//and has 5 or more heroes alive
		}
		
		public static boolean checkGameOver() {
			if (getRemainingVaccines() != 0)
				return false;
			
			int count = 0;
			for (int i = 0; i < heroes.size(); i++) {
				count += heroes.get(i).getVaccineInventory().size();
			}
			
			return (count == 0 || checkWin() || (heroes.size()==0) || availableHeroes.isEmpty());// added || availableHeroes.isEmpty()
		}
		
		
		public static void setAllCellVisbility(boolean isVisible) {
            for (int i = 0; i< 15; i++) {
                for (int j = 0; j< 15; j++) {
                    map[i][j].setVisible(isVisible);
                }
            }
        }
		
		
		public static int getRemainingVaccines() {
            int count = 0;
			for (int i = 0; i< 15; i++) {
                for (int j = 0; j< 15; j++) {
                    if (map[i][j] instanceof CollectibleCell) {
                    	if (((CollectibleCell)map[i][j]).getCollectible() instanceof Vaccine)
                    		count += 1;
                    }
                }
            }
			return count;
        }
		
		public static void endTurn() throws InvalidTargetException,NotEnoughActionsException{
            for (int i = 0; i < zombies.size();i++) {
                Zombie z = zombies.get(i);
				//try {
					z.attack();
					z.setTarget(null);
				//} catch (InvalidTargetException e) {
					// TODO Auto-generated catch block
					
				//} catch (NotEnoughActionsException e) {
					// TODO Auto-generated catch block
				
				//}
				
               // z.setTarget(null);
            }

            
//we only spawn new zombie in 2 cases: if a zombie is dead or when a turn ends
            //if (zombies.size() < 10) {//tab ma momken el hero my3mlsh cure bsor3a enough so zombies ykoon >10. s3tha msh hy spawn more zombies
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
            //}

            setAllCellVisbility(false);

            for (int i = 0; i < heroes.size();i++) {
                Hero h = heroes.get(i);
                h.setSpecialAction(false);
                h.setActionsAvailable(h.getMaxActions());
                h.setTarget(null);
                h.makeAdjacentVisible();//kda adj to hero vis
                
            } 
       }
		
		public static void printMap() {
			String vis = "";
			for (int i = 14; i >= 0; i--) {
                for (int j = 0; j < 15 ; j++) {
                	if (map[i][j] instanceof CharacterCell) {
                		if (((CharacterCell)map[i][j]).getCharacter() instanceof Zombie) {
                			vis = "Zom";}
                		else if (((CharacterCell)map[i][j]).getCharacter() instanceof Hero) {
                			vis = "Her";}
                		
                		else
                			vis = "Emp";
                	}
                	
                	else if (map[i][j] instanceof TrapCell) {
                		vis = "Tra";
                	}
                	
                	else if (map[i][j] instanceof CollectibleCell) {
                		vis = "Col";
                	}
                	
                	/*if (map[i][j].isVisible())
                		vis = "1";
                	else
                		vis = "0";*/
  
                		
                	System.out.print(" [" + vis + "] ");
                } 
                System.out.println();
            }
		}
	
		  
			 
			public static void main(String args[]) {//for testing
				
				
				startGame(new Fighter("n", 200, 10, 5));
				
				Character c1 =  new Fighter("n", 1, 1, 5);
				c1.setLocation(new Point(14, 0));
				map[14][0] = new CharacterCell(c1);
				heroes.add((Hero)c1);
				
				Character c2 =  new Fighter("n", 1, 200, 1);
				c2.setLocation(new Point(14, 1));
				map[14][1] = new CharacterCell(c2);
				heroes.add((Hero)c2);
				
				Character c3 =  new Fighter("n", 1, 200, 1);
				c3.setLocation(new Point(14, 2));
				map[14][2] = new CharacterCell(c3);
				heroes.add((Hero)c3);
				
				
				Character c4 =  new Fighter("n", 1, 200, 1);
				c4.setLocation(new Point(13, 0));
				map[13][0] = new CharacterCell(c4);
				heroes.add((Hero)c4);
				
				Character c5 =  new Fighter("n", 1, 200, 1);
				c5.setLocation(new Point(13, 2));
				map[13][2] = new CharacterCell(c5);
				heroes.add((Hero)c5);
				
				Character c6 =  new Fighter("n", 1, 200, 1);
				c6.setLocation(new Point(12, 0));
				map[12][0] = new CharacterCell(c6);
				heroes.add((Hero)c6);
				
				Character c7 =  new Fighter("n", 1, 200, 1);
				c7.setLocation(new Point(12, 1));
				map[12][1] = new CharacterCell(c7);
				heroes.add((Hero)c7);
				
				Character c8=  new Fighter("n", 1, 200, 1);
				c8.setLocation(new Point(12, 2));
				map[12][2] = new CharacterCell(c8);
				heroes.add((Hero)c8);
				
				
				Zombie z = new Zombie();
				//z.setCurrentHp(1);
				zombies.add(z);
				z.setLocation(new Point(13, 1));
				map[13][1] = new CharacterCell(z);
				z.giveAdjacentCells();
//				endTurn(); //1
//				endTurn(); //2
//				endTurn(); //3
//				endTurn(); //4
//				endTurn(); //5
//				endTurn(); //6
//				endTurn(); //7
//				endTurn(); //8
//				
				
				
				
				
				
				printMap();
				
				
				//System.out.println(map[(int)c.getLocation().getX()][(int)c.getLocation().getY()].isVisible());
				
			}
}
	
		
	