import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * TODO Faire les commentaires de la classe.
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
public class MatrixParser {
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