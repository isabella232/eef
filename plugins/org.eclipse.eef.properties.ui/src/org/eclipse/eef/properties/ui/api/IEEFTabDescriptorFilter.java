/*******************************************************************************
 * Copyright (c) 2016, 2018 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.eef.properties.ui.api;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This interface is used to filter {@link IEEFTabDescriptor}.
 *
 * @author mbats
 */
public interface IEEFTabDescriptorFilter {
	/**
	 * Returns if a tab descriptor must be filtered or not.
	 *
	 * @param tabDescriptor
	 *            The tab descriptor
	 *
	 * @return <code>true</code> if the tab descriptor should be used, <code>false</code> otherwise
	 */
	@Deprecated
	default boolean filter(IEEFTabDescriptor tabDescriptor) {
		return true;
	}

	/**
	 * Returns if a tab descriptor must be filtered or not.
	 *
	 * @param tabDescriptor
	 *            The tab descriptor
	 * @param part
	 *            The workbench part
	 * @param selection
	 *            The selection
	 *
	 * @return <code>true</code> if the tab descriptor should be used, <code>false</code> otherwise
	 */
	default boolean filter(IEEFTabDescriptor tabDescriptor, IWorkbenchPart part, ISelection selection) {
		return this.filter(tabDescriptor);
	}
}
