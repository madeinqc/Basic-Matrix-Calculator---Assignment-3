/**
 * Interface permettant les diverses opérations sur les matrices.
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

public interface MatrixListener {

    /**
     * Ajoute une matrice à la liste.
     * @param namedMatrix le nom de la matrice ajoutée.
     */
    public void matrixAdded(NamedItem<IMatrice> namedMatrix);

    /**
     * Enlève une matrice de la liste.
     * @param namedMatrix la matrice à enlever de la liste.
     */
    public void matrixRemoved(NamedItem<IMatrice> namedMatrix);

    /**
     * Sauvegarde la matrice.
     * @param matrix la matrice à sauvegarder.
     */
    public void saveMatrix(IMatrice matrix);

    /**
     * Enregistre l'opération à effectuer sur la/les matrices.
     * @param operation l'opération à effectuer.
     */
    public void matrixOperation(MatrixOperation operation);

    /**
     * Enregistre l'état que doit prendre la zone.
     * @param state l'état que doit prendre la zone.
     */
    public void stateChanged(MatrixEditor.EditorState state);
}
