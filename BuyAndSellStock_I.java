// LC 121
public class BuyAndSellStock_I {
    /**
     * For each CP, find the profit per future SP
     * <p>
     * TC: O(n^2)
     * SC: O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit_brute(int[] prices) {
        int n = prices.length;
        int maxprofit = 0;
        for (int i = 0, cp = 0; i < n - 1; i++) {
            cp = prices[i];
            for (int j = i + 1, profit = 0; i < n; i++) {
                profit = prices[j] - cp;
                maxprofit = Math.max(profit, maxprofit);
            }
        }
        return Math.max(maxprofit, 0);
    }

    /**
     * Think greedy -> consider the lowest possible CP, and the future prices as SP.
     * The profit at each step can update the max profit.
     * <p>
     * TC: O(n)
     * SC: O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit_greedy(int[] prices) {
        int cp = prices[0];
        int n = prices.length;
        int maxprofit = 0;
        for (int i = 1, profit = 0; i < n; i++) {
            profit = prices[i] - cp;
            maxprofit = Math.max(profit, maxprofit);
            cp = Math.min(cp, prices[i]);
        }
        return Math.max(maxprofit, 0);
    }
}
