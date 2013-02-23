/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.emf.eef.runtime.ui.platform.internal.services.view;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.ui.internal.services.view.ViewServiceImpl;
import org.eclipse.emf.eef.runtime.ui.platform.services.view.PlatformAwareViewService;
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
public class PlatformAwareViewServiceImpl extends ViewServiceImpl implements PlatformAwareViewService {
		
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.services.view.PlatformAwareViewService#createLabel(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite, java.lang.Object, java.lang.String)
	 */
	public Label createLabel(FormToolkit toolkit, Composite parent, Object editor, String alternate) {
		String text = getDescription(editor, alternate);
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
		EStructuralFeature associatedFeature = feature(editor);
		if (associatedFeature != null && associatedFeature.isRequired()) {
			label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
		}
		return label;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.services.view.PlatformAwareViewService#createHelpButton(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite, java.lang.Object)
	 */
	public Control createHelpButton(FormToolkit toolkit, Composite parent, Object editor ) {
		//To manage in future
//		String helpID = null;
		String alternate = getHelpContent(editor);
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
	 * @see org.eclipse.emf.eef.runtime.ui.internal.services.view.ViewServiceImpl#executeSyncUIRunnable(java.lang.Runnable)
	 */
	public void executeSyncUIRunnable(Runnable job) {
		if (null == Display.getCurrent()) {
			PlatformUI.getWorkbench().getDisplay().syncExec(job);
		} else {
			Display.getCurrent().syncExec(job);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.internal.services.view.ViewServiceImpl#executeAsyncUIRunnable(java.lang.Runnable)
	 */
	public void executeAsyncUIRunnable(Runnable job) {
		if (null == Display.getCurrent()) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(job);
		} else {
			Display.getCurrent().asyncExec(job);
		}
	}


}

