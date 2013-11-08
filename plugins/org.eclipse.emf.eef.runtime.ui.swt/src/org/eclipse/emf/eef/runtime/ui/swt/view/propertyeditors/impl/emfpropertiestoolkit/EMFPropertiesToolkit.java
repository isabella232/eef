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
package org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit;

import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor.EComboPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.econtainmenteditor.EContainmentPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.edatepicker.EDatePickerPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor.EReferencePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.singlecontainmenteditor.SingleContainmentPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.ViewerFilterBuilderProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.EEFToolkitImpl;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFPropertiesToolkit extends EEFToolkitImpl<Composite> {

	/**
	 * EMFProperties toolkit name.
	 */
	public static final String EMF_PROPERTIES_TOOLKIT_NAME = "EMFProperties";
	
	private static final Toolkit toolkit = ToolkitsFactory.eINSTANCE.createToolkit();
	
	static {
		toolkit.setName(EMF_PROPERTIES_TOOLKIT_NAME);
	}
	
	private EditUIProvidersFactory editUIProvidersFactory;
	private ViewerFilterBuilderProvider viewerFilterBuilderProvider; 
	private ImageManager imageManager;

	
	/**
	 * @return the editUIProvidersFactory
	 */
	public final EditUIProvidersFactory getEditUIProvidersFactory() {
		return editUIProvidersFactory;
	}

	/**
	 * @param editUIProvidersFactory the editUIProvidersFactory to set
	 */
	public final void setEditUIProvidersFactory(EditUIProvidersFactory editUIProvidersFactory) {
		this.editUIProvidersFactory = editUIProvidersFactory;
	}

	/**
	 * @return the viewerFilterBuilderProvider
	 */
	public ViewerFilterBuilderProvider getViewerFilterBuilderProvider() {
		return viewerFilterBuilderProvider;
	}

	/**
	 * @param viewerFilterBuilderProvider the viewerFilterBuilderProvider to set
	 */
	public void setViewerFilterBuilderProvider(ViewerFilterBuilderProvider viewerFilterBuilderProvider) {
		this.viewerFilterBuilderProvider = viewerFilterBuilderProvider;
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

	/**
	 * 
	 */
	public EMFPropertiesToolkit() {
		addPropertyEditorFactory(new EReferencePropertyEditorFactory(this))
			.addPropertyEditorFactory(new EComboPropertyEditorFactory(this))
			.addPropertyEditorFactory(new EContainmentPropertyEditorFactory(this))
			.addPropertyEditorFactory(new EDatePickerPropertyEditorFactory(this))
			.addPropertyEditorFactory(new SingleContainmentPropertyEditorFactory(this));
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorFactory#getModel()
	 */
	public Toolkit getModel() {
		return toolkit;
	}

}
