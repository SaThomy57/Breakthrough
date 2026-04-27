package breakthrough;

import java.util.ArrayList;

public class IA {
    private int profondeurMax;

    // COnstructeur
    public IA(int profondeurMax) {
        this.profondeurMax = profondeurMax;
    }

    /**
     * Methode principale pour avoir le meilleur coup
     * Renvoie EtatJeu du meilleur mouvement trouve
     */
    public EtatJeu choisirMeilleurCoup(EtatJeu etat){
        ArrayList<EtatJeu> successeurs = etat.successeurs();
        double max = Double.NEGATIVE_INFINITY;
        EtatJeu meilleurCoup = null;

        for (EtatJeu e : successeurs) {
            // On appelle minimax pour tous les successeurs
            // on COmmence sur false car l'adversaire est min
            double score = minimax(e, profondeurMax-1, false);
            if (score > max) {
                max = score;
                meilleurCoup = e;
            }
        }

        return meilleurCoup;
    }

    /**
     * Algorithme Minimax en recursif
     * @param etat L'etat a evoluer
     * @param profondeur la profondeut a explorer
     * @param estMax Vrai si c'est le tour de l'IA, Faux si c'est le tour de l'adversaire
     */
    private double minimax(EtatJeu etat, int profondeur, boolean estMax){
        // Condition d'arret
        if (profondeur == 0 || etat.etatFinal() != Gagnant.AUCUN) {
            return etat.evolutionNaive();
        }

        ArrayList<EtatJeu> successeurs = etat.successeurs();

        if (estMax){
            double max = Double.NEGATIVE_INFINITY;
            for (EtatJeu e : successeurs) {
                double score = minimax(e, profondeur-1, false);
                max = Math.max(max, score);
            }
            return max;
        }else{
            double min = Double.POSITIVE_INFINITY;
            for (EtatJeu e : successeurs) {
                double score = minimax(e, profondeur-1, true);
                min = Math.min(min, score);
            }
            return min;
        }
    }
}
