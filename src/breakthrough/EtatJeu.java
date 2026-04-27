package breakthrough;/*
EtatJeu.java
Classe décrivant une configuration du jeu (le joueur dont c'est le tour ainsi que l'état du plateau de jeu).
Auteur: Rémi PALLEN.
Date de la dernière version: 03/02/2026.
 */

import java.util.ArrayList;

public class EtatJeu {
    static private int largeur; /*Désigne le nombre de colonne du plateau de jeu*/
    private Plateau plateau;
    private boolean tourBlanc; /*=true si c'est le tour du Joueur blanc, false sinon.*/

    public int getLargeur() {return this.largeur;};
    public boolean getTourBlanc() {return this.tourBlanc;};
    public Plateau getPlateau() {return this.plateau;};

    public EtatJeu (int largeur) {
		this.largeur=largeur;
			this.plateau=new Plateau(largeur);
		this.tourBlanc=true; /*Le joueur Blanc commence.*/
    }

	public EtatJeu (EtatJeu e) {
		this.plateau = new Plateau(e.plateau);
		this.tourBlanc = e.tourBlanc;
	}

    public void modifie(int x,int y, Joueur j) { /*Remplie la case de la ligne x et de la colonne y par le pion du joueur j. Si j=null, alors vide la case.*/
	getPlateau().modifie(x,y,j);
    }

    public Gagnant etatFinal() { /*Renvoit AUCUN si l'état n'est pas final, le gagnant sinon.*/
		for (int k=0;k<getLargeur();k++) {
			if (getPlateau().getCase(0,k)== Joueur.joueurNoir) {
			return Gagnant.NOIR;
			}
		}
		for (int k=0;k<largeur;k++) {
			if (getPlateau().getCase(7,k)== Joueur.joueurBlanc) {
			return Gagnant.BLANC;
			}
		}
		return Gagnant.AUCUN;
    }
    /*---------------------------------------------Ne programmez qu'en dessous de cette ligne là! (Sauf pour l'importation de modules)------------------------------------------------------------------*/

	/**
	 * Calcul le successeur pour un deplacement donne
	 * x, y sont les coordonnées du pion
	 * d est la direction (face:0 / diagonale droite:1/ diagonale gauche:-1)
	 */
	public EtatJeu calculSuccesseur(int x, int y, int d) {
		Joueur courant = tourBlanc ? Joueur.joueurBlanc : Joueur.joueurNoir;
		int directionRange = tourBlanc ? 1 : -1; //Blanc monte et Noir descend

		// Donnee en relation avec le plateau
		int cibleX = x + directionRange;
		int cibleY = y + d;

		// Limite du plateau verifier
		if (cibleX < 0 || cibleX >= 8 || cibleY < 0 || cibleY >= largeur) return null;

		Joueur cible = plateau.getCase(cibleX, cibleY);
		if (d == 0){
			if (cible != null) return null;
		}else{
			if (cible == courant) return null;
		}

		// Creation d'un nouvel etat
		EtatJeu succ = new EtatJeu(this);
		succ.modifie(x, y, null); // Vide l'ancienne case
		succ.modifie(cibleX, cibleY, courant);
		succ.tourBlanc = !succ.tourBlanc; // Change de tour

		return succ;
	}

	/**
	 * Methode permettant de générer tous les coups possible
	 * @return
	 */
	public ArrayList<EtatJeu> successeurs() {
		ArrayList<EtatJeu> successeurs = new ArrayList<>();
		Joueur courant = tourBlanc ? Joueur.joueurBlanc : Joueur.joueurNoir;

		for (int l = 0; l < 8; l++) {
			for (int c = 0; c < largeur; c++) {
				if (plateau.getCase(l, c) == courant) {
					// Tester les 3 directions: -1 / 0 / 1
					for (int d = -1; d <= 1; d++){
						EtatJeu succ = calculSuccesseur(l, c, d);
						if (succ != null) successeurs.add(succ);
					}
				}
			}
		}
		return successeurs;
	}
}
