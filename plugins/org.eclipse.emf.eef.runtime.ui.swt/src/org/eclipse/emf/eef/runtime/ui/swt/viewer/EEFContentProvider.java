/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.viewer;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFContentProvider implements IContentProvider {

	private PropertiesEditingContext context;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (context != null) {
			context.dispose();
		}
		if (newInput instanceof PropertiesEditingContext) {
			context = (PropertiesEditingContext)newInput;
		}
	}
	
	/**
	 * @return the viewer {@link PropertiesEditingContext}.
	 */
	public PropertiesEditingContext getContext() {
		return context;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		if (context != null) {
			context.dispose();
		}
	}

	/**
	 * Add a listener to the component of the context.
	 * @param listener {@link PropertiesEditingListener} to add.
	 */
	void addEditingListener(PropertiesEditingListener listener) {
		//TODO: must be delayed
		context.getEditingComponent().addEditingListener(listener);
	}

	/**
	 * Remove a listener to the component of the context.
	 * @param listener {@link PropertiesEditingListener} to remove.
	 */
	void removeEditingListener(PropertiesEditingListener listener) {
		context.getEditingComponent().removeEditingListener(listener);
	}

}
