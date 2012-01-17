/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.eef.runtime.model.EClassBinding;

/**
 * @author glefur
 *
 */
public class EClassBindingImpl implements EClassBinding {

	private EClass eClass;
	private Object view;
	
	/**
	 * @param eClass
	 * @param view
	 */
	public EClassBindingImpl(EClass eClass, Object view) {
		this.eClass = eClass;
		this.view = view;
	}

	/**
	 * @return the eClass
	 */
	public EClass getEClass() {
		return eClass;
	}

	/**
	 * @return the view
	 */
	public Object getView() {
		return view;
	}
	
}
