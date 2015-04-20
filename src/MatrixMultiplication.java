/**
 * Cette classe gère la multiplication de deux matrices.
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
public class MatrixMultiplication implements MatrixOperation {

    private NamedItem<IMatrice> firstMatrix;
    private NamedItem<IMatrice> secondMatrix;

    private IMatrice result;
    private String name;

    /**
     * Effectue la multiplication de deux matrices
     *
     * @param firstMatrix       la première matrice à multiplier.
     * @param secondMatrix      la deuxième matrice à multiplier.
     *
     * @throws MatriceException lorsque les deux matrices sont incompatibles.
     */
    public MatrixMultiplication(NamedItem<IMatrice> firstMatrix, NamedItem<IMatrice> secondMatrix) throws MatriceException{
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;

        result = firstMatrix.getItem().produit(secondMatrix.getItem());
        name = firstMatrix.getName() + " × " + secondMatrix.getName();
    }

    /**
     * Retourne le résultat de la multiplication par deux matrices.
     * @return le résultat de la multiplication par deux matrices.
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
