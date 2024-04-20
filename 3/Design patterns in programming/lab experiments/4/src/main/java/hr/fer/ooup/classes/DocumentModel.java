package hr.fer.ooup.classes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.ooup.interfaces.DocumentModelListener;
import hr.fer.ooup.interfaces.GraphicalObject;
import hr.fer.ooup.interfaces.GraphicalObjectListener;
public class DocumentModel {

	public final static double SELECTION_PROXIMITY = 10;

	// Kolekcija svih grafičkih objekata:
	private List<GraphicalObject> objects;


    // Kolekcija prijavljenih promatrača:
	private List<DocumentModelListener> documentModelListeners;

	// Promatrač koji će biti registriran nad svim objektima crteža...
	private final GraphicalObjectListener goListener = new GraphicalObjectListener() {
        @Override
        public void graphicalObjectChanged(GraphicalObject go) {
            notifyListeners();
        }

        @Override
        public void graphicalObjectSelectionChanged(GraphicalObject go) {
            notifyListeners();
        }
    };
	
	// Konstruktor...
	public DocumentModel() {
        objects = new ArrayList<>();
        documentModelListeners = new ArrayList<>();
    }

   

	// Brisanje svih objekata iz modela (pazite da se sve potrebno odregistrira)
	// i potom obavijeste svi promatrači modela
	public void clear() {
        Iterator<GraphicalObject> it = objects.iterator();
        while (it.hasNext()) {
            GraphicalObject go = it.next();
            go.removeGraphicalObjectListener(goListener);
            it.remove();
        }
        notifyListeners();
    }

	// Dodavanje objekta u dokument (pazite je li već selektiran; registrirajte model kao promatrača)
	public void addGraphicalObject(GraphicalObject obj) {
        objects.add(obj);
        obj.addGraphicalObjectListener(goListener);
        notifyListeners();
    }
	
	// Uklanjanje objekta iz dokumenta (pazite je li već selektiran; odregistrirajte model kao promatrača)
	public void removeGraphicalObject(GraphicalObject obj) {
        obj.removeGraphicalObjectListener(goListener);
        objects.remove(obj);
        notifyListeners();
    }

	// Vrati nepromjenjivu listu postojećih objekata (izmjene smiju ići samo kroz metode modela)
	public List<GraphicalObject> list() {
        // Read-Only proxy oko kolekcije grafičkih objekata:
	    // private List roObjects = Collections.unmodifiableList(objects);
        return Collections.unmodifiableList(objects);
    }

	// Prijava...
	public void addDocumentModelListener(DocumentModelListener l) {
        documentModelListeners.add(l);
    }
	
	// Odjava...
	public void removeDocumentModelListener(DocumentModelListener l) {
        documentModelListeners.remove(l);
    }

	// Obavještavanje...
	public void notifyListeners() {
        for (DocumentModelListener l : documentModelListeners) {
            l.documentChange();
        }
    }
	
	// Vrati nepromjenjivu listu selektiranih objekata
	public List<GraphicalObject> getSelectedObjects() {
        // Read-Only proxy oko kolekcije selektiranih objekata:
	    // private List roSelectedObjects = Collections.unmodifiableList(selectedObjects);
        List<GraphicalObject> selectedObjects = objects.stream()
                .filter(GraphicalObject::isSelected)
                .collect(Collectors.toList());
        return Collections.unmodifiableList(selectedObjects);
    }

	// Pomakni predani objekt u listi objekata na jedno mjesto kasnije...
	// Time će se on iscrtati kasnije (pa će time možda veći dio biti vidljiv)
	public void increaseZ(GraphicalObject go) {
        int index = objects.indexOf(go);
        if (index == objects.size() - 1) return;
        objects.remove(index);
        objects.add(index + 1, go);
        notifyListeners();
    }
	
	// Pomakni predani objekt u listi objekata na jedno mjesto ranije...
	public void decreaseZ(GraphicalObject go) {
        int index = objects.indexOf(go);
        if (index == 0) return;
        objects.remove(index);
        objects.add(index - 1, go);
        notifyListeners();
    }
	
	// Pronađi postoji li u modelu neki objekt koji klik na točku koja je
	// predana kao argument selektira i vrati ga ili vrati null. Točka selektira
	// objekt kojemu je najbliža uz uvjet da ta udaljenost nije veća od
	// SELECTION_PROXIMITY. Status selektiranosti objekta ova metoda NE dira.
	public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
        GraphicalObject closest = null;
        double minDistance = SELECTION_PROXIMITY;
        for (GraphicalObject go : objects) {
            double distance = go.selectionDistance(mousePoint);
            if (distance < minDistance) {
                minDistance = distance;
                closest = go;
            }
        }
        return closest;
    }

	// Pronađi da li u predanom objektu predana točka miša selektira neki hot-point.
	// Točka miša selektira onaj hot-point objekta kojemu je najbliža uz uvjet da ta
	// udaljenost nije veća od SELECTION_PROXIMITY. Vraća se indeks hot-pointa 
	// kojeg bi predana točka selektirala ili -1 ako takve nema. Status selekcije 
	// se pri tome NE dira.
	public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
        for (int index = 0; index < object.getNumberOfHotPoints(); index++) {
            Point point = object.getHotPoint(index);
            if (point.distance(mousePoint) < SELECTION_PROXIMITY) {
                return index;
            }
        }
        return -1;
    }

}
