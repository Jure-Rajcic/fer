package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * class ElementFunction represents child of Element
 * primary purpose is to store function names 
 * 
 * @author Jure Rajcic
 */
public class ElementFunction extends Element {

    private String name;

    /**
     * Creates new instance of ElementFunction with the given @param name
     * checks validation for the given @param name
     * 
     * @throws SmartScriptParserException if name is not valid
     *                                    valid name : starts with @ and letter,
     *                                    after that we can have 0 or more leter
     *                                    and/or digits and/or underscores
     */
    public ElementFunction(String name) {
        char[] characters = name.toCharArray();
        if (!(characters[0] == '@' && Character.isLetter(characters[1])))
            throw new SmartScriptParserException("invalid function name");
        for (int i = 2; i < characters.length; i++) {
            char c = characters[i];
            if (!(Character.isLetter(c) || Character.isDigit(c) || c == '_'))
                throw new SmartScriptParserException("Invalid function name");
        }
        this.name = name;
    }

    /**
     * @return function name in format [@fun_name]
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return function name in string format [@fun_name]
     */
    @Override
    public String asText() {
        return this.name;
    }

}
