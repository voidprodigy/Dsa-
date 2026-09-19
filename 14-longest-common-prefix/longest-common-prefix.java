class Solution {

    public String longestCommonPrefix(String[] strs) {

        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {

            String current = strs[i];

            int j = 0;

            // Compare characters of prefix and current string
            while (j < prefix.length()
                    && j < current.length()
                    && prefix.charAt(j) == current.charAt(j)) {

                j++;
            }

            // Keep only the common part
            prefix = prefix.substring(0, j);

            // No common prefix
            if (prefix.length() == 0) {
                return "";
            }
        }

        return prefix;
    }
}