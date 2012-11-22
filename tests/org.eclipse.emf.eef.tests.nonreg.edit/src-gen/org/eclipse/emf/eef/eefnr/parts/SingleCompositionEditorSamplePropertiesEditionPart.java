/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.eefnr.parts;

// Start of user code for imports
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;


// End of user code

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public interface SingleCompositionEditorSamplePropertiesEditionPart {

	/**
	 * @return the singleCompositionEditorRequiredProperty
	 * 
	 */
	public EObject getSingleCompositionEditorRequiredProperty();

	/**
	 * Init the singleCompositionEditorRequiredProperty
	 * @param settings the combo setting
	 */
	public void initSingleCompositionEditorRequiredProperty(EObjectFlatComboSettings settings);

	/**
	 * Defines a new singleCompositionEditorRequiredProperty
	 * @param newValue the new singleCompositionEditorRequiredProperty to set
	 * 
	 */
	public void setSingleCompositionEditorRequiredProperty(EObject newValue);


	/**
	 * @return the singleCompositionEditorOptionalProperty
	 * 
	 */
	public EObject getSingleCompositionEditorOptionalProperty();

	/**
	 * Init the singleCompositionEditorOptionalProperty
	 * @param settings the combo setting
	 */
	public void initSingleCompositionEditorOptionalProperty(EObjectFlatComboSettings settings);

	/**
	 * Defines a new singleCompositionEditorOptionalProperty
	 * @param newValue the new singleCompositionEditorOptionalProperty to set
	 * 
	 */
	public void setSingleCompositionEditorOptionalProperty(EObject newValue);


	/**
	 * @return the singleCompositionEditorRequiredAbstractProperty
	 * 
	 */
	public EObject getSingleCompositionEditorRequiredAbstractProperty();

	/**
	 * Init the singleCompositionEditorRequiredAbstractProperty
	 * @param settings the combo setting
	 */
	public void initSingleCompositionEditorRequiredAbstractProperty(EObjectFlatComboSettings settings);

	/**
	 * Defines a new singleCompositionEditorRequiredAbstractProperty
	 * @param newValue the new singleCompositionEditorRequiredAbstractProperty to set
	 * 
	 */
	public void setSingleCompositionEditorRequiredAbstractProperty(EObject newValue);


	/**
	 * @return the singleCompositionEditorOptionalAbstractProperty
	 * 
	 */
	public EObject getSingleCompositionEditorOptionalAbstractProperty();

	/**
	 * Init the singleCompositionEditorOptionalAbstractProperty
	 * @param settings the combo setting
	 */
	public void initSingleCompositionEditorOptionalAbstractProperty(EObjectFlatComboSettings settings);

	/**
	 * Defines a new singleCompositionEditorOptionalAbstractProperty
	 * @param newValue the new singleCompositionEditorOptionalAbstractProperty to set
	 * 
	 */
	public void setSingleCompositionEditorOptionalAbstractProperty(EObject newValue);





	/**
	 * Returns the internationalized title text.
	 * 
	 * @return the internationalized title text.
	 * 
	 */
	public String getTitle();

	// Start of user code for additional methods
	
	// End of user code

}
