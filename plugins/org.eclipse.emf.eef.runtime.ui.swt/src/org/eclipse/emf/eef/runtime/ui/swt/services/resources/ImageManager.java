/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.services.resources;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ImageManager {
	
	/**
	 * Return an embedded image of the runtime.
	 * @param resourceLocator {@link ResourceLocator} to use.
	 * @param key ID of the image
	 * @return the associated image.
	 */
	Image getImage(ResourceLocator resourceLocator, String key);

}
