import java.util.*;

class Solution {
    
    // A simple helper class to represent our DP state
    private class State {
        long weight;
        List<Integer> indices;

        State(long weight, List<Integer> indices) {
            this.weight = weight;
            this.indices = indices;
        }
    }

    // Notice the updated parameter type here
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        
        // Enhance intervals with their original indices: [start, end, weight, original_index]
        int[][] arr = new int[n][4];
        for (int i = 0; i < n; i++) {
            List<Integer> interval = intervals.get(i);
            arr[i][0] = interval.get(0);
            arr[i][1] = interval.get(1);
            arr[i][2] = interval.get(2);
            arr[i][3] = i;
        }
        
        // Sort intervals based on their start times
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Precompute the index of the next non-overlapping interval for each interval
        int[] nextIdx = new int[n];
        for (int i = 0; i < n; i++) {
            nextIdx[i] = findNext(arr, arr[i][1], i + 1);
        }
        
        // dp[i][k] stores the best state starting from interval i, choosing exactly k intervals
        State[][] dp = new State[n + 1][5];
        
        // Reusable singleton states to save memory
        State INVALID = new State(-1, Collections.emptyList());
        State EMPTY = new State(0, Collections.emptyList());
        
        // Initialize base cases
        for (int i = 0; i <= n; i++) {
            for (int k = 1; k <= 4; k++) {
                dp[i][k] = INVALID;
            }
            dp[i][0] = EMPTY; // 0 intervals chosen yields 0 weight
        }
        
        // Fill DP table from right to left
        for (int i = n - 1; i >= 0; i--) {
            for (int k = 1; k <= 4; k++) {
                
                // Option 1: Skip the current interval
                State skipState = dp[i + 1][k];
                
                // Option 2: Take the current interval
                int nxt = nextIdx[i];
                State nextBest = dp[nxt][k - 1];
                
                State takeState = INVALID;
                if (nextBest.weight != -1) {
                    long newWeight = arr[i][2] + nextBest.weight;
                    List<Integer> newList = new ArrayList<>(nextBest.indices);
                    newList.add(arr[i][3]);
                    Collections.sort(newList); // Keep indices sorted for lexicographical comparison
                    
                    takeState = new State(newWeight, newList);
                }
                
                // Keep whichever choice is better
                if (isBetter(skipState, takeState)) {
                    dp[i][k] = skipState;
                } else {
                    dp[i][k] = takeState;
                }
            }
        }
        
        // Evaluate configurations of size 1 to 4 starting from index 0
        State bestResult = INVALID;
        for (int k = 1; k <= 4; k++) {
            if (isBetter(dp[0][k], bestResult)) {
                bestResult = dp[0][k];
            }
        }
        
        // Convert the best list to a primitive int array
        int[] result = new int[bestResult.indices.size()];
        for (int i = 0; i < bestResult.indices.size(); i++) {
            result[i] = bestResult.indices.get(i);
        }
        
        return result;
    }
    
    // Binary search to find the first interval that starts strictly after targetEnd
    private int findNext(int[][] arr, int targetEnd, int startIdx) {
        int left = startIdx;
        int right = arr.length - 1;
        int res = arr.length;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid][0] > targetEnd) {
                res = mid;
                right = mid - 1; // Try to find an earlier valid one
            } else {
                left = mid + 1;
            }
        }
        return res;
    }
    
    // Returns true if state s1 is strictly better than state s2
    private boolean isBetter(State s1, State s2) {
        // 1. Compare by total weight
        if (s1.weight != s2.weight) {
            return s1.weight > s2.weight;
        }
        
        // 2. If weights are equal, tie-break by choosing the lexicographically smaller list
        List<Integer> l1 = s1.indices;
        List<Integer> l2 = s2.indices;
        
        for (int i = 0; i < Math.min(l1.size(), l2.size()); i++) {
            int val1 = l1.get(i);
            int val2 = l2.get(i);
            if (val1 != val2) {
                return val1 < val2; 
            }
        }
        
        // If all compared elements match, the shorter list is lexicographically smaller
        return l1.size() < l2.size();
    }
}