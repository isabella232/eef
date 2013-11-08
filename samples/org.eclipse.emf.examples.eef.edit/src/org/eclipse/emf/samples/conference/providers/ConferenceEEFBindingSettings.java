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
package org.eclipse.emf.samples.conference.providers;

import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ConferenceEEFBindingSettings extends EEFBindingSettingsImpl {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsImpl#getEditingModelPath()
	 */
	
	protected String getEditingModelPath() {
		return "platform:/plugin/org.eclipse.emf.examples.eef.edit/models/conference.editingmodel";
	}

	


}