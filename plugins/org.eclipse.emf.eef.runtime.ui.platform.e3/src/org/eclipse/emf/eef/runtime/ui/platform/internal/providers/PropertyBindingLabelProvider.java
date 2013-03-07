/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.internal.providers;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertyBindingLabelProvider extends AdapterFactoryLabelProvider {
	
	private PropertyBinding propertyBinding;

	/**
	 * @param adapterFactory
	 * @param propertyBinding
	 */
	public PropertyBindingLabelProvider(AdapterFactory adapterFactory, PropertyBinding propertyBinding) {
		super(adapterFactory);
		this.propertyBinding = propertyBinding;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.edit.ui.provid er.AdapterFactoryLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	@Override
	public Image getColumnImage(Object object, int columnIndex) {
		propertyBinding.getFeature();
		return super.getColumnImage(object, columnIndex);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getColumnText(java.lang.Object, int)
	 */
	@Override
	public String getColumnText(Object object, int columnIndex) {
		return super.getColumnText(object, columnIndex);
	}

}
