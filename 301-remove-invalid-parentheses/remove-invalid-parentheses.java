import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {

        // Find minimum removals
        int leftRemove = 0;
        int rightRemove = 0;

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                leftRemove++;
            } 
            else if (ch == ')') {
                if (leftRemove > 0) {
                    leftRemove--;
                } 
                else {
                    rightRemove++;
                }
            }
        }

        Set<String> result = new HashSet<>();

        backtrack(
            s, 0,
            0, 0,
            leftRemove, rightRemove,
            new StringBuilder(),
            result
        );

        return new ArrayList<>(result);
    }

    private void backtrack(
            String s,
            int index,
            int open,
            int close,
            int leftRemove,
            int rightRemove,
            StringBuilder current,
            Set<String> result) {

        // End of string
        if (index == s.length()) {

            if (leftRemove == 0 &&
                rightRemove == 0 &&
                open == close) {

                result.add(current.toString());
            }

            return;
        }

        char ch = s.charAt(index);

        // -------------------------
        // OPTION 1: REMOVE
        // -------------------------

        if (ch == '(' && leftRemove > 0) {
            backtrack(
                s,
                index + 1,
                open,
                close,
                leftRemove - 1,
                rightRemove,
                current,
                result
            );
        }

        if (ch == ')' && rightRemove > 0) {
            backtrack(
                s,
                index + 1,
                open,
                close,
                leftRemove,
                rightRemove - 1,
                current,
                result
            );
        }

        // -------------------------
        // OPTION 2: KEEP
        // -------------------------

        current.append(ch);

        if (ch == '(') {

            backtrack(
                s,
                index + 1,
                open + 1,
                close,
                leftRemove,
                rightRemove,
                current,
                result
            );

        } 
        else if (ch == ')') {

            // Keep ')' only if an '(' is available
            if (open > close) {

                backtrack(
                    s,
                    index + 1,
                    open,
                    close + 1,
                    leftRemove,
                    rightRemove,
                    current,
                    result
                );
            }

        } 
        else {

            // Letter
            backtrack(
                s,
                index + 1,
                open,
                close,
                leftRemove,
                rightRemove,
                current,
                result
            );
        }

        // Remove last character
        current.deleteCharAt(current.length() - 1);
    }
}