/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.util;

import java.util.Collection;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class BindingSettingsBuilder {

	/**
	 * GenFeature property constants
	 */
	public static final String GENFEATURE_PROPERTY_VALUE_READONLY = "Readonly";
	public static final String GENFEATURE_PROPERTY_VALUE_NONE = "None";
	public static final String GENFEATURE_PROPERTY_CATEGORY = "propertyCategory";
	public static final String GENFEATURE_PROPERTY = "property";

	private EEFToolkitProvider toolkitProvider;
	private PropertiesEditingModel propertiesEditingModel;

	/**
	 * Widgets names
	 */
	private String GROUP_CONTAINER_NAME = "";
	private String TEXT_WIDGET_NAME = "";
	private String TEXTAREA_WIDGET_NAME = "";

	public BindingSettingsBuilder(PropertiesEditingModel propertiesEditingModel, EEFToolkitProvider toolkitProvider) {
		this.propertiesEditingModel = propertiesEditingModel;
		this.toolkitProvider = toolkitProvider;
	}

	public BindingSettingsBuilder(PropertiesEditingModel propertiesEditingModel, EEFToolkitProvider toolkitProvider, String... names) {
		this.propertiesEditingModel = propertiesEditingModel;
		this.toolkitProvider = toolkitProvider;
		if (names.length > 0) {
			this.GROUP_CONTAINER_NAME = names[0];
		}
		if (names.length > 1) {
			this.TEXT_WIDGET_NAME = names[1];
		}
		if (names.length > 2) {
			this.TEXTAREA_WIDGET_NAME = names[2];
		}

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
	 * @param editingModelEnvironment
	 */
	public void bindEStructuralFeature(EObject eObject, EClassBinding eClassBinding, View createdView, EditingModelEnvironment editingModelEnvironment) {
		for (EStructuralFeature feature : eClassBinding.getEClass().getEAllStructuralFeatures()) {
			// get editable information from genmodel if exists
			boolean isReadonly = false;
			Container createdGroup = getDefaultGroup(eObject, createdView);
			EObject genFeature = editingModelEnvironment.genFeature(feature);
			if (genFeature != null) {
				// get property type from genmodel : None, Editable, Readonly
				EStructuralFeature esf = genFeature.eClass().getEStructuralFeature(GENFEATURE_PROPERTY);
				if (esf != null) {
					Enumerator eGet = (Enumerator) genFeature.eGet(esf);
					if (GENFEATURE_PROPERTY_VALUE_NONE.equals(eGet.getName())) {
						// no property view
						continue;
					}
					if (GENFEATURE_PROPERTY_VALUE_READONLY.equals(eGet.getName())) {
						isReadonly = true;
					}
				}
				// get property category for genmodel : 1 category = 1 group
				createdGroup = getGroup(eObject, createdView, createdGroup, genFeature);
			}

			// create property binding
			createPropertyBinding(eClassBinding, feature, isReadonly, createdGroup);
		}
	}

	/**
	 * Create property binding.
	 * 
	 * @param eClassBinding
	 *            EClassBinding
	 * @param feature
	 *            EStructuralFeature
	 * @param isReadonly
	 *            boolean
	 * @param createdGroup
	 *            Container
	 */
	public void createPropertyBinding(EClassBinding eClassBinding, EStructuralFeature feature, boolean isReadonly, Container createdGroup) {
		PropertyBinding propertyBinding = EditingModelFactory.eINSTANCE.createPropertyBinding();
		propertyBinding.setFeature(feature);
		Widget widget = getWidgetForFeature(feature);
		if (widget != null && feature != null) {
			// create element editor
			ElementEditor newElementEditor = ViewsFactory.eINSTANCE.createElementEditor();
			newElementEditor.setName(feature.getName());
			newElementEditor.setRepresentation(widget);
			newElementEditor.setReadOnly(isReadonly);
			if (createdGroup != null) {
				createdGroup.getElements().add(newElementEditor);
			}
			// create EObjectEditor
			EObjectEditor buildedEditor = EditingModelFactory.eINSTANCE.createEObjectEditor();
			((EObjectEditor) buildedEditor).setDefinition(newElementEditor);
			propertyBinding.setEditor(buildedEditor);
		}
		eClassBinding.getPropertyBindings().add(propertyBinding);
	}

	/**
	 * @param eObject
	 *            EObject
	 * @param createdView
	 *            View
	 * @param createdGroup
	 *            defaukt group if exists
	 * @param genFeature
	 *            EObject
	 * @return the group
	 * 
	 */
	public Container getGroup(EObject eObject, View createdView, Container createdGroup, EObject genFeature) {
		EStructuralFeature esf;
		esf = genFeature.eClass().getEStructuralFeature(GENFEATURE_PROPERTY_CATEGORY);
		if (esf != null) {
			String category = (String) genFeature.eGet(esf);
			boolean existsDefaultGroup = createdGroup != null;
			if (Strings.isNullOrEmpty(category)) {
				if (!existsDefaultGroup) {
					return createContainerViewForEClassBinding(eObject, createdView);
				}
				return createdGroup;
			} else {
				Container group = getGroup(category, createdView);
				if (group == null) {
					return createContainerViewForEClassBinding(eObject, category, createdView);
				}
				return group;
			}
		}
		if (createdGroup == null) {
			return createContainerViewForEClassBinding(eObject, createdView);
		}
		return createdGroup;
	}

	/**
	 * @param feature
	 *            EStructuralFeature
	 * @return widget corresponding to the feature
	 */
	public Widget getWidgetForFeature(EStructuralFeature feature) {
		if (!feature.isMany() && feature instanceof EAttribute && (feature.getEType().getName().equals("EString") || feature.getEType().getName().equals("String"))
				&& "documentation".equals(feature.getName())) {
			return toolkitProvider.getWidgetByName(TEXTAREA_WIDGET_NAME);
		}
		Collection<Widget> widgetsFor = toolkitProvider.getAllWidgetsFor(feature);
		if (widgetsFor.size() == 1) {
			return widgetsFor.iterator().next();
		} else {
			Collection<Widget> widgets = widgetsFor;
			if (useFirstStandardWidgets()) {
				widgets = Collections2.filter(widgetsFor, new StandardEEFToolkitsSelector());
			}
			for (Widget widget : widgets) {
				if (!TEXT_WIDGET_NAME.equals(widget.getName()) && !TEXTAREA_WIDGET_NAME.equals(widget.getName())) {
					return widget;
				}
			}
			if (!widgets.isEmpty()) {
				return widgets.iterator().next();
			}
		}
		return null;
	}

	/**
	 * Defines if the widget selection algorithm have to eliminate specific
	 * toolkits.
	 * 
	 * @return the selection strategy.
	 */
	protected boolean useFirstStandardWidgets() {
		return true;
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
	public EClassBinding createEClassBinding(EClass eObject, org.eclipse.emf.eef.views.View createdView) {
		EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		eClassBinding.setEClass(eObject);
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
		return createContainerViewForEClassBinding(eObject, eObject.eClass().getName(), view);
	}

	/**
	 * @param eObject
	 *            EObject
	 * @param view
	 *            org.eclipse.emf.eef.views.View
	 * @return the new container
	 */
	public Container createContainerViewForEClassBinding(EObject eObject, String name, org.eclipse.emf.eef.views.View view) {
		Container newGroup = ViewsFactory.eINSTANCE.createContainer();
		newGroup.setName(name);
		newGroup.setRepresentation(toolkitProvider.getWidgetByName(GROUP_CONTAINER_NAME));
		view.getElements().add(newGroup);
		return newGroup;
	}

	/**
	 * @param category
	 *            String
	 * @param view
	 *            View
	 * @return if the group already exists
	 */
	public Container getGroup(final String category, org.eclipse.emf.eef.views.View view) {
		Iterable<Container> containerFilter = Iterables.filter(view.getElements(), Container.class);
		Iterable<Container> filter = Iterables.filter(containerFilter, new Predicate<Container>() {
			public boolean apply(Container arg0) {
				return !Strings.isNullOrEmpty(category) && category.equals(arg0.getName());
			}
		});
		return Iterables.getFirst(filter, null);
	}

	/**
	 * @param category
	 *            String
	 * @param view
	 *            View
	 * @return if the default group already exists
	 */
	public Container getDefaultGroup(final EObject eObject, org.eclipse.emf.eef.views.View view) {
		Iterable<Container> containerFilter = Iterables.filter(view.getElements(), Container.class);
		Iterable<Container> filter = Iterables.filter(containerFilter, new Predicate<Container>() {
			public boolean apply(Container arg0) {
				return !Strings.isNullOrEmpty(eObject.eClass().getName()) && eObject.eClass().getName().equals(arg0.getName());
			}
		});
		return Iterables.getFirst(filter, null);
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

	private static class StandardEEFToolkitsSelector implements Predicate<Widget> {

		public boolean apply(Widget widget) {
			return widget.getToolkit().getName() == "swt" || widget.getToolkit().getName() == "EMFProperties";
		}

	}

}
