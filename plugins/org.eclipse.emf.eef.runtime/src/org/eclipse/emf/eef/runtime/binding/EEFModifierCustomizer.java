/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

import org.eclipse.emf.eef.runtime.util.EEFInvocationParameters;

/**
 * @author NLEPINE
 * 
 */
public interface EEFModifierCustomizer<T> {

	/**
	 * @param parameters
	 * @return
	 */
	T execute(EEFInvocationParameters parameters);
}
