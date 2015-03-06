/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.eef.editor.internal.filters.utils.EStructuralFeatureBindingChoiceFilter;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.JavaEditor;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class EStructuralFeatureBindingItemProvider extends PropertyBindingItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EStructuralFeatureBindingItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addFeaturePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Feature feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	protected void addFeaturePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(new ItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_EStructuralFeatureBinding_feature_feature"), getString("_UI_PropertyDescriptor_description",
				"_UI_EStructuralFeatureBinding_feature_feature", "_UI_EStructuralFeatureBinding_type"), EditingModelPackage.Literals.ESTRUCTURAL_FEATURE_BINDING__FEATURE, true, false, true, null, null, null) {
			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getChoiceOfValues(java.lang.Object)
			 */
			@Override
			public Collection<?> getChoiceOfValues(Object object) {
				PropertyBinding propertyBinding = ((PropertyBinding) object);
				return new EStructuralFeatureBindingChoiceFilter(propertyBinding.eContainer()).filterPropertyBindingChoiceOfValues(super.getChoiceOfValues(object));
			}

		});
	}

	/**
	 * This returns EStructuralFeatureBinding.gif.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/EStructuralFeatureBinding"));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean shouldComposeCreationImage() {
		return true;
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		EStructuralFeatureBinding binding = (EStructuralFeatureBinding) object;
		StringBuilder sb = new StringBuilder();
		if (binding.getFeature() == null) {
			sb.append(getString("_UI_EStructuralFeatureBinding_type")).append(' ');
		}
		if (binding.getFeature() != null) {
			sb.append(binding.getFeature().getName());
		} else {
			sb.append("???");
		}
		sb.append(": ");
		if (binding.getEditor() != null) {
			if (binding.getEditor() instanceof EObjectEditor) {
				EObject definition = ((EObjectEditor) binding.getEditor()).getDefinition();
				if (definition instanceof ElementEditor) {
					sb.append(((ElementEditor) definition).getName());
				} else {
					sb.append("???");
				}
			} else if (binding.getEditor() instanceof JavaEditor) {
				sb.append(binding.getEditor().toString());
			} else {
				sb.append("???");
			}
		}
		return sb.toString();
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated not
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		switch (notification.getFeatureID(PropertyBinding.class)) {
		case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__FEATURE:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

}
