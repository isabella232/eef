/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.util;

import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage;
import org.eclipse.emf.eef.runtime.model.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.views.RootView;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.ReflectViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ComposedViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFTestStuffsBuilder {

	/**
	 * Build the {@link PropertiesEditingModel} for the EEF tests.
	 * @return a sample {@link PropertiesEditingModel}.
	 */
	public PropertiesEditingModel buildEditingModel() {
		return new PropertiesEditingModel.Builder()
						.bind(BindingmodelPackage.Literals.SAMPLE, SampleView.class)
						.bind(BindingmodelPackage.Literals.ROOT, RootView.class)
						.build();
	}
	
	/**
	 * Build the {@link ViewHandlerProvider} for the EEF tests.
	 * @return a sample {@link ViewHandlerProvider}
	 */
	public ViewHandlerProvider buildViewHandlerProvider() {
		return new ComposedViewHandlerProvider.Builder()
						.addHandler(new ReflectViewHandlerProvider())
						.build();
	}
	
}
