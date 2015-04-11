import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import javax.swing.JFrame;


/**
 * Cette classe represente l'interface de calcule de matrices.
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
public class TP3 extends WindowAdapter implements ActionListener {

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


    /**
     * Constructeur qui initialise l'application.
     */
    public TP3() {
        try {
            matrices = MatriceParser.parseFile("matrices.txt");
        } catch (IOException e) {
            matrices = new ArrayList<>();
        }
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
        //centrer la fenetre principale dans l'Ã©cran
        fenetre.setBounds(LARG_ECRAN / 2 - LARG_FENETRE / 2,
                HAUT_ECRAN / 2 - HAUT_FENETRE / 2,
                LARG_FENETRE, HAUT_FENETRE);


        //...



        //Laisser cette instruction à la fin de l'initialisation des composants
        //graphiques.
        fenetre.setVisible(true);


        /*************************
         * ÉCOUTEURS
         *************************/
        //ajout d'un ecouteur sur la fenetre
        fenetre.addWindowListener(this); //voir redef methode windowClosing

    }

    @Override
    public void actionPerformed (ActionEvent e) {

        //TODO...

    }

    /**
     * A la fermeture de la fenetre, enregistrement des toutes les matrices
     * dans le fichier FIC_MATRICES.
     * @param e l'evenement de fermeture de fenetre.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        //TODO...
    }


    public static void main (String [] args) {
        new TP3();
    }

}