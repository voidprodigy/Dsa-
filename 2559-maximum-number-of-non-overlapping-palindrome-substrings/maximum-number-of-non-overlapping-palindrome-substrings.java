class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        char[] chars = s.toCharArray();
        
        // pal[i][j] will be true if the substring from index i to j is a palindrome
        boolean[][] pal = new boolean[n][n];
        
        // Precompute palindromes from bottom-up (shorter lengths to larger lengths)
        for (int i = n - 1; i >= 0; i--) {
            pal[i][i] = true;
            for (int j = i + 1; j < n; j++) {
                if (chars[i] == chars[j]) {
                    if (j - i == 1) {
                        pal[i][j] = true;
                    } else {
                        pal[i][j] = pal[i + 1][j - 1];
                    }
                }
            }
        }
        
        // dp[i] will store the max palindromes from the prefix of length i
        int[] dp = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            // Option 1: Do not include the character at index i-1
            dp[i] = dp[i - 1];
            
            // Option 2: Try forming a palindrome ending at index i-1 of length >= k
            // j is the starting index of the palindrome
            for (int j = 0; j <= i - k; j++) {
                if (pal[j][i - 1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        
        return dp[n];
    }
}