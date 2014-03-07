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
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.query.QueryFactory;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertyBindingItemProvider
	extends ItemProviderAdapter
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
	public PropertyBindingItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
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
			childrenFeatures.add(EditingModelPackage.Literals.PROPERTY_BINDING__EDITOR);
			childrenFeatures.add(EditingModelPackage.Literals.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS);
			childrenFeatures.add(EditingModelPackage.Literals.PROPERTY_BINDING__SETTINGS);
			childrenFeatures.add(EditingModelPackage.Literals.PROPERTY_BINDING__GETTER);
			childrenFeatures.add(EditingModelPackage.Literals.PROPERTY_BINDING__VALUE_PROVIDER);
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
	 * This returns PropertyBinding.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/PropertyBinding"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_PropertyBinding_type");
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

		switch (notification.getFeatureID(PropertyBinding.class)) {
			case EditingModelPackage.PROPERTY_BINDING__EDITOR:
			case EditingModelPackage.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS:
			case EditingModelPackage.PROPERTY_BINDING__SETTINGS:
			case EditingModelPackage.PROPERTY_BINDING__GETTER:
			case EditingModelPackage.PROPERTY_BINDING__VALUE_PROVIDER:
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
	 * @not-generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(EditingModelPackage.Literals.PROPERTY_BINDING__EDITOR,
				 EditingModelFactory.eINSTANCE.createEObjectEditor()));

		newChildDescriptors.add
		(createChildParameter
				(EditingModelPackage.Literals.PROPERTY_BINDING__EDITOR,
						EditingModelFactory.eINSTANCE.createJavaEditor()));
		
		newChildDescriptors.add
			(createChildParameter
				(EditingModelPackage.Literals.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS,
				 EditingModelFactory.eINSTANCE.createPropertyBinding()));

		newChildDescriptors.add
			(createChildParameter
				(EditingModelPackage.Literals.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS,
				 EditingModelFactory.eINSTANCE.createMonoValuedPropertyBinding()));

		newChildDescriptors.add
			(createChildParameter
				(EditingModelPackage.Literals.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS,
				 EditingModelFactory.eINSTANCE.createMultiValuedPropertyBinding()));

		newChildDescriptors.add
			(createChildParameter
				(EditingModelPackage.Literals.PROPERTY_BINDING__SUB_PROPERTY_BINDINGS,
				 EditingModelFactory.eINSTANCE.createEStructuralFeatureBinding()));

		newChildDescriptors.add
			(createChildParameter
				(EditingModelPackage.Literals.PROPERTY_BINDING__SETTINGS,
				 EditingModelFactory.eINSTANCE.createEReferenceFilter()));

		newChildDescriptors.add
			(createChildParameter
				(EditingModelPackage.Literals.PROPERTY_BINDING__GETTER,
				 QueryFactory.eINSTANCE.createJavaBody()));

		newChildDescriptors.add
			(createChildParameter
				(EditingModelPackage.Literals.PROPERTY_BINDING__VALUE_PROVIDER,
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
			childFeature == EditingModelPackage.Literals.PROPERTY_BINDING__VALUE_PROVIDER;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return EditingModelEditPlugin.INSTANCE;
	}

}
