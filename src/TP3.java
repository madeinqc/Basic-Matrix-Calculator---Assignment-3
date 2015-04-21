import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
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