import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by madeinqc on 4/9/15.
 */
public class MatriceParser {
    public static ArrayList<AbstractMap.SimpleEntry<String, IMatrice>> parseFile(String fileName) throws IOException {
        ArrayList<AbstractMap.SimpleEntry<String, IMatrice>> matrices = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] nameAndData = line.split(":");
                matrices.add(new AbstractMap.SimpleEntry<>(nameAndData[0], createMatrixFromString(nameAndData[1])));
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