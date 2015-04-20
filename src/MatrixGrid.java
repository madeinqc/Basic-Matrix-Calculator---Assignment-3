import javax.swing.*;
import java.awt.*;

/**
 * Classe qui gère la grille d'une matrice.
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
public class MatrixGrid extends JPanel {

    private IMatrice matrix;
    private JTextField[][] textFields;
    private MatrixEditor.EditorState state;

    /**
     * Retourne la matrice.
     *
     * @return la matrice sous forme de IMatrice.
     */
    public IMatrice getMatrix() {
        return matrix;
    }

    /**
     * Remplace la matrice par la matrice passée en paramètre.
     *
     * @param matrix la matrice qui remplacera la matrice actuelle.
     */
    public void setMatrix(IMatrice matrix) {
        this.matrix = matrix;
    }

    /**
     * Retourne le mode d'opération actuel.
     * @return le mode d'opération actuel.
     */
    public MatrixEditor.EditorState getState() {
        return state;
    }

    /**
     * Ajuste l'état du mode d'opération avec l'état passé en paramètre et appelle la méthode qui rafraîchit l'UI.
     * @param state L'état que prendra le mode d'opération actuel.
     */
    public void setState(MatrixEditor.EditorState state) {
        this.state = state;

        updateUIFromState();
    }

    /**
     * Remplace la matrice actuel et le mode d'opération par les variables passées en paramètres.
     *
     * @param matrix la matrice qui écrasera la matrice actuelle.
     * @param state l'état qui remplacera le mode d'opération actuel.
     */
    public MatrixGrid(IMatrice matrix, MatrixEditor.EditorState state) {
        this.matrix = matrix;
        this.state = state;

        updateGrid();
    }

    /**
     * Ajuste l'UI en fonction de la matrice sélectionnée.
     */
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

    /**
     * Modifie l'état des cases de la grille.
     */
    public void updateUIFromState() {
        for (int i = 0; i < matrix.getNumLignes(); i++) {
            for (int j = 0; j < matrix.getNumColonnes(); j++) {
                textFields[i][j].setEnabled(state == MatrixEditor.EditorState.editing);
            }
        }
    }

    /**
     * Ajoute une ligne à la matrice avec des éléments initialisés à 0.0
     */
    public void addLine() {
        double[] line = new double[matrix.getNumColonnes()];
        for (int i = 0; i < matrix.getNumColonnes(); i++) {
            line[i] = 0.0;
        }
        matrix.ajouterLigne(matrix.getNumLignes(), line);
        updateGrid();
    }

    /**
     * Supprime la dernière ligne.
     */
    public void removeLine() {
        matrix.supprimerLigne(matrix.getNumLignes() - 1);
        updateGrid();
    }

    /**
     * Ajoute une colonne avec des éléments initialisés à 0.0
     */
    public void addColumn() {
        double[] row = new double[matrix.getNumLignes()];
        for (int i = 0; i < matrix.getNumLignes(); i++) {
            row[i] = 0.0;
        }
        matrix.ajouterColonne(matrix.getNumColonnes(), row);
        updateGrid();
    }

    /**
     * Supprime la dernière colonne.
     */
    public void removeColumn() {
        matrix.supprimerColonne(matrix.getNumColonnes() - 1);
        updateGrid();
    }

    /**
     * Modifie la matrice actuelle par sa transposée et ajuste l'UI.
     *
     * @return  la matrice transposée.
     */
    public IMatrice transposed() {
        matrix = matrix.transposee();
        updateGrid();

        return matrix;
    }

    /**
     * Vérifie si toutes les valeurs de la matrice sont valides.
     *
     * @return  - true si toutes les valeurs sont valides.
     *          - false si toutes les valeurs ne sont pas valides.
     */
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
