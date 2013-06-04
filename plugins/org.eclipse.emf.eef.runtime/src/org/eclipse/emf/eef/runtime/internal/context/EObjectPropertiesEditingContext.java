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
import org.eclipse.emf.eef.runtime.services.bindingSettings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.services.bindingSettings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectPropertiesEditingContext implements PropertiesEditingContext {

	private EMFServiceProvider emfServiceProvider;
	private ModelChangesNotificationManager notificationManager;
	private EEFBindingSettingsProvider bindingSettingsProvider;
	
	protected PropertiesEditingContext parentContext;
	protected EObject eObject;
	protected AdapterFactory adapterFactory;
	protected ContextOptions options;

	protected EditingRecorder editingRecorder;

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
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setNotificationManager(ModelChangesNotificationManager)
	 */
	public void setNotificationManager(ModelChangesNotificationManager notificationManager) {
		this.notificationManager = notificationManager;
	}

	/**
	 * @param bindingSettingsProvider the bindingSettingsProvider to set
	 */
	public void setBindingSettingsProvider(EEFBindingSettingsProvider bindingSettingsProvider) {
		this.bindingSettingsProvider = bindingSettingsProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEMFServiceProvider()
	 */
	public EMFServiceProvider getEMFServiceProvider() {
		if (emfServiceProvider != null) {
			return emfServiceProvider;
		} else {
			if (parentContext != null) {
				return parentContext.getEMFServiceProvider();
			}
		}
		return null;
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
			EMFService emfService = emfServiceProvider.getEMFService(eObject.eClass().getEPackage());
			Notifier highestNotifier = emfService.highestNotifier(eObject);
			notificationManager.initModelChangesNotifierIfNeeded(highestNotifier);
			EEFBindingSettings provider = bindingSettingsProvider.getBindingSettings(eObject.eClass().getEPackage());
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
