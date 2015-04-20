/**
 * Cette classe gère l'opération de type addition sur les matrices.
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
public class MatrixAddition implements MatrixOperation {

    /************************************
     * VARIABLES D'INSTANCE
     ************************************/
    private NamedItem<IMatrice> firstMatrix;
    private NamedItem<IMatrice> secondMatrix;

    private IMatrice result;
    private String name;

    /**
     * Effectue l'addition de deux matrices.
     *
     * @param firstMatrix   la première matrice à additionner.
     * @param secondMatrix  la deuxième matrice à additionner
     * @throws MatriceException si :
     *                          - les deux matrices ne sont pas compatibles.
     */
    public MatrixAddition(NamedItem<IMatrice> firstMatrix, NamedItem<IMatrice> secondMatrix) throws MatriceException{
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;

        result = firstMatrix.getItem().somme(secondMatrix.getItem());
        name = firstMatrix.getName() + " + " + secondMatrix.getName();
    }

    /**
     * @return le résultat de l'addition par les matrices
     */
    public IMatrice getResult() {
        return result;
    }

    /**
     * @return le nom de la nouvelle matrice créée.
     */
    public String getName() {
        return name;
    }
}
