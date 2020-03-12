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
 *    StudentTTester.java
 *    Copyright (C) 1999-2012 University of Waikato, Hamilton, New Zealand
 *
 */

package weka.experiment;

import weka.core.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Calculates T-Test statistics on data stored in a set of instances.
 * <p/>
 * 
 * <!-- options-start --> Valid options are:
 * <p/>
 * 
 * <pre>
 * -D &lt;index,index2-index4,...&gt;
 *  Specify list of columns that specify a unique
 *  dataset.
 *  First and last are valid indexes. (default none)
 * </pre>
 * 
 * <pre>
 * -R &lt;index&gt;
 *  Set the index of the column containing the run number
 * </pre>
 * 
 * <pre>
 * -F &lt;index&gt;
 *  Set the index of the column containing the fold number
 * </pre>
 * 
 * <pre>
 * -G &lt;index1,index2-index4,...&gt;
 *  Specify list of columns that specify a unique
 *  'result generator' (eg: classifier name and options).
 *  First and last are valid indexes. (default none)
 * </pre>
 * 
 * <pre>
 * -S &lt;significance level&gt;
 *  Set the significance level for comparisons (default 0.05)
 * </pre>
 * 
 * <pre>
 * -V
 *  Show standard deviations
 * </pre>
 * 
 * <pre>
 * -L
 *  Produce table comparisons in Latex table format
 * </pre>
 * 
 * <pre>
 * -csv
 *  Produce table comparisons in CSV table format
 * </pre>
 * 
 * <pre>
 * -html
 *  Produce table comparisons in HTML table format
 * </pre>
 * 
 * <pre>
 * -significance
 *  Produce table comparisons with only the significance values
 * </pre>
 * 
 * <pre>
 * -gnuplot
 *  Produce table comparisons output suitable for GNUPlot
 * </pre>
 * 
 * <!-- options-end -->
 * 
 * @author Len Trigg (trigg@cs.waikato.ac.nz)
 * @version $Revision: 11542 $
 */
public class WilcoxonSignedRankTester extends PairedTester {

  public WilcoxonSignedRankTester() {
    this.displayName = "Wilcoxon Signed Rank Test";
  }

  /**
   * Computes a paired t-test comparison for a specified dataset between two
   * resultsets.
   * 
   * @param datasetSpecifier the dataset specifier
   * @param resultset1Index the index of the first resultset
   * @param resultset2Index the index of the second resultset
   * @param comparisonColumn the column containing values to compare
   * @return the results of the paired comparison
   * @throws Exception if an error occurs
   */
  @Override
  public TesterStats calculateStatistics(Instance datasetSpecifier,
                                         int resultset1Index, int resultset2Index, int comparisonColumn)
    throws Exception {

    if (m_Instances.attribute(comparisonColumn).type() != Attribute.NUMERIC) {
      throw new Exception("Comparison column " + (comparisonColumn + 1) + " ("
        + m_Instances.attribute(comparisonColumn).name() + ") is not numeric");
    }
    if (!m_ResultsetsValid) {
      prepareData();
    }

    Resultset resultset1 = m_Resultsets.get(resultset1Index);
    Resultset resultset2 = m_Resultsets.get(resultset2Index);
    ArrayList<Instance> dataset1 = resultset1.dataset(datasetSpecifier);
    ArrayList<Instance> dataset2 = resultset2.dataset(datasetSpecifier);
    String datasetName = templateString(datasetSpecifier);
    if (dataset1 == null) {
      throw new Exception("No results for dataset=" + datasetName
        + " for resultset=" + resultset1.templateString());
    } else if (dataset2 == null) {
      throw new Exception("No results for dataset=" + datasetName
        + " for resultset=" + resultset2.templateString());
    } else if (dataset1.size() != dataset2.size()) {
      throw new Exception("Results for dataset=" + datasetName
        + " differ in size for resultset=" + resultset1.templateString()
        + " and resultset=" + resultset2.templateString());
    }

    WilcoxonSignedRankrStats pairedStats = new WilcoxonSignedRankrStats(m_SignificanceLevel);
    pairedStats.ranker.setSize(dataset1.size());

    for (int k = 0; k < dataset1.size(); k++) {
      Instance current1 = dataset1.get(k);
      Instance current2 = dataset2.get(k);
      if (current1.isMissing(comparisonColumn)) {
        System.err.println("Instance has missing value in comparison "
          + "column!\n" + current1);
        continue;
      }
      if (current2.isMissing(comparisonColumn)) {
        System.err.println("Instance has missing value in comparison "
          + "column!\n" + current2);
        continue;
      }
      if (current1.value(m_RunColumn) != current2.value(m_RunColumn)) {
        System.err.println("Run numbers do not match!\n" + current1 + current2);
      }
      if (m_FoldColumn != -1) {
        if (current1.value(m_FoldColumn) != current2.value(m_FoldColumn)) {
          System.err.println("Fold numbers do not match!\n" + current1
            + current2);
        }
      }
      double value1 = current1.value(comparisonColumn);
      double value2 = current2.value(comparisonColumn);
      pairedStats.add(value1, value2);
    }
    pairedStats.ranker.computeRank();

    for (int k = 0; k < dataset1.size(); k++) {
      Instance current1 = dataset1.get(k);
      Instance current2 = dataset2.get(k);

      double value1 = current1.value(comparisonColumn);
      double value2 = current2.value(comparisonColumn);

      if(pairedStats.ranker.rankedDifferences == null){

        System.err.println("No ranked values");

      }else{

        pairedStats.computeRankedSum(value1-value2);
      }

    }


    pairedStats.calculateDerived();
    return pairedStats;

  }

  /**
   * Test the class from the command line.
   * 
   * @param args contains options for the instance ttests
   */
  public static void main(String args[]) {

    try {

      WilcoxonSignedRankTester tt = new WilcoxonSignedRankTester();
      String datasetName = Utils.getOption('t', args);
      String compareColStr = Utils.getOption('c', args);
      String baseColStr = Utils.getOption('b', args);
      boolean summaryOnly = Utils.getFlag('s', args);
      boolean rankingOnly = Utils.getFlag('r', args);
      boolean noHeader = Utils.getFlag('n', args);
      try {
        if ((datasetName.length() == 0) || (compareColStr.length() == 0)) {
          throw new Exception("-t and -c options are required");
        }
        tt.setOptions(args);
        Utils.checkForRemainingOptions(args);
      } catch (Exception ex) {
        String result = "";
        Enumeration<Option> enu = tt.listOptions();
        while (enu.hasMoreElements()) {
          Option option = enu.nextElement();
          result += option.synopsis() + '\n' + option.description() + '\n';
        }
        throw new Exception(ex.getMessage() + "\n\nUsage:\n\n" + "-t <file>\n"
          + "\tSet the dataset containing data to evaluate\n" + "-b <index>\n"
          + "\tSet the resultset to base comparisons against (optional)\n"
          + "-c <index>\n" + "\tSet the column to perform a comparison on\n"
          + "-s\n" + "\tSummarize wins over all resultset pairs\n" + "-r\n"
          + "\tGenerate a resultset ranking\n" + "-n\n" + "\tDo not output header info\n"+ result);
      }
      Instances data = new Instances(new BufferedReader(new FileReader(
        datasetName)));
      tt.setInstances(data);
      // tt.prepareData();
      int compareCol = Integer.parseInt(compareColStr) - 1;
      if (!noHeader) {
        System.out.println(tt.header(compareCol));
      }
      if (rankingOnly) {
        System.out.println(tt.multiResultsetRanking(compareCol));
      } else if (summaryOnly) {
        System.out.println(tt.multiResultsetSummary(compareCol));
      } else {
        //System.out.println(tt.resultsetKey());
        if (baseColStr.length() == 0) {
          for (int i = 0; i < tt.getNumResultsets(); i++) {
            if (!tt.displayResultset(i)) {
              continue;
            }
            System.out.println(tt.multiResultsetFull(i, compareCol));
          }
        } else {
          int baseCol = Integer.parseInt(baseColStr) - 1;
          System.out.println(tt.multiResultsetFull(baseCol, compareCol));
        }
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
