/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.bindingSettings;

import org.eclipse.emf.ecore.EPackage;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFBindingSettingsProvider {

	/**
	 * Returns the most appropriate binding settings for the given package.
	 * @param ePackage the {@link EPackage} to process.
	 * @return the most appropriate {@link EEFBindingSettings} for the given package. 
	 */
	EEFBindingSettings getBindingSettings(EPackage ePackage);
	
}
