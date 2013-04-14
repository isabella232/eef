/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import java.util.Dictionary;

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
import org.osgi.service.component.ComponentContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectPropertiesEditingContext implements PropertiesEditingContext {

	public static final String FACTORY_ID = "org.eclipse.emf.eef.runtime.internal.context.EObjectPropertiesEditingContext";
	
	protected PropertiesEditingContext parentContext;
	protected EObject eObject;
	protected AdapterFactory adapterFactory;
	protected ContextOptions options;
	protected EEFServiceRegistry serviceRegistry;
	
	protected EditingRecorder editingRecorder;
	private ModelChangesNotificationManager notificationManager;

	private PropertiesEditingComponent component;
	
	/**
	 * Configure the current component instance with the given {@link ComponentContext}.
	 * @param context {@link ComponentContext} to use to configure the current instance.
	 */
	public void configure(ComponentContext context) {
		@SuppressWarnings("rawtypes")
		Dictionary properties = context.getProperties();
		Object eObjectParam = properties.get(EOBJECT_PARAM);
		if (eObjectParam instanceof EObject) {
			this.eObject = (EObject) eObjectParam;
			Object adapterFactoryParam = properties.get(ADAPTERFACTORY_PARAM);
			if (adapterFactoryParam instanceof AdapterFactory) {
				this.adapterFactory = (AdapterFactory) adapterFactoryParam;
				this.options = new ContextOptions();
			} else {
				Object parentContextParam = properties.get(PARENTCONTEXT_PARAM);
				if (parentContextParam instanceof PropertiesEditingContext) {
					this.parentContext = (PropertiesEditingContext) parentContextParam;
					this.options = new ContextOptions(parentContext.getOptions());
				}
			}			
			this.editingRecorder = new EditingRecorderImpl();
			editingRecorder.initRecording(eObject);
		} else {
			throw new IllegalArgumentException("Unable to instanciate an SemanticPropertiesEditingContext with the given parameters.");
		}
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
