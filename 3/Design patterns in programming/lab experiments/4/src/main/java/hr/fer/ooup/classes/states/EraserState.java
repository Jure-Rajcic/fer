package hr.fer.ooup.classes.states;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.ooup.classes.DocumentModel;
import hr.fer.ooup.interfaces.GraphicalObject;
import hr.fer.ooup.interfaces.Renderer;


public class EraserState extends IdleState {
	
	private List<Point> mouseTracePoints;
	private DocumentModel document;
	
	public EraserState(DocumentModel document) {
		this.mouseTracePoints = new ArrayList<>(500);
		this.document = document;
	}

    // povlacenjem misa dodajem tocke u listu tocaka, te obavjestajem dokumentListenere koje nanovo pokrecu crtanje
	@Override
	public void mouseDragged(Point mousePoint) {
		mouseTracePoints.add(mousePoint);
		document.notifyListeners();
	}

    // Nako sto se nacrtaju svi objekti nacrtaj i ovu liniju tocaka
    @Override
	public void afterDraw(Renderer r) {
		for(int i = 1; i < mouseTracePoints.size(); i++)
			r.drawLine(mouseTracePoints.get(i-1), mouseTracePoints.get(i), Color.MAGENTA);
	}

    @Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		for(Point p : mouseTracePoints) {
            GraphicalObject go = document.findSelectedGraphicalObject(p);
            if(go != null) 
                document.removeGraphicalObject(go);
		}
		document.notifyListeners(); // sta ako nismo nista selektirali dok smo brisali?
        mouseTracePoints.clear();
	}

	

}
