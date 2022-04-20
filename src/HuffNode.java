public class HuffNode implements Comparable {
    private char myChar;
    private int myFrequency;
    public HuffNode myLeft, myRight;

    public char getMyChar() {
        return myChar;
    }

    public void setMyChar(char myChar) {
        this.myChar = myChar;
    }

    public void setMyFrequency(int myFrequency) {
        this.myFrequency = myFrequency;
    }

    public HuffNode getMyLeft() {
        return myLeft;
    }

    public void setMyLeft(HuffNode myLeft) {
        this.myLeft = myLeft;
    }

    public HuffNode getMyRight() {
        return myRight;
    }

    public void setMyRight(HuffNode myRight) {
        this.myRight = myRight;
    }

    public HuffNode(char theChar, int theFrequency) {
        myChar = theChar;
        myFrequency = theFrequency;
    }

    public int getMyFrequency() {
        return myFrequency;
    }

    public int getParent(int index) {
        return (index - 1) / 2;
    }

    public String toString() {
        return myChar+""+myFrequency;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof HuffNode) {
            if (this.myFrequency < ((HuffNode) o).myFrequency) {
                return 1;
            }
        }
            return -1;
    }
}
