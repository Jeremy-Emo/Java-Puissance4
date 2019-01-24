package connectfour.model;

public class ImplGrid implements Grid {
	
	private Integer rowOfLastPutToken;
	private final Tokens[][] grid;
	
	ImplGrid(int x, int y){
		if(x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		this.grid = new Tokens[x][y];
	}

	@Override
	public Tokens getToken(int x, int y) {
		if(x > this.grid.length || y > this.grid[x].length) {
			throw new IllegalArgumentException();
		}
		return this.grid[x][y];
	}

	@Override
	public void putToken(Tokens token, int x) throws ConnectException {
		if(token == null) {
			throw new IllegalArgumentException();
		}
		if(x >= this.grid.length) {
			throw new ConnectException("PAS ASSEZ DE COLONNES !!!");
		}
		int y = 0;
		while(this.getToken(x, y) != null) {
			y++;
			if(y >= this.grid[x].length) {
				throw new ConnectException("TROP HAUT !!!");
			}
		}
		
		this.grid[x][y] = token;
		this.rowOfLastPutToken = y;
	}

	@Override
	public Integer getRowOfLastPutToken() {
		return this.rowOfLastPutToken;
	}

	@Override
	public void init() {
		for(int i = 0; i<this.grid.length; i++) {
			for(int j = 0; j<this.grid[i].length; j++) {
				this.grid[i][j] = null;
			}
		}
		this.rowOfLastPutToken = null;
	}

}
