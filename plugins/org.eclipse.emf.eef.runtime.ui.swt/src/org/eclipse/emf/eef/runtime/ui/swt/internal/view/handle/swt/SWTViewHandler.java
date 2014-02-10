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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.swt;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.ui.internal.view.handle.reflect.ReflectViewHandler;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class SWTViewHandler extends ReflectViewHandler<Composite> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.internal.view.handle.reflect.ReflectViewHandler#serviceFor(org.eclipse.emf.eef.runtime.editingModel.View)
	 */
	public boolean serviceFor(View view) {
		return super.serviceFor(view) && isCompositeClass((Class<?>) ((JavaView) view).getDefinition());
	}

	/**
	 * @param view
	 * @return
	 */
	private boolean isCompositeClass(Class<?> view) {
		if (view == Composite.class) {
			return true;
		} else if (view.getSuperclass() != null) {
			return isCompositeClass(view.getSuperclass());
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.internal.view.handle.reflect.ReflectViewHandler#dispose(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
	 *      java.lang.Object)
	 */
	@Override
	public void dispose(PropertiesEditingComponent editingComponent, Object view) {
		if (view instanceof Composite) {
			((Composite) view).dispose();
		}
		super.dispose(editingComponent, view);
	}

}
