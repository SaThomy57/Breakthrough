package breakthrough;/*
EtatJeu.java
Classe décrivant une configuration du jeu (le joueur dont c'est le tour ainsi que l'état du plateau de jeu).
Auteur: Rémi PALLEN.
Date de la dernière version: 03/02/2026.
 */

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
}
