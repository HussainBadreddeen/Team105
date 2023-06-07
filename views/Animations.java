package views;

import exceptions.InvalidTargetException;
import exceptions.NotEnoughActionsException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.characters.Hero;
import model.collectibles.Supply;

public class Animations extends Application {
	 static Timeline animation = new Timeline();
	 static Image actionImg = new Image("billAttackDown.gif");
	 static boolean moved = false;

	 public static void aura(Hero h){
			Timeline billMoveUp = new Timeline(
				    new KeyFrame(Duration.ZERO, e -> {effect.setImage(new Image("fightingAura.gif", 60, 60, false, false));}),
				    new KeyFrame(Duration.seconds(1), e -> {effect.setImage(null);})
				);

			billMoveUp.setOnFinished(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent e){
					SceneController.playRoot.getChildren().remove(effect);
				}
			});

			SceneController.playRoot.getChildren().add(effect);


			effect.setX(537 + 29 * h.getLocation().getY());
			effect.setY(157 + 31 * (14 - h.getLocation().getX()));
			billMoveUp.stop();
			billMoveUp.play();


		}

	 public static void heal(Hero h){
			Timeline billMoveUp = new Timeline(
				    new KeyFrame(Duration.ZERO, e -> {effect.setImage(new Image("medEffect.gif"));}),
				    new KeyFrame(Duration.seconds(1), e -> {effect.setImage(null);})
				);

			billMoveUp.setOnFinished(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent e){
					SceneController.playRoot.getChildren().remove(effect);
				}
			});
			effect.setScaleX(0.6);
			effect.setScaleY(0.6);
			SceneController.playRoot.getChildren().add(effect);

			effect.setX(467 + 29 * h.getTarget().getLocation().getY());
			effect.setY(95 + 31 * (14 - h.getTarget().getLocation().getX()));


			billMoveUp.stop();
			billMoveUp.play();


		}

	 public static void reveal(Hero h){
			Timeline billMoveUp = new Timeline(
				    new KeyFrame(Duration.ZERO, e -> {effect.setImage(new Image("expEffect.gif"));}),
				    new KeyFrame(Duration.seconds(1), e -> {effect.setImage(null);})
				);

			billMoveUp.setOnFinished(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent e){
					SceneController.playRoot.getChildren().remove(effect);
				}
			});
			effect.setScaleX(1);
			effect.setScaleY(1);
			SceneController.playRoot.getChildren().add(effect);

			effect.setX(505 + 29 * h.getLocation().getY());
			effect.setY(135 + 31 * (14 - h.getLocation().getX()));


			billMoveUp.stop();
			billMoveUp.play();


		}

	public static void BillMoveUp(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("billMoveSwordUp.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("billSwordLookUp.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();

	}

	public static void BillMoveRight(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("billMoveSwordR.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("billSwordLookR.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void BillMoveLeft(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("billMoveSwordL.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("billSwordLookL.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}



	public static void BillMoveDown(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("billMoveSwordDown.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("billSwordLookDown.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);

		animation.play();


	}

	static ImageView effect = new ImageView();




	public static void BillAttack(Hero h){

		int tX = (int)h.getTarget().getLocation().getX();
		int tY = (int)h.getTarget().getLocation().getY();

		int hX = (int)h.getLocation().getX();
		int hY = (int)h.getLocation().getY();

		if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX > 0){
			actionImg = new Image("billAttackUp.gif");
		}
		else if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX < 0){
			actionImg = new Image("billAttackDown.gif");
		}
		else if (tX == hX && tY - hY < 0){
			actionImg = new Image("billAttackL.gif");
		}

		else if (tX == hX && tY - hY > 0){
			actionImg = new Image("billAttackR.gif");
		}



		Timeline billMoveUp = new Timeline(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(actionImg);}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("billSwordLookDown.png"));})
			);

//		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent e) {
//				try {
//					SceneController.controlHero.attack();
//				} catch (NotEnoughActionsException | InvalidTargetException exp) {
//					billMoveUp
//					String msg = exp.getMessage();
//					SceneController.playRoot.getChildren().addAll(SceneController.throwAlert(msg));
//				}
//				SceneController.placeMapImages();
//				SceneController.displayHeroInfo(SceneController.controlHero);
//
//
//			}
//
//		});


		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				SceneController.placeMapImages();
			}

		});

//		animation.jumpTo(Duration.seconds(1));
		animation.stop();
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);


//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);

		billMoveUp.stop();
		billMoveUp.play();




//		billMoveUp.play();

	}



	/////////////////////////////////////////////////////////////////////////////////////////////

	public static void DavidMoveUp(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("davidMoveUp.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("davidLookUp.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void DavidMoveRight(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("davidMoveR.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("davidLookR.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void DavidMoveLeft(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("davidMoveL.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("davidLookL.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}



	public static void DavidMoveDown(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("davidMoveDown.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("davidLookDown.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);

		animation.play();


	}






	public static void DavidAttack(Hero h){

		int tX = (int)h.getTarget().getLocation().getX();
		int tY = (int)h.getTarget().getLocation().getY();

		int hX = (int)h.getLocation().getX();
		int hY = (int)h.getLocation().getY();

		if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX > 0){
			actionImg = new Image("davidAttackUp.gif");
		}
		else if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX < 0){
			actionImg = new Image("davidAttackDown.gif");
		}
		else if (tX == hX && tY - hY < 0){
			actionImg = new Image("davidAttackL.gif");
		}

		else if (tX == hX && tY - hY > 0){
			actionImg = new Image("davidAttackR.gif");
		}



		Timeline billMoveUp = new Timeline(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(actionImg);}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("davidLookDown.png"));})
			);

//		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent e) {
//				try {
//					SceneController.controlHero.attack();
//				} catch (NotEnoughActionsException | InvalidTargetException exp) {
//					billMoveUp
//					String msg = exp.getMessage();
//					SceneController.playRoot.getChildren().addAll(SceneController.throwAlert(msg));
//				}
//				SceneController.placeMapImages();
//				SceneController.displayHeroInfo(SceneController.controlHero);
//
//
//			}
//
//		});
		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				SceneController.placeMapImages();
			}

		});



//		animation.jumpTo(Duration.seconds(1));
		animation.stop();
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);


//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);

		billMoveUp.stop();
		billMoveUp.play();




//		billMoveUp.play();

	}


	//////////////////////////////////////////////////////////////////////////////
	public static void EllieMoveUp(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("ellieMoveUp.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("ellieLookUp.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void EllieMoveRight(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("ellieMoveR.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("ellieLookR.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void EllieMoveLeft(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("ellieMoveL.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("ellieLookL.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}



	public static void EllieMoveDown(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("ellieMoveDown.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("ellieLookDown.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);

		animation.play();


	}






	public static void EllieAttack(Hero h){

		int tX = (int)h.getTarget().getLocation().getX();
		int tY = (int)h.getTarget().getLocation().getY();

		int hX = (int)h.getLocation().getX();
		int hY = (int)h.getLocation().getY();

		if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX > 0){
			actionImg = new Image("ellieAttackUp.gif");
		}
		else if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX < 0){
			actionImg = new Image("ellieAttackDown.gif");
		}
		else if (tX == hX && tY - hY < 0){
			actionImg = new Image("ellieAttackL.gif");
		}

		else if (tX == hX && tY - hY > 0){
			actionImg = new Image("ellieAttackR.gif");
		}



		Timeline billMoveUp = new Timeline(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(actionImg);}),
			    new KeyFrame(Duration.seconds(1.75), e -> {h.getImage().setImage(new Image("ellieLookDown.png"));})
			);






		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				SceneController.placeMapImages();
			}

		});




//		animation.jumpTo(Duration.seconds(1));
		animation.stop();
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);


//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);

		billMoveUp.stop();
		billMoveUp.play();




//		billMoveUp.play();

	}

///////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void HenryMoveUp(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("henryMoveUp.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("henryLookUp.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void HenryMoveRight(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("henryMoveR.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("henryLookR.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void HenryMoveLeft(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("henryMoveL.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("henryLookL.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}



	public static void HenryMoveDown(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("henryMoveDown.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("henryLookDown.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);

		animation.play();


	}






	public static void HenryAttack(Hero h){

		int tX = (int)h.getTarget().getLocation().getX();
		int tY = (int)h.getTarget().getLocation().getY();

		int hX = (int)h.getLocation().getX();
		int hY = (int)h.getLocation().getY();

		if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX > 0){
			actionImg = new Image("henryAttackUp.gif");
		}
		else if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX < 0){
			actionImg = new Image("henryAttackDown.gif");
		}
		else if (tX == hX && tY - hY < 0){
			actionImg = new Image("henryAttackL.gif");
		}

		else if (tX == hX && tY - hY > 0){
			actionImg = new Image("henryAttackR.gif");
		}



		Timeline billMoveUp = new Timeline(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(actionImg);}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("henryLookDown.png"));})
			);



//		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent e) {
//				try {
//					SceneController.controlHero.attack();
//				} catch (NotEnoughActionsException | InvalidTargetException exp) {
//					billMoveUp
//					String msg = exp.getMessage();
//					SceneController.playRoot.getChildren().addAll(SceneController.throwAlert(msg));
//				}
//				SceneController.placeMapImages();
//				SceneController.displayHeroInfo(SceneController.controlHero);
//
//
//			}
//
//		});


		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				SceneController.placeMapImages();
			}

		});

//		animation.jumpTo(Duration.seconds(1));
		animation.stop();
		billMoveUp.stop();
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);


		h.getImage().setX(h.getImage().getX() - 64.8);
		h.getImage().setY(h.getImage().getY() - 64.8);
		billMoveUp.play();


		billMoveUp.play();

		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				h.getImage().setX(h.getImage().getX() + 64.8);
				h.getImage().setY(h.getImage().getY() + 64.8);


			}

		});




//		billMoveUp.play();

	}



	////////////////////////////////////////////////////////////////////////////

	public static void JoelMoveUp(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("joelMoveUp.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("joelLookUp.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void JoelMoveRight(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("joelMoveR.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("joelLookR.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void JoelMoveLeft(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("joelMoveL.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("joelLookL.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}



	public static void JoelMoveDown(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("joelMoveDown.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("joelLookDown.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);

		animation.play();


	}






	public static void JoelAttack(Hero h){

		int tX = (int)h.getTarget().getLocation().getX();
		int tY = (int)h.getTarget().getLocation().getY();

		int hX = (int)h.getLocation().getX();
		int hY = (int)h.getLocation().getY();

		if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX > 0){
			actionImg = new Image("joelAttackUp.gif");
		}
		else if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX < 0){
			actionImg = new Image("joelAttackDown.gif");
		}
		else if (tX == hX && tY - hY < 0){
			actionImg = new Image("joelAttackL.gif");
		}

		else if (tX == hX && tY - hY > 0){
			actionImg = new Image("joelAttackR.gif");
		}



		Timeline billMoveUp = new Timeline(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(actionImg);}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("joelLookDown.png"));})
			);



//		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent e) {
//				try {
//					SceneController.controlHero.attack();
//				} catch (NotEnoughActionsException | InvalidTargetException exp) {
//					billMoveUp
//					String msg = exp.getMessage();
//					SceneController.playRoot.getChildren().addAll(SceneController.throwAlert(msg));
//				}
//				SceneController.placeMapImages();
//				SceneController.displayHeroInfo(SceneController.controlHero);
//
//
//			}
//
//		});


		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				SceneController.placeMapImages();
			}

		});

//		animation.jumpTo(Duration.seconds(1));
		animation.stop();
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);


		h.getImage().setX(h.getImage().getX() - 30);
		h.getImage().setY(h.getImage().getY() - 30);

		billMoveUp.play();

		billMoveUp.stop();
		billMoveUp.play();

		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				h.getImage().setX(h.getImage().getX() + 30);
				h.getImage().setY(h.getImage().getY() + 30);


			}

		});




//		billMoveUp.play();

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void RileyMoveUp(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("rileyMoveUp.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("rileyLookUp.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void RileyMoveRight(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("rileyMoveR.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("rileyLookR.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void RileyMoveLeft(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("rileyMoveL.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("rileyLookL.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}



	public static void RileyMoveDown(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("rileyMoveDown.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("rileyLookDown.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);

		animation.play();


	}






	public static void RileyAttack(Hero h){

		int tX = (int)h.getTarget().getLocation().getX();
		int tY = (int)h.getTarget().getLocation().getY();

		int hX = (int)h.getLocation().getX();
		int hY = (int)h.getLocation().getY();

		if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX > 0){
			actionImg = new Image("rileyAttackUp.gif");
		}
		else if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX < 0){
			actionImg = new Image("rileyAttackDown.gif");
		}
		else if (tX == hX && tY - hY < 0){
			actionImg = new Image("rileyAttackL.gif");
		}

		else if (tX == hX && tY - hY > 0){
			actionImg = new Image("rileyAttackR.gif");
		}



		Timeline billMoveUp = new Timeline(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(actionImg);}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("rileyLookDown.png"));})
			);



//		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent e) {
//				try {
//					SceneController.controlHero.attack();
//				} catch (NotEnoughActionsException | InvalidTargetException exp) {
//					billMoveUp
//					String msg = exp.getMessage();
//					SceneController.playRoot.getChildren().addAll(SceneController.throwAlert(msg));
//				}
//				SceneController.placeMapImages();
//				SceneController.displayHeroInfo(SceneController.controlHero);
//
//
//			}
//
//		});

		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				SceneController.placeMapImages();
			}

		});


//		animation.jumpTo(Duration.seconds(1));
		animation.stop();
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);


		h.getImage().setX(h.getImage().getX());
		h.getImage().setY(h.getImage().getY());

		billMoveUp.play();

		billMoveUp.stop();
		billMoveUp.play();

//		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent e) {
//				h.getImage().setX(h.getImage().getX() + 30);
//				h.getImage().setY(h.getImage().getY() + 30);
//
//
//			}
//
//		});




//		billMoveUp.play();

	}

	//////////////////////////////////////////////////////////////////////
	public static void TessMoveUp(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("tessMoveUp.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("tessLookUp.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void TessMoveRight(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("tessMoveR.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("tessLookR.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void TessMoveLeft(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("tessMoveL.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("tessLookL.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}



	public static void TessMoveDown(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("tessMoveDown.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("tessLookDown.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);

		animation.play();

	}






	public static void TessAttack(Hero h){

		int tX = (int)h.getTarget().getLocation().getX();
		int tY = (int)h.getTarget().getLocation().getY();

		int hX = (int)h.getLocation().getX();
		int hY = (int)h.getLocation().getY();

		if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX > 0){
			actionImg = new Image("tessAttackUp.gif");
		}
		else if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX < 0){
			actionImg = new Image("tessAttackDown.gif");
		}
		else if (tX == hX && tY - hY < 0){
			actionImg = new Image("tessAttackL.gif");
		}

		else if (tX == hX && tY - hY > 0){
			actionImg = new Image("tessAttackR.gif");
		}



		Timeline billMoveUp = new Timeline(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(actionImg);}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("tessLookDown.png"));})
			);



//		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent e) {
//				try {
//					SceneController.controlHero.attack();
//				} catch (NotEnoughActionsException | InvalidTargetException exp) {
//					billMoveUp
//					String msg = exp.getMessage();
//					SceneController.playRoot.getChildren().addAll(SceneController.throwAlert(msg));
//				}
//				SceneController.placeMapImages();
//				SceneController.displayHeroInfo(SceneController.controlHero);
//
//
//			}
//
//		});

		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				SceneController.placeMapImages();
			}

		});


//		animation.jumpTo(Duration.seconds(1));
		animation.stop();
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);


		h.getImage().setX(h.getImage().getX());
		h.getImage().setY(h.getImage().getY());

		billMoveUp.play();

		billMoveUp.stop();
		billMoveUp.play();

//		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent e) {
//				h.getImage().setX(h.getImage().getX() + 30);
//				h.getImage().setY(h.getImage().getY() + 30);
//
//
//			}
//
//		});




//		billMoveUp.play();

	}



	public void start(Stage stage) throws Exception {
		ImageView ig = new ImageView(new Image("billAttackDown.gif"));

		Timeline billMoveUp = new Timeline(
			    new KeyFrame(Duration.ZERO, e -> {ig.setImage(new Image("billAttackDown.gif"));}),
			    new KeyFrame(Duration.seconds(2), e -> { ig.setImage(new Image("billSwordLookDown.png"));})
			);
		billMoveUp.play();
		Group root = new Group();

		ig.setRotate(15);
		root.getChildren().add(ig);
		Scene s = new Scene(root, 1536, 864, Color.BLACK);



		stage.setScene(s);
		stage.show();

	}
//////////////////////////////////////////////////////////////////
	public static void TommyMoveUp(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("tommyMoveUp.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("tommyLookUp.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void TommyMoveRight(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("tommyMoveR.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("tommyLookR.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}

	public static void TommyMoveLeft(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("tommyMoveL.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("tommyLookL.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);
		animation.stop();
		animation.play();



	}



	public static void TommyMoveDown(Hero h){
		animation.stop();
		animation.getKeyFrames().clear();
		animation.getKeyFrames().addAll(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(new Image("tommyMoveDown.gif"));}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("tommyLookDown.png"));})
			);
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);

//		h.getImage().setX(h.getImage().getX() - 22);
//		h.getImage().setY(h.getImage().getY() - 25);

		animation.play();


	}






	public static void TommyAttack(Hero h){

		int tX = (int)h.getTarget().getLocation().getX();
		int tY = (int)h.getTarget().getLocation().getY();

		int hX = (int)h.getLocation().getX();
		int hY = (int)h.getLocation().getY();

		if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX > 0){
			actionImg = new Image("tommyAttackUp.gif");
		}
		else if ((tY - hY == 0 || Math.abs(tY - hY) == 1) && tX - hX < 0){
			actionImg = new Image("tommyAttackDown.gif");
		}
		else if (tX == hX && tY - hY < 0){
			actionImg = new Image("tommyAttackL.gif");
		}

		else if (tX == hX && tY - hY > 0){
			actionImg = new Image("tommyAttackR.gif");
		}



		Timeline billMoveUp = new Timeline(
			    new KeyFrame(Duration.ZERO, e -> {h.getImage().setImage(actionImg);}),
			    new KeyFrame(Duration.seconds(1), e -> {h.getImage().setImage(new Image("tommyLookDown.png"));})
			);



//		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){
//
//			@Override
//			public void handle(ActionEvent e) {
//				try {
//					SceneController.controlHero.attack();
//				} catch (NotEnoughActionsException | InvalidTargetException exp) {
//					billMoveUp
//					String msg = exp.getMessage();
//					SceneController.playRoot.getChildren().addAll(SceneController.throwAlert(msg));
//				}
//				SceneController.placeMapImages();
//				SceneController.displayHeroInfo(SceneController.controlHero);
//
//
//			}
//
//		});


		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				SceneController.placeMapImages();
			}

		});

//		animation.jumpTo(Duration.seconds(1));
		animation.stop();
		h.getImage().setScaleX(0.675);
		h.getImage().setScaleY(0.675);


		h.getImage().setX(h.getImage().getX() - 64.8);
		h.getImage().setY(h.getImage().getY() - 64.8);
		billMoveUp.play();

		billMoveUp.stop();
		billMoveUp.play();

		billMoveUp.setOnFinished(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent e) {
				h.getImage().setX(h.getImage().getX() + 64.8);
				h.getImage().setY(h.getImage().getY() + 64.8);


			}

		});




//		billMoveUp.play();

	}




	public static void main(String[] args){
		launch(args);
	}

}
