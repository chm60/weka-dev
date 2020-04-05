package weka.experiment;

import weka.core.pmml.Array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ranker {

    HashMap<Double, Double> rankedDifferences = new HashMap<Double, Double>();

    Double[] DifferenceStatistics;

    /**
     * Sets the size of the input array
     * @param size number of pairs or sample elements
     */
    public void setSize(int size){
        DifferenceStatistics = new Double[size];
    }

    /**
     * adds differential absolute values to array
     * @param val1 dataset 1 pair x value
     * @param val2 dataset 2 pair x value
     * @param index the index to assign the new value
     */

    public void add(double val1, double val2, int index){

        DifferenceStatistics[index] = Math.abs(val1-val2);
    }

    /**
     * returns a map of unique values and the count of each unique value
     * @return Map of unique values with their counts
     */

    public Map<Double, Integer> valUnique()
    {
        Arrays.sort(DifferenceStatistics);
        return Arrays.stream(DifferenceStatistics).collect(Collectors.groupingBy(Function.identity(), Collectors.reducing(0, e -> 1, Integer::sum)));
    }

    /**
     * fills the hashmap with key : 'absolute value' and value : 'rank'
     */

    public void computeRank(){
        TreeMap<Double, Integer> unique = new TreeMap<Double, Integer>(valUnique());

        int rank = 0;
        unique.remove(0.0);



        for (Map.Entry<Double, Integer> value : unique.entrySet()){

            rank = rank + value.getValue();
            int numberUniques = value.getValue();
            rankedDifferences.put(value.getKey() , (double) ((((rank-numberUniques) + 1)*numberUniques) + ((numberUniques-1)*(numberUniques)) / 2) / numberUniques);
        }
    }

    /**#
     * Given a value, returns the associated rank of it.
     * @param value
     * @return rank
     */

    public double checkRank(double value){

        if(rankedDifferences.isEmpty()){
            return 0.0;
        }

        return rankedDifferences.get(Math.abs(value));

    }

}
