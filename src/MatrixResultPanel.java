import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
public class MatrixResultPanel extends JPanel {

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
            }
        }
    }
}
