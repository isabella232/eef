/**
 * 
 */
package org.eclipse.emf.eef.runtime.context.impl;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eef.runtime.EEFRuntime;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.SemanticDirectEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.util.EMFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectPropertiesEditingContext implements PropertiesEditingContext {

	protected EObject eObject;
	protected AdapterFactory adapterFactory;
	protected ChangeRecorder changeRecorder;

	protected ContextOptions options;


	/**
	 * @param eObject {@link EObject} to edit.
	 */
	public EObjectPropertiesEditingContext(AdapterFactory adapterFactory, EObject eObject) {
		this.adapterFactory = adapterFactory;
		this.eObject = eObject;
		this.options = initOptions();
		initChangeRecorder(eObject);
	}

	/**
	 * Initialize the options of this context.
	 * @return the {@link ContextOptions} to use.
	 */
	protected ContextOptions initOptions() {
		return new ContextOptions();
	}

	/**
	 * @param context the {@link PropertiesEditingContext}.
	 */
	protected void initChangeRecorder(EObject eObject) {
		Resource resource = eObject.eResource();
		if (resource != null) {
			if (resource.getResourceSet() != null) {
				this.changeRecorder = new ChangeRecorder(resource.getResourceSet());
			} else {
				this.changeRecorder = new ChangeRecorder(resource);
			}
		} else {
			this.changeRecorder = new ChangeRecorder(eObject);
		}
	}

	/**
	 * @return the eObject
	 */
	public EObject getEObject() {
		return eObject;
	}

	/**
	 * @return the adapterFactory
	 */
	public AdapterFactory getAdapterFactory() {
		return adapterFactory;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEditingComponent()
	 */
	public PropertiesEditingComponent getEditingComponent() {
		PropertiesEditingComponent component = (PropertiesEditingComponent) adapterFactory.adapt(eObject, PropertiesEditingComponent.class);
		component.setEditingContext(this);
		return component;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getOptions()
	 */
	public ContextOptions getOptions() {
		return options;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEMFService()
	 */
	public EMFService getEMFService() {
		if (getEditingComponent() != null && getEditingComponent().getTarget() instanceof EObject) {
			return EEFRuntime.getPlugin().getEMFService(((EObject) getEditingComponent().getTarget()).eClass().getEPackage());
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getChangeRecorder()
	 */
	public ChangeRecorder getChangeRecorder() {
		return changeRecorder;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext editingContext) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			SemanticPropertiesEditingContext semanticEditingContext = (SemanticPropertiesEditingContext) editingContext;
			return new SemanticDirectEditingPolicy(semanticEditingContext.getEditingComponent(), semanticEditingContext.getEditingEvent());
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#undoEditing()
	 */
	public void undoEditing() {
		ChangeDescription endRecording = getChangeRecorder().endRecording();
		endRecording.applyAndReverse();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#dispose()
	 */
	public void dispose() {
		changeRecorder.dispose();
	}

}
