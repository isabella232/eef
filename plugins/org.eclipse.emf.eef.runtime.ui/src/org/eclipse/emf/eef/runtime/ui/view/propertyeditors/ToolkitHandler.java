/*******************************************************************************
 * Copyright (c) 2014 IFPEN.
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.osgi.framework.FrameworkUtil;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class ToolkitHandler {

	private ResourceSet resourceSet;
	private EEFToolkitProvider toolkitProvider;
	private Copier copier = new Copier();
	private EEFLogger eefLogger;

	/**
	 * NOTE: here we put the 'model toolkit' in a new Resource. This implies
	 * that it will be removed from potential existing resource. Can be bug
	 * provider!
	 * 
	 * @param toolkitProvider
	 * @param resourceSet
	 * @param eefLogger
	 */
	public ToolkitHandler(EEFToolkitProvider toolkitProvider, ResourceSet resourceSet, EEFLogger eefLogger) {
		this.toolkitProvider = toolkitProvider;
		this.resourceSet = resourceSet;
		this.eefLogger = eefLogger;
		for (EEFToolkit<?> toolkit : toolkitProvider.getAllToolkits()) {
			String uri = new StringBuilder("eeftoolkit:/").append(FrameworkUtil.getBundle(toolkit.getClass()).getSymbolicName()).append('/').append(toolkit.getClass().getCanonicalName()).toString();
			URI createURI = URI.createURI(uri);
			Resource resource = resourceSet.getResource(URI.createURI(uri), true);
			if (!resource.getContents().isEmpty()) {
				EObject eObject = resource.getContents().get(0);
				for (Iterator iterator = eObject.eAllContents(); iterator.hasNext();) {
					EObject type = (EObject) iterator.next();
					if (type instanceof Widget) {
						copier.put(getWidgetByName2(((Widget) type).getName()), type);
					}
				}

			}
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
			Collection<Widget> allWidgetsFor = toolkit.getAllWidgetsFor(eStructuralFeature);
			for (Widget widget : allWidgetsFor) {
				if (copier.get(widget) == null) {
					eefLogger.logError(EEFRuntimeUI.PLUGIN_ID, "Widget not found in toolkit", null);
				}
				widgets.add((Widget) copier.get(widget));
			}

		}
		return widgets;
	}

	/**
	 * @param name
	 *            String
	 * @return the first widget named "name" in toolkits.
	 */
	public Widget getWidgetByName(String name) {
		Widget widget = getWidgetByName2(name);
		if (copier.get(widget) == null) {
			eefLogger.logError(EEFRuntimeUI.PLUGIN_ID, "Widget not found in toolkit", null);
		}
		return (Widget) copier.get(widget);
	}

	public Widget getWidgetByName2(String name) {
		for (EEFToolkit<?> toolkit : toolkitProvider.getAllToolkits()) {
			Widget widget = toolkit.getWidgetByName(name);
			if (widget != null) {
				return widget;
			}
		}
		return null;
	}

}