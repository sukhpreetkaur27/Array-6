import java.util.Arrays;
// LC 309
public class BuyAndSellStock_Cooldown {
    /**
     * explore all possible ways.
     * on each day, you're either allowed to buy or sell.
     * <p>
     * TC: O(2^n)
     * SC: O(n)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        return helper(prices, 0, true);
    }

    /*
    The max profit earned on day if you're allowed to buy or sell
     */
    private int helper(int[] prices, int day, boolean buy) {
        // base
        int n = prices.length;
        if(day >= n) {
            return 0;
        }
        if (day == n - 1) {
            if (buy) {
                return 0;
            } else {
                return prices[day];
            }
        }
        // logic
        int maxProfit = 0;
        if (buy) {
            // allowed to buy
            // buy == money out == -prices[day]
            int profitBuy = -prices[day] + helper(prices, day + 1, false);
            int profitNotBuy = helper(prices, day + 1, true);
            maxProfit = Math.max(profitBuy, profitNotBuy);
        } else {
            //not allowed to buy
            // sell == money in == prices[day]
            int profitSell = prices[day] + helper(prices, day + 2, true);
            ;
            int profitNotSell = helper(prices, day + 1, false);
            maxProfit = Math.max(profitSell, profitNotSell);
        }
        return maxProfit;
    }

    /**
     * Overlapping sub-problems == memoize
     * TC: O(2n)
     * SC: O(n) + O(2n)
     *
     * @param prices
     * @return
     */
    public int maxProfit_2(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        for (int[] i : dp) {
            Arrays.fill(i, -1);
        }
        return helper(prices, 0, 1, dp);
    }

    /*
    The max profit earned on day if you're allowed to buy or sell
     */
    private int helper(int[] prices, int day, int buy, int[][] dp) {
        // base
        int n = prices.length;
        if(day >= n) {
            return 0;
        }
        if (day == n - 1) {
            if (buy == 1) {
                return 0;
            } else {
                return prices[day];
            }
        }
        if (dp[day][buy] != -1) {
            return dp[day][buy];
        }
        // logic
        int maxProfit = 0;
        if (buy == 1) {
            // allowed to buy
            // buy == money out == -prices[day]
            int profitBuy = -prices[day] + helper(prices, day + 1, 0, dp);
            int profitNotBuy = helper(prices, day + 1, 1, dp);
            maxProfit = Math.max(profitBuy, profitNotBuy);
        } else {
            //not allowed to buy
            // sell == money in == prices[day]
            int profitSell = prices[day] + helper(prices, day + 2, 1, dp);
            ;
            int profitNotSell = helper(prices, day + 1, 0, dp);
            maxProfit = Math.max(profitSell, profitNotSell);
        }
        return dp[day][buy] = maxProfit;
    }

    /**
     * Remove aux space == tabulate
     * TC: O(2n)
     * SC: O(2n)
     *
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][2];
        // base
        dp[n - 1][1] = 0;
        dp[n - 1][0] = prices[n - 1];

        // logic
        for (int day = n - 2; day >= 0; day--) {
            int maxProfit = 0;
            for (int buy = 0; buy < 2; buy++) {
                if (buy == 1) {
                    // allowed to buy
                    // buy == money out == -prices[day]
                    int profitBuy = -prices[day] + dp[day + 1][0];
                    int profitNotBuy = dp[day + 1][1];
                    maxProfit = Math.max(profitBuy, profitNotBuy);
                } else {
                    //not allowed to buy
                    // sell == money in == prices[day]
                    int profitSell = prices[day] + dp[day + 2][1];
                    int profitNotSell = dp[day + 1][0];
                    maxProfit = Math.max(profitSell, profitNotSell);
                }
                dp[day][buy] = maxProfit;
            }
        }
        return dp[0][1];
    }

    /**
     * Space Optimization
     * TC: O(2n)
     * SC: O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit_4(int[] prices) {
        int n = prices.length;
        int[] prev = new int[2];
        int[] prev2 = new int[2];

        // base
        // dp[1] = 0;
        prev[0] = prices[n - 1];

        // logic
        for (int day = n - 2; day >= 0; day--) {
            int maxProfit = 0;
            int[] curr = new int[2];
            for (int buy = 0; buy < 2; buy++) {
                if (buy == 1) {
                    // allowed to buy
                    // buy == money out == -prices[day]
                    int profitBuy = -prices[day] + prev[0];
                    int profitNotBuy = prev[1];
                    maxProfit = Math.max(profitBuy, profitNotBuy);
                } else {
                    //not allowed to buy
                    // sell == money in == prices[day]
                    int profitSell = prices[day] + prev2[1];
                    int profitNotSell = prev[0];
                    maxProfit = Math.max(profitSell, profitNotSell);
                }
                curr[buy] = maxProfit;
            }
            prev2 = prev;
            prev = curr;
        }
        return prev[1];
    }

    /**
     * Space Optimization
     * TC: O(2n)
     * SC: O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit_5(int[] prices) {
        int n = prices.length;
        int[] prev = new int[2];
        int prev2 = 0;

        // base
        // dp[1] = 0;
        prev[0] = prices[n - 1];

        // logic
        for (int day = n - 2; day >= 0; day--) {
            int maxProfit = 0;
            int[] curr = new int[2];
            for (int buy = 0; buy < 2; buy++) {
                if (buy == 1) {
                    // allowed to buy
                    // buy == money out == -prices[day]
                    int profitBuy = -prices[day] + prev[0];
                    int profitNotBuy = prev[1];
                    maxProfit = Math.max(profitBuy, profitNotBuy);
                } else {
                    //not allowed to buy
                    // sell == money in == prices[day]
                    int profitSell = prices[day] + prev2;
                    int profitNotSell = prev[0];
                    maxProfit = Math.max(profitSell, profitNotSell);
                }
                curr[buy] = maxProfit;
            }
            prev2 = prev[1];
            prev = curr;
        }
        return prev[1];
    }
}
