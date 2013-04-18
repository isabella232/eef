/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.editing;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingService;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFEditingServiceProviderImpl extends EEFServiceProviderImpl<EObject, EEFEditingService> implements EEFEditingServiceProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editing.EEFEditingServiceProvider#getEditingService(org.eclipse.emf.ecore.EObject)
	 */
	public EEFEditingService getEditingService(EObject eObject) {
		return getService(eObject);
	}

}
