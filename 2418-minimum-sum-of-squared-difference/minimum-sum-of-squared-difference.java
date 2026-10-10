
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2,
                                 int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];

        long total = 0;
        int maxDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            total += diff[i];
            maxDiff = Math.max(maxDiff, diff[i]);
        }

        long k = (long) k1 + k2;

        if (k >= total) {
            return 0;
        }

        // Find the minimum achievable maximum difference.
        int left = 0, right = maxDiff;

        while (left < right) {
            int mid = left + (right - left) / 2;
            long required = 0;

            for (int d : diff) {
                if (d > mid) {
                    required += d - mid;
                }
            }

            if (required <= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        int x = left;
        long used = 0;
        long answer = 0;

        // Reduce every difference above x down to x.
        for (int d : diff) {
            int reduced = Math.min(d, x);
            answer += (long) reduced * reduced;

            if (d > x) {
                used += d - x;
            }
        }

        // Use leftover operations to reduce x to x - 1.
        long remaining = k - used;

        for (int d : diff) {
            if (remaining == 0) {
                break;
            }

            if (d >= x && x > 0) {
                answer -= (long) (2 * x - 1);
                remaining--;
            }
        }

        return answer;
    }
}
