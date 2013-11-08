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
package org.eclipse.emf.eef.runtime.ui.view;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewSettings {

	/**
	 * @return the default MultiEditor height
	 */
	int getMultiEditorHeight();
	
	/**
	 * @return the default EEF selection dialogs height 
	 */
	int getEEFSelectionDialogHeight();
	
	/**
	 * @return the default EEF selection dialogs width 
	 */
	int getEEFSelectionDialogWidth();

}
