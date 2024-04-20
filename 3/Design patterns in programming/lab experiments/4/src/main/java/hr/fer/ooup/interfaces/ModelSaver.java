package hr.fer.ooup.interfaces;

import java.io.IOException;
import java.util.List;

public interface ModelSaver {

    public void save(List<String> model) throws IOException;
    
}
