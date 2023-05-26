package engine;
import java.awt.List;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;

import model.characters.*; //we can use * instead of importing each indvidually
import model.characters.Character;//for some reason we need this bec. the one above isnt working .how? * works for hero!!!
import model.collectibles.*;
import model.world.*;
import views.SceneController;
import exceptions.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game extends Application {


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

		public static void spawnZombie() {
			boolean flag = true;
			int h;
			int w;
			do {
				h = Game.randomPosition();
				w = Game.randomPosition();
				if (Game.map[h][w] instanceof CharacterCell) {
					if (((CharacterCell)(Game.map[h][w])).getCharacter() == null)
						flag = false;
				}
			}
			while (flag);

			Zombie z = new Zombie();
			z.setLocation(new Point(h, w));
			Game.zombies.add(z);
			Game.map[h][w] = new CharacterCell(z);
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

			return (count == 0 || checkWin() || heroes.isEmpty()); /*|| (availableHeroes.isEmpty()) || (heroes.size() + getRemainingVaccines()< 5));*/// added || availableHeroes.isEmpty()
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
					z.attack();
					z.setTarget(null);
            }

            spawnZombie();

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


				Group attackDownFighter;
				int selectIndex = 0;

		        Hero leftHero;
		        Hero midHero;
		        Hero rightHero;
		        Hero startHero = new Medic("a", 1, 1, 1);




				public void start(Stage stage) {
					try {
						//GENERAL SCENES/////////////////////////////////////////////////////

						StackPane menuRoot = new StackPane();
						Scene menuScene = new Scene(menuRoot, 1536, 864);
						Pane menu = new Pane();

						StackPane startRoot = new StackPane();
						Scene startScene = new Scene(startRoot,  1536, 864);
						Pane singleMultiPane = new Pane();

						StackPane charSelectRoot = new StackPane();
						Scene charSelectScene = new Scene(charSelectRoot, 1536, 864);
						Pane charSelectPane = new Pane();

						BorderPane playRoot = new BorderPane();
						Scene playScene = new Scene(playRoot, 1536, 864);


						Group settingsRoot = new Group();
						Scene settingsScene = new Scene(settingsRoot, 1536, 864);



						/////////////////////////////////////////////////////////////////////




						//MENU SCENE////////////////////////////////////////////////////////////

						Button start = new Button("START GAME");
					    Button settings = new Button("SETTINGS");
					    Button exit = new Button("EXIT");

					    File mainMenuFile = new File("/C:/Users/nalaa/OneDrive/Desktop/GameAssets/LastOfUsMenu.mp4");//nadeem path
						Media mainMenuVideo = new Media(mainMenuFile.toURI().toString());
					    MediaPlayer mainMenuPlayer = new MediaPlayer(mainMenuVideo);
					    MediaView mainMenuMV = new MediaView(mainMenuPlayer);

					    DoubleProperty mvw = mainMenuMV.fitWidthProperty();
					    DoubleProperty mvh = mainMenuMV.fitHeightProperty();
					    mvw.bind(Bindings.selectDouble(mainMenuMV.sceneProperty(), "width"));
					    mvh.bind(Bindings.selectDouble(mainMenuMV.sceneProperty(), "height"));
					    mainMenuMV.setPreserveRatio(true);

					    String hoverSoundPath = "src/S1.mp3";//relative path
						//String path = "/C:/Users/nalaa/OneDrive/Desktop/GameAssets/useful/S1.mp3";
					    Media hoverSound = new Media(new File(hoverSoundPath).toURI().toString());
					    MediaPlayer hoverSoundPlayer = new MediaPlayer(hoverSound);

					    String clickSoundPath = "src/S2.mp3";//relative path
						  //String path2 = "/C:/Users/nalaa/OneDrive/Desktop/GameAssets/useful/S2.mp3";
						Media clickSound = new Media(new File(clickSoundPath).toURI().toString());
						MediaPlayer clickSoundPlayer = new MediaPlayer(clickSound);




					    Font titleFont = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 150);
					    Font optionFont = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 75);
					    Font optionHoverFont = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 85);

						Button back = new Button("BACK");
						back.setFont(optionFont);
						back.setTextFill(Color.WHITE);
						back.setStyle("-fx-background-color: transparent");
//						title.setLayoutX(200);
//					    title.setLayoutY(70);

						Stack<Scene> backStack = new Stack<Scene>();

						back.setOnMouseClicked(new EventHandler <Event>(){
							public void handle(Event e){
								clickSoundPlayer.play();
					    		clickSoundPlayer.seek(clickSoundPlayer.getStartTime());
								if (!backStack.isEmpty())
									stage.setScene(backStack.pop());

							}

						});

					    back.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		back.setFont(optionHoverFont);
					    		hoverSoundPlayer.play();
					    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());



					    	}
					    });

					    back.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		back.setFont(optionFont);


					    	}
					    });





						Label title = new Label("THE \nLAST \nOF US \nLEGACY");
						title.setFont(titleFont);
						title.setTextFill(Color.WHITE);
						title.setLayoutX(200);
					    title.setLayoutY(70);

					    start.setFont(optionFont);
					    settings.setFont(optionFont);
					    exit.setFont(optionFont);

					    start.setTextFill(Color.WHITE);
					    settings.setTextFill(Color.WHITE);
					    exit.setTextFill(Color.WHITE);

					    start.setLayoutX(700);
					    settings.setLayoutX(1000);
					    exit.setLayoutX(1250);

					    start.setLayoutY(700);
					    settings.setLayoutY(700);
					    exit.setLayoutY(700);

					    start.setStyle("-fx-background-color: transparent");
					    settings.setStyle("-fx-background-color: transparent");
					    exit.setStyle("-fx-background-color: transparent");

					    //START ACTION///////////////////////////////////////////////

					    start.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		clickSoundPlayer.play();
					    		clickSoundPlayer.seek(clickSoundPlayer.getStartTime());

					    		menu.getChildren().removeAll(title, exit);
							    menuRoot.getChildren().remove(mainMenuMV);

							    singleMultiPane.getChildren().addAll(title, exit);
							    startRoot.getChildren().addAll(mainMenuMV, singleMultiPane);

							    //SceneController.switchStartScene(stage);
							    stage.setScene(startScene);
					    		//stage.setScene(startScene); ******CHANGE SCENE

					    	}
					    });


					    start.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		start.setFont(optionHoverFont);
					    		hoverSoundPlayer.play();
					    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());



					    	}
					    });

					    start.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		start.setFont(optionFont);


					    	}
					    });

					  //SETTINGS ACTION///////////////////////////////////////////////

					    settings.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		clickSoundPlayer.play();
					    		clickSoundPlayer.seek(clickSoundPlayer.getStartTime());
					    		//stage.setScene(settingsScene); **CHANGE SCENE

					    	}
					    });

					    settings.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		settings.setFont(optionHoverFont);
					    		hoverSoundPlayer.play();
					    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());

					    	}
					    });

					    settings.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		settings.setFont(optionFont);

					    	}
					    });

					    //EXIT ACTION///////////////////////////////////////////////

					    exit.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		exit.setFont(optionHoverFont);
					    		hoverSoundPlayer.play();
					    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());

					    	}
					    });

					    exit.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		exit.setFont(optionFont);

					    	}
					    });




					    Duration RestartVideoTime = Duration.millis(7401);

					    Timeline restartMenuVid = new Timeline(new KeyFrame(RestartVideoTime, event -> mainMenuPlayer.play()));
					    restartMenuVid.play();
					    mainMenuPlayer.setOnEndOfMedia(() -> mainMenuPlayer.seek(RestartVideoTime));







				        String songPath = "/C:/Users/nalaa/OneDrive/Desktop/GameAssets/The_Last_Of_us_Theme_song.mp3";//relative path
					    Media mediaSong = new Media(new File(songPath).toURI().toString());
					    MediaPlayer songPlayer = new MediaPlayer(mediaSong);
					    songPlayer.play();

					    Duration StartSongTime = Duration.millis(7000);

					    Timeline restartMenuSong = new Timeline(new KeyFrame(StartSongTime, event -> songPlayer.play()));
					    restartMenuSong.play();//to play song after Team105 //song bt5lls then gap then restart
					    songPlayer.setOnEndOfMedia(() -> songPlayer.seek(Duration.ZERO));//restart song

					    mainMenuPlayer.play();

					    mainMenuPlayer.setOnEndOfMedia(new Runnable() {
				            public void run() {
				            	mainMenuPlayer.seek(Duration.millis(7401));
				            }
				        });

					    mainMenuVideo.getMarkers().put("Menu Display", Duration.millis(7400));


					    mainMenuPlayer.setOnMarker((MediaMarkerEvent event1) -> {
					    		menu.getChildren().addAll(title, start, settings, exit);
					      });



					    menuRoot.getChildren().addAll(mainMenuMV, menu);

					    stage.setScene(menuScene);


					    //////////////////////////////////////////////////////////////////////


					    //Start Scene/////////////////////////////////////////////////////////

					    Button singlePlayer = new Button("SINGLEPLAYER");
					    Button multiPlayer = new Button("MULTIPLAYER");



					    singlePlayer.setFont(optionFont);
					    multiPlayer.setFont(optionFont);
					    singlePlayer.setTextFill(Color.WHITE);
					    multiPlayer.setTextFill(Color.WHITE);
					    singlePlayer.setStyle("-fx-background-color: transparent");
					    multiPlayer.setStyle("-fx-background-color: transparent");

					    singlePlayer.setLayoutX(650);
					    multiPlayer.setLayoutX(958);

					    singlePlayer.setLayoutY(700);
					    multiPlayer.setLayoutY(700);


					    //SINGLEPLAYER button
					    singlePlayer.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		clickSoundPlayer.play();
					    		clickSoundPlayer.seek(clickSoundPlayer.getStartTime());


							    backStack.push(startScene);

					    		stage.setScene(charSelectScene);



					    	}
					    });


					    singlePlayer.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		singlePlayer.setFont(optionHoverFont);
					    		hoverSoundPlayer.play();
					    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());


					    	}
					    });

					    singlePlayer.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		singlePlayer.setFont(optionFont);


					    	}
					    });


					    //MULTIPLAYER button //currentl single and multi open map
					    multiPlayer.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		clickSoundPlayer.play();
					    		clickSoundPlayer.seek(clickSoundPlayer.getStartTime());
					    		stage.setScene(charSelectScene);

					    	}
					    });


					    multiPlayer.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		multiPlayer.setFont(optionHoverFont);
					    		hoverSoundPlayer.play();
					    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());


					    	}
					    });

					    multiPlayer.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		multiPlayer.setFont(optionFont);


					    	}
					    });

					    singleMultiPane.getChildren().addAll(singlePlayer, multiPlayer);





					    ////////////////////////////////////////////////////////////////////////



					    //CHARSELECT SCENE//////////////////////////////////////////////////////

					    File CharacterSelectVideo = new File("src/CharacterSelectionVideo.mp4");
				        Media CharacterSelectMedia = new Media(CharacterSelectVideo.toURI().toString());
				        MediaPlayer CharacterSelectPlayer = new MediaPlayer(CharacterSelectMedia);
				        MediaView CharacterSelectionView = new MediaView(CharacterSelectPlayer);

				        DoubleProperty mvw2 = CharacterSelectionView.fitWidthProperty();
				        DoubleProperty mvh2 = CharacterSelectionView.fitHeightProperty();
				        mvw2.bind(Bindings.selectDouble(CharacterSelectionView.sceneProperty(), "width"));
				        mvh2.bind(Bindings.selectDouble(CharacterSelectionView.sceneProperty(), "height"));

				        CharacterSelectionView.setPreserveRatio(true);

				        CharacterSelectPlayer.play();

				        CharacterSelectPlayer.setCycleCount(Timeline.INDEFINITE );



//				        CharacterSelectPlayer.setOnEndOfMedia(new Runnable() {
//				            public void run() {
//				            	CharacterSelectPlayer.seek(Duration.ZERO);
//				            }
//				        });


				        loadHeroes("/C:/Users/nalaa/OneDrive/Desktop/Team105/GameAssets/useful/charSelec/Heros.csv");



				        ImageView[] visibleImages = new ImageView[3];

				        if (selectIndex == 0)
				        	leftHero = availableHeroes.get(availableHeroes.size() - 1);

				        else
				        	leftHero = availableHeroes.get(selectIndex - 1);

				        midHero = availableHeroes.get(selectIndex);
				        rightHero = availableHeroes.get((selectIndex + 1) % availableHeroes.size());

				        Hero[] visibleHeroes = {leftHero, midHero, rightHero};



				        Label heroName = new Label("Name: " + visibleHeroes[1].getName());
				        Label heroHP = new Label("Max HP: " + visibleHeroes[1].getMaxHp());
				        Label heroDmg = new Label("Attack Damage: " + visibleHeroes[1].getAttackDmg());
				        Label heroActions = new Label("Max Actions: " + visibleHeroes[1].getMaxActions());
				        Label heroClass = new Label("Class: Explorer");

				        if (visibleHeroes[1] instanceof Fighter)
				        	heroClass.setText("Class: Fighter");

				        else if (visibleHeroes[1] instanceof Medic)
				        	heroClass.setText("Class: Medic");

				        heroName.setFont(optionFont);
				        heroHP.setFont(optionFont);
				        heroDmg.setFont(optionFont);
				        heroActions.setFont(optionFont);
				        heroClass.setFont(optionFont);

				        heroName.setTextFill(Color.WHITE);
				        heroHP.setTextFill(Color.WHITE);
				        heroDmg.setTextFill(Color.WHITE);
				        heroActions.setTextFill(Color.WHITE);
				        heroClass.setTextFill(Color.WHITE);



				        for (int i = 0; i < 3; i++){
							if (visibleHeroes[i] instanceof Explorer){
					        	visibleImages[i] = new ImageView(new Image("file:/C:/Users/nalaa/OneDrive/Desktop/Team105/GameAssets/useful/charSelec/ExpMoveDown0.png"));

							}

							else if (visibleHeroes[i] instanceof Medic){
								visibleImages[i] = new ImageView(new Image("file:/C:/Users/nalaa/OneDrive/Desktop/Team105/GameAssets/useful/charSelec/MedMoveDown0.png"));

							}

							else if (visibleHeroes[i] instanceof Fighter){
								visibleImages[i] = new ImageView(new Image("file:/C:/Users/nalaa/OneDrive/Desktop/Team105/GameAssets/useful/charSelec/FtmoveDown0.png"));

							}}


					    ImageView nextHero = new ImageView(new Image("/arrowLeft.png"));
					    nextHero.setRotate(180);
					    nextHero.setLayoutX(1100);
					    nextHero.setLayoutY(320);
					    nextHero.setStyle("-fx-background-color: transparent");

					    ImageView prevHero = new ImageView(new Image("/arrowLeft.png"));

					    prevHero.setLayoutX(300);
					    prevHero.setLayoutY(320);
					    prevHero.setStyle("-fx-background-color: transparent");

					    Button selectHero = new Button("SELECT");
					    selectHero.setFont(optionFont);
					    selectHero.setTextFill(Color.WHITE);
					    selectHero.setStyle("-fx-background-color: transparent");
					    selectHero.setLayoutX(1300);
					    selectHero.setLayoutY(0);

					    Button play = new Button("PLAY");
					    play.setFont(optionFont);
					    play.setTextFill(Color.WHITE);
					    play.setStyle("-fx-background-color: transparent");
					    play.setLayoutX(1300);
					    play.setLayoutY(750);



					    play.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle(Event e){
					    		charSelectRoot.getChildren().clear();
					    		//stage.setScene(playScene);

					    		songPlayer.pause();

					    	}

					    });

					    play.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		play.setFont(optionHoverFont);
					    		hoverSoundPlayer.play();
					    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());


					    	}
					    });

					    play.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		play.setFont(optionFont);


					    	}
					    });





					    nextHero.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		nextHero.setScaleX(1.1);
					    		nextHero.setScaleY(1.1);
					    		hoverSoundPlayer.play();
					    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());


					    	}
					    });

					    nextHero.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		nextHero.setScaleX(1);
					    		nextHero.setScaleY(1);


					    	}
					    });



					    nextHero.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle(Event e){
					    		selectIndex = (selectIndex + 1) % availableHeroes.size();

					    		//charSelectPane.getChildren().removeAll(visibleImages[0],visibleImages[1], visibleImages[2]);



					    		if (selectIndex == 0)
						        	leftHero = availableHeroes.get(availableHeroes.size() - 1);

						        else
						        	leftHero = availableHeroes.get(selectIndex - 1);

						        midHero = availableHeroes.get(selectIndex);
						        rightHero = availableHeroes.get((selectIndex + 1) % availableHeroes.size());


						        Hero[] visibleHeroes = {leftHero, midHero, rightHero};



					    		for (int i = 0; i < 3; i++){
									if (visibleHeroes[i] instanceof Explorer){
							        	visibleImages[i] = new ImageView(new Image("file:/C:/Users/nalaa/OneDrive/Desktop/Team105/GameAssets/useful/charSelec/ExpMoveDown0.png"));}

									else if (visibleHeroes[i] instanceof Medic){
										visibleImages[i] = new ImageView(new Image("file:/C:/Users/nalaa/OneDrive/Desktop/Team105/GameAssets/useful/charSelec/MedMoveDown0.png"));}

									else if (visibleHeroes[i] instanceof Fighter){
										visibleImages[i] = new ImageView(new Image("file:/C:/Users/nalaa/OneDrive/Desktop/Team105/GameAssets/useful/charSelec/FtmoveDown0.png"));}}

					    		charSelectPane.getChildren().addAll(visibleImages[0],visibleImages[1], visibleImages[2]);





					    		visibleImages[0].setX(500);
					    		visibleImages[0].setY(300);

					    		visibleImages[0].setScaleX(2);
						        visibleImages[0].setScaleY(2);

						        visibleImages[1].setX(700);
						        visibleImages[1].setY(300);

						        visibleImages[1].setScaleX(4);
						        visibleImages[1].setScaleY(4);

						        visibleImages[2].setX(900);
						        visibleImages[2].setY(300);

						        visibleImages[2].setScaleX(2);
						        visibleImages[2].setScaleY(2);


						        charSelectPane.getChildren().removeAll(heroName, heroClass, heroHP, heroDmg, heroActions);

						        Label heroName = new Label("Name: " + visibleHeroes[1].getName());
						        Label heroHP = new Label("Max HP: " + visibleHeroes[1].getMaxHp());
						        Label heroDmg = new Label("Attack Damage: " + visibleHeroes[1].getAttackDmg());
						        Label heroActions = new Label("Max Actions: " + visibleHeroes[1].getMaxActions());
						        Label heroClass = null;

						        if (visibleHeroes[1] instanceof Fighter)
						        	heroClass = new Label("Class: Fighter");

						        else if (visibleHeroes[1] instanceof Medic)
						        	heroClass = new Label("Class: Medic");

						        else if (visibleHeroes[1] instanceof Explorer)
						        	heroClass = new Label("Class: Explorer");


						        heroName.setFont(optionFont);
						        heroHP.setFont(optionFont);
						        heroDmg.setFont(optionFont);
						        heroActions.setFont(optionFont);
						        heroClass.setFont(optionFont);

						        heroName.setTextFill(Color.WHITE);
						        heroHP.setTextFill(Color.WHITE);
						        heroDmg.setTextFill(Color.WHITE);
						        heroActions.setTextFill(Color.WHITE);
						        heroClass.setTextFill(Color.WHITE);


						        heroName.setLayoutX(200);
						        heroName.setLayoutY(500);

						        heroClass.setLayoutX(650);
						        heroClass.setLayoutY(500);

						        heroHP.setLayoutX(200);
						        heroHP.setLayoutY(610);

						        heroDmg.setLayoutX(500);
						        heroDmg.setLayoutY(610);

						        heroActions.setLayoutX(920);
						        heroActions.setLayoutY(610);

						        selectHero.setOnMouseClicked(new EventHandler <Event>(){
							    	public void handle(Event e){
							    		GlobalVariableController.setStartHero(visibleHeroes[1]);
							    		System.out.println((GlobalVariableController.getStartHero().getName()));
							    		charSelectPane.getChildren().removeAll(nextHero, prevHero);
							    		charSelectPane.getChildren().add(play);
							    		startGame(GlobalVariableController.getStartHero());
							    	}

							    });

							    selectHero.setOnMouseEntered(new EventHandler <Event>(){
							    	public void handle (Event event){
							    		selectHero.setFont(optionHoverFont);
							    		hoverSoundPlayer.play();
							    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());


							    	}
							    });

							    selectHero.setOnMouseExited(new EventHandler <Event>(){
							    	public void handle (Event event){
							    		selectHero.setFont(optionFont);


							    	}
							    });



						        charSelectPane.getChildren().clear();
						        charSelectPane.getChildren().addAll(visibleImages[0],visibleImages[1], visibleImages[2], nextHero, prevHero, back, selectHero);
						        charSelectPane.getChildren().addAll(heroName, heroClass, heroHP, heroDmg, heroActions);

					    	}



					    });


					    prevHero.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		prevHero.setScaleX(1.1);
					    		prevHero.setScaleY(1.1);
					    		hoverSoundPlayer.play();
					    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());


					    	}
					    });

					    prevHero.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		prevHero.setScaleX(1);
					    		prevHero.setScaleY(1);


					    	}
					    });




					    prevHero.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle(Event e){
					    		selectIndex = selectIndex - 1;
					    		if (selectIndex < 0)
					    			selectIndex = availableHeroes.size() - 1;

					    		charSelectPane.getChildren().removeAll(heroName, heroClass, heroHP, heroDmg, heroActions);
					    		charSelectPane.getChildren().removeAll(visibleImages[0],visibleImages[1], visibleImages[2]);

					    		if (selectIndex == 0)
						        	leftHero = availableHeroes.get(availableHeroes.size() - 1);

						        else
						        	leftHero = availableHeroes.get(selectIndex - 1);

						        midHero = availableHeroes.get(selectIndex);
						        rightHero = availableHeroes.get((selectIndex + 1) % availableHeroes.size());


						        Hero[] visibleHeroes = {leftHero, midHero, rightHero};

					    		for (int i = 0; i < 3; i++){
									if (visibleHeroes[i] instanceof Explorer){
							        	visibleImages[i] = new ImageView(new Image("file:/C:/Users/nalaa/OneDrive/Desktop/Team105/GameAssets/useful/charSelec/ExpMoveDown0.png"));

									}

									else if (visibleHeroes[i] instanceof Medic){
										visibleImages[i] = new ImageView(new Image("file:/C:/Users/nalaa/OneDrive/Desktop/Team105/GameAssets/useful/charSelec/MedMoveDown0.png"));

									}

									else if (visibleHeroes[i] instanceof Fighter){
										visibleImages[i] = new ImageView(new Image("file:/C:/Users/nalaa/OneDrive/Desktop/Team105/GameAssets/useful/charSelec/FtmoveDown0.png"));

									}}


					    		visibleImages[0].setX(500);
					    		visibleImages[0].setY(300);

					    		visibleImages[0].setScaleX(2);
						        visibleImages[0].setScaleY(2);

						        visibleImages[1].setX(700);
						        visibleImages[1].setY(300);

						        visibleImages[1].setScaleX(4);
						        visibleImages[1].setScaleY(4);

						        visibleImages[2].setX(900);
						        visibleImages[2].setY(300);

						        visibleImages[2].setScaleX(2);
						        visibleImages[2].setScaleY(2);

						        charSelectPane.getChildren().clear();
						        charSelectPane.getChildren().addAll(visibleImages[0],visibleImages[1], visibleImages[2], nextHero, prevHero, back);


						        Label heroName = new Label("Name: " + visibleHeroes[1].getName());
						        Label heroHP = new Label("Max HP: " + visibleHeroes[1].getMaxHp());
						        Label heroDmg = new Label("Attack Damage: " + visibleHeroes[1].getAttackDmg());
						        Label heroActions = new Label("Max Actions: " + visibleHeroes[1].getMaxActions());
						        Label heroClass = null;


						        if (visibleHeroes[1] instanceof Fighter)
						        	heroClass = new Label("Class: Fighter");

						        else if (visibleHeroes[1] instanceof Medic)
						        	heroClass = new Label("Class: Medic");

						        else if (visibleHeroes[1] instanceof Explorer)
						        	heroClass = new Label("Class: Explorer");


						        heroName.setFont(optionFont);
						        heroHP.setFont(optionFont);
						        heroDmg.setFont(optionFont);
						        heroActions.setFont(optionFont);
						        heroClass.setFont(optionFont);

						        heroName.setTextFill(Color.WHITE);
						        heroHP.setTextFill(Color.WHITE);
						        heroDmg.setTextFill(Color.WHITE);
						        heroActions.setTextFill(Color.WHITE);
						        heroClass.setTextFill(Color.WHITE);



						        heroName.setLayoutX(200);
						        heroName.setLayoutY(500);

						        heroClass.setLayoutX(650);
						        heroClass.setLayoutY(500);

						        heroHP.setLayoutX(200);
						        heroHP.setLayoutY(610);

						        heroDmg.setLayoutX(500);
						        heroDmg.setLayoutY(610);

						        heroActions.setLayoutX(920);
						        heroActions.setLayoutY(610);

						        selectHero.setOnMouseClicked(new EventHandler <Event>(){
							    	public void handle(Event e){
							    		GlobalVariableController.setStartHero(visibleHeroes[1]);
							    		System.out.println((GlobalVariableController.getStartHero().getName()));
							    		charSelectPane.getChildren().removeAll(nextHero, prevHero);
							    		charSelectPane.getChildren().add(play);
							    		startGame(GlobalVariableController.getStartHero());
							    	}

							    });

							    selectHero.setOnMouseEntered(new EventHandler <Event>(){
							    	public void handle (Event event){
							    		selectHero.setFont(optionHoverFont);
							    		hoverSoundPlayer.play();
							    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());


							    	}
							    });

							    selectHero.setOnMouseExited(new EventHandler <Event>(){
							    	public void handle (Event event){
							    		selectHero.setFont(optionFont);


							    	}
							    });



						        charSelectPane.getChildren().addAll(heroName, heroClass, heroHP, heroDmg, heroActions, selectHero);

					    	}



					    });




					    //DEFAULT SELECTION////////////////////////////////////////////////////////////////



						visibleImages[0].setX(500);
			    		visibleImages[0].setY(300);

			    		visibleImages[0].setScaleX(2);
				        visibleImages[0].setScaleY(2);

				        visibleImages[1].setX(700);
				        visibleImages[1].setY(300);

				        visibleImages[1].setScaleX(4);
				        visibleImages[1].setScaleY(4);

				        visibleImages[2].setX(900);
				        visibleImages[2].setY(300);

				        visibleImages[2].setScaleX(2);
				        visibleImages[2].setScaleY(2);





				        heroName.setLayoutX(200);
				        heroName.setLayoutY(500);

				        heroClass.setLayoutX(650);
				        heroClass.setLayoutY(500);

				        heroHP.setLayoutX(200);
				        heroHP.setLayoutY(610);

				        heroDmg.setLayoutX(500);
				        heroDmg.setLayoutY(610);

				        heroActions.setLayoutX(920);
				        heroActions.setLayoutY(610);


				        selectHero.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle(Event e){
					    		GlobalVariableController.setStartHero(visibleHeroes[1]);
					    		System.out.println((GlobalVariableController.getStartHero().getName()));
					    		charSelectPane.getChildren().removeAll(nextHero, prevHero);
					    		charSelectPane.getChildren().add(play);
					    		startGame(GlobalVariableController.getStartHero());
					    	}

					    });

					    selectHero.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		selectHero.setFont(optionHoverFont);
					    		hoverSoundPlayer.play();
					    		hoverSoundPlayer.seek(hoverSoundPlayer.getStartTime());


					    	}
					    });

					    selectHero.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		selectHero.setFont(optionFont);


					    	}
					    });


				        charSelectPane.getChildren().addAll(heroName, heroClass, heroHP, heroDmg, heroActions);
				        charSelectPane.getChildren().addAll(back, visibleImages[0], visibleImages[1],visibleImages[2], nextHero, prevHero, selectHero);

				        charSelectRoot.getChildren().addAll(CharacterSelectionView,  charSelectPane);

				        ////////////////////////////////////////////////////////////////////////////////



				        //PLAY/////////////////////////////////////////////////////////////////////////////]




				        ImageView map = new ImageView(new Image("/FINALMAP.png", 956, 864, false, false));
				        ImageView leftHUD = new ImageView(new Image("/sideHUD.png", 290, 864, false, false));
				        ImageView rightHUD = new ImageView(new Image("/sideHUD.png", 290, 864, false, false));
				        ImageView specialAction = new ImageView(new Image("/IconSpecialFt.png", 100, 100, false, false));
				        ImageView cureAction = new ImageView(new Image("/IconCure.png", 100, 100, false, false));
				        ImageView attackAction = new ImageView(new Image("/IconAttack.png", 100, 100, false, false));
				        ImageView target = new ImageView(new Image("/IconTarget.png", 100, 100, false, false));

				        ImageView moveLeft = new ImageView(new Image("/arrowLeft.png", 100, 100, false, false));
				        ImageView moveRight = new ImageView(new Image("/arrowLeft.png", 100, 100, false, false));
				        ImageView moveUp = new ImageView(new Image("/arrowLeft.png", 100, 100, false, false));
				        ImageView moveDown = new ImageView(new Image("/arrowLeft.png", 100, 100, false, false));



				        moveRight.setRotate(180);
				        moveUp.setRotate(90);
				        moveDown.setRotate(270);

				        ImageView hero1 = new ImageView(new Image("/ExpMoveDown0.png", 40, 40, false, false));
				        System.out.println(GlobalVariableController.getStartHero().getName());

				        if (GlobalVariableController.getStartHero() instanceof Fighter){
				        	hero1 = new ImageView(new Image("/FtmoveDown0.png", 40, 40, false, false));

				        	}
				        else if (GlobalVariableController.getStartHero() instanceof Medic){
				        	hero1 = new ImageView(new Image("/MedMoveDown0.png", 40, 40, false, false));}








				        rightHUD.setRotate(180);

				        playRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY)));

					    Font HUDFont = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 60);


				        Text cureLabel = new Text("CURE");
				        cureLabel.setFont(HUDFont);
				        cureLabel.setFill(Color.WHITE);
				        cureLabel.setLayoutX(130);
				        cureLabel.setLayoutY(110);

				        Text attackLabel = new Text("ATTACK");
				        attackLabel.setFont(HUDFont);
				        attackLabel.setFill(Color.WHITE);
				        attackLabel.setLayoutX(130);
				        attackLabel.setLayoutY(235);

				        Text specialLabel = new Text("SPECIAL");
				        specialLabel.setFont(HUDFont);
				        specialLabel.setFill(Color.WHITE);
				        specialLabel.setLayoutX(130);
				        specialLabel.setLayoutY(360);

				        Text targetLabel = new Text("TARGET");
				        targetLabel.setFont(HUDFont);
				        targetLabel.setFill(Color.WHITE);
				        targetLabel.setLayoutX(130);
				        targetLabel.setLayoutY(485);

				        cureAction.setX(18);
				        cureAction.setY(35);

				        attackAction.setX(18);
				        attackAction.setY(160);

				        specialAction.setX(18);
				        specialAction.setY(285);

				        target.setX(18);
				        target.setY(410);

				        moveUp.setX(80);
				        moveUp.setY(600);

				        moveDown.setX(80);
				        moveDown.setY(750);

				        moveRight.setX(160);
				        moveRight.setY(675);

				        moveLeft.setX(0);
				        moveLeft.setY(675);



				        hero1.setX(545);
				        hero1.setY(175);

				        hero1.setX(545 + 29 );
				        hero1.setY(175 + 31 * 14);


				        cureAction.setOnMouseEntered(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		cureAction.setScaleX(1.1);
				        		cureAction.setScaleY(1.1);
				        	}
				        });

				        cureAction.setOnMouseExited(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		cureAction.setScaleX(1);
				        		cureAction.setScaleY(1);
				        	}
				        });

				        attackAction.setOnMouseEntered(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		attackAction.setScaleX(1.1);
				        		attackAction.setScaleY(1.1);
				        	}
				        });

				        attackAction.setOnMouseExited(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		attackAction.setScaleX(1);
				        		attackAction.setScaleY(1);
				        	}
				        });

				        specialAction.setOnMouseEntered(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		specialAction.setScaleX(1.1);
				        		specialAction.setScaleY(1.1);
				        	}
				        });

				        specialAction.setOnMouseExited(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		specialAction.setScaleX(1);
				        		specialAction.setScaleY(1);
				        	}
				        });

				        target.setOnMouseEntered(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		target.setScaleX(1.1);
				        		target.setScaleY(1.1);
				        	}
				        });

				        target.setOnMouseExited(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		target.setScaleX(1);
				        		target.setScaleY(1);
				        	}
				        });

				        moveUp.setOnMouseEntered(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		moveUp.setScaleX(1.1);
				        		moveUp.setScaleY(1.1);
				        	}
				        });

				        moveUp.setOnMouseExited(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		moveUp.setScaleX(1);
				        		moveUp.setScaleY(1);
				        	}
				        });

				        moveDown.setOnMouseEntered(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		moveDown.setScaleX(1.1);
				        		moveDown.setScaleY(1.1);
				        	}
				        });

				        moveDown.setOnMouseExited(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		moveDown.setScaleX(1);
				        		moveDown.setScaleY(1);
				        	}
				        });

				        moveRight.setOnMouseEntered(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		moveRight.setScaleX(1.1);
				        		moveRight.setScaleY(1.1);
				        	}
				        });

				        moveRight.setOnMouseExited(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		moveRight.setScaleX(1);
				        		moveRight.setScaleY(1);
				        	}
				        });

				        moveLeft.setOnMouseEntered(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		moveLeft.setScaleX(1.1);
				        		moveLeft.setScaleY(1.1);
				        	}
				        });

				        moveLeft.setOnMouseExited(new EventHandler <Event>(){
				        	public void handle(Event e){
				        		moveLeft.setScaleX(1);
				        		moveLeft.setScaleY(1);
				        	}
				        });








				        playRoot.setLeft(leftHUD);
				        playRoot.setRight(rightHUD);
				        playRoot.setCenter(map);
				        playRoot.getChildren().addAll(specialAction, attackAction, cureAction, target, hero1, moveUp, moveDown, moveRight, moveLeft, cureLabel, attackLabel, specialLabel, targetLabel);


				        //////////////////////////////////////////////////////////////////////////////////
					    //double zoomFactor = 1.0;

						//Settings

					    //need to add buttons for play,pause, stop and adjust volume
//					    ThemeSong.pause();
//					    ThemeSong.play();
//					    ThemeSong.stop();

					    ImageView settingsBox = new ImageView(new Image("/medBox.png", 750, 500, false, false));
					    settingsBox.setX(300);

					    	settingsBox.setOnScroll((ScrollEvent event) -> {
					            // Adjust the zoom factor as per your requirement
					            double zoomFactor = 1.00;
					            double deltaY = event.getDeltaY();


					            if (deltaY < 0 && settingsBox.getScaleX() >= 0.5){
					                zoomFactor -= 0.1;
					            }
					            else
					            	if (settingsBox.getScaleX() < 2 && deltaY > 0 )
					            		zoomFactor += 0.1;
					            settingsBox.setScaleX(settingsBox.getScaleX() * zoomFactor);
					            settingsBox.setScaleY(settingsBox.getScaleY() * zoomFactor);
					        });




					    settingsRoot.getChildren().add(settingsBox);

					    //start scene


					    //startRoot.getChildren().add(map);
					  // startRoot.getChildren().add(map);




						stage.setScene(menuScene);
						stage.setFullScreen(true);
						stage.show();


					} catch(Exception e) {
						e.printStackTrace();
					}
				}

				public static void main(String[] args) {
					launch(args);

				}
			}




