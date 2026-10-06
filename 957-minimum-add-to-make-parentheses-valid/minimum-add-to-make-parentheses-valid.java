class Solution {
    public int minAddToMakeValid(String s) {
        int open = 0;
        int ans = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                open++;
            } else {
                if (open > 0) {
                    open--;
                } else {
                    // Need to insert '(' before ')'
                    ans++;
                }
            }
        }

        // Insert ')' for remaining '('
        ans += open;

        return ans;
    }
}