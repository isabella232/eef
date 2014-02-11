/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e4.internal.providers;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertyBindingLabelProvider extends AdapterFactoryLabelProvider {
	
	private PropertyBinding propertyBinding;
	private EMFServiceProvider emfServiceProvider;

	/**
	 * @param adapterFactory
	 * @param propertyBinding
	 */
	public PropertyBindingLabelProvider(EMFServiceProvider emfServiceProvider, AdapterFactory adapterFactory, PropertyBinding propertyBinding) {
		super(adapterFactory);
		this.emfServiceProvider = emfServiceProvider;
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
			EStructuralFeature feature = subBinding.getFeature();
			if (feature != null) {
				EObject src = (EObject) object;
				if (!src.eClass().getEAllStructuralFeatures().contains(feature)) {
					EMFService service = emfServiceProvider.getEMFService(src.eClass().getEPackage());
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
				if (!src.eClass().getEAllStructuralFeatures().contains(feature)) {
					EMFService service = emfServiceProvider.getEMFService(src.eClass().getEPackage());
					feature = service.mapFeature(src, feature);
				}
				Object target = src.eGet(feature);
				return getText(target);
					
			}
		}
		return super.getColumnText(object, columnIndex);
	}

}
