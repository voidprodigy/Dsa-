class Solution {
    public boolean checkValidString(String s) {

        int low = 0;
        int high = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                low++;
                high++;
            } 
            else if (ch == ')') {
                low--;
                high--;
            } 
            else { // '*'
                low--;
                high++;
            }

            // Minimum cannot be negative
            low = Math.max(0, low);

            // Even maximum is negative -> impossible
            if (high < 0) {
                return false;
            }
        }

        return low == 0;
    }
}