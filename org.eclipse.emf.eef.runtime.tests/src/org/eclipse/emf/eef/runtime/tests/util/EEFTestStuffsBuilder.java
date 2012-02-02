/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.views.RootView;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ComposedPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.swttoolkit.SWTToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.view.handler.ComposedViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFTestStuffsBuilder {

	/**
	 * Build a {@link PropertiesEditingModel} with simple SWT views for the EEF tests.
	 * @return a sample {@link PropertiesEditingModel}.
	 */
	public PropertiesEditingModel buildEditingModelWithSWTViews() {
		return new EditingModelBuilder()
						.bindClass(BindingmodelPackage.Literals.SAMPLE).withView(SampleView.class)
						.bindClass(BindingmodelPackage.Literals.ROOT).withView(RootView.class)
						.build();
	}
	
	/**
	 * Build a full sample of {@link PropertiesEditingContext} for the EEF tests.
	 * @return a sample {@link PropertiesEditingContext}.
	 */
	public PropertiesEditingContext buildEditingContextWithPropertiesEditingViews() {
		ViewHandlerProvider viewHandlerProvider = buildViewHandlerProvider();
		Toolkit swtToolkit = searchSWTToolkit(viewHandlerProvider);
		List<View> views = buildSampleViews(swtToolkit);
		PropertiesEditingModel editingModel = new EditingModelBuilder()
						.bindClass(BindingmodelPackage.Literals.SAMPLE).withView(views.get(0))
						.build();
		Sample sampleToEdit = BindingmodelFactory.eINSTANCE.createSample();
		sampleToEdit.setName("This is a named Sample");
		sampleToEdit.setActive(true);
		EObjectPropertiesEditingContext context = new EObjectPropertiesEditingContext(sampleToEdit);
		context.setAdapterFactory(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		context.setEditingModel(editingModel);
		context.setViewHandlerProvider(viewHandlerProvider);
		return context;
	}
	
	/**
	 * Build the {@link ViewHandlerProvider} for the EEF tests.
	 * @return a sample {@link ViewHandlerProvider}
	 */
	public ViewHandlerProvider buildViewHandlerProvider() {
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
	public PropertyEditorProvider buildPropertyEditorProvider() {
		return new ComposedPropertyEditorProvider.Builder()
						.addPropertyEditorProvider(new SWTToolkitPropertyEditorProvider())
						.build();
	}


	private List<View> buildSampleViews(Toolkit swtToolkit) {
		List<View> result = new ArrayList<View>();
		View sampleView = ViewsFactory.eINSTANCE.createView();
		sampleView.setName("Sample");
		ElementEditor nameEditor = ViewsFactory.eINSTANCE.createElementEditor();
		nameEditor.setName("name");
		nameEditor.setRepresentation(searchWidget(swtToolkit, "Text"));
		sampleView.getElements().add(nameEditor);

		ElementEditor activeEditor = ViewsFactory.eINSTANCE.createElementEditor();
		activeEditor.setName("active");
		activeEditor.setRepresentation(searchWidget(swtToolkit, "Checkbox"));
		sampleView.getElements().add(activeEditor);
		
		result.add(sampleView);
		
		return result;
	}

	private Widget searchWidget(Toolkit toolkit, String name) {
		TreeIterator<EObject> eAllContents = toolkit.eAllContents();
		while (eAllContents.hasNext()) {
			EObject next = eAllContents.next();
			if (next instanceof Widget && name.equals(((Widget) next).getName())) {
				return (Widget) next;
			}
		}
		return null;
	}
	
	private Toolkit searchSWTToolkit(ViewHandlerProvider provider) {
		PropertiesEditingViewHandlerProvider propertiesEditingViewHandlerProvider = searchPropertiesEditingViewHandlerProvider(provider);
		if (propertiesEditingViewHandlerProvider != null) {
			ToolkitPropertyEditorProvider toolkitPropertyEditorProvider = searchToolkitPropertyEditorProvider(propertiesEditingViewHandlerProvider.getPropertyEditorProvider());
			if (toolkitPropertyEditorProvider != null) {
				return toolkitPropertyEditorProvider.getModel();
			}
		}
		
		return null;
	}
	
	private PropertiesEditingViewHandlerProvider searchPropertiesEditingViewHandlerProvider(ViewHandlerProvider provider) {
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
	
	private SWTToolkitPropertyEditorProvider searchToolkitPropertyEditorProvider(PropertyEditorProvider provider) {
		if (provider instanceof ToolkitPropertyEditorProvider) {
			return (SWTToolkitPropertyEditorProvider) provider;
		} else if (provider instanceof ComposedPropertyEditorProvider) {
			for (PropertyEditorProvider propertyEditorProvider : ((ComposedPropertyEditorProvider) provider).getPropertyEditorProviders()) {
				if (propertyEditorProvider instanceof ToolkitPropertyEditorProvider) {
					return (SWTToolkitPropertyEditorProvider) propertyEditorProvider;
				}
			}
		}
		return null;
	}
	
}
