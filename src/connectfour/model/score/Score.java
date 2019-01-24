package connectfour.model.score;

import java.util.Date;

import connectfour.model.Tokens;

public interface Score {
	
	Date getDate();
	
	Tokens getWinner();
	
	String getWinnerType();
}
