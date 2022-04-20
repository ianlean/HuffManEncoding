
public class ArrayHeap {
    private int size;
    private HuffNode[] heap;
    private int count = -1;

    public ArrayHeap(int theSize) {
        this.size = theSize;
        heap = new HuffNode[size];
    }

    public void add(HuffNode temp) {
        count++;
        heap[count] = temp;
        heapify(temp, count);
    }
    // heapify method, private so other classes
    //do not use this method directly
    private void heapify (HuffNode temp, int theIndex) {
        // swaps the node at the current index with its parents until the
        // heap is in proper order
        while (theIndex > 0 && temp.compareTo(heap[getParent(theIndex)]) > 0) {
            swap(getParent(theIndex), theIndex);
            theIndex = getParent(theIndex);
        }
    }

    protected HuffNode removeMin() {
        if (isEmpty()) {
            throw new NullPointerException("Removing from empty heap");
        } else {
            //swaps element with last element
            HuffNode temp = heap[0];
            heap[0] = heap[count];
            //nullify the object, and reduce the count
            heap[count] = null;
            count--;
            // heapify every single node to make sure
            // the entire tree is in proper order
            for (int i = count; i > 0; i--) {
                heapify(heap[i], i);
            }
            return temp;
        }
    }
    // swaps two nodes, private because other classes
    // could harm the state with this method
    private void swap(int first, int second) {
        HuffNode temp;
        temp = heap[first];
        heap[first] = heap[second];
        heap[second] = temp;
    }

    public boolean isFull() {
        return heap.length == size;
    }

    public boolean isEmpty() {
        return count == -1;
    }

    public int getParent(int index) {
        return (index - 1) / 2;
    }

    public String toString() {
        return null;
    }

    protected HuffNode[] getHeap() {
        return heap;
    }

    public int getCount() {
        return this.count;
    }
}

