/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

import org.eclipse.emf.eef.runtime.internal.binding.NullModifierCustomizer;
import org.eclipse.emf.eef.runtime.util.EEFInvocationParameters;

/**
 * @author NLEPINE
 * 
 */
public abstract class MonoPropertyBindingCustomizer implements PropertyBindingCustomizer {

	public static final int SETTER = 10;
	public static final int UNSETTER = 11;

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer#getGetter()
	 */
	public EEFModifierCustomizer<Object> getGetter() {
		return new NullModifierCustomizer<Object>();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer#getValuesProvider()
	 */
	public EEFModifierCustomizer<Object> getValuesProvider() {
		return new NullModifierCustomizer<Object>();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer#getEventFilter()
	 */
	public EEFModifierCustomizer<Boolean> getEventFilter() {
		return new NullModifierCustomizer<Boolean>() {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.internal.binding.NullModifierCustomizer#execute(org.eclipse.emf.eef.runtime.util.EEFInvocationParameters)
			 */
			@Override
			public Boolean execute(EEFInvocationParameters parameters) {
				return false;
			}

		};
	}

	/**
	 * @return
	 */
	public EEFModifierCustomizer<Void> getSetter() {
		return new NullModifierCustomizer<Void>();
	}

	/**
	 * @return
	 */
	public EEFModifierCustomizer<Void> getUnsetter() {
		return new NullModifierCustomizer<Void>();
	}
}
