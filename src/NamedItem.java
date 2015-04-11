/**
 * Created by madeinqc on 4/11/15.
 */
public class NamedItem<T> {

    private String name;
    private T item;

    /**
     * Get the name of the item.
     *
     * @return the name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the item.
     *
     * @param name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the item.
     *
     * @return the item.
     */
    public T getItem() {
        return item;
    }

    /**
     * Set the current item.
     *
     * @param item to set.
     */
    public void setItem(T item) {
        this.item = item;
    }

    public NamedItem(String name, T item) {
        this.name = name;
        this.item = item;
    }

    @Override
    public String toString() {
        return name;
    }
}
