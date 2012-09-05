/**
 * 
 */
package org.eclipse.emf.eef.runtime.context.impl;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.EditingRecorder;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.EditingRecorderImpl;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.SemanticDirectEditingPolicy;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectPropertiesEditingContext implements PropertiesEditingContext {

	protected EObject eObject;
	protected AdapterFactory adapterFactory;
	protected ContextOptions options;
	
	private PropertiesEditingProviderRegistry propertiesEditingProviderRegistry;
	private EditingRecorder editingRecorder;
	private EMFServiceProvider emfServiceProvider;
	private ModelChangesNotificationManager notificationManager;

	private PropertiesEditingComponent component;

	/**
	 * @param eObject {@link EObject} to edit.
	 */
	EObjectPropertiesEditingContext(AdapterFactory adapterFactory, EObject eObject) {
		this.adapterFactory = adapterFactory;
		this.eObject = eObject;
		this.options = initOptions();
		this.editingRecorder = new EditingRecorderImpl();
		editingRecorder.initRecording(eObject);
	}

	/**
	 * Initialize the options of this context.
	 * @return the {@link ContextOptions} to use.
	 */
	protected ContextOptions initOptions() {
		return new ContextOptions();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setEmfServiceProvider(EMFServiceProvider)
	 */
	public void setEmfServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setPropertiesEditingProviderRegistry(PropertiesEditingProviderRegistry)
	 */
	public void setPropertiesEditingProviderRegistry(PropertiesEditingProviderRegistry propertiesEditingProviderRegistry) {
		this.propertiesEditingProviderRegistry = propertiesEditingProviderRegistry;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setNotificationManager(ModelChangesNotificationManager)
	 */
	public void setNotificationManager(ModelChangesNotificationManager notificationManager) {
		this.notificationManager = notificationManager;
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
		if (component == null) {
			notificationManager.initModelChangesNotifierIfNeeded(eObject);
			component = propertiesEditingProviderRegistry.getPropertiesEditingProvider(eObject.eClass().getEPackage()).createComponent(eObject);
			component.setEditingContext(this);
		}
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
		if (getEditingComponent() != null) {
			return emfServiceProvider.getEMFServiceForPackage((getEditingComponent().getEObject()).eClass().getEPackage());
		}
		return null;
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
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#stopEditing()
	 */
	public void stopEditing() {
		editingRecorder.stopEditing();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#cancelEditing()
	 */
	public void cancelEditing() {
		editingRecorder.cancelEditing();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#undoEditing()
	 */
	public void undoEditing() {
		editingRecorder.undoEditing();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#dispose()
	 */
	public void dispose() {
		editingRecorder.dispose();
	}

	/**
	 * @return the editingRecorder
	 */
	protected EditingRecorder getEditingRecorder() {
		return editingRecorder;
	}

}
