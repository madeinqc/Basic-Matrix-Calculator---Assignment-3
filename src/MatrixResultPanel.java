import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * TODO Faire les commentaires de la classe.
 * Created by madeinqc on 4/13/15.
 */
public class MatrixResultPanel extends JPanel {

    private final JLabel matrixNameLabel;
    private final JTextArea matrixResultTextArea;
    private final JButton saveButton;
    private MatrixOperation operation;

    private ArrayList<MatrixListener> listeners = new ArrayList<>();

    public MatrixOperation getOperation() {
        return operation;
    }

    public void addListener(MatrixListener listener) {
        listeners.add(listener);
    }

    public void removeListener(MatrixListener listener) {
        listeners.remove(listener);
    }

    public void setOperation(MatrixOperation operation) {
        this.operation = operation;
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
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

    private class SaveActionListener implements ActionListener {
        /**
         * Invoked when an action occurs.
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
