package connectfour.model.score;

import java.util.Date;

import connectfour.model.Tokens;

public class ImplScore implements Score {
	
	public final Date date;
	public final Tokens winner;
	public final String winner_type;
	
	ImplScore(Date date, Tokens winner, String type){
		this.date = date;
		this.winner = winner;
		this.winner_type = type;
	}

	@Override
	public Date getDate() {
		return this.date;
	}

	@Override
	public Tokens getWinner() {
		return this.winner;
		
	}

	@Override
	public String getWinnerType() {
		return "Human";
		//return this.winner_type;
	}

}
