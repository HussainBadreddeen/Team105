package views;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MediaController {
	public File file;
	public Media media;
	public MediaPlayer player;
	public MediaView viewMedia;


	public MediaController(){

	}

	public MediaPlayer getPlayer(){
		return this.player;

	}

	public Media getMedia(){
		return this.media;
	}


	public MediaView playVideo(String filePath){
		this.file = new File(filePath);//nadeem path
		this.media = new Media(this.file.toURI().toString());
	    this.player = new MediaPlayer(this.media);
	    this.viewMedia = new MediaView(this.player);

	    DoubleProperty mvw = viewMedia.fitWidthProperty();
	    DoubleProperty mvh = viewMedia.fitHeightProperty();
	    mvw.bind(Bindings.selectDouble(viewMedia.sceneProperty(), "width"));
	    mvh.bind(Bindings.selectDouble(viewMedia.sceneProperty(), "height"));
	    viewMedia.setPreserveRatio(true);



	    this.player.play();

	    return this.viewMedia;

	}


	public MediaPlayer playAudio(String filePath){
		this.file = new File(filePath);
		this.media = new Media(this.file.toURI().toString());
	    this.player = new MediaPlayer(this.media);

	    return this.player;
	}
	
	
}
