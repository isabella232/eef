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
package org.eclipse.emf.eef.runtime.ui.commands;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public abstract class AbstractBatchEditingCommand extends AbstractCommand {

	protected SemanticPropertiesEditingContext editingContext;

	/**
	 * @param editionContext
	 */
	public AbstractBatchEditingCommand(SemanticPropertiesEditingContext editionContext) {
		this.editingContext = editionContext;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
	 */
	@Override
	protected boolean prepare() {
		boolean result = prepareBatchEditing();
		return result;
	}

	/**
	 * Edit the model with a {@link PropertiesEditingContext} in Batch mode.
	 * 
	 * @return <code>true</code> if the editing successful.
	 */
	protected abstract boolean prepareBatchEditing();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	public void execute() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
	 */
	@Override
	public void undo() {
		editingContext.undoEditing();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	public void redo() {
		editingContext.undoEditing();
	}

}
