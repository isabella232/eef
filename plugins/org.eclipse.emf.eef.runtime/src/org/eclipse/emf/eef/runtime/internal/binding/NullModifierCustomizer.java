/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer;
import org.eclipse.emf.eef.runtime.util.EEFInvocationParameters;

/**
 * @author NLEPINE
 * 
 */
public class NullModifierCustomizer<T> implements EEFModifierCustomizer<T> {

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer#execute(org.eclipse.emf.eef.runtime.util.EEFInvocationParameters)
	 */
	public T execute(EEFInvocationParameters parameters) {
		return null;
	}

}
