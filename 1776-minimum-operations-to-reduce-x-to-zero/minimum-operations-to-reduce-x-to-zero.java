class Solution {
    public int minOperations(int[] nums, int x) {
        int n = nums.length;

        // Calculate total sum
        long total = 0;
        for (int num : nums) {
            total += num;
        }

        // Sum of the subarray we want to keep
        long target = total - x;

        // If target is negative, impossible
        if (target < 0) {
            return -1;
        }

        // If target is 0, remove the entire array
        if (target == 0) {
            return n;
        }

        int left = 0;
        long sum = 0;
        int maxLength = -1;

        // Sliding window
        for (int right = 0; right < n; right++) {
            sum += nums[right];

            while (sum > target && left <= right) {
                sum -= nums[left];
                left++;
            }

            if (sum == target) {
                maxLength = Math.max(maxLength, right - left + 1);
            }
        }

        // No valid subarray found
        if (maxLength == -1) {
            return -1;
        }

        return n - maxLength;
    }
}