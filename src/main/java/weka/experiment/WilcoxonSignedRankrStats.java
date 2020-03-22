package weka.experiment;

import weka.core.Statistics;

public class WilcoxonSignedRankrStats extends TesterStats {

    /** Ranks the paried stats, specific for the wilcoxon test */
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
    public WilcoxonSignedRankrStats(double sig) {

        super(sig);
        ranker = new Ranker();

    }


    /**
     * Add an observed pair of values for wilcoxon test.
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

    public void computeRankedSum(double value){
        if (value > 0){
            positiveCounter += ranker.checkRank(value);
        }else if(value < 0){
            negativeCounter += ranker.checkRank(value);
        }

    }

    /**
     * Calculates the derived statistics for wilcoxon (significance etc).
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

            int tval = (int)Math.floor(Math.min(negativeCounter, positiveCounter));

            // Used to generate z value as a place holder
//            double mn = count * (count+1) *0.25;
//            double se = Math.sqrt((count * (count + 1) * (2*count + 1) )/24 );
//            double sgn = ((tval-mn) > 0) ? 1 : -1;
//            double d = 0.5 * sgn;
//            double z = (tval - mn - d) / se;

            if (m_degreesOfFreedom >= 1) {
                //differencesProbability = 2 * Statistics.normalProbability(Math.abs(z));
            } else {
                if (count > 20) {

                    // TODO: Use normal distribution
                //    differencesProbability = 2*Statistics.normalProbability(z);
                } else if(count > 3){

                    for (int i = 0; i < tval+1; i++) {
                        differencesProbability += StatSig.ProbabilityStatistic(tval, count);
                    }

                    differencesProbability = differencesProbability / Math.pow(2,count);
                }

                else {
                    differencesProbability = 1;
                }
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
            WilcoxonSignedRankrStats ps = new WilcoxonSignedRankrStats(0.05);
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
