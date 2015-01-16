/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.editor.internal.binding.command;

import java.util.Collection;

import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;

/**
 * Drag and drop for EClass on PropertiesEditingModel. If EClassBinding does not
 * already exist, create it in PropertiesEditingModel.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 *
 */
public class EditingModelDragAndDropCommand extends AbstractDragAndDropCommand {

	/**
	 * @param domain
	 *            EditingDomain
	 * @param propertiesEditingModel
	 *            PropertiesEditingModel
	 * @param location
	 * @param operations
	 * @param operation
	 * @param collection
	 *            eClassToDrop
	 */
	public EditingModelDragAndDropCommand(EditingDomain domain, PropertiesEditingModel propertiesEditingModel, float location, int operations, int operation, Collection<?> collection) {
		super(domain, propertiesEditingModel, location, operations, operation, collection);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.edit.command.DragAndDropCommand#prepareDropMoveOn()
	 */
	protected boolean prepareDropMoveOn() {
		if (isCrossDomain()) {
			dragCommand = IdentityCommand.INSTANCE;
			dropCommand = UnexecutableCommand.INSTANCE;
		} else {
			dropCommand = new DropEClassCommand(getPropertiesEditingModel());
			dragCommand = IdentityCommand.INSTANCE;
		}

		boolean result = dragCommand.canExecute() && dropCommand.canExecute();
		return result;
	}

	/**
	 * Drop EClass Command
	 * 
	 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
	 *
	 */
	private final class DropEClassCommand extends ChangeCommand {
		private DropEClassCommand(Notifier notifier) {
			super(notifier);
		}

		@Override
		protected void doExecute() {
			for (Object object : getElementToDrop()) {
				if (object instanceof EClass) {
					EClass eObject = (EClass) object;
					if (!getBuilder().existEClassBinding(eObject)) {
						// create View
						org.eclipse.emf.eef.views.View createdView = getBuilder().createViewForEClassBinding(eObject);

						// create EClassBinding and link the createdView
						EClassBinding eClassBinding = getBuilder().createEClassBinding(eObject, createdView);

						// bind eobject structural features
						getBuilder().bindEStructuralFeature(eObject, eClassBinding, createdView, getEditingModelEnvironment());
					}
				}
			}

		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
		 */
		@Override
		public boolean canExecute() {
			boolean canExecute = super.canExecute();
			if (!canExecute) {
				return canExecute;
			}
			for (Object object : getElementToDrop()) {
				if (object instanceof EClass) {
					EClass eObject = (EClass) object;
					if (!getBuilder().existEClassBinding(eObject)) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
