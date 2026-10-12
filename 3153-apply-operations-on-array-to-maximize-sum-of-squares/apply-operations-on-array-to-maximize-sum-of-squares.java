
import java.util.*;

class Solution {
    public int maxSum(List<Integer> nums, int k) {
        int MOD = 1_000_000_007;
        int[] bitCount = new int[32];

        for (int num : nums) {
            for (int bit = 0; bit < 32; bit++) {
                if ((num & (1 << bit)) != 0) {
                    bitCount[bit]++;
                }
            }
        }

        long ans = 0;

        for (int i = 0; i < k; i++) {
            long value = 0;

            for (int bit = 0; bit < 32; bit++) {
                if (bitCount[bit] > 0) {
                    value |= (1L << bit);
                    bitCount[bit]--;
                }
            }

            ans = (ans + value * value % MOD) % MOD;
        }

        return (int) ans;
    }
}
