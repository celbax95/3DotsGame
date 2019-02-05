package fr.dots3;

/**
 * @author Loïc Macé
 * @brief Piece du jeu (Blanche, Rouge, Bleue)
 */
public class Piece {
	private char color;
	private int x;
	private int y;
	private char or; //Orientation
	
	/**
	 * @brief Constructeur de la classe Piece
	 * @param ind : Indice de la piece 0 = Rouge | 1 = Bleu | 2 = Blanc
	 */
	public Piece(int ind) {
		assert((ind>=0 && ind < Board.MAX_PIECES));
		this.x = 1;
		this.or = 'h';
		
		switch(ind) {
		case(0):
			this.color = 'R';
			this.y = 0;
			break;
		case(1):
			this.color = 'B';
			this.y = 2;
			break;
		case(2):
			this.color = 'W';
			this.y = 1;
			break;
		}
	}
	
	/**
	 * @brief Constructeur utilisé pour les tests Junit
	 * @param c : Couleur de la piece
	 * @param x : Position en x
	 * @param y : Position en y
	 * @param or : Orientation
	 */
	public Piece(char c, int x, int y, char or) {
		c = Character.toUpperCase(c);
		assert((c == 'R' || c == 'B' || c == 'W') &&
				(x >= 0 && x < Board.SIZE) &&
				(y >= 0 && y < Board.SIZE) &&
				(or == 'h' || or == 'v'));
		this.color = c;
		this.x = x;
		this.y = y;
		this.or = or;
	}
	
	
	/**
	 * @brief Accesseur : color
	 * @return Couleur de la piece
	 */
	public char getColor() {
		return color;
	}

	/**
	 * @brief Accesseur : x
	 * @return Coordonée x de la premiere partie de la piece
	 */
	public int getX() {
		return x;
	}

	/**
	 * @brief Accesseur : y
	 * @return Coordonée y de la premiere partie de la piece
	 */
	public int getY() {
		return y;
	}

	/**
	 * @brief Calcul de la coordonée x de la seconde partie de la piece
	 * @return Coordonée x de la seconde partie de la piece
	 */
	public int getX2() {
		if (this.or == 'h')
			return x+1;
		else
			return x;
	}
	
	/**
	 * @brief Calcul de la coordonée y de la seconde partie de la piece
	 * @return Coordonée y de la seconde partie de la piece
	 */
	public int getY2() {
		if (this.or == 'v')
			return y-1;
		else
			return y;
	}
	
	/**
	 * @brief Accesseur : or
	 * @return Orientation de la piece
	 */
	public char getOr() {
		return or;
	}
	
	/**
	 * @brief Applique un mouvement a la piece
	 * @param m : Movement à appliquer (Move)
	 */
	public void move(Move m) {
		assert((m.getOr() == 'h' && m.getX() >= 0 && m.getX() < Board.SIZE-1 && 
				m.getY() >= 0 && m.getY() < Board.SIZE) ||
				(m.getOr() == 'v' && m.getX() >= 0 && m.getX() < Board.SIZE && 
				m.getY() > 0 && m.getY() < Board.SIZE)
				);
		this.x = m.getX();
		this.y = m.getY();
		this.or = m.getOr();
	}
}
