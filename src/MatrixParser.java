import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
public class MatrixParser {

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