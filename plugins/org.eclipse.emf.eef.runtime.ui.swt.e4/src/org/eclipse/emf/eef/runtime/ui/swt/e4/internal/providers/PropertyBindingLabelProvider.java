/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e4.internal.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertyBindingLabelProvider extends AdapterFactoryLabelProvider {

	private EEFEditingServiceProvider eefEditingServiceProvider;	
	private PropertiesEditingContext editingContext;
	private final PropertyBinding propertyBinding;


	public PropertyBindingLabelProvider(EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingContext editingContext, PropertyBinding propertyBinding) {
		super(editingContext.getAdapterFactory());
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.editingContext = editingContext;
		this.propertyBinding = propertyBinding;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.edit.ui.provid er.AdapterFactoryLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	@Override
	public Image getColumnImage(Object object, int columnIndex) {
		if (object instanceof EObject && propertyBinding.getSubPropertyBindings().size() > columnIndex) {
			PropertyBinding subBinding = propertyBinding.getSubPropertyBindings().get(columnIndex);
			EObject src = (EObject) object;
			Object target = eefEditingServiceProvider.getEditingService(src).getValueOfSubbinding(editingContext, editingContext.getEditingComponent().getEObject(), subBinding);
			return getImage(target);
		}
		return super.getColumnImage(object, columnIndex);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getColumnText(java.lang.Object, int)
	 */
	@Override
	public String getColumnText(Object object, int columnIndex) {
		if (object instanceof EObject && propertyBinding.getSubPropertyBindings().size() > columnIndex) {
			PropertyBinding subBinding = propertyBinding.getSubPropertyBindings().get(columnIndex);
			EObject src = (EObject) object;
			Object target = eefEditingServiceProvider.getEditingService(src).getValueOfSubbinding(editingContext, editingContext.getEditingComponent().getEObject(), subBinding);
			return getText(target);
		}
		return super.getColumnText(object, columnIndex);
	}

}
