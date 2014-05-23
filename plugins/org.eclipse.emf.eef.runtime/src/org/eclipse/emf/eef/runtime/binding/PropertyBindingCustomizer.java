/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

/**
 * @author NLEPINE
 * 
 */
public interface PropertyBindingCustomizer {

	final int GETTER = 0;
	final int VALUES_PROVIDER = 1;
	final int EVENT_FILTER = 2;

	/**
	 * @return
	 */
	EEFModifierCustomizer<Object> getGetter();

	/**
	 * @return
	 */
	EEFModifierCustomizer<Object> getValuesProvider();

	/**
	 * @return
	 */
	EEFModifierCustomizer<Boolean> getEventFilter();

}
