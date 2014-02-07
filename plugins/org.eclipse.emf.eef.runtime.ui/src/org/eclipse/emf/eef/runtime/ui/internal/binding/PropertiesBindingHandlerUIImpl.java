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
package org.eclipse.emf.eef.runtime.ui.internal.binding;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.emf.eef.views.ViewsPackage;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class PropertiesBindingHandlerUIImpl extends PropertiesBindingHandlerImpl {

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#notifySettingsChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
	 *      org.eclipse.emf.common.notify.Notification)
	 */
	public void notifySettingsChanged(PropertiesEditingComponent editingComponent, Notification msg) {
		if (ViewsPackage.eINSTANCE.getElementEditor_ReadOnly().equals(msg.getFeature())) {
			if (msg.getNotifier() instanceof ElementEditor) {
				ElementEditor elementEditor = (ElementEditor) msg.getNotifier();
				for (Object view : editingComponent.getViews()) {
					View descriptorForView = editingComponent.getDescriptorForView(view);
					if (isViewElementIn(descriptorForView, elementEditor)) {
						if (elementEditor.isReadOnly()) {
							getLockManager(view).lockEditor(view, elementEditor);
						} else if (!elementEditor.isReadOnly()) {
							getLockManager(view).clearEditorLock(view, elementEditor);
						}
					}

				}
			}
		}
	}

	public boolean isViewElementIn(View view, ViewElement editor) {
		EObject container = editor.eContainer();
		while (container != null && !(container instanceof View)) {
			container = container.eContainer();
		}
		return container != null && container.equals(view);
	}
}
