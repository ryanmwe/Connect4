package core;
import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
/**
 * Server interface to handle online multiplayer play
 * 
 * 
 * Date: 9/25/2020
 * 
 * @author Ryan Wells
 * @version 1.0
 */
public class Connect4Server extends Application {
	private int session = 1;
	
	public void start(Stage primaryStage) {
		TextArea log = new TextArea();
		
		Scene scene = new Scene(new ScrollPane(log), 450, 200);
		primaryStage.setTitle("Connect4 Server Log");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		new Thread( () -> {
			try {
				//create the socket
				ServerSocket socket = new ServerSocket(8000);
				Platform.runLater(() -> log.appendText(
						new Date() + ": Server started at scoket 8000\n"));
				
				//Create Sessions
				while(true) {
					Platform.runLater( () -> log.appendText(
							new Date() + ": Waiting for players to join session"
							  + session++ + "\n"));
					
					//ack player 1's connection
					Socket player1 = socket.accept();
					
					Platform.runLater(() -> {
						log.appendText(
								new Date() + ": Player 1 has joined session from IP - " 
								  + player1.getInetAddress().getHostAddress() + "\n" );
					});
					
					//send to client they are player 1
					new DataOutputStream(player1.getOutputStream()).writeInt(1);
					
					//ack player 2's connection
					Socket player2 = socket.accept();
					
					Platform.runLater(() -> {
						log.appendText(
								new Date() + ": Player 2 has joined session from IP - " 
								  + player1.getInetAddress().getHostAddress() + "\n" );
					});
					
					//send to client they are play 2
					new DataOutputStream(player2.getOutputStream()).writeInt(2);	
					
					// Launch a new thread to initialize game
					new Thread(new Handler(player1, player2)).start();
					
				}
				
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}).start();
		
	}
	/**
	 * Handles the new game thread between two players
	 * @author Ryan
	 *
	 */
	class Handler implements Runnable{
		private Socket player1;
		private Socket player2;
		private Connect4 game;
		private DataInputStream p1Input;
		private DataOutputStream p1Output;
		private DataInputStream p2Input;
		private DataOutputStream p2Output;
		
		/**
		 * Handler class constructor method
		 * @param p1 -player 1 socket connection
		 * @param p2 -player 2 socket connection
		 */
		public Handler(Socket p1, Socket p2) {
			this.player1 = p1;
			this.player2 = p2;
			
			//create game logic
			game = new Connect4();
			
		}
		/**
		 * runs the game on the server and uses core game logic
		 */
		public void run() {
			try {
				//Initialize data input and output stream
				p1Input = new DataInputStream(player1.getInputStream());
				p1Output = new DataOutputStream(player1.getOutputStream());
				p2Input = new DataInputStream(player2.getInputStream());
				p2Output = new DataOutputStream(player2.getOutputStream());
				
				//start game for player 1;
				p1Output.writeInt(1);
				//handle game logic
				while (true) {
					if (game.activePlayer == 1) {
						int column = p1Input.readInt();
						column += 1;
						game.playerTurn(column);
						if (game.hasWon()) {
							game.setGameEnded(true);
							p1Output.writeInt(1); /** send player 1 has won */
							p2Output.writeInt(1);
							sendMove(p2Output, column);
							break;
						}
						else if (game.checkDraw()) {
							game.setGameEnded(true);
							p1Output.writeInt(3); /** Send game was draw */
							p2Output.writeInt(3);
							sendMove(p2Output, column);
							break;
						}
						else {
							p2Output.writeInt(4); /** send to continue the game */
							sendMove(p2Output, column);
						}
					}
					if (game.activePlayer == 2) {
						int column = p2Input.readInt();
						game.playerTurn(column);
						if (game.hasWon()) {
							game.setGameEnded(true);
							p1Output.writeInt(2); /** send player 2 has won */
							p2Output.writeInt(2);
							sendMove(p1Output, column);
							break;
						}
						else if (game.checkDraw()) {
							game.setGameEnded(true);
							p1Output.writeInt(3);  /** Send game was draw */
							p2Output.writeInt(3);
							sendMove(p1Output, column);
							break;
						}
						else {
							p2Output.writeInt(4); /** send to continue the game */
							sendMove(p1Output, column);
						}
					}
				} 
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		/** 
		 * sends active players play to network opponent
		 * @param out
		 * @param column
		 * @throws IOException
		 */
		private void sendMove(DataOutputStream out, int column) throws IOException{
			out.writeInt(column);
		}
		
	}
	public static void main(String[] args) {
		    launch(args);
	}
		
}
