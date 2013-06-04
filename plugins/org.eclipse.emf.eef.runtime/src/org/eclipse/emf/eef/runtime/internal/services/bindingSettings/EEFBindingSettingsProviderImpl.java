/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.bindingSettings;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.services.bindingSettings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.services.bindingSettings.EEFBindingSettingsProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFBindingSettingsProviderImpl extends EEFServiceProviderImpl<EPackage, EEFBindingSettings> implements EEFBindingSettingsProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.bindingSettings.EEFBindingSettingsProvider#getBindingSettings(org.eclipse.emf.ecore.EPackage)
	 */
	public EEFBindingSettings getBindingSettings(EPackage ePackage) {
		return getService(ePackage);
	}

}
