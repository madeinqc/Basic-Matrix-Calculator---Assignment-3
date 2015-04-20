/**
 * Cette classe gère la multiplication d'une matrice par un nombre réel.
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
public class MatrixScalarMultiplication implements MatrixOperation {

    private NamedItem<IMatrice> firstMatrix;

    private IMatrice result;
    private String name;

    /**
     * Construit le résultat d'une multiplication par un nombre.
     *
     * @param firstMatrix   la matrice à multiplier par un nombre.
     * @param number        le nombre par lequel la matrice doit être multipliée.
     *
     * @throws MatriceException lorsque la matrice ne peut être multipliée par ce nombre.
     */
    public MatrixScalarMultiplication(NamedItem<IMatrice> firstMatrix, double number) throws MatriceException{
        this.firstMatrix = firstMatrix;

        result = firstMatrix.getItem().produit(number);
        name = firstMatrix.getName() + " × " + number;
    }

    /**
     * Retourne la nouvelle matrice.
     *
     * @return la nouvelle matrice.
     */
    public IMatrice getResult() {
        return result;
    }

    /**
     * Retourne le nom de la matrice.
     *
     * @return le nom de la matrice.
     */
    public String getName() {
        return name;
    }
}
