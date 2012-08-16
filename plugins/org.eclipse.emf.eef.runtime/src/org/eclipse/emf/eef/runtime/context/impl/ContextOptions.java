/**
 * 
 */
package org.eclipse.emf.eef.runtime.context.impl;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ContextOptions {

	private final static String AUTOWIRING_OPTION = "AUTOWIRING OPTION";
	private final static String VALIDATION_OPTION = "VALIDATION OPTION";
	private final static String DELAYEDFIREPROPERTIESCHANGED_DELAY = "DELAYED FIREPROPERTIESCHANGED DELAY";
	private final static String BATCH_MODE = "BATCH MODE";
	
	private final Map<String, Object> options;

	/**
	 * 
	 */
	public ContextOptions() {
		options = Maps.newHashMap();
		initDefaultOptions();
	}
	
	protected void initDefaultOptions() {
		setOption(AUTOWIRING_OPTION, true);
		setOption(VALIDATION_OPTION, true);
		setOption(DELAYEDFIREPROPERTIESCHANGED_DELAY, 500L);
		setOption(BATCH_MODE, false);
	}

	/**
	 * Puts an option value in the context.
	 * @param key name of the option.
	 * @param value value of the option.
	 */
	public void setOption(final String key, final Object value) {
		options.put(key, value);
	}
	
	/**
	 * @param key name of the option.
	 * @return the value of the key.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getOption(final String key) {
		return (T)options.get(key);
	}

	/**
	 * @return the autowiring state of the context.
	 */
	public boolean autowire() {
		Boolean object = getOption(AUTOWIRING_OPTION);
		if (object != null) {
			return object.booleanValue();
		} else {
			return true;
		}
	}

	/**
	 * Sets the autowiring state of the context.
	 * @param autowiring the new state.
	 */
	public void setAutowiring(final boolean autowiring) {
		options.put(AUTOWIRING_OPTION, Boolean.valueOf(autowiring));
	}
	
	/**
	 * @return the flag specifying if the component should validate the edited object on an editing event.
	 */
	public boolean validateEditing() {
		Boolean object = getOption(VALIDATION_OPTION);
		if (object != null) {
			return object.booleanValue();
		} else {
			return false;
		}
	}
	
	/**
	 * Sets the state if the validation on editing state.
	 * @param validateEditing the new state.
	 */
	public void setValidateEditing(final boolean validateEditing) {
		options.put(VALIDATION_OPTION, Boolean.valueOf(validateEditing));
	}
	
	/**
	 * @return the delay to wait before processing an editing event.
	 */
	public long delayedFirePropertiesChangedDelay() {
		Long object = getOption(DELAYEDFIREPROPERTIESCHANGED_DELAY);
		if (object != null) {
			return object.longValue();
		} else {
			return 500L;
		}
	}
	
	/**
	 * Sets the delay to wait before processing an editing event.
	 * @param delay the new delay.
	 */
	public void setDelayedFirePropertiesChangedDelay(final long delay) {
		options.put(DELAYEDFIREPROPERTIESCHANGED_DELAY, delay);
	}
	
	/**
	 * @return <code>true</code> if the editing mode of current context is "batch"
	 */
	public boolean batchMode() {
		return getOption(BATCH_MODE);
	}
	
	/**
	 * @return <code>true</code> if the editing mode of current context is "live"
	 */
	public boolean liveMode() {
		return !batchMode();
	}
	
	/**
	 * Sets the editing mode of this context.
	 * @param mode batch mode if <code>true</code> live otherwise.
	 */
	public void setBatchMode(final boolean mode) {
		options.put(BATCH_MODE, mode);
	}
	
}