/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.provider;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.editor.internal.binding.command.EClassBindingDragAndDropCommand;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.View;

import com.google.common.collect.Lists;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class EClassBindingItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClassBindingItemProvider(AdapterFactory adapterFactory) {
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

			addEClassPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the EClass feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addEClassPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(), getString("_UI_EClassBinding_eClass_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_EClassBinding_eClass_feature", "_UI_EClassBinding_type"), EditingModelPackage.Literals.ECLASS_BINDING__ECLASS, true, false, true, null, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to
	 * deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in
	 * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(EditingModelPackage.Literals.ECLASS_BINDING__VIEWS);
			childrenFeatures.add(EditingModelPackage.Literals.ECLASS_BINDING__PROPERTY_BINDINGS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper
		// feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns EClassBinding.gif. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/EClassBinding"));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
		EClassBinding binding = (EClassBinding) object;
		StringBuilder sb = new StringBuilder();
		if (binding.getEClass() == null) {
			sb.append(getString("_UI_EClassBinding_type")).append(' ');
		}
		if (binding.getEClass() != null && binding.getEClass().getName() != null) {
			sb.append(binding.getEClass().getName());
		} else {
			sb.append("???");
		}
		sb.append(": ");
		for (Iterator<View> iterator = binding.getViews().iterator(); iterator.hasNext();) {
			View view = iterator.next();
			if (view instanceof EObjectView) {
				EObject definition = ((EObjectView) view).getDefinition();
				if (definition instanceof org.eclipse.emf.eef.views.View) {
					sb.append(((org.eclipse.emf.eef.views.View) definition).getName());
				} else {
					sb.append("???");
				}
			} else if (view instanceof JavaView) {
				sb.append(((JavaView) view).getDefinition().toString());
			} else {
				sb.append("???");
			}
			if (iterator.hasNext()) {
				sb.append(',');
			}
		}
		return sb.toString();
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated not
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(EClassBinding.class)) {
		case EditingModelPackage.ECLASS_BINDING__ECLASS:
		case EditingModelPackage.ECLASS_BINDING__VIEWS:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, true));
			return;
		case EditingModelPackage.ECLASS_BINDING__PROPERTY_BINDINGS:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(EditingModelPackage.Literals.ECLASS_BINDING__VIEWS, EditingModelFactory.eINSTANCE.createEObjectView()));

		// newChildDescriptors.add
		// (createChildParameter
		// (EditingModelPackage.Literals.ECLASS_BINDING__VIEWS,
		// EditingModelFactory.eINSTANCE.createJavaView()));

		newChildDescriptors.add(createChildParameter(EditingModelPackage.Literals.ECLASS_BINDING__PROPERTY_BINDINGS, EditingModelFactory.eINSTANCE.createPropertyBinding()));
		newChildDescriptors.add(createChildParameter(EditingModelPackage.Literals.ECLASS_BINDING__PROPERTY_BINDINGS, EditingModelFactory.eINSTANCE.createEStructuralFeatureBinding()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return EditingModelEditPlugin.INSTANCE;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createDragAndDropCommand(org.eclipse.emf.edit.domain.EditingDomain,
	 *      java.lang.Object, float, int, int, java.util.Collection)
	 * @generated NOT
	 */
	@Override
	protected Command createDragAndDropCommand(EditingDomain domain, Object owner, float location, int operations, int operation, Collection<?> collection) {
		final EClassBinding eClassBinding = (EClassBinding) owner;
		if (!isPropertiesEditingModelDrop(collection, eClassBinding.getEditingModel())) {
			return new EClassBindingDragAndDropCommand(domain, eClassBinding, location, operations, operation, collection);
		}
		return super.createDragAndDropCommand(domain, owner, location, operations, operation, collection);
	}

	/**
	 * @param collection
	 * @param propertiesEditingModel
	 * @generated NOT
	 */
	public boolean isPropertiesEditingModelDrop(Collection<?> collection, PropertiesEditingModel propertiesEditingModel) {
		List<String> packageURIs = Lists.newArrayList();
		for (EObject model : propertiesEditingModel.getInvolvedModels()) {
			if (model instanceof EPackage && ((EPackage) model).getNsURI() != null) {
				packageURIs.add(((EPackage) model).getNsURI());
			}
		}
		for (Object object : collection) {
			if (object instanceof EStructuralFeature && packageURIs.contains(((EClassifier) ((EStructuralFeature) object).eContainer()).getEPackage().getNsURI())) {
				return false;
			}
		}
		return true;
	}

}
