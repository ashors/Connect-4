
public class Board {
	
	int[][] board;
	int x;
	int y;
	
	public Board(int x, int y) {
		this.x = x;
		this.y = y;
		board = new int[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				board[i][j] = 0;
			}
		}
	}
	
	// checks if column is full
	public boolean col_full(int col) {
		boolean full = true;
		for (int i = x-1; i >= 0; i--) {
			if (board[i][col] == 0) {
				full = false; 
				break;
			}
		}
		return full;
	}
	
	public void make_move(int col, int player) {
		if (col_full(col)) {
			System.out.println("invalid move! column full");
		}
		else {
			for (int i = x-1; i >= 0; i--) {
				if (board[i][col] == 0) {
					board[i][col] = player;
					break;
				}
			}
		}
	}
	
	public void printGame() {
		System.out.print("  ");
		for (int i = 0; i < y; i++) {
			System.out.print(" " + i + " ");
		}
		System.out.println();
		for (int i = 0; i < x; i++) {
			System.out.print(i);
			System.out.print(" ");
			for (int j = 0; j < y; j++) {
				if (board[i][j] == 0) {
					System.out.print("   ");
				}
				if (board[i][j] == 1) {
					System.out.print(" X ");
				}
				if (board[i][j] == 2) {
					System.out.print(" O ");
				}
				if (j == y-1) {
					System.out.println(" " + i);
				}
			}
		}
		System.out.print("  ");
		for (int i = 0; i < y; i++) {
			System.out.print(" " + i + " ");
		}
		System.out.println();
		System.out.println();
	}
	
	// checks if board is full
	public boolean boardFull() {
		boolean full = true;
		for (int i = 0; i < y; i++) {
			if (!col_full(i)) {
				full = false;
			}
		}
		return full;
	}
	
	// checks if specified player has specified number of tiles in a row along a diagonal
	public boolean diag(int player, int num_in_row) {
		for (int i = 0; i < x - (num_in_row - 1); i++) {
			for (int j = 0; j < y - (num_in_row - 1); j++) { // fixing starting position
				int num = 0;
				for (int k = 0; k < num_in_row; k++) {
					if (board[i+k][j+k] == player) {
						num +=1;
					}
					else {
						num = 0;
					}
				}
				if (num == num_in_row) {
					return true;
				}
			}
		}
		for (int i = x - 1; i >= num_in_row - 1; i--) {
			for (int j = 0; j < y - (num_in_row - 1); j++) { // fixing starting position
				int num = 0;
				for (int k = 0; k < num_in_row; k++) {
					if (board[i-k][j+k] == player) {
						num +=1;
					}
					else {
						num = 0;
					}
				}
				if (num == num_in_row) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean col(int player, int num_in_row) {
		for (int i = 0; i < y; i++) {
			int num = 0;
			for (int j = 0; j < x; j++) {
				if (board[j][i] == player) {
					num += 1;
					if (num == num_in_row) {
						return true;
					}
				}
				else {
					num = 0;
				}
			}
		}
		return false;
	}

	boolean row(int player, int num_in_row) {
		for (int i = 0; i < x; i++) {
			int num = 0;
			for (int j = 0; j < y; j++) {
				if (board[i][j] == player) {
					num += 1;
					if (num == num_in_row) {
						return true;
					}
				}
				else {
					num = 0;
				}
			}
		}
		return false;
	}

	public Board makeCopy() {
		Board board2 = new Board(x, y);
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				board2.board[i][j] = board[i][j];
			}
		}
		return board2;
	}
	
	// FOR HEURISTIC
	int rownum(int player, int num_in_row) { 
		// returns number of num_in_rows there are for that player
		int count = 0;
		for (int i = 0; i < x; i++) {
			int num = 0;
			for (int j = 0; j < y; j++) {
				if (board[i][j] == player) {
					num += 1;
					if (num == num_in_row) {
						count += 1;
						num = 0;
					}
				}
				else {
					num = 0;
				}
			}
		}
		return count;
	}
	
	// number of times player has num_in_row tiles in a column
	public int colnum(int player, int num_in_row) {
		int count = 0;
		for (int i = 0; i < y; i++) {
			int num = 0;
			for (int j = 0; j < x; j++) {
				if (board[j][i] == player) {
					num += 1;
					if (num == num_in_row) {
						count += 1;
						num = 0;
					}
				}
				else {
					num = 0;
				}
			}
		}
		return count;
	}
	
	public int diagnum(int player, int num_in_row) {
		int count = 0;
		for (int i = 0; i < x - (num_in_row - 1); i++) {
			for (int j = 0; j < y - (num_in_row - 1); j++) { // fixing starting position
				int num = 0;
				for (int k = 0; k < num_in_row; k++) {
					if (board[i+k][j+k] == player) {
						num +=1;
					}
					else {
						num = 0;
					}
				}
				if (num == num_in_row) {
					count +=1;
					num = 0;
				}
			}
		}
		for (int i = x - 1; i >= num_in_row - 1; i--) {
			for (int j = 0; j < y - (num_in_row - 1); j++) { // fixing starting position
				int num = 0;
				for (int k = 0; k < num_in_row; k++) {
					if (board[i-k][j+k] == player) {
						num +=1;
					}
					else {
						num = 0;
					}
				}
				if (num == num_in_row) {
					count += 1;
					num = 0;
				}
			}
		}
		return count;
	}
	
	// returns number of num_in_rows there are for player 1 - number of num_in_rows for player 2
	int rownumdiff(int num_in_row) { 
		int count1 = 0;
		int count2 = 0;
		for (int i = 0; i < x; i++) {
			int num1 = 0;
			int num2 = 0;
			for (int j = 0; j < y; j++) {
				if (board[i][j] == 1) {
					num1 += 1;
					num2 = 0;
					if (num1 == num_in_row) {
						count1 += 1;
						num1 = 0;
					}
				}
				else {
					num1 = 0;
					if (board[i][j] == 2) {
						num2 += 1;
						if (num2 == num_in_row) {
							count2 += 1;
							num2 = 0;
						}
					}
					else {
						num2 = 0;
					}
				}
			}
		}
		return count1 - 2*count2;
	}
	
	public int colnumdiff(int num_in_row) {
		int count1 = 0;
		int count2 = 0;
		for (int i = 0; i < y; i++) {
			int num1 = 0;
			int num2 = 0;
			for (int j = 0; j < x; j++) {
				if (board[j][i] == 1) {
					num2 = 0;
					num1 += 1;
					if (num1 == num_in_row) {
						count1 += 1;
						num1 = 0;
					}
				}
				else {
					num1 = 0;
					if (board[j][i] == 2) {
						num1 = 0;
						num2 += 1;
						if (num2 == num_in_row) {
							count2 += 1;
							num2 = 0;
						}
					}
					else {
						num2 = 0;
					}
				}
			}
		}
		return count1 - 2*count2;
	}

}
