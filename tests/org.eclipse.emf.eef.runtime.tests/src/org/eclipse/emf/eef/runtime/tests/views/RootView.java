/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.views;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class RootView extends Composite {

	private List eClassifiersList;

	/**
	 * @param parent the composite owner
	 */
	public RootView(Composite parent) {
		super(parent, SWT.NONE);
		this.setLayout(new GridLayout(3, false));
		Label samplesLabel = new Label(this, SWT.None);
		samplesLabel.setText("Samples :");
		eClassifiersList = new List(this, SWT.NONE);
	}

	/**
	 * @param samples
	 */
	public void setEClassifiers(Collection<Object> samples) {
		for (Object object : samples) {
			eClassifiersList.add(object.toString());
		}
	}
	
	/**
	 * @param sample
	 */
	public void addEClassifiers(Object sample) {
		eClassifiersList.add(sample.toString());
	}
	
	/**
	 * @param sample
	 */
	public void removeEClassifiers(Object sample) {
		eClassifiersList.remove(sample.toString());
	}
	
	/**
	 * @return
	 */
	public int eClassifiersSize() {
		return eClassifiersList.getItemCount();
	}
	
}
