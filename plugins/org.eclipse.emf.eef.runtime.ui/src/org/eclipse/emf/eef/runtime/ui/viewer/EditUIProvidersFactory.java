/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.viewer;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditUIProvidersFactory {
	
	/**
	 * Creates an {@link IContentProvider} for EEF viewers.
	 * @param adapterFactory a contextual {@link AdapterFactory}.
	 * @return the content provider to use in EEF viewers.
	 */
	IContentProvider createContentProvider(AdapterFactory adapterFactory);

	/**
	 * Create an {@link ILabelProvider} for EEF viewers.
	 * @param adapterFactory a contextual {@link AdapterFactory}.
	 * @return the label provider to use in EEF viewers.
	 */
	ILabelProvider createLabelProvider(AdapterFactory adapterFactory);
	
}
