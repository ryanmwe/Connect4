package core;
/**
 * CPU AI for a connect 4 game.
 * 
 * 
 * Date: 9/9/2020
 * 
 * @author Ryan Wells
 * @version 1.0
 */

import java.util.Random;

public class Connect4ComputerPlayer {
	private Connect4 game;
	private int leftDiagMatching=0;
	private int rightDiagMatching=0;
	private int verticalMatching=0;
	private int horizMatching=0;
	private boolean hasGoneFlag = false;
	/**
	 * set difficulty of the bot with this variable
	 * 0 being the easiest, the CPU will never block your connect 4 on purpose
	 * 10 being the hardest, the CPU will almost always block your connect 4 on purpose
	 */
	private final int difficulty = 8;
	
	public Connect4ComputerPlayer(Connect4 match){
		game = match;
	}

	/**
	 * Resets the CPU's counters
	 */
	private void resetCounter() {
		rightDiagMatching = 0;
		leftDiagMatching = 0;
		horizMatching = 0;
		verticalMatching = 0;
		
	}
	public void setHasGoneFlag(boolean bool) {
		hasGoneFlag = bool;
	}
	/**
	 * Uses modified checks specific to computer.
	 */
	private void scan(int i, int k, String token) {
		upperLeftCheck(i, k, token);
		upperMiddleCheck(i, k, token);
		upperRightCheck(i, k, token);
		midRightCheck(i, k, token);
		midLeftCheck(i, k, token);
		lowerLeftCheck(i, k, token);
		lowerMiddleCheck(i, k, token);
		lowerRightCheck(i, k, token);
	}
	/**
	 * CPU checks to see if it can win
	 */
	public void checkForWin() {
		if(!hasGoneFlag) {
			for(int k=0; k < game.COLUMNS; k++) {
				int row = game.ROWS - 1;
				while(game.hasToken(row,k) && row > 0) {
					row--;
					}
				scan(row, k, "| O |");
				if (leftDiagMatching >= 3 || rightDiagMatching >= 3 || verticalMatching >= 3 || horizMatching >= 3) {
					if(!game.hasToken(row, k) && hasGoneFlag == false){
						setHasGoneFlag(true);
						int choice = k + 1;
						System.out.print("CPU chose " + choice +" \n");
						game.playerTurn(choice);
					}
				}
				resetCounter();	
			}
		}
	}
	
	/**
	 * CPU checks if it needs to block a player chance, RNG is used to make the CPU miss a block. Change the if (random > int) range to change difficulty.
	 */
	public void checkForBlock() {
		if(!hasGoneFlag) {
			for(int k=0; k < game.COLUMNS; k++) {
				int row = game.ROWS - 1;
				while(game.hasToken(row,k) && row > 0) {
					row--;
					}
				scan(row, k, "| X |");
				if (leftDiagMatching >= 3 || rightDiagMatching >= 3 || verticalMatching >= 3 || horizMatching >= 3) {
					Random randotron = new Random();
					int rando = randotron.nextInt(10);
					if(!game.hasToken(row, k) && hasGoneFlag == false && rando < difficulty ){
						setHasGoneFlag(true);
						int choice = k + 1;
						System.out.print("CPU chose " + choice +" \n");
						game.playerTurn(choice);
					}
				}
				resetCounter();	
			}		
		}
		/**
		 * If no block is found or RNG stops CPU from blocking
		 */
		if(!hasGoneFlag) {
		setHasGoneFlag(true);
		chooseRandom();
		}
	}
	
	/**
	 * CPU Chooses a random column to place a token
	 */
	private void chooseRandom() {
		Random randotron = new Random();
		int choice = randotron.nextInt(6);
		choice++;
		
		while(game.hasToken(0,choice - 1)) {
			choice = randotron.nextInt(6);
			choice++;
		}
		System.out.print("CPU chose " +choice +" \n");
		game.playerTurn(choice);
	
	}
	
	/** 
	 * check upper left for possible win chances
	 * @param row
	 * @param column
	 * @return
	 * @Override
	 */
	private boolean upperLeftCheck(int row, int column, String token) {
		int k = 0;
		for(int i=0; i < 4; i++) {
			if (row - i < 0 || column - k < 0) {
				return false;
			} else {
				if (token.compareTo(game.gameBoard[row-i][column-k]) != 0)  {
					//Do NOthing;
				} else {
					leftDiagMatching++;
				}
			}
		k++;	
		}
		return true;
	}
	
	/**
	 * check upper middle for possible win chances
	 * @param row
	 * @param column
	 * @return
	 * @Override
	 */
	private boolean upperMiddleCheck(int row, int column,String token) {
		for(int i=0; i < 4; i++) {
			if (row - i < 0) {
				return false;
			} else {
				if (token.compareTo(game.gameBoard[row-i][column]) != 0) {
					//Do Nothing;
				} else {
					verticalMatching++;
				}
			}
			
		}
		return true;
	}
			
	/**
	 * check upper right for possible win chances
	 * @param row
	 * @param column
	 * @return
	 * @Override
	 */
	private boolean upperRightCheck(int row, int column, String token) {
		int k = 0;
		for(int i=0; i < 4; i++) {
			if (row - i < 0 || column + k > game.COLUMNS - 1) {
				return false;
			} else {
				if (token.compareTo(game.gameBoard[row-i][column+k]) != 0)  {
					//Do Nothing;
				} else {
					rightDiagMatching++;
				}
			}
			k++;
			
		}
		return true;
	}		
	
	
	/**
	 * check mid right for possible win chances
	 * @param row
	 * @param column
	 * @return
	 * @Override
	 */
	private boolean midRightCheck(int row, int column, String token) {
		for (int k=0; k < 4; k++) {
			if (column + k > game.COLUMNS - 1 ) {
				return false;
			} else {
				if (token.compareTo(game.gameBoard[row][column+k]) != 0)  {
					//Do Nothing;
				} else {
					horizMatching++;
				}
			}
			
		}
		return true;	
	}
	
	/**
	 * check mid left for possible win chances
	 * @param row
	 * @param column
	 * @return
	 * @Override
	 */
	private boolean midLeftCheck(int row, int column, String token) {
		for (int k=0; k < 4; k++) {
			if (column - k < 0) {
				return false;
			} else {
				if (token.compareTo(game.gameBoard[row][column-k]) != 0)  {
					//Do Nothing;
				} else {
					horizMatching++;
				}
			}
			
		}
		return true;	
	}		
	
	/**
	 * check lower left for possible win chances
	 * @param row
	 * @param column
	 * @return
	 * @Override
	 */
	private boolean lowerLeftCheck(int row, int column, String token) {
		int k = 0;
		for(int i=0; i < 4; i++) {
			if (row + i > game.ROWS - 1 || column - k < 0) {
				return false;
			} else {
				if (token.compareTo(game.gameBoard[row+i][column-k]) != 0)  {
					//Do Nothing;
				} else {
					rightDiagMatching++;
				}
			}
			k++;
		}
		return true;
	}		
	
	/**
	 * check lower middle for possible win chances
	 * @param row
	 * @param column
	 * @return
	 * @Override
	 */
	private boolean lowerMiddleCheck(int row, int column, String token) {
		for(int i=0; i < 4; i++) {
			if (row + i > game.ROWS - 1) {
				return false;
			} else {
				if (token.compareTo(game.gameBoard[row+i][column]) != 0) {
					//Do Nothing;
				} else {
					verticalMatching++;
				}
			}
			
		}
		return true;
	}
	
	/**
	 * check the lower for possible win chances
	 * @param row
	 * @param column
	 * @return
	 * @Override
	 */
	private boolean lowerRightCheck(int row, int column, String token) {
		int k = 0;
		for(int i=0; i < 4; i++) {

			if (row + i > game.ROWS -1 || column + k > game.COLUMNS - 1) {
				return false;
			} else {
				if (token.compareTo(game.gameBoard[row+i][column+k]) != 0)  {
					//Do Nothing;
				} else {
					leftDiagMatching++;
				}
			}
			k++;
			
		}
		return true;
	}
	
}