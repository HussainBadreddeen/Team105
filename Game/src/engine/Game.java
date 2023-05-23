package engine;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;

import model.characters.*; //we can use * instead of importing each indvidually
import model.characters.Character;//for some reason we need this bec. the one above isnt working .how? * works for hero!!!
import model.collectibles.*;
import model.world.*;
import exceptions.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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




		private Stack<Scene> sceneStack = new Stack<>();
				public void start(Stage stage) {
					try {
						
						StackPane menuRoot = new StackPane();
						Scene menuScene = new Scene(menuRoot, 1536, 864);
						Pane menu = new Pane();

						Group startRoot = new Group();
						Scene startScene = new Scene(startRoot, 1000, 600, Color.BLACK);
						
						//for character selection
						StackPane CharacterSelectionRoot = new StackPane();
						Scene afterstartScene = new Scene(CharacterSelectionRoot, 1536, 864);
						Pane CharacterSelection = new Pane();
	

						Group settingsRoot = new Group();
						Scene settingsScene = new Scene(settingsRoot, 1000, 600, Color.BLACK);

						Font font = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 150);
						//Font font = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 150);
						//Font font = Font.loadFont("file:C:/Users/hussa/OneDrive/Desktop/Team105/GameAssets/gameFont.ttf", 150);
						
//						Image GameIcon = new Image("file:src/LastofUsLegacy.ico");
//						stage.getIcons().add(GameIcon);
						//the logo for the game doesn't work yet
						
						
						//Main Menu Scene

						Label title = new Label("THE \nLAST \nOF US \nLEGACY");
						title.setFont(font);
						title.setTextFill(Color.WHITE);
						title.setLayoutX(200);
					    title.setLayoutY(70);

					    Button start = new Button("START GAME");
					    Button settings = new Button("SETTINGS");
					    Button exit = new Button("EXIT");
  
					    Font font1 = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 75);
					    // Font font1 = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 75);
					    //Font font1 = Font.loadFont("file:C:/Users/hussa/OneDrive/Desktop/Team105/GameAssets/gameFont.ttf", 75);

					    start.setFont(font1);
					    settings.setFont(font1);
					    exit.setFont(font1);

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
					    
					    Button back = new Button("BACK");// Stack will start with the 2nd scene
					    back.setFont(font1);
					    back.setTextFill(Color.WHITE);
					    back.setStyle("-fx-background-color: transparent");
					    
					    back.setLayoutX(500);					    
					    back.setLayoutY(500);
					    
				//	    sceneStack.push(menuScene);//da el base 3shan lmma ye3ml back yerg3llo// shltha w hagrb f each
		    
					    //styling after pressing start game
					    Button singlePlayer = new Button("SINGLEPLAYER");
					    Button multiPlayer = new Button("MULTIPLAYER");					    
					
					    singlePlayer.setFont(font1);
					    multiPlayer.setFont(font1);
					    singlePlayer.setTextFill(Color.WHITE);
					    multiPlayer.setTextFill(Color.WHITE);
					    singlePlayer.setStyle("-fx-background-color: transparent");
					    multiPlayer.setStyle("-fx-background-color: transparent");
					    
					    singlePlayer.setLayoutX(650);
					    multiPlayer.setLayoutX(958);
					    
					    singlePlayer.setLayoutY(700);
					    multiPlayer.setLayoutY(700);
					    
					    start.setOnAction(new EventHandler<ActionEvent>() {
			                public void handle(ActionEvent event) {
			                    menu.getChildren().removeAll(start,settings);
			                    menu.getChildren().addAll(singlePlayer, multiPlayer,back);
			                
			                    
			                }
			            });
					    
		
					    //after pressing start game
					    Button Play = new Button("PLAY");
					   					    
					
					    Play.setFont(font1);
					    Play.setTextFill(Color.WHITE);
					    Play.setStyle("-fx-background-color: transparent");
					    
					    Play.setLayoutX(650);					    
					    Play.setLayoutY(700);
					    
					    //after pressing singleplayer
					    singlePlayer.setOnAction(new EventHandler<ActionEvent>() {
			                public void handle(ActionEvent event) {
			                	//sceneStack.push(menuScene);//da el base 3shan lmma ye3ml back yerg3llo
			                	sceneStack.push(afterstartScene);
			                	//sceneStack.push(MAP B2A)
			                	CharacterSelection.getChildren().removeAll(Play);
			                	CharacterSelection.getChildren().add(back);
			                	//load map here
			                }
			            });
					    //after pressing Multiplayer
//					  multiPlayer.setOnAction(new EventHandler<ActionEvent>() {
//			                public void handle(ActionEvent event) {
//			                //	sceneStack.push(menuScene);//da el base 3shan lmma ye3ml back yerg3llo
//			                	sceneStack.push(afterstartScene);
//			                	//sceneStack.push(MAP B2A)
//			                	CharacterSelection.getChildren().removeAll(Play);
//			                	CharacterSelection.getChildren().add(back);
//			                	//load map here
//			                }
//			            });
					    
				
					    
					    
					    
					    //audio theme song
					    String pathSong = "src/The_Last_Of_us_Theme_song.mp3";//relative path
					    Media mediaSong = new Media(new File(pathSong).toURI().toString());
					    MediaPlayer ThemeSong = new MediaPlayer(mediaSong);
					    Duration StartSongTime = Duration.millis(2800); //delay song start till after Team105
					    
					    
					    String path = "src/S1.mp3";//relative path
					   //String path = "/C:/Users/nalaa/OneDrive/Desktop/GameAssets/useful/S1.mp3";
					    Media media = new Media(new File(path).toURI().toString());
					    MediaPlayer hoverSound = new MediaPlayer(media);

					  
					    String path2 = "src/S1.mp3";//relative path
					  //String path2 = "/C:/Users/nalaa/OneDrive/Desktop/GameAssets/useful/S2.mp3";
					    Media media2 = new Media(new File(path2).toURI().toString());
					    MediaPlayer clickSound = new MediaPlayer(media2);
					   
					    File video = new File("src/LastOfUsMenu.mp4"); //seeno path
					    // File video = new File("/C:/Users/nalaa/OneDrive/Desktop/GameAssets/LastOfUsMenu.mp4");//nadeem path
					    Media media3 = new Media(video.toURI().toString());
					    MediaPlayer vid = new MediaPlayer(media3);
					    MediaView menuVid = new MediaView(vid);

					    DoubleProperty mvw = menuVid.fitWidthProperty();
					    DoubleProperty mvh = menuVid.fitHeightProperty();
					    mvw.bind(Bindings.selectDouble(menuVid.sceneProperty(), "width"));
					    mvh.bind(Bindings.selectDouble(menuVid.sceneProperty(), "height"));
					    menuVid.setPreserveRatio(true);
					    Duration RestartVideoTime = Duration.millis(7800); //restart vid after effects and button show

					    //after choosing sinlge or multiplayer
					    File video2 = new File("src/CharacterSelectionVideo.mp4");
					    Media media4 = new Media(video2.toURI().toString());
					    MediaPlayer vid2 = new MediaPlayer(media4);
					    MediaView CharacterSelectionVid = new MediaView(vid2);
					    DoubleProperty mvw2 = CharacterSelectionVid.fitWidthProperty();
					    DoubleProperty mvh2 = CharacterSelectionVid.fitHeightProperty();
					    mvw2.bind(Bindings.selectDouble(CharacterSelectionVid.sceneProperty(), "width"));
					    mvh2.bind(Bindings.selectDouble(CharacterSelectionVid.sceneProperty(), "height"));
					    CharacterSelectionVid.setPreserveRatio(true);
					    
					   CharacterSelectionRoot.getChildren().addAll(CharacterSelectionVid, CharacterSelection,back);
					    //check tany back here

					    menuRoot.getChildren().addAll(menuVid, menu);
					    vid.play();
					    
					    Timeline timeline1 = new Timeline(new KeyFrame(StartSongTime, event -> vid.play()));
				        timeline1.play();
				        vid.setOnEndOfMedia(() -> vid.seek(RestartVideoTime));//restart video at 7.8 sec after ending
				        
				        
					    Timeline timeline2 = new Timeline(new KeyFrame(StartSongTime, event -> ThemeSong.play()));
				        timeline2.play();//to play song after Team105 //song bt5lls then gap then restart
				        ThemeSong.setOnEndOfMedia(() -> ThemeSong.seek(Duration.ZERO));//restart song 
					
				        //for charcter selection vid but we play it in the button
				        Timeline timeline3 = new Timeline(new KeyFrame(StartSongTime, event -> vid2.play()));
				        vid2.setOnEndOfMedia(() -> vid2.seek(Duration.ZERO));//restart charachterSelectionVid
				        

					    media3.getMarkers().put("Menu Display", Duration.millis(7400));

					    vid.setOnMarker((MediaMarkerEvent event1) -> {
					    		menu.getChildren().addAll(title, start, settings, exit);
					      });



					    Font font2 = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 85);
					    //Font font2 = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 85);
					    //Font font2 = Font.loadFont("file:C:/Users/hussa/OneDrive/Desktop/Team105/GameAssets/gameFont.ttf", 85);

					    //start button
					    start.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		//sceneStack.push(startScene);//keda  start scene awel haga tt7t
					    		clickSound.play();
					    		clickSound.seek(clickSound.getStartTime());
					    		sceneStack.push(menuScene);//da el base 3shan lmma ye3ml back yerg3llo
					    		stage.setScene(startScene);

					    	}
					    });
					

					    start.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		start.setFont(font2);
					    		hoverSound.play();
					    		hoverSound.seek(hoverSound.getStartTime());


					    	}
					    });

					    start.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		start.setFont(font1);


					    	}
					    });

					    //Setting Button
					    settings.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle (Event event){
//					    		sceneStack.push(settingsScene);//keda  settings scene awel haga tt7t
					    		clickSound.play();
					    		clickSound.seek(clickSound.getStartTime());
					    		sceneStack.push(menuScene);//da el base 3shan lmma ye3ml back yerg3llo
					    		stage.setScene(settingsScene);
					    	

					    	}
					    });
	
					    settings.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		settings.setFont(font2);
					    		hoverSound.play();
					    		hoverSound.seek(hoverSound.getStartTime());

					    	}
					    });

					    settings.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		settings.setFont(font1);

					    	}
					    });
					    
					    
					    //Back Button					    
					    back.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		if (!sceneStack.isEmpty()) {
						            Scene previousScene = sceneStack.pop();// sheel el prev
						            stage.setScene(previousScene);// w display it
						        }

					    	}
					    });
//
					    back.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		back.setFont(font2);
					    		hoverSound.play();
					    		hoverSound.seek(hoverSound.getStartTime());

					    	}
					    });

					    back.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		back.setFont(font1);

					    	}
					    });
		    
					    //SINGLEPLAYER button
					    singlePlayer.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		clickSound.play();
					    		clickSound.seek(clickSound.getStartTime());
					    		stage.setScene(afterstartScene);
					    		vid.pause();
					    		timeline1.pause();
					    		vid2.play();
					    		timeline3.play();
					    	    CharacterSelectionRoot.getChildren().addAll(Play);//7ateit back fo2 fa msh lazem hena
					    	    sceneStack.push(afterstartScene);//keda afterstartScene tany haga tt7t
					    	}//note e7na lesa sybeen multi w singleplayer nafs el haga
					    });
					

					    singlePlayer.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		singlePlayer.setFont(font2);
					    		hoverSound.play();
					    		hoverSound.seek(hoverSound.getStartTime());


					    	}
					    });

					    singlePlayer.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		singlePlayer.setFont(font1);


					    	}
					    });
					    
//					    multiPlayer.setOnAction(new EventHandler<ActionEvent>() {
//			                public void handle(ActionEvent event) {
//			                //	sceneStack.push(menuScene);//da el base 3shan lmma ye3ml back yerg3llo
//			                	//sceneStack.push(afterstartScene);
//			                	//sceneStack.push(MAP B2A)
//			                	CharacterSelection.getChildren().removeAll(Play);
//			                	CharacterSelection.getChildren().add(back);
//			                	//load map here
//			                }
//			            });
					    //MULTIPLAYER button //currentl single and multi open map
					    multiPlayer.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		clickSound.play();
					    		clickSound.seek(clickSound.getStartTime());
					    		stage.setScene(afterstartScene);
					    		timeline1.pause();
					    		vid.pause();
					    		vid2.play();
				    		    timeline3.play();
								CharacterSelectionRoot.getChildren().addAll(Play);//7ateit back fo2 fa msh lazem hena
								sceneStack.push(afterstartScene);//keda afterstartScene tany haga tt7t
					    	}//note e7na lesa sybeen multi w singleplayer nafs el haga
					    });
					

					    multiPlayer.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		multiPlayer.setFont(font2);
					    		hoverSound.play();
					    		hoverSound.seek(hoverSound.getStartTime());


					    	}
					    });

					    multiPlayer.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		multiPlayer.setFont(font1);


					    	}
					    });
					    
					    //Play button //opens map from character selection
					    Play.setOnMouseClicked(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		clickSound.play();
					    		clickSound.seek(clickSound.getStartTime());
					    	//	stage.setScene(afterstartScene);//adj to map scene
					    		vid2.pause();
				    		    timeline3.pause();
				    		    //sceneStack.push(SCENE BTA3T EL MAPPPPPPPPP);//keda MAPSCENE taleit haga tt7t
					    	}
					    });
					

					    Play.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		Play.setFont(font2);
					    		hoverSound.play();
					    		hoverSound.seek(hoverSound.getStartTime());


					    	}
					    });

					    Play.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		Play.setFont(font1);


					    	}
					    });
					    
					    
					    
					    

					    //Exit Button

					    exit.setOnMouseEntered(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		exit.setFont(font2);
					    		hoverSound.play();
					    		hoverSound.seek(hoverSound.getStartTime());

					    	}
					    });

					    exit.setOnMouseExited(new EventHandler <Event>(){
					    	public void handle (Event event){
					    		exit.setFont(font1);

					    	}
					    });



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




					    settingsRoot.getChildren().addAll(settingsBox,back);//check dee 3shan 3lla 7asb el shakl b2a

					    //start scene

					    ImageView map = new ImageView(new Image("/map.png", 1000, 1000, false, false));
					    //startRoot.getChildren().add(map);
					   startRoot.getChildren().add(map);




						stage.setScene(menuScene);
						stage.show();


					    } catch(Exception e) {
						e.printStackTrace();
					}
				}

				public static void main(String[] args) {
					launch(args);

				}
			}




