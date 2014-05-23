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
public abstract class MultiPropertyBindingCustomizer implements PropertyBindingCustomizer {

	public static final int ADDER = 20;
	public static final int REMOVER = 21;

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
	public EEFModifierCustomizer<Void> getAdder() {
		return new NullModifierCustomizer<Void>();
	}

	/**
	 * @return
	 */
	public EEFModifierCustomizer<Void> getRemover() {
		return new NullModifierCustomizer<Void>();
	}
}
