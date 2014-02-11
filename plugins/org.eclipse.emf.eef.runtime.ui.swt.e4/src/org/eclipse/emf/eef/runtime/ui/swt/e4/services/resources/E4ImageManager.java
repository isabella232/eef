/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e4.services.resources;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class E4ImageManager implements ImageManager {
	
	private RegistryProvider registryProvider;
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object element) {
		return true;
	}

	/**
	 * @param registryProvider the registryProvider to set
	 */
	public void setRegistryProvider(RegistryProvider registryProvider) {
		this.registryProvider = registryProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.images.ImageManager#getImage(org.eclipse.emf.common.util.ResourceLocator, java.lang.String)
	 */
	public Image getImage(ResourceLocator resourceLocator, String key) {
		Object image = resourceLocator.getImage(key);
		ExtendedImageRegistry imageRegistry = registryProvider.getImageRegistry();
		return imageRegistry.getImage(image);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager#getImageFromObject(java.lang.Object)
	 */
	public Image getImageFromObject(Object object) {
		ExtendedImageRegistry imageRegistry = registryProvider.getImageRegistry();
		return imageRegistry.getImage(object);
	}

}
