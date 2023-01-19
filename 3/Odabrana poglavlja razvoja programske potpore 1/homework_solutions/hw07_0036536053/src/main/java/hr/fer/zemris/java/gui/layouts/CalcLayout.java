package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * class CalcLayout models positions and arrangement calculator components
 * 
 * @author Jure Rajcic
 *
 */
public class CalcLayout implements LayoutManager2 {

	private int spaceBetweenComponents;
	private Map<Component, RCPosition> map;

	/**
	 * default constructor
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * constructor for sreating components that are equaly spaced apart from each
	 * other
	 * 
	 * @param margin separation between components
	 */
	public CalcLayout(int margin) {
		super();
		this.spaceBetweenComponents = margin;
		this.map = new HashMap<>();
	}

	/*
	 * U metodi addLayoutComponent(String name, Component comp) slobodno bacite
	 * iznimku
	 * UnsupportedOperationException; naime, ne želimo da itko poziva tu metodu.
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("ne želimo da itko poziva tu metodu");
	}

	/**
	 * U metodi removeLayoutComponent(Component comp) uklonite sve informacije koje ste imali o predanoj
komponenti jer ona više nije u kontejneru, pa isti to ovom metodom dojavljuje i Vama.
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		this.map.remove(comp);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, LayoutSize.MINIMUM);
	}

	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, LayoutSize.PREFERED);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize(target, LayoutSize.MAXIMUM);
	}


	private Dimension getLayoutSize(Container parent, LayoutSize ls) {
		Dimension largestCellDimension = new Dimension(0, 0);
		
		int count = parent.getComponentCount();
		for (int i = 0; i < count; i++) {
			Component component = parent.getComponent(i);
			RCPosition position = this.map.get(component);
			if (component != null && position != null) {
				Dimension size = switch (ls) {
					case MINIMUM -> component.getMinimumSize();
					case PREFERED -> component.getPreferredSize();
					case MAXIMUM -> component.getMaximumSize();
				};
				if (position.getRow() == 1 && position.getColumn() == 1) {
					largestCellDimension.width = Math.max(largestCellDimension.width, (size.width - 4 * this.spaceBetweenComponents) / 5);
					largestCellDimension.height = Math.max(largestCellDimension.height, size.height);
				} else {
					largestCellDimension.width = Math.max(largestCellDimension.width, size.width);
					largestCellDimension.height = Math.max(largestCellDimension.height, size.height);
				}
			}
		}
		Insets insets = parent.getInsets();
		largestCellDimension.width = 7 * largestCellDimension.width + 6 * this.spaceBetweenComponents + insets.left + insets.right;
		largestCellDimension.height = 5 * largestCellDimension.height + 4 * this.spaceBetweenComponents + insets.top + insets.bottom;
		return largestCellDimension;
	}







	

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		Dimension dimension = parent.getSize();
		
		// calculating cell dimensions
		double width = dimension.getWidth() - insets.left - insets.right;
		double cellWidth = (width - 6 * this.spaceBetweenComponents) / 7.;
		double height = dimension.getHeight() - insets.top - insets.bottom;
		double cellHeight = (height - 4 * this.spaceBetweenComponents) / 5.;
		
		int count = parent.getComponentCount();
		for (int i = 0; i < count; i++) {
			Component component = parent.getComponent(i);
			RCPosition position = this.map.get(component);

			if (position != null) {
				if (position.getColumn() == 1 && position.getRow() == 1) {
					component.setBounds((int) insets.left, (int) insets.top, (int) cellWidth * 5 + 4 * this.spaceBetweenComponents, (int) cellHeight);
				} else {
					double x = insets.left + (position.getColumn() - 1.) * (cellWidth + this.spaceBetweenComponents);
					double y = insets.top + (position.getRow() - 1.) * (cellHeight + this.spaceBetweenComponents);
					component.setBounds((int) x, (int) y, (int) cellWidth, (int) cellHeight);
				}
			}
		}
	}

	// poziva ju kontenjer u demoFrame1 metodom add

	@Override
	public void addLayoutComponent(Component comp, Object rc) {
		if (rc == null || comp == null)
			throw new NullPointerException("component or RCposition information is null");
		if (!(rc instanceof RCPosition || rc instanceof String))
			throw new IllegalArgumentException("RCPosition information can nly be String or RCPosition object.");
		
		RCPosition position = rc instanceof String ? RCPosition.parse((String) rc) : (RCPosition) rc;
		for (RCPosition value : this.map.values()) {
			if (value.getRow() == position.getRow() && value.getColumn() == position.getColumn())
				throw new CalcLayoutException("dvije komponente pod istim ograničenjem!!!");
		}
		this.map.put(comp, position);
	}

	/*
	 * U metodama getLayoutAlignmentX i getLayoutAlignmentY možete vratiti 0.
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	

	
}
