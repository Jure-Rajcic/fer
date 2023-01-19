package hr.fer.oprpp1.custom.scripting.elems;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
/**
 * class ElementVariable represents child of Element
 * primary purpose is to store variable names
 * 
 * @author Jure Rajcic
 */
public class ElementVariable extends Element {
    private String name;

    /**
     * Creates new instance of ElementVariable with the given @param name
     * checks validation for the given @param name
     * if name is not valid @throws SmartScriptParserException
     * valid name : starts with letter, after
     * that we can have 0 or more leter and/or
     * digits and/or underscores
     */
    public ElementVariable(String name) {
        char[] characters = name.toCharArray();
        if (!Character.isLetter(characters[0]))
            throw new SmartScriptParserException("Invalid variable name");
        for (char c : characters)
            if (!(Character.isLetter(c) || Character.isDigit(c) || c == '_'))
                throw new SmartScriptParserException("Invalid variable name");
        this.name = name;
    }

    /**
     * @return variable name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return variable name in string format
     */
    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String asText() {
        return getName();
    }

}
