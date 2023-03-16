package engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.world.Cell;

public class Game {
	

	
	public static ArrayList<Hero> availableHeros =  new ArrayList<Hero>();
	public static ArrayList<Hero> heros = new ArrayList<Hero>();
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static Cell [][] map;
	
	public static void loadHeros(String filePath) throws Exception {
		Scanner sc = new Scanner(new File(filePath));
		sc.useDelimiter(",");
		ArrayList<String> x = new ArrayList<String>();
		while (sc.hasNext()) {
			x.add(sc.next());
		}
		
		while (x.size() >= 4) {
			if (x.remove(1).equals("FIGH")) {
				Hero ch = new Fighter(x.remove(0), Integer.parseInt(x.remove(2)), Integer.parseInt(x.remove(3)), Integer.parseInt(x.remove(4)) );
				availableHeros.add(ch);
			}
			else if (x.get(1).equals("EXP")) {
				Hero ch = new Explorer(x.remove(0), Integer.parseInt(x.remove(2)), Integer.parseInt(x.remove(3)), Integer.parseInt(x.remove(4)) );
				availableHeros.add(ch);
			}
			
			else if (x.get(1).equals("MED")) {
				Hero ch = new Medic(x.remove(0), Integer.parseInt(x.remove(2)), Integer.parseInt(x.remove(3)), Integer.parseInt(x.remove(4)) );
				availableHeros.add(ch);
			}
			;
		
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		loadHeros("\\C:\\Users\\nalaa\\eclipse-workspace\\Game\\src\\engine\\Heros.csv");
		while (availableHeros.size() > 0) {
			System.out.println(availableHeros.get(0).getName());
			System.out.println(availableHeros.get(0).getMaxHp());
			System.out.println(availableHeros.get(0).getMaxActions());
			System.out.println(availableHeros.get(0).getAttackDmg());
			System.out.println(availableHeros.get(1).getName());
			System.out.println(availableHeros.get(1).getMaxHp());
			System.out.println(availableHeros.get(1).getMaxActions());
			System.out.println(availableHeros.get(1).getAttackDmg());
			System.out.println(availableHeros.get(2).getName());
			System.out.println(availableHeros.get(2).getMaxHp());
			System.out.println(availableHeros.get(2).getMaxActions());
			System.out.println(availableHeros.get(2).getAttackDmg());
		}
		
	}
}
