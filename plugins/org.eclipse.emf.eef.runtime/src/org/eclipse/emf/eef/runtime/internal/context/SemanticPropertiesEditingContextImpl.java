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
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.eef.runtime.context.EditingRecorder;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.osgi.service.component.ComponentContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class SemanticPropertiesEditingContextImpl extends DelegatingPropertiesEditingContext implements SemanticPropertiesEditingContext {

	protected PropertiesEditingEvent editingEvent;
	protected ContextOptions options;

	protected EditingRecorder editingRecorder;
	private PropertiesEditingContext parentContext;

	/**
	 * Configure the current component instance with the given properties.
	 * 
	 * @param context
	 *            {@link ComponentContext} to use to configure the current
	 *            instance.
	 */
	SemanticPropertiesEditingContextImpl(PropertiesEditingContext parentContext, PropertiesEditingEvent editingEvent) {
		super(parentContext);
		this.parentContext = parentContext;
		this.editingEvent = editingEvent;
		this.options = new ContextOptions(parentContext.getOptions());
		this.editingRecorder = new EditingRecorderImpl();
		editingRecorder.initRecording(parentContext.getEditingComponent().getEObject());
	}

	/**
	 * @return the parent {@link PropertiesEditingContext} of the current
	 *         context.
	 */
	public PropertiesEditingContext getParentContext() {
		return getDelegatingContext();
	}

	/**
	 * @return the {@link PropertiesEditingEvent} which have generated this
	 *         context.
	 */
	public PropertiesEditingEvent getEditingEvent() {
		return editingEvent;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.internal.context.DelegatingPropertiesEditingContext#getOptions()
	 */
	@Override
	public ContextOptions getOptions() {
		return options;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getChangeRecorder()
	 */
	public ChangeRecorder getChangeRecorder() {
		return editingRecorder.getChangeRecorder();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#startEditing()
	 */
	public void startEditing() {
		editingRecorder.initRecording(parentContext.getEditingComponent().getEObject());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#stopEditing()
	 */
	public void stopEditing() {
		editingRecorder.stopEditing();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#cancelEditing()
	 */
	public void cancelEditing() {
		editingRecorder.cancelEditing();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#undoEditing()
	 */
	public void undoEditing() {
		editingRecorder.undoEditing();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#dispose()
	 */
	public void dispose() {
		editingRecorder.dispose();
	}
}
