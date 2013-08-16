/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.eef.runtime.editingModel.util.EditingModelAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class EditingModelItemProviderAdapterFactory extends EditingModelAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditingModelItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertiesEditingModelItemProvider propertiesEditingModelItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPropertiesEditingModelAdapter() {
		if (propertiesEditingModelItemProvider == null) {
			propertiesEditingModelItemProvider = new PropertiesEditingModelItemProvider(this);
		}

		return propertiesEditingModelItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClassBindingItemProvider eClassBindingItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.EClassBinding}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEClassBindingAdapter() {
		if (eClassBindingItemProvider == null) {
			eClassBindingItemProvider = new EClassBindingItemProvider(this);
		}

		return eClassBindingItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emf.eef.runtime.editingModel.JavaView} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaViewItemProvider javaViewItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.JavaView}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createJavaViewAdapter() {
		if (javaViewItemProvider == null) {
			javaViewItemProvider = new JavaViewItemProvider(this);
		}

		return javaViewItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyBindingItemProvider propertyBindingItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.PropertyBinding}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPropertyBindingAdapter() {
		if (propertyBindingItemProvider == null) {
			propertyBindingItemProvider = new PropertyBindingItemProvider(this);
		}

		return propertyBindingItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emf.eef.runtime.editingModel.EObjectView} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EObjectViewItemProvider eObjectViewItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.EObjectView}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEObjectViewAdapter() {
		if (eObjectViewItemProvider == null) {
			eObjectViewItemProvider = new EObjectViewItemProvider(this);
		}

		return eObjectViewItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emf.eef.runtime.editingModel.JavaEditor} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaEditorItemProvider javaEditorItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.JavaEditor}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createJavaEditorAdapter() {
		if (javaEditorItemProvider == null) {
			javaEditorItemProvider = new JavaEditorItemProvider(this);
		}

		return javaEditorItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emf.eef.runtime.editingModel.EObjectEditor} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EObjectEditorItemProvider eObjectEditorItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.EObjectEditor}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEObjectEditorAdapter() {
		if (eObjectEditorItemProvider == null) {
			eObjectEditorItemProvider = new EObjectEditorItemProvider(this);
		}

		return eObjectEditorItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.emf.eef.runtime.editingModel.EditingOptions} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EditingOptionsItemProvider editingOptionsItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.emf.eef.runtime.editingModel.EditingOptions}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createEditingOptionsAdapter() {
		if (editingOptionsItemProvider == null) {
			editingOptionsItemProvider = new EditingOptionsItemProvider(this);
		}

		return editingOptionsItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		Adapter adapt = super.adapt(notifier, this);
		if (adapt == null) {
			if (parentAdapterFactory != null) {
				adapt = parentAdapterFactory.adapt(notifier, type);
			}
		}
		return adapt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void dispose() {
		if (propertiesEditingModelItemProvider != null) propertiesEditingModelItemProvider.dispose();
		if (eClassBindingItemProvider != null) eClassBindingItemProvider.dispose();
		if (propertyBindingItemProvider != null) propertyBindingItemProvider.dispose();
		if (javaViewItemProvider != null) javaViewItemProvider.dispose();
		if (eObjectViewItemProvider != null) eObjectViewItemProvider.dispose();
		if (javaEditorItemProvider != null) javaEditorItemProvider.dispose();
		if (eObjectEditorItemProvider != null) eObjectEditorItemProvider.dispose();
		if (editingOptionsItemProvider != null) editingOptionsItemProvider.dispose();
	}

}
