package weka.experiment;

import weka.core.Statistics;

public class WilcoxonSignedPairedStats extends PairedStats {

    /** Ranks the paried stats, specific for the wilcoxon test */
    public Ranker ranker;

    /** The counter for the negative group */
    public double negativeCounter;

    /** The counter for the positive group */
    public double positiveCounter;

    /**
     * Creates a new PairedStats object with the supplied significance level.
     *
     * @param sig the significance level for comparisons
     */
    public WilcoxonSignedPairedStats(double sig) {

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


        ranker.add(value1, value2, (int)count);
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

            double tval = Math.min(negativeCounter, positiveCounter);

            // Used to generate z value as a place holder
            double mn = count * (count+1) *0.25;
            double se = Math.sqrt((count * (count + 1) * (2*count + 1) )/24 );
            double sgn = ((tval-mn) > 0) ? 1 : -1;
            double d = 0.5 * sgn;
            double z = (tval - mn - d) / se;

            if (m_degreesOfFreedom >= 1) {
                differencesProbability = 2 * Statistics.normalProbability(Math.abs(z));
            } else {
                if (count > 1) {

                    // TODO: Implement stat table generation here
                    differencesProbability = 2*Statistics.normalProbability(z);
                } else {
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
}
