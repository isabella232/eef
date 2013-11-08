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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.resources;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class E3ImageManager implements ImageManager {
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.services.resources.ImageManager#getImage(org.eclipse.emf.common.util.ResourceLocator, java.lang.String)
	 */
	public Image getImage(ResourceLocator resourceLocator, String key) {
		Object image = resourceLocator.getImage(key);
		return ExtendedImageRegistry.getInstance().getImage(image);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager#getImageFromObject(java.lang.Object)
	 */
	public Image getImageFromObject(Object object) {
		return ExtendedImageRegistry.getInstance().getImage(object);
	}

}
