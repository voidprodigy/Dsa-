
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] reward = new int[n];

        // Calculate reward for each item
        for (int i = 0; i < n; i++) {
            reward[i] = 1;

            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    reward[i]++;
                }
            }
        }

        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }

        Arrays.sort(order, (a, b) ->
            items[b][1] - items[a][1]
        );

        int NEG = -1000000000;
        int[] dp = new int[budget + 1];
        Arrays.fill(dp, NEG);
        dp[0] = 0;

        int ans = 0;
        int idx = 0;

        while (idx < n) {
            int price = items[order[idx]][1];

            int[] cur = new int[budget + 1];
            Arrays.fill(cur, NEG);

            int end = idx;

            while (end < n && items[order[end]][1] == price) {
                int id = order[end];

                for (int c = budget; c >= price; c--) {
                    if (dp[c - price] != NEG) {
                        cur[c] = Math.max(cur[c],
                            dp[c - price] + reward[id]);
                    }

                    if (cur[c - price] != NEG) {
                        cur[c] = Math.max(cur[c],
                            cur[c - price] + reward[id]);
                    }
                }
                end++;
            }

            for (int c = price; c <= budget; c++) {
                if (cur[c] != NEG) {
                    ans = Math.max(ans,
                        cur[c] + (budget - c) / price);
                }

                dp[c] = Math.max(dp[c], cur[c]);
            }

            idx = end;
        }

        return ans;
    }
}
