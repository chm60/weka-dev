package weka.experiment;

import weka.core.Statistics;

public class StudentStats extends TesterStats {
    /**
     * Creates a new TesterStats object with the supplied significance level.
     *
     * @param sig the significance level for comparisons
     */
    public StudentStats(double sig) {
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

    /**
     * Tests the paired stats object from the command line.
     * reads line from stdin, expecting two values per line.
     *
     * @param args ignored.
     */
    public static void main(String [] args) {

        try {
            StudentStats ps = new StudentStats(0.05);
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
