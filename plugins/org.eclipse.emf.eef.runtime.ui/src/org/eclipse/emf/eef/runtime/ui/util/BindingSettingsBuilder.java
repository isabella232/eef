/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.util;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ToolkitHandler;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.ViewsRepository;
import org.eclipse.emf.eef.views.toolkits.Widget;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class BindingSettingsBuilder {

	/**
	 * GenFeature property constants
	 */
	public static final String GENFEATURE_PROPERTY_VALUE_READONLY = "Readonly";
	public static final String GENFEATURE_PROPERTY_MULTILINE = "propertyMultiLine";
	public static final String GENFEATURE_PROPERTY_VALUE_NONE = "None";
	public static final String GENFEATURE_PROPERTY_CATEGORY = "propertyCategory";
	public static final String GENFEATURE_PROPERTY = "property";

	private EEFToolkitProvider toolkitProvider;
	private ToolkitHandler toolkitHandler;

	private PropertiesEditingModel propertiesEditingModel;

	/**
	 * Widgets names
	 */
	private String GROUP_CONTAINER_NAME = "";
	private String TEXT_WIDGET_NAME = "";
	private String TEXTAREA_WIDGET_NAME = "";
	private ViewsRepository viewsRepository;

	private ResourceSet resourceSet;

	private ToolkitHandler getToolkitHandler() {
		if (toolkitHandler == null) {
			toolkitHandler = toolkitProvider.createHandler(resourceSet);
		}
		return toolkitHandler;
	}

	public BindingSettingsBuilder(PropertiesEditingModel propertiesEditingModel, ViewsRepository viewsRepository, EEFToolkitProvider toolkitProvider, String... names) {
		this.propertiesEditingModel = propertiesEditingModel;
		this.resourceSet = propertiesEditingModel.eResource().getResourceSet();
		this.viewsRepository = viewsRepository;
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
	public void bindEStructuralFeature(EClass eObject, EClassBinding eClassBinding, View createdView, EditingModelEnvironment editingModelEnvironment) {
		List<EStructuralFeature> eAllStructuralFeatures = sortEStructuralFeatures(eClassBinding.getEClass().getEAllStructuralFeatures());
		for (EStructuralFeature feature : eAllStructuralFeatures) {
			// get editable information from genmodel if exists
			boolean isReadonly = false;
			boolean isMultiLine = false;
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

				// get multiline property
				esf = genFeature.eClass().getEStructuralFeature(GENFEATURE_PROPERTY_MULTILINE);
				if (esf != null) {
					isMultiLine = (Boolean) genFeature.eGet(esf);
				}
			}

			// no genmodel property, no group
			if (createdGroup == null) {
				createdGroup = createContainerViewForEClassBinding(eObject, createdView);
			}
			// create property binding
			createPropertyBinding(eClassBinding, feature, isReadonly, isMultiLine, createdGroup);
		}
	}

	/**
	 * Sort structural features, required attributes first.
	 * 
	 * @param structuralFeatures
	 *            List<EStructuralFeature>
	 * @return List<EStructuralFeature>
	 */
	public List<EStructuralFeature> sortEStructuralFeatures(List<EStructuralFeature> structuralFeatures) {
		List<EStructuralFeature> result = Lists.newArrayList();
		for (EStructuralFeature eStructuralFeature : structuralFeatures) {
			if (eStructuralFeature instanceof EAttribute && eStructuralFeature.isRequired()) {
				result.add(eStructuralFeature);
			}
		}
		for (EStructuralFeature eStructuralFeature : structuralFeatures) {
			if (!result.contains(eStructuralFeature)) {
				result.add(eStructuralFeature);
			}
		}
		return result;
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
	 * @param isMultiLine
	 * @param createdGroup
	 *            Container
	 */
	public void createPropertyBinding(EClassBinding eClassBinding, EStructuralFeature feature, boolean isReadonly, boolean isMultiLine, Container createdGroup) {
		EStructuralFeatureBinding propertyBinding = EditingModelFactory.eINSTANCE.createEStructuralFeatureBinding();
		propertyBinding.setFeature(feature);
		Widget widget = null;
		if (isMultiLine && feature.getEType() instanceof EDataType && feature.getEType().getName().contains("String")) {
			widget = getToolkitHandler().getWidgetByName(TEXTAREA_WIDGET_NAME);
		}
		if (widget == null) {
			widget = getWidgetForFeature(feature);
		}

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
	public Container getGroup(EClass eObject, View createdView, Container createdGroup, EObject genFeature) {
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
					return createContainerViewForEClassBinding(category, createdView);
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
		if (!feature.isMany() && feature instanceof EAttribute && feature.getEType() != null && !(feature.getEType() instanceof EEnum) && (feature.getEType().getName().equals("EString") || feature.getEType().getName().equals("String")) && "documentation".equals(feature.getName())) {
			return getToolkitHandler().getWidgetByName(TEXTAREA_WIDGET_NAME);
		}
		Collection<Widget> widgetsFor = getToolkitHandler().getAllWidgetsFor(feature);
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
	public org.eclipse.emf.eef.views.View createViewForEClassBinding(EClass eObject) {
		org.eclipse.emf.eef.views.View createdView = ViewsFactory.eINSTANCE.createView();
		createdView.setName(eObject.getName());
		viewsRepository.getViews().add(createdView);
		return createdView;
	}

	/**
	 * @param eObject
	 *            EObject
	 * @param view
	 *            org.eclipse.emf.eef.views.View
	 * @return the new container
	 */
	public Container createContainerViewForEClassBinding(EClass eObject, org.eclipse.emf.eef.views.View view) {
		return createContainerViewForEClassBinding(eObject.getName(), view);
	}

	/**
	 * @param eObject
	 *            EObject
	 * @param view
	 *            org.eclipse.emf.eef.views.View
	 * @return the new container
	 */
	public Container createContainerViewForEClassBinding(String name, org.eclipse.emf.eef.views.View view) {
		Container newGroup = ViewsFactory.eINSTANCE.createContainer();
		newGroup.setName(name);
		newGroup.setRepresentation(getToolkitHandler().getWidgetByName(GROUP_CONTAINER_NAME));
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
	public Container getDefaultGroup(final EClass eObject, org.eclipse.emf.eef.views.View view) {
		Iterable<Container> containerFilter = Iterables.filter(view.getElements(), Container.class);
		Iterable<Container> filter = Iterables.filter(containerFilter, new Predicate<Container>() {
			public boolean apply(Container arg0) {
				return !Strings.isNullOrEmpty(eObject.getName()) && eObject.getName().equals(arg0.getName());
			}
		});
		return Iterables.getFirst(filter, null);
	}

	/**
	 * @param eObject
	 *            EObject
	 * @return if the eobject class is already binding
	 */
	public boolean existEClassBinding(EClass eObject) {
		for (EClassBinding eClassBinding : propertiesEditingModel.getBindings()) {
			if (eClassBinding.getEClass().getName().equals(eObject.getName()) && eClassBinding.getEClass().getEPackage().getNsURI().equals(eObject.getEPackage().getNsURI())) {
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
