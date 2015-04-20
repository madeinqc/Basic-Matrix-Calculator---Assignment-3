/**
 * TODO Faire les commentaires de la classe.
 * Created by madeinqc on 4/11/15.
 */
public interface MatrixListener {
    public void matrixAdded(NamedItem<IMatrice> namedMatrix);
    public void matrixRemoved(NamedItem<IMatrice> namedMatrix);
    public void saveMatrix(IMatrice matrix);
    public void matrixOperation(MatrixOperation operation);
    public void stateChanged(MatrixEditor.EditorState state);
}
