package core;
/**
 * logic for a connect 4 game.
 * 
 * 
 * Date: 9/3/2020
 * 
 * @author Ryan Wells
 * @version 1.0
 */
public class Connect4 {
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
	 * selects column for player to place there token.
	 * @param column
	 */
	protected void selectColumn(int column){
		int row = ROWS - 1;
		column = column - 1;
		while(hasToken(row,column) && row > 0) {
			row--;
			}
		if (!hasToken(row,column)) {
			insertToken(row,column);
			switchPlayer();
		} else {
			System.out.print("Invalid input\n");
		}
	}
	
	protected boolean checkWinner() {
		return false;
	}
}
