package engine;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.world.Cell;

public class Game {
	
	public static ArrayList<Hero> availableHeroes =  new ArrayList<Hero>();
	public static ArrayList<Hero> heroes = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static Cell [][] map;
	
	public static void loadHeroes(String filePath) throws Exception { //check order of parameters
		//parsing a CSV file into Scanner class constructor
		File f = new File(filePath);
		BufferedReader csvReader = new BufferedReader(new FileReader(f));
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
		 
		
		  
			 
			
		    // do something with the data
		
		
			
		
			
		
	
	public static void main(String[] args){
		try {
			loadHeroes("C:\\Users\\nalaa\\eclipse-workspace\\Game\\src\\engine\\Heros.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		/*	System.out.println(availableHeros.get(0).getName());   //check order of parameter from csv file
			System.out.println(availableHeros.get(0).getMaxHp());
			System.out.println(availableHeros.get(0).getMaxActions());
			System.out.println(availableHeros.get(0).getAttackDmg());
			System.out.println(availableHeros.get(0) instanceof Fighter);
			System.out.println(availableHeros.get(1).getName());
			System.out.println(availableHeros.get(1).getMaxHp());
			System.out.println(availableHeros.get(1).getMaxActions());
			System.out.println(availableHeros.get(1).getAttackDmg());
			System.out.println(availableHeros.get(1) instanceof Medic);
			System.out.println(availableHeros.get(2).getName());
			System.out.println(availableHeros.get(2).getMaxHp());
			System.out.println(availableHeros.get(2).getMaxActions());
			System.out.println(availableHeros.get(2).getAttackDmg());*/
	
		
	}}

