package weka.experiment;

import weka.core.Statistics;

public class StudentPairedStats extends PairedStats {
    /**
     * Creates a new PairedStats object with the supplied significance level.
     *
     * @param sig the significance level for comparisons
     */
    public StudentPairedStats(double sig) {
        super(sig);
    }

    /**
     * Calculates the derived statistics (significance etc).
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
            // finds tval
            double tval = differencesStats.mean
                    * Math.sqrt(count)
                    / differencesStats.stdDev;

            // converts t to p value
            if (m_degreesOfFreedom >= 1) {
                differencesProbability = Statistics.FProbability(tval * tval, 1,
                        m_degreesOfFreedom);
            } else {
                if (count > 1) {
                    differencesProbability = Statistics.FProbability(tval * tval, 1,
                            (int) count - 1);
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
