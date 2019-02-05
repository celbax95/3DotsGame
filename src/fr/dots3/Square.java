package fr.dots3;

import java.util.ArrayList;

/**
 * @author Loïc Macé
 * @brief Carré du plateau
 */
public class Square {

	private static ArrayList<Move> moves = new ArrayList<Move>();
	private int x;
	private int y;
	private boolean spot;
	private char piece;
	private String dep;
	
	public Square(int x,int y) {
		assert((x >= 0 && x < Board.SIZE) && (y >= 0 && y < Board.SIZE));
		this.x = x;
		this.y = y;
		if (this.x == Board.SIZE-1)
			this.spot = true;
		this.dep = "";
		this.piece = 0;
	}
	
	/*
	 * Getters - Setters (Junit)
	 */
	
	public char getPiece() {return piece;}
	public char[] getDep() {
		char[] c = new char[dep.length()];
		for (int i = 0; i < dep.length(); i++) {
			c[i] = dep.charAt(i);
		}
		return c;
	}
	
	/*
	 * *************************
	 */
	

	/**
	 * @brief Accesseur : x
	 * @return Coordonée x du carré
	 */
	public int getX() {
		return x;
	}

	/**
	 * @brief Accesseur : y
	 * @return Coordonée y du carré
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @brief Setter : piece
	 * @param p : couleur de la piece (char)
	 */
	public void setPiece(char p) {
		assert(p == 'R' || p == 'B' || p == 'W');
		this.piece = p;
	}
	
	/**
	 * @brief Affiche le contenu d'un carré
	 * @see java.lang.Object#toString()
	 * @see Board#toString()
	 * @return Valeur du carré (String formatée)
	 */
	public String toString() {		
		if (this.piece != 0)
			return "  " + String.valueOf(this.piece)+ "  ";
		else if (this.x == 2)
			return "  •  ";
		else
			return "     ";
	}
	
	/**
	 * @brief Affichage le contenu d'un carré sans la piece spécifiée 
	 * mais avec ses mouvements possibles
	 * @param color : couleur de la piece (char)
	 * @return Valeur du carré (String formatée)
	 */
	public String toString(char color) {
		assert(color == 'R' || color == 'B' || color == 'W');
		if (this.piece != 0 && this.piece != color)
			return "  " + String.valueOf(this.piece)+ "  ";
		else if (this.hasMoveOn()) {
			if (this.dep.length() > 1)
				return this.dep;
			else
				return "  " + this.dep + "  ";
		}
		else if (this.x == 2)
			return "  •  ";
		else
			return "     ";
	}
	
	/**
	 * @brief Setter : piece
	 * @param p : Piece (Piece)
	 */
	public void setPiece(Piece p) {
		if ((p.getX() == this.x && p.getY() == this.y) || (p.getX2() == this.x && p.getY2() == this.y))
			this.piece = p.getColor();
	}
	
	/**
	 * @brief Enleve une piece spécifiée du carré
	 * @param p : Piece à enlever (Piece)
	 */
	public void removePiece(Piece p) {
		if (this.piece == p.getColor())
			this.piece = 0;
	}
	
	/**
	 * @brief Réinitialise le déplacement d'un carré
	 */
	public void removeDep() {
		this.dep = "";
	}
	
	/**
	 * @brief Setter : dep
	 * @param z : Numero du  déplacement (int)
	 */
	public void setDep(int z) {
		assert(z == Square.getNbOfMoves());
		if (this.dep.isEmpty())
			this.dep = String.valueOf(z);
		else
			this.dep += " - " + String.valueOf(z);
	}
	
	/**
	 * @brief Reset du tableau de mouvements
	 */
	public static void removeMoves() {
		Square.moves.removeAll(moves);
	}
	
	/**
	 * @brief Ajout d'un mouvement
	 * @param x : Coordonée x du mouvement
	 * @param y : Coordonée y du mouvement
	 * @param or : Orientaion du mouvement
	 */
	public static void addMove(int x, int y, char or) {
		assert((x >= 0 && x < Board.SIZE) 
				&& (y >= 0 && y < Board.SIZE) 
				&& (or == 'h' || or == 'v'));
		assert((or == 'h' && x < Board.SIZE-1) ||
				(or == 'v' && y > 0));
		Square.moves.add(new Move(x,y,or));
	}
	
	/**
	 * @brief Test si un mouvement est présent sur la case
	 * @return true si un mouvement est présent, false si non
	 */
	public boolean hasMoveOn() {
		for (Move m : Square.moves) {
			if (this.x == m.getX() && this.y == m.getY())
				return true;
		}
		return false;
	}
	
	/**
	 * @brief Retoune une liste des mouvements de la case
	 * @return Liste des mouvements de la case (ArrayList<Move>)
	 */
	public ArrayList<Move> MovesOn() {
		ArrayList<Move> am = new ArrayList<Move>();
		for (Move m : Square.moves) {
			if (this.x == m.getX() && this.y == m.getY())
				am.add(m);
		}
		return am;
	}
	
	/**
	 * @brief Accesseur : Nombre de mouvements possibles
	 * @return Nombre de mouvements possibles
	 */
	public static int getNbOfMoves() {
		return Square.moves.size();
	}
	
	/**
	 * @brief Accesseur : Mouvement en fonction de son numero
	 * @param i : Numero du deplacement
	 * @return Mouvement correspondant au numero (Move) 
	 */
	public static Move getMove(int i) {
		assert(i < Square.getNbOfMoves());
		return Square.moves.get(i);
	}
}
