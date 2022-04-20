public class PriorityQueue extends ArrayHeap {

    public PriorityQueue(int length) {
        super(length);
    }

    public void addElement(HuffNode huffy) {
        add(huffy);
    }

    public HuffNode removeNext() {
        HuffNode temp = removeMin();
        return temp;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public HuffNode getRoot() {
        return getHeap()[0];    }
}
