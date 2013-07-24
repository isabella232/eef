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
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.emf.eef.views.ElementEditor;

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
	
}
