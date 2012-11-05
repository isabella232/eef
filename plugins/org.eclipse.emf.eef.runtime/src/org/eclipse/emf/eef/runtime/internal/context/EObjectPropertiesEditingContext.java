/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.EditingRecorder;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectPropertiesEditingContext implements PropertiesEditingContext {
	
	protected PropertiesEditingContext parentContext;
	protected EObject eObject;
	protected AdapterFactory adapterFactory;
	protected ContextOptions options;
	protected EEFServiceRegistry serviceRegistry;
	
	private EditingRecorder editingRecorder;
	private ModelChangesNotificationManager notificationManager;

	private PropertiesEditingComponent component;

	/**
	 * @param adapterFactory the {@link AdapterFactory} to use in the current context.
	 * @param eObject the edited {@link EObject}.
	 */
	EObjectPropertiesEditingContext(AdapterFactory adapterFactory, EObject eObject) {
		this.adapterFactory = adapterFactory;
		this.eObject = eObject;
		this.options = new ContextOptions();
		this.editingRecorder = new EditingRecorderImpl();
		editingRecorder.initRecording(eObject);
	}

	/**
	 * @param parentContext the parent {@link PropertiesEditingContext}.
	 * @param eObject the edited {@link EObject}.
	 */
	EObjectPropertiesEditingContext(PropertiesEditingContext parentContext, EObject eObject) {
		this.eObject = eObject;
		this.options = new ContextOptions(parentContext.getOptions());
		this.editingRecorder = new EditingRecorderImpl();
		editingRecorder.initRecording(eObject);
		this.parentContext = parentContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getServiceRegistry()
	 */
	public EEFServiceRegistry getServiceRegistry() {
		if (this.serviceRegistry != null) {
			return serviceRegistry;
		} else {
			if (parentContext != null) {
				return parentContext.getServiceRegistry();
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setServiceRegistry(EMFServiceProvider)
	 */
	public void setServiceRegistry(EEFServiceRegistry emfServiceProvider) {
		this.serviceRegistry = emfServiceProvider;
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
		if (eObject != null) {
			return eObject;
		} else {
			if (parentContext != null) {
				return parentContext.getEditingComponent().getEObject();
			}
		}
		return null;
	}

	/**
	 * @return the adapterFactory
	 */
	public AdapterFactory getAdapterFactory() {
		if (adapterFactory != null) {
			return adapterFactory;
		} else {
			if (parentContext != null) {
				return parentContext.getAdapterFactory();
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEditingComponent()
	 */
	public PropertiesEditingComponent getEditingComponent() {
		if (component == null) {
			EMFService emfService = serviceRegistry.getService(EMFService.class, eObject.eClass().getEPackage());
			Notifier highestNotifier = emfService.highestNotifier(eObject);
			notificationManager.initModelChangesNotifierIfNeeded(highestNotifier);
			PropertiesEditingProvider provider = serviceRegistry.getService(PropertiesEditingProvider.class, eObject.eClass().getEPackage());
			provider.setNotificationManager(notificationManager);
			component = provider.createComponent(eObject);
			component.setEditingContext(this);
		}
		return component;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getOptions()
	 */
	public ContextOptions getOptions() {
		if (options != null) {
			return options;
		} else {
			if (parentContext != null) {
				return parentContext.getOptions();
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext editingContext) {
		return serviceRegistry.getService(PropertiesEditingPolicyProvider.class, editingContext).getEditingPolicy(editingContext);
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
