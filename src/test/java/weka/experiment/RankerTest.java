package weka.experiment;
import junit.framework.TestCase;

import java.util.Map;


public class RankerTest extends TestCase {

    Ranker ranker;

    /**
     * calls setSize method to set size of DifferenceStatistics to 5.
     * Asserts that DifferenceStatistic is 5
     */
    public void testSetSize(){

        ranker.setSize(5);
        assertEquals(5, ranker.DifferenceStatistics.length);

    }

    /**
     * calls add method to add val1-val2 to DifferenceStatistics[index].
     * Asserts that DifferenceStatistic[index] is val1-val2
     */
    public void testAdd(){

        ranker.add(5,1,0);
        assertEquals(4, ranker.DifferenceStatistics[0]);


        ranker.add(7,2,1);
        assertEquals(5, ranker.DifferenceStatistics[1]);


        ranker.add(11,6,2);
        assertEquals(5, ranker.DifferenceStatistics[2]);


        ranker.add(6,2,4);
        assertEquals(4, ranker.DifferenceStatistics[4]);


        ranker.add(22,12,3);
        assertEquals(10, ranker.DifferenceStatistics[3]);

    }

    /**
     * Asserts that the map has the correct values and their counts
     */

    public void testValUnique(){

        Map<Double, Integer> uniqueTest = ranker.valUnique();

        assertEquals(uniqueTest.size() , 3);
        assertEquals(uniqueTest.get(4).intValue(),2);
        assertEquals(uniqueTest.get(5).intValue(),2);
        assertEquals(uniqueTest.get(10).intValue(),1);

    }

    /**
     * Asserts that the map has the correct values and their ranked counterparts.
     * also employes checkRank
     */
    public void testComputeRank(){

        ranker.computeRank();

        assertEquals(ranker.checkRank(4), 1.5);
        assertEquals(ranker.checkRank(5), 3.5);
        assertEquals(ranker.checkRank(10), 5);

    }

}
