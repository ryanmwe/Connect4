package ui;

/**
 * text based ui for a Connect4 Game
 * 
 * 
 * Date: 9/3/2020
 * 
 * @author Ryan Wells
 * @version 1.1
 */
import java.util.Scanner;

public class Connect4TextConsole extends core.Connect4 {

	/**
	 * Generates a new blank gameboard
	 */
	public Connect4TextConsole(){
		for(int i=0; i < ROWS; i++) {
			for(int j=0; j < COLUMNS; j++) {
				gameBoard[i][j] ="|   |"; 
			}
		}
	}
	
	/**
	 * prints the current game board
	 */
	private void printBoard() {
		System.out.print("Current Board:\n");
		for(int i=0; i < ROWS; i++) {
			for(int j=0; j < COLUMNS; j++) {
				if (j == COLUMNS - 1){
					System.out.print(gameBoard[i][j] + "\n");
				} else {
					System.out.print(gameBoard[i][j]);
				}
			}
		}
	}
	
	/**
	 * intiates the game for a console output using logic from the core package.
	 */
	private void playGame(){
		if(activePlayer == 1) {
			System.out.print("PlayerX - your turn. Choose a column number from 1-7\n");
		} else {
			System.out.print("PlayerO - your turn. Choose a column number from 1-7\n");
		}
		Scanner in = new Scanner (System.in);
		int input = in.nextInt();
		while (input < 1 || input > COLUMNS) {
			System.out.print("Invalid Input\n");
			input = in.nextInt();
		}
		playerTurn(input);
		if(hasWon()) {
			/**playerTurn switched active player so the opposite of the active won */
			if(activePlayer == 2) { 
				printBoard();
				System.out.print("PlayerX Wins!\n");
			} else {
				printBoard();
				System.out.print("PlayerO Wins!\n");
			}
			
		} else if(checkDraw()) {
			printBoard();
			System.out.print("Game is a draw\n");
		} else {
			
			printBoard();
			playGame();
		}

		
		
	}
	
	public static void main(String[] args) {
		Connect4TextConsole game = new Connect4TextConsole();
		game.printBoard();
		System.out.print("Beggin Game\n");
		game.playGame();
		
	}
}