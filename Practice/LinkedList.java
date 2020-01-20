import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class LinkedList.
 * @author V.Pavan Kumar
 */
public class LinkedList<Item> implements Iterable<Item> {
    /**
     * Front Node.
     */
    private Node front;
    /**
     * Count of number of items.
     */
    private int itemsCount;

    /**
     * @return iterable object
     */
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item> {
        private Node current = front;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            final Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * Class Node.
     */
    private class Node {
        
        /**
         * Next Node.
         */
        private Node next;
        /**
         * Item to be stored.
         */
        private Item item;

        /**
         * No-Argument Constructor for initializing instance variables.
         */
        public Node() {

        }

        /**
         * Argument Constructor for initializing instance variables.
         */
        public Node(final Item itm) {
            this.item = itm;
        }
    }

    /**
     * No-Argument Constructor for initalizing instance variables.
     */
    public LinkedList() {

    }

    /**
     * @return boolean value based on count of items in LinkedList
     */
    public boolean isEmpty() {
        return itemsCount == 0;
    }

    /**
     * @return count of number of items present in LinkedList
     */
    public int size() {
        return itemsCount;
    }

    /**
     * @param item item to be added into LinkedList
     */
    public void add(final Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        final Node node = new Node(item);
        if (isEmpty()) {
            front = node;
            itemsCount = itemsCount + 1;
            return;
        }

        final Node oldFront = front;
        front = node;
        front.next = oldFront;
        itemsCount = itemsCount + 1;
    }

    public static void main(final String[] args) {
        final LinkedList<Integer> linkedListObj = new LinkedList<>();
        linkedListObj.add(10);
        System.out.println("COUNT : " + linkedListObj.itemsCount);
    }
}
