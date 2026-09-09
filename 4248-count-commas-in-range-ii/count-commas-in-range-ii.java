class Solution {
    public long countCommas(long n) {
        long total = 0;
        long start = 1000;
        int commas = 1;

        while (start <= n) {
            long end = Math.min(n, start * 1000 - 1);

            total += (end - start + 1) * commas;

            start *= 1000;
            commas++;
        }

        return total;
    }
}