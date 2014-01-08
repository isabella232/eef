/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.internal.editingModel.EditingModelEnvironmentImpl;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.ui.util.BindingSettingsBuilder;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.views.Container;

/**
 * Generic binding settings for EObject.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class GenericBindingSettings implements EEFBindingSettings<PropertiesEditingModel>, DefaultService {

	/**
	 * Widgets constants
	 */
	public static final String GROUP_CONTAINER_NAME = "Group";
	public static final String TEXT_WIDGET_NAME = "Text";
	public static final String ECOMBO_EDITOR_WIDGET_NAME = "ECombo Editor";
	public static final String ECONTAINEMENT_EDITOR_WIDGET_NAME = "EContainement Editor";
	public static final String EDATE_PICKER_EDITOR_WIDGET_NAME = "EDatePicker Editor";
	public static final String EREFERENCE_EDITOR_WIDGET_NAME = "EReference Editor";
	public static final String SINGLE_CONTAINMENT_EDITOR_WIDGET_NAME = "Single Containment Editor";
	public static final String CHECKBOX_WIDGET_NAME = "Checkbox";
	public static final String COMBO_WIDGET_NAME = "Combo";
	public static final String TEXTAREA_WIDGET_NAME = "Textarea";

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
	private ResourceSet resourceSet;
	private Map<String, PropertiesEditingModel> mapURI2PropertiesEditingModel = new HashMap<String, PropertiesEditingModel>();

	public static final String PROPERTIES_EDITING_MODEL_NAME = "Generic Binding Settings";
	public static final String PROPERTIES_EDITING_MODEL_ID = "org.eclipse.emf.eef.runtime.ui.swt.genericBindingSetting";

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
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEditingModelEnvironment()
	 */
	public EditingModelEnvironment getEditingModelEnvironment() {
		if (editingModelEnvironment == null) {
			editingModelEnvironment = new EditingModelEnvironmentImpl();
		}
		return editingModelEnvironment;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEEFDescription(org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingModel getEEFDescription(EObject eObject) {
		createOrGetResourceSet();
		// get PropertiesEditingModel if exists, else create one.
		PropertiesEditingModel propertiesEditingModel = getPropertiesEditingModel(eObject);
		// define EClass and EStruturalFeature bindings if do not exist.
		updatePropertiesEditingModel(eObject, propertiesEditingModel);

		return propertiesEditingModel;
	}

	/**
	 * Defines bindings between EClass/ EStructuralFeatures and
	 * views/representations.
	 * 
	 * @param eObject
	 *            EObject
	 * @param propertiesEditingModel
	 *            PropertiesEditingModel
	 */
	protected void updatePropertiesEditingModel(EObject eObject, PropertiesEditingModel propertiesEditingModel) {
		// create EClassBinding on eobject with its view
		BindingSettingsBuilder builder = new BindingSettingsBuilder(propertiesEditingModel, toolkitProvider, GROUP_CONTAINER_NAME, TEXT_WIDGET_NAME);
		if (!builder.existEClassBinding(eObject)) {
			// create View
			org.eclipse.emf.eef.views.View createdView = builder.createViewForEClassBinding(eObject);
			// add container group
			Container createdGroup = builder.createContainerViewForEClassBinding(eObject, createdView);

			// create EClassBinding and link the createdView
			EClassBinding eClassBinding = builder.createEClassBinding(eObject, createdView);

			// bind eobject structural features
			builder.bindEStructuralFeature(eObject, eClassBinding, createdGroup);
		}

	}

	/**
	 * @param eObject
	 *            EObject
	 * @return the existing PropertiesEditingModel if exists, else create one.
	 */
	private PropertiesEditingModel getPropertiesEditingModel(EObject eObject) {
		PropertiesEditingModel propertiesEditingModel = null;
		String uri = eObject.eClass().getEPackage().getNsURI();
		if (mapURI2PropertiesEditingModel.get(uri) == null) {
			propertiesEditingModel = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
			propertiesEditingModel.setId(PROPERTIES_EDITING_MODEL_ID);
			propertiesEditingModel.setName(PROPERTIES_EDITING_MODEL_NAME);
			propertiesEditingModel.setEMFServiceProvider(emfServiceProvider);
			mapURI2PropertiesEditingModel.put(uri, propertiesEditingModel);
		} else {
			propertiesEditingModel = mapURI2PropertiesEditingModel.get(uri);
		}
		return propertiesEditingModel;
	}

	/**
	 * @return the resource set.
	 */
	private ResourceSet createOrGetResourceSet() {
		if (resourceSet == null) {
			resourceSet = getEditingModelEnvironment().getResourceSet();
		}
		return resourceSet;
	}

	/**
	 * @return map
	 */
	public Map<String, PropertiesEditingModel> getMapURI2PropertiesEditingModel() {
		return mapURI2PropertiesEditingModel;
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

}
