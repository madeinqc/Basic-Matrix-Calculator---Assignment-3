import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Cette classe represente une matrice de format n par m.
 * Elle contient également quelques méthodes afin de manipuler les matrices.
 *
 * @author Nicolas Lamoureux
 *         Code permanent : LAMN19109003
 *         Courriel : lamoureux.nicolas.2@courrier.uqam.ca
 *         Cours : INF2120-10
 *
 * @author Marc-Antoine Sauvé
 *         Code permanent : SAUM13119008
 *         Courriel : marc-antoine.sauve.2@courrier.uqam.ca
 *         Cours : INF2120-10
 *
 * @version 2015-04-09
 */
public class Matrice implements IMatrice {

    private ArrayList<Double> elements;
    private int numLignes;
    private int numColonnes;

    /**
     * Retourne le nombre de lignes de cette matrice.
     *
     * @return le nombre de lignes de cette matrice.
     */
    @Override
    public int getNumLignes() {
        return numLignes;
    }

    /**
     * Retourne le nombre de colonnes de cette matrice.
     *
     * @return le nombre de colonnes de cette matrice.
     */
    @Override
    public int getNumColonnes() {
        return numColonnes;
    }

    /**
     * Construit une matrice numLignes x numColonnes, dont toutes les cases
     * contiennent la valeur donnee.
     *
     * @param numLignes   le nombre de lignes de cette matrice.
     * @param numColonnes le nombre de colonnes de cette matrice.
     * @param valeur      la valeur d'initialisation de toutes les cases de cette
     *                    matrice.
     * @throws MatriceException si :
     *                          - numLignes est plus petit que 1
     *                          - numCol est plus petit que 1
     */
    public Matrice(int numLignes, int numColonnes, double valeur) {
        if (numColonnes < 1 || numLignes < 1)
            throw new MatriceException();

        this.numLignes = numLignes;
        this.numColonnes = numColonnes;

        elements = new ArrayList<>(numColonnes * numLignes);

        for (int i = numColonnes * numLignes - 1; i >= 0; i--) {
            elements.add(0, valeur);
        }
    }

    /**
     * Construit une matrice numLignes X numColonnes, dont les cases sont
     * initialisees avec les elements passes en parametre. Le parametre elements
     * est une liste de nombres contenant les lignes de la matrice, une a la
     * suite de l'autre.
     * Ex: numLignes = 4, numCol = 3, elems : 1 2 3 4 5 6 7 8 9 10 11 12
     * correspond a la matrice :
     * [ 1 2 3 ]
     * [ 4 5 6 ]
     * [ 7 8 9 ]
     * [ 10 11 12]
     *
     * @param numLignes   le nombre de lignes de cette matrice.
     * @param numColonnes le nombre de colonnes de cette matrice
     * @param elements    la liste des valeurs d'initialisation de toutes les cases
     *                    de cette matrice.
     * @throws MatriceException si :
     *                          - numLignes est plus petit que 1
     *                          - numColonnes est plus petit que 1
     *                          - elements est null ou ne contient pas exactement
     *                          (numLignes * numColonnes) elements.
     */
    public Matrice(int numLignes, int numColonnes, double[] elements) {
        if (numColonnes < 1 || numLignes < 1 ||
                elements == null || elements.length != numColonnes * numLignes)
            throw new MatriceException();

        this.numLignes = numLignes;
        this.numColonnes = numColonnes;

        this.elements = new ArrayList<>(numColonnes * numLignes);

        for (int i = numColonnes * numLignes - 1; i >= 0; i--) {
            this.elements.add(0, elements[i]);
        }
    }

    /**
     * Constructeur de copie. Construit une copie de surface de autreMatrice.
     *
     * @param autreMatrice la matrice a copier.
     */
    public Matrice(Matrice autreMatrice) {
        numColonnes = autreMatrice.numColonnes;
        numLignes = autreMatrice.numLignes;
        elements = (ArrayList<Double>) autreMatrice.elements.clone();
    }

    /**
     * Constructeur de copie. Construit une copie de surface de autreMatrice.
     *
     * @param autreMatrice la matrice a copier.
     */
    public Matrice(IMatrice autreMatrice) {
        numColonnes = autreMatrice.getNumColonnes();
        numLignes = autreMatrice.getNumLignes();
        elements = new ArrayList<>();

        for (int i = 0; i < numLignes; i++) {
            for (int j = 0; j < numColonnes; j++) {
                elements.add(autreMatrice.getElement(i, j));
            }
        }
    }

    /**
     * Retourne l'index de l'ArrayList correspondant à la ligne et à colonne fournies.
     *
     * @param noLigne le numero de la ligne dans cette matrice.
     * @param noCol   le numero de la colonne dans cette matrice.
     * @return l'index de l'ArrayList correspondant à la ligne et à colonne fournies.
     * @throws MatriceException si :
     *                          noLigne ou noCol n'est pas un indice valide dans cette matrice.
     */
    private int getRealIndex(int noLigne, int noCol) throws MatriceException {
        if (noCol < 0 || noLigne < 0 || noCol >= numColonnes || noLigne >= numLignes)
            throw new MatriceException();

        return noLigne * numColonnes + noCol;
    }

    /**
     * Retourne l'element de la matrice a la position noLigne, noCol.
     *
     * @param noLigne le numero de la ligne dans cette matrice.
     * @param noCol   le numero de la colonne dans cette matrice.
     * @return l'element de la matrice a la position noLigne, noCol.
     * @throws MatriceException si :
     *                          noLigne ou noCol n'est pas un indice valide dans cette matrice.
     */
    @Override
    public double getElement(int noLigne, int noCol) {
        return elements.get(getRealIndex(noLigne, noCol));
    }

    /**
     * Modifie l'element a la position noLigne, noCol par l'element passe en
     * parametre.
     *
     * @param noLigne le numero de la ligne dans cette matrice.
     * @param noCol   le numero de la colonne dans cette matrice.
     * @param element le nouvel element a inserer a la position noLigne, noCol
     *                dans cette matrice.
     * @throws MatriceException si noLigne ou noCol n'est pas un indice valide
     *                          dans cette matrice.
     */
    @Override
    public void setElement(int noLigne, int noCol, double element) {
        elements.set(getRealIndex(noLigne, noCol), element);
    }

    /**
     * Retourne la ligne de cette matrice specifiee par noLigne.
     *
     * @param noLigne le numero de la ligne a retourner.
     * @return la ligne de cette matrice specifiee par noLigne.
     * @throws MatriceException si noLigne n'est pas une ligne valide
     *                          dans cette matrice.
     */
    @Override
    public double[] getLigne(int noLigne) {
        double[] ligne = new double[numColonnes];
        for (int i = 0; i < numColonnes; i++) {
            ligne[i] = elements.get(getRealIndex(noLigne, i));
        }
        return ligne;
    }

    /**
     * Retourne la colonne de cette matrice specifiee par noCol.
     *
     * @param noCol le numero de la colonne a retourner.
     * @return la colonne de cette matrice specifiee par noCol.
     * @throws MatriceException si noCol n'est pas une colonne valide
     *                          dans cette matrice.
     */
    @Override
    public double[] getColonne(int noCol) {
        double[] colonne = new double[numLignes];
        for (int i = 0; i < numLignes; i++) {
            colonne[i] = elements.get(getRealIndex(i, noCol));
        }
        return colonne;
    }

    /**
     * Remplace la ligne specifiee par noLigne de cette matrice par la ligne
     * donnee en parametre.
     *
     * @param noLigne le numero de la ligne a remplacer.
     * @param ligne   la nouvelle ligne
     * @throws Matrice Exception si :
     *                 - noLigne n'est pas une ligne valide dans cette matrice.
     *                 - ligne est null ou ne contient pas exactement getNumColonnes()
     *                 valeurs.
     */
    @Override
    public void remplacerLigne(int noLigne, double[] ligne) {
        if (ligne == null || ligne.length != numColonnes)
            throw new MatriceException();

        for (int i = 0; i < numColonnes; i++) {
            elements.set(getRealIndex(noLigne, i), ligne[i]);
        }
    }

    /**
     * Remplace la colonne specifiee par noCol de cette matrice par la colonne
     * donnee en parametre.
     *
     * @param noCol   le numero de la colonne a remplacer.
     * @param colonne la nouvelle colonne
     * @throws MatriceException si :
     *                          - noCol n'est pas une colonne valide dans cette matrice.
     *                          - colonne est null ou ne contient pas exactement getNumLignes()
     *                          valeurs.
     */
    @Override
    public void remplacerColonne(int noCol, double[] colonne) {
        if (colonne == null || colonne.length != numLignes)
            throw new MatriceException();

        for (int i = 0; i < numLignes; i++) {
            elements.set(getRealIndex(i, noCol), colonne[i]);
        }
    }

    /**
     * Ajoute a cette matrice la ligne donnee au noLigne donne.
     * Apres cet ajout, la matrice contient une ligne de plus et la nouvelle
     * ligne se trouve a l'indice noLigne (le numero des lignes suivantes aura
     * augmente de 1).
     *
     * @param noLigne l'indice de la nouvelle ligne apres l'ajout.
     * @param ligne   la ligne a ajouter dans cette matrice, a l'indice noLigne.
     * @throws MatriceException si :
     *                          - noLigne n'est pas entre 0 et getNumLignes() inclusivement
     *                          - ligne est null ou ne contient pas exactement getNumColonnes() valeurs.
     */
    @Override
    public void ajouterLigne(int noLigne, double[] ligne) {
        if (ligne == null || ligne.length != numColonnes)
            throw new MatriceException();

        numLignes++;
        for (int i = 0; i < numColonnes; i++) {
            elements.add(getRealIndex(noLigne, i), ligne[i]);
        }
    }

    /**
     * Ajoute a cette matrice la colonne donnee au noCol donne.
     * Apres cet ajout, la matrice contient une colonne de plus et la nouvelle
     * colonne se trouve a l'indice noCol (le numero des colonnes suivantes aura
     * augmente de 1).
     *
     * @param noCol   l'indice de la nouvelle colonne apres l'ajout.
     * @param colonne la colonne a ajouter.
     * @throws MatriceException si :
     *                          - noCol n'est pas entre 0 et getNumColonnes() inclusivement.
     *                          - colonne est null ou ne contient pas exactement getNumLignes() valeurs.
     */
    @Override
    public void ajouterColonne(int noCol, double[] colonne) {
        if (colonne == null || colonne.length != numLignes)
            throw new MatriceException();

        numColonnes++;
        for (int i = 0; i < numLignes; i++) {
            elements.add(getRealIndex(i, noCol), colonne[i]);
        }
    }

    /**
     * Supprime la ligne de cette matrice correspondant au noLigne donne.
     * Apres l'appel de cette methode, cette matrice contient une ligne de moins.
     * Note : on ne peut pas supprimer une ligne dans une matrice qui ne contient
     * qu'une seule ligne.
     *
     * @param noLigne le numero de la ligne a supprimer.
     * @return la ligne supprimee.
     * @throws MatriceException si :
     *                          - noLigne n'est pas une ligne valide dans cette matrice ou
     *                          - si cette matrice ne contient qu'une seule ligne avant la
     *                          suppression.
     */
    @Override
    public double[] supprimerLigne(int noLigne) {
        if (numLignes == 1)
            throw new MatriceException();

        double[] ligne = new double[numColonnes];
        for (int i = numColonnes - 1; i >= 0; i--) {
            ligne[i] = elements.remove(getRealIndex(noLigne, i));
        }

        numLignes--;
        return ligne;
    }

    /**
     * Supprime la colonne de cette matrice correspondant au noCol donne.
     * Apres l'appel de cette methode, cette matrice contient une colonne de moins.
     * Note : on ne peut pas supprimer une colonne dans une matrice qui ne contient
     * qu'une seule colonne.
     *
     * @param noCol le numero de la colonne a supprimer.
     * @return la colonne supprimee.
     * @throws MatriceException si :
     *                          - noCol n'est pas une colonne valide dans cette matrice
     *                          - si cette matrice ne contient qu'une seule colonne avant la
     *                          suppression.
     */
    @Override
    public double[] supprimerColonne(int noCol) {
        if (numColonnes == 1)
            throw new MatriceException();

        double[] colonne = new double[numLignes];
        for (int i = numLignes - 1; i >= 0; i--) {
            colonne[i] = elements.remove(getRealIndex(i, noCol));
        }

        numColonnes--;
        return colonne;
    }

    /**
     * Effectue la somme de cette Matrice avec autreMatrice donnee en parametre.
     *
     * @param autreMatrice la matrice a additionner avec cette matrice.
     * @return la matrice resultante de la somme de cette matrice avec
     * autreMatrice.
     * @throws MatriceException si autreMatrice est null ou n'est pas de meme
     *                          dimension que cette matrice(meme nombre de lignes et meme nombre de
     *                          colonnes).
     */
    @Override
    public IMatrice somme(IMatrice autreMatrice) {
        if (autreMatrice == null || numColonnes != autreMatrice.getNumColonnes() || numLignes != autreMatrice.getNumLignes())
            throw new MatriceException();

        IMatrice nouvelleMatrice = new Matrice(numLignes, numColonnes, 0);

        for (int i = 0; i < numLignes; i++) {
            for (int j = 0; j < numColonnes; j++) {
                nouvelleMatrice.setElement(i, j, getElement(i, j) + autreMatrice.getElement(i, j));
            }
        }

        return nouvelleMatrice;
    }

    /**
     * Calcul le produit de cette matrice par la valeur donnee.
     *
     * @param valeur la valeur de multiplication de cette matrice.
     * @return la matrice resultante du produit de cette matrice par la
     * valeur donnee.
     */
    @Override
    public IMatrice produit(double valeur) {
        IMatrice nouvelleMatrice = new Matrice(numLignes, numColonnes, 0);

        for (int i = 0; i < numLignes; i++) {
            for (int j = 0; j < numColonnes; j++) {
                nouvelleMatrice.setElement(i, j, getElement(i, j) * valeur);
            }
        }

        return nouvelleMatrice;
    }

    /**
     * Calcule le produit de cette Matrice (A) par autreMatrice (B) = A X B.
     *
     * @param autreMatrice la matrice a multiplier avec cette matrice.
     * @return la matrice resultante du produit de cette matrice par
     * autreMatrice. La dimension de la matrice resultante sera
     * this.getNumLignes() X autreMatrice.getNumColonnes().
     * @throws MatriceException si :
     *                          - autreMatrice est null
     *                          - le nombre de colonnes de cette matrice n'est pas egal au
     *                          nombre de lignes de autreMatrice.
     */
    @Override
    public IMatrice produit(IMatrice autreMatrice) {
        if (autreMatrice == null || numColonnes != autreMatrice.getNumLignes())
            throw new MatriceException();

        IMatrice nouvelleMatrice = new Matrice(numLignes, autreMatrice.getNumColonnes(), 0);

        for (int i = 0; i < numLignes; i++) {
            for (int j = 0; j < autreMatrice.getNumColonnes(); j++) {
                Double valeur = 0.0;
                double[] ligne = getLigne(i);
                double[] colonne = autreMatrice.getColonne(j);
                for (int k = 0; k < ligne.length; k++) {
                    valeur += ligne[k] * colonne[k];
                }
                nouvelleMatrice.setElement(i, j, valeur);
            }
        }

        return nouvelleMatrice;
    }

    /**
     * Construit la transposee de cette matrice.
     *
     * @return la transposee de cette matrice.
     */
    @Override
    public IMatrice transposee() {
        IMatrice matrice = new Matrice(numColonnes, numLignes, 0);

        for (int i = 0; i < numColonnes; i++) {
            matrice.remplacerLigne(i, getColonne(i));
        }

        return matrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrice)) return false;

        Matrice matrice = (Matrice) o;

        if (numColonnes != matrice.numColonnes) return false;
        if (numLignes != matrice.numLignes) return false;
        if (elements != null ? !elements.equals(matrice.elements) : matrice.elements != null) return false;

        return true;
    }

    /**
     * Retourne une representation sous forme de chaine de caracteres de cette
     * matrice.
     * Exemple de la representation d'une matrice de 3 lignes par 2 colonnes :
     *
     *                                  [     9,0     5,0  ]
     *                                  [     6,0     7,0  ]
     *                                  [     7,0     4,0  ]
     *
     * NOTE : Cette methode fonctionne pour les matrices dont tous les
     * elements sous forme de chaine de caracteres ne contiennent pas plus de 7
     * caracteres. Les elements sont arrondis a une decimale.
     * @return une representation sous forme de chaine de caracteres de cette
     * matrice.
     */
    @Override
    public String toString() {
        final DecimalFormat DEC_FORMAT = new DecimalFormat("0.0");
        final int ESP = 8;
        int num;
        String sTmp;
        String s = "[";
        for (int i = 0 ; i < (numLignes * numColonnes) ; i++) {
            //etendre i sur ESP colonnes
            sTmp = "";
            num =  ESP - DEC_FORMAT.format(elements.get(i)).length();
            for (int j = 0 ; j < num ; j++) {
                sTmp = sTmp + " ";
            }
            sTmp = sTmp + DEC_FORMAT.format(elements.get(i));

            if (i != 0 && i % numColonnes == 0) {
                s = s + "  ]\n[" + sTmp;
            } else {
                s = s + sTmp;
            }
        }
        s = s + "  ]";
        return s;
    }
}
