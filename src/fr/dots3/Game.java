package fr.dots3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Loïc Macé
 * @brief Gestion de la partie de 3Dots
 * @see Board
 * @see Player
 */
public class Game {
	public static final int MAX_PLAYERS = 2;
	private static final int SCORE_MID = 6;

	private static final int SCORE_END = 12;

	private static final int FORMATCOORD_X = 0;
	private static final int FORMATCOORD_Y = 2;

	// Variables de boucles
	private boolean play;
	private boolean replay;

	// Liste de stockage des scores à chaque partie
	private ArrayList<int[]> totalScore;

	Board b;
	Player[] p;

	/**
	 * @brief Constructeur du type Game
	 * Création du plateau et des deux joueurs
	 * @see Game#manage()
	 */
	public Game() {
		//Variables de boucles
		this.play = true;
		this.replay = true;
		// Creation du plateau
		this.b = new Board();
		// Creation des deux joueurs
		p = new Player[Game.MAX_PLAYERS];
		for (int i=0; i<Game.MAX_PLAYERS; i++) {
			p[i] = new Player(i);
		}

		this.totalScore = new ArrayList<int[]>();
		// Lancement du jeu
		this.manage();
	}

	/**
	 * @brief Ajout du score de la partie dans la liste des scores totaux
	 */
	private void addTotalScore() {
		int[] score = new int[Game.MAX_PLAYERS];

		for (int i = 0; i < Game.MAX_PLAYERS; i++) {
			score[i] = p[i].getScore();
		}
		this.totalScore.add(score);
	}

	/**
	 * @breif Gestion de la fin de la partie (Rejouer/Quitter)
	 * @see Game#reset()
	 */
	private void askReplay() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Voulez-vous rejouer ? (oui/non) : ");
		String s = sc.nextLine();
		char c = 0;
		if (s.length() > 0)
			c = s.toLowerCase().charAt(0);
		while (c != 'o' && c != 'n') {
			System.out.println("Reponse invalide, réessayez : ");
			s = sc.nextLine();
			if (s.length() > 0)
				c = s.toLowerCase().charAt(0);
		}
		if (c == 'n') {
			System.out.println("\nAu revoir !");
			this.replay = false;
		}
		else {
			System.out.println("");
			this.reset();
		}
	}

	/**
	 * @brief Affichage du plateau puis pause
	 */
	private void displayBoard() {
		System.out.println(this.b);
		Game.pause();
	}

	/**
	 * @brief Affichage du tableau de score de la partie
	 */
	private void displayScore() {
		System.out.println("/----------------------------------\\\n"
				+ "|            | Joueur 1 | Joueur 2 |\n"
				+ "|------------|----------|----------|\n"
				+ "|   Scores   |    "+p[0].getScore2()+"    |    "+p[1].getScore2()+"    |\n"
				+ "\\----------------------------------/");

		Game.pause();
	}

	/**
	 * @brief Affichage du tableau des scores totaux puis pause
	 */
	private void displayTotalScore() {
		String s = "  /------------------------------\\\n"
				+ " /         Scores  Totaux         \\\n"
				+ "/----------------------------------\\\n"
				+ "|   Scores   | Joueur 1 | Joueur 2 |\n";
		// Scores par parties
		for (int[] ts : this.totalScore) {
			s += "|------------|----------|----------|\n";
			s += "|            |";
			for (int i = 0; i < ts.length; i++) {
				s += "    " + Game.formatTotalScore(ts[i]) + "    |";
			}
			s += "\n";
		}
		// Nombre de parties gagnées
		s += "|------------|----------|----------|\n";
		s += "|    Win(s)  |" + this.sumVictories(0) + "|" + this.sumVictories(1) + "|\n";
		s += "\\----------------------------------/\n"
				+ " \\--------------------------------/\n";
		System.out.println(s);
		Game.pause();
	}

	/**
	 * @brief Gestion des évenements d'une partie (Affichage, Saisie, etc)
	 */
	private void manage() {
		while (this.replay) {
			// Choix des pieces
			this.p[0].chooseColor();
			this.p[1].defaultColor(p[0].getColor());
			System.out.println("");

			// Boucle de jeu
			while (this.play) {
				// Boucle 2 joueurs
				for (int i = 0; i < Game.MAX_PLAYERS; i++) {
					this.displayScore();
					System.out.println("Au tour du joueur "+(i+1)+" :\t\t\t\t----");
					this.displayBoard();
					// Déplacement de la piece du joueur
					this.play(this.p[i].getColor());
					this.displayBoard();
					System.out.println("Il vous faut maintenant bouger la piece blanche :");
					// Déplacement de la piece blanche
					this.play('W');
					// Mise à jour du score
					this.p[i].addScore(this.b.getScore(Board.getPieceIndice(this.p[i].getColor())));
				}
				// Fin de partie
				for (int i = 0; i < Game.MAX_PLAYERS; i++) {
					if (p[i].getScore() >= Game.SCORE_END) {
						this.addTotalScore();
						System.out.println(" --> Partie terminée !\n");
						if (p[Game.MAX_PLAYERS-1-i].getScore() < Game.SCORE_MID) {
							System.out.println("Victoire du joueur " + (Game.MAX_PLAYERS-i) + ", Bravo !\n");
						}
						else {
							System.out.println("Victoire du joueur " + (i+1) + ", Bravo !\n");
						}
						this.displayTotalScore();
						this.play = false;
					}
				}
			}
			this.askReplay();
		}
	}

	/**
	 * @brief Choix du mouvement choisi
	 * @param ind : Indice de la piece à deplacer
	 * @return false si valeur saisie invalide
	 * @see Numero de mouvement, {@link Board#move(int, int)}
	 * @see Coodronées : {@link Board#move(int, int, int)}
	 */
	private boolean move(int ind) {
		assert(ind == 0 || ind == 1 || ind == 2);
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		// Coordonées
		if (s.length() == 3) {
			int x = Character.getNumericValue(s.charAt(Game.FORMATCOORD_X));
			int y = Character.getNumericValue(s.charAt(Game.FORMATCOORD_Y));
			if ((x<0 || x>Board.SIZE-1) || (y<0 || y>Board.SIZE-1)) {
				return false;
			}
			else {
				System.out.println("");
				this.b.move(ind,x,y);
			}
		}
		// Numero
		else if (s.length() == 1){
			int c = Character.getNumericValue(s.charAt(0));
			if (c<=0 || c>Square.getNbOfMoves())
				return false;
			else {
				System.out.println("");
				this.b.move(ind,c);
			}
		}
		else
			return false;
		return true;
	}

	/**
	 * @brief Affichage des mouvements possibles
	 * @param color
	 * @see Board#setMoves(int)
	 * @see Board#toString(int)
	 * @see Game#move(int)
	 */
	private void play(char color) {
		assert(color == 'R' || color == 'B' || color == 'W');
		int ind = Board.getPieceIndice(color);
		// Initialisation des mouvements
		this.b.setMoves(ind);
		System.out.println(this.b.toString(ind));
		System.out.println("Choisissez un deplacement ou saisissez des coordonées au format 'x y' : ");
		while (!this.move(ind))
			System.out.println("Format ou donnée invalide, Réessayez : ");
	}

	/**
	 * @brief Reinitialisation des attributs relatif à une partie
	 */
	private void reset() {
		this.play = true;
		this.replay = true;
		this.b = new Board();
		p = new Player[Game.MAX_PLAYERS];
		for (int i=0; i<Game.MAX_PLAYERS; i++) {
			p[i] = new Player(i);
		}
	}

	/**
	 * @brief Calcul du nombre de victoire du joueur
	 * @param ind : Indice du joueur
	 * @return Somme des victoires du joueur (String formatée)
	 */
	private String sumVictories(int ind) {
		assert(ind >= 0);
		int sum = 0;

		// Calcul nombre de victoire
		for (int[] is : this.totalScore) {
			if ((is[ind] < Game.SCORE_MID
					&& is[Game.MAX_PLAYERS-1-ind] >= Game.SCORE_END)
					|| (is[ind] >= Game.SCORE_END
					&& is[Game.MAX_PLAYERS-1-ind] >= Game.SCORE_MID))
				sum++;
		}
		// Formatage du nombre de victoire
		String strSum = "";
		if (sum < 10)
			strSum = "     " + sum + "    ";
		else if (sum < 100)
			strSum = "    " + sum + "    ";
		else if (sum < 1000)
			strSum = "    " + sum + "   ";
		return strSum;
	}

	/**
	 * @brief Formatage pour le tableau de scores totaux
	 * @param sc : Score (int)
	 * @return Score (String formatée)
	 * @see Game#displayTotalScore()
	 */
	private static String formatTotalScore(int sc) {
		assert(sc>=0);
		String s = "";
		if (sc < 10)
			s += " ";
		return s + sc;
	}

	/**
	 * @brief Demande une entrée clavier pour continuer le déroulement du
	 * programme
	 */
	private static void pause() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Appuyez sur 'entrer' pour continuer ...\n");
		sc.nextLine();
	}

}