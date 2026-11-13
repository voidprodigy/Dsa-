
class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        int LOG = 17;

        long[] pref = new long[n + 1];

        for (int i = 0; i < n; i++) {
            pref[i + 1] = pref[i] + nums[i];
        }

        // Find next greater element
        int[] next = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && nums[stack[top]] <= nums[i]) {
                top--;
            }

            next[i] = (top == -1) ? -1 : stack[top];
            stack[++top] = i;
        }

        // Binary lifting tables
        int[][] up = new int[LOG][n];
        long[][] cost = new long[LOG][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = next[i];

            if (next[i] != -1) {
                int j = next[i];
                cost[0][i] = (long)(j - i - 1) * nums[i]
                        - (pref[j] - pref[i + 1]);
            }
        }

        for (int p = 1; p < LOG; p++) {
            for (int i = 0; i < n; i++) {
                int mid = up[p - 1][i];

                if (mid != -1) {
                    up[p][i] = up[p - 1][mid];
                    cost[p][i] = cost[p - 1][i]
                            + cost[p - 1][mid];
                } else {
                    up[p][i] = -1;
                }
            }
        }

        // Calculate operations for subarray [l, r]
        long ans = 0;
        int right = -1;

        for (int left = 0; left < n; left++) {

            if (right < left) {
                right = left;
            }

            while (right + 1 < n &&
                   getCost(left, right + 1, nums, pref, up, cost, LOG) <= k) {
                right++;
            }

            ans += right - left + 1;
        }

        return ans;
    }

    private long getCost(int l, int r, int[] nums,
                         long[] pref, int[][] up,
                         long[][] cost, int LOG) {

        long ans = 0;
        int cur = l;

        for (int p = LOG - 1; p >= 0; p--) {
            int nxt = up[p][cur];

            if (nxt != -1 && nxt <= r) {
                ans += cost[p][cur];
                cur = nxt;
            }
        }

        // Remaining elements after the last greater element
        ans += (long)(r - cur) * nums[cur]
                - (pref[r + 1] - pref[cur + 1]);

        return ans;
    }
}
