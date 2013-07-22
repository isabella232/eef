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
package org.eclipse.emf.eef.runtime.ui.swt.resources;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ImageManager {
	
	/**
	 * Return an embedded image of the runtime.
	 * @param resourceLocator {@link ResourceLocator} to use.
	 * @param key ID of the image
	 * @return the associated image.
	 */
	Image getImage(ResourceLocator resourceLocator, String key);

}
