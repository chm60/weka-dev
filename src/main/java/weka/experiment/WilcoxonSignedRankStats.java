/**
 * @author Len Trigg (trigg@cs.waikato.ac.nz) Chris Machala (chm60@aber.ac.uk)
 */
package weka.experiment;

import weka.core.Statistics;

public class WilcoxonSignedRankStats extends TesterStats {

    /** Ranks the paried stats, used for the Wilcoxon Signed and Sum test */
    public Ranker ranker;

    /** The counter for the negative group */
    public double negativeCounter;

    /** The counter for the positive group */
    public double positiveCounter;

    /**
     * Creates a new TesterStats object with the supplied significance level.
     *
     * @param sig the significance level for comparisons
     */
    public WilcoxonSignedRankStats(double sig) {

        super(sig);
        ranker = new Ranker();

    }


    /**
     * Add an observed pair of values for Wilcoxon signed rank test.
     *
     * @param value1 the value from column 1
     * @param value2 the value from column 2
     */
    public void add(double value1, double value2) {

        xStats.add(value1);
        yStats.add(value2);
        differencesStats.add(value1 - value2);
        xySum += value1 * value2;

        ranker.add(value1, value2, count);
        count ++;

    }

    /**
     * Assigns rank value to appropriate signed variable
     * @param value the rank value
     */
    public void computeRankedSum(double value){
        if (value > 0){
            positiveCounter += ranker.checkRank(value);
        }else if(value < 0){
            negativeCounter += ranker.checkRank(value);
        }

    }

    /**
     * Calculates the derived statistics for Wilcoxon signed rank (significance etc).
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

           /**
            * Use a normal distribution rather than the W distribution to asses for significance when there are more than 20 pairs
            */
           if (ranker.returnPairCount() > 20) {

                   double Wstat = Math.floor(Math.min(negativeCounter, positiveCounter));
                   double mn = differencesStats.sum / differencesStats.count;
                   double z = (Wstat - mn) / differencesStats.stdDev;
                   differencesProbability = Statistics.normalProbability(z);

               /**
                * When more there are less than 20 pairs use a Wsum distribution to asses for significance, down to a lower limit of 3.
                 */
           } else if(ranker.returnPairCount() > 3){

                    int Wstat = (int)Math.ceil(Math.min(negativeCounter, positiveCounter));

                    for (int i = 0; i < Wstat+1; i++) {
                        differencesProbability += ProbabilityDistribution.WilcoxonSignedRank(i, ranker.returnPairCount());
                    }

                    differencesProbability = 2*(differencesProbability / Math.pow(2,ranker.returnPairCount()));
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
            WilcoxonSignedRankStats ps = new WilcoxonSignedRankStats(0.05);
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
