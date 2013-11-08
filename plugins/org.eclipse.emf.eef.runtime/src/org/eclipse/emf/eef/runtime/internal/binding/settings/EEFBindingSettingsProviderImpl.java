/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding.settings;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFBindingSettingsProviderImpl extends EEFServiceProviderImpl<EPackage, EEFBindingSettings<PropertiesEditingModel>> implements EEFBindingSettingsProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider#getBindingSettings(org.eclipse.emf.ecore.EPackage)
	 */
	public EEFBindingSettings<PropertiesEditingModel> getBindingSettings(EPackage ePackage) {
		return getService(ePackage);
	}

}
