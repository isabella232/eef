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
package org.eclipse.emf.eef.runtime.editingModel.resources;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class EditingModelResourceFactory extends XMIResourceFactoryImpl {

	@Override
	public Resource createResource(URI uri) {
		EditingModelResource resource = new EditingModelResource(uri);

		XMLResource.URIHandler uriHandler = new XMLResource.URIHandler() {

			public void setBaseURI(URI uri) {
			}

			public URI deresolve(URI uri) {
				return uri;
			}

			public URI resolve(URI uri) {
				return uri;
			}

		};
		resource.getDefaultLoadOptions().put(XMLResource.OPTION_URI_HANDLER, uriHandler);
		resource.getDefaultSaveOptions().put(XMLResource.OPTION_URI_HANDLER, uriHandler);

		return resource;
	}

}
