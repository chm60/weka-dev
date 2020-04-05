package weka.experiment;

import junit.framework.TestCase;

public class ProbabilityDistributionTest extends TestCase {


    public void testWilcoxonSignedRank(){

        double a = Math.ceil(33.3);

        /*
          Test Asserts for pair count of 3; from W Stats from 0 -> 6
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 10 Figure 1.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(0,3));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(1,3));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(2,3));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(3,3));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(4,3));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(5,3));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(6,3));

        /*
          Test Asserts for pair count of 4; from W Stats from 0 -> 10
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 10 Figure 1.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(0,4));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(1,4));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(2,4));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(3,4));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(4,4));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(5,4));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(6,4));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(7,4));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(8,4));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(9,4));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(10,4));

        /*
          Test Asserts for pair count of 5; from W Stats from 0 -> 15
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 10 Figure 1.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(0,5));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(1,5));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(2,5));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(3,5));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(4,5));
        assertEquals(3, ProbabilityDistribution.WilcoxonSignedRank(5,5));
        assertEquals(3, ProbabilityDistribution.WilcoxonSignedRank(6,5));
        assertEquals(3, ProbabilityDistribution.WilcoxonSignedRank(7,5));
        assertEquals(3, ProbabilityDistribution.WilcoxonSignedRank(8,5));
        assertEquals(3, ProbabilityDistribution.WilcoxonSignedRank(9,5));
        assertEquals(3, ProbabilityDistribution.WilcoxonSignedRank(10,5));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(11,5));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(12,5));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(13,5));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(14,5));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(15,5));

        /*
          Test Asserts for pair count of 6; from W Stats from 0 -> 21
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 10 Figure 1.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(0,6));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(1,6));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(2,6));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(3,6));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(4,6));
        assertEquals(3, ProbabilityDistribution.WilcoxonSignedRank(5,6));
        assertEquals(4, ProbabilityDistribution.WilcoxonSignedRank(6,6));
        assertEquals(4, ProbabilityDistribution.WilcoxonSignedRank(7,6));
        assertEquals(4, ProbabilityDistribution.WilcoxonSignedRank(8,6));
        assertEquals(5, ProbabilityDistribution.WilcoxonSignedRank(9,6));
        assertEquals(5, ProbabilityDistribution.WilcoxonSignedRank(10,6));
        assertEquals(5, ProbabilityDistribution.WilcoxonSignedRank(11,6));
        assertEquals(5, ProbabilityDistribution.WilcoxonSignedRank(12,6));
        assertEquals(4, ProbabilityDistribution.WilcoxonSignedRank(13,6));
        assertEquals(4, ProbabilityDistribution.WilcoxonSignedRank(14,6));
        assertEquals(4, ProbabilityDistribution.WilcoxonSignedRank(15,6));
        assertEquals(3, ProbabilityDistribution.WilcoxonSignedRank(16,6));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(17,6));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(18,6));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(19,6));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(20,6));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(21,6));

        /*
          Test Asserts for pair count of 7; from W Stats from 0 -> 28
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 10 Figure 1.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(0,7));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(1,7));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(2,7));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(3,7));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(4,7));
        assertEquals(3, ProbabilityDistribution.WilcoxonSignedRank(5,7));
        assertEquals(4, ProbabilityDistribution.WilcoxonSignedRank(6,7));
        assertEquals(5, ProbabilityDistribution.WilcoxonSignedRank(7,7));
        assertEquals(5, ProbabilityDistribution.WilcoxonSignedRank(8,7));
        assertEquals(6, ProbabilityDistribution.WilcoxonSignedRank(9,7));
        assertEquals(7, ProbabilityDistribution.WilcoxonSignedRank(10,7));
        assertEquals(7, ProbabilityDistribution.WilcoxonSignedRank(11,7));
        assertEquals(8, ProbabilityDistribution.WilcoxonSignedRank(12,7));
        assertEquals(8, ProbabilityDistribution.WilcoxonSignedRank(13,7));
        assertEquals(8, ProbabilityDistribution.WilcoxonSignedRank(14,7));
        assertEquals(8, ProbabilityDistribution.WilcoxonSignedRank(15,7));
        assertEquals(8, ProbabilityDistribution.WilcoxonSignedRank(16,7));
        assertEquals(7, ProbabilityDistribution.WilcoxonSignedRank(17,7));
        assertEquals(7, ProbabilityDistribution.WilcoxonSignedRank(18,7));
        assertEquals(6, ProbabilityDistribution.WilcoxonSignedRank(19,7));
        assertEquals(5, ProbabilityDistribution.WilcoxonSignedRank(20,7));
        assertEquals(5, ProbabilityDistribution.WilcoxonSignedRank(21,7));
        assertEquals(4, ProbabilityDistribution.WilcoxonSignedRank(22,7));
        assertEquals(3, ProbabilityDistribution.WilcoxonSignedRank(23,7));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(24,7));
        assertEquals(2, ProbabilityDistribution.WilcoxonSignedRank(25,7));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(26,7));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(27,7));
        assertEquals(1, ProbabilityDistribution.WilcoxonSignedRank(28,7));

    }

    public void testWilcoxonSumRank(){

        /*
          Test Asserts for group size [2,2] from W Stats from 3 -> 7
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 12 Figure 3.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(3,2,2));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(4,2,2));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(5,2,2));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(6,2,2));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(7,2,2));

        /*
          Test Asserts for group size [2,3] from W Stats from 3 -> 9
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 12 Figure 3.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(3,2,3));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(4,2,3));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(5,2,3));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(6,2,3));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(7,2,3));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(8,2,3));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(9,2,3));

        /*
          Test Asserts for group size [2,4] from W Stats from 3 -> 11
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 12 Figure 3.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(3,2,4));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(4,2,4));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(5,2,4));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(6,2,4));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(7,2,4));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(8,2,4));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(9,2,4));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(10,2,4));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(11,2,4));

        /*
          Test Asserts for group size [2,5] from W Stats from 3 -> 13
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 12 Figure 3.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(3,2,5));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(4,2,5));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(5,2,5));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(6,2,5));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(7,2,5));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(8,2,5));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(9,2,5));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(10,2,5));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(11,2,5));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(12,2,5));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(13,2,5));

        /*
          Test Asserts for group size [2,6] from W Stats from 3 -> 15
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 12 Figure 3.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(3,2,6));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(4,2,6));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(5,2,6));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(6,2,6));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(7,2,6));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(8,2,6));
        assertEquals(4, ProbabilityDistribution.WilcoxonSumRank(9,2,6));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(10,2,6));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(11,2,6));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(12,2,6));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(13,2,6));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(14,2,6));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(15,2,6));

        /*
          Test Asserts for group size [3,3] from W Stats from 6 -> 15
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 12 Figure 3.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(6,3,3));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(7,3,3));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(8,3,3));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(9,3,3));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(10,3,3));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(11,3,3));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(12,3,3));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(13,3,3));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(14,3,3));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(15,3,3));

        /*
          Test Asserts for group size [3,4] from W Stats from 6 -> 18
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 12 Figure 3.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(6,3,4));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(7,3,4));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(8,3,4));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(9,3,4));
        assertEquals(4, ProbabilityDistribution.WilcoxonSumRank(10,3,4));
        assertEquals(4, ProbabilityDistribution.WilcoxonSumRank(11,3,4));
        assertEquals(5, ProbabilityDistribution.WilcoxonSumRank(12,3,4));
        assertEquals(4, ProbabilityDistribution.WilcoxonSumRank(13,3,4));
        assertEquals(4, ProbabilityDistribution.WilcoxonSumRank(14,3,4));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(15,3,4));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(16,3,4));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(17,3,4));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(18,3,4));

        /*
          Test Asserts for group size [3,5] from W Stats from 6 -> 21
          Expected Value from https://amstat.tandfonline.com/doi/pdf/10.1080/10691898.2010.11889486?needAccess=true page 12 Figure 3.
         */

        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(6,3,5));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(7,3,5));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(8,3,5));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(9,3,5));
        assertEquals(4, ProbabilityDistribution.WilcoxonSumRank(10,3,5));
        assertEquals(5, ProbabilityDistribution.WilcoxonSumRank(11,3,5));
        assertEquals(6, ProbabilityDistribution.WilcoxonSumRank(12,3,5));
        assertEquals(6, ProbabilityDistribution.WilcoxonSumRank(13,3,5));
        assertEquals(6, ProbabilityDistribution.WilcoxonSumRank(14,3,5));
        assertEquals(6, ProbabilityDistribution.WilcoxonSumRank(15,3,5));
        assertEquals(5, ProbabilityDistribution.WilcoxonSumRank(16,3,5));
        assertEquals(4, ProbabilityDistribution.WilcoxonSumRank(17,3,5));
        assertEquals(3, ProbabilityDistribution.WilcoxonSumRank(18,3,5));
        assertEquals(2, ProbabilityDistribution.WilcoxonSumRank(19,3,5));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(20,3,5));
        assertEquals(1, ProbabilityDistribution.WilcoxonSumRank(21,3,5));

    }

}
