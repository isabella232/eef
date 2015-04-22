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
package org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit;

import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.checkbox.CheckboxPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.combo.ComboPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.group.GroupContainerFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.hbox.HBoxContainerFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.text.TextPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.textarea.TextareaPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.EEFToolkitImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class SWTToolkit extends EEFToolkitImpl<Composite> {

	private EEFEditingServiceProvider eefEditingServiceProvider;

	/**
	 * @return the eefEditingServiceProvider
	 */
	public EEFEditingServiceProvider getEEFEditingServiceProvider() {
		return eefEditingServiceProvider;
	}

	/**
	 * @param eefEditingServiceProvider
	 *            the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	private EditUIProvidersFactory editUIProvidersFactory;

	/**
	 * @return the editUIProvidersFactory
	 */
	public final EditUIProvidersFactory getEditUIProvidersFactory() {
		return editUIProvidersFactory;
	}

	/**
	 * @param editUIProvidersFactory
	 *            the editUIProvidersFactory to set
	 */
	public final void setEditUIProvidersFactory(EditUIProvidersFactory editUIProvidersFactory) {
		this.editUIProvidersFactory = editUIProvidersFactory;
	}

	/**
	 * 
	 */
	public SWTToolkit() {
		addPropertyEditorFactory(new TextPropertyEditorFactory(this))
		.addPropertyEditorFactory(new CheckboxPropertyEditorFactory(this))
		.addPropertyEditorFactory(new GroupContainerFactory())
		.addPropertyEditorFactory(new HBoxContainerFactory())
		.addPropertyEditorFactory(new TextareaPropertyEditorFactory(this)).addPropertyEditorFactory(new ComboPropertyEditorFactory(this));
	}

}
