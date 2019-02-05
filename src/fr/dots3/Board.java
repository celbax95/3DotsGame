package fr.dots3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Loïc Macé
 * @brief Plateau de jeu
 * @see Piece
 * @see Square
 */
public class Board {
	// Taille du plateau (3x3 Cases)
	public static final int SIZE = 3;
	// Nombre de pieces
	public static final int MAX_PIECES = 3;
	
	private Square[][] squares;
	private Piece[] pieces;
	
	public Board() {
		// Initialisation des cases
		squares = new Square[Board.SIZE][Board.SIZE];
		for (int i=0; i<squares.length; i++) {
			for (int j=0; j<squares[i].length; j++) {
				squares[i][j] = new Square(j,i);
			}
		}

		// Initialisation des pieces
		pieces = new Piece[MAX_PIECES];
		for (int i=0; i<this.pieces.length;i++)
			pieces[i] = new Piece(i);
		
		// Placement des pieces sur les cases
		for (int i = 0; i < pieces.length; i++) {
			this.setPiece(this.pieces[i]);
		}
	}
	
	/*
	 * Getters - Setters (Junit)
	 */
	
	Piece[] getPieces() {return pieces;}
	/**
	 * @brief Calcul du score marqué par la piece au moment de l'appel
	 * @param ind : Indice de la piece (int)
	 * @return Score marqué par la piece
	 */
	public int getScore(int ind) {
		assert((ind>=0 && ind < Board.MAX_PIECES));
		int s = 0;
		// Premiere partie de la piece
		if (((this.pieces[ind].getX() == Board.SIZE-1) && (this.pieces[ind].getY()>=0 && this.pieces[ind].getY()<Board.SIZE)))
				s++;
		// Seconde partie de la piece
		if (((this.pieces[ind].getX2() == Board.SIZE-1) && (this.pieces[ind].getY()>=0 && this.pieces[ind].getY2()<Board.SIZE)))
			s++;
		return s;
	}
	
	/*
	 ************************** 
	 */
	
	Square[][] getSquares() {return squares;}
	
	/**
	 * @brief Verifie qu'un mouvement horizontal est possible
	 * @param x : Coordonée x testée (int)
	 * @param y : Coordonée y testée (int)
	 * @param j2 : Piece testée (int)
	 * @param ind : Piece à jouer (int)
	 * @return false si mouvement impossible, true si mouvement possible
	 */
	private boolean horizontalMoves(int x, int y, int j2, int ind) {
		assert((x >= 0 && x < Board.SIZE) && (y >= 0 && y < Board.SIZE) 
				&& (j2 >= 0 && j2 < Board.MAX_PIECES) 
				&& (ind >= 0 && ind < Board.MAX_PIECES));
		// Case != premiere colonne
		if (x < Board.SIZE-1) {
			if (
				(
					// j2 != la piece à bouger
					(j2 != ind) && (
						// Premiere partie de la piece
						(x != this.pieces[j2].getX() || y != this.pieces[j2].getY())
						&& (x != this.pieces[j2].getX2() || y != this.pieces[j2].getY2())
						// Seconde partie de la piece
						&& (x+1 != this.pieces[j2].getX() || y != this.pieces[j2].getY())
						&& (x+1 != this.pieces[j2].getX2() || y != this.pieces[j2].getY2())
					)
					// j2 = la piece à bouger
					|| (j2 == ind) && (
						// Piece pas au même endroit
						(x != this.pieces[j2].getX() || y != this.pieces[j2].getY())
						|| (x+1 != this.pieces[j2].getX2() || y != this.pieces[j2].getY2())
					)
				)
			) {
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	/**
	 * @brief Deplace la piece spécifiée suivant le mouvement choisi
	 * @param ind : Indice de la piece (int)
	 * @param c : Indice du mouvement (int)
	 * @see Board#removePiece(Piece)
	 * @see Piece#move(Move)
	 * @see Board#setPiece(Piece)
	 */
	public void move(int ind, int c) {
		assert((ind>=0 && ind < Board.MAX_PIECES) && (c>=0 && c<Square.getNbOfMoves()));
		Move m = Square.getMove(c-1);
		this.removePiece(this.pieces[ind]);
		this.pieces[ind].move(m);
		this.setPiece(this.pieces[ind]);
	}
	
	/**
	 * @brief Deplace la piece spécifiée suivant les coordonnées choisies
	 * @param ind : Indice de la piece (int)
	 * @param y : Coordonées y du mouvement (int)
	 * @param x : Coordonées x du mouvement (int)
	 * @see Board#removePiece(Piece)
	 * @see Piece#move(Move)
	 * @see Board#setPiece(Piece)
	 */
	public void move(int ind, int x, int y) {
		assert((ind>=0 && ind < Board.MAX_PIECES)
				&& (x >= 0 && x < Board.SIZE) && (y >= 0 && y < Board.SIZE));
		ArrayList<Move> am = this.squares[y][x].MovesOn();
		Move m = null;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		if (am.size() <= 0)
			System.out.println("Pas de déplacement possible ici");
		else if (am.size() <= 1)
			m = am.get(0);
		else { //(am.size() > 1)
			/*
			 * On demande l'orientation si deux mouvements sont possibles
			 */
			System.out.println("Saisissez une orientation (h/v); : ");
			char c = Character.toLowerCase(sc.nextLine().charAt(0));
			while (c != 'h' && c != 'v') {
				System.out.println("Orientation incorrecte, réessayez : ");
				c = Character.toLowerCase(sc.nextLine().charAt(0));
			}
			for (Move tmpM : am) {
				if (tmpM.getOr() == c) {
					m = tmpM;
					break;
				}
			}
		}
		this.removePiece(this.pieces[ind]);
		this.pieces[ind].move(m);
		this.setPiece(this.pieces[ind]);
	}
	
	/**
	 * @brief Retire la piece des cases
	 * @param p : Piece à enelever (Piece)
	 * @see Board#setPiece(Piece)
	 */
	private void removePiece(Piece p) {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
					squares[i][j].removePiece(p);
			}
		}
	}
	
	/**
	 * @brief Initialisation des mouvements possiles de la piece
	 * @param ind : Indice de la piece
	 * @see Square#resetMoves()
	 * @see Square#resetDep()
	 * @see Square#addMove(int, int, char)
	 * @see Square#setDep(int)
	 */
	public void setMoves(int ind) {
		assert(ind == 0 || ind == 1 || ind == 2);
		// Reset des mouvements précédents
		Square.removeMoves();
		boolean h=false,v=false;
		// Compteur de mouvements
		int tmp = 1;
		
		// Pour chaque carré aux coordonées x y
		for (int y = 0; y < this.squares.length; y++) {
			for (int x = 0; x < this.squares[y].length; x++) {
				// Reset du déplacement précédent
				this.squares[y][x].removeDep();
				h = true;
				v = true;
				// Pour chaque piece
				for (int j2 = 0; j2 < this.pieces.length; j2++) {
					// Case != coin haut droite
					if (x != Board.SIZE-1 || y != 0) {
						
						// Deplacement vertical
						if (v) {
							v = this.verticalMoves(x,y,j2,ind);
							// Toutes les pieces ont été testées
							if ((j2 == Board.MAX_PIECES-1) && v) {
								// Ajout du numero du déplacement et ajout du Mouvement
								Square.addMove(x,y,'v');
								this.squares[y][x].setDep(tmp++);
							}							
						}
						
						// Deplacement horizontal
						if (h) {
							h = this.horizontalMoves(x,y,j2,ind);
							// Toutes les pieces ont été testées
							if ((j2 == Board.MAX_PIECES-1) && h) {
								// Ajout du numero du déplacement et ajout du Mouvement
								Square.addMove(x,y,'h');
								this.squares[y][x].setDep(tmp++);
							}							
						}
					}
				}
			}
		}
	}
	
	/**
	 * @brief Placement de la piece sur les cases
	 * @param p : Piece a placer (Piece)
	 * @see Board#removePiece(Piece)
	 */
	private void setPiece(Piece p) {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
					squares[i][j].setPiece(p);
			}
		}
	}

	/**
	 * @brief Affichage du plateau
	 * @see java.lang.Object#toString()
	 * @see Square#toString()
	 * @return Plateau (String formatée)
	 */
	@Override
	public String toString() {
		String s = "* * * * * * * * * * * * *";
		for (int i=0; i<Board.SIZE; i++) {
			s+= "\n*       *       *       *"
					+ "\n*";
			for (int j=0; j<Board.SIZE; j++) {
				s += " " + this.squares[i][j].toString() + " *";
			}
			s+= "\n*       *       *       *";
			s += "\n* * * * * * * * * * * * *";
		}
		return s;
	}
	
	/**
	 * @brief Affichage du plateau sans la piece spécifiée 
	 * mais avec ses mouvements possibles
	 * @param ind : Indice de la piece (int)
	 * @see Square#toString(char)
	 * @return Plateau (String formatée)
	 */
	public String toString(int ind) {
		assert(ind == 0 || ind == 1 || ind == 2);
		String s = "* * * * * * * * * * * * *";
		for (int i=0; i<Board.SIZE; i++) {
			s+= "\n*       *       *       *"
					+ "\n*";
			for (int j=0; j<Board.SIZE; j++) {
				s += " " + this.squares[i][j].toString(this.pieces[ind].getColor()) + " *";
			}
			s+= "\n*       *       *       *";
			s += "\n* * * * * * * * * * * * *";
		}
		return s;
	}
	
	/**
	 * @brief Verifie qu'un mouvement vertical est possible
	 * @param x : Coordonée x testée (int)
	 * @param y : Coordonée y testée (int)
	 * @param j2 : Piece testée (int)
	 * @param ind : Piece à jouer (int)
	 * @return false si mouvement impossible, true si mouvement possible
	 */
	private boolean verticalMoves(int x, int y, int j2, int ind) {
		assert((x >= 0 && x < Board.SIZE) && (y >= 0 && y < Board.SIZE) 
				&& (j2 >= 0 && j2 < Board.MAX_PIECES) 
				&& (ind >= 0 && ind < Board.MAX_PIECES));
		// Case != premiere ligne
		if (y > 0) {
			if (
					(
						// j2 != la piece à bouger
						(j2 != ind) && (
							// Premiere partie de la piece
							(x != this.pieces[j2].getX() || y != this.pieces[j2].getY())
							&& (x != this.pieces[j2].getX2() || y != this.pieces[j2].getY2())
							// Seconde partie de la piece
							&& (x != this.pieces[j2].getX() || y-1 != this.pieces[j2].getY())
							&& (x != this.pieces[j2].getX2() || y-1 != this.pieces[j2].getY2())
						)
						// j2 = la piece à bouger
						|| (j2 == ind) && (
							// Piece pas au même endroit
							(x != this.pieces[j2].getX() || y != this.pieces[j2].getY())
							|| (x != this.pieces[j2].getX2() || y-1 != this.pieces[j2].getY2())
						)
					)
				) {
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	/**
	 * @brief Obtention de l'indice d'une piece à partir de sa couleur
	 * @param color : Couleur de la piece (char)
	 * @return Indice de la piece
	 */
	public static int getPieceIndice(char color) {
		assert(color == 'R' || color == 'B' || color == 'W');
		if (color == 'R')
			return 0;
		else if (color == 'B')
			return 1;
		else
			return 2;
	}
}
