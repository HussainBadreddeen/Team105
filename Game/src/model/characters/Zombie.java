package model.characters;

public class Zombie extends Character{
/*public*/ static int ZOMBIES_COUNT = 1; //remov private sol doesnt have private here
	
	public Zombie() {
		super("Zombie " + ZOMBIES_COUNT++, 40, 10);
		//ZOMBIES_COUNT++; here in sol but our method works the same
	}

	public static int getZombiesCount() { //probably not needed
		// TODO Auto-generated method stub
		return ZOMBIES_COUNT;
	}

	public static void setZombiesCount() {//should only decrease by 1 //probably not needed
		ZOMBIES_COUNT --;
	}
	
	
}
