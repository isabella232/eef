/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.ui.internal.view.util.ViewSettingsImpl;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewServiceProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewElement;

import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractPropertiesEditingView<T> implements PropertiesEditingView<T> {
	
	private ViewServiceProvider viewServiceProvider;
	protected EEFToolkitProvider eefToolkitProvider;
	
	protected PropertiesEditingComponent editingComponent;
	protected View viewDescriptor;
	
	protected Map<ViewElement, PropertyEditor> propertyEditors;
	protected T contentsComposite;
	protected ViewService service;
	
	/**
	 * Non-parameterized constructor for {@link SectionPropertiesEditingView} purpose.
	 * Mustn't be use otherwise.
	 */
	public AbstractPropertiesEditingView() { }
	
	/**
	 * @param editingComponent {@link PropertiesEditingComponent} managing the view.
	 */
	public AbstractPropertiesEditingView(PropertiesEditingComponent editingComponent, View viewDescriptor) {
		this.viewDescriptor = viewDescriptor;
		this.editingComponent = editingComponent;
		this.propertyEditors = Maps.newHashMap();
		editingComponent.addEditingListener(this);
	}

	/**
	 * @param viewServiceProvider the viewServiceProvider to set
	 */
	public void setViewServiceProvider(ViewServiceProvider viewServiceProvider) {
		this.viewServiceProvider = viewServiceProvider;
	}

	/**
	 * @param eefToolkitProvider the eefToolkitProvider to set
	 */
	public void setToolkitPropertyEditorFactory(EEFToolkitProvider eefToolkitProvider) {
		this.eefToolkitProvider = eefToolkitProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getViewModel()
	 */
	public final View getViewModel() {
		return viewDescriptor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getEditingComponent()
	 */
	public final PropertiesEditingComponent getEditingComponent() {
		return editingComponent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getPropertyEditor(org.eclipse.emf.eef.views.ViewElement)
	 */
	public final PropertyEditor getPropertyEditor(ViewElement editor) {
		return propertyEditors.get(editor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getViewService()
	 */
	public final ViewService getViewService() {
		if (service == null) {
			service = viewServiceProvider.getViewService(viewDescriptor);
		}
		if (editingComponent != null && editingComponent != service.getEditingComponent()) {
			service.setEditingComponent(editingComponent);
		}
		return service;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getViewSettings()
	 */
	public final ViewSettings getViewSettings() {
		return new ViewSettingsImpl();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getContents()
	 */
	public final T getContents() {
		return contentsComposite;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#init()
	 */
	public final void init() {
		UnmodifiableIterator<ElementEditor> elementEditors = Iterators.filter(viewDescriptor.eAllContents(), ElementEditor.class);
		while (elementEditors.hasNext()) {
			ElementEditor elementEditor = elementEditors.next();
			EStructuralFeature bindingFeature = editingComponent.getBinding().feature(elementEditor, editingComponent.getEditingContext().getOptions().autowire());
			EObject editedObject = editingComponent.getEObject();
			EMFService emfService = editingComponent.getEditingContext().getEMFServiceProvider().getEMFService(editedObject.eClass().getEPackage());
			EStructuralFeature feature = emfService.mapFeature(editedObject, bindingFeature);
			if (feature != null) {
				PropertyEditor propertyEditor = propertyEditors.get(elementEditor);
				propertyEditor.init(feature);
			}
			
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#lock()
	 */
	public final void lock() {
		for (PropertyEditor editor : propertyEditors.values()) {
			editor.getPropertyEditorViewer().lock();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#unlock()
	 */
	public final void unlock() {
		for (PropertyEditor editor : propertyEditors.values()) {
			editor.getPropertyEditorViewer().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#dispose()
	 */
	public void dispose() {
		editingComponent.removeEditingListener(this);
		//TODO: dispose View from Notify Service
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public final void firePropertiesChanged(PropertiesEditingEvent event) {
		// Default : Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#setValue(java.lang.Object, java.lang.Object)
	 */
	public final void setValue(Object field, Object value) {
		if (field instanceof ElementEditor) {
			PropertyEditor propertyEditor = propertyEditors.get(field);
			if (propertyEditor instanceof MonovaluedPropertyEditor) {
				((MonovaluedPropertyEditor) propertyEditor).setValue(value);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#unsetValue(java.lang.Object)
	 */
	public final void unsetValue(Object field) {
		if (field instanceof ElementEditor) {
			PropertyEditor propertyEditor = propertyEditors.get(field);
			if (propertyEditor instanceof MonovaluedPropertyEditor) {
				((MonovaluedPropertyEditor) propertyEditor).unsetValue();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#addValue(java.lang.Object, java.lang.Object)
	 */
	public final void addValue(Object field, Object value) {
		if (field instanceof ElementEditor) {
			PropertyEditor propertyEditor = propertyEditors.get(field);
			if (propertyEditor instanceof MultivaluedPropertyEditor) {
				((MultivaluedPropertyEditor) propertyEditor).addValue(value);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#addAllValues(java.lang.Object, java.util.Collection)
	 */
	public final void addAllValues(Object field, Collection<?> values) {
		if (field instanceof ElementEditor) {
			PropertyEditor propertyEditor = propertyEditors.get(field);
			if (propertyEditor instanceof MultivaluedPropertyEditor) {
				((MultivaluedPropertyEditor) propertyEditor).addAllValues(values);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#removeValue(java.lang.Object, java.lang.Object)
	 */
	public final void removeValue(Object field, Object value) {
		if (field instanceof ElementEditor) {
			PropertyEditor propertyEditor = propertyEditors.get(field);
			if (propertyEditor instanceof MultivaluedPropertyEditor) {
				((MultivaluedPropertyEditor) propertyEditor).removeValue(value);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#removeAllValues(java.lang.Object, java.util.Collection)
	 */
	public final void removeAllValues(Object field, Collection<?> values) {
		if (field instanceof ElementEditor) {
			PropertyEditor propertyEditor = propertyEditors.get(field);
			if (propertyEditor instanceof MultivaluedPropertyEditor) {
				((MultivaluedPropertyEditor) propertyEditor).removeAllValues(values);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#moveValue(java.lang.Object, java.lang.Object, int)
	 */
	public final void moveValue(Object field, Object value, int newIndex) {
		if (field instanceof ElementEditor) {
			PropertyEditor propertyEditor = propertyEditors.get(field);
			if (propertyEditor instanceof MultivaluedPropertyEditor) {
				((MultivaluedPropertyEditor) propertyEditor).moveValue(value, newIndex);
			}
		}
	}

}
