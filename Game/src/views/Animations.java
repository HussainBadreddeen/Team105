package views;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animations {
	public Animations(){

	}

	public void attackDownFighter(Group group){
		ArrayList<Image> images = new ArrayList<>();



		images.add(new Image("/tile012.png"));
        images.add(new Image("/tile013.png"));
        images.add(new Image("/tile014.png"));
        images.add(new Image("/tile015.png"));
        images.add(new Image("/tile016.png"));
        images.add(new Image("/tile017.png"));
        images.add(new Image("/tile011.png"));

        ImageView f1 = new ImageView(images.get(0));
        ImageView f2 = new ImageView(images.get(1));
        ImageView f3 = new ImageView(images.get(2));
        ImageView f4 = new ImageView(images.get(3));
        ImageView f5 = new ImageView(images.get(4));
        ImageView f6 = new ImageView(images.get(5));
        ImageView f7 = new ImageView(images.get(6));




        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);

        timeline.getKeyFrames().add(new KeyFrame(
        		Duration.millis(100),
        		(ActionEvent event) -> {
        			group.setTranslateX(group.getTranslateX() - 64);
        			group.setTranslateY(group.getTranslateY() - 64);
        			group.getChildren().setAll(f1);
        		}
        		));


        timeline.getKeyFrames().add(new KeyFrame(
        		Duration.millis(200),
        		(ActionEvent event) -> {
        			group.getChildren().setAll(f2);
        			group.setTranslateY(group.getTranslateY() + 2);

        		}
        		));

        timeline.getKeyFrames().add(new KeyFrame(
        		Duration.millis(200),
        		(ActionEvent event) -> {
        			group.getChildren().setAll(f2);
        			group.setTranslateY(group.getTranslateY() + 2);
        		}
        		));

        timeline.getKeyFrames().add(new KeyFrame(
        		Duration.millis(300),
        		(ActionEvent event) -> {
        			group.getChildren().setAll(f3);
        			group.setTranslateY(group.getTranslateY() + 2);
        		}
        		));

        timeline.getKeyFrames().add(new KeyFrame(
        		Duration.millis(400),
        		(ActionEvent event) -> {
        			group.getChildren().setAll(f4);
        			group.setTranslateY(group.getTranslateY() + 2);
        		}
        		));

        timeline.getKeyFrames().add(new KeyFrame(
        		Duration.millis(500),
        		(ActionEvent event) -> {
        			group.getChildren().setAll(f5);
        			group.setTranslateY(group.getTranslateY() + 2);
        		}
        		));

        timeline.getKeyFrames().add(new KeyFrame(
        		Duration.millis(600),
        		(ActionEvent event) -> {
        			group.getChildren().setAll(f6);
        			group.setTranslateY(group.getTranslateY() + 2);
        		}
        		));

        timeline.getKeyFrames().add(new KeyFrame(
        		Duration.millis(700),
        		(ActionEvent event) -> {
        			group.getChildren().setAll(f7);
        			group.setTranslateY(group.getTranslateY() + 2);
        			group.setTranslateX(group.getTranslateX() + 64);
        			group.setTranslateY(group.getTranslateY() + 64);
        		}
        		));


        timeline.play();
	}
}
