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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;

/**
 * Drag and drop for EStructuralFeature on EClasssBinding. If EFeatureBinding
 * does not already exist, create it in EClasssBinding.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 *
 */
public class EClassBindingDragAndDropCommand extends AbstractDragAndDropCommand {

	/**
	 * EClassBinding
	 */
	private EClassBinding eClassBinding;

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
	public EClassBindingDragAndDropCommand(EditingDomain domain, EClassBinding eClassBinding, float location, int operations, int operation, Collection<?> collection) {
		super(domain, eClassBinding.getEditingModel(), location, operations, operation, collection);
		this.eClassBinding = eClassBinding;
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
			dropCommand = new DropEStructuralFeatureCommand(getPropertiesEditingModel());
			dragCommand = IdentityCommand.INSTANCE;
		}

		boolean result = dragCommand.canExecute() && dropCommand.canExecute();
		return result;
	}

	/**
	 * Drop EStructuralFeature Command
	 * 
	 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
	 *
	 */
	private final class DropEStructuralFeatureCommand extends ChangeCommand {
		private DropEStructuralFeatureCommand(Notifier notifier) {
			super(notifier);
		}

		@Override
		protected void doExecute() {
			for (Object object : getElementToDrop()) {
				if (object instanceof EStructuralFeature) {
					EStructuralFeature eObject = (EStructuralFeature) object;
					if (!getBuilder().existEStructuralFeatureBinding(eClassBinding, eObject)) {
						// create View
						org.eclipse.emf.eef.views.View view = getBuilder().getFirstViewForEClassBinding(eClassBinding);

						// bind eobject structural features
						if (view != null) {
							getBuilder().bindEStructuralFeature((EClass) eObject.eContainer(), eClassBinding, view, getEditingModelEnvironment(), eObject);
						} else {
							// TODO log erreor
						}
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
				if (object instanceof EStructuralFeature) {
					EStructuralFeature eObject = (EStructuralFeature) object;
					if (!getBuilder().existEStructuralFeatureBinding(eClassBinding, eObject)) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
