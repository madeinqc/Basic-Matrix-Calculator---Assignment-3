import java.io.*;
import java.util.ArrayList;

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
public class MatrixWriter {

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
