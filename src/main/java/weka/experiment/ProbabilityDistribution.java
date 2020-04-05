package weka.experiment;

public class ProbabilityDistribution {

    /**
     * Wilcoxon Signed rank rank probability distribution
     */

    protected static final int[] wilcoxon_signed_rank_distribution = {1, 1, 1, 2, 1, 1, 1};

    /**
     * Generates Probability occurrence tree for a given rank in Wilcoxon sum rank, uses recursion to calculate the level below the rank in question
     * @param Wstat W stat from the Wilcoxon Sum Rank test
     * @param n size of group 1
     * @param m size of group 2
     * @return cumulative occurrence weight for given W stat
     */
    protected static int WilcoxonSumRank(double Wstat, double n, double m){

        double min = (n*(n+1)/2);

        if(Wstat>Math.pow(n, 2)+min | Wstat < min){
            return 0;
        }else if(n==1 | m==1){
            return 1;
        }else{
            return WilcoxonSumRank(Wstat-n-m, n-1, m) + WilcoxonSumRank(Wstat, n, m-1);
        }
    }

    /**
     * Generates Probability occurrence tree for a given rank in Wilcoxon signed rank, uses recursion to calculate the level below the rank in question
     * @param WStat W stat from the Wilcoxon Signed Rank test
     * @param pair_count number of pairs in the dataset
     * @return probability occurrence of a given rank total
     */
    protected static int WilcoxonSignedRank(int WStat, int pair_count){

        // Total amount of rank values possible
        int WTotal = pair_count*(pair_count+1)/2;

        // need more than 3 sets of pairs to find out WStat currently here as a fail safe
        if(pair_count > 3){

            if(WStat-pair_count < 0){
                return 0;
            }


            if(WStat <= WTotal){

                return (WStat - pair_count) < 0 ? WilcoxonSignedRank(WStat-1, pair_count) : WilcoxonSignedRank( WStat-pair_count, pair_count-1);

            }
        }

        if(WStat<=6){
            return wilcoxon_signed_rank_distribution[WStat];
        }
        else {
            return 0;
        }


    }

}
