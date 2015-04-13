/**
 * TODO Faire les commentaires de la classe.
 * Created by madeinqc on 4/13/15.
 */
public class MatrixTranspose implements MatrixOperation {

    private NamedItem<IMatrice> firstMatrix;

    private IMatrice result;
    private String name;

    public MatrixTranspose(NamedItem<IMatrice> firstMatrix) throws MatriceException{
        this.firstMatrix = firstMatrix;

        result = firstMatrix.getItem().transposee();
        name = "T (" + firstMatrix.getName() + ")";
    }

    public IMatrice getResult() {
        return result;
    }

    public String getName() {
        return name;
    }
}
