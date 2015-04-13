/**
 * TODO Faire les commentaires de la classe.
 * Created by madeinqc on 4/13/15.
 */
public class MatrixScalarMultiplication implements MatrixOperation {

    private NamedItem<IMatrice> firstMatrix;

    private IMatrice result;
    private String name;

    public MatrixScalarMultiplication(NamedItem<IMatrice> firstMatrix, double number) throws MatriceException{
        this.firstMatrix = firstMatrix;

        result = firstMatrix.getItem().produit(number);
        name = firstMatrix.getName() + " × " + number;
    }

    public IMatrice getResult() {
        return result;
    }

    public String getName() {
        return name;
    }
}
