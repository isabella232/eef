/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e4.utils.impl;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.ui.swt.e4.utils.EditingInput;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class VoidEditingInput implements EditingInput {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e4.utils.EditingInput#getEditingDomain()
	 */
	public EditingDomain getEditingDomain() {
		return null;
	}

}
