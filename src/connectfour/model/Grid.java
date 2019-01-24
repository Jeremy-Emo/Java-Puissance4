package connectfour.model;

/**
 * Une grille du jeu Connect Four.
 * Elle se caract�rise par deux dimensions x et y.
 * A chaque position (x, y) est situ� un jeton de type Tokens ou null.
 * 
 * Pour ajouter un jeton, on choisit la couleur (Tokens) et la colonne (x).
 * Le jeton "tombe" alors au plus petit y disponible (en (x,y) == null).
 * Une exception est lev�e si une colonne d�borde, c'est-�-dire si on tente
 *  de mettre un jeton dans une colonne remplie.
 */
public interface Grid {
	
	/**
	 * R�cup�re le jeton pr�sent en (x,y).
	 * @param x la colonne
	 * @param y la ligne
	 * @return le jeton pr�sent, null si aucun
	 */
	Tokens getToken(int x, int y);
	
	/**
	 * Ins�re le jeton token dans la colonne x.
	 * Le jeton descend jusqu'� atteindre la derni�re ligne y
	 *  o� getToken(x, y) == null.
	 * @param token le jeton � ins�rer
	 * @param x la colonne cible
	 */
	void putToken(Tokens token, int x) throws ConnectException;
	
	/**
	 * R�cup�re la ligne dans laquelle se trouve le dernier jeton d�pos�.
	 * @return la ligne du dernier jeton, null si aucun jeton n'a �t� pos�
	 */
	Integer getRowOfLastPutToken();
	
	/**
	 * Initialise la grille, en la vidant de tous ses jetons.
	 */
	void init();

}
