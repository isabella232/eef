/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.services.resources;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.ui.services.resources.ImageManager;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class E4ImageManager extends AbstractEEFService<Object> implements ImageManager {
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.images.ImageManager#getImage(org.eclipse.emf.common.util.ResourceLocator, java.lang.String)
	 */
	public Image getImage(ResourceLocator resourceLocator, String key) {
		Object image = resourceLocator.getImage(key);
		ExtendedImageRegistry imageRegistry = getServiceRegistry().getService(RegistryProvider.class, this).getImageRegistry();
		return imageRegistry.getImage(image);
	}

}
