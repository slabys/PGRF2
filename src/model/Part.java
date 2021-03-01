package model;

public class Part {
    private TopologyType type;
    private int startIndex;
    private int count;

    public TopologyType getType() {
        return type;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getCount() {
        return count;
    }

    public Part(TopologyType type, int startIndex, int count) {
        this.type = type;
        this.startIndex = startIndex;
        this.count = count;
    }
}
