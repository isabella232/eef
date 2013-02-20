/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.viewer;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.ui.viewer.EditUIProvidersFactory;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class E3EditUIProvidersFactory implements EditUIProvidersFactory {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.viewer.EditUIProvidersFactory#createContentProvider(org.eclipse.emf.common.notify.AdapterFactory)
	 */
	public IContentProvider createContentProvider(AdapterFactory adapterFactory) {
		return new AdapterFactoryContentProvider(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.viewer.EditUIProvidersFactory#createLabelProvider(org.eclipse.emf.common.notify.AdapterFactory)
	 */
	public ILabelProvider createLabelProvider(AdapterFactory adapterFactory) {
		return new AdapterFactoryLabelProvider(adapterFactory);
	}

	

}
