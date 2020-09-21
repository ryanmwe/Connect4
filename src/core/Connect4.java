package core;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import ui.Connect4GUI;
import ui.Connect4TextConsole;
/**
 * logic for a connect 4 game.
 * 
 * 
 * Date: 9/3/2020
 * 
 * @author Ryan Wells
 * @version 1.5
 */
public class Connect4 extends Application {
	private Stage stage;
	private boolean victory = false;
	private int rightDiagMatching = 0;
	private int leftDiagMatching = 0;
	private int horizMatching = 0;
	private int verticalMatching = 0;
	protected int activePlayer = 1;
	protected final int ROWS = 6;
	protected final int COLUMNS = 7;
	protected String[][] gameBoard = new String [ROWS][COLUMNS];
	private boolean CPU = false;
	private boolean gameEnded = false;
	/**
	 * sets the CPU value
	 */
	protected void setCPU(boolean bool) {
		CPU = bool;
	}
	/**
	 * returns of CPU is false or true
	 */
	protected boolean isCPU() {
		return CPU;
	}
	/**
	 * set gameEnded flag
	 * @param bool
	 */
	protected void setGameEnded(boolean bool) {
		gameEnded = bool;
	}
	
	/**
	 * Returns if the games win condition has been met or not
	 * @return
	 */
	protected boolean hasGameEnded() {
		return gameEnded;
	}
	/**
	 * places a token
	 * @param row
	 * @param column
	 */
	protected void insertToken(int row, int column) {
		if(activePlayer == 1) {
			gameBoard[row][column] = "| X |";
		}
		if(activePlayer == 2) {
			gameBoard[row][column] = "| O |";
		}
	}
	
	/**
	 * checks if a token is already in a row
	 * @param row
	 * @param column
	 * @return
	 */
	protected boolean hasToken(int row, int column) {
		if (gameBoard[row][column] == "|   |") {
			return false;
		}
		return true;
	}
	
	/**
	 * resets the counters when checking for matches on a player turn 
	 */
	private void resetCounter() {
		rightDiagMatching = 0;
		leftDiagMatching = 0;
		horizMatching = 0;
		verticalMatching = 0;
		
	}
	
	/**
	 * returns if a player wont that turn
	 * @return
	 */
	protected boolean hasWon() {
		return victory;
	}
	
	/** 
	 * check upper left of current location for a Connect 4
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean upperLeftCheck(int row, int column) {
		int k = 0;
		for(int i=0; i < 4; i++) {
			if (row - i < 0 || column - k < 0) {
				return false;
			} else {
				if (gameBoard[row][column].compareTo(gameBoard[row-i][column-k]) != 0)  {
					return false;
				} else {
					leftDiagMatching++;
				}
			}
		k++;	
		}
		return true;
	}
	
	/**
	 * check upper middle of current location for a Connect 4
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean upperMiddleCheck(int row, int column) {
		for(int i=0; i < 4; i++) {
			if (row - i < 0) {
				return false;
			} else {
				if (gameBoard[row][column].compareTo(gameBoard[row-i][column]) != 0) {
					return false;
				} else {
					verticalMatching++;
				}
			}
			
		}
		return true;
	}
			
	/**
	 * check upper right of current location for a Connect 4
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean upperRightCheck(int row, int column) {
		int k = 0;
		for(int i=0; i < 4; i++) {
			if (row - i < 0 || column + k > COLUMNS - 1) {
				return false;
			} else {
				if (gameBoard[row][column].compareTo(gameBoard[row-i][column+k]) != 0)  {
					return false;
				} else {
					rightDiagMatching++;
				}
			}
			k++;
			
		}
		return true;
	}		
	
	
	/**
	 * check mid right of current location for a Connect 4
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean midRightCheck(int row, int column) {
		for (int k=0; k < 4; k++) {
			if (column + k > COLUMNS - 1 ) {
				return false;
			} else {
				if (gameBoard[row][column].compareTo(gameBoard[row][column+k]) != 0)  {
					return false;
				} else {
					horizMatching++;
				}
			}
			
		}
		return true;	
	}
	
	/**
	 * check mid left of current location for a Connect 4
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean midLeftCheck(int row, int column) {
		for (int k=0; k < 4; k++) {
			if (column - k < 0) {
				return false;
			} else {
				if (gameBoard[row][column].compareTo(gameBoard[row][column-k]) != 0)  {
					return false;
				} else {
					horizMatching++;
				}
			}
			
		}
		return true;	
	}		
	
	/**
	 * check lower left from current loaction for a Connect 4
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean lowerLeftCheck(int row, int column) {
		int k = 0;
		for(int i=0; i < 4; i++) {
			if (row + i > ROWS - 1 || column - k < 0) {
				return false;
			} else {
				if (gameBoard[row][column].compareTo(gameBoard[row+i][column-k]) != 0)  {
					return false;
				} else {
					rightDiagMatching++;
				}
			}
			k++;
		}
		return true;
	}		
	
	/**
	 * check lower middle from current location for a Connect 4
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean lowerMiddleCheck(int row, int column) {
		for(int i=0; i < 4; i++) {
			if (row + i > ROWS - 1) {
				return false;
			} else {
				if (gameBoard[row][column].compareTo(gameBoard[row+i][column]) != 0) {
					return false;
				} else {
					verticalMatching++;
				}
			}
			
		}
		return true;
	}
	
	/**
	 * check the lower right for connect4
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean lowerRightCheck(int row, int column) {
		int k = 0;
		for(int i=0; i < 4; i++) {

			if (row + i > ROWS -1 || column + k > COLUMNS - 1) {
				return false;
			} else {
				if (gameBoard[row][column].compareTo(gameBoard[row+i][column+k]) != 0)  {
					return false;
				} else {
					leftDiagMatching++;
				}
			}
			k++;
			
		}
		return true;
	}
	
	/**
	 * check all win conditions with a single call
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean winCheck(int row, int column) {
		if (upperLeftCheck(row, column) || upperMiddleCheck(row, column) || upperRightCheck(row, column) || midRightCheck(row, column) || midLeftCheck(row, column) || lowerLeftCheck(row, column) || lowerMiddleCheck(row, column) || lowerRightCheck(row, column)) {
		return true;
		}
		//**We check 5 because the piece the player placed that turn is counted twice.*/
		if (leftDiagMatching >= 5 || rightDiagMatching >= 5 || verticalMatching >= 5 || horizMatching >= 5) {
			return true;
		}
		resetCounter();
		return false;
	}
	
	/**
	 * switches active player between player 1 and 2
	 */
	private void switchPlayer() {
		if (activePlayer == 1) {
			activePlayer = 2;
		} else {
			activePlayer = 1;
		}
		
	}
	/**
	 * executes the players turn, determines if they have won or not and switches the active player.
	 * @param column
	 */
	protected void playerTurn(int column){
		int row = ROWS - 1;
		column = column - 1;
		while(hasToken(row,column) && row > 0) {
			row--;
			}
		if (!hasToken(row,column)) {
			insertToken(row,column);
			victory = winCheck(row,column);
			switchPlayer();
		} else {
			System.out.print("Invalid input: The column is full\n");
		}
	}
	
	/**
	 * Will check to if the entire board has been filled and no more moves can be made.
	 * @return
	 */
	protected boolean checkDraw() {
		for(int i=0; i < ROWS; i++) {
			for(int k=0; k < COLUMNS; k++) {

				if(!hasToken(i, k)){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Replaces the main method, 
	 * displays to use if they want to play with GUI or console ui
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		GridPane buttonPane = new GridPane();
		Button btConsole = new Button("Console");
		Button btGUI = new Button("GUI");
		ClientHandler clientHandle = new ClientHandler();
		btConsole.setOnAction(clientHandle);
		buttonPane.add(btConsole,1, 1);
		buttonPane.add(btGUI,2, 1);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		buttonPane.setHgap(5.5);
		buttonPane.setVgap(10);
		
		BorderPane borderPane = new BorderPane();
		Label label = new Label("Select your UI");
		label.setFont(new Font("arial",24));
		borderPane.setCenter(buttonPane);
		borderPane.setTop(label);
		BorderPane.setAlignment(label, Pos.BOTTOM_CENTER);
		
		
		Scene scene = new Scene(borderPane, 240, 120);
		primaryStage.setTitle("Connect 4");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
		
	/**
	 * Class to handle button event from the client button
	 * @author Ryan
	 *
	 */
	class ClientHandler implements EventHandler<ActionEvent>{
		/**
		 * Starts the console ui when the "console" button is clicked
		 */
		@Override
		public void handle(ActionEvent e) {
			Connect4TextConsole.start();
		}
	}
	/**
	 * Class to handle button event from the GUI button
	 * @author Ryan
	 *
	 */
	class GUIHandler implements EventHandler<ActionEvent>{
		/**
		 * Starts the GUI ui when the "GUI" button is clicked
		 * Currently needs to updated 
		 */
		@Override
		public void handle(ActionEvent e) {
			String[] args = new String[0];
			Connect4GUI.launch(args);
		}
	}
	/**
	 * Main method to start to the program, launches the application.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
