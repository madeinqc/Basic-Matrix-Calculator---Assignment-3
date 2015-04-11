import javax.swing.*;

/**
 * Created by madeinqc on 4/11/15.
 */
public class MatrixGrid extends JPanel {

    private IMatrice matrix;

    public IMatrice getMatrix() {
        return matrix;
    }

    public void setMatrix(IMatrice matrix) {
        this.matrix = matrix;
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public MatrixGrid(IMatrice matrix) {
        this.matrix = matrix;

    }

    public void addLine() {

    }

    public void removeLine() {

    }

    public void addColumn() {

    }

    public void removeColumn() {

    }

    public void transposed() {

    }

    public void multiplyBy(int multiplier) {

    }
}
