class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;

        // dp = number of distinct subsequences including empty subsequence
        long dp = 1;

        // last[c] stores dp value before the previous occurrence of c
        long[] last = new long[26];

        for (char c : s.toCharArray()) {
            int idx = c - 'a';

            long newDp = (2 * dp % MOD - last[idx] + MOD) % MOD;

            // Save current dp before updating
            last[idx] = dp;
            dp = newDp;
        }

        // Remove the empty subsequence
        return (int) ((dp - 1 + MOD) % MOD);
    }
}