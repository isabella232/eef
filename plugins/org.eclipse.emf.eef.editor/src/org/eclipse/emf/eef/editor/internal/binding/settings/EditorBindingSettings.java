/**
 * 
 */
package org.eclipse.emf.eef.editor.internal.binding.settings;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.internal.editingModel.EditingModelEnvironmentImpl;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;

import com.google.common.collect.Iterables;

/**
 * Generic binding settings for EObject.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class EditorBindingSettings implements EEFBindingSettings<PropertiesEditingModel>, DefaultService {

	/**
	 * Providers
	 */
	private EMFServiceProvider emfServiceProvider;
	private EditingModelEnvironment editingModelEnvironment;
	private ViewHandlerProvider viewHandlerProvider;
	private EEFToolkitProvider toolkitProvider;

	/**
	 * Properties editing model.
	 */
	private EEFBindingSettingsProvider eefBindingSettingsProvider;
	private EditingDomain editingDomain;

	public EditorBindingSettings(EEFBindingSettingsProvider eefBindingSettingsProvider, EditingDomain editingDomain) {
		this.eefBindingSettingsProvider = eefBindingSettingsProvider;
		this.editingDomain = editingDomain;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EPackage element) {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEMFServiceProvider()
	 */
	public EMFServiceProvider getEMFServiceProvider() {
		return emfServiceProvider;
	}

	/**
	 * @param emfServiceProvider
	 *            EMFServiceProvider
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefToolkitProvider
	 *            EEFToolkitProvider
	 */
	public void setEEFToolkitProvider(EEFToolkitProvider eefToolkitProvider) {
		this.toolkitProvider = eefToolkitProvider;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEEFDescription(org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingModel getEEFDescription(EObject eObject) {
		return getEEFDescription(eObject.eClass());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEEFDescription(org.eclipse.emf.ecore.EClass)
	 */
	public PropertiesEditingModel getEEFDescription(EClass eClass) {
		for (Resource resource : editingDomain.getResourceSet().getResources()) {
			if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof PropertiesEditingModel) {
				PropertiesEditingModel editingModel = (PropertiesEditingModel) resource.getContents().get(0);
				if (editingModel != null) {
					editingModel.setEMFServiceProvider(emfServiceProvider);
					if (editingModel.bindingEClass(eClass) != null) {
						return editingModel;
					}
				}
			}
		}

		return (PropertiesEditingModel) eefBindingSettingsProvider.getBindingSettings(eClass.getEPackage()).getEEFDescription(eClass);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getViewHandler(org.eclipse.emf.eef.runtime.editingModel.View)
	 */
	public ViewHandler<?> getViewHandler(View view) {
		return viewHandlerProvider.getViewHandler(view);
	}

	/**
	 * @param viewHandlerProvider
	 *            ViewHandlerProvider
	 */
	public void setViewHandlerProvider(ViewHandlerProvider viewHandlerProvider) {
		this.viewHandlerProvider = viewHandlerProvider;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#enableLockPolicy(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy)
	 */
	public boolean enableLockPolicy(EEFLockPolicy lockPolicy) {
		return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEditingModelEnvironment()
	 */
	public EditingModelEnvironment getEditingModelEnvironment() {
		if (editingModelEnvironment == null) {
			editingModelEnvironment = new EditingModelEnvironmentImpl(null);
		}
		return editingModelEnvironment;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEditingModel(org.eclipse.emf.ecore.EPackage)
	 */
	public PropertiesEditingModel getEditingModel(EPackage ePackage) {
		Iterable<EClass> filter = Iterables.filter(ePackage.getEClassifiers(), EClass.class);
		if (!Iterables.isEmpty(filter)) {
			return getEEFDescription(filter.iterator().next());
		}
		return null;
	}

}
