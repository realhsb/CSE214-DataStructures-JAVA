package application;
	
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Main extends Application {
    private SnakeWorld world;
    private StageCanvas canvas;
    private Timeline timeline;
    private Button btnPlay;
    private Label lblScore;
    private enum GameState { Playing, Paused, GameOver }
    private GameState gameState;
    
	@Override
	public void start(Stage primaryStage) {
		try {
		    
		    world = new SnakeWorld();
		    canvas = new StageCanvas(world);
		    gameState = GameState.Playing;
            
            //timeline
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(250),
                    e -> { onTimeline(); }));
            timeline.setCycleCount(Animation.INDEFINITE);
            
			//Buttons
			btnPlay = new Button();
			btnPlay.setText("Pause");
			btnPlay.setOnAction(e->{ onPlayPause(); });
			
			lblScore = new Label("Score: " + 0);
			lblScore.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
			
            HBox hbButtons = new HBox();
            hbButtons.setAlignment(Pos.CENTER);
            hbButtons.setSpacing(20);
            hbButtons.getChildren().add(lblScore);
			hbButtons.getChildren().add(btnPlay);
			
			//Canvas
			StackPane centerPane = new StackPane();
			centerPane.getChildren().add(canvas);

			//root scene node
            BorderPane root = new BorderPane();
            root.setPadding(new Insets(5, 5, 5, 5));     
            root.setBottom(hbButtons);
            root.setCenter(centerPane);
            
            //scene
            Scene scene = new Scene(root,canvas.getWidth()+10,canvas.getHeight()+50);
            scene.setOnKeyReleased(ke->{ onKeyStroke(ke); });
            
            //stage
            primaryStage.setTitle("Snake Bite");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
			primaryStage.show();
			
			//start the game
			canvas.repaint();
			timeline.play();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void onTimeline() {
	    boolean gameOver = world.step();
	    if(gameOver) {
            timeline.stop();
	        gameState = GameState.GameOver;
            btnPlay.setText("New Game");
	    }
	    else {
	        lblScore.setText("Score: " + world.getScore());
	        canvas.repaint();
	        double rate = 1.0 + 3.0*world.getAppleCount()/SnakeWorld.MAX_APPLES;
	        timeline.setRate(rate);
	    }
	}
	private void onPlayPause() {
	    switch(gameState) {
        case GameOver:
            world.init();
	    case Paused:
            btnPlay.setText("Pause");
            timeline.play();
            gameState = GameState.Playing;
            break;
	    case Playing:
            btnPlay.setText("Play");
            timeline.stop();
            gameState = GameState.Paused;
            break;
	    }
	}
	private void onKeyStroke(KeyEvent ke) {
	    if(gameState != GameState.Playing)
	        return;
	    if(ke.getCode() == KeyCode.LEFT)   
	        world.turnRight(); //left -> right: reversed y axis
	    else if(ke.getCode() == KeyCode.RIGHT)
            world.turnLeft();  //right -> left: reversed y axis
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
