package connectfour.model;

import java.util.ArrayList;
import java.util.List;

public class ImplListGrid implements Grid {
	
	private Integer rowOfLastPutToken;
	private final List<List<Tokens>> grid;
	private int rows;
	private int columns;
	
	ImplListGrid(int x, int y){
		if(x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		this.grid = new ArrayList<List<Tokens>>();
		this.columns = x - 1;
		this.rows = y - 1;
		while(y >= 0) {
			y--;
			this.grid.add(new ArrayList<Tokens>());
		}
	}

	@Override
	public Tokens getToken(int x, int y) {
		if(x > this.columns || y > this.rows) {
			throw new IllegalArgumentException();
		}
		Tokens value;
		try {
			value =  this.grid.get(x).get(y);
		} catch(Exception e) {
			value = null;
		}
		return value;		
	}

	@Override
	public void putToken(Tokens token, int x) throws ConnectException {
		if(token == null) {
			throw new IllegalArgumentException();
		}
		if(x > this.columns) {
			throw new ConnectException("PAS ASSEZ DE COLONNES !!!");
		}
		int y = this.grid.get(x).size();
		if(y > this.rows) {
			throw new ConnectException("TROP HAUT !!!");
		}
		
		this.grid.get(x).add(token);
		this.rowOfLastPutToken = y;
	}

	@Override
	public Integer getRowOfLastPutToken() {
		return this.rowOfLastPutToken;
	}

	@Override
	public void init() {
		for(int i = 0; i<this.grid.size(); i++) {
			for(int j = 0; j<this.grid.get(i).size(); j++) {
				this.grid.get(i).set(j, null);
			}
		}
		this.rowOfLastPutToken = null;
	}

}
