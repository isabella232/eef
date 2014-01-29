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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.util;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.ui.swt.e3.util.PlatformAwareViewService;
import org.eclipse.emf.eef.runtime.ui.swt.internal.util.SWTViewServiceImpl;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class PlatformAwareViewServiceImpl extends SWTViewServiceImpl implements PlatformAwareViewService {
		
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e3.util.PlatformAwareViewService#createLabel(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite, java.lang.Object, java.lang.String)
	 */
	public Label createLabel(PropertiesEditingComponent editingComponent, FormToolkit toolkit, Composite parent, Object editor, String alternate) {
		String text = getDescription(editingComponent, editor, alternate);
		if (!text.endsWith(": ") && !text.endsWith(":")) {
			text += ": ";
		}
		Label label;
		if (toolkit != null) {
			label = toolkit.createLabel(parent, text);
		} else {
			label = new Label(parent, SWT.NONE);
			label.setText(text);
		}
		if (!eefEditingServiceProvider.getEditingService(editingComponent.getBinding()).isReflectiveBinding(editingComponent.getBinding())) {
			PropertyBinding propertyBinding = editingComponent.getBinding().propertyBinding(editor, editingComponent.getEditingContext().getOptions().autowire());
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature associatedFeature = ((EStructuralFeatureBinding) propertyBinding).getFeature();
				if (associatedFeature != null && associatedFeature.isRequired()) {
					label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
				}
			}
		}
		return label;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e3.util.PlatformAwareViewService#createHelpButton(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite, java.lang.Object)
	 */
	public Control createHelpButton(PropertiesEditingComponent editingComponent, FormToolkit toolkit, Composite parent, Object editor ) {
		//To manage in future
//		String helpID = null;
		String alternate = getHelpContent(editingComponent, editor);
		Image image = JFaceResources.getImage(DLG_IMG_HELP);
//		if (helpID != null && !EMPTY_STRING.equals(helpID)) { //$NON-NLS-1$
//			ToolBar result = new ToolBar(parent, SWT.FLAT | SWT.NO_FOCUS);
//			((GridLayout)parent.getLayout()).numColumns++;
//			result.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
//			ToolItem item = new ToolItem(result, SWT.NONE);
//			item.setImage(image);
//			if (alternate != null && !EMPTY_STRING.equals(alternate)) //$NON-NLS-1$
//				item.setToolTipText(alternate);
//			return result;
//		} else {
			Label result = null; 
			if (toolkit != null) {
				result = toolkit.createLabel(parent, EMPTY_STRING); //$NON-NLS-1$
			} else {
				result = new Label(parent, SWT.NONE);
			}
			if (alternate != null && !EMPTY_STRING.equals(alternate)) { //$NON-NLS-1$
				result.setImage(image);
				result.setToolTipText(alternate);
			}
			return result;
//		}
	}
	
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.util.impl.ViewServiceImpl#executeSyncUIRunnable(org.eclipse.swt.widgets.Display, java.lang.Runnable)
	 */
	public void executeSyncUIRunnable(Display display, Runnable job) {
		if (display != null) {
			display.syncExec(job);
		} else if (null == Display.getCurrent()) {
			PlatformUI.getWorkbench().getDisplay().syncExec(job);
		} else {
			Display.getCurrent().syncExec(job);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.util.impl.ViewServiceImpl#executeAsyncUIRunnable(java.lang.Runnable)
	 */
	public void executeAsyncUIRunnable(Display display, Runnable job) {
		if (display != null) {
			display.asyncExec(job);
		} else if (null == Display.getCurrent()) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(job);
		} else {
			Display.getCurrent().asyncExec(job);
		}
	}


}

