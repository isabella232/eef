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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit;

import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.checkbox.CheckboxPlatformAwarePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.combo.ComboPlatformAwarePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.group.GroupPlatformAwareContainerFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.hbox.HBoxPlatformAwareContainerFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.text.TextPlatformAwarePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.textarea.TextareaPlatformAwarePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.SWTToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTPlatformAwareToolkit extends SWTToolkit {
	
	/**
	 * TODO: Ugly pattern ... need a redesign.
	 * If we don't clear the editorProviders instanciated by the default constructor, the PlatformAware editorProviders
	 * aren't use since the editorProvider selection algorithm (method 
	 *  org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.EEFToolkitImpl.getPropertyEditor(PropertyEditorContext)) 
	 *  don't have an ordering system between the different editorProviders.
	 */
	public SWTPlatformAwareToolkit() {
		clearEditorProviders();
		addPropertyEditorFactory(new TextPlatformAwarePropertyEditorFactory(this))
		.addPropertyEditorFactory(new CheckboxPlatformAwarePropertyEditorFactory(this))
		.addPropertyEditorFactory(new GroupPlatformAwareContainerFactory())
		.addPropertyEditorFactory(new HBoxPlatformAwareContainerFactory())
		.addPropertyEditorFactory(new TextareaPlatformAwarePropertyEditorFactory(this))
		.addPropertyEditorFactory(new ComboPlatformAwarePropertyEditorFactory(this));
	}

}
