/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.views;

import java.util.Collection;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class RootView extends Composite {

	private ListViewer samplesList;
	private Collection<Object> samples;

	/**
	 * @param parent the composite owner
	 */
	public RootView(Composite parent) {
		super(parent, SWT.NONE);
		this.setLayout(new GridLayout(3, false));
		Label samplesLabel = new Label(this, SWT.None);
		samplesLabel.setText("Samples :");
		samplesList = new ListViewer(this);
		samplesList.setContentProvider(new ArrayContentProvider());
	}

	/**
	 * @param samples
	 */
	public void setSamples(Collection<Object> samples) {
		this.samples = samples;
		samplesList.setInput(samples);
	}
	
	/**
	 * @param sample
	 */
	public void addSample(Object sample) {
		samples.add(sample);
		samplesList.refresh();
	}
	
	/**
	 * @param sample
	 */
	public void removeSample(Object sample) {
		samples.remove(sample);
		samplesList.refresh();
	}
	
}
