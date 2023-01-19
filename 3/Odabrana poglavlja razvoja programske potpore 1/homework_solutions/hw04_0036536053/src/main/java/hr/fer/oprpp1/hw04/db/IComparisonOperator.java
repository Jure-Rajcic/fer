package hr.fer.oprpp1.hw04.db;

/**
 * functional interface [IComparisonOperator] represents strategy pattern
 * 
 * @author Jure Rajcic
 */
public interface IComparisonOperator {
    /**
     * @returns strategy result @param value1 and  @param value2
     */
    public boolean satisfied(String value1, String value2);
}
