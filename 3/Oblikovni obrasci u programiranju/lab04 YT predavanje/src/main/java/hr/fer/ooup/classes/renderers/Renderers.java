package hr.fer.ooup.classes.renderers;

import hr.fer.ooup.interfaces.Renderer;

public class Renderers {

    private static Renderer defaultRenderer;

    public static Renderer getDefaultRenderer() {
        return defaultRenderer;
    }

    public static void setRenderer(Renderer defaultRenderer) {
        Renderers.defaultRenderer = defaultRenderer;
    }
    
}
