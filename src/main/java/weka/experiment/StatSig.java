package weka.experiment;

import java.util.Map;

public class StatSig {

    /**
     * Wilcoxon Signed rank rank probability distribution
     */

    protected static final int[] wilcoxon_distribution = {1, 1, 1, 2, 1, 1, 1};

    /**
     * Generates probabilty occurence tree for a given rank, uses recursion to calculate the level below the rank in question
     * @param WStat W stat from the Wilcoxon Signed Rank test
     * @param pair_count # of pairs in the dataset
     * @return probability occurence of a given rank total
     */
    protected static int ProbabilityStatistic(int WStat, int pair_count){

        // Total amount of rank values possible
        int WTotal = pair_count*(pair_count+1)/2;

        // need more than 3 sets of pairs to find out WStat currently here as a fail safe
        if(pair_count > 3){


            if(WStat <= WTotal){

                return (WStat - pair_count) < 0 ? ProbabilityStatistic(WStat-1, pair_count) : ProbabilityStatistic( WStat-pair_count, pair_count-1);

            }
        }

        if(WStat<=6){
            return wilcoxon_distribution[WStat];
        }
        else {
            return 0;
        }


    }

}
