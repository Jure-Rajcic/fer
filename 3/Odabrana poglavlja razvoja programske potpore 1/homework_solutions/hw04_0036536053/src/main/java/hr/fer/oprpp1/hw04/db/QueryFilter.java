package hr.fer.oprpp1.hw04.db;

import java.util.List;


/**
 * class [QueryFilter]
 * @author Jure Rajcic
 */
public class QueryFilter implements IFilter {

    private List<ConditionalExpression> list; // list of conditions for filtering student database

    public QueryFilter(List<ConditionalExpression> list) {
        this.list = list;
    }

    /**
     * @returns boolean value for @param record when passed to all "filters" in list
     */
    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression expr : list) {
            if (!expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral()))
                return false;
        }
        return true;
    }

}
