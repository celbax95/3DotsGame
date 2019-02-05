package fr.dots3;

import java.util.Scanner;

/**
 * @author Loïc Macé
 * @brief Un joueur
 */
public class Player {
	private char color;
	private int score;
	
	public Player(int ind) {
		assert((ind>=0 && ind < Game.MAX_PLAYERS));
		this.score = 0;
		if (ind == 0)
			this.score = 0;
		if (ind == 1)
			this.score = 0;
	}
	
	/**
	 * @brief Met à jour le score du joueur
	 * @param s : Score à ajouter
	 * @see Player#getScore()
	 */
	public void addScore(int s) {
		assert(s>=0);
		this.score += s;
	}
	
	/**
	 * @brief Choix de la couleur du premier joueur
	 * @see Player#defaultColor(char)
	 */
	public void chooseColor() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Joueur1 choisissez une couleur (R/B)\n\t"
				+ "(Le 2nd aura la couleur restante) : ");
		color = Character.toUpperCase(sc.nextLine().charAt(0));
		while (color != 'R' && color != 'B') {
			System.out.println("Couleur saisie invalide, réessayez : ");
			color = Character.toUpperCase(sc.nextLine().charAt(0));
		}
		this.setColor(color);
	}
	
	/**
	 * @brief Choix auto de la couleur du deuxieme joueur
	 * @param c1 : Couleur du premier joueur
	 * @see Player#chooseColor()
	 */
	public void defaultColor(char c1) {
		assert(c1 == 'R' || c1 == 'B');
		if (c1 == 'R')
			this.setColor('B');
		else
			this.setColor('R');
	}
	
	/**
	 * @brief Accesseur : color
	 * @return Couleur de la piece du joueur
	 * @see Player#setColor(int)
	 */
	public char getColor() {
		return this.color;
	}
	
	/**
	 * @brief Accesseur : score
	 * @return Score du joueur
	 * @see Player#addScore(int)
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * @brief Accesseur : score
	 * @return Score du joueur (String formatée)
	 */
	public String getScore2() {
		String s = "";
		if (this.score < 10)
			s += " ";
		return s + this.score;
	}
	
	/**
	 * @brief Setter : Change la valeur de color
	 * @param color : Couleur à appliquer au joueur (char)
	 */
	private void setColor(char color) {
		Character.toUpperCase(color);
		assert(color == 'R' || color == 'B');
		this.color = color;
	}
	
	/**
	 * @brief Setter : Change la valeur de color
	 * @param ind : Indice de la couleur, 0 = Rouge | 1 = Bleu
	 * @see Player#getColor()
	 */
	public void setColor(int ind) {
		assert((ind>=0 && ind < Game.MAX_PLAYERS));
		switch(ind) {
		case(0):
			this.color = 'R';
			break;
		case(1):
			this.color = 'B';
			break;
		}
	}
	
}
