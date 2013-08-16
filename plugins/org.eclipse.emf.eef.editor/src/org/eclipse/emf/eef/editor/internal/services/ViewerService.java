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
package org.eclipse.emf.eef.editor.internal.services;

import java.util.Collection;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.editor.internal.services.util.ViewLockingSettings;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewerService {
	
	private EEFLockManagerProvider lockManagerProvider;
	
	/**
	 * @param lockManagerProvider
	 */
	public ViewerService(EEFLockManagerProvider lockManagerProvider) {
		this.lockManagerProvider = lockManagerProvider;
	}

	public void lockEditors(PropertiesEditingComponent editingComponent, ViewLockingSettings lockingSettings) {
		for (Object editingView : editingComponent.getViews()) {
			View viewDescriptor = editingComponent.getDescriptorForView(editingView);
			if (viewDescriptor instanceof EObjectView && ((EObjectView) viewDescriptor).getDefinition() instanceof org.eclipse.emf.eef.views.View) {
				org.eclipse.emf.eef.views.View viewDefinition = (org.eclipse.emf.eef.views.View) ((EObjectView) viewDescriptor).getDefinition();
				String viewID = viewDefinition.getQualifiedIdentifier();
				if (lockingSettings.getConfiguredViews().contains(viewID)) {
					TreeIterator<EObject> eAllContents = viewDefinition.eAllContents();
					Collection<String> lockedEditors = lockingSettings.getLockedEditors(viewID);
					if (lockedEditors != null && !lockedEditors.isEmpty()) {
						while (eAllContents.hasNext()) {
							EObject next = eAllContents.next();
							if (next instanceof ElementEditor && lockedEditors.contains((((ElementEditor)next).getQualifiedIdentifier()))) {
								lockManagerProvider.getLockManager(editingView).lockEditor(editingView, next);
							}
						}
					}
				}
			}
		}

	}	
	
	public void updateViewerBackground(FormToolkit toolkit, EEFViewer viewer) {
		//FIXME: Ugly but ... it works ...
		Control[] controlChildren = ((Composite)viewer.getControl()).getChildren();
		if (controlChildren.length == 1 && controlChildren[0] instanceof ScrolledComposite) {
			ScrolledComposite scrolledComposite = (ScrolledComposite)controlChildren[0];
			if (scrolledComposite.getChildren().length > 0 && scrolledComposite.getChildren()[0] instanceof CTabFolder) { 
				toolkit.adapt((CTabFolder) scrolledComposite.getChildren()[0]);
			}
		}
	}

}
