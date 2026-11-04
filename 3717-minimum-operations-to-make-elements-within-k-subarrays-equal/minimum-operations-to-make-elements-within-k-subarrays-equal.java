
import java.util.*;

class Solution {

    class Fenwick {
        long[] bit;
        int n;

        Fenwick(int n) {
            this.n = n;
            bit = new long[n + 1];
        }

        void update(int i, long val) {
            while (i <= n) {
                bit[i] += val;
                i += i & -i;
            }
        }

        long query(int i) {
            long sum = 0;
            while (i > 0) {
                sum += bit[i];
                i -= i & -i;
            }
            return sum;
        }

        int kth(long k) {
            int idx = 0;

            for (int step = Integer.highestOneBit(n);
                 step > 0; step >>= 1) {

                int next = idx + step;

                if (next <= n && bit[next] < k) {
                    idx = next;
                    k -= bit[next];
                }
            }

            return idx + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int[] unique = Arrays.stream(sorted).distinct().toArray();

        int m = unique.length;

        Fenwick count = new Fenwick(m);
        Fenwick sum = new Fenwick(m);

        long totalSum = 0;

        // Initialize first window
        for (int i = 0; i < x; i++) {
            int idx = Arrays.binarySearch(unique, nums[i]) + 1;

            count.update(idx, 1);
            sum.update(idx, nums[i]);

            totalSum += nums[i];
        }

        long[] cost = new long[n - x + 1];

        for (int i = 0; i <= n - x; i++) {

            // Find median
            int medianIdx = count.kth((x + 1) / 2);
            long median = unique[medianIdx - 1];

            long leftCount = count.query(medianIdx);
            long leftSum = sum.query(medianIdx);

            long rightCount = x - leftCount;
            long rightSum = totalSum - leftSum;

            cost[i] =
                median * leftCount - leftSum
                + rightSum - median * rightCount;

            if (i == n - x) break;

            // Remove outgoing element
            int outIdx = Arrays.binarySearch(unique, nums[i]) + 1;

            count.update(outIdx, -1);
            sum.update(outIdx, -nums[i]);

            totalSum -= nums[i];

            // Add incoming element
            int inIdx = Arrays.binarySearch(unique, nums[i + x]) + 1;

            count.update(inIdx, 1);
            sum.update(inIdx, nums[i + x]);

            totalSum += nums[i + x];
        }

        // Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];
        long[] curr = new long[n + 1];

        for (int j = 1; j <= k; j++) {

            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                curr[i] = curr[i - 1];

                if (i >= x && prev[i - x] != INF) {

                    curr[i] = Math.min(
                        curr[i],
                        prev[i - x] + cost[i - x]
                    );
                }
            }

            long[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }
}
