import java.io.*;
import java.util.ArrayList;

/**
 * TODO Faire les commentaires de la classe.
 * Created by madeinqc on 4/11/15.
 */
public class MatrixWriter {
    public static void writeFile(String fileName, ArrayList<NamedItem<IMatrice>> matrices) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (NamedItem<IMatrice> namedMatrix : matrices) {
                System.out.println(namedMatrix.getName());
                if (namedMatrix.getItem() != null) {
                    bufferedWriter.write(namedMatrix.getName());
                    bufferedWriter.write(":");
                    bufferedWriter.write(getStringFromMatrix(namedMatrix.getItem()));
                    bufferedWriter.newLine();
                }
            }
        }
    }

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
