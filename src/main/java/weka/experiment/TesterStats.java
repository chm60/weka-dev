/**
 * @author Len Trigg (trigg@cs.waikato.ac.nz)  Chris Machala (chm60@aber.ac.uk)
 */



/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *    TesterStats.java
 *    Copyright (C) 1999-2012 University of Waikato, Hamilton, New Zealand
 *
 */


package weka.experiment;

import weka.core.RevisionHandler;
import weka.core.RevisionUtils;
import weka.core.Utils;

/**
 * A class for storing stats on a paired comparison (t-test and correlation)
 *
 * @author Len Trigg (trigg@cs.waikato.ac.nz)
 * @version $Revision: 14314 $
 */
public class TesterStats
  implements RevisionHandler, TesterStatsInterface {
  
  /** The stats associated with the data in column 1 */
  public Stats xStats;
  
  /** The stats associated with the data in column 2 */
  public Stats yStats;
  
  /** The stats associated with the paired differences */
  public Stats differencesStats;

  /** The probability of obtaining the observed differences */
  public double differencesProbability;

  /** The correlation coefficient */
  public double correlation;

  /** The sum of the products */
  public double xySum;
  
  /** The number of data points seen */
  public int count;
  
  /**
   * A significance indicator:
   * 0 if the differences are not significant
   * > 0 if x significantly greater than y
   * < 0 if x significantly less than y
   */
  public int differencesSignificance;
  
  /** The significance level for comparisons */
  public double sigLevel;

  /** The degrees of freedom (if set programmatically) */
  protected int m_degreesOfFreedom = 0;
    
  /**
   * Creates a new TesterStats object with the supplied significance level.
   *
   * @param sig the significance level for comparisons
   */
  public TesterStats(double sig) {
    xStats = new Stats();
    yStats = new Stats();
    differencesStats = new Stats();
    sigLevel = sig;
  }

  /**
   * Sets the degrees of freedom (if calibration is required).
   */
  public void setDegreesOfFreedom(int d) {
   
    if (d <= 0) {
      throw new IllegalArgumentException("TesterStats: degrees of freedom must be >= 1");
    }
    m_degreesOfFreedom = d;
  }

  /**
   * Gets the degrees of freedom.
   */
  public int getDegreesOfFreedom() {

    return m_degreesOfFreedom;
  }

  /**
   * Add an observed pair of values.
   *
   * @param value1 the value from column 1
   * @param value2 the value from column 2
   */

  public void add(double value1, double value2) {

    xStats.add(value1);
    yStats.add(value2);
    differencesStats.add(value1 - value2);
    xySum += value1 * value2;
    count ++;
  }
    
  /**
   * Removes an observed pair of values.
   *
   * @param value1 the value from column 1
   * @param value2 the value from column 2
   */
  public void subtract(double value1, double value2) {

    xStats.subtract(value1);
    yStats.subtract(value2);
    differencesStats.subtract(value1 - value2);
    xySum -= value1 * value2;
    count --;
  }

    
  /**
   * Adds an array of observed pair of values.
   *
   * @param value1 the array containing values from column 1
   * @param value2 the array containing values from column 2
   */
  public void add(double value1[], double value2[]) {
    if ((value1 == null) || (value2 == null)) {
      throw new NullPointerException();
    }
    if (value1.length != value2.length) {
      throw new IllegalArgumentException("Arrays must be of the same length");
    }
    for (int i = 0; i < value1.length; i++) {
      add(value1[i], value2[i]);
    }
  }


  /**
   * Removes an array of observed pair of values.
   *
   * @param value1 the array containing values from column 1
   * @param value2 the array containing values from column 2
   */
  public void subtract(double value1[], double value2[]) {
    if ((value1 == null) || (value2 == null)) {
      throw new NullPointerException();
    }
    if (value1.length != value2.length) {
      throw new IllegalArgumentException("Arrays must be of the same length");
    }
    for (int i = 0; i < value1.length; i++) {
      subtract(value1[i], value2[i]);
    }
  }

  /**
   * Calculates the derived statistics for the given test (significance etc).
   */

  @Override
  public void calculateDerived() {

  }

  /**
   * Returns statistics on the paired comparison.
   *
   * @return the t-test statistics as a string
   */
  public String toString() {

    return "Analysis for " + Utils.doubleToString(count, 0)
      + " points:\n"
      + "                "
      + "         Column 1"
      + "         Column 2"
      + "       Difference\n"
      + "Minimums        "
      + Utils.doubleToString(xStats.min, 17, 4)
      + Utils.doubleToString(yStats.min, 17, 4)
      + Utils.doubleToString(differencesStats.min, 17, 4) + '\n'
      + "Maximums        "
      + Utils.doubleToString(xStats.max, 17, 4)
      + Utils.doubleToString(yStats.max, 17, 4)
      + Utils.doubleToString(differencesStats.max, 17, 4) + '\n'
      + "Sums            "
      + Utils.doubleToString(xStats.sum, 17, 4)
      + Utils.doubleToString(yStats.sum, 17, 4)
      + Utils.doubleToString(differencesStats.sum, 17, 4) + '\n'
      + "SumSquares      "
      + Utils.doubleToString(xStats.sumSq, 17, 4)
      + Utils.doubleToString(yStats.sumSq, 17, 4)
      + Utils.doubleToString(differencesStats.sumSq, 17, 4) + '\n'
      + "Means           "
      + Utils.doubleToString(xStats.mean, 17, 4)
      + Utils.doubleToString(yStats.mean, 17, 4)
      + Utils.doubleToString(differencesStats.mean, 17, 4) + '\n'
      + "SDs             "
      + Utils.doubleToString(xStats.stdDev, 17, 4)
      + Utils.doubleToString(yStats.stdDev, 17, 4)
      + Utils.doubleToString(differencesStats.stdDev, 17, 4) + '\n'
      + "Prob(differences) "
      + Utils.doubleToString(differencesProbability, 4)
      + " (sigflag " + differencesSignificance + ")\n"
      + "Correlation       "
      + Utils.doubleToString(correlation,4) + "\n";
  }
  
  /**
   * Returns the revision string.
   * 
   * @return		the revision
   */
  public String getRevision() {
    return RevisionUtils.extract("$Revision: 14314 $");
  }

  /**
   * Tests the paired stats object from the command line.
   * reads line from stdin, expecting two values per line.
   *
   * @param args ignored.
   */
  public static void main(String [] args) {


  }
} // TesterStats



