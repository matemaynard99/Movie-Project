

public class Linked_List<Movie_Class> implements Iterable<Movie_Class> {
    /** A doubly-linked list node */
    private class Node {
    	Movie_Class data;
        Node next, prev;
        Node(Movie_Class value) { data = value; }
    }

    // Data fields
    private Node head, tail;
    private int numOfItems;

    // Constructors

    public Linked_List() {}  // Default constructor

    // Methods

    /** Returns the size of the linked list.
        @return: the number of items stored in the linked list
    */
    public int size() { return numOfItems; }  // Time complexity: O(1)

    /** Tests whether the linked list is empty.
        @return: {true} if the list is empty; {false} otherwise
    */
    public boolean isEmpty() { return numOfItems == 0; }  // Time complexity: O(1)

    /** Returns the value stored in the head of the linked list.
        @throws Exception: list is empty.
        @return: the value stored in the head of the linked list
    */
    public Movie_Class getFirst() throws Exception {
        if (isEmpty()) { throw new Exception("Attempt to access item in empty list."); }
        return head.data;
    }  // Time complexity: O(1)

    /** Returns the value stored in the tail of the linked list.
        @throws Exception: list is empty.
        @return: the value stored in the tail of the linked list
    */
    public Movie_Class getLast() throws Exception {
        if (isEmpty()) { throw new Exception("Attempt to access item in empty list."); }
        return tail.data;
    }  // Time complexity: O(1)

    /** Adds a new item to the front of the linked list.
        @param item: the new item to be added to the list
    */
    public void addFirst(Movie_Class item) {
        if (numOfItems++ == 0) { head = tail = new Node(item); }
        else {
            Node newNode = new Node(item);
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }  // Time complexity: O(1)

    /** Adds a new item to the end of the linked list.
        @param item: the new item to be added to the list
    */
    public void addLast(Movie_Class item) {
        if (numOfItems++ == 0) { head = tail = new Node(item); }
        else {
            Node newNode = new Node(item);
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
    }  // Time complexity: O(1)

    /** Deletes the first node (head) from the linked list.
        @throws Exception: list is empty.
        @return: the item deleted
    */
    public Movie_Class removeFirst() throws Exception {
        if (isEmpty()) { throw new Exception("Attempted to delete item from empty list."); }
        Movie_Class deleted = head.data;
        if (numOfItems-- == 1) { head = tail = null; }
        else {
            head = head.next;
            head.prev = null;
        }
        return deleted;
    }  // Time complexity: O(1)

    /** Deletes the last node (tail) from the linked list.
        @throws Exception: list is empty.
        @return: the item deleted
    */
    public Movie_Class removeLast() throws Exception {
        if (isEmpty()) { throw new Exception("Attempted to delete item from empty list."); }
        Movie_Class deleted = tail.data;
        if (numOfItems-- == 1) { head = tail = null; }
        else {
            tail = tail.prev;
            tail.next = null;
        }
        return deleted;
    }  // Time complexity: O(1)

    /** Tests whether a target value appears in the linked list.
        @param target: the value to search
        @return: {true} if target found in the list; {false} otherwise
    */
    public boolean contains(Movie_Class target) {
        Node p = head;
        while (p != null) {
            if (p.data.equals(target)) { return true; }
            p = p.next;
        }
        return false;
    }  // Time complexity: O(n)

    /** Deletes all the items from the linked list. */
    public void clear() {
        head = tail = null;
        numOfItems = 0;
    }
    
    
    /** Generates a List_Iterator positioned at the beginning of the list.
    @return: a List_iterator positioned at the beginning of the list.
*/
@Override
public List_Iterator<Movie_Class> iterator() {
    return new List_Iterator<Movie_Class>() {
        // Data fields
        private Node nextNode = head, prevNode = null;

        /** Tests whether there is a next item at current iterator position.
            @return: {true} if current iterator is not at the end of the list; {false} otherwise.
        */
        @Override
        public boolean hasNext() { return nextNode != null; }

        /** Moves the iterator forward and returns the item passed by.
            @return: the next item at current iterator position
            @throws Exception: iterator has already reached the end of the list.
        */
        @Override
        public Movie_Class next() throws Exception {
            if (!hasNext()) { throw new Exception("Cannot move iterator forward."); }
            Movie_Class value = nextNode.data;
            prevNode = nextNode;
            nextNode = nextNode.next;
            return value;
        }

        /** Deletes the next item at current iterator position.
            @return: the item deleted
            @throws Exception: iterator has already reached the end of the list.
        */
        @Override
        public Movie_Class removeNext() throws Exception {
            if (!hasNext()) { throw new Exception("Attempt to remove null reference."); }
            Movie_Class value = nextNode.data;
            if (numOfItems-- == 1) { head = tail = prevNode = nextNode = null; }
            else if (prevNode == null) {  // The node to be removed is the head.
                nextNode = nextNode.next;
                nextNode.prev = null;
                head = nextNode;
            } else if (nextNode.next == null) {  // The node to be removed is the tail.
                nextNode = null;
                prevNode.next = null;
                tail = prevNode;
            } else {
                prevNode.next = nextNode.next;
                nextNode = nextNode.next;
                nextNode.prev = prevNode;
            }
            return value;
        }

        /** Tests whether there is a previous item at current iterator position.
            @return: {true} if current iterator is not at the beginning of the list; {false} otherwise.
        */
        @Override
        public boolean hasPrevious() { return prevNode != null; }

        /** Moves the iterator backward and returns the item passed by.
            @return: the previous item at current iterator position
            @throws Exception: iterator has already reached the beginning of the list.
        */
        @Override
        public Movie_Class previous() throws Exception {
            if (!hasPrevious()) { throw new Exception("Cannot move iterator backward."); }
            Movie_Class value = prevNode.data;
            nextNode = prevNode;
            prevNode = prevNode.prev;
            return value;
        }

        /** Deletes the previous item at current iterator position.
            @return: the item deleted
            @throws Exception: iterator has already reached the beginning of the list.
        */
        @Override
        public Movie_Class removePrevious() throws Exception {
            if (!hasPrevious()) { throw new Exception("Attempt to remove null reference."); }
            Movie_Class value = prevNode.data;
            if (numOfItems-- == 1) { head = tail = nextNode = prevNode = null; }
            else if (prevNode.prev == null) {  // The node to be removed is the head.
                prevNode = null;
                nextNode.prev = null;
                head = nextNode;
            } else if (nextNode == null) {  // The node to be removed is the tail.
                prevNode = prevNode.prev;
                prevNode.next = null;
                tail = prevNode;
            } else {
                nextNode.prev = prevNode.prev;
                prevNode = prevNode.prev;
                prevNode.next = nextNode;
            }
            return value;
        }

        /** Adds a new item at current iterator position.
            @param item: the item to add
        */
        @Override
        public void add(Movie_Class item) {
            if (isEmpty()) {
                head = tail = new Node(item);
                prevNode = head;
                numOfItems++;
            } else if (!hasPrevious()) {  // The new item will be the new head of the list.
                addFirst(item);
                prevNode = head;
                nextNode = head.next;
            } else if (!hasNext()) {  // The new item will be the new tail of the list.
                addLast(item);
                prevNode = tail;
                nextNode = null;
            } else {
                Node newNode = new Node(item);
                newNode.prev = prevNode;
                newNode.prev.next = newNode;
                newNode.next = nextNode;
                newNode.next.prev = newNode;
                prevNode = newNode;
                numOfItems++;
            }
        }

        /** Updates the value of the next item at current iterator position.
            @param item: the updated value
            @throws Exception: iterator has already reached the end of the list.
        */
        @Override
        public void setNext(Movie_Class item) throws Exception {
            if (!hasNext()) { throw new Exception("Attempt to set value for null reference."); }
            nextNode.data = item;
            next();  // Moves the iterator forward.
        }

        /** Updates the value of the previous item at current iterator position.
            @param item: the updated value
            @throws Exception: iterator has already reached the beginning of the list.
        */
        @Override
        public void setPrevious(Movie_Class item) throws Exception {
            if (!hasPrevious()) { throw new Exception("Attempt to set value for null reference."); }
            prevNode.data = item;
            previous();
        }
        
    };
}

}

