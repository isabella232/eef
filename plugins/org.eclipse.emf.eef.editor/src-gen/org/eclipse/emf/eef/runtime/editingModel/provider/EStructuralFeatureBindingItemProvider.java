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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.eef.editor.internal.filters.utils.EStructuralFeatureBindingChoiceFilter;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.JavaEditor;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.query.QueryFactory;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class EStructuralFeatureBindingItemProvider
	extends MonoValuedPropertyBindingItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeatureBindingItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * This adds a property descriptor for the Feature feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void addFeaturePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(new ItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_EStructuralFeatureBinding_feature_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_EStructuralFeatureBinding_feature_feature", "_UI_EStructuralFeatureBinding_type"),
				 EditingModelPackage.Literals.ESTRUCTURAL_FEATURE_BINDING__FEATURE,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null) {
				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.edit.provider.ItemPropertyDescriptor#getChoiceOfValues(java.lang.Object)
				 */
				@Override
				public Collection<?> getChoiceOfValues(Object object) {
					PropertyBinding propertyBinding = ((PropertyBinding)object);
					return new EStructuralFeatureBindingChoiceFilter(propertyBinding.eContainer()).filterPropertyBindingChoiceOfValues(super.getChoiceOfValues(object));
				}
			
				
			});
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(EditingModelPackage.Literals.MULTI_VALUED_PROPERTY_BINDING__ADDER);
			childrenFeatures.add(EditingModelPackage.Literals.MULTI_VALUED_PROPERTY_BINDING__REMOVER);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns EStructuralFeatureBinding.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/EStructuralFeatureBinding"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		EStructuralFeatureBinding binding = (EStructuralFeatureBinding) object;
		StringBuilder sb = new StringBuilder(getString("_UI_EStructuralFeatureBinding_type")).append(' ');
		if (binding.getFeature() != null) {
			sb.append(binding.getFeature().getName());
		} else {
			sb.append("???");
		}
		sb.append(" <-> ");
		if (binding.getEditor() != null) {
			if (binding.getEditor() instanceof EObjectEditor) {
				EObject definition = ((EObjectEditor)binding.getEditor()).getDefinition();
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(EStructuralFeatureBinding.class)) {
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__ADDER:
			case EditingModelPackage.ESTRUCTURAL_FEATURE_BINDING__REMOVER:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(EditingModelPackage.Literals.MULTI_VALUED_PROPERTY_BINDING__ADDER,
				 QueryFactory.eINSTANCE.createJavaBody()));

		newChildDescriptors.add
			(createChildParameter
				(EditingModelPackage.Literals.MULTI_VALUED_PROPERTY_BINDING__REMOVER,
				 QueryFactory.eINSTANCE.createJavaBody()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == EditingModelPackage.Literals.PROPERTY_BINDING__GETTER ||
			childFeature == EditingModelPackage.Literals.PROPERTY_BINDING__VALUE_PROVIDER ||
			childFeature == EditingModelPackage.Literals.MONO_VALUED_PROPERTY_BINDING__SETTER ||
			childFeature == EditingModelPackage.Literals.MONO_VALUED_PROPERTY_BINDING__UNSETTER ||
			childFeature == EditingModelPackage.Literals.MULTI_VALUED_PROPERTY_BINDING__ADDER ||
			childFeature == EditingModelPackage.Literals.MULTI_VALUED_PROPERTY_BINDING__REMOVER;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
