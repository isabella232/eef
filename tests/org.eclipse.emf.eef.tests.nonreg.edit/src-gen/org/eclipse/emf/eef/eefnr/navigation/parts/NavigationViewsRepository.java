/*******************************************************************************
 * Copyright (c) 2009 - 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.eefnr.navigation.parts;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class NavigationViewsRepository {

	public static final int SWT_KIND = 0;

	public static final int FORM_KIND = 1;


	/**
	 * DeferedFlatReferencesTableSample view descriptor
	 * 
	 */
	public static class DeferedFlatReferencesTableSample {
		public static class Properties {
	
			
			public static String name = "navigation::DeferedFlatReferencesTableSample::properties::name";
			
			
			public static String flatReferencesTableSampleEditor = "navigation::DeferedFlatReferencesTableSample::properties::flatReferencesTableSampleEditor";
			
		
		}
	
	}

	/**
	 * DeferedReferencesTableSample view descriptor
	 * 
	 */
	public static class DeferedReferencesTableSample {
		public static class Properties {
	
			
			public static String name = "navigation::DeferedReferencesTableSample::properties::name";
			
			
			public static String referencesTableSampleEditor = "navigation::DeferedReferencesTableSample::properties::referencesTableSampleEditor";
			
		
		}
	
	}

}
