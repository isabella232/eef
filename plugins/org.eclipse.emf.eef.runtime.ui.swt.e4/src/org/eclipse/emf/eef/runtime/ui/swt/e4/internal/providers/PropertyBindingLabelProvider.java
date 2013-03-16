/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e4.internal.providers;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
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
	 * @param serviceRegistry the serviceRegistry to set
	 */
	public void setServiceRegistry(EEFServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.edit.ui.provid er.AdapterFactoryLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	@Override
	public Image getColumnImage(Object object, int columnIndex) {
		if (object instanceof EObject && propertyBinding.getSubPropertyBindings().size() > columnIndex) {
			PropertyBinding subBinding = propertyBinding.getSubPropertyBindings().get(columnIndex);
			EStructuralFeature feature = subBinding.getFeature();
			if (feature != null) {
				EObject src = (EObject) object;
				if (!src.eClass().getEAllStructuralFeatures().contains(feature) && serviceRegistry != null) {
					EMFService service = serviceRegistry.getService(EMFService.class, src.eClass().getEPackage());
					feature = service.mapFeature(src, feature);
				}
				Object target = src.eGet(feature);
				return getImage(target);
					
			}
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
			EStructuralFeature feature = subBinding.getFeature();
			if (feature != null) {
				EObject src = (EObject) object;
				if (!src.eClass().getEAllStructuralFeatures().contains(feature) && serviceRegistry != null) {
					EMFService service = serviceRegistry.getService(EMFService.class, src.eClass().getEPackage());
					feature = service.mapFeature(src, feature);
				}
				Object target = src.eGet(feature);
				return getText(target);
					
			}
		}
		return super.getColumnText(object, columnIndex);
	}

}
