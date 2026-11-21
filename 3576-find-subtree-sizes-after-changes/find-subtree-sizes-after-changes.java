
class Solution {
    int[] ans;
    ArrayList<Integer>[] tree;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        tree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        // Build the original tree
        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        // Store the closest ancestor for each character
        int[] newParent = parent.clone();
        int[] last = new int[26];
        Arrays.fill(last, -1);

        dfs(0, last, newParent, s);

        // Build the modified tree
        tree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[newParent[i]].add(i);
        }

        // Calculate subtree sizes
        ans = new int[n];
        dfsSize(0);

        return ans;
    }

    void dfs(int node, int[] last, int[] newParent, String s) {
        int ch = s.charAt(node) - 'a';
        int old = last[ch];

        if (node != 0 && old != -1) {
            newParent[node] = old;
        }

        last[ch] = node;

        for (int child : tree[node]) {
            dfs(child, last, newParent, s);
        }

        last[ch] = old;
    }

    int dfsSize(int node) {
        int size = 1;

        for (int child : tree[node]) {
            size += dfsSize(child);
        }

        ans[node] = size;
        return size;
    }
}
