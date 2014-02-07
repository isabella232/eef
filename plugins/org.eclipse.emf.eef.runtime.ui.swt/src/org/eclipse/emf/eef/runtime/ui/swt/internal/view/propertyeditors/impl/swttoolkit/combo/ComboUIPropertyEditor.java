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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.combo;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ComboUIPropertyEditor {

	/**
	 * Initializes the input of the combo owned by the propertyEditor.
	 * @param input the editing settings.
	 */
	public void initCombo(ComboContentProviderInput input);
	
	public final static class ComboContentProvider implements IStructuredContentProvider {
		
		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() { }

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {	}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			Object choiceOfValues = null;
			if (inputElement instanceof ComboContentProviderInput) {
				PropertiesEditingContext contentProviderContext = ((ComboContentProviderInput) inputElement).getEditingContext();
				EEFEditingService service = contentProviderContext.getEEFEditingServiceProvider().getEditingService((((ComboContentProviderInput) inputElement).getEditingContext().getEditingComponent().getEObject().eClass().getEPackage()));
				choiceOfValues = service.getChoiceOfValue(contentProviderContext, contentProviderContext.getEditingComponent().getEObject(), ((ComboContentProviderInput) inputElement).getElementEditor());
			} else {
				choiceOfValues = inputElement;
			}
			if (choiceOfValues instanceof EObject) {
				//TODO: Purely arbitrary choice, to validate.
				return ((EObject) choiceOfValues).eContents().toArray();
			}
			if (choiceOfValues instanceof List<?>) {
				return ((List<?>) choiceOfValues).toArray();
			}
			if (choiceOfValues instanceof Object[]) {
				return (Object[]) choiceOfValues;
			} 
			return null;
		}
		
		
		
	}
	
	public static final class ComboContentProviderInput {
		
		private PropertiesEditingContext editingContext;
		private ElementEditor elementEditor;

		/**
		 * @param editingContext
		 * @param propertyBinding
		 */
		public ComboContentProviderInput(PropertiesEditingContext editingContext, ElementEditor elementEditor) {
			this.editingContext = editingContext;
			this.elementEditor = elementEditor;
		}
		
		/**
		 * @return the editingContext
		 */
		public PropertiesEditingContext getEditingContext() {
			return editingContext;
		}

		/**
		 * @return the elementEditor
		 */
		public final ElementEditor getElementEditor() {
			return elementEditor;
		}
		
	}

}
