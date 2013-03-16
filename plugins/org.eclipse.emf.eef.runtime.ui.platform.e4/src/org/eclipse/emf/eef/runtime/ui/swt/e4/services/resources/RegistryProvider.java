/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e4.services.resources;

import org.eclipse.emf.edit.ui.provider.ExtendedColorRegistry;
import org.eclipse.emf.edit.ui.provider.ExtendedFontRegistry;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface RegistryProvider extends EEFService<Object> {
	
	/**
	 * @return an EMF {@link ExtendedColorRegistry}.
	 */
	ExtendedColorRegistry getColorRegistry();
	
	/**
	 * @return an EMF {@link ExtendedFontRegistry}.
	 */
	ExtendedFontRegistry getFontRegistry();
	
	/**
	 * @return an EMF {@link ExtendedImageRegistry}.
	 */
	ExtendedImageRegistry getImageRegistry();

}
