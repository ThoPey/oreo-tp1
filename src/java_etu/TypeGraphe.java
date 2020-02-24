/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_etu;

/**
 *
 * @author Eric-PC
 */
public enum TypeGraphe {
    CHAINE, CYCLE, RANDOM, GRILLE, TORE, ANC;

    @Override
    public String toString() {
        switch (this) {
            case CHAINE:
                return "chaine";
            case CYCLE:
                return "cycle";
            case RANDOM:
                return "graphe aléatoire";
            case GRILLE:
                return "grille carrée";
            case TORE:
                return "tore";
            case ANC:
                return "arbre n-aire complet";
        }
        return "";
    }
    
    public String[] getParam() {
        String argc[] = null;
        switch (this) {
            case CHAINE:
                argc = new String[1];
                argc[0] = "Longueur";
                break;
            case CYCLE:
                argc = new String[1];
                argc[0] = "Longueur";
                break;
            case RANDOM:
                argc = new String[2];
                argc[0] = "Taille";
                argc[1] = "Degré moyen";
                break;
            case GRILLE:
                argc = new String[1];
                argc[0] = "Longueur";
                break;
            case TORE:
                argc = new String[1];
                argc[0] = "Longueur";
                break;
            case ANC:
                argc = new String[2];
                argc[0] = "Nombre de fils";
                argc[1] = "Hauteur";
                break;
        }
        return argc;
    }
    
}

