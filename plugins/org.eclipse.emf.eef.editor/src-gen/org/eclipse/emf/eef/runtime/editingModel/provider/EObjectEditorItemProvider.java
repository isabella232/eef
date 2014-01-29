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
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.views.ElementEditor;

import com.google.common.collect.Lists;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.EObjectEditor} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class EObjectEditorItemProvider
	extends EditorItemProvider
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
	public EObjectEditorItemProvider(AdapterFactory adapterFactory) {
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

			addDefinitionPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Definition propertyBinding.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void addDefinitionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(new ItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_EObjectEditor_definition_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_EObjectEditor_definition_feature", "_UI_EObjectEditor_type"),
				 EditingModelPackage.Literals.EOBJECT_EDITOR__DEFINITION,
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
						EObjectEditor eObjectEditor = ((EObjectEditor)object);
						if (eObjectEditor.eContainer() instanceof PropertyBinding) {
							List<ElementEditor> usableEditors = Lists.newArrayList();
							if (eObjectEditor.eContainer().eContainer() instanceof EClassBinding) {
								EClassBinding eClassBinding = (EClassBinding) eObjectEditor.eContainer().eContainer();
								for (View view : eClassBinding.getViews()) {
									if (view instanceof EObjectView && ((EObjectView) view).getDefinition() instanceof org.eclipse.emf.eef.views.View) {
										org.eclipse.emf.eef.views.View uiView = (org.eclipse.emf.eef.views.View) ((EObjectView) view).getDefinition();
										TreeIterator<EObject> eAllContents = uiView.eAllContents();
										while (eAllContents.hasNext()) {
											EObject next = eAllContents.next();
											if (next instanceof ElementEditor) {
												usableEditors.add((ElementEditor) next);
											}
										}
									}
								}
							} else if (eObjectEditor.eContainer().eContainer() instanceof PropertyBinding) {
								PropertyBinding propertyBinding = (PropertyBinding) eObjectEditor.eContainer().eContainer();
								if (propertyBinding.getEditor() instanceof EObjectEditor && ((EObjectEditor)propertyBinding.getEditor()).getDefinition() instanceof ElementEditor) {
									usableEditors.addAll((Collection<? extends ElementEditor>) ((EObjectEditor)propertyBinding.getEditor()).getDefinition().eContents());
								}
								
							}
							List<ElementEditor> result = Lists.newArrayList();
							Collection<?> choiceOfValues = super.getChoiceOfValues(object);
							for (Object value : choiceOfValues) {
								if (value instanceof ElementEditor && usableEditors.contains(value)) {
									result.add((ElementEditor) value);
								}
							}
							return result;
						}
						return super.getChoiceOfValues(object);
					}
				
			});
	}

	/**
	 * This returns EObjectEditor.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		EObjectEditor editor = (EObjectEditor) object;
		if (editor.getDefinition() != null) {
			IItemLabelProvider labelProvider = (IItemLabelProvider) getAdapterFactory().adapt(editor.getDefinition(), IItemLabelProvider.class);
			Object image = labelProvider.getImage(editor.getDefinition());
			if (image != null) {
				return image;
			} 
		}
		return overlayImage(object, getResourceLocator().getImage("full/obj16/EObjectEditor"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		EObjectEditor editor = (EObjectEditor) object;
		if (editor.getDefinition() != null) {
			IItemLabelProvider labelProvider = (IItemLabelProvider) getAdapterFactory().adapt(editor.getDefinition(), IItemLabelProvider.class);
			String text = labelProvider.getText(editor.getDefinition());
			if (text != null) {
				return text;
			} 
		}
		return getString("_UI_EObjectEditor_type") + " with no editor definition";
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
	}

}
