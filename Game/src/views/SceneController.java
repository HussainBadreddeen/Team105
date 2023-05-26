package views;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import model.characters.Character;
import engine.Game;
import engine.GlobalVariableController;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaMarkerEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.CollectibleCell;

public class SceneController{
	static Hero startHero;
	static int selectIndex = 0;

    static Hero leftHero;
    static Hero midHero;
    static Hero rightHero;

	static Stack<Scene> backStack = new Stack<Scene>();

	static StackPane menuRoot = new StackPane();
	static Scene menuScene = new Scene(menuRoot, 1536, 864);
	static Pane menu = new Pane();

	static StackPane startRoot = new StackPane();
	static Scene startScene = new Scene(startRoot,  1536, 864);
	static Pane singleMultiPane = new Pane();

	static StackPane charSelectRoot = new StackPane();
	static Scene charSelectScene = new Scene(charSelectRoot, 1536, 864);
	static Pane charSelectPane = new Pane();

	static BorderPane playRoot = new BorderPane();
	static Scene playScene = new Scene(playRoot, 1536, 864);

	static String hoverSoundPath = "src/S1.mp3";//relative path
	//String path = "/C:/Users/nalaa/OneDrive/Desktop/GameAssets/useful/S1.mp3";
    static Media hoverSound = new Media(new File(hoverSoundPath).toURI().toString());
    static MediaPlayer hoverSoundPlayer = new MediaPlayer(hoverSound);

    static String clickSoundPath = "src/S2.mp3";//relative path
	  //String path2 = "/C:/Users/nalaa/OneDrive/Desktop/GameAssets/useful/S2.mp3";
	static Media clickSound = new Media(new File(clickSoundPath).toURI().toString());
	static MediaPlayer clickSoundPlayer = new MediaPlayer(clickSound);



//
//    static Font titleFont = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 150);
//    static Font optionFont = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 75);
//    static Font optionHoverFont = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 85);
    
	static Font titleFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 150);
	static Font optionFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 75);
	static Font optionHoverFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 85);
	static Font settingsFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 130);
	

    static String songPath = "src/The_Last_Of_us_Theme_song.mp3";//relative path
    static Media mediaSong = new Media(new File(songPath).toURI().toString());
    static MediaPlayer songPlayer = new MediaPlayer(mediaSong);

	public SceneController(){




	}

	public static void switchMenuScene(Stage stage){
		Button start = new Button("START GAME");
	    Button settings = new Button("SETTINGS");
	    Button exit = new Button("EXIT");

	    File mainMenuFile = new File("src/LastOfUsMenu.mp4");//nadeem path
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





		Button back = new Button("BACK");
		back.setFont(optionFont);
		back.setTextFill(Color.WHITE);
		back.setStyle("-fx-background-color: transparent");
//		title.setLayoutX(200);
//	    title.setLayoutY(70);

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

			    SceneController.switchStartScene(stage);
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




	    //songPlayer.play();  //TODO

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


	}

	public static void switchStartScene(Stage stage){
		 	Button singlePlayer = new Button("SINGLEPLAYER");
		    Button multiPlayer = new Button("MULTIPLAYER");



		    singlePlayer.setFont(optionFont);
		    multiPlayer.setFont(optionFont);
		    singlePlayer.setTextFill(Color.WHITE);
		    multiPlayer.setTextFill(Color.WHITE);
		    singlePlayer.setStyle("-fx-background-color: transparent");
		    multiPlayer.setStyle("-fx-background-color: transparent");



		    //SINGLEPLAYER button
		    singlePlayer.setOnMouseClicked(new EventHandler <Event>(){
		    	public void handle (Event event){
		    		clickSoundPlayer.play();
		    		clickSoundPlayer.seek(clickSoundPlayer.getStartTime());


				    backStack.push(startScene);

		    		SceneController.switchCharSelectScene(stage);



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
		    		//stage.setScene(charSelectScene);
		    		SceneController.switchCharSelectScene(stage);

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
		    Button Mute = new Button();
		    Image MuteIcon = new Image("/mute.png", 200, 200, false, false);//				
		    ImageView MuteIconView = new ImageView(MuteIcon);
		    Mute.setGraphic(MuteIconView);
//		    Mute.setLayoutX(300);
//		    Mute.setLayoutY(340);
		    Mute.setStyle("-fx-background-color: transparent");
		    MuteIconView.setPreserveRatio(true);
		    
			Label muteTitle = new Label("MUTE");
			muteTitle.setFont(optionFont);
			muteTitle.setTextFill(Color.GOLDENROD);
//			muteTitle.setLayoutX(360);
//			muteTitle.setLayoutY(260);
		    
		    Mute.setOnMouseClicked(new EventHandler<Event>() {
		        public void handle(Event event) {
		        		songPlayer.setVolume(0);
		        }
		    });
		    
		    Mute.setOnMouseEntered(new EventHandler <Event>(){
              public void handle(Event e){
              	Mute.setScaleX(1.1);
              	Mute.setScaleY(1.1);
              }
          });

		    Mute.setOnMouseExited(new EventHandler <Event>(){
              public void handle(Event e){
              	Mute.setScaleX(1);
              	Mute.setScaleY(1);
              }
          });



		    singleMultiPane.getChildren().addAll(singlePlayer, multiPlayer,Mute);



		   // startRoot.getChildren().addAll();

		    stage.setScene(startScene);

		    singlePlayer.setLayoutX(650);
		    multiPlayer.setLayoutX(958);

		    singlePlayer.setLayoutY(700);
		    multiPlayer.setLayoutY(700);

	}

	public static void switchCharSelectScene(Stage stage){
	       //after choosing singlePlayer or multiPlayer Da el hyban



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



//	        CharacterSelectPlayer.setOnEndOfMedia(new Runnable() {
//	            public void run() {
//	            	CharacterSelectPlayer.seek(Duration.ZERO);
//	            }
//	        });

	        try {
//				Game.loadHeroes("/C:/Users/nalaa/OneDrive/Desktop/Team105/GameAssets/useful/charSelec/Heros.csv");
	        	Game.loadHeroes("/C:/Users/hussa/Desktop/Team105/GameAssets/useful/charSelec/Heros.csv");
	        	
			} catch (Exception e1) {

			}



	        ImageView[] visibleImages = new ImageView[3];

	        if (selectIndex == 0)
	        	leftHero = Game.availableHeroes.get(Game.availableHeroes.size() - 1);

	        else
	        	leftHero = Game.availableHeroes.get(selectIndex - 1);

	        midHero = Game.availableHeroes.get(selectIndex);
	        rightHero = Game.availableHeroes.get((selectIndex + 1) % Game.availableHeroes.size());

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
		        	visibleImages[i] = new ImageView(new Image("/ExpMoveDown0.png"));

				}

				else if (visibleHeroes[i] instanceof Medic){
					visibleImages[i] = new ImageView(new Image("/MedMoveDown0.png"));

				}

				else if (visibleHeroes[i] instanceof Fighter){
					visibleImages[i] = new ImageView(new Image("/FtmoveDown0.png"));

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
		    		//charSelectRoot.getChildren().clear();
		    		SceneController.switchPlayScene(stage);
		    		//stage.setScene(playScene);

		    		//songPlayer.pause();

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
		    		selectIndex = (selectIndex + 1) % Game.availableHeroes.size();

		    		//charSelectPane.getChildren().removeAll(visibleImages[0],visibleImages[1], visibleImages[2]);



		    		if (selectIndex == 0)
			        	leftHero = Game.availableHeroes.get(Game.availableHeroes.size() - 1);

			        else
			        	leftHero = Game.availableHeroes.get(selectIndex - 1);

			        midHero =Game.availableHeroes.get(selectIndex);
			        rightHero = Game.availableHeroes.get((selectIndex + 1) % Game.availableHeroes.size());


			        Hero[] visibleHeroes = {leftHero, midHero, rightHero};



		    		for (int i = 0; i < 3; i++){
						if (visibleHeroes[i] instanceof Explorer){
				        	visibleImages[i] = new ImageView(new Image("/ExpMoveDown0.png"));}

						else if (visibleHeroes[i] instanceof Medic){
							visibleImages[i] = new ImageView(new Image("/MedMoveDown0.png"));}

						else if (visibleHeroes[i] instanceof Fighter){
							visibleImages[i] = new ImageView(new Image("/FtmoveDown0.png"));}}

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
				    		startHero = (visibleHeroes[1]);
				    		//System.out.println((GlobalVariableController.getStartHero().getName()));
				    		charSelectPane.getChildren().removeAll(nextHero, prevHero);
				    		charSelectPane.getChildren().add(play);
				    		Game.startGame(startHero);
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
			        charSelectPane.getChildren().addAll(visibleImages[0],visibleImages[1], visibleImages[2], nextHero, prevHero, selectHero);
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
		    			selectIndex = Game.availableHeroes.size() - 1;

		    		charSelectPane.getChildren().removeAll(heroName, heroClass, heroHP, heroDmg, heroActions);
		    		charSelectPane.getChildren().removeAll(visibleImages[0],visibleImages[1], visibleImages[2]);

		    		if (selectIndex == 0)
			        	leftHero = Game.availableHeroes.get(Game.availableHeroes.size() - 1);

			        else
			        	leftHero = Game.availableHeroes.get(selectIndex - 1);

			        midHero = Game.availableHeroes.get(selectIndex);
			        rightHero = Game.availableHeroes.get((selectIndex + 1) % Game.availableHeroes.size());


			        Hero[] visibleHeroes = {leftHero, midHero, rightHero};

		    		for (int i = 0; i < 3; i++){
						if (visibleHeroes[i] instanceof Explorer){
				        	visibleImages[i] = new ImageView(new Image("/ExpMoveDown0.png"));

						}

						else if (visibleHeroes[i] instanceof Medic){
							visibleImages[i] = new ImageView(new Image("/MedMoveDown0.png"));

						}

						else if (visibleHeroes[i] instanceof Fighter){
							visibleImages[i] = new ImageView(new Image("/FtmoveDown0.png"));

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
			        charSelectPane.getChildren().addAll(visibleImages[0],visibleImages[1], visibleImages[2], nextHero, prevHero);


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
				    		startHero = (visibleHeroes[1]);
				    		//System.out.println((GlobalVariableController.getStartHero().getName()));
				    		charSelectPane.getChildren().removeAll(nextHero, prevHero);
				    		charSelectPane.getChildren().add(play);
				    		Game.startGame(startHero);
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
		    		startHero = (visibleHeroes[1]);
		    		//System.out.println((GlobalVariableController.getStartHero().getName()));
		    		charSelectPane.getChildren().removeAll(nextHero, prevHero);
		    		charSelectPane.getChildren().add(play);
		    		Game.startGame(startHero);
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
	        charSelectPane.getChildren().addAll(visibleImages[0], visibleImages[1],visibleImages[2], nextHero, prevHero, selectHero);

	        charSelectRoot.getChildren().addAll(CharacterSelectionView,  charSelectPane);

	        stage.setScene(charSelectScene);


	    }

		public static Hero controlHero;

		static ImageView[] heroIcons = {
				new ImageView(new Image("/joel.png", 90, 90, false, false)),
				new ImageView(new Image("/ellieIcon.png", 90, 90, false, false)),
				new ImageView(new Image("/tessIcon.png", 90, 90, false, false)),
				new ImageView(new Image("/davidIcon.png", 90, 90, false, false)),
				new ImageView(new Image("/billIcon.png", 90, 90, false, false)),
				new ImageView(new Image("/tommyIcon.png", 90, 90, false, false)),
				new ImageView(new Image("/rileyIcon.png", 90, 90, false, false)),
				new ImageView(new Image("/henryIcon.png", 90, 90, false, false))

		};

		static Group info = new Group();
		static ImageView specialAction;

		public static void switchPlayScene(Stage stage){
			controlHero = Game.heroes.get(0);

			ImageView map = new ImageView(new Image("/FINALMAP.png", 956, 864, false, false));
	        ImageView leftHUD = new ImageView(new Image("/sideHUD.png", 290, 864, false, false));
	        ImageView rightHUD = new ImageView(new Image("/sideHUD.png", 290, 864, false, false));
	        if (controlHero instanceof Medic){
	        	specialAction = new ImageView(new Image("/IconSpecialMed.png", 100, 100, false, false));}

	        else if (controlHero instanceof Explorer){
	        	specialAction = new ImageView(new Image("/IconSpecialExp.png", 100, 100, false, false));}

	        else{
	        	specialAction = new ImageView(new Image("/IconSpecialFt.png", 100, 100, false, false));
	        }

	        ImageView cureAction = new ImageView(new Image("/IconCure.png", 100, 100, false, false));
	        ImageView attackAction = new ImageView(new Image("/IconAttack.png", 100, 100, false, false));
	        ImageView target = new ImageView(new Image("/IconTarget.png", 100, 100, false, false));

	        ImageView moveLeft = new ImageView(new Image("/arrowLeft.png", 100, 100, false, false));
	        ImageView moveRight = new ImageView(new Image("/arrowLeft.png", 100, 100, false, false));
	        ImageView moveUp = new ImageView(new Image("/arrowLeft.png", 100, 100, false, false));
	        ImageView moveDown = new ImageView(new Image("/arrowLeft.png", 100, 100, false, false));

	        ImageView endTurn = new ImageView(new Image("/EndTurn.png", 210, 70, false, false));

	        endTurn.setX(1300);
	        endTurn.setY(730);

	        moveRight.setRotate(180);
	        moveUp.setRotate(90);
	        moveDown.setRotate(270);


	        rightHUD.setRotate(180);

	        playRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY)));

//		    Font HUDFont = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 60);
	        Font HUDFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 60);


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



	        endTurn.setOnMouseClicked(new EventHandler <Event>(){
	        	public void handle(Event e){
	        		try {
						Game.endTurn();
						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);

					} catch (InvalidTargetException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();


					} catch (NotEnoughActionsException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();

					}

	        	}
	        });


	        endTurn.setOnMouseEntered(new EventHandler <Event>(){
	        	public void handle(Event e){
	        		endTurn.setScaleX(1.1);
	        		endTurn.setScaleY(1.1);
	        	}
	        });

	        endTurn.setOnMouseExited(new EventHandler <Event>(){
	        	public void handle(Event e){
	        		endTurn.setScaleX(1);
	        		endTurn.setScaleY(1);
	        	}
	        });

	        cureAction.setOnMouseClicked(new EventHandler <Event>(){
	        	public void handle(Event e){
						try {
							controlHero.cure();
							placeMapImages();
							displayHeroInfo(controlHero);
							checkGameFinish(stage);

						} catch (NotEnoughActionsException e1) {
							Stage s = new Stage();
							StackPane promptRoot = new StackPane();
							Scene prompt = new Scene(promptRoot, 400, 300);
							Text message = new Text(e1.getMessage());
							promptRoot.getChildren().add(message);
							s.setScene(prompt);
							s.show();


						} catch (InvalidTargetException e1) {
							Stage s = new Stage();
							StackPane promptRoot = new StackPane();
							Scene prompt = new Scene(promptRoot, 400, 300);
							Text message = new Text(e1.getMessage());
							promptRoot.getChildren().add(message);
							s.setScene(prompt);
							s.show();

						} catch (NoAvailableResourcesException e1) {
							Stage s = new Stage();
							StackPane promptRoot = new StackPane();
							Scene prompt = new Scene(promptRoot, 400, 300);
							Text message = new Text(e1.getMessage());
							promptRoot.getChildren().add(message);
							s.setScene(prompt);
							s.show();

						}




	        	}
	        });


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

	        attackAction.setOnMouseClicked(new EventHandler <Event>(){
	        	public void handle(Event e){
	        		try {
						controlHero.attack();
						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);

					} catch (NotEnoughActionsException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();
						e1.printStackTrace();
					} catch (InvalidTargetException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();
					}
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

	        specialAction.setOnMouseClicked(new EventHandler <Event>(){
	        	public void handle(Event e){
	        		try {
						controlHero.useSpecial();
						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);
					} catch (NoAvailableResourcesException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();}

					 catch (InvalidTargetException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();

						e1.printStackTrace();
					}
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


	        moveUp.setOnMouseClicked(new EventHandler <Event>(){
	        	public void handle(Event e){
	        		try {
						controlHero.move(Direction.UP);
						//controlHero.makeAdjacentVisible();
						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);
					} catch (MovementException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();

					} catch (NotEnoughActionsException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();

					}
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

	        moveDown.setOnMouseClicked(new EventHandler <Event>(){
	        	public void handle(Event e){
	        		try {
						controlHero.move(Direction.DOWN);
						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);
					} catch (MovementException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();



					} catch (NotEnoughActionsException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();


					}
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

	        moveRight.setOnMouseClicked(new EventHandler <Event>(){
	        	public void handle(Event e){
	        		try {
						controlHero.move(Direction.RIGHT);
						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);
					} catch (MovementException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();


					} catch (NotEnoughActionsException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();


					}
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

	        moveLeft.setOnMouseClicked(new EventHandler <Event>(){
	        	public void handle(Event e){
	        		try {
						controlHero.move(Direction.LEFT);
						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);
					} catch (MovementException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();

					} catch (NotEnoughActionsException e1) {
						Stage s = new Stage();
						StackPane promptRoot = new StackPane();
						Scene prompt = new Scene(promptRoot, 400, 300);
						Text message = new Text(e1.getMessage());
						promptRoot.getChildren().add(message);
						s.setScene(prompt);
						s.show();


					}
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
	        playRoot.getChildren().addAll(specialAction, attackAction, cureAction, target, moveUp, moveDown, moveRight, moveLeft, cureLabel, attackLabel, specialLabel, targetLabel, endTurn);
	        //playRoot.getChildren().clear();

	        placeMapImages();

	        displayHeroInfo(controlHero);


	        stage.setScene(playScene);
	}

		static Group heroCells = new Group();

		public static void placeHeroCells(){

			heroCells.getChildren().clear();

		      for (int i = 0; i < Game.heroes.size(); i++){
		        	Hero h = Game.heroes.get(i);
		        	//ArrayList<ImageView> heroIcons = new ArrayList<ImageView>();
		        	ImageView heroIcon = new ImageView(new Image("/joel.png", 90, 90, false, false));
		        	ImageView heroCell = new ImageView(new Image("/cell.png", 100, 100, false, false));
		        	String name = h.getName();

		        	if (name.equals("Joel Miller"))
		        		heroIcon = heroIcons[0];

		        	else if (name.equals("Ellie Williams"))
		        		heroIcon = heroIcons[1];

		        	else if (name.equals("Tess"))
		        		heroIcon = heroIcons[2];

		        	else if (name.equals("David"))
		        		heroIcon = heroIcons[3];

		        	else if (name.equals("Bill"))
		        		heroIcon = heroIcons[4];

		        	else if (name.equals("Tommy Miller"))
		        		heroIcon = heroIcons[5];

		        	else if (name.equals("Riley Abel"))
		        		heroIcon = heroIcons[6];

		        	else if (name.equals("Henry Burell"))
		        		heroIcon = heroIcons[7];






		        heroIcon.setX(325 + 110 * i);
		        heroIcon.setY(730);

		        heroCell.setX(320 + 110 * i);
		        heroCell.setY(725);


		        heroIcons[0].setOnMouseClicked(new EventHandler <Event>(){
			        public void handle(Event e){
			        	controlHero = getHeroFromName("Joel Miller");
			        	displayHeroInfo(controlHero);
			        	changeSpecialIcon();

			        		}
			        	});


		        heroIcons[0].setOnMouseEntered(new EventHandler <Event>(){
		        public void handle(Event e){
		        	heroIcons[0].setScaleX(1.15);
		        	heroIcons[0].setScaleY(1.15);
		        		}
		        	});

		        heroIcons[0].setOnMouseExited(new EventHandler <Event>(){
			        public void handle(Event e){
			        	heroIcons[0].setScaleX(1);
			        	heroIcons[0].setScaleY(1);
			        		}
			        	});

		        heroIcons[1].setOnMouseClicked(new EventHandler <Event>(){
			        public void handle(Event e){
			        	controlHero = getHeroFromName("Ellie Williams");
			        	displayHeroInfo(controlHero);
			        	changeSpecialIcon();
			        		}
			        	});


		        heroIcons[1].setOnMouseEntered(new EventHandler <Event>(){
		        public void handle(Event e){
		        	heroIcons[1].setScaleX(1.15);
		        	heroIcons[1].setScaleY(1.15);
		        		}
		        	});

		        heroIcons[1].setOnMouseExited(new EventHandler <Event>(){
			        public void handle(Event e){
			        	heroIcons[1].setScaleX(1);
			        	heroIcons[1].setScaleY(1);
			        		}
			        	});

		        heroIcons[2].setOnMouseClicked(new EventHandler <Event>(){
			        public void handle(Event e){
			        	controlHero = getHeroFromName("Tess");
			        	displayHeroInfo(controlHero);
			        	changeSpecialIcon();
			        		}
			        	});


		        heroIcons[2].setOnMouseEntered(new EventHandler <Event>(){
		        public void handle(Event e){
		        	heroIcons[2].setScaleX(1.15);
		        	heroIcons[2].setScaleY(1.15);
		        		}
		        	});

		        heroIcons[2].setOnMouseExited(new EventHandler <Event>(){
			        public void handle(Event e){
			        	heroIcons[2].setScaleX(1);
			        	heroIcons[2].setScaleY(1);
			        		}
			        	});

		        heroIcons[3].setOnMouseClicked(new EventHandler <Event>(){
			        public void handle(Event e){
			        	controlHero = getHeroFromName("David");
			        	displayHeroInfo(controlHero);
			        	changeSpecialIcon();
			        		}
			        	});


		        heroIcons[3].setOnMouseEntered(new EventHandler <Event>(){
		        public void handle(Event e){
		        	heroIcons[3].setScaleX(1.15);
		        	heroIcons[3].setScaleY(1.15);
		        		}
		        	});

		        heroIcons[3].setOnMouseExited(new EventHandler <Event>(){
			        public void handle(Event e){
			        	heroIcons[3].setScaleX(1);
			        	heroIcons[3].setScaleY(1);
			        		}
			        	});

		        heroIcons[4].setOnMouseClicked(new EventHandler <Event>(){
			        public void handle(Event e){
			        	controlHero = getHeroFromName("Bill");
			        	displayHeroInfo(controlHero);
			        	changeSpecialIcon();
			        		}
			        	});


		        heroIcons[4].setOnMouseEntered(new EventHandler <Event>(){
		        public void handle(Event e){
		        	heroIcons[4].setScaleX(1.15);
		        	heroIcons[4].setScaleY(1.15);
		        		}
		        	});

		        heroIcons[4].setOnMouseExited(new EventHandler <Event>(){
			        public void handle(Event e){
			        	heroIcons[4].setScaleX(1);
			        	heroIcons[4].setScaleY(1);
			        		}
			        	});

		        heroIcons[5].setOnMouseClicked(new EventHandler <Event>(){
			        public void handle(Event e){
			        	controlHero = getHeroFromName("Tommy Miller");
			        	displayHeroInfo(controlHero);
			        	changeSpecialIcon();
			        		}
			        	});


		        heroIcons[5].setOnMouseEntered(new EventHandler <Event>(){
		        public void handle(Event e){
		        	heroIcons[5].setScaleX(1.15);
		        	heroIcons[5].setScaleY(1.15);
		        		}
		        	});

		        heroIcons[5].setOnMouseExited(new EventHandler <Event>(){
			        public void handle(Event e){
			        	heroIcons[5].setScaleX(1);
			        	heroIcons[5].setScaleY(1);
			        		}
			        	});

		        heroIcons[6].setOnMouseClicked(new EventHandler <Event>(){
			        public void handle(Event e){
			        	controlHero = getHeroFromName("Riley Abel");
			        	displayHeroInfo(controlHero);
			        	changeSpecialIcon();
			        		}
			        	});


		        heroIcons[6].setOnMouseEntered(new EventHandler <Event>(){
		        public void handle(Event e){
		        	heroIcons[6].setScaleX(1.15);
		        	heroIcons[6].setScaleY(1.15);
		        		}
		        	});

		        heroIcons[6].setOnMouseExited(new EventHandler <Event>(){
			        public void handle(Event e){
			        	heroIcons[6].setScaleX(1);
			        	heroIcons[6].setScaleY(1);
			        		}
			        	});

		        heroIcons[7].setOnMouseClicked(new EventHandler <Event>(){
			        public void handle(Event e){
			        	controlHero = getHeroFromName("David");
			        	displayHeroInfo(controlHero);
			        	changeSpecialIcon();
			        		}
			        	});


		        heroIcons[7].setOnMouseEntered(new EventHandler <Event>(){
		        public void handle(Event e){
		        	heroIcons[7].setScaleX(1.15);
		        	heroIcons[7].setScaleY(1.15);
		        		}
		        	});

		        heroIcons[7].setOnMouseExited(new EventHandler <Event>(){
			        public void handle(Event e){
			        	heroIcons[7].setScaleX(1);
			        	heroIcons[7].setScaleY(1);
			        		}
			        	});







		        heroCells.getChildren().addAll(heroCell, heroIcon);

		        playRoot.getChildren().remove(heroCells);
		        playRoot.getChildren().add(heroCells);
		}}

		static Group mapImages = new Group();

		public static void placeMapImages(){

			mapImages.getChildren().clear();

			for (int i = 0; i < Game.heroes.size(); i++){
	        	Hero h = Game.heroes.get(i);

		        h.setImage(new ImageView(new Image("/ExpMoveDown0.png", 40, 40, false, false)));

		        if (h instanceof Fighter){
		        	h.setImage(new ImageView(new Image("/FtmoveDown0.png", 40, 40, false, false)));
		        	}

		        else if (h instanceof Medic){
		        	h.setImage(new ImageView(new Image("/MedMoveDown0.png", 40, 40, false, false)));}

		        mapImages.getChildren().addAll(h.getImage());
		        h.getImage().setX(545 + 29 * h.getLocation().getY());   // 29 for x movement 31 y for y movement
		        h.getImage().setY(175 + 31 * (14 - h.getLocation().getX()));


	        }



	        for (int i = 0; i < Game.zombies.size(); i++){
	        	Zombie z = Game.zombies.get(i);

	        z.setImage(new ImageView(new Image("/tile026.png", 40, 40, false, false)));

        	mapImages.getChildren().add(z.getImage());
        	z.getImage().setX(545 + 29 * z.getLocation().getY());   // 29 for x movement 31 y for y movement
        	z.getImage().setY(175 + 31 * (14 - z.getLocation().getX()));






	        }

	        placeHeroCells();
	        ImageView invisible;

	        for (int i = 14; i >= 0; i--){
	        	for (int j = 0; j < 15; j++){
	        		if (Game.map[j][i] instanceof CollectibleCell && Game.map[j][i].isVisible()){
	        			ImageView collectible = new ImageView(new Image("/Supplybox.png", 40, 40, false, false));
	        			if (((CollectibleCell)Game.map[j][i]).getCollectible() instanceof Vaccine){
	        				collectible = new ImageView(new Image("/vaccineIcon.png", 40, 40, false, false));
	        				}
	        			mapImages.getChildren().addAll(collectible);
	        			collectible.setX((545 + 29 * i));   // 29 for x movement 31 y for y movement
	        			collectible.setY((175 + 31 * (14 - j)));
	        			}

	        		if (!Game.map[j][i].isVisible()){
	        			invisible = new ImageView(new Image("/fog.png", 34, 34, false, false));
	        			mapImages.getChildren().addAll(invisible);
	        			invisible.setX((550 + 29 * i));   // 29 for x movement 31 y for y movement
	        			invisible.setY((180 + 31 * (14 - j)));
	        		}


	        }
	        	playRoot.getChildren().remove(mapImages);
	        	playRoot.getChildren().addAll(mapImages);
	        }
		}


		public static Hero getHeroFromName(String s){
			Hero h = new Fighter("PH", 1, 1, 1);
			for (int i = 0; i < Game.heroes.size(); i++){
				if (Game.heroes.get(i).getName().equals(s))
					h = Game.heroes.get(i);
			}
			return h;

		}

		public static void displayHeroInfo(Hero h){
			info.getChildren().clear();

			Text type;
			if (h instanceof Medic)
				type = new Text("Class: Medic");
			else if (h instanceof Fighter)
				type = new Text("Class: Fighter");
			else
				type = new Text("Class: Explorer");

			Text name = new Text("Name: " + h.getName());
			Text hp = new Text(h.getCurrentHp() + "/" + h.getMaxHp());
			Text actions = new Text(h.getActionsAvailable() + "/" + h.getMaxActions());
			Text supplyCount = new Text(h.getSupplyInventory().size() + "");
			Text vaccineCount = new Text(h.getVaccineInventory().size() + "");

			ImageView hpIcon = new ImageView(new Image("/hpIcon.png", 40, 40, false, false));
			ImageView actionsIcon = new ImageView(new Image("/actionsIcon.png", 60, 60, false, false));
			ImageView vaccineIcon = new ImageView(new Image("/vaccineIcon.png", 60, 60, false, false));
			ImageView supplyIcon = new ImageView(new Image("/Supplybox.png", 80, 80, false, false));



//			Font infoFont = Font.loadFont("file:C:/Users/nalaa/OneDrive/Desktop/GameAssets/gameFont.ttf", 40);
			Font infoFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 40);

			name.setFont(infoFont);
			name.setFill(Color.WHITE);
			name.setX(1295);
			name.setY(85);

			type.setFont(infoFont);
			type.setFill(Color.WHITE);
			type.setX(1295);
			type.setY(145);

			hp.setFont(infoFont);
			hp.setFill(Color.WHITE);
			hp.setX(1345);
			hp.setY(220);

			hpIcon.setX(1300);
			hpIcon.setY(185);

			hp.setFont(infoFont);
			hp.setFill(Color.WHITE);
			hp.setX(1355);
			hp.setY(220);

			actions.setFont(infoFont);
			actions.setFill(Color.WHITE);
			actions.setX(1360);
			actions.setY(280);

			actionsIcon.setX(1290);
			actionsIcon.setY(235);

			vaccineCount.setX(1360);
			vaccineCount.setY(350);
			vaccineCount.setFont(infoFont);
			vaccineCount.setFill(Color.WHITE);

			vaccineIcon.setX(1290);
			vaccineIcon.setY(300);

			supplyCount.setX(1360);
			supplyCount.setY(420);
			supplyCount.setFont(infoFont);
			supplyCount.setFill(Color.WHITE);

			supplyIcon.setX(1280);
			supplyIcon.setY(365);



			info.getChildren().addAll(name, hp, type, hpIcon, actionsIcon, actions, vaccineCount, vaccineIcon, supplyCount, supplyIcon);

			playRoot.getChildren().remove(info);
			playRoot.getChildren().addAll(info);

		}

		public static void changeSpecialIcon(){
			playRoot.getChildren().remove(specialAction);
			if (controlHero instanceof Medic){
	        	specialAction.setImage(new Image("/IconSpecialMed.png", 100, 100, false, false));}

	        else if (controlHero instanceof Explorer){
	        	specialAction.setImage(new Image("/IconSpecialExp.png", 100, 100, false, false));}

	        else{
	        	specialAction.setImage(new Image("/IconSpecialFt.png", 100, 100, false, false));
	        }
			playRoot.getChildren().add(specialAction);

		}

		public static void checkGameFinish(Stage stage){
			if (Game.checkWin()){
				StackPane endRoot = new StackPane();
				Scene endScene = new Scene(endRoot, 1536, 864);
				Pane title = new Pane();


				File mainMenuFile = new File("src/YouWin.mp4");//nadeem path
				Media mainMenuVideo = new Media(mainMenuFile.toURI().toString());
				MediaPlayer mainMenuPlayer = new MediaPlayer(mainMenuVideo);
				MediaView mainMenuMV = new MediaView(mainMenuPlayer);
				mainMenuMV.setPreserveRatio(true);
				mainMenuPlayer.play();
				songPlayer.pause();

				endRoot.getChildren().addAll(mainMenuMV, title);

				stage.setScene(endScene);

			}

		else if (Game.checkGameOver()){
//				//switch to game over scene
			}
		}



}
