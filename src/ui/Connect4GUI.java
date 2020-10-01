package ui;
import javafx.application.Application;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

/**
 * GUI ui for a connect 4 game.
 * 
 * 
 * Date: 9/19/2020
 * 
 * @author Ryan Wells
 * @version 1.0
 */
public class Connect4GUI extends core.Connect4 {
	GameSpace[][] GUIBoard = new GameSpace[ROWS][COLUMNS];
	Label status = new Label("Your turn");
	/**
	 * created a blank background gameboard for logic purposes
	 */
	public Connect4GUI(){
		for(int i=0; i < ROWS; i++) {
			for(int j=0; j < COLUMNS; j++) {
				gameBoard[i][j] ="|   |"; 
			}
		}
	}
	/**
	 * Launches and created the GUI of the application
	 */
	@Override
	public void start(Stage primaryStage) {
		GridPane pane = new GridPane();
		Rectangle square = new Rectangle(25,25,25,25);
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				pane.add(GUIBoard[i][j] = new GameSpace(), j, i);
			}
		}
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(pane);
		borderPane.setBottom(status);
		
		Scene scene = new Scene(borderPane, 450, 170);
		primaryStage.setTitle("Connect 4");
		primaryStage.setScene(scene);
		primaryStage.show();
	
	}
	private void createBackground() {
		Rectangle square = new Rectangle(80,80);
		
		for (int i = 0; i < ROWS; i++) {
			for (int k = 0; k < COLUMNS; k++) {
				
			}
		}
		
		
	}
	
	/**
	 * A seperate method currently used to test building the gameboard.
	 */
	public void buildBoard() {
		GridPane paneColumns = new GridPane();
		
		for (int i = 0; i < COLUMNS; i++) {
			paneColumns.add(new GameSpace(), 0, 1);
		}
	}
	//public void playGame();
	//public void checkBoard();
	/**
	 * Is ment to create a object that represents a section of the gameboard where a token can be placed
	 * @author Ryan
	 *
	 */
	class GameSpace extends Pane {
		
		/**
		 * sets a size and even handler for when an object is clicked
		 */
		public GameSpace(){
			this.setPrefSize(2000,2000);
			this.setOnMouseClicked(e -> onMouseClick(e));
		}
		
		/** 
		 * displays the inserted token. Currently just sets token to wherever it clicked.
		 * Needs logic for columns to work properly like connect 4.
		 */
		public void setToken() {
			if(activePlayer == 1) {
				Ellipse ellipse = new Ellipse(this.getWidth() / 2,
		 	    this.getHeight() / 2, this.getWidth() / 2 - 10,
		 	    this.getHeight() / 2 - 10);
		 	    ellipse.centerXProperty().bind(this.widthProperty().divide(2));
		 	    ellipse.centerYProperty().bind(this.heightProperty().divide(2));
			    ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
		 	    ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
			    ellipse.setStroke(Color.CRIMSON);
		 	    ellipse.setFill(Color.RED);
			    getChildren().add(ellipse); // Add the ellipse to the pane
				
			} else {
				Ellipse ellipse = new Ellipse(this.getWidth() / 2,
		 	    this.getHeight() / 2, this.getWidth() / 2 - 10,
		 	    this.getHeight() / 2 - 10);
		 	    ellipse.centerXProperty().bind(this.widthProperty().divide(2));
		 	    ellipse.centerYProperty().bind(this.heightProperty().divide(2));
			    ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
		 	    ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
			    ellipse.setStroke(Color.BLACK);
		 	    ellipse.setFill(Color.GREY);
			    getChildren().add(ellipse); // Add the ellipse to the pane
			}
		}
		/**
		 * event handler for a click event
		 * @param event
		 */
		private void onMouseClick(MouseEvent event) {
			setToken();
			playerTurn(GridPane.getColumnIndex((Node) event.getSource()) + 1);
			
		}
		
	}
	/**
	 * main program currently for testing since launching the game is currently broken.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}				
	
}
