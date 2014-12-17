/*******************************************************************************
 * Copyright (c) 2014 IFPEN.
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class ToolkitHandler {

	private ResourceSet resourceSet;
	private EEFToolkitProvider toolkitProvider;

	/**
	 * NOTE: here we put the 'model toolkit' in a new Resource. This implies
	 * that it will be removed from potential existing resource. Can be bug
	 * provider!
	 * 
	 * @param toolkitProvider
	 * @param resourceSet
	 */
	public ToolkitHandler(EEFToolkitProvider toolkitProvider, ResourceSet resourceSet) {
		this.toolkitProvider = toolkitProvider;
		this.resourceSet = resourceSet;
		for (EEFToolkit<?> toolkit : toolkitProvider.getAllToolkits()) {
			String uri = new StringBuilder("eeftoolkit://").append(toolkit.getModel().getName()).append(".eeftoolkit").toString();
			// TODO check that the resource doesn't exist yet.
			Resource resource = resourceSet.createResource(URI.createURI(uri));
			// TODO to clone
			resource.getContents().add(toolkit.getModel());
		}
	}

	/**
	 * Returns all widgets which can display the feature.
	 * 
	 * @param eStructuralFeature
	 *            EStructuralFeature
	 */
	public Collection<Widget> getAllWidgetsFor(EStructuralFeature eStructuralFeature) {
		Collection<Widget> widgets = new ArrayList<Widget>();
		for (EEFToolkit<?> toolkit : toolkitProvider.getAllToolkits()) {
			widgets.addAll(toolkit.getAllWidgetsFor(eStructuralFeature));

		}
		return widgets;
	}

	/**
	 * @param name
	 *            String
	 * @return the first widget named "name" in toolkits.
	 */
	public Widget getWidgetByName(String name) {
		for (EEFToolkit<?> toolkit : toolkitProvider.getAllToolkits()) {
			Widget widget = toolkit.getWidgetByName(name);
			if (widget != null) {
				return widget;
			}
		}
		return null;
	}

}