package connectfour.model.score;

import java.util.List;

import connectfour.model.ConnectException;
import connectfour.model.Tokens;

public interface ScoreManager {

	List<Score> getLastScores(int count) throws ConnectException;
	
	void saveScore(Tokens winner, String winnerType) throws ConnectException;

}
