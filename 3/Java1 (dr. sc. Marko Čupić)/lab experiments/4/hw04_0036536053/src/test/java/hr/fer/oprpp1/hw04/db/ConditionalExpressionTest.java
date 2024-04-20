package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConditionalExpressionTest {

    public static StudentRecord getSomehowOneRecord() {
        StudentDatabase sd = StudentDatabase.database();
        return sd.forJMBAG("0000000019");
    }

    @Test
    public void testConditionalExpressionSatisfied() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Gva*n",
                ComparisonOperators.LIKE);
        StudentRecord record = getSomehowOneRecord();
        boolean recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record), // returns lastName from given record
                expr.getStringLiteral() // returns "Gvardijan"
        );
        assertTrue(recordSatisfies);
    }

    @Test
    public void testConditionalExpressionNotSatisfied() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Netocno",
                ComparisonOperators.GREATER_OR_EQUALS);
        StudentRecord record = getSomehowOneRecord();
        boolean recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record), // returns lastName from given record
                expr.getStringLiteral() // returns "Gvardijan"
        );
        assertFalse(recordSatisfies);
    }
}
