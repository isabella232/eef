/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e4.utils.impl;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.ui.swt.e4.utils.EditingInput;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class URIEditingInput implements EditingInput {

	private URI uri;
	private EditingDomain editingDomain;
	
	public URIEditingInput(URI uri, EditingDomain editingDomain) {
		this.uri = uri;
		this.editingDomain = editingDomain;
	}

	/**
	 * @return the uri
	 */
	public URI getUri() {
		return uri;
	}

	/**
	 * @return the editingDomain
	 */
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}
	
	public List<EObject> getRoots() {
		Resource resource = editingDomain.getResourceSet().getResource(uri, true);
		if (resource != null) {
			return resource.getContents();
		}
		return Collections.emptyList();
	}
	
	
}
