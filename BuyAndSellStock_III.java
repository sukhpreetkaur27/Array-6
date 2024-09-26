import java.util.Arrays;

// LC 123
public class BuyAndSellStock_III {

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
        return helper(prices, 0, true, 0);
    }

    /*
    The max profit earned on day if you're allowed to buy or sell
     */
    private int helper(int[] prices, int day, boolean buy, int txnCount) {
        // base
        if (txnCount == 2) {
            return 0;
        }
        if (day == prices.length - 1) {
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
            int profitBuy = -prices[day] + helper(prices, day + 1, false, txnCount);
            int profitNotBuy = helper(prices, day + 1, true, txnCount);
            maxProfit = Math.max(profitBuy, profitNotBuy);
        } else {
            //not allowed to buy
            // sell == money in == prices[day]
            int profitSell = prices[day] + helper(prices, day + 1, true, txnCount + 1);
            ;
            int profitNotSell = helper(prices, day + 1, false, txnCount);
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
        int[][][] dp = new int[n][2][2];
        for (int[][] i : dp) {
            for (int[] j : i) {
                Arrays.fill(j, -1);
            }
        }
        return helper(prices, 0, 1, dp, 0);
    }

    /*
    The max profit earned on day if you're allowed to buy or sell
     */
    private int helper(int[] prices, int day, int buy, int[][][] dp, int txnCount) {
        // base
        if (txnCount == 2) {
            return 0;
        }
        if (day == prices.length - 1) {
            if (buy == 1) {
                return 0;
            } else {
                return prices[day];
            }
        }
        if (dp[day][buy][txnCount] != -1) {
            return dp[day][buy][txnCount];
        }
        // logic
        int maxProfit = 0;
        if (buy == 1) {
            // allowed to buy
            // buy == money out == -prices[day]
            int profitBuy = -prices[day] + helper(prices, day + 1, 0, dp, txnCount);
            int profitNotBuy = helper(prices, day + 1, 1, dp, txnCount);
            maxProfit = Math.max(profitBuy, profitNotBuy);
        } else {
            //not allowed to buy
            // sell == money in == prices[day]
            int profitSell = prices[day] + helper(prices, day + 1, 1, dp, txnCount + 1);
            ;
            int profitNotSell = helper(prices, day + 1, 0, dp, txnCount);
            maxProfit = Math.max(profitSell, profitNotSell);
        }
        return dp[day][buy][txnCount] = maxProfit;
    }

    /**
     * Remove aux space == tabulate
     * TC: O(2n)
     * SC: O(2n)
     *
     * @param prices
     * @return
     */
    public int maxProfit_3(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n][2][3];
        // base
//        dp[n - 1][1][0] = 0;
        dp[n - 1][0][1] = prices[n - 1];
        dp[n - 1][0][0] = prices[n - 1];

        // logic
        for (int day = n - 2; day >= 0; day--) {
            int maxProfit = 0;
            for (int buy = 0; buy < 2; buy++) {
                for (int txnCount = 0; txnCount < 2; txnCount++) {
                    if (buy == 1) {
                        // allowed to buy
                        // buy == money out == -prices[day]
                        int profitBuy = -prices[day] + dp[day + 1][0][txnCount];
                        int profitNotBuy = dp[day + 1][1][txnCount];
                        maxProfit = Math.max(profitBuy, profitNotBuy);
                    } else {
                        //not allowed to buy
                        // sell == money in == prices[day]
                        int profitSell = prices[day] + dp[day + 1][1][txnCount + 1];
                        int profitNotSell = dp[day + 1][0][txnCount];
                        maxProfit = Math.max(profitSell, profitNotSell);
                    }
                    dp[day][buy][txnCount] = maxProfit;
                }
            }
        }
        return dp[0][1][0];
    }

    /**
     * Space Optimization
     * TC: O(4n)
     * SC: O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit_4(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[2][3];
        // base
//        dp[n - 1][1][0] = 0;
        dp[0][1] = prices[n - 1];
        dp[0][0] = prices[n - 1];

        // logic
        for (int day = n - 2; day >= 0; day--) {
            int maxProfit = 0;
            int[][] currDp = new int[2][3];
            for (int buy = 0; buy < 2; buy++) {
                for (int txnCount = 0; txnCount < 2; txnCount++) {
                    if (buy == 1) {
                        // allowed to buy
                        // buy == money out == -prices[day]
                        int profitBuy = -prices[day] + dp[0][txnCount];
                        int profitNotBuy = dp[1][txnCount];
                        maxProfit = Math.max(profitBuy, profitNotBuy);
                    } else {
                        //not allowed to buy
                        // sell == money in == prices[day]
                        int profitSell = prices[day] + dp[1][txnCount + 1];
                        int profitNotSell = dp[0][txnCount];
                        maxProfit = Math.max(profitSell, profitNotSell);
                    }
                    currDp[buy][txnCount] = maxProfit;
                }
            }
            dp = currDp;
        }
        return dp[1][0];
    }

}
