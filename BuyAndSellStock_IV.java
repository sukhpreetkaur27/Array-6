// LC 188
public class BuyAndSellStock_IV {
    // same as buy and sell stock III
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int[][] dp = new int[2][k + 1];
        // base
//        dp[n - 1][1][0] = 0;
        for (int i = 0; i < k; i++) {
            dp[0][i] = prices[n - 1];
        }


        // logic
        for (int day = n - 2; day >= 0; day--) {
            int maxProfit = 0;
            int[][] currDp = new int[2][k + 1];
            for (int buy = 0; buy < 2; buy++) {
                for (int txnCount = 0; txnCount < k; txnCount++) {
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
