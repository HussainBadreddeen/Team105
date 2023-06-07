package engine;

import model.characters.Explorer;
import model.characters.Hero;

public class GlobalVariableController {
	static Hero startHero = new Explorer("a", 1, 1, 1);

//	public GlobalVariableController(Hero h) {
//		this.startHero = h;
//
//	}

	public GlobalVariableController() {
		//this.startHero = startHero;
	}

	public static Hero getStartHero(){
		return startHero;
	}

	public static void setStartHero(Hero h){
		startHero = h;
	}
}
