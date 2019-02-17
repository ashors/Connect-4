import java.util.ArrayList;

public class State {
	
	public Board board;
	public int player; // player whose turn it is
	public int num_in_row; // number of tiles in a row player is trying to achieve
	
	public State(Board board, int player, int num_in_row) {
		this.board = board;
		this.player = player;
		this.num_in_row = num_in_row;
	}
	
	public boolean checkTerminal() {
		Boolean terminal = false;
		if (board.boardFull()) {
			terminal = true;
		}
		for (int i = 1; i < 3; i++) {
			if (board.row(i, num_in_row) || board.col(i, num_in_row) || board.diag(i, num_in_row)) {
				terminal = true;
			}
		}
		return terminal;
	}
	
	public int calcUtility() {
		int utility = 0;
		if (board.row(1, num_in_row) || board.col(1, num_in_row) || board.diag(1, num_in_row)) {
			utility = 1;
		}
		else if (board.row(2, num_in_row) || board.col(2, num_in_row) || board.diag(2, num_in_row)) {
			utility = -1;
		}
		else {
			utility = 0;
		}
		return utility;
	}

	public ArrayList<Integer> getActions() {
		ArrayList<Integer> actions = new ArrayList<Integer>();
		for (int i = 0; i < board.y; i++) {
			if (!board.col_full(i)) {
				actions.add(i);
			}
		}
		return actions;
	}
	
	public State result(int i) {
		Board newBoard = board.makeCopy();
		newBoard.make_move(i, player);
		return new State(newBoard, 3-player, num_in_row);
	}
	
	public State makecopy() {
		Board new_board = board.makeCopy();
		int newplayer = player;
		int new_num_in_row = num_in_row;
		State newstate = new State(new_board, newplayer, new_num_in_row);
		return newstate;
	}
	
	public double heuristicUtility() {
		double utility = 3*(board.rownumdiff(num_in_row) + board.colnumdiff(num_in_row) 
		+ board.diagnum(1, num_in_row) - 3/2*board.diagnum(2, num_in_row))
		+ board.rownumdiff(num_in_row - 1) + board.colnumdiff(num_in_row - 1);
		return utility;
	}
}
