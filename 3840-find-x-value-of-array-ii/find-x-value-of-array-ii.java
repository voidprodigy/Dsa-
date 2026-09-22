class Solution {

    int n, k;
    int[] nums;
    Node[] tree;

    static class Node {
        int product;
        int[] pref;
        int[] suff;
        int[] sub;

        Node(int k) {
            pref = new int[k];
            suff = new int[k];
            sub = new int[k];
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        this.nums = nums;
        this.n = nums.length;
        this.k = k;

        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Update persists for all future queries
            nums[index] = value;
            update(1, 0, n - 1, index, value);

            // Get information about nums[start ... n-1]
            Node res = query(1, 0, n - 1, start, n - 1);

            // IMPORTANT:
            // Remaining array must be a PREFIX,
            // because we remove a suffix.
            result[i] = res.pref[x];
        }

        return result;
    }

    // Build segment tree
    void build(int node, int left, int right) {

        if (left == right) {
            tree[node] = createLeaf(nums[left]);
            return;
        }

        int mid = left + (right - left) / 2;

        build(node * 2, left, mid);
        build(node * 2 + 1, mid + 1, right);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Create leaf
    Node createLeaf(int value) {

        Node node = new Node(k);

        int rem = value % k;

        node.product = rem;

        // Only prefix is the element itself
        node.pref[rem] = 1;

        // Only suffix is the element itself
        node.suff[rem] = 1;

        // Only subarray is the element itself
        node.sub[rem] = 1;

        return node;
    }

    // Point update
    void update(int node, int left, int right,
                int index, int value) {

        if (left == right) {
            tree[node] = createLeaf(value);
            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, right, index, value);
        }

        tree[node] = merge(tree[node * 2],
                           tree[node * 2 + 1]);
    }

    // Range query
    Node query(int node, int left, int right,
               int ql, int qr) {

        if (ql <= left && right <= qr) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        if (qr <= mid) {
            return query(node * 2, left, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, right, ql, qr);
        }

        Node leftNode =
                query(node * 2, left, mid, ql, qr);

        Node rightNode =
                query(node * 2 + 1, mid + 1, right, ql, qr);

        return merge(leftNode, rightNode);
    }

    // Merge two nodes
    Node merge(Node left, Node right) {

        Node res = new Node(k);

        // Product of complete segment
        res.product =
                (int) ((long) left.product *
                       right.product % k);

        // --------------------------------
        // PREFIX
        // --------------------------------

        // Prefix completely inside left
        for (int r = 0; r < k; r++) {
            res.pref[r] += left.pref[r];
        }

        // Prefix = entire left + prefix of right
        for (int r = 0; r < k; r++) {

            if (right.pref[r] == 0)
                continue;

            int rem =
                    (int) ((long) left.product *
                           r % k);

            res.pref[rem] += right.pref[r];
        }

        // --------------------------------
        // SUFFIX
        // --------------------------------

        // Suffix completely inside right
        for (int r = 0; r < k; r++) {
            res.suff[r] += right.suff[r];
        }

        // Suffix = suffix of left + entire right
        for (int r = 0; r < k; r++) {

            if (left.suff[r] == 0)
                continue;

            int rem =
                    (int) ((long) r *
                           right.product % k);

            res.suff[rem] += left.suff[r];
        }

        // --------------------------------
        // SUBARRAY
        // --------------------------------

        // Subarrays completely inside left
        for (int r = 0; r < k; r++) {
            res.sub[r] += left.sub[r];
        }

        // Subarrays completely inside right
        for (int r = 0; r < k; r++) {
            res.sub[r] += right.sub[r];
        }

        // Subarrays crossing the boundary
        for (int a = 0; a < k; a++) {

            if (left.suff[a] == 0)
                continue;

            for (int b = 0; b < k; b++) {

                if (right.pref[b] == 0)
                    continue;

                int rem =
                        (int) ((long) a * b % k);

                res.sub[rem] +=
                        left.suff[a] *
                        right.pref[b];
            }
        }

        return res;
    }
}