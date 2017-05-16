/*******************************************************************************
 * Copyright (c) 2007, 2017 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.eef.ide.ui.properties.internal;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * This class is responsible for providing an ID used during the refresh to match candidates and existing elements. We
 * used to use EcoreUtil.getURI() but it was not performing well enough and could change when an attribute used as an
 * eKeys was changed.
 *
 * The ID is associated with the element through the eAdapters mechanism and as such is stable as long as the element is
 * there.
 *
 * This class is roughly based on RefreshIdsHolder in the Eclipse Sirius codebase.
 *
 * @author cbrun
 * @since 2.0
 *
 */
public final class RefreshIdsHolder extends AdapterImpl {

	/**
	 * The previous identifier created for an EObject.
	 */
	private static Integer lastID = Integer.valueOf(0);

	/**
	 * The identifier of the EObject to which this adapter is attached.
	 */
	private Integer id;

	/**
	 * The constructor.
	 *
	 * @param id
	 *            The identifier of the EObject
	 */
	private RefreshIdsHolder(Integer id) {
		this.id = id;
	}

	/**
	 * Return the element Id if there is one, create a new one if it's not already here.
	 *
	 * @param eObject
	 *            any EObject.
	 * @return the existing Id or the new one.
	 */
	public static Integer getOrCreateID(final EObject eObject) {
		// @formatter:off
		RefreshIdsHolder refreshIdsHolder = eObject.eAdapters().stream()
				.filter(RefreshIdsHolder.class::isInstance)
				.map(RefreshIdsHolder.class::cast)
				.findFirst()
				.orElseGet(() -> {
					RefreshIdsHolder newRefreshIdsHolder = new RefreshIdsHolder(lastID);
					eObject.eAdapters().add(newRefreshIdsHolder);
					lastID = Integer.valueOf(lastID.intValue() + 1);
					return newRefreshIdsHolder;
				});
		// @formatter:on

		return refreshIdsHolder.getId();
	}

	/**
	 * return the ID which got assigned to this Object.
	 *
	 * @return the ID which got assigned to this Object
	 */
	private Integer getId() {
		return this.id;
	}

}
