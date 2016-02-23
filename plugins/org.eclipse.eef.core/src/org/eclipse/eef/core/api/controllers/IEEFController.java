/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.eef.core.api.controllers;

import java.util.List;

/**
 * Common interface of all the controllers.
 *
 * @author sbegaudeau
 */
public interface IEEFController {
	/**
	 * Registers a consumer which will be called with the validation status.
	 *
	 * @param consumer
	 *            The consumer of the validation status
	 */
	void onValidation(IConsumer<List<IValidationRuleResult>> consumer);

	/**
	 * Removes the consumer of the validation.
	 */
	void removeValidationConsumer();

	/**
	 * Refresh the controller.
	 */
	void refresh();
}
