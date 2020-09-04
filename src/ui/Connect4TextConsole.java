package ui;
/**
 * ui for a Connect4 Game
 * 
 * 
 * Date: 9/3/2020
 * 
 * @author Ryan Wells
 * @version 1.0
 */
import java.util.Scanner;

public class Connect4TextConsole extends core.Connect4 {

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
	
	private void playGame() {
		if(activePlayer == 1) {
			System.out.print("PlayerX - your turn. Choose a column number from 1-7\n");
		} else {
			System.out.print("PlayerO - your turn. Choose a column number from 1-7\n");
		}
		Scanner in = new Scanner (System.in);
		int input = in.nextInt();
		while (input < 1 || input > 7) {
			System.out.print("Invalid Input\n");
			input = in.nextInt();
		}
		selectColumn(input);
		if(checkWinner()) {
			if(activePlayer == 2) { //selectColumn switched active player so the opposite of the active won
				printBoard();
				System.out.print("PlayerX Wins!\n");
			} else {
				printBoard();
				System.out.print("PlayerO Wins!\n");
			}
			
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