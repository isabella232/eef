/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e4.utils.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingContextEditingInput extends URIEditingInput {

	private DomainAwarePropertiesEditingContext editingContext;

	public EditingContextEditingInput(URI uri, DomainAwarePropertiesEditingContext editingContext) {
		super(uri, editingContext.getEditingDomain());
		this.editingContext = editingContext;
	}

	/**
	 * @return the editingContext
	 */
	public DomainAwarePropertiesEditingContext getEditingContext() {
		return editingContext;
	}

}
