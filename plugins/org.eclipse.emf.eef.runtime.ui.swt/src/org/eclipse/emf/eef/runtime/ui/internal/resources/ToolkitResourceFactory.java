/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.resources;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ToolkitResourceFactory extends ResourceFactoryImpl {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl#createResource(org.eclipse.emf.common.util.URI)
	 */
	public Resource createResource(URI uri) {
		return new ToolkitResource(uri);
	}

	
	
}
