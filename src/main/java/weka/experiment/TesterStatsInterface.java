package weka.experiment;

import weka.core.RevisionHandler;

public interface TesterStatsInterface extends RevisionHandler {


    /**
     * Sets the degrees of freedom (if calibration is required).
     */
    public void setDegreesOfFreedom(int d);

    /**
     * Gets the degrees of freedom.
     */
    public int getDegreesOfFreedom();

    /**
     * Add an observed pair of values.
     *
     * @param value1 the value from column 1
     * @param value2 the value from column 2
     */
    public void add(double value1, double value2);

    /**
     * Removes an observed pair of values.
     *
     * @param value1 the value from column 1
     * @param value2 the value from column 2
     */
    public void subtract(double value1, double value2);

    /**
     * Adds an array of observed pair of values.
     *
     * @param value1 the array containing values from column 1
     * @param value2 the array containing values from column 2
     */
    public void add(double value1[], double value2[]);

    /**
     * Removes an array of observed pair of values.
     *
     * @param value1 the array containing values from column 1
     * @param value2 the array containing values from column 2
     */
    public void subtract(double value1[], double value2[]);

    /**
     * Calculates the derived statistics for the given test (significance etc).
     */
    public void calculateDerived();

    /**
     * Returns statistics on the paired comparison.
     *
     * @return the t-test statistics as a string
     */
    public String toString();

    /**
     * Returns the revision string.
     *
     * @return		the revision
     */
    public String getRevision();


}
