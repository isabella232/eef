/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.eef.runtime.model.EClassBinding;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;

/**
 * @author glefur
 *
 */
public class EClassBindingImpl implements EClassBinding {

	private EClass eClass;
	private Object view;
	private ViewHandler<?> handler;
	
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

	/**
	 * @return the handler
	 */
	public ViewHandler<?> getHandler() {
		return handler;
	}

	/**
	 * @param handler the handler to set
	 */
	public void setHandler(ViewHandler<?> handler) {
		this.handler = handler;
	}
	
	
}
