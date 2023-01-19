package hr.fer.oprpp1.hw04.db;
/**
 * functional interface [IFilter]
 * @author Jure Rajcic
 */
public interface IFilter {

    /**
     * @returns boolean value for @param record when passed to filter
     */
 public boolean accepts(StudentRecord record);
}