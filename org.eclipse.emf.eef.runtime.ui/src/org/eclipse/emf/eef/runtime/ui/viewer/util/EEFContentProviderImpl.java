/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.viewer.util;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFContentProviderImpl implements EEFContentProvider {

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
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.viewer.util.EEFContentProvider#getContext()
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

}
