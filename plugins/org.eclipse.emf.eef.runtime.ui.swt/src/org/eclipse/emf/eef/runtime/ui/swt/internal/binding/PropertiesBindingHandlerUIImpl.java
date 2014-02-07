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
package org.eclipse.emf.eef.runtime.ui.swt.internal.binding;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.emf.eef.views.ViewsPackage;
import org.eclipse.swt.widgets.Composite;

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
				final ElementEditor elementEditor = (ElementEditor) msg.getNotifier();
				for (final Object view : editingComponent.getViews()) {
					View descriptorForView = editingComponent.getDescriptorForView(view);
					if (descriptorForView instanceof EObjectView && ((EObjectView) descriptorForView).getDefinition() instanceof org.eclipse.emf.eef.views.View) {
						if (isViewElementIn(((EObjectView) descriptorForView).getDefinition(), elementEditor)) {

							((SWTViewService) ((PropertiesEditingView<?>) view).getViewService()).executeSyncUIRunnable(((Composite) ((PropertiesEditingView<Composite>) view).getContents()).getDisplay(), new Runnable() {
								public void run() {
									if (elementEditor.isReadOnly()) {
										getLockManager(view).lockEditor(view, elementEditor);
									} else if (!elementEditor.isReadOnly()) {
										getLockManager(view).clearEditorLock(view, elementEditor);
									}
								}
							});

						}
					}

				}
			}
		}
	}

	public boolean isViewElementIn(EObject view, ViewElement editor) {
		EObject container = editor.eContainer();
		while (container != null && !(container instanceof org.eclipse.emf.eef.views.View)) {
			container = container.eContainer();
		}
		return container != null && container.equals(view);
	}
}
