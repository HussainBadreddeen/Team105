package views;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Gui extends Application {



	public void start(Stage stage) throws Exception {
		stage.setTitle("The Last Of Us Legacy");

		SceneController.switchMenuScene(stage);

		stage.show();
	}

	public static void main(String[] args){
		launch(args);

	}

}
