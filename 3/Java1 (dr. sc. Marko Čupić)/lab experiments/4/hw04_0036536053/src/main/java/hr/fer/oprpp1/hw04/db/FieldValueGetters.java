package hr.fer.oprpp1.hw04.db;

/**
 * enum [FieldValueGetters] represents algorithams based on atributes
 * 
 * @author Jure Rajcic
 */
public enum FieldValueGetters implements IFieldValueGetter {

    FIRST_NAME((student) -> student.getFirstName()),
    LAST_NAME((student) -> student.getLastName()),
    JMBAG((student) -> student.getJmbag());

    private IFieldValueGetter ivg; // functional interface object

    /**
     * enum constructor, asigns algorithm (strategt) to @param ics
     */
    private FieldValueGetters(IFieldValueGetter ivg) {
        this.ivg = ivg;
    }

    /**
     * @return strategy result for @param record
     */
    @Override
    public String get(StudentRecord record) {
        return ivg.get(record);
    }

}
