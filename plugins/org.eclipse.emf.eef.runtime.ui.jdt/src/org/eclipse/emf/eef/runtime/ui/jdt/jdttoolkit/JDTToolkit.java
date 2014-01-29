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
package org.eclipse.emf.eef.runtime.ui.jdt.jdttoolkit;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.emf.eef.runtime.ui.jdt.internal.jdttoolkit.propertyeditors.JavaClassChooserPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.EEFToolkitImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class JDTToolkit extends EEFToolkitImpl<Composite> {

	/**
	 * EEF JDT Toolkit name.
	 */
	public static final String JDT_TOOLKIT_NAME = "jdt";
	
	private static final Toolkit toolkit = ToolkitsFactory.eINSTANCE.createToolkit();
	
	static {
		toolkit.setName(JDT_TOOLKIT_NAME);
	}

	private EEFEditingServiceProvider eefEditingServiceProvider;
	private EditUIProvidersFactory editUIProvidersFactory;
	private ImageManager imageManager;
	
	
	/**
	 * 
	 */
	public JDTToolkit() {
		addPropertyEditorFactory(new JavaClassChooserPropertyEditorFactory(this));
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorFactory#getModel()
	 */
	public Toolkit getModel() {
		return toolkit;
	}

	/**
	 * @return the eefEditingServiceProvider
	 */
	public EEFEditingServiceProvider getEEFEditingServiceProvider() {
		return eefEditingServiceProvider;
	}

	/**
	 * @param eefEditingServiceProvider the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * @return the editUIProvidersFactory
	 */
	public EditUIProvidersFactory getEditUIProvidersFactory() {
		return editUIProvidersFactory;
	}

	/**
	 * @param editUIProvidersFactory the editUIProvidersFactory to set
	 */
	public void setEditUIProvidersFactory(EditUIProvidersFactory editUIProvidersFactory) {
		this.editUIProvidersFactory = editUIProvidersFactory;
	}

	/**
	 * @return the imageManager
	 */
	public ImageManager getImageManager() {
		return imageManager;
	}

	/**
	 * @param imageManager the imageManager to set
	 */
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

}
