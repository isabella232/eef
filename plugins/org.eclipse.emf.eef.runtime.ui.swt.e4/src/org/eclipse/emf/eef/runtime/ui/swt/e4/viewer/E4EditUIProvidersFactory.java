/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e4.viewer;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.ui.swt.e4.internal.providers.PropertyBindingLabelProvider;
import org.eclipse.emf.eef.runtime.ui.swt.e4.services.resources.RegistryProvider;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class E4EditUIProvidersFactory implements EditUIProvidersFactory {

	private AdapterFactory adapterFactory;
	private EEFEditingServiceProvider eefEditingServiceProvider;
	private ImageManager imageManager;
	private RegistryProvider registryProvider;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object element) {
		return true;
	}

	/**
	 * @param eefEditingServiceProvider
	 *            the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * @param imageManager
	 *            the imageManager to set
	 */
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	/**
	 * @param registryProvider
	 *            the registryProvider to set
	 */
	public void setRegistryProvider(RegistryProvider registryProvider) {
		this.registryProvider = registryProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.viewer.EditUIProvidersFactory#getAdapterFactory()
	 */
	public AdapterFactory getAdapterFactory() {
		if (this.adapterFactory == null) {
			this.adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		}
		return this.adapterFactory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.viewer.EditUIProvidersFactory#createContentProvider(org.eclipse.emf.common.notify.AdapterFactory)
	 */
	public IContentProvider createContentProvider(AdapterFactory adapterFactory) {
		AdapterFactory af = adapterFactory;
		if (af == null) {
			af = getAdapterFactory();
		}
		return new AdapterFactoryContentProvider(af);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory#createLabelProvider(org.eclipse.emf.common.notify.AdapterFactory)
	 */
	public ILabelProvider createLabelProvider(AdapterFactory adapterFactory) {
		AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(adapterFactory);
		adapterFactoryLabelProvider.setImageManager(imageManager);
		adapterFactoryLabelProvider.setRegistryProvider(registryProvider);
		return adapterFactoryLabelProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory#createPropertyBindingLabelProvider(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
	 *      org.eclipse.emf.eef.runtime.editingModel.PropertyBinding)
	 */
	public ILabelProvider createPropertyBindingLabelProvider(PropertiesEditingContext editingContext, PropertyBinding propertyBinding) {
		PropertyBindingLabelProvider adapterFactoryLabelProvider = new PropertyBindingLabelProvider(eefEditingServiceProvider, editingContext, propertyBinding);
		adapterFactoryLabelProvider.setImageManager(imageManager);
		adapterFactoryLabelProvider.setRegistryProvider(registryProvider);
		return adapterFactoryLabelProvider;
	}

}
