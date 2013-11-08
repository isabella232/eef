/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.policies;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class EditingPolicyValidation {
	
	private PropertiesEditingPolicy testedPolicy;
	
	private boolean result;
	
	private Object data;

	/**
	 * @param testedPolicy the tested {@link PropertiesEditingPolicy}.
	 * @param result result of the validation.
	 */
	public EditingPolicyValidation(PropertiesEditingPolicy testedPolicy, boolean result) {
		this.testedPolicy = testedPolicy;
		this.result = result;
	}

	/**
	 * @param testedPolicy the tested {@link PropertiesEditingPolicy}.
	 * @param result result of the validation.
	 * @param data Additional information on the validation purpose.
	 */
	public EditingPolicyValidation(PropertiesEditingPolicy testedPolicy, boolean result, Object data) {
		this.testedPolicy = testedPolicy;
		this.result = result;
		this.data = data;
	}

	/**
	 * @return the testedPolicy
	 */
	public PropertiesEditingPolicy getTestedPolicy() {
		return testedPolicy;
	}

	/**
	 * @return the result
	 */
	public boolean canPerform() {
		return result;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

}
