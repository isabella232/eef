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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.handle.editingview;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.impl.FormImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.impl.SWTImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.views.View;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PlatformAwarePropertiesEditingViewHandler extends PropertiesEditingViewHandler {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.editingview.PropertiesEditingViewHandler#serviceFor(org.eclipse.emf.eef.runtime.editingModel.View)
	 */
	@Override
	public boolean serviceFor(org.eclipse.emf.eef.runtime.editingModel.View view) {
		return super.serviceFor(view);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.editingview.PropertiesEditingViewHandler#createView(java.lang.Object, java.lang.Object[])
	 */
	@Override
	public PropertiesEditingView<Composite> createView(PropertiesEditingComponent editingComponent, org.eclipse.emf.eef.runtime.editingModel.View viewDescriptor, Object... args) throws ViewConstructionException {
		PropertiesEditingView<Composite> view;
		if (viewDescriptor instanceof EObjectView && ((EObjectView)viewDescriptor).getDefinition() instanceof View 
				&& args.length > 0 && args[0] instanceof Composite) {
			View eObjectViewDescriptor = (View) ((EObjectView)viewDescriptor).getDefinition();
			FormToolkit toolkit = editingComponent.getEditingContext().getOptions().getOption(EEFSWTConstants.FORM_TOOLKIT);
			if (toolkit != null) {
				view = new FormImplPropertiesEditingView(editingComponent, eObjectViewDescriptor);
				((FormImplPropertiesEditingView) view).setViewServiceProvider(getViewServiceProvider());
				((FormImplPropertiesEditingView) view).setToolkitPropertyEditorFactory(getEEFToolkitProvider());
				((FormImplPropertiesEditingView) view).createContents(toolkit, (Composite)args[0]);
			} else {
				view = new SWTImplPropertiesEditingView(editingComponent, eObjectViewDescriptor);					
				((SWTImplPropertiesEditingView) view).setViewServiceProvider(getViewServiceProvider());
				((SWTImplPropertiesEditingView) view).setToolkitPropertyEditorFactory(getEEFToolkitProvider());
				((SWTImplPropertiesEditingView) view).createContents((Composite)args[0]);
			}
			editingComponent.setViewForDescriptor(viewDescriptor, view);
			return view;
		}
		return super.createView(editingComponent, viewDescriptor, args);
	}

	

}
