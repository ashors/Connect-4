import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class Play {
	
	public static void main(String[] args) {
		System.out.println("Connect 4 by Anna Shors and Adeel Qayum" + "\n");
		System.out.println("Choose your game:" + "\n" + 
		"1. Tiny 3x3x3 Connect-Three" + "\n" + 
		"2. Wider 3x5x3 Connect-Three" + "\n" + 
		"3. Standard 6x7x4 Connect-Four" + "\n");
		System.out.print("Your choice: ");
		Scanner sc1 = new Scanner(System.in);
		int gamechoice = sc1.nextInt();
		while(gamechoice != 1 && gamechoice != 2 && gamechoice != 3) {
			System.out.print("Please enter a valid option: ");
			sc1 = new Scanner(System.in);
			gamechoice = sc1.nextInt();
		}
		State state = new State(new Board(6,7), 1, 4);
		if(gamechoice == 1) {
			state = new State(new Board(3,3), 1, 3);
		}
		else if(gamechoice == 2) {
			state = new State(new Board(3,5), 1, 3);
		}
		
		int columnNumber = state.board.y;
		
		Scanner sc = new Scanner(System.in);
		System.out.println();
		
		System.out.println("Choose your opponent:" + "\n" + 
		"1. An agent that plays randomly" + "\n" + 
		"2. An agent that uses Minimax " + "\n" + 
		"3. An agent that uses Minimax with Alpha-Beta Pruning" + "\n" +
		"4. An agent that uses H-Minimax with a fixed depth cutoff" + "\n");
		System.out.print("Your choice: ");
		Scanner sc2 = new Scanner(System.in);
		int opponentchoice = sc2.nextInt();
		
		int depthlimit = 0;
		
		if(opponentchoice == 4) {
			System.out.print("Depth Limit: ");
			Scanner sc3 = new Scanner(System.in);
			depthlimit = sc3.nextInt();
		}
		
		System.out.println();
		
		int turnchoice = 0;
		
		if(opponentchoice != 4) {
			System.out.println("If you want to play first (X), type 1." + "\n" + "If you would like to play second (O), type 2" + 
				"\n");
			System.out.print("Your choice: ");
			Scanner sc4 = new Scanner(System.in);
			turnchoice = sc4.nextInt();
			while(turnchoice != 1 && turnchoice != 2) {
				System.out.print("Please enter a valid option: ");
				sc4 = new Scanner(System.in);
				gamechoice = sc4.nextInt();
			}
		}

		
		
		state.board.printGame();
		
		//RANDOM
		if(opponentchoice == 1) {
			int move = 0;
			if(turnchoice == 1) {
			while (!state.checkTerminal()) {
				System.out.println("enter a column");
				move = sc.nextInt();					
				while(move < 0 || move >= columnNumber) {
					System.out.print("Please enter a valid option: ");
					move = sc.nextInt();
				}
				state = state.result(move);
				state.board.printGame();
				if (!state.checkTerminal()) {
					int randomcolumn = randomcolumn(columnNumber);
					System.out.println(randomcolumn);
					System.out.println("computer's move: " + randomcolumn);
					System.out.println();
					state = state.result(randomcolumn);
					state.board.printGame();
				}
				}
			if (state.board.row(1, state.num_in_row) || state.board.col(1, state.num_in_row) || state.board.diag(1, state.num_in_row)) {
				System.out.println("You won!");
			}
			else if (state.board.row(2, state.num_in_row) || state.board.col(2, state.num_in_row) || state.board.diag(2, state.num_in_row)) {
				System.out.println("You lost!");
			}
			else {
				System.out.println("You tied!");
			}
			}
			if(turnchoice == 2) {
				while (!state.checkTerminal()) {
					int randomcolumn = randomcolumn(columnNumber);
					System.out.println(randomcolumn);
					System.out.println("computer's move: " + randomcolumn);
					System.out.println();
					state = state.result(randomcolumn);
					state.board.printGame();
					if (!state.checkTerminal()) {
						System.out.println("enter a column");
						move = sc.nextInt();					
						while(move < 0 || move >= columnNumber) {
							System.out.print("Please enter a valid option: ");
							move = sc.nextInt();
						}
						state = state.result(move);
						state.board.printGame();
					}	
				}
				if (state.board.row(1, state.num_in_row) || state.board.col(1, state.num_in_row) || state.board.diag(1, state.num_in_row)) {
					System.out.println("You lost!");
				}
				else if (state.board.row(2, state.num_in_row) || state.board.col(2, state.num_in_row) || state.board.diag(2, state.num_in_row)) {
					System.out.println("You won!");
				}
				else {
					System.out.println("You tied!");
				}
			}
		}
		
		//MINIMIAX
		if(opponentchoice == 2) {
			int move = 0;
			if(turnchoice == 1) {
			while (!state.checkTerminal()) {
				System.out.println("enter a column");
				move = sc.nextInt();					
				while(move < 0 || move >= columnNumber) {
					System.out.print("Please enter a valid option: ");
					move = sc.nextInt();
				}
				state = state.result(move);
				state.board.printGame();
				if (!state.checkTerminal()) {
					int utility = minimax(state);
					int mymove = bestmove(state, utility);
					System.out.println(utility);
					System.out.println("computer's move: " + mymove);
					System.out.println();
					state = state.result(mymove);
					state.board.printGame();
				}
			}
			if (state.board.row(1, state.num_in_row) || state.board.col(1, state.num_in_row) || state.board.diag(1, state.num_in_row)) {
				System.out.println("You won!");
			}
			else if (state.board.row(2, state.num_in_row) || state.board.col(2, state.num_in_row) || state.board.diag(2, state.num_in_row)) {
				System.out.println("You lost!");
			}
			else {
				System.out.println("You tied!");
			}
			
			}
			if(turnchoice == 2) {
				while (!state.checkTerminal()) {
					int utility = minimax(state);
					int mymove = bestmove(state, utility);
					System.out.println(utility);
					System.out.println("computer's move: " + mymove);
					System.out.println();
					state = state.result(mymove);
					state.board.printGame();
					if (!state.checkTerminal()) {
						System.out.println("enter a column");
						move = sc.nextInt();					
						while(move < 0 || move >= columnNumber) {
							System.out.print("Please enter a valid option: ");
							move = sc.nextInt();
						}
						state = state.result(move);
						state.board.printGame();
					}	
				}
				if (state.board.row(2, state.num_in_row) || state.board.col(2, state.num_in_row) || state.board.diag(2, state.num_in_row)) {
					System.out.println("You won!");
				}
				else if (state.board.row(1, state.num_in_row) || state.board.col(1, state.num_in_row) || state.board.diag(1, state.num_in_row)) {
					System.out.println("You lost!");
				}
				else {
					System.out.println("You tied!");
				}
			}
		}

		// ALPHA BETA PRUNING
		if(opponentchoice == 3) {
			int move = 0;
			if(turnchoice == 1) {
				while (!state.checkTerminal()) {
					System.out.println("enter a column");
					move = sc.nextInt();					
					while(move < 0 || move >= columnNumber) {
						System.out.print("Please enter a valid option: ");
						move = sc.nextInt();
					}
					state = state.result(move);
					state.board.printGame();
					if (!state.checkTerminal()) {
						int utility = alphabeta(state);
						int mymove = abbest(state, utility);
						System.out.println("computer's move: " + mymove);
						System.out.println();
						state = state.result(mymove);
						state.board.printGame();
					}
					}
				if (state.board.row(1, state.num_in_row) || state.board.col(1, state.num_in_row) || state.board.diag(1, state.num_in_row)) {
					System.out.println("You won!");
				}
				else if (state.board.row(2, state.num_in_row) || state.board.col(2, state.num_in_row) || state.board.diag(2, state.num_in_row)) {
					System.out.println("You lost!");
				}
				else {
					System.out.println("You tied!");
				}
			}
			if(turnchoice == 2) {
			while (!state.checkTerminal()) {
				int utility = alphabeta(state);
				int mymove = abbest(state, utility);
				System.out.println("computer's move: " + mymove);
				System.out.println();
				state = state.result(mymove);
				state.board.printGame();
				if (!state.checkTerminal()) {
					System.out.println("enter a column");
					move = sc.nextInt();
					state = state.result(move);
					state.board.printGame();
					}
				}
			if (state.board.row(2, state.num_in_row) || state.board.col(2, state.num_in_row) || state.board.diag(2, state.num_in_row)) {
				System.out.println("You won!");
			}
			else if (state.board.row(1, state.num_in_row) || state.board.col(1, state.num_in_row) || state.board.diag(1, state.num_in_row)) {
				System.out.println("You lost!");
			}
			else {
				System.out.println("You tied!");
			}
			}
		}
		
		// HEURISTIC
		if(opponentchoice == 4) {
			int move = 0;
			while (!state.checkTerminal()) {
				Long start = System.currentTimeMillis();
				double utility = heuristicminimax(state, 0, depthlimit);
				int mymove = heuristicbest(state, utility, depthlimit);
				Long end = System.currentTimeMillis();
				System.out.println("computer's move: " + mymove);
				System.out.println("time elapsed: " + (end-start)/1000 + " seconds");
				System.out.println();
				state = state.result(mymove);
				state.board.printGame();
				if (!state.checkTerminal()) {
					System.out.println("enter a column");
					move = sc.nextInt();					
					while(move < 0 || move >= columnNumber) {
						System.out.print("Please enter a valid option: ");
						move = sc.nextInt();
					}
					state = state.result(move);
					state.board.printGame();
					}
				}
			if (state.board.row(2, state.num_in_row) || state.board.col(2, state.num_in_row) || state.board.diag(2, state.num_in_row)) {
				System.out.println("You won!");
			}
			else if (state.board.row(1, state.num_in_row) || state.board.col(1, state.num_in_row) || state.board.diag(1, state.num_in_row)) {
				System.out.println("You lost!");
			}
			else {
				System.out.println("You tied!");
			}
			}
		}
	
	public static int randomcolumn(int columnNumber) {
		return (int) (Math.random()*(columnNumber));
	}

	// MINIMAX IMPLEMENTATION
	public static int minimax(State s) {
		State s_copy = s.makecopy();
		if (s_copy.checkTerminal()) {
			return s_copy.calcUtility();
		}
		else if (s_copy.player == 1) {
			int max = -10;
			for (int i : s_copy.getActions()) {
				State newstate = s_copy.result(i);
				int utility = minimax(newstate);
				if (utility > max) {
					max = utility;
				}
			}
			return max;
		}
		else {
			int min = 10;
			for (int i : s_copy.getActions()) {
				State newstate = s_copy.result(i);
				int utility = minimax(newstate);
				if (utility < min) {
					min = utility;
				}
			}
			return min;
		}
	}
	
	public static int bestmove(State s, int utility) {
		int mymove = -1;
		State newstate = s.makecopy();
		for (int i : newstate.getActions()) {
			if (minimax(newstate.result(i)) == utility) {
				mymove = i;
				return mymove;
			}
		}
		return mymove;
	}
	
	// ALPHA BETA IMPLEMENTATION
	public static int alphabeta(State s) {
		int v;
		if (s.player == 1) {
			v = max_value(s, -10000, 10000);
		}
		else {
			v = min_value(s, -10000, 10000);
		}
		return v;
	}
	
	public static int max_value(State s, int alpha, int beta) {
		State s_copy = s.makecopy();
		if (s_copy.checkTerminal()) {
			return s_copy.calcUtility();
		}
		int v = -10000;
		for (int a : s_copy.getActions()) {
			v = Math.max(v, min_value(s_copy.result(a), alpha, beta));
			if (v >= beta) {
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;
	}
	
	public static int min_value(State s, int alpha, int beta) {
		State s_copy = s.makecopy();
		if (s_copy.checkTerminal()) {
			return s_copy.calcUtility();
		}
		int v = 10000;
		for (int a : s_copy.getActions()) {
			v = Math.min(v, max_value(s_copy.result(a), alpha, beta));
			if (v <= alpha) {
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}
	
	private static int abbest(State s, int utility) {
		int mymove = -1;
		State newstate = s.makecopy();
		for (int i : newstate.getActions()) {
			if (alphabeta(newstate.result(i)) == utility) {
				mymove = i;
				return mymove;
			}
		}
		return mymove;
	}
	
	// HEURISTIC IMPLEMENTATION
	private static double heuristicminimax(State s, int depth, int depthlim) {
		int depth_copy = 0;
		for (int i = 0; i < depth; i++) {
			depth_copy += 1;
		}
		State s_copy = s.makecopy();
		if (depth_copy == depthlim) {
			return s_copy.heuristicUtility();
		}
		else if (s_copy.player == 1) {
			double max = -100;
			for (int i : s_copy.getActions()) {
				State newstate = s_copy.result(i);
				double utility = heuristicminimax(newstate, depth_copy + 1, depthlim);
				if (utility > max) {
					max = utility;
				}
			}
			return max;
		}
		else {
			double min = 100;
			for (int i : s_copy.getActions()) {
				State newstate = s_copy.result(i);
				double utility = heuristicminimax(newstate, depth_copy + 1, depthlim);
				if (utility < min) {
					min = utility;
				}
			}
			return min;
		}
	}
	
	private static int heuristicbest(State s, double utility, int depthlim) {
		int mymove = -1;
		State newstate = s.makecopy();
		for (int i : newstate.getActions()) {
			if (heuristicminimax(newstate.result(i), 1, depthlim) == utility) {
				mymove = i;
				return mymove;
			}
		}
		return mymove;
	}
	
}
