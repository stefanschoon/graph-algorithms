package utils;

public class UnionFind {

    private final int size;
    private final int[] parents;

    public UnionFind(int size) {
        this.size = size;
        this.parents = new int[size];
        for (int i = 0; i < size; i++) {
            this.parents[i] = i;
        }
    }

    public void unify(int source, int target) {
        int oldParent = getParent(target);
        for (int i = 0; i < this.size; i++) {
            if (this.parents[i] == oldParent) {
                this.parents[i] = getParent(source);
            }
        }
    }

    public int getParent(int source) {
        return this.parents[source];
    }
}
