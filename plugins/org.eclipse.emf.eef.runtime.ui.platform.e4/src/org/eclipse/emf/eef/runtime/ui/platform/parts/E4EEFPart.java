 
package org.eclipse.emf.eef.runtime.ui.platform.parts;


import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class E4EEFPart {
	
	
	private EEFViewer viewer;


	@Inject
	public E4EEFPart(EEFServiceRegistry serviceRegistry, Composite parent) {

		// Initializing model
		viewer = new EEFViewer(parent, SWT.NONE);
		viewer.setContentProvider(new EEFContentProvider());
		
	}

	/**
	 * @return the viewer
	 */
	public EEFViewer getViewer() {
		return viewer;
	}

	@Focus
	public void onFocus() {
		viewer.getControl().setFocus();
	}

}