package core;
/**
 * logic for a connect 4 game.
 * 
 * 
 * Date: 9/3/2020
 * 
 * @author Ryan Wells
 * @version 1.3
 */
public class Connect4 {
	private boolean victory = false;
	private int rightDiagMatching = 0;
	private int leftDiagMatching = 0;
	private int horizMatching = 0;
	private int verticalMatching = 0;
	protected int activePlayer = 1;
	protected final int ROWS = 6;
	protected final int COLUMNS = 7;
	protected String[][] gameBoard = new String [ROWS][COLUMNS];	

	/**
	 * places a token
	 * @param row
	 * @param column
	 */
	private void insertToken(int row, int column) {
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
	private boolean hasToken(int row, int column) {
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
		//**We check 5 because the peace the player placed that turn is counted twice.*/
		if (leftDiagMatching == 5 || rightDiagMatching == 5 || verticalMatching == 5 || horizMatching == 5) {
			return true;
		}
		resetCounter();
		return false;
	}
	
	/**
	 * switches active player between player 1 and 2
	 */
	protected void switchPlayer() {
		if (activePlayer == 1) {
			activePlayer = 2;
		} else {
			activePlayer = 1;
		}
		
	}
	/**
	 * executes the players turn, determines if they have won or not and switches the active player if the other has won.
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
			System.out.print("Invalid input\n");
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
	
}
