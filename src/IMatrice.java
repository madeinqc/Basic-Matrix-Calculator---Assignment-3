
/**
 * Interface offrant des methodes pour la manipulation simple de matrices dont
 * les elements sont des nombres reels.
 * @author Melanie lord
 * @version janvier 2015 (TP1 INF2120 H15)
 */
public interface IMatrice {
   
   /**
    * Retourne le nombre de lignes de cette matrice.
    * @return le nombre de lignes de cette matrice.
    */
   int getNumLignes ();
   
   /**
    * Retourne le nombre de colonnes de cette matrice.
    * @return le nombre de colonnes de cette matrice.
    */
   int getNumColonnes ();
   
   /**
    * Retourne l'element de la matrice a la position noLigne, noCol.
    * @param noLigne le numero de la ligne dans cette matrice.
    * @param noCol le numero de la colonne dans cette matrice.
    * @return l'element de la matrice a la position noLigne, noCol.
    * @throws MatriceException si :
    *    noLigne ou noCol n'est pas un indice valide dans cette matrice.
    */
   double getElement (int noLigne, int noCol);
   
   /**
    * Modifie l'element a la position noLigne, noCol par l'element passe en 
    * parametre.
    * @param noLigne le numero de la ligne dans cette matrice.
    * @param noCol le numero de la colonne dans cette matrice.
    * @param element le nouvel element a inserer a la position noLigne, noCol
    * dans cette matrice.
    * @throws MatriceException si noLigne ou noCol n'est pas un indice valide 
    * dans cette matrice.
    */
   void setElement (int noLigne, int noCol, double element);
   
   /**
    * Retourne la ligne de cette matrice specifiee par noLigne.
    * @param noLigne le numero de la ligne a retourner.
    * @return la ligne de cette matrice specifiee par noLigne.
    * @throws MatriceException si noLigne n'est pas une ligne valide
    * dans cette matrice.
    */
   public double[] getLigne (int noLigne);
   
   /**
    * Retourne la colonne de cette matrice specifiee par noCol.
    * @param noCol le numero de la colonne a retourner.
    * @return la colonne de cette matrice specifiee par noCol.
    * @throws MatriceException si noCol n'est pas une colonne valide
    * dans cette matrice.
    */
   double[] getColonne (int noCol);

   /**
    * Remplace la ligne specifiee par noLigne de cette matrice par la ligne
    * donnee en parametre.
    * @param noLigne le numero de la ligne a remplacer.
    * @param ligne la nouvelle ligne
    * @throws Matrice Exception si :
    *       - noLigne n'est pas une ligne valide dans cette matrice.
    *       - ligne est null ou ne contient pas exactement getNumColonnes()
    *         valeurs.
    */
   void remplacerLigne (int noLigne, double[] ligne);
   
   /**
    * Remplace la colonne specifiee par noCol de cette matrice par la colonne
    * donnee en parametre. 
    * @param noCol le numero de la colonne a remplacer.
    * @param colonne la nouvelle colonne 
    * @throws MatriceException si :
    *       - noCol n'est pas une colonne valide dans cette matrice.
    *       - colonne est null ou ne contient pas exactement getNumLignes() 
    *         valeurs.
    */
   void remplacerColonne (int noCol, double[] colonne);
   
   /**
    * Ajoute a cette matrice la ligne donnee au noLigne donne. 
    * Apres cet ajout, la matrice contient une ligne de plus et la nouvelle 
    * ligne se trouve a l'indice noLigne (le numero des lignes suivantes aura 
    * augmente de 1). 
    * @param noLigne l'indice de la nouvelle ligne apres l'ajout.
    * @param ligne la ligne a ajouter dans cette matrice, a l'indice noLigne.
    * @throws MatriceException si :
    *  - noLigne n'est pas entre 0 et getNumLignes() inclusivement
    *  - ligne est null ou ne contient pas exactement getNumColonnes() valeurs.
    */
   void ajouterLigne (int noLigne, double[] ligne);
   
   /**
    * Ajoute a cette matrice la colonne donnee au noCol donne. 
    * Apres cet ajout, la matrice contient une colonne de plus et la nouvelle 
    * colonne se trouve a l'indice noCol (le numero des colonnes suivantes aura 
    * augmente de 1). 
    * @param noCol l'indice de la nouvelle colonne apres l'ajout. 
    * @param colonne la colonne a ajouter.
    * @throws MatriceException si :
    *  - noCol n'est pas entre 0 et getNumColonnes() inclusivement.
    *  - colonne est null ou ne contient pas exactement getNumLignes() valeurs.
    */
   void ajouterColonne (int noCol, double[] colonne);
   
   /**
    * Supprime la ligne de cette matrice correspondant au noLigne donne.
    * Apres l'appel de cette methode, cette matrice contient une ligne de moins.
    * Note : on ne peut pas supprimer une ligne dans une matrice qui ne contient 
    * qu'une seule ligne.
    * @param noLigne le numero de la ligne a supprimer.
    * @return la ligne supprimee.
    * @throws MatriceException si :
    *       - noLigne n'est pas une ligne valide dans cette matrice ou 
    *       - si cette matrice ne contient qu'une seule ligne avant la 
    *         suppression.
    */
   double[] supprimerLigne (int noLigne);
   
   /**
    * Supprime la colonne de cette matrice correspondant au noCol donne.
    * Apres l'appel de cette methode, cette matrice contient une colonne de moins.
    * Note : on ne peut pas supprimer une colonne dans une matrice qui ne contient 
    * qu'une seule colonne.
    * @param noCol le numero de la colonne a supprimer.
    * @return la colonne supprimee.
    * @throws MatriceException si :
    *       - noCol n'est pas une colonne valide dans cette matrice 
    *       - si cette matrice ne contient qu'une seule colonne avant la 
    *         suppression.
    */
   double[] supprimerColonne (int noCol);
   
   /**
    * Effectue la somme de cette Matrice avec autreMatrice donnee en parametre.
    * @param autreMatrice la matrice a additionner avec cette matrice.
    * @return la matrice resultante de la somme de cette matrice avec
    * autreMatrice.
    * @throws MatriceException si autreMatrice est null ou n'est pas de meme 
    * dimension que cette matrice(meme nombre de lignes et meme nombre de 
    * colonnes).
    */
   IMatrice somme (IMatrice autreMatrice);
   
   /**
    * Calcul le produit de cette matrice par la valeur donnee.
    * @param valeur la valeur de multiplication de cette matrice.
    * @return la matrice resultante du produit de cette matrice par la 
    * valeur donnee.
    */
   IMatrice produit (double valeur);
   
   /**
    * Calcule le produit de cette Matrice (A) par autreMatrice (B) = A X B.
    * @param autreMatrice la matrice a multiplier avec cette matrice.
    * @return la matrice resultante du produit de cette matrice par 
    * autreMatrice. La dimension de la matrice resultante sera 
    * this.getNumLignes() X autreMatrice.getNumColonnes().
    * @throws MatriceException si : 
    *             - autreMatrice est null  
    *             - le nombre de colonnes de cette matrice n'est pas egal au 
    *               nombre de lignes de autreMatrice.
    */
   IMatrice produit (IMatrice autreMatrice);
   
   /**
    * Construit la transposee de cette matrice.
    * @return la transposee de cette matrice.
    */
   IMatrice transposee();
   
}
