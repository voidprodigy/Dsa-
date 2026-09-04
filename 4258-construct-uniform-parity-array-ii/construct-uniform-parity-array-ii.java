class Solution {
    public boolean uniformArray(int[] nums1) {
        int minOdd = Integer.MAX_VALUE;
        int minEven = Integer.MAX_VALUE;

        for (int num : nums1) {
            if (num % 2 == 0) {
                minEven = Math.min(minEven, num);
            } else {
                minOdd = Math.min(minOdd, num);
            }
        }

        // All elements are even
        if (minOdd == Integer.MAX_VALUE) {
            return true;
        }

        // All elements are odd
        if (minEven == Integer.MAX_VALUE) {
            return true;
        }

        // Both parities exist.
        // We can make everything odd if the smallest odd
        // number is smaller than every even number.
        return minOdd < minEven;
    }
}