package hr.fer.ooup.interfaces;

import java.io.IOException;
import java.util.Stack;

public interface ModelLoader {
    public void load(Stack<GraphicalObject> stack) throws IOException;

}
