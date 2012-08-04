/**
 * 
 */
package org.eclipse.emf.samples.conferences.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ComposedPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.SWTToolkit;
import org.eclipse.emf.eef.runtime.view.handler.ComposedViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.eclipse.emf.samples.conference.ConferencePackage;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ConferencePropertiesEditingProvider extends PropertiesEditingProvider {

	private ViewHandlerProvider buildedViewHandlerProvider;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider#initSpecificEditingModel()
	 */
	@Override
	protected Collection<? extends PropertiesEditingModel> initSpecificEditingModel() {
		final List<Toolkit> toolkits = new ArrayList<Toolkit>(2);
		toolkits.add(searchSWTToolkit(getBuildedViewHandlerProvider()));
		toolkits.add(searchEMFPropertiesToolkit(getBuildedViewHandlerProvider()));		
		buildViews(toolkits);
		Collection<PropertiesEditingModel> result = new ArrayList<PropertiesEditingModel>();
		result.add(generateModel(buildViews(toolkits)));
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider#initViewHandlerProvider()
	 */
	@Override
	protected ViewHandlerProvider initViewHandlerProvider() {
		return getBuildedViewHandlerProvider();
	}
	
	private ViewHandlerProvider getBuildedViewHandlerProvider() {
		if (buildedViewHandlerProvider == null) {
			buildedViewHandlerProvider = buildViewHandlerProvider();
		}
		return buildedViewHandlerProvider;
	}

	public static PropertiesEditingModel generateModel(List<View> views) {
		return new EditingModelBuilder()
		.bindClass(ConferencePackage.Literals.PERSON)
		.withView(views.get(0))
		.withView(views.get(1))
		.build();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.notify.OpenWizardOnDoubleClick#buildViews(java.util.List)
	 */
	public static List<View> buildViews(List<Toolkit> toolkits) {
		List<View> result = new ArrayList<View>();
		View identity = ViewsFactory.eINSTANCE.createView();
		identity.setName("Identity");
		ElementEditor firstnameEditor = ViewsFactory.eINSTANCE.createElementEditor();
		firstnameEditor.setName("firstname");
		firstnameEditor.setRepresentation(searchWidget(toolkits.get(0), "Text"));
		identity.getElements().add(firstnameEditor);
		ElementEditor lastnameEditor = ViewsFactory.eINSTANCE.createElementEditor();
		lastnameEditor.setName("lastname");
		lastnameEditor.setRepresentation(searchWidget(toolkits.get(0), "Text"));
		identity.getElements().add(lastnameEditor);
		ElementEditor isRegisteredEditor = ViewsFactory.eINSTANCE.createElementEditor();
		isRegisteredEditor.setName("isRegistered");
		isRegisteredEditor.setRepresentation(searchWidget(toolkits.get(0), "Checkbox"));
		identity.getElements().add(isRegisteredEditor);
		result.add(identity);
		View conference = ViewsFactory.eINSTANCE.createView();
		conference.setName("Conference");
		ElementEditor assists = ViewsFactory.eINSTANCE.createElementEditor();
		assists.setName("assists");
		assists.setRepresentation(searchWidget(toolkits.get(1), "EReferenceMultiEditor"));
		conference.getElements().add(assists);
		result.add(conference);
		return result;
	}


	protected static Widget searchWidget(Toolkit toolkit, String name) {
		TreeIterator<EObject> eAllContents = toolkit.eAllContents();
		while (eAllContents.hasNext()) {
			EObject next = eAllContents.next();
			if (next instanceof Widget && name.equals(((Widget) next).getName())) {
				return (Widget) next;
			}
		}
		return null;
	}

	/**
	 * Build the {@link ViewHandlerProvider} for the EEF tests.
	 * @return a sample {@link ViewHandlerProvider}
	 */
	public static ViewHandlerProvider buildViewHandlerProvider() {
		return new ComposedViewHandlerProvider.Builder()
		.addHandler(new PropertiesEditingViewHandlerProvider(buildPropertyEditorProvider()))
		.addHandler(new SWTViewHandlerProvider())
		.addHandler(new ReflectViewHandlerProvider())
		.build();
	}

	/**
	 * Build the {@link PropertyEditorProvider} for the EEF tests
	 * @return a sample {@link PropertyEditorProvider}.
	 */
	private static PropertyEditorProvider buildPropertyEditorProvider() {
		return new ComposedPropertyEditorProvider.Builder()
		.addPropertyEditorProvider(new SWTToolkit())
		.addPropertyEditorProvider(new EMFPropertiesToolkit())
		.build();
	}


	private static Toolkit searchSWTToolkit(ViewHandlerProvider provider) {
		PropertiesEditingViewHandlerProvider propertiesEditingViewHandlerProvider = searchPropertiesEditingViewHandlerProvider(provider);
		if (propertiesEditingViewHandlerProvider != null) {
			ToolkitPropertyEditorProvider toolkitPropertyEditorProvider = searchToolkitWithName(propertiesEditingViewHandlerProvider.getPropertyEditorProvider(), SWTToolkit.SWT_TOOLKIT_NAME);
			if (toolkitPropertyEditorProvider != null) {
				return toolkitPropertyEditorProvider.getModel();
			}
		}

		return null;
	}

	private static Toolkit searchEMFPropertiesToolkit(ViewHandlerProvider provider) {
		PropertiesEditingViewHandlerProvider propertiesEditingViewHandlerProvider = searchPropertiesEditingViewHandlerProvider(provider);
		if (propertiesEditingViewHandlerProvider != null) {
			ToolkitPropertyEditorProvider toolkitPropertyEditorProvider = searchToolkitWithName(propertiesEditingViewHandlerProvider.getPropertyEditorProvider(), EMFPropertiesToolkit.EMF_PROPERTIES);
			if (toolkitPropertyEditorProvider != null) {
				return toolkitPropertyEditorProvider.getModel();
			}
		}

		return null;
	}

	private static PropertiesEditingViewHandlerProvider searchPropertiesEditingViewHandlerProvider(ViewHandlerProvider provider) {
		if (provider instanceof PropertiesEditingViewHandlerProvider) {
			return (PropertiesEditingViewHandlerProvider) provider;
		} else if (provider instanceof ComposedViewHandlerProvider) {
			for (ViewHandlerProvider viewHandlerProvider : ((ComposedViewHandlerProvider) provider).getProviders()) {
				if (viewHandlerProvider instanceof PropertiesEditingViewHandlerProvider) {
					return (PropertiesEditingViewHandlerProvider) viewHandlerProvider;
				}
			}
		}
		return null;
	}

	private static ToolkitPropertyEditorProvider searchToolkitWithName(PropertyEditorProvider provider, String name) {
		if (provider instanceof ToolkitPropertyEditorProvider) {
			if (name.equals(((ToolkitPropertyEditorProvider) provider).getModel().getName())) {
				return (ToolkitPropertyEditorProvider) provider;
			}
		} else if (provider instanceof ComposedPropertyEditorProvider) {
			for (PropertyEditorProvider propertyEditorProvider : ((ComposedPropertyEditorProvider) provider).getPropertyEditorProviders()) {
				ToolkitPropertyEditorProvider searchToolkitWithName = searchToolkitWithName(propertyEditorProvider, name);
				if (searchToolkitWithName != null) {
					return searchToolkitWithName;
				}
			}
		}
		return null;
	}

}