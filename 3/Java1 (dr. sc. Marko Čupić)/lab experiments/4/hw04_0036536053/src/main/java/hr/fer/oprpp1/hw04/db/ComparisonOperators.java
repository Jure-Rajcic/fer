package hr.fer.oprpp1.hw04.db;

/**
 * enum [ComparisonOperators] represents algorithams based on operator
 * 
 * @author Jure Rajcic
 */
public enum ComparisonOperators implements IComparisonOperator {
    LESS((s1, s2) -> s1.compareTo(s2) < 0),
    LESS_OR_EQUALS((s1, s2) -> s1.compareTo(s2) <= 0),
    GREATER((s1, s2) -> s1.compareTo(s2) > 0),
    GREATER_OR_EQUALS((s1, s2) -> s1.compareTo(s2) >= 0),
    EQUALS((s1, s2) -> s1.compareTo(s2) == 0),
    NOT_EQUALS((s1, s2) -> s1.compareTo(s2) != 0),
    LIKE((s1, s2) -> { 
        long count = s2.chars().filter(ch -> ch == '*').count();
        if (count == 0)
            return s2.equals(s1);
        if (count > 1)
            throw new IllegalArgumentException("LIKE can be used on string with max one *");
        // count == 1
        if (s1.matches(String.format("%s(.*)", s2.replaceAll("\\*", ""))))
            return true;
        if (s1.matches(String.format("(.*)%s", s2.replaceAll("\\*", ""))))
            return true;
        // * somwhere in midle
        if (s2.length() - s1.length() > 1)
            return false;
        return s1.matches(String.format("%s(.*)", s2.split("\\*")[0]))
                && s1.matches(String.format("(.*)%s", s2.split("\\*")[1]));
    });

    private IComparisonOperator ics; // functional interface object

    /**
     * enum constructor, asigns algorithm (strategt) to @param ics
     */
    private ComparisonOperators(IComparisonOperator ics) {
        this.ics = ics;
    }

    /**
     * 
     * if strategy is LIKE and @param value2 has more then 1
     * star('*') @throws IllegalArgumentException
     * @returns strategy result for @param value1 and @param value2
     * 
     */
    @Override
    public boolean satisfied(String value1, String value2) {
        boolean result = false;
        try {
            result = ics.satisfied(value1, value2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " [" + value2 + "]");
        }
        return result;
    }
}
