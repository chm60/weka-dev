/**
 * @author Len Trigg (trigg@cs.waikato.ac.nz) Chris Machala (chm60@aber.ac.uk)
 */

package weka.experiment;

import weka.core.Statistics;

import java.util.stream.LongStream;

public class WilcoxonSumRankStats extends TesterStats {

    /** Ranks the paried stats, used for the Wilcoxon Signed and Sum test */
    public Ranker ranker;

    /** The summed rank value for the sample x */
    public double X;

    /** The summed rank value for the sample y */
    public double Y;

    /**
     * Creates a new TesterStats object with the supplied significance level.
     *
     * @param sig the significance level for comparisons
     */
    public WilcoxonSumRankStats(double sig) {

        super(sig);
        ranker = new Ranker();

    }


    /**
     * Add an observed pair of values for wilcoxon sum rank test, also add the values to the ranker instance.
     *
     * @param value1 the value from column 1
     * @param value2 the value from column 2
     */
    public void add(double value1, double value2) {

        xStats.add(value1);
        yStats.add(value2);
        differencesStats.add(value1 - value2);

        ranker.add(value1, 0, count);
        count ++;
        ranker.add(value2, 0, count);
        count ++;

    }

    /**
     * Add rank value to appropriate variable
     * @param value1 variable to return correct rank value to sample X
     * @param value2 variable to return correct rank value to sample Y
     */

    public void computeRankedSum(double value1, double value2){

            X+= ranker.checkRank(value1);
            Y+= ranker.checkRank(value2);

    }

    /**
     * Method to calculate factorial of a given number
     * (1x2x3x4x.....n)
     * @param n value we want the factorial of
     * @return factorial of n
     */

    public long factorial(long n){

       return LongStream.rangeClosed( 1, (long) n ).reduce(1, ( long a, long b ) -> a * b);

    }

    /**
     * Calculates the derived statistics for Wilcoxon sum rank (significance etc).
     */
    public void calculateDerived() {

        xStats.calculateDerived();
        yStats.calculateDerived();
        differencesStats.calculateDerived();

        correlation = Double.NaN;
        if (!Double.isNaN(xStats.stdDev) && !Double.isNaN(yStats.stdDev)
                && (xStats.stdDev > 0) && (yStats.stdDev > 0) && (count > 1)) {
            correlation = (xySum - xStats.sum * yStats.sum / count)
                    / ((count - 1) * xStats.stdDev * yStats.stdDev);
        }

       if (differencesStats.stdDev > 0) {


            double tval = differencesStats.mean
                    * Math.sqrt(count)
                    / differencesStats.stdDev;

                if (count > 19) {

                    double Wstat = Math.floor(Math.min(X, Y));
                    double mn = (double) (count*(count+1)/4);
                    double stdDev = Math.sqrt((double)(count*(count+1)*(2*count+1))/24);
                    double z = (Wstat - mn) / stdDev;
                    differencesProbability = Statistics.normalProbability(z);

                } else if(count > 3){

                    long t = factorial( (count));
                    long a = factorial( (count/2));
                    long z = factorial( (count/2));

                    double distributionTotal = (double) (t / (a*z));
                    long min = (count/2 * ( count/2+1 )/2);
                    //X =  (X - min); Y =  (Y - min);

                    int Wstat = (int)Math.ceil(Math.min(X, Y));

                    for (int i = (int) min; i < Wstat+1; i++) {
                        differencesProbability += ProbabilityDistribution.WilcoxonSumRank(i, xStats.count, yStats.count);
                    }

                    differencesProbability = (differencesProbability / distributionTotal);
                }

                else {
                    differencesProbability = 0.0;
                }

        } else {
            if (differencesStats.sumSq == 0) {
                differencesProbability = 1.0;
            } else {
                differencesProbability = 0.0;
            }
        }
        differencesSignificance = 0;
        if (differencesProbability <= sigLevel) {
            if (xStats.mean > yStats.mean) {
                differencesSignificance = 1;
            } else {
                differencesSignificance = -1;
            }
        }
    }

    /**
     * Tests the paired stats object from the command line.
     * reads line from stdin, expecting two values per line.
     *
     * @param args ignored.
     */
    public static void main(String [] args) {

        try {
            WilcoxonSumRankStats ps = new WilcoxonSumRankStats(0.05);
            java.io.LineNumberReader r = new java.io.LineNumberReader(
                    new java.io.InputStreamReader(System.in));
            String line;
            while ((line = r.readLine()) != null) {
                line = line.trim();
                if (line.equals("") || line.startsWith("@") || line.startsWith("%")) {
                    continue;
                }
                java.util.StringTokenizer s
                        = new java.util.StringTokenizer(line, " ,\t\n\r\f");
                int count = 0;
                double v1 = 0, v2 = 0;
                while (s.hasMoreTokens()) {
                    double val = (new Double(s.nextToken())).doubleValue();
                    if (count == 0) {
                        v1 = val;
                    } else if (count == 1) {
                        v2 = val;
                    } else {
                        System.err.println("MSG: Too many values in line \""
                                + line + "\", skipped.");
                        break;
                    }
                    count++;
                }
                if (count == 2) {
                    ps.add(v1, v2);
                }
            }
            ps.calculateDerived();
            System.err.println(ps);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }
}
