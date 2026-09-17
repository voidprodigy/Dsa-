class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = Integer.MAX_VALUE / 2;

        // best[i] = shortest target-sum subarray ending at or before i
        int[] best = new int[n];

        int left = 0;
        int sum = 0;
        int ans = INF;
        int minLen = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            // Since all elements are positive
            while (sum > target) {
                sum -= arr[left++];
            }

            if (sum == target) {
                int len = right - left + 1;

                // Best previous non-overlapping subarray
                if (left > 0 && best[left - 1] != INF) {
                    ans = Math.min(ans, len + best[left - 1]);
                }

                minLen = Math.min(minLen, len);
            }

            best[right] = minLen;
        }

        return ans == INF ? -1 : ans;
    }
}