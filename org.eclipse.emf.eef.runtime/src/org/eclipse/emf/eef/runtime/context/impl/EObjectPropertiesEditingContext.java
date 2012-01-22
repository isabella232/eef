/**
 * 
 */
package org.eclipse.emf.eef.runtime.context.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.model.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectPropertiesEditingContext implements PropertiesEditingContext {

	protected EObject eObject;
	private PropertiesEditingModel editingModel;
	private ViewHandlerProvider viewHandlerProvider;
	
	/**
	 * @param eObject {@link EObject} to edit.
	 */
	public EObjectPropertiesEditingContext(EObject eObject) {
		this.eObject = eObject;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEditingModel()
	 */
	public PropertiesEditingModel getEditingModel() {
		return editingModel;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setEditingModel(org.eclipse.emf.eef.runtime.model.PropertiesEditingModel)
	 */
	public void setEditingModel(PropertiesEditingModel editingModel) {
		this.editingModel = editingModel;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getViewHandlerProvider()
	 */
	public ViewHandlerProvider getViewHandlerProvider() {
		return viewHandlerProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setViewHandlerProvider(org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider)
	 */
	public void setViewHandlerProvider(ViewHandlerProvider viewHandlerProvider) {
		this.viewHandlerProvider = viewHandlerProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getComponent()
	 */
	public PropertiesEditingComponent getComponent() {
		PropertiesEditingComponent component = (PropertiesEditingComponent) editingModel.adapt(eObject, PropertiesEditingComponent.class);
		component.setEditingContext(this);
		return component;
	}
	
}
