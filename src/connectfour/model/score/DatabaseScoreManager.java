package connectfour.model.score;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import connectfour.model.ConnectException;
import connectfour.model.Tokens;

public class DatabaseScoreManager implements ScoreManager {
	
	private final String DRIVER = "jdbc:mysql://";
	private final String DATABASE_HOST = "db4free.net/";
	private final String DATABASE_NAME = "";
	private final String DATABASE_USER = "";
	private final String DATABASE_PASSWORD = "";
	private final String TABLE_NAME = "";
	
	public Connection connection;
	
	private void initConnection() throws SQLException {
		if(this.connection == null) {
			try {
				this.connection = DriverManager.getConnection(this.DRIVER + this.DATABASE_HOST + this.DATABASE_NAME + "?useSSL=false", this.DATABASE_USER, this.DATABASE_PASSWORD);
			} catch(Exception e) {
				e.getMessage();
			}
		}
	}
	
	private Score resultToScore(ResultSet set) throws SQLException {
		Date date = new Date(set.getTimestamp("date").getTime());
		Tokens winner = Tokens.valueOf(set.getString("winner"));
		String winner_type = set.getString("winnerType");
		Score score = new ImplScore(date, winner, winner_type);
		return score;
	}


	@Override
	public List<Score> getLastScores(int count) throws ConnectException {
		List<Score> scores = new ArrayList<Score>();
		Statement state;
		try {
			this.initConnection();
			state = this.connection.createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM " + this.TABLE_NAME + " LIMIT " + count);
			while(rs.next()) {
				scores.add(this.resultToScore(rs));
			}
			rs.close();
			state.close();
			
		} catch (Exception e) {
			e.getMessage();
		}
		return scores;
	}

	@Override
	public void saveScore(Tokens winner, String winnerType) throws ConnectException {
		Statement state;
		try {
			this.initConnection();
			state = this.connection.createStatement();
			state.executeUpdate("INSERT INTO " + this.TABLE_NAME + "(date, winner, winnerType) VALUES('" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "', '" + winner.name() + "', '" + winnerType + "')");
			state.close();
			
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
