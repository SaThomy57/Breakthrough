import java.util.Scanner;
import breakthrough.*;

public class Breakthrough {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialisation du jeu (largeur 8 par défaut)
        int largeur = 8;
        EtatJeu jeu = new EtatJeu(largeur);

        System.out.println("=== BREAKTHROUGH ===");

        // BOUCLE DE JEU
        while (jeu.etatFinal() == Gagnant.AUCUN) {
            jeu.getPlateau().afficher(); // Utilise la version améliorée

            String couleur = jeu.getTourBlanc() ? "BLANCS (B)" : "NOIRS (N)";
            System.out.println("\nC'est au tour des " + couleur);
            System.out.println("Entrez votre coup (ligne colonne direction) :");
            System.out.println("Direction : -1 (diag gauche), 0 (tout droit), 1 (diag droite)");

            try {
                int l = sc.nextInt();
                int c = sc.nextInt();
                int d = sc.nextInt();

                // On vérifie si le pion appartient bien au joueur
                Joueur pion = jeu.getPlateau().getCase(l, c);
                Joueur courant = jeu.getTourBlanc() ? Joueur.joueurBlanc : Joueur.joueurNoir;

                if (pion != courant) {
                    System.out.println("Erreur : Cette case ne contient pas un de vos pions !");
                    continue;
                }

                // Calcul du nouvel état
                EtatJeu suivant = jeu.calculSuccesseur(l, c, d);

                if (suivant == null) {
                    System.out.println("Coup invalide selon les règles du Breakthrough !");
                } else {
                    jeu = suivant; // Le coup est validé, on passe à l'état suivant
                }

            } catch (Exception e) {
                System.out.println("Entrée invalide. Veuillez saisir trois chiffres.");
                sc.nextLine(); // Nettoyer le scanner
            }
        }

        // FIN DE PARTIE
        jeu.getPlateau().afficher();
        Gagnant gagnant = jeu.etatFinal();
        System.out.println("\n=== PARTIE TERMINÉE ===");
        if (gagnant == Gagnant.BLANC) System.out.println("VICTOIRE DES BLANCS !");
        else System.out.println("VICTOIRE DES NOIRS !");

        sc.close();
    }
}