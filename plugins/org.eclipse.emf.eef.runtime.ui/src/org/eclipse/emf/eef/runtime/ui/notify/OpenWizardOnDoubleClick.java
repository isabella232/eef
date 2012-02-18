/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.notify;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.DomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ComposedPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.swttoolkit.SWTToolkit;
import org.eclipse.emf.eef.runtime.ui.wizard.EEFEditingWizard;
import org.eclipse.emf.eef.runtime.ui.wizard.EEFWizardDialog;
import org.eclipse.emf.eef.runtime.view.handler.ComposedViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class OpenWizardOnDoubleClick implements IDoubleClickListener {

	private EditingDomain domain;
	private AdapterFactory adapterFactory;
	
	/**
	 * @param domain {@link EditingDomain} to use for editing actions.
	 * @param adapterFactory {@link AdapterFactory} to use for element editing.
	 */
	public OpenWizardOnDoubleClick(EditingDomain domain, AdapterFactory adapterFactory) {
		this.domain = domain;
		this.adapterFactory = adapterFactory;
	}



	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
	 */
	public void doubleClick(DoubleClickEvent event) {
		StructuredSelection selection = (StructuredSelection) event.getSelection();
		if (selection.getFirstElement() instanceof EObject) {
			PropertiesEditingContext context = new DomainPropertiesEditingContext(domain, (EObject) selection.getFirstElement());
			// Creating ViewHandlerProvider
			ViewHandlerProvider viewHandlerProvider = buildViewHandlerProvider();
			
			// Creating views
			List<Toolkit> toolkits = new ArrayList<Toolkit>(2);
			toolkits.add(searchSWTToolkit(viewHandlerProvider));
			toolkits.add(searchEMFPropertiesToolkit(viewHandlerProvider));		
			List<View> views = buildViews(toolkits);
			
			// Creation Editing Model
			PropertiesEditingModel editingModel = buildEditingModel(views);
			
			// Creating model
			((DomainPropertiesEditingContext) context).setAdapterFactory(adapterFactory);
			context.setEditingModel(editingModel);
			context.setViewHandlerProvider(viewHandlerProvider);
			EEFWizardDialog dialog = new EEFWizardDialog(event.getViewer().getControl().getShell(), new EEFEditingWizard(context));
			dialog.open();
		}
	}



	protected abstract PropertiesEditingModel buildEditingModel(List<View> views);
	
	protected abstract List<View> buildViews(List<Toolkit> toolkits);

	protected Widget searchWidget(Toolkit toolkit, String name) {
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
	
	
	private Toolkit searchSWTToolkit(ViewHandlerProvider provider) {
		PropertiesEditingViewHandlerProvider propertiesEditingViewHandlerProvider = searchPropertiesEditingViewHandlerProvider(provider);
		if (propertiesEditingViewHandlerProvider != null) {
			ToolkitPropertyEditorProvider toolkitPropertyEditorProvider = searchToolkitWithName(propertiesEditingViewHandlerProvider.getPropertyEditorProvider(), SWTToolkit.SWT_TOOLKIT_NAME);
			if (toolkitPropertyEditorProvider != null) {
				return toolkitPropertyEditorProvider.getModel();
			}
		}
		
		return null;
	}
	
	private Toolkit searchEMFPropertiesToolkit(ViewHandlerProvider provider) {
		PropertiesEditingViewHandlerProvider propertiesEditingViewHandlerProvider = searchPropertiesEditingViewHandlerProvider(provider);
		if (propertiesEditingViewHandlerProvider != null) {
			ToolkitPropertyEditorProvider toolkitPropertyEditorProvider = searchToolkitWithName(propertiesEditingViewHandlerProvider.getPropertyEditorProvider(), EMFPropertiesToolkit.EMF_PROPERTIES);
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
	
	private ToolkitPropertyEditorProvider searchToolkitWithName(PropertyEditorProvider provider, String name) {
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
