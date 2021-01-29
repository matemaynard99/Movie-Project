/** A list iterator */
public interface List_Iterator<T> extends Iterator<T> {
    boolean hasPrevious();
    T previous() throws Exception;
    T removePrevious() throws Exception;
    void add(T item);
    void setNext(T item) throws Exception;
    void setPrevious(T item) throws Exception;
}
