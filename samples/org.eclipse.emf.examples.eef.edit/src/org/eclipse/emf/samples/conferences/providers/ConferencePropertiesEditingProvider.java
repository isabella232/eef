/**
 * 
 */
package org.eclipse.emf.samples.conferences.providers;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ComposedPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.SWTToolkit;
import org.eclipse.emf.eef.runtime.view.handler.ComposedViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;

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
		Collection<PropertiesEditingModel> result = new ArrayList<PropertiesEditingModel>();
		ResourceSet rSet = new ResourceSetImpl();
		Resource resource = rSet.getResource(URI.createPlatformPluginURI("org.eclipse.emf.examples.eef.edit/models/conference.editingmodel", true), true);
		result.add((PropertiesEditingModel) resource.getContents().get(0));
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider#initViewHandlerProvider()
	 */
	@Override
	protected ViewHandlerProvider initViewHandlerProvider() {
		if (buildedViewHandlerProvider == null) {
			buildedViewHandlerProvider = buildViewHandlerProvider();
		}
		return buildedViewHandlerProvider;
	}
	
	/**
	 * Build the {@link ViewHandlerProvider} for the EEF tests.
	 * @return a sample {@link ViewHandlerProvider}
	 */
	private ViewHandlerProvider buildViewHandlerProvider() {
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
	private PropertyEditorProvider buildPropertyEditorProvider() {
		return new ComposedPropertyEditorProvider.Builder()
		.addPropertyEditorProvider(new SWTToolkit())
		.addPropertyEditorProvider(new EMFPropertiesToolkit())
		.build();
	}


}