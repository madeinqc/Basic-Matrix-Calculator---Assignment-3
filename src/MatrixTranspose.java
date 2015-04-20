/**
 * Cette classe gère la transposée d'une matrice.
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
public class MatrixTranspose implements MatrixOperation {

    private NamedItem<IMatrice> firstMatrix;

    private IMatrice result;
    private String name;

    /**
     * Crée la transposée d'une matrice en fonction de celle-ci passée en paramètre.
     *
     * @param firstMatrix       la matrice sur laquelle on doit effectuer une transposée.
     * @throws MatriceException lorsque la matrice ne peut être transposée.
     */
    public MatrixTranspose(NamedItem<IMatrice> firstMatrix) throws MatriceException{
        this.firstMatrix = firstMatrix;

        result = firstMatrix.getItem().transposee();
        name = "T (" + firstMatrix.getName() + ")";
    }

    /**
     * Retourne le résultat obtenu par la matrice transposée.
     *
     * @return la matrice transposée.
     */
    public IMatrice getResult() {
        return result;
    }

    /**
     * Retourne le nom de la matrice transposée.
     *
     * @return le nom de la matrice transposée.
     */
    public String getName() {
        return name;
    }
}
