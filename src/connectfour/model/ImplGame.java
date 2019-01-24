package connectfour.model;
import java.lang.Math;

public class ImplGame implements Game {
	
	private final Grid grid;
	private Tokens currentPlayer;
	private static final Tokens[] TOKEN_VALUES = Tokens.values();
	public boolean over;
	public Tokens winner;
	
	public ImplGame(){
		this.grid = new ImplListGrid(Game.COLUMNS, Game.ROWS);
		this.init();
	}

	@Override
	public Tokens getToken(int x, int y) {
		return this.grid.getToken(x, y);
	}

	@Override
	public Tokens getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	private Tokens getNextPlayer() {
		return ( ( (this.currentPlayer.ordinal() + 1) < TOKEN_VALUES.length ) ? TOKEN_VALUES[this.currentPlayer.ordinal() + 1 ] : TOKEN_VALUES[0]); 
	}

	@Override
	public boolean isOver() {
		return this.over;
	}

	@Override
	public Tokens getWinner() {
		return this.winner;
	}

	@Override
	public void putToken(int column) throws ConnectException {
		this.grid.putToken(this.getCurrentPlayer(), column);
		this.over = this.calculateOver(column);
		if(!this.over) {
			this.currentPlayer = this.getNextPlayer();
		}
	}
	
	private boolean calculateOver(int column) {
		if(
			this.inspectNWSE(column, this.grid.getRowOfLastPutToken()) >= Game.REQUIRED_TOKENS
			|| this.inspectSouth(column, this.grid.getRowOfLastPutToken()) >= Game.REQUIRED_TOKENS
			|| this.inspectWestEast(column, this.grid.getRowOfLastPutToken()) >= Game.REQUIRED_TOKENS
			|| this.inspectNESW(column, this.grid.getRowOfLastPutToken()) >= Game.REQUIRED_TOKENS
		) {
			this.winner = this.currentPlayer;
			return true;
		}
		for(int i = 0; i < Game.COLUMNS; i++) {
			if(this.grid.getToken(i, Game.ROWS - 1) == null) {
				return false;
			}
		}
		return true;
	}
	
	private int inspectNWSE(int x, int y) {
		int foundInLine = 0;
		for (int i = 1; x - i >= 0 && y + i < ROWS && getToken(x - i, y + i) == currentPlayer; i++) {
			foundInLine++;
		}
		for (int i = 1; x + i < COLUMNS && y - i >= 0 && getToken(x + i, y - i) == currentPlayer; i++) {
			foundInLine++;
		}
		return foundInLine + 1;
	}
	
	private int inspectSouth(int x, int y) {
		int foundInLine = 0;
		for (int i = 1; x < COLUMNS && y - i >= 0 && getToken(x, y - i) == currentPlayer; i++) {
			foundInLine++;
		}
		return foundInLine + 1;
	}
	
	private int inspectWestEast(int x, int y) {
		int foundInLine = 0;
		for (int i = 1; x - i >= 0 && y < ROWS && getToken(x - i, y) == currentPlayer; i++) {
			foundInLine++;
		}
		return foundInLine + 1;
	}
	
	private int inspectNESW(int x, int y) {
		int foundInLine = 0;
		for (int i = 1; x - i >= 0 && y - i >= 0 && getToken(x - i, y - i) == currentPlayer; i++) {
			foundInLine++;
		}
		for (int i = 1; x + i < COLUMNS && y + i < ROWS && getToken(x + i, y + i) == currentPlayer; i++) {
			foundInLine++;
		}
		return foundInLine + 1;
	}
	

	@Override
	public void init() {
		this.grid.init();
		this.over = false;
		this.winner = null;
		this.currentPlayer = TOKEN_VALUES[(int) Math.floor(Math.random() * TOKEN_VALUES.length)];
	}

}
