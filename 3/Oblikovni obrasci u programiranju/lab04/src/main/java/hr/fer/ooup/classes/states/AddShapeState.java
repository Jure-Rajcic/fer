package hr.fer.ooup.classes.states;

import java.awt.Point;

import hr.fer.ooup.classes.DocumentModel;
import hr.fer.ooup.interfaces.GraphicalObject;

public class AddShapeState extends IdleState {
	
	private GraphicalObject prototype;
	private DocumentModel model;
	
	public AddShapeState(DocumentModel model, GraphicalObject prototype) {
        this.model = model;
        this.prototype = prototype;
	}

	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		// dupliciraj zapamćeni prototip, pomakni ga na poziciju miša i dodaj u model
        GraphicalObject go = prototype.duplicate();
        go.translate(mousePoint);
        model.addGraphicalObject(go);
	}
}
