/**
 * Created by madeinqc on 4/11/15.
 */
public interface MatrixEditorListener {
    public void matrixAdded(NamedItem<IMatrice> namedMatrix);
    public void matrixRemoved(NamedItem<IMatrice> namedMatrix);

    public void stateChanged(MatrixEditor.EditorState state);
}
