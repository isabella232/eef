/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.internal.view.helpers.ViewHelperImpl;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.ViewHelper;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingViewImpl implements PropertiesEditingView {

	private PropertiesEditingComponent editingComponent;
	private View viewDescriptor;
	
	/**
	 * @param editingComponent {@link PropertiesEditingComponent} managing the view.
	 */
	public PropertiesEditingViewImpl(PropertiesEditingComponent editingComponent, View viewDescriptor) {
		this.editingComponent = editingComponent;
		this.viewDescriptor = viewDescriptor;
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
		return new ViewHelperImpl(editingComponent);
	}

}
