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
package org.eclipse.emf.eef.runtime.ui.internal.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.emf.eef.views.ViewsRepository;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class NamingHelper {

	/**
	 * @param element
	 * @return
	 */
	public static String nameDiscriminator(ViewElement element) {
		String baseName = element.getName();
		StringBuffer buffer = new StringBuffer();
		EObject container = element.eContainer();
		while (container instanceof ViewElement) {
			if (equalsIgnoreWhiteSpacesAndCase(((ViewElement) container).getName(), baseName)) {
				buffer.append('_');
			}
			container = container.eContainer();
		}
		if (container != null) {
			ViewsRepository repository = repository(container);
			if (repository != null) {
				if (equalsIgnoreWhiteSpacesAndCase(repository.getName(), baseName)) {
					buffer.append('_');
				}
			}
		}
		return buffer.toString();
	}

	private static boolean equalsIgnoreWhiteSpacesAndCase(String name1, String name2) {
		if (removeWhiteSpaces(name1) == null && removeWhiteSpaces(name2) == null) {
			return true;
		}
		if (removeWhiteSpaces(name1) == null && removeWhiteSpaces(name2) != null) {
			return false;
		}
		return removeWhiteSpaces(name1).equalsIgnoreCase(removeWhiteSpaces(name2));
	}

	private static String removeWhiteSpaces(String name) {
		return name == null ? null : name.replaceAll("\\s", "").trim();
	}

	private static ViewsRepository repository(EObject obj) {
		EObject container = obj.eContainer();
		while (container != null) {
			if (container instanceof ViewsRepository) {
				return (ViewsRepository) container;
			}
			container = container.eContainer();
		}
		return null;
	}
}
