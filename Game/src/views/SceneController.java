package views;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaMarkerEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneController{
	StackPane root;
	Scene scene;
	Pane pane;

	public SceneController(){
		this.root = new StackPane();
		this.scene = new Scene(root, 1536, 864);
		this.pane = new Pane();



	}

	public void getMenuScene(Stage stage){

		Button start = new Button("START GAME");
	    Button settings = new Button("SETTINGS");
	    Button exit = new Button("EXIT");

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
	    		CharacterSelectionMenu(stage);
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




	    File mainMenuFile = new File("/C:/Users/nalaa/OneDrive/Desktop/GameAssets/LastOfUsMenu.mp4");//nadeem path
		Media mainMenuVideo = new Media(mainMenuFile.toURI().toString());
	    MediaPlayer mainMenuPlayer = new MediaPlayer(mainMenuVideo);
	    MediaView mainMenuMV = new MediaView(mainMenuPlayer);

	    DoubleProperty mvw = mainMenuMV.fitWidthProperty();
	    DoubleProperty mvh = mainMenuMV.fitHeightProperty();
	    mvw.bind(Bindings.selectDouble(mainMenuMV.sceneProperty(), "width"));
	    mvh.bind(Bindings.selectDouble(mainMenuMV.sceneProperty(), "height"));
	    mainMenuMV.setPreserveRatio(true);


	    Duration RestartVideoTime = Duration.millis(7800);

	    Timeline restartMenuVid = new Timeline(new KeyFrame(RestartVideoTime, event -> mainMenuPlayer.play()));
	    restartMenuVid.play();
	    //mainMenuPlayer.setOnEndOfMedia(() -> mainMenuPlayer.seek(RestartVideoTime));
	    mainMenuPlayer.setCycleCount(Timeline.INDEFINITE);







        String songPath = "/C:/Users/nalaa/OneDrive/Desktop/GameAssets/The_Last_Of_us_Theme_song.mp3";//relative path
	    Media mediaSong = new Media(new File(songPath).toURI().toString());
	    MediaPlayer songPlayer = new MediaPlayer(mediaSong);
	    songPlayer.play();

	    Duration StartSongTime = Duration.millis(7000);

	    Timeline restartMenuSong = new Timeline(new KeyFrame(StartSongTime, event -> songPlayer.play()));
	    restartMenuSong.play();//to play song after Team105 //song bt5lls then gap then restart
	    songPlayer.setOnEndOfMedia(() -> songPlayer.seek(Duration.ZERO));//restart song

	    mainMenuPlayer.play();

	    mainMenuVideo.getMarkers().put("Menu Display", Duration.millis(7400));


	    mainMenuPlayer.setOnMarker((MediaMarkerEvent event1) -> {
	    		pane.getChildren().addAll(title, start, settings, exit);
	      });

	    this.root.getChildren().addAll(mainMenuMV, pane);

	    stage.setScene(this.scene);


	}

	public void CharacterSelectionMenu(Stage stage){
	       //after choosing singlePlayer or multiPlayer Da el hyban

	        this.root  = new StackPane();
	        this.scene = new Scene(this.root,1536, 864);
	        this.pane  = new Pane();

	        File CharacterSelectVideo = new File("src/CharacterSelectionVideo.mp4");
	        Media CharacterSelectMedia = new Media(CharacterSelectVideo.toURI().toString());
	        MediaPlayer CharacterSelectPlayer = new MediaPlayer(CharacterSelectMedia);
	        MediaView CharacterSelectionView = new MediaView(CharacterSelectPlayer);
	        DoubleProperty mvw2 = CharacterSelectionView.fitWidthProperty();
	        DoubleProperty mvh2 = CharacterSelectionView.fitHeightProperty();
	        mvw2.bind(Bindings.selectDouble(CharacterSelectionView.sceneProperty(), "width"));
	        mvh2.bind(Bindings.selectDouble(CharacterSelectionView.sceneProperty(), "height"));
	        CharacterSelectionView.setPreserveRatio(true);

	        root.getChildren().addAll(CharacterSelectionView,pane);//add back button here

	        //for Character Selection video but we play it in the button
	        Timeline ResetCharacterSelection = new Timeline(new KeyFrame(Duration.ZERO, event -> CharacterSelectPlayer.play()));
	        CharacterSelectPlayer.setOnEndOfMedia(() -> CharacterSelectPlayer.seek(Duration.ZERO));//restart CharacterSelectionVideo


	    }


}
