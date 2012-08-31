/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.ui.internal.view.util.ViewSettingsImpl;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProviderRegistry;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.ViewSettings;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.swt.widgets.Composite;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractPropertiesEditingView implements PropertiesEditingView {

	protected PropertiesEditingComponent editingComponent;
	protected View viewDescriptor;
	protected PropertyEditorProviderRegistry propertyEditorProviderRegistry;
	
	protected Map<ElementEditor, PropertyEditor> propertyEditors;
	protected Composite contentsComposite;
	protected ViewServiceRegistry viewServiceRegistry; 
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
		this.propertyEditors = new HashMap<ElementEditor, PropertyEditor>();
		editingComponent.addEditingListener(this);
	}

	/**
	 * @param viewServiceRegistry the viewServiceRegistry to set
	 */
	public void setViewServiceRegistry(ViewServiceRegistry viewServiceRegistry) {
		this.viewServiceRegistry = viewServiceRegistry;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getEditingComponent()
	 */
	public PropertiesEditingComponent getEditingComponent() {
		return editingComponent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getViewService()
	 */
	public ViewService getViewService() {
		if (service == null) {
			service = viewServiceRegistry.getServiceForView(viewDescriptor);
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
	public ViewSettings getViewSettings() {
		return new ViewSettingsImpl();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#setPropertyEditorProviderRegistry(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProviderRegistry)
	 */
	public void setPropertyEditorProviderRegistry(PropertyEditorProviderRegistry propertyEditorProviderRegistry) {
		this.propertyEditorProviderRegistry = propertyEditorProviderRegistry;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getContents()
	 */
	public Composite getContents() {
		return contentsComposite;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#init()
	 */
	public void init() {
		UnmodifiableIterator<ElementEditor> elementEditors = Iterators.filter(viewDescriptor.eAllContents(), ElementEditor.class);
		while (elementEditors.hasNext()) {
			ElementEditor elementEditor = elementEditors.next();
			EStructuralFeature bindingFeature = editingComponent.getBinding().feature(elementEditor, editingComponent.getEditingContext().getOptions().autowire());
			EObject editedObject = editingComponent.getEObject();
			EStructuralFeature feature = editingComponent.getEditingContext().getEMFService().mapFeature(editedObject, bindingFeature);
			if (feature != null) {
				PropertyEditor propertyEditor = propertyEditors.get(elementEditor);
				propertyEditor.init(feature);
			}
			
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#dispose()
	 */
	public void dispose() {
		editingComponent.removeEditingListener(this);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public void firePropertiesChanged(PropertiesEditingEvent event) {
		// Default : Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#setValue(java.lang.Object, java.lang.Object)
	 */
	public void setValue(Object field, Object value) {
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
	public void unsetValue(Object field) {
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
	public void addValue(Object field, Object value) {
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
	public void addAllValues(Object field, Collection<?> values) {
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
	public void removeValue(Object field, Object value) {
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
	public void removeAllValues(Object field, Collection<?> values) {
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
	public void moveValue(Object field, Object value, int newIndex) {
		if (field instanceof ElementEditor) {
			PropertyEditor propertyEditor = propertyEditors.get(field);
			if (propertyEditor instanceof MultivaluedPropertyEditor) {
				((MultivaluedPropertyEditor) propertyEditor).moveValue(value, newIndex);
			}
		}
	}

}
