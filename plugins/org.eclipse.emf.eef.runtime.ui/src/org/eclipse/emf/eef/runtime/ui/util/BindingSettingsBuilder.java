/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.util;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class BindingSettingsBuilder {

	private EEFToolkitProvider toolkitProvider;
	private String GROUP_CONTAINER_NAME = "";
	private String TEXT_WIDGET_NAME = "";
	private PropertiesEditingModel propertiesEditingModel;

	public BindingSettingsBuilder(PropertiesEditingModel propertiesEditingModel, EEFToolkitProvider toolkitProvider) {
		this.propertiesEditingModel = propertiesEditingModel;
		this.toolkitProvider = toolkitProvider;
	}

	public BindingSettingsBuilder(PropertiesEditingModel propertiesEditingModel, EEFToolkitProvider toolkitProvider, String groupContainerName, String textWidgetName) {
		this.propertiesEditingModel = propertiesEditingModel;
		this.toolkitProvider = toolkitProvider;
		this.GROUP_CONTAINER_NAME = groupContainerName;
		this.TEXT_WIDGET_NAME = textWidgetName;

	}

	/**
	 * Bind structural feature with a representation.
	 * 
	 * @param eObject
	 *            EObject
	 * @param eClassBinding
	 *            EClassBinding
	 * @param createdGroup
	 *            Container
	 */
	public void bindEStructuralFeature(EObject eObject, EClassBinding eClassBinding, Container createdGroup) {
		for (EStructuralFeature feature : eObject.eClass().getEAllStructuralFeatures()) {
			// create property binding
			PropertyBinding propertyBinding = EditingModelFactory.eINSTANCE.createPropertyBinding();
			propertyBinding.setFeature(feature);
			Widget widget = getWidgetForFeature(feature);
			if (widget != null) {
				// create element editor
				ElementEditor newElementEditor = ViewsFactory.eINSTANCE.createElementEditor();
				newElementEditor.setName(feature.getName());
				newElementEditor.setRepresentation(widget);
				createdGroup.getElements().add(newElementEditor);
				// create EObjectEditor
				EObjectEditor buildedEditor = EditingModelFactory.eINSTANCE.createEObjectEditor();
				((EObjectEditor) buildedEditor).setDefinition(newElementEditor);
				propertyBinding.setEditor(buildedEditor);
			}
			eClassBinding.getPropertyBindings().add(propertyBinding);
		}
	}

	/**
	 * @param feature
	 *            EStructuralFeature
	 * @return widget corresponding to the feature
	 */
	public Widget getWidgetForFeature(EStructuralFeature feature) {
		Collection<Widget> widgetsFor = toolkitProvider.getAllWidgetsFor(feature);
		if (widgetsFor.size() == 1) {
			return widgetsFor.iterator().next();
		} else {
			for (Widget widget : widgetsFor) {
				if (!TEXT_WIDGET_NAME.equals(widget.getName())) {
					return widget;
				}
			}
		}
		return null;
	}

	/**
	 * @param eObject
	 *            EObject
	 * @param createdView
	 *            org.eclipse.emf.eef.views.View
	 * @param propertiesEditingModel
	 *            PropertiesEditingModel
	 * @return the EClass binding
	 */
	public EClassBinding createEClassBinding(EObject eObject, org.eclipse.emf.eef.views.View createdView) {
		EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		eClassBinding.setEClass(eObject.eClass());
		EObjectView modelView = EditingModelFactory.eINSTANCE.createEObjectView();
		((EObjectView) modelView).setDefinition((EObject) createdView);
		eClassBinding.getViews().add(modelView);
		propertiesEditingModel.getBindings().add(eClassBinding);
		return eClassBinding;
	}

	/**
	 * @param eObject
	 *            EObject
	 * @return the created org.eclipse.emf.eef.views.View
	 */
	public org.eclipse.emf.eef.views.View createViewForEClassBinding(EObject eObject) {
		org.eclipse.emf.eef.views.View createdView = ViewsFactory.eINSTANCE.createView();
		createdView.setName(eObject.eClass().getName());
		return createdView;
	}

	/**
	 * @param eObject
	 *            EObject
	 * @param view
	 *            org.eclipse.emf.eef.views.View
	 * @return the new container
	 */
	public Container createContainerViewForEClassBinding(EObject eObject, org.eclipse.emf.eef.views.View view) {
		Container newGroup = ViewsFactory.eINSTANCE.createContainer();
		newGroup.setName(eObject.eClass().getName());
		newGroup.setRepresentation(toolkitProvider.getWidgetByName(GROUP_CONTAINER_NAME));
		view.getElements().add(newGroup);
		return newGroup;
	}

	/**
	 * @param eObject
	 *            EObject
	 * @return if the eobject class is already binding
	 */
	public boolean existEClassBinding(EObject eObject) {
		for (EClassBinding eClassBinding : propertiesEditingModel.getBindings()) {
			if (eClassBinding.getEClass().equals(eObject.eClass())) {
				return true;
			}
		}
		return false;
	}

}
