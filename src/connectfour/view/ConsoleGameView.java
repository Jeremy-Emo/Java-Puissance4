package connectfour.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import connectfour.model.ConnectException;
import connectfour.model.Game;
import connectfour.model.Tokens;
import connectfour.model.score.DatabaseScoreManager;
import connectfour.model.score.Score;
import connectfour.model.score.ScoreManager;

public class ConsoleGameView implements GameView {
	
	// ATTRIBUTES
	
	private final Game game;
	
	private final BufferedReader reader;
	private String userInput;
	private String errorMessage;
	private boolean exitRequest;
	private final static String KEYWORD_EXIT = "exit";
	private final static String KEYWORD_RESTART = "restart";
	private final static String KEYWORD_SCORES = "scores";
	public final ScoreManager scoreManager;
	
	// CONSTRUCTOR
	
	public ConsoleGameView(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game ne peut �tre null");
		}
		this.game = game;
		this.scoreManager = new DatabaseScoreManager();
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	// METHODS

	@Override
	public void play() {
		clearConsole();
		System.out.println("Vous pouvez relancer une partie avec la commande " + KEYWORD_RESTART + ".");
		System.out.println("Vous pouvez afficher les scores avec la commande " + KEYWORD_SCORES + ".");
		System.out.println("Vous pouvez quitter le jeu avec la commande " + KEYWORD_EXIT + ".");
		displayGrid();
		try {
			do {
				errorMessage = null;
				displayGameState();
				userInput = readUserInput();
				switch (userInput) {
					case KEYWORD_EXIT:
						exitRequest = true;
						break;
					case KEYWORD_RESTART:
						this.game.init();
						break;
					case KEYWORD_SCORES:
						System.out.println("Chargement...");
						this.displayScores(this.scoreManager.getLastScores(10));
						System.out.println("Appuyer sur la touche Entrée.");
						readUserInput();
						break;
					default:
						if (!game.isOver()) {
							try {
								int column = Integer.parseInt(userInput);
								game.putToken(column);
								if(game.isOver()) {
									this.scoreManager.saveScore(game.getWinner(), "Human");
								}
								clearConsole();
							} catch (NumberFormatException e) {
								errorMessage = "Je n'ai pas compris cette r�ponse...";
							} catch (ConnectException e) {
								errorMessage = e.getMessage();
							} 
						} else {
							errorMessage = "Je n'ai pas compris cette r�ponse...";
						}
						break;
				}
				if (errorMessage != null) {
					System.err.println(errorMessage);
				}
			} while (errorMessage != null);
		} catch (Exception e) {
			e.printStackTrace();
			exitRequest = true;
		} 
		if (!exitRequest) {
			play();
		}
	}
	
	// TOOLS
	
	private String readUserInput() throws IOException {
		return reader.readLine();
	}
	
	private void displayGameState() {
		if (game.isOver()) {
			Tokens winner = game.getWinner();
			if (winner == null) {
				System.out.println("La partie s'est termin�e sur un match nul.");
			} else {
				System.out.println("La partie a �t� remport�e par " + winner + " !");
			}
		} else {
			System.out.print("C'est au tour de [" + game.getCurrentPlayer() + "] ! [0-" + (Game.COLUMNS - 1) + "] : ");
		}
	}
	
	private void displayGrid() {
		StringBuffer output = new StringBuffer();
		Tokens token;
		for (int x = 0; x < Game.COLUMNS; x++) {
			output.append("   " + x + "  ");
		}
		output.append('\n');
		for (int y = Game.ROWS - 1; y >= 0; y--) {
			for (int x = 0; x < Game.COLUMNS; x++) {
				output.append("|     ");
			}
			output.append("|\n");
			for (int x = 0; x < Game.COLUMNS; x++) {
				output.append("|  ");
				token = game.getToken(x, y);
				if (token == null) {
					output.append(' ');
				} else {
					output.append(token);
				}
				output.append("  ");
			}
			output.append("|\n");
			for (int x = 0; x < Game.COLUMNS; x++) {
				output.append("|_____");
			}
			output.append("|\n");
		}
		System.out.println(output.toString());
	}
	
	private void clearConsole() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}
	
	private void displayScores(List<Score> scores) {
		if (scores.size() == 0) {
			System.out.println("Vous n'avez encore joué aucune partie. :)");
		} else {
			System.out.println(String.format("%-20s", "Date") + "| Vainqueur | Type");
			System.out.println("--------------------|-----------|----------");
			for (Score score : scores) {
				System.out.println(String.format("%-20s", LocalDateTime.ofInstant(score.getDate().toInstant(),
				ZoneId.systemDefault())) +
				"| " + String.format("%-10s", score.getWinner() == null ? "Match nul" : score.getWinner().name()) +
				"| " + (score.getWinnerType() == null ? "" : score.getWinnerType()));
				System.out.println("--------------------|-----------|----------");
			}
		}
	}

}
