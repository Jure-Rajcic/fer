package hr.fer.oprpp1.hw04.db;
/**
 * functional interface [IFieldValueGetter] represents strategy pattern
 * 
 * @author Jure Rajcic
 */
public interface IFieldValueGetter {
    /**
     * @returns strategy result @param record
     */
    public String get(StudentRecord record);
}
