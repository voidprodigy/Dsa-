class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int pos = -1;
        
        // Find the index of 0
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                pos = i;
                break;
            }
        }
        
        // 1. Check if the array is a shifted ascending sequence
        boolean isAsc = true;
        for (int i = 0; i < n; i++) {
            if (nums[(pos + i) % n] != i) {
                isAsc = false;
                break;
            }
        }
        
        if (isAsc) {
            if (pos == 0) {
                return 0;
            }
            return Math.min(pos, n - pos + 2);
        }
        
        // 2. Check if the array is a shifted descending sequence
        boolean isDesc = true;
        for (int i = 0; i < n; i++) {
            if (nums[(pos - i + n) % n] != i) {
                isDesc = false;
                break;
            }
        }
        
        if (isDesc) {
            return Math.min(pos + 2, n - pos);
        }
        
        // 3. If neither, it's impossible to reach a sorted state
        return -1;
    }
}
