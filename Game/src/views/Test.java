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

public class Test extends Application {



	public void start(Stage stage) throws Exception {
		stage.setTitle("The Last Of Us Legacy");
	

//		Button attack = new Button("Attack");

		SceneController.switchMenuScene(stage);

//		ImageView f7 = new ImageView(new Image("/tile011.png"));
//
//		Group attackDownFighter = new Group(f7);
//
//		attackDownFighter.setTranslateX(300);
//		attackDownFighter.setTranslateY(300);
//
//		Group root = new Group(attack);
//
//
//		attack.setOnMouseClicked(new EventHandler <Event>(){
//			public void handle(Event event){
//				Animations.attackDownFighter(attackDownFighter);
//				//root.getChildren().add(attackDownFighter);
//			}
//		});
//
//		root.getChildren().add(attackDownFighter);
//		;
//
//		stage.setScene(new Scene(root, 1000, 600));
//
		stage.show();
	}

	public static void main(String[] args){
		launch(args);

	}

}
