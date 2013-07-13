/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.resources;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class E3ImageManager implements ImageManager {
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.services.resources.ImageManager#getImage(org.eclipse.emf.common.util.ResourceLocator, java.lang.String)
	 */
	public Image getImage(ResourceLocator resourceLocator, String key) {
		Object image = resourceLocator.getImage(key);
		return ExtendedImageRegistry.getInstance().getImage(image);
	}

}
