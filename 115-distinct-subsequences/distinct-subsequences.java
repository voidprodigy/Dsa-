class Solution {
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();

        // dp[j] = number of ways to form first j characters of t
        long[] dp = new long[m + 1];

        // Empty string can always be formed in one way
        dp[0] = 1;

        for (int i = 0; i < n; i++) {
            // Traverse backwards to avoid overwriting values
            for (int j = m; j >= 1; j--) {
                if (s.charAt(i) == t.charAt(j - 1)) {
                    dp[j] += dp[j - 1];
                }
            }
        }

        return (int) dp[m];
    }
}