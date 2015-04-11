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

        setLoaderSection();
        setMatrixSection();
        setEditionSection();
    }

    private void setLoaderSection() {
        loaderSection = new JPanel();
        loaderSection.setLayout(new FlowLayout());

        matrixSelector = new JComboBox<NamedItem<IMatrice>>();
        loaderSection.add(matrixSelector);
        matrixSelector.addActionListener(new MatrixSelectorActionListener());

        deleteButton = new JButton("Supprimer");
        loaderSection.add(deleteButton);
        deleteButton.addActionListener(new DeleteActionListener());

        add(loaderSection);
    }

    private void setMatrixSection() {
        switch (state)
        {
            case editing:
                break;
            case initialization:
                break;
            case newMatrix:
                break;
            case operation:
                break;
        }
    }

    private void setEditionSection() {
        editionSection = new JPanel();
        editionSection.setLayout(new FlowLayout());

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

        editionSection.add(new JLabel("Mult. par"));

        multiplicatorTextField = new JTextField();
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
}
