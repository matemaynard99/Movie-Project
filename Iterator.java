
/** A general iterator */
public interface Iterator<T> {
    boolean hasNext();
    T next() throws Exception;
    T removeNext() throws Exception;
}
