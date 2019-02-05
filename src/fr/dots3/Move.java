
package fr.dots3;

/**
 * @author Lo�c Mac�
 * @brief Mouvement
 * @see Square#getMove(int)
 * @see Square#getNbOfMoves()
 */
public class Move {

	private int x;
	private int y;
	private char or;
	
	public Move(int x, int y, char or) {
		assert((x >= 0 && x < Board.SIZE) 
				&& (y >= 0 && y < Board.SIZE) 
				&& (or == 'h' || or == 'v'));
		this.x = x;
		this.y = y;
		this.or = or;
	}
	
	/**
	 * @brief Accesseur : x
	 * @return Coordon�e x du mouvement
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @brief Accesseur : y
	 * @return Coordon�e y du mouvement
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @brief Accesseur : or
	 * @return Orientation du mouvement
	 */
	public char getOr() {
		return or;
	}
}
