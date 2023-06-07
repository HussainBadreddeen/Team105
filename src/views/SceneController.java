package views;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Stack;



import model.characters.Character;
import engine.Game;
import engine.GlobalVariableController;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.animation.FadeTransition;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
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
import javafx.scene.shape.Rectangle;
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

	public static BorderPane playRoot = new BorderPane();
	static Scene playScene = new Scene(playRoot, 1536, 864);

	static String hoverSoundPath = "src/S1.mp3";//relative path
	//String path = "/C:/Users/nalaa/OneDrive/Desktop/GameAssets/useful/S1.mp3";
    static Media hoverSound = new Media(new File(hoverSoundPath).toURI().toString());
    static MediaPlayer hoverSoundPlayer = new MediaPlayer(hoverSound);

    static String clickSoundPath = "src/select3R6.mp3";//relative path
	  //String path2 = "/C:/Users/nalaa/OneDrive/Desktop/GameAssets/useful/S2.mp3";
	static Media clickSound = new Media(new File(clickSoundPath).toURI().toString());
	static MediaPlayer clickSoundPlayer = new MediaPlayer(clickSound);


	static String fontPath = "file:src/gameFont.ttf";
//
//	static Font titleFont = new Font(new f"src/gameFont.ttf", 150);
    static Font titleFont = Font.loadFont(fontPath, 150);
    static Font optionFont = Font.loadFont(fontPath, 75);
    static Font optionHoverFont = Font.loadFont(fontPath, 85);
	static Font settingsFont = Font.loadFont(fontPath, 130);


//	static Font titleFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 150);
//	static Font optionFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 75);
//	static Font optionHoverFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 85);
//	static Font settingsFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 130);


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

//	    String clickSoundPath = "src/S2.mp3";//relative path
//		  //String path2 = "/C:/Users/nalaa/OneDrive/Desktop/GameAssets/useful/S2.mp3";
//		Media clickSound = new Media(new File(clickSoundPath).toURI().toString());
//		MediaPlayer clickSoundPlayer = new MediaPlayer(clickSound);





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




	    songPlayer.play();  //TODO

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

	static Button Mute = new Button();


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
		    		songPlayer.stop();

				    backStack.push(startScene);
				    ImageView blackBG = new ImageView(new Image("/blackBG.jpg", 1536, 864, false ,false));
				    singleMultiPane.getChildren().add(blackBG);
				    FadeTransition ft = new FadeTransition(Duration.millis(1200), blackBG);
				    ft.setFromValue(0);
				    ft.setToValue(1);
				    ft.setCycleCount(1);
				    ft.play();

				    ft.setOnFinished(new EventHandler <ActionEvent> (){
				    	public void handle(ActionEvent e){
				    		SceneController.switchCharSelectScene(stage);

				    	}
				    });



		    		 //TODO
//				    Trial.trial(stage);



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
		    Image MuteIcon = new Image("/speakerOn.png", 70, 70, false, false);//
            ImageView MuteIconView = new ImageView(MuteIcon);
            Mute.setGraphic(MuteIconView);
            Mute.setLayoutX(1420);
            Mute.setLayoutY(10);
            Mute.setStyle("-fx-background-color: transparent");
            MuteIconView.setPreserveRatio(true);

            Mute.setOnMouseClicked(new EventHandler<Event>() {
                public void handle(Event event) {
                    if(!(songPlayer.getVolume()==0)){
                        songPlayer.setVolume(0);
                        MuteIconView.setImage((new Image("/speakerOff.png", 70, 70, false, false)));
                    }
                    else{
                        songPlayer.setVolume(80);
                        MuteIconView.setImage((new Image("/speakerOn.png", 70, 70, false, false)));
                    }
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

		    singlePlayer.setLayoutX(630);
		    multiPlayer.setLayoutX(948);

		    singlePlayer.setLayoutY(700);
		    multiPlayer.setLayoutY(700);

	}


	static ArrayList <ImageView> selImages =  new ArrayList <ImageView>();
	static ImageView selectedImage = new ImageView();
	static Timeline randomAnimation = new Timeline();
	static Collection<KeyFrame> frames = randomAnimation.getKeyFrames();
	static Duration frameGap = Duration.millis(60);
	static Duration frameTime = Duration.ZERO;
	static boolean randomSet = true;
	static Text hClass = new Text("Explorer");

	public static void switchCharSelectScene(Stage stage){
	       //after choosing singlePlayer or multiPlayer Da el hyban

		File CharacterSelectVideo = new File("src/CharacterSelectionVideo.mp4");
        Media CharacterSelectMedia = new Media(CharacterSelectVideo.toURI().toString());
        MediaPlayer CharacterSelectPlayer = new MediaPlayer(CharacterSelectMedia);
        MediaView CharacterSelectionView = new MediaView(CharacterSelectPlayer);

    	File CharacterSelectMusic = new File("src/charSelectMusic.mp3");
        Media CharacterSelectAudio= new Media(CharacterSelectMusic.toURI().toString());
        MediaPlayer CharacterSelectSoundPlayer = new MediaPlayer(CharacterSelectAudio);
        MediaView CharacterSelectionSoundView = new MediaView(CharacterSelectSoundPlayer);

//		ImageView ftClass = new ImageView(new Image("/IconSpecialFt.png",  200, 200, false, false));
//		ImageView medClass = new ImageView(new Image("/IconSpecialMed.png",  200, 200, false, false));
//		ImageView expClass = new ImageView(new Image("/IconSpecialExp.png",  200, 200, false, false));
		ImageView blackBG = new ImageView(new Image("/blackBG.jpg", 1536, 864, false ,false));
//		ImageView charBG = new ImageView();
//
//		ftClass.setX(300 + 50);
//		ftClass.setY(200);
//
//		expClass.setX(600 + 50);
//		expClass.setY(200);
//
//		medClass.setX(900 + 50);
//		medClass.setY(200);
//
//
//
//		ftClass.setOnMouseEntered(new EventHandler <Event> (){
//			public void handle(Event e){
//
//			}
//		});


		BorderPane charSelectRoot = new BorderPane();
		Scene charSelectScene = new Scene(charSelectRoot, 1536, 864, Color.BLACK);
		charSelectRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY)));




		  try {
				Game.loadHeroes("src/Heros.csv");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		Image random = new Image("randomSel.png");
        selImages.add(new ImageView(new Image("/joelSel.png")));
        selImages.add(new ImageView(new Image("/ellieSel.png")));
        selImages.add(new ImageView(new Image("/tessSel.png")));
        selImages.add(new ImageView(new Image("/rileySel.png")));
        selImages.add(new ImageView(new Image("/tommySel.png")));
        selImages.add( new ImageView(new Image("/billSel.png")));
        selImages.add(new ImageView(new Image("/davidSel.png")));
        selImages.add( new ImageView(new Image("/henrySel.png")));
        selImages.add(new ImageView(random));


        ImageView prev = new ImageView(new Image("arrowLeft.png", 50, 50, false, false));
        ImageView next = new ImageView(new Image("arrowLeft.png", 50, 50, false, false));
        next.setRotate(180);

        Font smallFont = Font.loadFont(fontPath, 25);

        Text nText = new Text("NEXT");
        nText.setFill(Color.WHITE);
        nText.setFont(smallFont);

        nText.setX(615);
        nText.setY(780);

        next.setX(605);
        next.setY(790);

        Text pText = new Text("PREV");
        pText.setFill(Color.WHITE);
        pText.setFont(smallFont);

        prev.setX(230);
        prev.setY(790);

        pText.setX(250);
        pText.setY(780);


        Font numberFont = Font.loadFont(fontPath, 60);
        Text hpNum = new Text(Game.availableHeroes.get(0).getCurrentHp() + "");
        hpNum.setFill(Color.WHITE);
        hpNum.setFont(numberFont);
        hpNum.setX(300);
        hpNum.setY(660);

        Text hpText = new Text("HEALTH");
        Font staFont = Font.loadFont(fontPath, 40);
        hpText.setFill(Color.WHITE);
        hpText.setFont(staFont);
        hpText.setX(290);
        hpText.setY(700);

        Text dmgNum = new Text(Game.availableHeroes.get(0).getAttackDmg() + "");
        dmgNum.setFill(Color.WHITE);
        dmgNum.setFont(numberFont);
        dmgNum.setX(445);
        dmgNum.setY(660);

        Text dmgText = new Text("DAMAGE");
        dmgText.setFill(Color.WHITE);
        dmgText.setFont(staFont);
        dmgText.setX(420);
        dmgText.setY(700);

        Text actNum = new Text(Game.availableHeroes.get(0).getMaxActions() + "");
        actNum.setFill(Color.WHITE);
        actNum.setFont(numberFont);
        actNum.setX(595);
        actNum.setY(660);

        Text actText = new Text("ACTIONS");
        actText.setFill(Color.WHITE);
        actText.setFont(staFont);
        actText.setX(560);
        actText.setY(700);


        ImageView statWin = new ImageView(new Image("ftWin.png"));
        statWin.setScaleX(0.7);
        statWin.setScaleY(0.7);

        statWin.setX(650);
        statWin.setY(-400);

        ImageView randomWin = new ImageView();
        randomWin.setScaleX(0.7);
        randomWin.setScaleY(0.7);

        randomWin.setX(650);
		randomWin.setY(-400);

        Font nameFont = Font.loadFont(fontPath, 70);
        Font classFont = Font.loadFont(fontPath, 30);

        Text name = new Text(Game.availableHeroes.get(0).getName()); //TODO
        name.setFill(Color.WHITE);
        name.setFont(nameFont);
        name.setX(835);
        name.setY(150);

        hClass = new Text("Explorer");

        if (Game.availableHeroes.get(0) instanceof Fighter){
        	 hClass = new Text("Fighter");
        }

        else if (Game.availableHeroes.get(0) instanceof Medic){
        	hClass = new Text("Medic");
        }


        //TODO
        hClass.setFill(Color.WHITE);
        hClass.setFont(classFont);
        hClass.setX(835);
        hClass.setY(178);

        Text hpRectText = new Text("Health");
        hpRectText.setFill(Color.WHITE);
        hpRectText.setFont(staFont);
        hpRectText.setX(860);
        hpRectText.setY(240);

        Text dmgRectText = new Text("Damage");
        dmgRectText.setFill(Color.WHITE);
        dmgRectText.setFont(staFont);
        dmgRectText.setX(860);
        dmgRectText.setY(340);

        Text actRectText = new Text("Actions");
        actRectText.setFill(Color.WHITE);
        actRectText.setFont(staFont);
        actRectText.setX(860);
        actRectText.setY(440);

        ImageView classIcon = new ImageView(new Image("ftIcon.png", 90, 90, false, false));
        classIcon.setX(1375);
        classIcon.setY(90);


        Rectangle hpRecB = new Rectangle(305, 15, Color.GREY);
        hpRecB.setX(860);
        hpRecB.setY(250);

        Rectangle hpRec = new Rectangle(350, 15, Color.WHITE);
        hpRec.setX(860);
        hpRec.setY(250);
        hpRec.setWidth((hpRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxHp() - 80) * (hpRecB.getWidth())/2) / 70);


        Rectangle dmgRecB = new Rectangle(305, 15, Color.GREY);
        dmgRecB.setX(860);
        dmgRecB.setY(350);

        Rectangle dmgRec = new Rectangle(350, 15, Color.WHITE);
        dmgRec.setX(860);
        dmgRec.setY(350);
        dmgRec.setWidth((dmgRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getAttackDmg() - 10) * (dmgRecB.getWidth())/2) / 25);

        Rectangle actRecB = new Rectangle(305, 15, Color.GREY);
        actRecB.setX(860);
        actRecB.setY(450);

        Rectangle actRec = new Rectangle(350, 15, Color.WHITE);
        actRec.setX(860);
        actRec.setY(450);
        actRec.setWidth((actRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxActions() - 4) * (actRecB.getWidth())/2) / 3);

        File switchSoundFile = new File("src/selectR6.mp3");
        File selectSoundFile = new File("src/select2R6.mp3");


        MediaPlayer switchSound= new MediaPlayer(new Media(switchSoundFile.toURI().toString()));
        MediaPlayer selectSound= new MediaPlayer(new Media(selectSoundFile.toURI().toString()));

        next.setOnMouseClicked(new EventHandler <Event>(){
        	public void handle(Event e){
        		switchSound.play();
        		switchSound.seek(Duration.ZERO);
        		selImages.add(selImages.remove(0));
        		randomWin.setImage(null);
        		selectIndex ++;






        		changeSelImage();
        		if (Math.floorMod(selectIndex, 9) != 8){
        			  hpRec.setWidth((hpRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxHp() - 80) * (hpRecB.getWidth())/2) / 70);
          	        dmgRec.setWidth((dmgRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getAttackDmg() - 10) * (dmgRecB.getWidth())/2) / 25);
          	        actRec.setWidth((actRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxActions() - 4) * (actRecB.getWidth())/2) / 3);
        			randomAnimation.stop();
        			if (!randomSet){
        				selImages.get(8).setImage(random);
        				randomSet = true;
        			}
        			hClass.setText("Explorer");
        			classIcon.setImage(new Image("expIcon.png", 90, 90, false, false));


        	        if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Fighter){
        	        	hClass.setText("Fighter");
        	        	classIcon.setImage(new Image("ftIcon.png", 90, 90, false, false));
        	        }

        	        else if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Medic){
        	        	hClass.setText("Medic");
        	        	classIcon.setImage(new Image("medIcon.png", 90, 90, false, false));
        	        }
        	        name.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getName());
	    			hpNum.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxHp() +"");
	    			dmgNum.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getAttackDmg() +"");
	    			actNum.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxActions() +"");
        			if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Fighter)
        				statWin.setImage(new Image("ftWin.png"));
        			else if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Medic)
        				statWin.setImage(new Image("medWin.png"));
        			else
        				statWin.setImage(new Image("expWin.png"));}

        		else{
        			randomSet = false;
        			statWin.setImage(new Image("randomWin.png"));
        			randomWin.setImage(new Image("randomWin.png"));
        			randomAnimation.setCycleCount(Timeline.INDEFINITE);
        			randomAnimation.play();


        		}
        	}
        });

        next.setOnMouseEntered(new EventHandler <Event>(){
        	public void handle(Event e){
        		next.setScaleX(1.1);
        		next.setScaleY(1.1);

        	}
        });

        next.setOnMouseExited(new EventHandler <Event>(){
        	public void handle(Event e){
        		next.setScaleX(1);
        		next.setScaleY(1);

        	}
        });


        prev.setOnMouseClicked(new EventHandler <Event>(){
        	public void handle(Event e){
        		switchSound.play();
        		switchSound.seek(Duration.ZERO);

        		selImages.add(0, selImages.remove(8));
        		randomWin.setImage(null);
        		selectIndex --;
        		changeSelImage();
        		if (Math.floorMod(selectIndex, 9) != 8){
        			hpRec.setWidth((hpRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxHp() - 80) * (hpRecB.getWidth())/2) / 70);
         	        dmgRec.setWidth((dmgRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getAttackDmg() - 10) * (dmgRecB.getWidth())/2) / 25);
         	        actRec.setWidth((actRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxActions() - 4) * (actRecB.getWidth())/2) / 3);
        			randomAnimation.stop();
        			if (!randomSet){
        				selImages.get(1).setImage(random);
        				randomSet = true;
        			}
        			hClass.setText("Explorer");
        			classIcon.setImage(new Image("expIcon.png", 90, 90, false, false));


        	        if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Fighter){
        	        	hClass.setText("Fighter");
        	        	classIcon.setImage(new Image("ftIcon.png", 90, 90, false, false));
        	        }

        	        else if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Medic){
        	        	hClass.setText("Medic");
        	        	classIcon.setImage(new Image("medIcon.png", 90, 90, false, false));
        	        }
        	        name.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getName());
	    			hpNum.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxHp() +"");
	    			dmgNum.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getAttackDmg() +"");
	    			actNum.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxActions() +"");
        			if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Fighter)
        				statWin.setImage(new Image("ftWin.png"));
        			else if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Medic)
        				statWin.setImage(new Image("medWin.png"));
        			else
        				statWin.setImage(new Image("expWin.png"));

        		}

        		else{
        			randomSet = false;
        			randomWin.setImage(new Image("randomWin.png"));
        			statWin.setImage(null);

        			randomAnimation.setCycleCount(Timeline.INDEFINITE);
        			randomAnimation.play();}



        	}

        });

        prev.setOnMouseEntered(new EventHandler <Event>(){
        	public void handle(Event e){
        		prev.setScaleX(1.1);
        		prev.setScaleY(1.1);

        	}
        });

        prev.setOnMouseExited(new EventHandler <Event>(){
        	public void handle(Event e){
        		prev.setScaleX(1);
        		prev.setScaleY(1);

        	}
        });

        Pane selectPane = new Pane();
        Label select = new Label("SELECT");
        selectPane.getChildren().add(select);
        select.setTextFill(Color.WHITE);
        select.setFont(nameFont);
        select.setLayoutX(385);
        select.setLayoutY(760);
        select.setStyle("-fx-background-color: transparent");

        select.setOnMouseClicked(new EventHandler <Event>(){
        	public void handle(Event e){

        		selectSound.play();
        		CharacterSelectSoundPlayer.stop();

        		ImageView blackBG = new ImageView(new Image("blackBG.jpg", 1536, 864, false, false));

        		FadeTransition ft = new FadeTransition(Duration.millis(2000), blackBG);
        	    ft.setFromValue(0);
        	    ft.setToValue(1);
        	    ft.setCycleCount(1);
        	    ft.play();

        	    if (Math.floorMod(selectIndex, 9) == 8){
	    			selectIndex = (int) (Math.random() * 8);

	    			randomAnimation.stop();

	    			selImages.get(0).setImage(selImages.get(Math.floorMod(selectIndex + 1 , 9)).getImage());
	    			charSelectRoot.getChildren().remove(randomWin);



	    			randomWin.setImage(null);
	    			hpRec.setWidth((hpRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxHp() - 80) * (hpRecB.getWidth())/2) / 70);
         	        dmgRec.setWidth((dmgRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getAttackDmg() - 10) * (dmgRecB.getWidth())/2) / 25);
         	        actRec.setWidth((actRecB.getWidth()/2) + ((Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxActions() - 4) * (actRecB.getWidth())/2) / 3);
        			hClass.setText("Explorer");
        			classIcon.setImage(new Image("expIcon.png", 90, 90, false, false));


        	        if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Fighter){
        	        	hClass.setText("Fighter");
        	        	classIcon.setImage(new Image("ftIcon.png", 90, 90, false, false));
        	        }

        	        else if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Medic){
        	        	hClass.setText("Medic");
        	        	classIcon.setImage(new Image("medIcon.png", 90, 90, false, false));
        	        }
        	        name.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getName());
	    			hpNum.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxHp() +"");
	    			dmgNum.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getAttackDmg() +"");
	    			actNum.setText(Game.availableHeroes.get(Math.floorMod(selectIndex, 9)).getMaxActions() +"");
        			if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Fighter)
        				statWin.setImage(new Image("ftWin.png"));
        			else if (Game.availableHeroes.get(Math.floorMod(selectIndex, 9)) instanceof Medic)
        				statWin.setImage(new Image("medWin.png"));
        			else
        				statWin.setImage(new Image("expWin.png"));


	    		}

        	    charSelectRoot.getChildren().add(blackBG);

        	    ft.setOnFinished(new EventHandler <ActionEvent> (){
        	    	public void handle(ActionEvent e){
        	    		Game.startGame(Game.availableHeroes.remove(Math.floorMod(selectIndex, 9)));
        	    		SceneController.switchPlayScene(stage);
        	    	}
        	    });


//        		Game.heroes.add(Game.availableHeroes.remove(Math.floorMod(selectIndex, 9)));

        	}
        });

        select.setOnMouseEntered(new EventHandler <Event>(){
        	public void handle(Event e){
        		select.setScaleX(1.1);
        		select.setScaleY(1.1);
        	}
        });

        select.setOnMouseExited(new EventHandler <Event>(){
        	public void handle(Event e){
        		select.setScaleX(1);
        		select.setScaleY(1);
        	}
        });



        ImageView hpBox = new ImageView(new Image("statBox.png"));
        hpBox.setX(-170);
        hpBox.setY(-50);
        hpBox.setScaleX(0.55);
        hpBox.setScaleY(0.55);

        ImageView dmgBox = new ImageView(new Image("statBox.png"));
        dmgBox.setX(-30);
        dmgBox.setY(-50);
        dmgBox.setScaleX(0.55);
        dmgBox.setScaleY(0.55);

        ImageView actBox = new ImageView(new Image("statBox.png"));
        actBox.setX(110);
        actBox.setY(-50);
        actBox.setScaleX(0.55);
        actBox.setScaleY(0.55);



        for (ImageView img : selImages) {
            frameTime = frameTime.add(frameGap);
            frames.add(new KeyFrame(frameTime, e -> selImages.get(0).setImage(img.getImage())));
        }




		charSelectRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY)));

		charSelectRoot.getChildren().addAll(
				CharacterSelectionView, CharacterSelectionSoundView,
				selImages.get(0),
				selImages.get(1),
				selImages.get(2),
				selImages.get(3),
				selImages.get(4),
				selImages.get(5),
				selImages.get(6),
				selImages.get(7),
				hpBox,
				dmgBox,
				actBox,
				hpNum,
				hpText,
				dmgNum,
				dmgText,
				actNum,
				actText,
				selImages.get(8),
				selectPane,
				next,
				prev,
				nText,
				pText,
				statWin,
				name,
				hClass,
				hpRecB,
				hpRec,
				dmgRecB,
				dmgRec,
				actRecB,
				actRec,
				hpRectText,
				dmgRectText,
				actRectText,
				classIcon,
				randomWin

				); //TODO

		changeSelImage();




		charSelectRoot.getChildren().addAll(blackBG);
		CharacterSelectPlayer.setCycleCount(Timeline.INDEFINITE);
		CharacterSelectPlayer.play();
		CharacterSelectSoundPlayer.setCycleCount(Timeline.INDEFINITE);
		CharacterSelectSoundPlayer.play();

		FadeTransition ft = new FadeTransition(Duration.millis(2000), blackBG);
	    ft.setFromValue(1.0);
	    ft.setToValue(0);
	    ft.setCycleCount(1);
	    ft.play();

	    ft.setOnFinished(new EventHandler <ActionEvent> (){
	    	public void handle(ActionEvent e){
	    		charSelectRoot.getChildren().remove(blackBG);

	    	}
	    });



		stage.setScene(charSelectScene);
		stage.show();



	    }

	public static void changeSpirte(){

	}


	public static void changeSelImage(){
		selectedImage = selImages.get(0);
		selectedImage.setX(-10);
		selectedImage.setY(-203);
		selectedImage.setScaleX(0.6);
	    selectedImage.setScaleY(0.6);

	    selImages.get(8).setX(-350);
	    selImages.get(8).setY(0);
	    selImages.get(8).setScaleX(0.25);
	    selImages.get(8).setScaleY(0.25);

	    selImages.get(1).setX(270);
	    selImages.get(1).setY(0);
	    selImages.get(1).setScaleX(0.25);
	    selImages.get(1).setScaleY(0.25);

	    selImages.get(2).setX(460);
	    selImages.get(2).setY(0);
	    selImages.get(2).setScaleX(0.25);
	    selImages.get(2).setScaleY(0.25);

	    selImages.get(3).setX(650);
	    selImages.get(3).setY(0);
	    selImages.get(3).setScaleX(0.25);
	    selImages.get(3).setScaleY(0.25);

	    selImages.get(4).setX(840);
	    selImages.get(4).setY(0);
	    selImages.get(4).setScaleX(0.25);
	    selImages.get(4).setScaleY(0.25);

	    selImages.get(5).setX(1030);
	    selImages.get(5).setY(0);
	    selImages.get(5).setScaleX(0.25);
	    selImages.get(5).setScaleY(0.25);

	    selImages.get(6).setX(1220);
	    selImages.get(6).setY(0);
	    selImages.get(6).setScaleX(0.25);
	    selImages.get(6).setScaleY(0.25);

	    selImages.get(7).setX(1410);
	    selImages.get(7).setY(0);
	    selImages.get(7).setScaleX(0.25);
	    selImages.get(7).setScaleY(0.25);




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

		static String expSpPath = "src/radar.mp3";//relative path
	    static Media expSound = new Media(new File(expSpPath).toURI().toString());
	    static MediaPlayer expsoundPlayer = new MediaPlayer(expSound);

		static String punchPath = "src/punch.mp3";//relative path
	    static Media punchSound = new Media(new File(punchPath).toURI().toString());
	    static MediaPlayer punchSoundPlayer = new MediaPlayer(punchSound);


		static String zombiePath = "src/zombie.mp3";//relative path
	    static Media zombieSound = new Media(new File(zombiePath).toURI().toString());
	    static MediaPlayer zombieSoundPlayer = new MediaPlayer(zombieSound);


		static String healPath = "src/heal2.mp3";//relative path
	    static Media healSound = new Media(new File(healPath).toURI().toString());
	    static MediaPlayer healSoundPlayer = new MediaPlayer(healSound);


		static String movePath = "src/moving.mp3";//relative path
	    static Media moveSound = new Media(new File(movePath).toURI().toString());
	    static MediaPlayer moveSoundPlayer = new MediaPlayer(moveSound);


		static String musicPath = "src/gameSound.mp3";//relative path
	    static Media musicSound = new Media(new File(musicPath).toURI().toString());
	    static MediaPlayer musicSoundPlayer = new MediaPlayer(musicSound);

	    static String ftSpPath = "src/fighterSp.mp3";//relative path
	    static Media ftSound = new Media(new File(ftSpPath).toURI().toString());
	    static MediaPlayer ftsoundPlayer = new MediaPlayer(ftSound);



		public static void switchPlayScene(Stage stage){

			musicSoundPlayer.play();
			musicSoundPlayer.setCycleCount(Timeline.INDEFINITE);

			controlHero = Game.heroes.get(0);

			//Game.startGame(controlHero);

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

		    Font HUDFont = Font.loadFont(fontPath, 60);
//	        Font HUDFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 60);


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
						Alert a = new Alert(Alert.AlertType.WARNING);
						a.setContentText(e1.getMessage());
						a.show();


					} catch (NotEnoughActionsException e1) {
						Alert a = new Alert(Alert.AlertType.WARNING);
						a.setContentText(e1.getMessage());
						a.show();
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
//							Alert a = new Alert(Alert.AlertType.WARNING);
//							a.setContentText(e1.getMessage());
//							a.show();
							playRoot.getChildren().addAll(throwAlert(e1.getMessage()));


						} catch (InvalidTargetException e1) {
//							Alert a = new Alert(Alert.AlertType.WARNING);
//							a.setContentText(e1.getMessage());
//							a.show();
							playRoot.getChildren().addAll(throwAlert(e1.getMessage()));
						} catch (NoAvailableResourcesException e1) {
//							Alert a = new Alert(Alert.AlertType.WARNING);
//							a.setContentText(e1.getMessage());
//							a.show();
							playRoot.getChildren().addAll(throwAlert(e1.getMessage()));

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
						punchSoundPlayer.play();
						punchSoundPlayer.seek(Duration.ZERO);

						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);
						if (controlHero.getTarget() != null){
							if (controlHero.getName().equals("Bill")){
								Animations.BillAttack(controlHero);}
							else if (controlHero.getName().equals("David")){
								Animations.DavidAttack(controlHero);}
							else if (controlHero.getName().equals("Ellie Williams")){
								Animations.EllieAttack(controlHero);}
							else if (controlHero.getName().equals("Henry Burell")){
								Animations.HenryAttack(controlHero);}
							else if (controlHero.getName().equals("Joel Miller")){
								Animations.JoelAttack(controlHero);}
							else if (controlHero.getName().equals("Riley Abel")){
								Animations.RileyAttack(controlHero);}
							else if (controlHero.getName().equals("Tess")){
								Animations.TessAttack(controlHero);}
							else if (controlHero.getName().equals("Tommy Miller")){
								Animations.TommyAttack(controlHero);}


							checkGameFinish(stage);
							}

					} catch (NotEnoughActionsException e1) {
//						Alert a = new Alert(Alert.AlertType.WARNING);
//						a.setContentText(e1.getMessage());
//						a.show();
						playRoot.getChildren().addAll(throwAlert(e1.getMessage()));
					} catch (InvalidTargetException e1) {
//						Alert a = new Alert(Alert.AlertType.WARNING);
//						a.setContentText(e1.getMessage());
//						a.show();
						playRoot.getChildren().addAll(throwAlert(e1.getMessage()));
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

						checkGameFinish(stage);
						if (controlHero instanceof Medic){
							Animations.heal(controlHero);
							healSoundPlayer.play();
							healSoundPlayer.seek(Duration.ZERO);

						}

						else if (controlHero instanceof Fighter){
							Animations.aura(controlHero);
							ftsoundPlayer.play();
							ftsoundPlayer.seek(Duration.ZERO);
						}
						else{
							Animations.reveal(controlHero);
							expsoundPlayer.play();
							expsoundPlayer.seek(Duration.ZERO);

						}

						placeMapImages();
						displayHeroInfo(controlHero);

					} catch (NoAvailableResourcesException e1) {
//						Alert a = new Alert(Alert.AlertType.WARNING);
//						a.setContentText(e1.getMessage());
//						a.show();
						playRoot.getChildren().addAll(throwAlert(e1.getMessage()));
					}

					 catch (InvalidTargetException e1) {
//						 Alert a = new Alert(Alert.AlertType.WARNING);
//							a.setContentText(e1.getMessage());
//							a.show();
						 playRoot.getChildren().addAll(throwAlert(e1.getMessage()));
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

	        target.setOnMouseClicked(new EventHandler <Event>(){
	        	public void handle(Event e){
//	        		Alert a = new Alert(Alert.AlertType.INFORMATION);
//	    			a.setContentText("Click on a character to set your target");
//	    			a.show();
	        		throwAlert("Click on a character to set your target");
	        	}
	        });




	        moveUp.setOnMouseClicked(new EventHandler <Event>(){
	        	public void handle(Event e){
	        		try {
						controlHero.move(Direction.UP);
						moveSoundPlayer.play();
						moveSoundPlayer.seek(Duration.ZERO);
						//controlHero.makeAdjacentVisible();
						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);
						if (controlHero.getName().equals("Bill")){
							Animations.BillMoveUp(controlHero);}
						else if (controlHero.getName().equals("David")){
							Animations.DavidMoveUp(controlHero);}
						else if (controlHero.getName().equals("Ellie Williams")){
							Animations.EllieMoveUp(controlHero);}
						else if (controlHero.getName().equals("Henry Burell")){
							Animations.HenryMoveUp(controlHero);}
						else if (controlHero.getName().equals("Joel Miller")){
							Animations.JoelMoveUp(controlHero);}
						else if (controlHero.getName().equals("Riley Abel")){
							Animations.RileyMoveUp(controlHero);}
						else if (controlHero.getName().equals("Tess")){
							Animations.TessMoveUp(controlHero);}
						else if (controlHero.getName().equals("Tommy Miller")){
							Animations.TommyMoveUp(controlHero);}




					} catch (MovementException e1) {
//						Alert a = new Alert(Alert.AlertType.WARNING);
//						a.setContentText(e1.getMessage());
//						a.show();
//						playRoot.getChildren().clear();
						playRoot.getChildren().addAll(throwAlert(e1.getMessage()));


					} catch (NotEnoughActionsException e1) {
//						Alert a = new Alert(Alert.AlertType.WARNING);
//						a.setContentText(e1.getMessage());
//						a.show();
						playRoot.getChildren().addAll(throwAlert(e1.getMessage()));

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
						moveSoundPlayer.play();
						moveSoundPlayer.seek(Duration.ZERO);
						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);
						if (controlHero.getName().equals("Bill")){
							Animations.BillMoveDown(controlHero);}
						else if (controlHero.getName().equals("David")){
							Animations.DavidMoveDown(controlHero);}
						else if (controlHero.getName().equals("Ellie Williams")){
							Animations.EllieMoveDown(controlHero);}
						else if (controlHero.getName().equals("Henry Burell")){
							Animations.HenryMoveDown(controlHero);}
						else if (controlHero.getName().equals("Joel Miller")){
							Animations.JoelMoveDown(controlHero);}
						else if (controlHero.getName().equals("Riley Abel")){
							Animations.RileyMoveDown(controlHero);}
						else if (controlHero.getName().equals("Tess")){
							Animations.TessMoveDown(controlHero);}
						else if (controlHero.getName().equals("Tommy Miller")){
							Animations.TommyMoveDown(controlHero);}


					} catch (MovementException e1) {
//						Alert a = new Alert(Alert.AlertType.WARNING);
//						a.setContentText(e1.getMessage());
//						a.show();
						playRoot.getChildren().addAll(throwAlert(e1.getMessage()));



					} catch (NotEnoughActionsException e1) {
//						Alert a = new Alert(Alert.AlertType.WARNING);
//						a.setContentText(e1.getMessage());
//						a.show();
						playRoot.getChildren().addAll(throwAlert(e1.getMessage()));
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
						moveSoundPlayer.play();
						moveSoundPlayer.seek(Duration.ZERO);
						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);
						if (controlHero.getName().equals("Bill")){
							Animations.BillMoveRight(controlHero);}
						else if (controlHero.getName().equals("David")){
							Animations.DavidMoveRight(controlHero);}
						else if (controlHero.getName().equals("Ellie Williams")){
							Animations.EllieMoveRight(controlHero);}
						else if (controlHero.getName().equals("Henry Burell")){
							Animations.HenryMoveRight(controlHero);}
						else if (controlHero.getName().equals("Joel Miller")){
							Animations.JoelMoveRight(controlHero);}
						else if (controlHero.getName().equals("Riley Abel")){
							Animations.RileyMoveRight(controlHero);}
						else if (controlHero.getName().equals("Tess")){
							Animations.TessMoveRight(controlHero);}
						else if (controlHero.getName().equals("Tommy Miller")){
							Animations.TommyMoveRight(controlHero);}

					} catch (MovementException e1) {
//						Alert a = new Alert(Alert.AlertType.WARNING);
//						a.setContentText(e1.getMessage());
//						a.show();
						playRoot.getChildren().addAll(throwAlert(e1.getMessage()));


					} catch (NotEnoughActionsException e1) {
//						Alert a = new Alert(Alert.AlertType.WARNING);
//						a.setContentText(e1.getMessage());
//						a.show();;
						playRoot.getChildren().addAll(throwAlert(e1.getMessage()));


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
						moveSoundPlayer.play();
						moveSoundPlayer.seek(Duration.ZERO);
						placeMapImages();
						displayHeroInfo(controlHero);
						checkGameFinish(stage);
						if (controlHero.getName().equals("Bill")){
							Animations.BillMoveLeft(controlHero);}
						else if (controlHero.getName().equals("David")){
							Animations.DavidMoveLeft(controlHero);}
						else if (controlHero.getName().equals("Ellie Williams")){
							Animations.EllieMoveLeft(controlHero);}
						else if (controlHero.getName().equals("Henry Burell")){
							Animations.HenryMoveLeft(controlHero);}
						else if (controlHero.getName().equals("Joel Miller")){
							Animations.JoelMoveLeft(controlHero);}
						else if (controlHero.getName().equals("Riley Abel")){
							Animations.RileyMoveLeft(controlHero);}
						else if (controlHero.getName().equals("Tess")){
							Animations.TessMoveLeft(controlHero);}
						else if (controlHero.getName().equals("Tommy Miller")){
							Animations.TommyMoveLeft(controlHero);}

					} catch (MovementException e1) {
//						Alert a = new Alert(Alert.AlertType.WARNING);
//						a.setContentText(e1.getMessage());
//						a.show();
						playRoot.getChildren().addAll(throwAlert(e1.getMessage()));

					} catch (NotEnoughActionsException e1) {
//						Alert a = new Alert(Alert.AlertType.WARNING);
//						a.setContentText(e1.getMessage());
//						a.show();
						playRoot.getChildren().addAll(throwAlert(e1.getMessage()));

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

	        ImageView blackBG = new ImageView(new Image("blackBG.jpg", 1536, 864, false, false));

    		FadeTransition ft = new FadeTransition(Duration.millis(2000), blackBG);
    	    ft.setFromValue(1);
    	    ft.setToValue(0);
    	    ft.setCycleCount(1);
    	    ft.play();
    	    playRoot.getChildren().add(blackBG);

    	    ft.setOnFinished(new EventHandler <ActionEvent>(){
		    	public void handle(ActionEvent e){
		    		playRoot.getChildren().remove(blackBG);
		    	}
		    });



	        stage.setScene(playScene);
	}

		static Group heroCells = new Group();
		static Image[] flowSelects = {
    			(new Image("/flowSelect0.png", 100, 100, false, false)),
    			(new Image("/flowSelect1.png", 100, 100, false, false)),
    			(new Image("/flowSelect2.png", 100, 100, false, false)),
    			(new Image("/flowSelect3.png", 100, 100, false, false)),
    	};

		static ImageView flowSelect = new ImageView(flowSelects[0]);
		static Timeline selectTimeline = new Timeline();
		static boolean animationDone = true;



		public static void placeHeroCells(){


			heroCells.getChildren().clear();
			heroCells.getChildren().remove(flowSelect);

			selectTimeline.getKeyFrames().clear();




		      for (int i = 0; i < Game.heroes.size(); i++){
	        	Hero h = Game.heroes.get(i);
	        	//ArrayList<ImageView> heroIcons = new ArrayList<ImageView>();
	        	ImageView heroIcon = new ImageView(new Image("/joel.png", 90, 90, false, false));
	        	ImageView heroCell = new ImageView(new Image("/cell.png", 100, 100, false, false));



	        	int index = 0;
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
			        	controlHero = getHeroFromName("Henry Burell");
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

		        Collection<KeyFrame> frames = selectTimeline.getKeyFrames();
		        Duration frameGap = Duration.millis(100);
		        Duration frameTime = Duration.ZERO;

		        for (Image img : flowSelects) {
		            frameTime = frameTime.add(frameGap);
		            frames.add(new KeyFrame(frameTime, e -> flowSelect.setImage(img)));
		        }


		        selectTimeline.setCycleCount(Timeline.INDEFINITE);


		        if (controlHero.equals(Game.heroes.get(i))){
		        	flowSelect.setX(320 + 110 * i);
			    	flowSelect.setY(725);
		        }


		        selectTimeline.play();

		        heroCells.getChildren().remove(flowSelect);

		        heroCells.getChildren().addAll(heroCell,  heroIcon, flowSelect);

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

		        if (h.getName().equals("Bill")){
					h.getImage().setImage((new Image("billSwordLookDown.png")));
					h.getImage().setX(525 + 29 * h.getLocation().getY());   // 29 for x movement 31 y for y movement
			        h.getImage().setY(155 + 31 * (14 - h.getLocation().getX()));
			    	h.getImage().setScaleX(0.675);
					h.getImage().setScaleY(0.675);
					h.getImage().setX(h.getImage().getX() - 22);
					h.getImage().setY(h.getImage().getY() - 25);

				}

		        else if (h.getName().equals("David")){
		        	h.getImage().setImage((new Image("davidLookDown.png")));
					h.getImage().setX(545 + 29 * h.getLocation().getY());   // 29 for x movement 31 y for y movement
			        h.getImage().setY(175 + 31 * (14 - h.getLocation().getX()));
			    	h.getImage().setScaleX(0.675);
					h.getImage().setScaleY(0.675);
					h.getImage().setX(h.getImage().getX() - 12);
					h.getImage().setY(h.getImage().getY() - 15);

		        }

		        else if (h.getName().equals("Ellie Williams")){
		        	h.getImage().setImage((new Image("ellieLookDown.png")));
					h.getImage().setX(545 + 29 * h.getLocation().getY());   // 29 for x movement 31 y for y movement
			        h.getImage().setY(175 + 31 * (14 - h.getLocation().getX()));
			    	h.getImage().setScaleX(0.675);
					h.getImage().setScaleY(0.675);
					h.getImage().setX(h.getImage().getX() - 12);
					h.getImage().setY(h.getImage().getY() - 15);
					}

		        else if (h.getName().equals("Henry Burell")){
		        	h.getImage().setImage((new Image("henryLookDown.png")));
					h.getImage().setX(545 + 29 * h.getLocation().getY());   // 29 for x movement 31 y for y movement
			        h.getImage().setY(175 + 31 * (14 - h.getLocation().getX()));
			    	h.getImage().setScaleX(0.675);
					h.getImage().setScaleY(0.675);
					h.getImage().setX(h.getImage().getX() - 12);
					h.getImage().setY(h.getImage().getY() - 15);
					}

		        else if (h.getName().equals("Joel Miller")){
		        	h.getImage().setImage((new Image("joelLookDown.png")));
					h.getImage().setX(545 + 29 * h.getLocation().getY());   // 29 for x movement 31 y for y movement
			        h.getImage().setY(175 + 31 * (14 - h.getLocation().getX()));
			    	h.getImage().setScaleX(0.675);
					h.getImage().setScaleY(0.675);
					h.getImage().setX(h.getImage().getX() - 12);
					h.getImage().setY(h.getImage().getY() - 15);
					}

		        else if (h.getName().equals("Riley Abel")){
		        	h.getImage().setImage((new Image("rileyLookDown.png")));
					h.getImage().setX(545 + 29 * h.getLocation().getY());   // 29 for x movement 31 y for y movement
			        h.getImage().setY(175 + 31 * (14 - h.getLocation().getX()));
			    	h.getImage().setScaleX(0.675);
					h.getImage().setScaleY(0.675);
					h.getImage().setX(h.getImage().getX() - 12);
					h.getImage().setY(h.getImage().getY() - 15);
					}
		        else if (h.getName().equals("Tess")){
		        	h.getImage().setImage((new Image("tessLookDown.png")));
					h.getImage().setX(545 + 29 * h.getLocation().getY());   // 29 for x movement 31 y for y movement
			        h.getImage().setY(175 + 31 * (14 - h.getLocation().getX()));
			    	h.getImage().setScaleX(0.675);
					h.getImage().setScaleY(0.675);
					h.getImage().setX(h.getImage().getX() - 12);
					h.getImage().setY(h.getImage().getY() - 15);
					}
		        else if (h.getName().equals("Tommy Miller")){
		        	h.getImage().setImage((new Image("tommyLookDown.png")));
					h.getImage().setX(545 + 29 * h.getLocation().getY());   // 29 for x movement 31 y for y movement
			        h.getImage().setY(175 + 31 * (14 - h.getLocation().getX()));
			    	h.getImage().setScaleX(0.675);
					h.getImage().setScaleY(0.675);
					h.getImage().setX(h.getImage().getX() - 12);
					h.getImage().setY(h.getImage().getY() - 15);
					}


		        if (h.equals(controlHero)){
		        	ImageView flowSelect2 = new ImageView(flowSelects[0]);
		        	flowSelect2.setX(550 + 29 * h.getLocation().getY());
		        	flowSelect2.setY(180 + 31 * (14 - h.getLocation().getX()));
		        	flowSelect2.setFitWidth(32);
		        	flowSelect2.setFitHeight(35);
		        	 mapImages.getChildren().addAll(flowSelect2);
		        }

		        mapImages.getChildren().addAll(h.getImage());
		        h.makeAdjacentVisible();




	        }



	        for (int i = 0; i < Game.zombies.size(); i++){
	        	Zombie z = Game.zombies.get(i);

	        z.setImage(new ImageView(new Image("/tile026.png", 40, 40, false, false)));

        	mapImages.getChildren().add(z.getImage());
        	z.getImage().setX(545 + 29 * z.getLocation().getY());   // 29 for x movement 31 y for y movement
        	z.getImage().setY(175 + 31 * (14 - z.getLocation().getX()));






	        }

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




	        placeHeroCells();
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
			Text dmg = new Text("" + h.getAttackDmg());
			Text actions = new Text(h.getActionsAvailable() + "/" + h.getMaxActions());
			Text supplyCount = new Text(h.getSupplyInventory().size() + "");
			Text vaccineCount = new Text(h.getVaccineInventory().size() + "");


			ImageView hpIcon = new ImageView(new Image("/hpIcon.png", 40, 40, false, false));
			ImageView dmgIcon = new ImageView(new Image("/dmgIcon.png", 60, 40, false, false));
			ImageView actionsIcon = new ImageView(new Image("/actionsIcon.png", 60, 60, false, false));
			ImageView vaccineIcon = new ImageView(new Image("/vaccineIcon.png", 60, 60, false, false));
			ImageView supplyIcon = new ImageView(new Image("/Supplybox.png", 80, 80, false, false));



			Font infoFont = Font.loadFont(fontPath, 40);
//			Font infoFont = Font.loadFont("file:C:/Users/hussa/Desktop/Team105/GameAssets/gameFont.ttf", 40);

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
			hp.setX(1355);
			hp.setY(220);

			hpIcon.setX(1300);
			hpIcon.setY(185);



			dmg.setFont(infoFont);
			dmg.setFill(Color.WHITE);
			dmg.setX(1360);
			dmg.setY(280);

			dmgIcon.setX(1290);
			dmgIcon.setY(245);



			actions.setFont(infoFont);
			actions.setFill(Color.WHITE);
			actions.setX(1360);
			actions.setY(280 + 60);

			actionsIcon.setX(1290);
			actionsIcon.setY(235 + 60 );

			vaccineCount.setX(1360);
			vaccineCount.setY(350 + 60);
			vaccineCount.setFont(infoFont);
			vaccineCount.setFill(Color.WHITE);

			vaccineIcon.setX(1290);
			vaccineIcon.setY(300  + 60);

			supplyCount.setX(1360);
			supplyCount.setY(420  + 60);
			supplyCount.setFont(infoFont);
			supplyCount.setFill(Color.WHITE);

			supplyIcon.setX(1280);
			supplyIcon.setY(365  + 60 );



			info.getChildren().addAll(name, hp, type, hpIcon, dmg, dmgIcon, actionsIcon, actions, vaccineCount, vaccineIcon, supplyCount, supplyIcon);

			playRoot.getChildren().remove(info);
			playRoot.getChildren().addAll(info);


		     flowSelect.setX(320 + 110 * Game.heroes.indexOf(h));
			 flowSelect.setY(725);


		}

		static ArrayList<Text> alertStack = new ArrayList<Text>();

		public static Text throwAlert(String msg){

		   Font alertFont = Font.loadFont(fontPath, 45);
		   Text alert = new Text(msg);
		   alert.setFill(Color.WHITE);
		   alert.setFont(alertFont);

		   if (alertStack.size() == 3){
			   alertStack.remove(2);
			   alertStack.add(0, alert);
		   }
		   else{
			   alertStack.add(0, alert);}


		   FadeTransition ft = new FadeTransition(Duration.millis(3000), alert);
		   ft.setFromValue(1);
		   ft.setToValue(0);
		   ft.setCycleCount(1);
		   ft.play();

		   ft.setOnFinished(new EventHandler <ActionEvent>(){
		    	public void handle(ActionEvent e){
		    		if (alertStack.contains(ft.getNode())){
		    			alertStack.remove(ft.getNode());
		    			playRoot.getChildren().remove(ft.getNode());
		    		}
		    	}
		   });


		   displayAlertStack();

		   return alert;


		}

		public static void displayAlertStack(){
			for (int i = 0; i < alertStack.size(); i++){
				Text alert = alertStack.get(i);
				alert.setX(500);
				alert.setY(40 + 50 * (2 - i));

			}
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
				musicSoundPlayer.stop();
				StackPane endRoot = new StackPane();
				Scene endScene = new Scene(endRoot, 1536, 864);
				Pane pane = new Pane();


				File mainMenuFile = new File("src/YouWin.mp4");//nadeem path
				Media mainMenuVideo = new Media(mainMenuFile.toURI().toString());

				MediaPlayer mainMenuPlayer = new MediaPlayer(mainMenuVideo);
				MediaView mainMenuMV = new MediaView(mainMenuPlayer);

				mainMenuMV.setPreserveRatio(true);
				mainMenuPlayer.play();
				mainMenuPlayer.setCycleCount(Timeline.INDEFINITE);

				songPlayer.pause();

				Text winText = new Text("YOU WIN!");
				winText.setFont(titleFont);
				winText.setFill(Color.WHITE);
				winText.setX(595);
				winText.setY(270);

				pane.getChildren().add(winText);


			    endRoot.getChildren().addAll(mainMenuMV, pane);
			    stage.setScene(endScene);


			}
//
		else if (Game.checkGameOver()){
				musicSoundPlayer.stop();

			StackPane endRoot = new StackPane();
			Scene endScene = new Scene(endRoot, 1536, 864);
			Pane pane = new Pane();


			File gameOverFile = new File("src/GameOver.mp4");//nadeem path
			Media gameOverVideo = new Media(gameOverFile.toURI().toString());

			MediaPlayer gameOverPlayer = new MediaPlayer(gameOverVideo);
			MediaView gameOverMv = new MediaView(gameOverPlayer);

			gameOverMv.setPreserveRatio(true);

			gameOverPlayer.setCycleCount(Timeline.INDEFINITE);


			File gameOverSound = new File("src/GameOver.mp3");
			Media gameOverMedia = new Media(gameOverSound.toURI().toString());
			MediaPlayer gameOverSoundPlayer = new MediaPlayer(gameOverMedia);





			gameOverMv.setPreserveRatio(true);
//			gameOverSoundPlayer.play();





			songPlayer.pause();

			Text loseText = new Text("                        " + "GAME OVER! \n Zombies have overwhelmed you");
			loseText.setFont(titleFont);
			loseText.setFill(Color.RED);






			pane.getChildren().add(loseText);

//			endRoot.getChildren().addAll(gameOverMv, loseText);








		    gameOverPlayer.play();
    		gameOverSoundPlayer.play();

    		stage.setScene(endScene);
			endRoot.getChildren().addAll(gameOverMv, loseText, new MediaView(gameOverSoundPlayer));



			}
		}



}
