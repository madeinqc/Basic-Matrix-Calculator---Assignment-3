import javax.swing.*;

/**
 * TODO Faire les commentaires de la classe.
 * Created by madeinqc on 4/13/15.
 */
public class MatrixResultPanel extends JPanel {

    private MatrixOperation operation;

    public MatrixOperation getOperation() {
        return operation;
    }

    public void setOperation(MatrixOperation operation) {
        this.operation = operation;
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public MatrixResultPanel() {
    }


}
