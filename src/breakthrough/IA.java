package breakthrough;

import java.util.ArrayList;
import java.util.Random;


public class IA {
    private int profondeurMax;
    private Random random = new Random();

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
                double score =alphaBeta(e, profondeurMax - 1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false);
                min = Math.min(min, score);
            }
            return min;
        }
    }

    /**
     * Algorithme AlphaBeta - logique d'elagage
     * Permet une exploration plus profonde en séparant les branches
     */
    private double alphaBeta(EtatJeu etat, int profondeur, double alpha, double beta, boolean estMax) {
        // Condition d'arret
        Gagnant g = etat.etatFinal();
        if (g != Gagnant.AUCUN) {
            if (g == (etat.getTourBlanc() ? Gagnant.NOIR : Gagnant.BLANC)) return -1000;
            return 1000;
        }

        if (profondeur == 0) return evaluationMonteCarlo(etat);

        ArrayList<EtatJeu> succs = etat.successeurs();
        if (estMax) {
            double v = Double.NEGATIVE_INFINITY;
            for (EtatJeu s : succs) {
                v = Math.max(v, alphaBeta(s, profondeur - 1, alpha, beta, false));
                alpha = Math.max(alpha, v);
                if (beta <= alpha) break;
            }
            return v;
        } else {
            double v = Double.POSITIVE_INFINITY;
            for (EtatJeu s : succs) {
                v = Math.min(v, alphaBeta(s, profondeur - 1, alpha, beta, true));
                beta = Math.min(beta, v);
                if (beta <= alpha) break;
            }
            return v;
        }
    }

    private double evaluationMonteCarlo(EtatJeu etatInitial) {
        int victoires = 0;
        int nbSimu = 50; // Baisse à 50 pour que ça soit plus rapide au début

        for (int i = 0; i < nbSimu; i++) {
            EtatJeu simulation = new EtatJeu(etatInitial);
            while (simulation.etatFinal() == Gagnant.AUCUN) {
                ArrayList<EtatJeu> possibles = simulation.successeurs();
                if (possibles.isEmpty()) break;
                simulation = possibles.get(random.nextInt(possibles.size()));
            }
            if (simulation.etatFinal() != Gagnant.AUCUN) victoires++;
        }
        return (double) victoires / nbSimu;
    }
}
