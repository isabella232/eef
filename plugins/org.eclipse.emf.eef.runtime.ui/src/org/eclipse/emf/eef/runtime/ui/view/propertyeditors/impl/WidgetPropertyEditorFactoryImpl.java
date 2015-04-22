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
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class WidgetPropertyEditorFactoryImpl<T> implements WidgetPropertyEditorFactory<T> {

	private EEFToolkit<T> toolkit;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#setToolkit(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.EEFToolkitImpl)
	 */
	public final void setToolkit(EEFToolkit<T> toolkit) {
		this.toolkit = toolkit;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#serviceFor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return this.getClass().getName().equals(editorContext.viewElement.getRepresentation().getImplementation());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl#getModel(org.eclipse.emf.ecore.resource.Resource)
	 */
	public Widget getModel(Resource resource) {
		TreeIterator<EObject> allContents = resource.getAllContents();
		while (allContents.hasNext()) {
			EObject next = allContents.next();
			if (next instanceof Widget) {
				if (this.getClass().getName().equals(((Widget) next).getImplementation())) {
					return (Widget) next;
				}
 			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorFactory#firePropertiesChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
	 *      org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public final void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		toolkit.firePropertiesChanged(editingComponent, editingEvent);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public final PropertyEditor getPropertyEditor(PropertyEditorContext editorContext) {
		PropertyEditor propertyEditor = createPropertyEditor(editorContext);
		if (propertyEditor instanceof PropertyEditorImpl) {
			((PropertyEditorImpl) propertyEditor).setPropertyEditorFactory(this);
		}
		return propertyEditor;
	}

	protected abstract PropertyEditor createPropertyEditor(PropertyEditorContext editorContext);

}
