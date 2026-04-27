import java.util.Scanner;
import breakthrough.*;

public class Breakthrough {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialisation du jeu (largeur 8 par défaut)
        int largeur = 8;
        EtatJeu jeu = new EtatJeu(largeur);

        System.out.println("=== BREAKTHROUGH ===");
        // CHoix du mode de jeu
        System.out.println("Choisissez le mode de jeu :");
        System.out.println("1. Humain VS Humain");
        System.out.println("2. Humain VS IA");
        int mode = sc.nextInt();

        IA intelligence = null;
        if (mode == 2) {
            System.out.println("Entrez la profondeur maximale de l'IA :");
            int profondeurMax = sc.nextInt();
            intelligence = new IA(profondeurMax);
        }

        // boucle de jeu
        while (jeu.etatFinal() == Gagnant.AUCUN) {
            jeu.getPlateau().afficher();

            // Pions blancs (Toujours Humain dans ce code)
            if (jeu.getTourBlanc()) {
                System.out.println("\nTour des BLANCS (Humain)");
                jeu = jouerCoupHumain(jeu, sc); // Appel de al methode general

            } else {
                // Pions noir (Humain ou IA)
                if (mode == 1) {
                    System.out.println("\nTour des NOIRS (Humain)");
                    jeu = jouerCoupHumain(jeu, sc);
                } else {
                    System.out.println("\nL'IA réfléchit...");
                    long debut = System.currentTimeMillis();

                    jeu = intelligence.choisirMeilleurCoup(jeu);

                    long fin = System.currentTimeMillis();
                    System.out.println("IA a joué en " + (fin - debut) + " ms.");
                }
            }
        }

        // fin du jeu
        jeu.getPlateau().afficher();
        Gagnant gagnant = jeu.etatFinal();
        System.out.println("\n=== PARTIE TERMINÉE ===");
        if (gagnant == Gagnant.BLANC) System.out.println("VICTOIRE DES BLANCS !");
        else System.out.println("VICTOIRE DES NOIRS !");

        sc.close();
    }

    /**
     * Methode pour eviter la repetition de saisi
     */
    private static EtatJeu jouerCoupHumain(EtatJeu jeu, Scanner sc) {
        EtatJeu suivant = null;
        while (suivant == null) {
            System.out.println("Entrez votre coup (ligne colonne direction) :");
            try{
                int l = sc.nextInt();
                int c = sc.nextInt();
                int d = sc.nextInt();

                Joueur pion = jeu.getPlateau().getCase(l, c);
                Joueur courant = jeu.getTourBlanc() ? Joueur.joueurBlanc : Joueur.joueurNoir;

                if (pion != courant) {
                    System.out.println("Erreur : Cette case ne contient pas un de vos pions !");
                    continue;
                }

                suivant = jeu.calculSuccesseur(l, c, d);
                if (suivant == null) {
                    System.out.println("Coup illégal !");
                }

            }catch(Exception e){
                System.out.println("Erreur de saisie !");
                sc.nextLine();
            }
        }
        return suivant;
    }
}