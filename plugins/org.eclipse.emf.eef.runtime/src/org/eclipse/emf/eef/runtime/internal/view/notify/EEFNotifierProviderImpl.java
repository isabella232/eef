/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.view.notify;

import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifierProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFNotifierProviderImpl extends EEFServiceProviderImpl<Object, EEFNotifier> implements EEFNotifierProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifierProvider#getNotifier(java.lang.Object)
	 */
	public EEFNotifier getNotifier(Object view) {
		return getService(view);
	}

}
