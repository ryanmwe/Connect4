package ui;


/**
 * text based ui for a Connect4 Game
 * 
 * 
 * Date: 9/3/2020
 * 
 * @author Ryan Wells
 * @version 1.2
 */
import java.util.Scanner;
import core.Connect4ComputerPlayer;
import java.util.InputMismatchException;

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
	 * Checks the board status, to be used after playerMove();
	 */
	private void checkBoard() {
		if(hasWon()) {
			/**playerTurn switched active player so the opposite of the active won */
			if(activePlayer == 2) { 
				printBoard();
				if(!isCPU()) {
					System.out.print("PlayerX Wins!\n");
				} else {
					System.out.print("You Win!\n");
				}
				setGameEnded(true);
			} else {
				printBoard();
				if(!isCPU()) {
					System.out.print("PlayerO Wins!\n");
				} else {
					System.out.print("CPU Wins!\n");
				}
				
				setGameEnded(true);
			}
			
		} else if(checkDraw()) {
			printBoard();
			System.out.print("Game is a draw\n");
			setGameEnded(true);
		} else {
			
			setGameEnded(false);
		}
	}
	/**
	 * intiates the game for a console output using logic from the core package.
	 */
	private void playGame(Connect4ComputerPlayer cpu){
		if (!isCPU()) {
			if(activePlayer == 1) {
				System.out.print("PlayerX - your turn. Choose a column number from 1-7\n");
			} else {
				System.out.print("PlayerO - your turn. Choose a column number from 1-7\n");
			}
		}
		if (isCPU()) {
			if(activePlayer == 1) {
				System.out.print("It is your turn. Choose a column number from 1-7\n");
			} else {
				System.out.print("CPU is taking its turn. . .\n");
				cpu.setHasGoneFlag(false);
				cpu.checkForWin();
				cpu.checkForBlock();
				checkBoard();
				if(!hasGameEnded()) {
					printBoard();
					playGame(cpu);
				} else {
					System.exit(0);
				}
			}
		}
		Scanner in = new Scanner (System.in);
		int input = 0;
		try {
			input = in.nextInt();
		}
		catch (InputMismatchException ex) {
			in.nextLine();
		}
		while (input < 1 || input > COLUMNS) {
			System.out.print("Invalid Input: Not a number between 1-7\n");
			try {
				input = in.nextInt();
			}
			catch (InputMismatchException ex) {
				in.nextLine();
			}
		}
		playerTurn(input);
		checkBoard();
		if(!hasGameEnded()) {
			printBoard();
			playGame(cpu);
		} else {
			in.close();
			System.exit(0);
		}

	}

	/**
	 * Main method to run the game.
	 * @param args
	 */
	public static void start() {
		Connect4TextConsole game = new Connect4TextConsole();
		game.printBoard();
		System.out.print("Begin Game. Enter 'P' if you want to play against another player; \nenter 'C' to play against computer.\n");
		Scanner in = new Scanner (System.in);
		String input = in.nextLine();
		while (input.toUpperCase().compareTo("C") != 0 && input.toUpperCase().compareTo("P") != 0) {
			System.out.print("Invalid Input: Please select 'C' or 'P'\n");
			input = in.nextLine();
		}
		if(input.toUpperCase().compareTo("P") == 0){
			game.playGame(null);
		}
		if(input.toUpperCase().compareTo("C") == 0){
			System.out.print("\nStarting game against computer\n");
			Connect4ComputerPlayer cpu = new Connect4ComputerPlayer(game);
			game.setCPU(true);
			game.playGame(cpu);
		}
	}
}