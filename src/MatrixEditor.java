import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Cette classe gère l'ensemble des opérations disponibles sur les matrices.
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
 * @version 2015-04-09
 */
public class MatrixEditor extends JPanel implements MatrixListener {

    private JTextArea initializationTextArea;
    private MatrixGrid matrixGrid = null;
    private JPanel newMatrixPanel;
    private JComboBox<Integer> newColumnNumberSelector;
    private JComboBox<Integer> newLineNumberSelector;
    private JButton confirmNewMatrixButton;
    private JLabel multiplicatorLabel;
    private JPanel matrixPanel;
    private ArrayList<MatrixListener> listeners = new ArrayList<>();

    public enum EditorState {
        initialization, operation, editing, newMatrix
    }

    IMatrice currentMatrix = null;
    private ArrayList<NamedItem<IMatrice>> matrices;

    private JPanel loaderSection;
    private JComboBox<NamedItem<IMatrice>> matrixSelector;
    private JButton deleteButton;

    private JPanel editionSection;
    private JButton newButton;
    private JButton editButton;
    private JButton addLineButton;
    private JButton addColumnButton;
    private JButton removeLineButton;
    private JButton removeColumnButton;
    private JButton transposedButton;
    private JTextField multiplicatorTextField;

    private EditorState state = EditorState.initialization;

    /**
     * Retourne toutes les matrices contenue dans l'ArrayList
     *
     * @return toutes les matrices contenues dans l'ArrayList
     */
    public ArrayList<NamedItem<IMatrice>> getMatrices() {
        return matrices;
    }

    /**
     * Retourne la matrice présentement sélectionnée
     *
     * @return la matrice présentement sélectionnée
     */
    public NamedItem<IMatrice> getSelectedMatrix() {
        return matrixSelector.getItemAt(matrixSelector.getSelectedIndex());
    }

    /**
     * Retourne le mode d'opération actuel de la zone
     *
     * @return le mode d'opération actuel de la zone
     */
    public EditorState getState() {
        return state;
    }

    /**
     * Ajoute un Listener à la zone
     *
     * @param listener  le Listener à ajouter à la zone actuelle
     */
    public void addListener(MatrixListener listener) {
        listeners.add(listener);
    }

    /**
     * Enlève un Listener à la zone
     *
     * @param listener  le Listener à enlever à la zone actuelle
     */
    public void removeListener(MatrixListener listener) {
        listeners.remove(listener);
    }

    /**
     * Constructeur qui prend en paramètre une ArrayList de matrice et appelle les méthodes pour initialiser l'UI.
     *
     * @param matrices  une ArrayList de matrices
     */
    public MatrixEditor(ArrayList<NamedItem<IMatrice>> matrices) {
        this.matrices = matrices;

        setLayout(new BorderLayout(0, 6));

        setupLoaderSection();
        setupMatrixSection();
        setupEditionSection();

        updateUIFromState();
    }

    /**
     * Modifie l'interface utilisateur en fonction du mode d'opération actuel.
     */
    private void updateUIFromState() {
        matrixPanel.removeAll();

        switch (state)
        {
            case initialization:
                matrixSelector.setSelectedIndex(0);
                matrixPanel.add(initializationTextArea);
                break;

            case operation:
                editButton.setText("Éditer");
                matrixPanel.add(matrixGrid);
                break;

            case editing:
                editButton.setText("Sauvegarder");
                matrixPanel.add(matrixGrid);
                break;

            case newMatrix:
                matrixSelector.setSelectedIndex(0);
                matrixPanel.add(newMatrixPanel);
                break;
        }

        deleteButton.setEnabled(state == EditorState.operation);
        editButton.setEnabled(state == EditorState.operation || state == EditorState.editing);
        addLineButton.setEnabled(state == EditorState.editing);
        addColumnButton.setEnabled(state == EditorState.editing);
        removeLineButton.setEnabled(state == EditorState.editing);
        removeColumnButton.setEnabled(state == EditorState.editing);
        transposedButton.setEnabled(state == EditorState.operation);
        multiplicatorLabel.setEnabled(state == EditorState.operation);
        multiplicatorTextField.setEnabled(state == EditorState.operation);
        if (state == EditorState.operation)
            multiplicatorTextField.setBackground(Color.YELLOW);
        else
            multiplicatorTextField.setBackground(Color.WHITE);

        matrixPanel.validate();
        matrixPanel.repaint();

        for (MatrixListener listener : listeners) {
            if (listener != null) {
                listener.stateChanged(state);
            }
        }
    }

    /**
     * Construit la section contenant la liste de matrices et le bouton de suppression
     */
    private void setupLoaderSection() {
        loaderSection = new JPanel();
        loaderSection.setLayout(new FlowLayout());

        matrixSelector = new JComboBox<NamedItem<IMatrice>>();
        loaderSection.add(matrixSelector);
        matrixSelector.setPreferredSize(new Dimension(120, matrixSelector.getPreferredSize().height));
        for (NamedItem<IMatrice> namedMatrix : matrices) {
            matrixSelector.addItem(namedMatrix);
        }
        matrixSelector.addActionListener(new MatrixSelectorActionListener());

        deleteButton = new JButton("Supprimer");
        loaderSection.add(deleteButton);
        deleteButton.addActionListener(new DeleteActionListener());

        add(loaderSection, BorderLayout.NORTH);
    }

    /**
     * Construit l'interface utilisateur qui permet de créer une nouvelle matrice
     */
    private void setupMatrixSection() {
        matrixPanel = new JPanel();
        add(matrixPanel, BorderLayout.CENTER);

        initializationTextArea = new JTextArea();
        initializationTextArea.setText("Créez une nouvelle matrice en cliquant\n\r" +
                "sur le bouton [ Nouvelle ] ci-dessous ou\n\r" +
                "choisissez une matrice existante dans\n\r" +
                "la liste déroulante ci-dessus.");
        initializationTextArea.setEditable(false);
        initializationTextArea.setPreferredSize(new Dimension(375, 80));
        initializationTextArea.setMargin(new Insets(10,65,10,0));

        newMatrixPanel = new JPanel();
        newMatrixPanel.setLayout(new GridLayout(3, 2));
        newMatrixPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        newMatrixPanel.add(new JLabel("Nombre de lignes : "));
        newLineNumberSelector = new JComboBox<>();
        newMatrixPanel.add(newLineNumberSelector);
        newMatrixPanel.add(new JLabel("Nombre de colonnes : "));
        newColumnNumberSelector = new JComboBox<>();
        newMatrixPanel.add(newColumnNumberSelector);

        for (int i = 1; i <= 8; i++) {
            newColumnNumberSelector.addItem(i);
            newLineNumberSelector.addItem(i);
        }

        newMatrixPanel.add(new JPanel());
        confirmNewMatrixButton = new JButton("OK");
        confirmNewMatrixButton.addActionListener(new confirmNewMatrixActionListener());
        newMatrixPanel.add(confirmNewMatrixButton);
    }

    /**
     * Construit l'interface utilisateur pour éditer une matrice
     */
    private void setupEditionSection() {
        editionSection = new JPanel();
        editionSection.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        editionSection.setPreferredSize(new Dimension(385, 80));

        newButton = new JButton("Nouvelle");
        editionSection.add(newButton);
        newButton.addActionListener(new NewActionListener());

        editButton = new JButton("Éditer");
        editionSection.add(editButton);
        editButton.addActionListener(new EditActionListener());

        addLineButton = new JButton("+ Ligne");
        editionSection.add(addLineButton);
        addLineButton.addActionListener(new AddLineActionListener());

        addColumnButton = new JButton("+ Colonne");
        editionSection.add(addColumnButton);
        addColumnButton.addActionListener(new AddColumnActionListener());

        removeLineButton = new JButton("- Ligne");
        editionSection.add(removeLineButton);
        removeLineButton.addActionListener(new RemoveLineActionListener());

        removeColumnButton = new JButton("- Colonne");
        editionSection.add(removeColumnButton);
        removeColumnButton.addActionListener(new RemoveColumnActionListener());

        transposedButton = new JButton("Transposée");
        editionSection.add(transposedButton);
        transposedButton.addActionListener(new TransposedActionListener());

        multiplicatorLabel = new JLabel("Mult. par");
        editionSection.add(multiplicatorLabel);

        multiplicatorTextField = new JTextField();
        multiplicatorTextField.setPreferredSize(new Dimension(40,
                multiplicatorTextField.getPreferredSize().height));
        multiplicatorTextField.addKeyListener(new MultiplicatorKeyListener());
        editionSection.add(multiplicatorTextField);

        add(editionSection, BorderLayout.SOUTH);
    }

    /**
     * Affiche un message d'erreur selon le message passé en paramètre
     *
     * @param error le message à afficher
     */
    private void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "ERREUR", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Enregistre la matrice dans l'ArrayList
     *
     * @param name le nom de la matrice à enregistrer
     */
    public void saveMatrix(String name, IMatrice matrix) {
        NamedItem<IMatrice> namedMatrix = new NamedItem<>(name, matrix);

        matrices.add(namedMatrix);
        matrixAdded(namedMatrix);

        for (MatrixListener listener : listeners) {
            if (listener != null) {
                listener.matrixAdded(namedMatrix);
            }
        }
    }

    /**
     * Ajoute une matrice à la liste
     *
     * @param namedMatrix la matrice à ajouter à la liste
     */
    @Override
    public void matrixAdded(NamedItem<IMatrice> namedMatrix) {
        matrixSelector.addItem(namedMatrix);
    }

    /**
     * Supprime une matrice de la liste et modifie le mode d'opération de la zone
     *
     * @param namedMatrix la matrice à supprimer
     */
    @Override
    public void matrixRemoved(NamedItem<IMatrice> namedMatrix) {
        if (getSelectedMatrix() == namedMatrix) {
            state = EditorState.initialization;
            updateUIFromState();
        }
        matrices.remove(namedMatrix);
        matrixSelector.removeItem(namedMatrix);
    }

    /**
     * Appelle la classe mère pour gérer la sauvegarde la matrice.
     *
     * @param matrix la matrice à sauvegarder.
     */
    @Override
    public void saveMatrix(IMatrice matrix) {
        saveMatrix(getNewMatrixName(), matrix);
    }

    @Override
    public void matrixOperation(MatrixOperation operation) {

    }

    @Override
    public void stateChanged(EditorState state) {

    }

    /**
     * Affiche le message d'entrée de la nouvelle matrice et vérifie sa validité.
     *
     * @return le nom de la matrice
     */
    private String getNewMatrixName() {
        String name = "";

        do {
            name = JOptionPane.showInputDialog(this, "Nom matrice:");
            if (name != null) {
                // On vérifie la validité du nom de la matrice (1 à 5 caractères, chiffres et lettres seulement)
                if (name.length() > 5 || name.equals("") || name.length() != name.replaceAll("[^A-Za-z0-9]", "").length()) {
                    showError("Le nom de la matrice doit contenir entre 1 et 5 caractères alphanumériques !");
                    name = "";
                } else if (doesNameExist(name)) {
                    showError("Une matrice portant ce nom existe déjà. Veuillez en choisir un autre.");
                    name = "";
                }
            }
        // Si on appuie sur "Annuler", on quitte la boucle.
        } while (name != null && name.equals(""));

        return name;
    }

    /**
     * Vérifie si le nom entré de la nouvelle matrice existe déjà
     *
     * @return
     *          - false si la matrice n'existe pas dans la liste
     *          - true si la matrice existe déjà dans la liste
     */
    private boolean doesNameExist(String name) {
        boolean found = false;

        for (NamedItem<IMatrice> namedMatrix : matrices)
        {
            if (namedMatrix.getName().equals(name)) {
                found = true;
            }
        }

        return found;
    }

    /**
     * Cette classe gère la sélection de la matrice de l'ArrayList
     */
    private class MatrixSelectorActionListener implements java.awt.event.ActionListener {
        /**
         * Gère l'UI, le mode d'opération en fonction de la matrice sélectionnée.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (matrixSelector.getSelectedIndex() == 0)
            {
                if (state != EditorState.newMatrix) {
                    state = EditorState.initialization;
                    currentMatrix = null;
                    matrixGrid = null;
                }
            } else {
                state = EditorState.operation;
                currentMatrix = new Matrice(getSelectedMatrix().getItem());
                matrixGrid = new MatrixGrid(currentMatrix, state);
            }
            updateUIFromState();
        }
    }

    /**
     * Gère le bouton de supression de matrice
     */
    private class DeleteActionListener implements ActionListener {
        /**
         * Supprimme la matrice sélectionnée, ajuste l'UI.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            NamedItem<IMatrice> matrix = getSelectedMatrix();
            matrixRemoved(matrix);
            state = EditorState.initialization;
            updateUIFromState();

            for (MatrixListener listener : listeners) {
                if (listener != null) {
                    listener.matrixRemoved(matrix);
                }
            }
        }
    }

    /**
     * Gère le bouton de création de matrice.
     */
    private class NewActionListener implements ActionListener {
        /**
         * Gère l'UI pour qu'elle soit en mode Nouvelle Matrice.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            state = EditorState.newMatrix;
            newColumnNumberSelector.setSelectedIndex(0);
            newLineNumberSelector.setSelectedIndex(0);
            updateUIFromState();
            currentMatrix = null;
            matrixGrid = null;
        }
    }

    /**
     * Gère la confirmation de la création d'une nouvelle matrice.
     */
    private class confirmNewMatrixActionListener implements ActionListener {
        /**
         * Gère l'UI pour qu'elle soit en mode d'édition et crée la nouvelle matrice
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            state = EditorState.editing;
            int numLine = newLineNumberSelector.getItemAt(newLineNumberSelector.getSelectedIndex());
            int numColumn = newColumnNumberSelector.getItemAt(newColumnNumberSelector.getSelectedIndex());
            currentMatrix = new Matrice(numLine, numColumn, 0.0);
            matrixGrid = new MatrixGrid(currentMatrix, state);
            updateUIFromState();
        }
    }

    /**
     * Gère l'édition de matrices
     */
    private class EditActionListener implements ActionListener {
        /**
         * Gère l'édition et la sauvegarde de la matrice lorsque le bouton d'édition/sauvegarde est appuyé.
         * Rafraîchit également l'UI.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (state != EditorState.editing) {
                state = EditorState.editing;
                matrixGrid.setState(state);
                matrixGrid.updateUIFromState();
            } else {
                if (matrixGrid.ValidateAndFillMatrix()) {
                    String name = getNewMatrixName();
                    if (name != null) {
                        saveMatrix(name, currentMatrix);
                        matrixSelector.setSelectedIndex(matrixSelector.getItemCount() - 1);
                        state = EditorState.operation;
                        matrixGrid.setState(state);
                        matrixGrid.updateUIFromState();
                    }
                } else {
                    showError("Tous les champs de la matrices doivent être des nombres.");
                }
            }

            updateUIFromState();
        }
    }

    /**
     * Gère l'ajout d'une ligne à une matrice lorsque le bouton + Ligne est appuyé.
     */
    private class AddLineActionListener implements ActionListener {
        /**
         * Ajoute une ligne à la matrice sélectionnée.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            matrixGrid.addLine();
        }
    }

    /**
     * Gère l'ajout d'une colonne à une matrice lorsque le bouton + Colonne est appuyé.
     */
    private class AddColumnActionListener implements ActionListener {
        /**
         * Ajoute une ligne à la matrice sélectionnée.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            matrixGrid.addColumn();
        }
    }

    /**
     * Gère la suppression d'une ligne à une matrice lorsque le bouton - Ligne est appuyé.
     */
    private class RemoveLineActionListener implements ActionListener {
        /**
         * Supprime une ligne à la matrice sélectionnée.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            matrixGrid.removeLine();
        }
    }

    /**
     * Gère la suppression d'une colonne à une matrice.
     */
    private class RemoveColumnActionListener implements ActionListener {
        /**
         * Supprime une colonne à la matrice sélectionnée lorsque le bouton - Colonne est appuyé.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            matrixGrid.removeColumn();
        }
    }

    /**
     * Gère la transposée d'une matrice lorsque le bouton Transposée est appuyé.
     */
    private class TransposedActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (MatrixListener listener : listeners) {
                listener.matrixOperation(new MatrixTranspose(getSelectedMatrix()));
            }
        }
    }

    /**
     * Gère la multiplication de matrice par un nombre.
     */
    private class MultiplicatorKeyListener implements KeyListener {
        /**
         * Invoked when a key has been typed.
         * See the class description for {@link java.awt.event.KeyEvent} for a definition of
         * a key typed event.
         *
         * @param event
         */
        @Override
        public void keyTyped(KeyEvent event) {

        }

        /**
         * Gère la multiplication de matrice par un nombre et lance une erreur si le nombre entrée est invalide.
         *
         * @param event
         */
        @Override
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    double multiplier = Double.parseDouble(multiplicatorTextField.getText());

                    for (MatrixListener listener : listeners) {
                        listener.matrixOperation(new MatrixScalarMultiplication(getSelectedMatrix(), multiplier));
                    }
                } catch (NumberFormatException e) {
                    showError("Le multiplicateur doit être un nombre.");
                }
            }
        }

        /**
         * Invoked when a key has been released.
         * See the class description for {@link java.awt.event.KeyEvent} for a definition of
         * a key released event.
         *
         * @param event
         */
        @Override
        public void keyReleased(KeyEvent event) {

        }
    }
}
