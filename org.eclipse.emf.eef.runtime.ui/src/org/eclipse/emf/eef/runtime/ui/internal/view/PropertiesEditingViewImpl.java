/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.ViewHelper;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingViewImpl implements PropertiesEditingView {

	private PropertiesEditingComponent editingComponent;
	
	/**
	 * @param editingComponent {@link PropertiesEditingComponent} managing the view.
	 */
	public PropertiesEditingViewImpl(PropertiesEditingComponent editingComponent) {
		this.editingComponent = editingComponent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getEditingComponent()
	 */
	public PropertiesEditingComponent getEditingComponent() {
		return editingComponent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getViewHelper()
	 */
	public ViewHelper getViewHelper() {
		return null;
	}

}
