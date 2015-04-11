import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * TODO Fill this
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
public class MatrixEditor extends JPanel {

    private JTextArea initializationTextArea;
    private MatrixGrid matrixGrid = null;
    private JPanel newMatrixPanel;
    private JComboBox<Integer> newColumnNumberSelector;
    private JComboBox<Integer> newLineNumberSelector;
    private JButton confirmNewMatrixButton;
    private JLabel multiplicatorLabel;
    private JPanel matrixPanel;

    public enum EditorState {
        initialization, operation, editing, newMatrix
    }

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

    public MatrixEditor(ArrayList<NamedItem<IMatrice>> matrices) {
        this.matrices = matrices;

        setLayout(new GridLayout(3, 1));

        setupLoaderSection();
        setupMatrixSection();
        setupEditionSection();

        updateUIFromState();
    }

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
    }

    private void setupLoaderSection() {
        loaderSection = new JPanel();
        loaderSection.setLayout(new FlowLayout());

        matrixSelector = new JComboBox<NamedItem<IMatrice>>();
        loaderSection.add(matrixSelector);
        matrixSelector.setPreferredSize(new Dimension(120, matrixSelector.getPreferredSize().height));
        matrixSelector.addActionListener(new MatrixSelectorActionListener());
        for (NamedItem<IMatrice> namedMatrix : matrices) {
            matrixSelector.addItem(namedMatrix);
        }

        deleteButton = new JButton("Supprimer");
        loaderSection.add(deleteButton);
        deleteButton.addActionListener(new DeleteActionListener());

        add(loaderSection, 0);
    }

    private void setupMatrixSection() {
        matrixPanel = new JPanel();
        add(matrixPanel);

        initializationTextArea = new JTextArea();
        initializationTextArea.setText("Créez une nouvelle matrice en cliquant\n\r" +
                "sur le bouton [ Nouvelle ] ci-dessous ou\n\r" +
                "choisissez une matrice existante dans\n\r" +
                "la liste déroulante ci-dessus.");

        newMatrixPanel = new JPanel();
        newMatrixPanel.setLayout(new GridLayout(3, 2));
        newMatrixPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        newMatrixPanel.add(new JLabel("Nombre de lignes"));
        newColumnNumberSelector = new JComboBox<>();
        newMatrixPanel.add(newColumnNumberSelector);
        newLineNumberSelector = new JComboBox<>();
        newMatrixPanel.add(newLineNumberSelector);

        for (int i = 1; i <= 8; i++) {
            newColumnNumberSelector.addItem(i);
            newLineNumberSelector.addItem(i);
        }

        confirmNewMatrixButton = new JButton("OK");
        confirmNewMatrixButton.addActionListener(new confirmNewMatrixActionListener());
        newMatrixPanel.add(confirmNewMatrixButton);
    }

    private void setupEditionSection() {
        editionSection = new JPanel();
        editionSection.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        newButton = new JButton("Nouvelle");
        editionSection.add(newButton);
        newButton.addActionListener(new NewActionListener());

        editButton = new JButton("Edit");
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

        add(editionSection);
    }

    private class MatrixSelectorActionListener implements java.awt.event.ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DeleteActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class NewActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class EditActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class AddLineActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class AddColumnActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class RemoveLineActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class RemoveColumnActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TransposedActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class MultiplicatorKeyListener implements KeyListener {
        /**
         * Invoked when a key has been typed.
         * See the class description for {@link java.awt.event.KeyEvent} for a definition of
         * a key typed event.
         *
         * @param e
         */
        @Override
        public void keyTyped(KeyEvent e) {

        }

        /**
         * Invoked when a key has been pressed.
         * See the class description for {@link java.awt.event.KeyEvent} for a definition of
         * a key pressed event.
         *
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e) {

        }

        /**
         * Invoked when a key has been released.
         * See the class description for {@link java.awt.event.KeyEvent} for a definition of
         * a key released event.
         *
         * @param e
         */
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class confirmNewMatrixActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
