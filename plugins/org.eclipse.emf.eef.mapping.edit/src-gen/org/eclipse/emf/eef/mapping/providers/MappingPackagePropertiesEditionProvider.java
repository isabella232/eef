/**
 *  Copyright (c) 2008-2009 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 *
 * $Id: MappingPackagePropertiesEditionProvider.java,v 1.1 2009/04/30 17:14:43 glefur Exp $
 */
package org.eclipse.emf.eef.mapping.providers;

import org.eclipse.emf.eef.runtime.impl.providers.ComposedPropertiesEditionProvider;
 	
/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 */
public class MappingPackagePropertiesEditionProvider extends ComposedPropertiesEditionProvider {

	/**
	 * Default Constructor
	 */
	public MappingPackagePropertiesEditionProvider() {
		super();
		append(createDatabindingPropertiesEditionProvider());
		append(createEMFElementBindingPropertiesEditionProvider());
		append(createEMFPropertyBindingPropertiesEditionProvider());
		append(createStandardElementBindingPropertiesEditionProvider());
		append(createStandardPropertyBindingPropertiesEditionProvider());
		append(createCategoryPropertiesEditionProvider());
		append(createElementBindingReferencePropertiesEditionProvider());
		append(createEMFMultiPropertiesBindingPropertiesEditionProvider());
	}
	
	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * Databinding instances.
	 */
	protected DatabindingPropertiesEditionProvider databindingPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a Databinding
	 */
	public DatabindingPropertiesEditionProvider createDatabindingPropertiesEditionProvider() {
		if (databindingPropertiesEditionProvider == null)
			databindingPropertiesEditionProvider = new DatabindingPropertiesEditionProvider();
		return databindingPropertiesEditionProvider;
	}
	
	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * EMFElementBinding instances.
	 */
	protected EMFElementBindingPropertiesEditionProvider eMFElementBindingPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a EMFElementBinding
	 */
	public EMFElementBindingPropertiesEditionProvider createEMFElementBindingPropertiesEditionProvider() {
		if (eMFElementBindingPropertiesEditionProvider == null)
			eMFElementBindingPropertiesEditionProvider = new EMFElementBindingPropertiesEditionProvider();
		return eMFElementBindingPropertiesEditionProvider;
	}
	
	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * EMFPropertyBinding instances.
	 */
	protected EMFPropertyBindingPropertiesEditionProvider eMFPropertyBindingPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a EMFPropertyBinding
	 */
	public EMFPropertyBindingPropertiesEditionProvider createEMFPropertyBindingPropertiesEditionProvider() {
		if (eMFPropertyBindingPropertiesEditionProvider == null)
			eMFPropertyBindingPropertiesEditionProvider = new EMFPropertyBindingPropertiesEditionProvider();
		return eMFPropertyBindingPropertiesEditionProvider;
	}
	
	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * StandardElementBinding instances.
	 */
	protected StandardElementBindingPropertiesEditionProvider standardElementBindingPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a StandardElementBinding
	 */
	public StandardElementBindingPropertiesEditionProvider createStandardElementBindingPropertiesEditionProvider() {
		if (standardElementBindingPropertiesEditionProvider == null)
			standardElementBindingPropertiesEditionProvider = new StandardElementBindingPropertiesEditionProvider();
		return standardElementBindingPropertiesEditionProvider;
	}
	
	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * StandardPropertyBinding instances.
	 */
	protected StandardPropertyBindingPropertiesEditionProvider standardPropertyBindingPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a StandardPropertyBinding
	 */
	public StandardPropertyBindingPropertiesEditionProvider createStandardPropertyBindingPropertiesEditionProvider() {
		if (standardPropertyBindingPropertiesEditionProvider == null)
			standardPropertyBindingPropertiesEditionProvider = new StandardPropertyBindingPropertiesEditionProvider();
		return standardPropertyBindingPropertiesEditionProvider;
	}
	
	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * Category instances.
	 */
	protected CategoryPropertiesEditionProvider categoryPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a Category
	 */
	public CategoryPropertiesEditionProvider createCategoryPropertiesEditionProvider() {
		if (categoryPropertiesEditionProvider == null)
			categoryPropertiesEditionProvider = new CategoryPropertiesEditionProvider();
		return categoryPropertiesEditionProvider;
	}
	
	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * ElementBindingReference instances.
	 */
	protected ElementBindingReferencePropertiesEditionProvider elementBindingReferencePropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a ElementBindingReference
	 */
	public ElementBindingReferencePropertiesEditionProvider createElementBindingReferencePropertiesEditionProvider() {
		if (elementBindingReferencePropertiesEditionProvider == null)
			elementBindingReferencePropertiesEditionProvider = new ElementBindingReferencePropertiesEditionProvider();
		return elementBindingReferencePropertiesEditionProvider;
	}
	
	/**
	 * This keeps track of the one PropertiesEditionProvider used for all
	 * EMFMultiPropertiesBinding instances.
	 */
	protected EMFMultiPropertiesBindingPropertiesEditionProvider eMFMultiPropertiesBindingPropertiesEditionProvider;

	/**
	 * This creates an PropertiesEditionProvider for a EMFMultiPropertiesBinding
	 */
	public EMFMultiPropertiesBindingPropertiesEditionProvider createEMFMultiPropertiesBindingPropertiesEditionProvider() {
		if (eMFMultiPropertiesBindingPropertiesEditionProvider == null)
			eMFMultiPropertiesBindingPropertiesEditionProvider = new EMFMultiPropertiesBindingPropertiesEditionProvider();
		return eMFMultiPropertiesBindingPropertiesEditionProvider;
	}
	

}	
