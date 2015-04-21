import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


/**
 * Cette classe represente l'interface de calcul de matrices.
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
public class TP3 extends WindowAdapter implements MatrixListener {

    /************************************
     * CONSTANTES DE CLASSE
     ************************************/
    //largeur et hauteur de l'ecran de l'ordinateur
    public final static int LARG_ECRAN =
            Toolkit.getDefaultToolkit().getScreenSize().width;
    public final static int HAUT_ECRAN =
            Toolkit.getDefaultToolkit().getScreenSize().height;
    //largeur et hauteur de la fenetre principale
    public final static int LARG_FENETRE = 1000;
    public final static int HAUT_FENETRE = 630;

    //fichier d'enregistrement des matrices (À la racine du projet)
    public final static String FIC_MATRICES = "matrices.txt";

    //titre de la fenêtre principale
    public final static String TITRE_FENETRE = "CALCULATEUR MATRICES";


    //...


    /************************************
     * VARIABLES D'INSTANCE
     ************************************/
    //...
    ArrayList<NamedItem<IMatrice>> matrices;


    /************************************
     * COMPOSANTS GRAPHIQUES
     ************************************/
    //fenetre principale
    private JFrame fenetre;

    //...
    private MatrixEditor firstMatrixEditor;
    private JButton addMatricesButton;
    private JButton multiplicateMatricesButton;
    private MatrixEditor secondMatrixEditor;
    private MatrixResultPanel resultPanel;

    /**
     * Constructeur qui initialise l'application.
     */
    public TP3() {
        try {
            matrices = MatrixParser.parseFile(FIC_MATRICES);
        } catch (IOException e) {
            matrices = new ArrayList<>();
        }
        matrices.add(0, new NamedItem<IMatrice>("", null));
        init();
    }

    /**
     * Initialisation des composants graphiques.
     */
    private void init() {

        //FENETRE JFRAME
        fenetre = new JFrame(TITRE_FENETRE);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setResizable(false);
        //centrer la fenetre principale dans l'écran
        fenetre.setBounds(LARG_ECRAN / 2 - LARG_FENETRE / 2,
                HAUT_ECRAN / 2 - HAUT_FENETRE / 2,
                LARG_FENETRE, HAUT_FENETRE);


        //...
        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.setLayout(new BorderLayout(0, 10));
        fenetre.setContentPane(container);

        firstMatrixEditor = new MatrixEditor(matrices);
        firstMatrixEditor.setPreferredSize(new Dimension(460, 385));
        fenetre.getContentPane().add(firstMatrixEditor, BorderLayout.WEST);

        JPanel operationsPanel = new JPanel();
        operationsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        operationsPanel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.GRAY));
        operationsPanel.setPreferredSize(new Dimension(80, 385));
        fenetre.getContentPane().add(operationsPanel, BorderLayout.CENTER);

        JPanel addMatricesContainer = new JPanel();
        addMatricesContainer.setLayout(new BoxLayout(addMatricesContainer, BoxLayout.PAGE_AXIS));
        addMatricesContainer.setBorder(new EmptyBorder(120, 0, 0, 0));
        addMatricesButton = new JButton("+");
        addMatricesButton.setVerticalAlignment(JButton.CENTER);
        addMatricesButton.setPreferredSize(new Dimension(50, addMatricesButton.getPreferredSize().height));
        addMatricesButton.setEnabled(false);
        addMatricesButton.addActionListener(new AddMatricesListener());
        addMatricesContainer.add(addMatricesButton);
        addMatricesContainer.add(Box.createRigidArea(new Dimension(12, 20)));

        multiplicateMatricesButton = new JButton("×");
        multiplicateMatricesButton.setVerticalAlignment(JButton.CENTER);
        multiplicateMatricesButton.setPreferredSize(new Dimension(50, multiplicateMatricesButton.getPreferredSize().height));
        multiplicateMatricesButton.setEnabled(false);
        multiplicateMatricesButton.addActionListener(new MultiplicateMatricesListener());
        addMatricesContainer.add(multiplicateMatricesButton);
        operationsPanel.add(addMatricesContainer);

        secondMatrixEditor = new MatrixEditor(matrices);
        secondMatrixEditor.setPreferredSize(new Dimension(460, 385));
        fenetre.getContentPane().add(secondMatrixEditor, BorderLayout.EAST);

        resultPanel = new MatrixResultPanel();
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        resultPanel.setPreferredSize(new Dimension(1000, 245));
        resultPanel.addListener(firstMatrixEditor);
        fenetre.getContentPane().add(resultPanel, BorderLayout.SOUTH);

        firstMatrixEditor.addListener(secondMatrixEditor);
        secondMatrixEditor.addListener(firstMatrixEditor);
        firstMatrixEditor.addListener(this);
        secondMatrixEditor.addListener(this);


        //Laisser cette instruction à la fin de l'initialisation des composants
        //graphiques.
        fenetre.setVisible(true);


        /*************************
         * ÉCOUTEURS
         *************************/
        //ajout d'un ecouteur sur la fenetre
        fenetre.addWindowListener(this); //voir redef methode windowClosing

    }

    /**
     * A la fermeture de la fenetre, enregistrement des toutes les matrices
     * dans le fichier FIC_MATRICES.
     * @param e l'evenement de fermeture de fenetre.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        try {
            MatrixWriter.writeFile(FIC_MATRICES, firstMatrixEditor.getMatrices());
        } catch (IOException e1) {
            showError("Impossible d'enregistrer les matrices.");
        }
    }


    public static void main (String [] args) {
        new TP3();
    }

    @Override
    public void matrixAdded(NamedItem<IMatrice> namedMatrix) {

    }

    @Override
    public void matrixRemoved(NamedItem<IMatrice> namedMatrix) {

    }

    @Override
    public void saveMatrix(IMatrice matrix) {

    }

    @Override
    public void matrixOperation(MatrixOperation operation) {
        resultPanel.setOperation(operation);
        resultPanel.updateUIFromOperation();
    }

    @Override
    public void stateChanged(MatrixEditor.EditorState state) {
        if (firstMatrixEditor != null && secondMatrixEditor != null &&
                firstMatrixEditor.getState() == MatrixEditor.EditorState.operation &&
                secondMatrixEditor.getState() == MatrixEditor.EditorState.operation) {
            addMatricesButton.setEnabled(true);
            multiplicateMatricesButton.setEnabled(true);
        } else {
            addMatricesButton.setEnabled(false);
            multiplicateMatricesButton.setEnabled(false);
        }
    }

    private void showError(String error) {
        JOptionPane.showMessageDialog(fenetre, error, "ERREUR", JOptionPane.ERROR_MESSAGE);
    }

    private class AddMatricesListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param event
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                resultPanel.setOperation(new MatrixAddition(firstMatrixEditor.getSelectedMatrix(), secondMatrixEditor.getSelectedMatrix()));
                resultPanel.updateUIFromOperation();
            } catch (MatriceException e) {
                showError("Ces matrices ne peut pas être additionnées ensemble.");
            }
        }
    }

    private class MultiplicateMatricesListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param event
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                resultPanel.setOperation(new MatrixMultiplication(firstMatrixEditor.getSelectedMatrix(), secondMatrixEditor.getSelectedMatrix()));
                resultPanel.updateUIFromOperation();
            } catch (MatriceException e) {
                showError("Ces matrices ne peuvent pas être multipliées ensemble.");
            }
        }
    }
}

/**
 * Cette classe gère l'opération de type addition sur les matrices.
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
class MatrixAddition implements MatrixOperation {

    /************************************
     * VARIABLES D'INSTANCE
     ************************************/
    private NamedItem<IMatrice> firstMatrix;
    private NamedItem<IMatrice> secondMatrix;

    private IMatrice result;
    private String name;

    /**
     * Effectue l'addition de deux matrices.
     *
     * @param firstMatrix   la première matrice à additionner.
     * @param secondMatrix  la deuxième matrice à additionner
     * @throws MatriceException si :
     *                          - les deux matrices ne sont pas compatibles.
     */
    public MatrixAddition(NamedItem<IMatrice> firstMatrix, NamedItem<IMatrice> secondMatrix) throws MatriceException{
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;

        result = firstMatrix.getItem().somme(secondMatrix.getItem());
        name = firstMatrix.getName() + " + " + secondMatrix.getName();
    }

    /**
     * @return le résultat de l'addition par les matrices
     */
    public IMatrice getResult() {
        return result;
    }

    /**
     * @return le nom de la nouvelle matrice créée.
     */
    public String getName() {
        return name;
    }
}

/**
 * Cette classe gère la multiplication de deux matrices.
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
class MatrixMultiplication implements MatrixOperation {

    private NamedItem<IMatrice> firstMatrix;
    private NamedItem<IMatrice> secondMatrix;

    private IMatrice result;
    private String name;

    /**
     * Effectue la multiplication de deux matrices
     *
     * @param firstMatrix       la première matrice à multiplier.
     * @param secondMatrix      la deuxième matrice à multiplier.
     *
     * @throws MatriceException lorsque les deux matrices sont incompatibles.
     */
    public MatrixMultiplication(NamedItem<IMatrice> firstMatrix, NamedItem<IMatrice> secondMatrix) throws MatriceException{
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;

        result = firstMatrix.getItem().produit(secondMatrix.getItem());
        name = firstMatrix.getName() + " × " + secondMatrix.getName();
    }

    /**
     * Retourne le résultat de la multiplication par deux matrices.
     * @return le résultat de la multiplication par deux matrices.
     */
    public IMatrice getResult() {
        return result;
    }

    /**
     * Retourne le nom de la matrice.
     *
     * @return le nom de la matrice.
     */
    public String getName() {
        return name;
    }
}


/**
 * Cette classe gère la multiplication d'une matrice par un nombre réel.
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
class MatrixScalarMultiplication implements MatrixOperation {

    private NamedItem<IMatrice> firstMatrix;

    private IMatrice result;
    private String name;

    /**
     * Construit le résultat d'une multiplication par un nombre.
     *
     * @param firstMatrix   la matrice à multiplier par un nombre.
     * @param number        le nombre par lequel la matrice doit être multipliée.
     *
     * @throws MatriceException lorsque la matrice ne peut être multipliée par ce nombre.
     */
    public MatrixScalarMultiplication(NamedItem<IMatrice> firstMatrix, double number) throws MatriceException{
        this.firstMatrix = firstMatrix;

        result = firstMatrix.getItem().produit(number);
        name = firstMatrix.getName() + " × " + number;
    }

    /**
     * Retourne la nouvelle matrice.
     *
     * @return la nouvelle matrice.
     */
    public IMatrice getResult() {
        return result;
    }

    /**
     * Retourne le nom de la matrice.
     *
     * @return le nom de la matrice.
     */
    public String getName() {
        return name;
    }
}


/**
 * Cette classe gère la transposée d'une matrice.
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
class MatrixTranspose implements MatrixOperation {

    private NamedItem<IMatrice> firstMatrix;

    private IMatrice result;
    private String name;

    /**
     * Crée la transposée d'une matrice en fonction de celle-ci passée en paramètre.
     *
     * @param firstMatrix       la matrice sur laquelle on doit effectuer une transposée.
     * @throws MatriceException lorsque la matrice ne peut être transposée.
     */
    public MatrixTranspose(NamedItem<IMatrice> firstMatrix) throws MatriceException{
        this.firstMatrix = firstMatrix;

        result = firstMatrix.getItem().transposee();
        name = "T (" + firstMatrix.getName() + ")";
    }

    /**
     * Retourne le résultat obtenu par la matrice transposée.
     *
     * @return la matrice transposée.
     */
    public IMatrice getResult() {
        return result;
    }

    /**
     * Retourne le nom de la matrice transposée.
     *
     * @return le nom de la matrice transposée.
     */
    public String getName() {
        return name;
    }
}


/**
 * Interface permettant d'obtenir les éléments obtenus par une opération.
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
interface MatrixOperation {

    /**
     * Retourne une IMatrice obtenue par le résultat d'une opération.
     * @return une IMatrice
     */
    public IMatrice getResult();

    /**
     * Retourne le nom de la matrice obtenue.
     * @return le nom de la matrice.
     */
    public String getName();

}


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
class MatrixEditor extends JPanel implements MatrixListener {

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
            if (matrixGrid.getMatrix().getNumLignes() >= 8)
            {
                showError("La matrice ne peut avoir plus de 8 lignes.");
            } else {
                matrixGrid.addLine();
            }
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
            if (matrixGrid.getMatrix().getNumColonnes() >= 8)
            {
                showError("La matrice ne peut avoir plus de 8 colonnes.");
            } else {
                matrixGrid.addColumn();
            }
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
            if (matrixGrid.getMatrix().getNumLignes() <= 1)
            {
                showError("La matrice ne peut avoir moins d'une ligne.");
            } else {
                matrixGrid.removeLine();
            }
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
            if (matrixGrid.getMatrix().getNumColonnes() <= 1)
            {
                showError("La matrice ne peut avoir moins d'une colonne.");
            } else {
                matrixGrid.removeColumn();
            }
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
class MatrixGrid extends JPanel {

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
                if (state == MatrixEditor.EditorState.editing) {
                    textFields[i][j].setEnabled(true);
                    textFields[i][j].setBackground(Color.YELLOW);
                } else {
                    textFields[i][j].setEnabled(false);
                    textFields[i][j].setDisabledTextColor(Color.BLACK);
                    textFields[i][j].setBackground(Color.WHITE);
                }
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
interface MatrixListener {

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


/**
 * Cette classe lit le fichier qui contient les matrices sauvegardées et les insère dans une liste.
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
class MatrixParser {

    /**
     * Effectue la lecture du fichier de matrices et retourne sous forme d'ArrayList les matrices.
     *
     * @param fileName      le nom du fichier à lire.
     * @return              l'ArrayListe construite.
     *
     * @throws IOException  lorsque le fichier n'est pas trouvé, mal construit.
     */
    public static ArrayList<NamedItem<IMatrice>> parseFile(String fileName) throws IOException {
        ArrayList<NamedItem<IMatrice>> matrices = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] nameAndData = line.split(":");
                matrices.add(new NamedItem<IMatrice>(nameAndData[0], createMatrixFromString(nameAndData[1])));
            }
        }
        return matrices;
    }

    /**
     * Constuit une matrice à partir d'une chaine de caractères passée en paramètre.
     * @param matrixString  la chaine de caractère à traiter pour construire la matrice.
     *
     * @return              la matrice créée.
     */
    private static IMatrice createMatrixFromString(String matrixString) {
        String[] data = matrixString.split(";");
        int numLine = Integer.parseInt(data[0]);
        int numCol = Integer.parseInt(data[1]);
        int contentLenght = data.length - 2;
        double[] content = new double[contentLenght];
        for (int i = 0; i < contentLenght; i++) {
            content[i] = Double.parseDouble(data[i + 2]);
        }
        return new Matrice(numLine, numCol, content);
    }
}


/**
 * Cette classe gère la zone 4 qui affiche les résultats.
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
class MatrixResultPanel extends JPanel {

    private final JLabel matrixNameLabel;
    private final JTextArea matrixResultTextArea;
    private final JButton saveButton;
    private MatrixOperation operation;

    private ArrayList<MatrixListener> listeners = new ArrayList<>();

    /**
     * Retourne le mode d'opération actuel de la zone.
     * @return le mode d'opération actuel de la zone.
     */
    public MatrixOperation getOperation() {
        return operation;
    }

    /**
     * Ajoute un listener à la liste.
     *
     * @param listener le listener à ajouter à la liste,
     */
    public void addListener(MatrixListener listener) {
        listeners.add(listener);
    }

    /**
     * Enlève un listener de la liste.
     *
     * @param listener le listener à enlever.
     */
    public void removeListener(MatrixListener listener) {
        listeners.remove(listener);
    }

    /**
     * Modifie le mode d'opération de la zone en fonction de l'opération passée en paramètre.
     * @param operation     l'opération que doit prendre le mode d'opération.
     */
    public void setOperation(MatrixOperation operation) {
        this.operation = operation;
    }

    /**
     * Construit la zone Résultats et construit l'ArrayList en fonction de celle-ci passée en paramètre.
     */
    public MatrixResultPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        matrixNameLabel = new JLabel("",SwingConstants.RIGHT);
        matrixNameLabel.setPreferredSize(new Dimension(150, matrixNameLabel.getPreferredSize().height));
        matrixNameLabel.setVerticalAlignment(SwingConstants.NORTH);
        matrixNameLabel.setBorder(new EmptyBorder(20, 0, 0, 0));
        matrixNameLabel.setForeground(Color.darkGray);
        add(matrixNameLabel, BorderLayout.WEST);

        matrixResultTextArea = new JTextArea();
        matrixResultTextArea.setFont(new Font("Courier", Font.BOLD, 12));
        matrixResultTextArea.setForeground(Color.darkGray);
        matrixResultTextArea.setBorder(new EmptyBorder(10, 10, 10, 0));

        JScrollPane scroll = new JScrollPane(matrixResultTextArea);
        scroll.setPreferredSize(new Dimension(700, 10));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);

        saveButton = new JButton("Sauvegarder");
        saveButton.setPreferredSize(new Dimension(150, saveButton.getPreferredSize().height));
        JPanel saveButtonContainer = new JPanel();
        saveButtonContainer.setBackground(Color.WHITE);
        saveButtonContainer.setBorder(new EmptyBorder(15, 10, 10, 15));
        saveButtonContainer.add(saveButton);
        add(saveButtonContainer, BorderLayout.EAST);
        saveButton.setVisible(false);
        saveButton.addActionListener(new SaveActionListener());
    }

    /**
     * Ajuste l'UI en fonction du mode d'opération actuel.
     */
    public void updateUIFromOperation() {
        if (operation == null) {
            matrixNameLabel.setText("");
            matrixResultTextArea.setText("");
            saveButton.setVisible(false);
        } else {
            matrixNameLabel.setText(operation.getName() + " = ");
            matrixResultTextArea.setText(getPrettyTextMatrice(operation.getResult()));
            saveButton.setVisible(true);
        }
    }

    /**
     * Construit la matrice qui sera affichée en fonction de celle-ci passée en paramètre.
     *
     * @param result    la matrice passée en paramètre.
     *
     * @return          la string qui affichera le résultat de la matrice.
     */
    private String getPrettyTextMatrice(IMatrice result) {
        String[][] nums = new String[result.getNumLignes()][result.getNumColonnes()];
        int maxLength = 0;

        for (int i = 0; i < result.getNumLignes(); i++) {
            for (int j = 0; j < result.getNumColonnes(); j++) {
                nums[i][j] = String.valueOf(result.getElement(i, j));
                maxLength = Math.max(maxLength, nums[i][j].length());
            }
        }

        maxLength += 2;

        StringBuilder sb = new StringBuilder(" _  ");
        int lengthOfLine = maxLength * result.getNumColonnes();
        sb.append(String.format("%1$" + lengthOfLine + "s", " "));

        sb.append("_");
        sb.append(System.lineSeparator());

        for (int i = 0; i < result.getNumLignes(); i++) {
            sb.append("|");

            if (i == result.getNumLignes() - 1)
                sb.append("_  ");
            else
                sb.append("   ");

            for (int j = 0; j < result.getNumColonnes(); j++) {
                sb.append(String.format("%1$-" + maxLength + "s", nums[i][j]));
            }

            if (i == result.getNumLignes() - 1)
                sb.append("_");
            else
                sb.append(" ");

            sb.append("|");

            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    /**
     * Implémente l'action à prendre lorsque le bouton Sauvegarder est appuyé.
     */
    private class SaveActionListener implements ActionListener {
        /**
         * Gère l'action à prendre lorsque le bouton Sauvegarder est appuyé.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            for (MatrixListener listener : listeners) {
                listener.saveMatrix(operation.getResult());
                MatrixResultPanel.this.operation = null;
                MatrixResultPanel.this.updateUIFromOperation();
            }
        }
    }
}


/**
 * Cette classe effectue la sauvegarde des matrices dans un fichier texte.
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
class MatrixWriter {

    /**
     * Effectue la sauvegarde des matrices dans un fichier texte.
     *
     * @param fileName  le nom du fichier contenant les matrices.
     * @param matrices  toutes les matrices à sauvegarder.
     *
     * @throws IOException  lorsqu'une erreur de sauvegarde survient.
     */
    public static void writeFile(String fileName, ArrayList<NamedItem<IMatrice>> matrices) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (NamedItem<IMatrice> namedMatrix : matrices) {
                if (namedMatrix.getItem() != null) {
                    bufferedWriter.write(namedMatrix.getName());
                    bufferedWriter.write(":");
                    bufferedWriter.write(getStringFromMatrix(namedMatrix.getItem()));
                    bufferedWriter.newLine();
                }
            }
        }
    }

    /**
     * Retourne les informations d'une matrices sous forme souhaitée.
     *
     * @param matrix    la matrice que l'on doit transformer en format texte.
     *
     * @return          la matrice sous forme de texte à sauvegarder.
     */
    private static String getStringFromMatrix(IMatrice matrix) {
        StringBuilder s = new StringBuilder();

        s.append(matrix.getNumLignes());
        s.append(";");
        s.append(matrix.getNumColonnes());
        s.append(";");

        for (int i = 0; i < matrix.getNumLignes(); i++) {
            for (int j = 0; j < matrix.getNumColonnes(); j++) {
                s.append(matrix.getElement(i, j));
                s.append(";");
            }
        }

        s.deleteCharAt(s.length() - 1);

        return s.toString();
    }
}

/**
 *
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
class NamedItem<T> {

    private String name;
    private T item;

    /**
     * Get the name of the item.
     *
     * @return the name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the item.
     *
     * @param name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the item.
     *
     * @return the item.
     */
    public T getItem() {
        return item;
    }

    /**
     * Set the current item.
     *
     * @param item to set.
     */
    public void setItem(T item) {
        this.item = item;
    }

    public NamedItem(String name, T item) {
        this.name = name;
        this.item = item;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NamedItem)) return false;

        NamedItem namedItem = (NamedItem) o;

        if (item != null ? !item.equals(namedItem.item) : namedItem.item != null) return false;
        if (name != null ? !name.equals(namedItem.name) : namedItem.name != null) return false;

        return true;
    }
}
