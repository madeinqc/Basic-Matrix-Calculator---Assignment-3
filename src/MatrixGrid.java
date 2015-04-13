import javax.swing.*;
import java.awt.*;

/**
 * TODO Faire les commentaires de la classe.
 * Created by madeinqc on 4/11/15.
 */
public class MatrixGrid extends JPanel {

    private IMatrice matrix;
    private JTextField[][] textFields;
    private MatrixEditor.EditorState state;

    public IMatrice getMatrix() {
        return matrix;
    }

    public void setMatrix(IMatrice matrix) {
        this.matrix = matrix;
    }

    public MatrixEditor.EditorState getState() {
        return state;
    }

    public void setState(MatrixEditor.EditorState state) {
        this.state = state;

        updateUIFromState();
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public MatrixGrid(IMatrice matrix, MatrixEditor.EditorState state) {
        this.matrix = matrix;
        this.state = state;

        updateGrid();
    }

    private void updateGrid() {
        removeAll();

        setLayout(new GridLayout(matrix.getNumLignes(), matrix.getNumColonnes(), 10, 10));
        textFields = new JTextField[matrix.getNumLignes()][matrix.getNumColonnes()];

        for (int i = 0; i < matrix.getNumLignes(); i++) {
            for (int j = 0; j < matrix.getNumColonnes(); j++) {
                JTextField textField = new JTextField(String.valueOf(matrix.getElement(i, j)));
                textFields[i][j] = textField;
                textField.setPreferredSize(new Dimension(45, textField.getPreferredSize().height));
                add(textField);
            }
        }

        updateUIFromState();

        revalidate();
        repaint();
    }

    public void updateUIFromState() {
        for (int i = 0; i < matrix.getNumLignes(); i++) {
            for (int j = 0; j < matrix.getNumColonnes(); j++) {
                textFields[i][j].setEnabled(state == MatrixEditor.EditorState.editing);
            }
        }
    }

    public void addLine() {
        double[] line = new double[matrix.getNumColonnes()];
        for (int i = 0; i < matrix.getNumColonnes(); i++) {
            line[i] = 0.0;
        }
        matrix.ajouterLigne(matrix.getNumLignes(), line);
        updateGrid();
    }

    public void removeLine() {
        matrix.supprimerLigne(matrix.getNumLignes() - 1);
        updateGrid();
    }

    public void addColumn() {
        double[] row = new double[matrix.getNumLignes()];
        for (int i = 0; i < matrix.getNumLignes(); i++) {
            row[i] = 0.0;
        }
        matrix.ajouterColonne(matrix.getNumColonnes(), row);
        updateGrid();
    }

    public void removeColumn() {
        matrix.supprimerColonne(matrix.getNumColonnes() - 1);
        updateGrid();
    }

    public IMatrice transposed() {
        matrix = matrix.transposee();
        updateGrid();

        return matrix;
    }

    public boolean ValidateAndFillMatrix() {
        boolean isValid = true;

        for (int i = 0; i < matrix.getNumLignes(); i++) {
            for (int j = 0; j < matrix.getNumColonnes(); j++) {
                try {
                    matrix.setElement(i, j, Double.parseDouble(textFields[i][j].getText()));
                } catch (NumberFormatException e) {
                    isValid = false;
                }
            }
        }

        return isValid;
    }
}
