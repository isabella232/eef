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
package org.eclipse.emf.eef.view.listener;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.editor.EEFReflectiveEditor;
import org.eclipse.emf.eef.view.EEFReflectiveView;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class EEFViewPartListener implements IPartListener {

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {
		inputChanged(part);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partBroughtToTop(IWorkbenchPart part) {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partClosed(IWorkbenchPart part) {
		if (part instanceof EEFReflectiveEditor && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(EEFReflectiveView.ID);
			if (view instanceof EEFReflectiveView) {
				((EEFReflectiveView) view).setInput(null);
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partDeactivated(IWorkbenchPart part) {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partOpened(IWorkbenchPart part) {
		inputChanged(part);
	}

	/**
	 * @param part
	 */
	public void inputChanged(IWorkbenchPart part) {
		if (part instanceof EEFReflectiveView && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			EEFReflectiveView view = (EEFReflectiveView) part;
			IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (activeEditor instanceof EEFReflectiveEditor) {
				EditingDomain domain = ((EEFReflectiveEditor) activeEditor).getEditingDomainForOtherModel();
				if (domain != null && !domain.getResourceSet().getResources().isEmpty() && !domain.equals(view.getEditingDomain())) {
					view.setInput(domain);
				}
			}
		} else if (part instanceof EEFReflectiveEditor && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			EEFReflectiveEditor editor = (EEFReflectiveEditor) part;
			IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(EEFReflectiveView.ID);
			if (view instanceof EEFReflectiveView) {
				EditingDomain domain = editor.getEditingDomainForOtherModel();
				if (domain != null && !domain.getResourceSet().getResources().isEmpty() && !domain.equals(((EEFReflectiveView) view).getEditingDomain())) {
					((EEFReflectiveView) view).setInput(domain);
				}
			}
		}
	}
}
