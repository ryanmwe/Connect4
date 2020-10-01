package core;
import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
/**
 * Client interface to handle online multiplayer play
 * 
 * 
 * Date: 9/25/2020
 * 
 * @author Ryan Wells
 * @version 1.0
 */
public class Connect4Client extends Application{
	private DataInputStream servInput;
	private DataOutputStream servOutput;
	private String host = "localhost";
	private int port = 8000;
	private int ROWS = 6;
	private int COLUMNS = 7;
	private int columnChoice;
	private String color;
	private String opponent;
	private GameCell GUIBoard[][] = new GameCell[ROWS][COLUMNS];
	/**
	 * from Server:
	 * 1 = player 1 has won
	 * 2 = Player 2 has won
	 * 3 = draw
	 * 4 = continue playing
	 */
	private Label title = new Label();
	private Label status = new Label();
	private boolean continueToPlay = true;
	private boolean waiting = true;
	private boolean myTurn = false;
	

	public void start(Stage primaryStage) {
		GridPane pane = new GridPane();
		Rectangle square = new Rectangle(25,25,25,25);
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				pane.add(GUIBoard[i][j] = new GameCell(i,j), j, i);
			}
		}
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(title);
		borderPane.setCenter(pane);
		borderPane.setBottom(status);
		
		Scene scene = new Scene(borderPane, 450, 170);
		primaryStage.setTitle("Connect 4");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		connectToServer();
			
	}
	/**
	 * connect to the game server
	 */
	private void connectToServer() {
		try {
			//create socket to connect to the server.
			Socket socket = new Socket(host, port);
			
			servInput = new DataInputStream(socket.getInputStream());
			servOutput = new DataOutputStream(socket.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//Control game from a seperate thread
		new Thread( () -> {
			try {
				int player = servInput.readInt();
				if (player == 1) {
					color = "Red";
					opponent = "Black";
					Platform.runLater( () -> {
						title.setText("Player 1 - color Red");
						status.setText("Waiting for Player 2 to join");
					});
				servInput.readInt(); 
				
				//when play 2 has joined
				Platform.runLater( () -> {
					status.setText("Player 2 has joined! Make the first move!");
				});
				myTurn = true;
				} else if (player == 2) {
					color = "Black";
					opponent = "Red";
					Platform.runLater( () -> {
						title.setText("Player 2 - color Black");
						status.setText("Waiting for Player 1 to go.");
					});
				}
				
				while (continueToPlay) {
					if (player == 1) {
						waitForPlayerAction();
						sendMove();
						receiveInfoFromServer();
					}
					else if (player == 2) {
						receiveInfoFromServer();
						waitForPlayerAction();
						sendMove();
					}
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}).start();
	}
	
	/**
	 * waits for play to make a move
	 */
	private void waitForPlayerAction() throws InterruptedException {
		while (waiting) {
			Thread.sleep(100);
		}
		waiting = true; //reset the waiting before exiting
	}
	private void sendMove() throws IOException {
		servOutput.writeInt(columnChoice); //send the selected column to the server;
	}
	
	private void receiveInfoFromServer() throws IOException {
		int status = servInput.readInt();
		
		if (status == 1) {
			//Player 1 won;
			continueToPlay = false;
			if (color == "Red") {
				Platform.runLater(() -> this.status.setText("You won!"));
			} else if (color == "Black") {
				Platform.runLater( () -> this.status.setText("Player 1 (Red) has won!"));
				receiveMove();
			}
		} else if (status == 2) {
			continueToPlay = false;
			if (color == "Black") {
				Platform.runLater( () -> this.status.setText("You won!"));
			} else if (color == "Red") {
				Platform.runLater( () -> this.status.setText("Player 2 (Black) has won!"));
				receiveMove();
			}
		} else if (status == 3) {
			continueToPlay = false;
			Platform.runLater(() -> this.status.setText("Game is over, no winner"));
			
			if (color == "Red") {
				receiveMove();
			}
		} else {
			receiveMove();
			Platform.runLater(() -> this.status.setText("Your Turn"));
			myTurn = true;
		}
	}
	
	private void receiveMove() throws IOException {
		int column = servInput.readInt();
		Platform.runLater(() -> GUIBoard[0][column].setToken(opponent));
	}
	class GameCell extends Pane {
		private int row;
		private int column;
		
		/**
		 * sets a size and even handler for when an object is clicked
		 */
		public GameCell(int row, int column){
			this.row = row;
			this.column = column;
			this.setPrefSize(2000,2000);
			this.setOnMouseClicked(e -> onMouseClick(e));
		}
		
		/** 
		 * displays the inserted token. Currently just sets token to wherever it clicked.
		 * Needs logic for columns to work properly like connect 4.
		 */
		public void setToken(String token) {
			if(token == "Red") {
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
				
			} else if(token == "Black") {
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
			if(myTurn) {
				setToken(color);
				myTurn = false;
				columnChoice = column;
				status.setText("Waiting for other play to move");
				waiting = false;
			}			
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
