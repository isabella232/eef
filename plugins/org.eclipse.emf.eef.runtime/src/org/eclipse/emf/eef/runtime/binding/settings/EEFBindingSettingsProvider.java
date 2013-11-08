/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding.settings;

import org.eclipse.emf.ecore.EObject;
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
	<T extends EObject> EEFBindingSettings<T> getBindingSettings(EPackage ePackage);
	
}
