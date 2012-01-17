/**
 * 
 */
package org.eclipse.emf.eef.runtime.model;

import org.eclipse.emf.ecore.EClass;

/**
 * @author glefur
 *
 */
public interface EClassBinding {

	/**
	 * Binded EClass.
	 * @return the binded {@link EClass}.
	 */
	EClass getEClass();

	/**
	 * View of the binding.
	 * @return the view of the binding.
	 */
	Object getView();
}
