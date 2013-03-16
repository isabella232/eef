/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e4.services.resources.impl;

import org.eclipse.emf.edit.ui.provider.ExtendedColorRegistry;
import org.eclipse.emf.edit.ui.provider.ExtendedFontRegistry;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.ui.swt.e4.services.resources.RegistryProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * TODO: In a better world, the component disabling should dispose the providers.
 */
public class RegistryProviderImpl extends AbstractEEFService<Object> implements RegistryProvider {
	
	private ExtendedColorRegistry colorRegistry;
	private ExtendedFontRegistry fontRegistry;
	private ExtendedImageRegistry imageRegistry;
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e4.services.resources.RegistryProvider#getColorRegistry()
	 */
	public ExtendedColorRegistry getColorRegistry() {
		if (colorRegistry == null) {
			colorRegistry = new ExtendedColorRegistry();
			colorRegistry.setServiceRegistry(getServiceRegistry());
		}
		return colorRegistry;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e4.services.resources.RegistryProvider#getFontRegistry()
	 */
	public ExtendedFontRegistry getFontRegistry() {
		if (fontRegistry == null) {
			fontRegistry = new ExtendedFontRegistry();
			fontRegistry.setServiceRegistry(getServiceRegistry());
		}
		return fontRegistry;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e4.services.resources.RegistryProvider#getImageRegistry()
	 */
	public ExtendedImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = new ExtendedImageRegistry();
			imageRegistry.setServiceRegistry(getServiceRegistry());
		}
		return imageRegistry;
	}

}
