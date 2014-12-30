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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.undefined.editor;

import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class AbstractUndefinedPropertyEditor {
	
	/**
	 * Builds a comprehensive error message for the given {@link ViewElement}.
	 * @param element {@link ViewElement} to process.
	 * @return an error message.
	 */
	protected String buildErrorMessage(ViewElement element) {
		StringBuilder sb = new StringBuilder("Unable to provide a PropertyEditor for editor ");
		if (element.getName() != null) { 
			sb.append(element.getName());
		} else {
			sb.append("<NO-NAME>");
		}
		sb.append(", ");
		if (element.getRepresentation() == null) {
			sb.append("no representation is defined");
		} else {
			Widget representation = element.getRepresentation();
			if (representation.getName() != null) {
				sb.append("no PropertyEditorFactory can handle a editor using the widget ").append(representation.getName()).append('.');
			} else {
				sb.append("no PropertyEditorFactory can handle the widget associated to this editor");
			}
			if (representation.eContainer() instanceof Toolkit) {
				Toolkit tk = (Toolkit) representation.eContainer();
				if (tk.getName() != null) {
					sb.append("(toolkit: ").append(tk.getName()).append(')');
				}
			}
		}
		sb.append('.');
		String text = sb.toString();
		return text;
	}

}
