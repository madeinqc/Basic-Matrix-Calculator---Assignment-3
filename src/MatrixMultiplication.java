/**
 * TODO Faire les commentaires de la classe.
 * Created by madeinqc on 4/13/15.
 */
public class MatrixMultiplication implements MatrixOperation {

    private NamedItem<IMatrice> firstMatrix;
    private NamedItem<IMatrice> secondMatrix;

    private IMatrice result;
    private String name;

    public MatrixMultiplication(NamedItem<IMatrice> firstMatrix, NamedItem<IMatrice> secondMatrix) throws MatriceException{
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;

        result = firstMatrix.getItem().produit(secondMatrix.getItem());
        name = firstMatrix.getName() + " × " + secondMatrix.getName();
    }

    public IMatrice getResult() {
        return result;
    }

    public String getName() {
        return name;
    }
}
