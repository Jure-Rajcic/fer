package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class [QueryParser]
 * 
 * @author Jure Rajcic
 */
public class QueryParser {

    private List<ConditionalExpression> list; // list of conditions for filtering student database
    String[] columns;
    /**
     * constructor for [QueryParser]
     * fills list based on @param input
     */
    public QueryParser(String input) {
        list = new ArrayList<>();
        char[] characters = input.strip().toCharArray();
        for (int i = 0; i < characters.length; i++) {
            if (characters[i] == '"') {
                i++;
                while (characters[i] != '"')
                    i++;
                i++;
            }
            if (i == characters.length)
                break;
            characters[i] = Character.toLowerCase(characters[i]);
        }
        // since input is trimed()
        int start = 0, end = "query".length();
        if (!(end < characters.length && (new String(characters, start, end - start)).equals("query"))) {
            throw new IllegalStateException("query neds to start with keyword [query]");
        }
        start = end;
        boolean parsingQuery = true;

        while (parsingQuery) {
            while (Character.isWhitespace(characters[start]))
                start++;
            end = start;
            while (Character.isLetter(characters[end]) || characters[end] == '-')
                end++;
            String atribute = new String(characters, start, end - start);
            IFieldValueGetter ifvg = switch (atribute) {
                case "jmbag" -> FieldValueGetters.JMBAG;
                case "firstname" -> FieldValueGetters.FIRST_NAME;
                case "lastname" -> FieldValueGetters.LAST_NAME;
                default -> throw new IllegalStateException("unknown atribute");
            };
            start = end;
            while (Character.isWhitespace(characters[start]))
                start++;
            if (Character.isLetter(characters[start])) {
                // LIKE
                end = start + "like".length();
            } else {
                // <, ,<=, !=, =, =>, >
                end = start + 1;
                while ("!<=>".contains(String.format("%c", characters[end])))
                    end++;
            }
            String operator = new String(characters, start, end - start);
            IComparisonOperator ico = switch (operator) {
                case "<" -> ComparisonOperators.LESS;
                case "<=" -> ComparisonOperators.LESS_OR_EQUALS;
                case ">" -> ComparisonOperators.GREATER;
                case ">=" -> ComparisonOperators.GREATER_OR_EQUALS;
                case "=" -> ComparisonOperators.EQUALS;
                case "!=" -> ComparisonOperators.NOT_EQUALS;
                case "like" -> ComparisonOperators.LIKE;
                default -> throw new IllegalStateException("unknown operator");
            };
            start = end;
            while (Character.isWhitespace(characters[start]))
                start++;
            if (characters[start] != '"')
                throw new IllegalStateException("all atribute values must be given inside \"...\"");
            start = start + 1;
            end = start;
            while (characters[end] != '"')
                end++;
            String literal = new String(characters, start, end - start);
            start = end + 1;
            list.add(new ConditionalExpression(ifvg, literal, ico));
            while (start < characters.length && Character.isWhitespace(characters[start]))
                start++;
            if (start == characters.length)
                break;
            end = start + "and".length();
            if (end < characters.length && (new String(characters, start, end - start)).equals("and")) {
                start = end;
            } else {
                // end = start + "with-statistics".length();
                // // System.out.println(end);
                // // System.out.println(characters.length);
                // // System.out.println(new String(characters, start, end - start));
                // if (end <= characters.length && (new String(characters, start, end - start)).equals("with-statistics")) {
                //     start = end;
                //     if (start == characters.length) {
                //         printStatistics = true;
                //         break;
                //     } else {
                //         throw new IllegalStateException("[with-statistics] needs to be last in query");
                //     }
                // } else {
                //     throw new IllegalStateException("invalid query linking, use [AND] keyword");
                // }
                end = start + "showing".length();
                
                if (end < characters.length) {
                    String s = new String(characters, end, characters.length - end);
                    String[] l = s.replaceAll(" +", "").split(",");
                    for(String str : l) {
                        if (! "jmbag lastname firstname".contains(str.toLowerCase()))
                            throw new IllegalArgumentException("only jmbag, lastName or firstName are allowed after showing keyword");
                    }
                    columns = l;
                }
                break;
                // query lastName Like "B*" showing lastName, jmbag, firstName, firstName
            }
            if (start < characters.length && Character.isWhitespace(characters[start])) {
                while (start < characters.length && Character.isWhitespace(characters[start]))
                    start++;
            } else
                throw new IllegalStateException("invalid query after keyword [AND]");
            parsingQuery = start < characters.length;
        }
    }

    /**
     * @returns true if only filter in the list is for JMBAG
     */
    boolean isDirectQuery() {
        if (list.size() != 1)
            return false;
        return list.get(0).getFieldGetter() == FieldValueGetters.JMBAG;
    }

    /**
     * @returns jmbag for direct query
     */
    String getQueriedJMBAG() {
        if (!isDirectQuery())
            throw new IllegalStateException("cant call getQueriedJMBAG for non direct query");
        return list.get(0).getStringLiteral();
    }

    /**
     * @returns list of filters
     */
    List<ConditionalExpression> getQuery() {
        return list;
    }
}
