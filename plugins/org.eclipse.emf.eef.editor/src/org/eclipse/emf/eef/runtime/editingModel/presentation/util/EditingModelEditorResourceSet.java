/**
 * 
 */
package org.eclipse.emf.eef.runtime.editingModel.presentation.util;

import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.eef.runtime.ui.internal.resources.EEFURIConverter;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingModelEditorResourceSet extends ResourceSetImpl {

	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#getURIConverter()
	 */
	@Override
	public URIConverter getURIConverter() {
		if (uriConverter == null) {
			uriConverter = new EEFURIConverter(super.getURIConverter());
		}
		return uriConverter;
	}
	
}
