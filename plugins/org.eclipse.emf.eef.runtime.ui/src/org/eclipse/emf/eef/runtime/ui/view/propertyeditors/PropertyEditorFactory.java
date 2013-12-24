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
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.ViewElement;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditorFactory<T> {
	
	/**
	 * Return the PropertyEditor for this ElementEditor
	 * @param editorContext {@link PropertyEditorContext} to process.
	 * @return the {@link PropertyEditor} for the given {@link ElementEditor}. 
	 */
	PropertyEditor getPropertyEditor(PropertyEditorContext editorContext);
	
	/**
	 * Represents a context for a {@link PropertyEditor} composed of:
	 * 	- a View
	 * 	- a ElementEditor description
	 *  
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 */
	public final class PropertyEditorContext {
		
		public PropertiesEditingView<?> view;
		public PropertyBinding propertyBinding;
		public ViewElement viewElement;
		/**
		 * @param view
		 * @param propertyBinding
		 * @param viewElement
		 */
		public PropertyEditorContext(PropertiesEditingView<?> view, PropertyBinding propertyBinding, ViewElement viewElement) {
			this.view = view;
			this.propertyBinding = propertyBinding;
			this.viewElement = viewElement;
		}
		
	}

}
