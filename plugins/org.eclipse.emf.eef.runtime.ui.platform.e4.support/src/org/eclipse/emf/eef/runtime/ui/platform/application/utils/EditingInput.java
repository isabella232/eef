/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.application.utils;

import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingInput {
	
	/**
	 * Returns the {@link EditingDomain} associated to this input.
	 * @return the {@link EditingDomain} of the input.
	 */
	EditingDomain getEditingDomain();

}
