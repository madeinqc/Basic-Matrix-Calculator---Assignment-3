/**
 * Interface permettant d'obtenir les éléments obtenus par une opération.
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
 * @version 2015-04-20
 */
public interface MatrixOperation {

    /**
     * Retourne une IMatrice obtenue par le résultat d'une opération.
     * @return une IMatrice
     */
    public IMatrice getResult();

    /**
     * Retourne le nom de la matrice obtenue.
     * @return le nom de la matrice.
     */
    public String getName();

}
