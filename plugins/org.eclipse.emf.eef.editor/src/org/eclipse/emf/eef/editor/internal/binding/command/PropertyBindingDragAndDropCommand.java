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
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.views.ViewElement;

/**
 * Drag and drop for {@link ViewElement} on PropertyBinding. If ViewElement does
 * not already exist, add it in PropertyBinding.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 *
 */
public class PropertyBindingDragAndDropCommand extends AbstractDragAndDropCommand {

	/**
	 * PropertyBinding
	 */
	private PropertyBinding propertyBinding;

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
	public PropertyBindingDragAndDropCommand(EditingDomain domain, PropertyBinding propertyBinding, float location, int operations, int operation, Collection<?> collection) {
		super(domain, propertyBinding.getEClassBinding().getEditingModel(), location, operations, operation, collection);
		this.propertyBinding = propertyBinding;
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
			dropCommand = new DropElementEditorCommand(getPropertiesEditingModel());
			dragCommand = IdentityCommand.INSTANCE;
		}

		boolean result = dragCommand.canExecute() && dropCommand.canExecute();
		return result;
	}

	/**
	 * Drop ElementEditor Command
	 * 
	 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
	 *
	 */
	private final class DropElementEditorCommand extends ChangeCommand {
		private DropElementEditorCommand(Notifier notifier) {
			super(notifier);
		}

		@Override
		protected void doExecute() {
			for (Object object : getElementToDrop()) {
				if (object instanceof ViewElement) {
					ViewElement eObject = (ViewElement) object;
					// add editor
					getBuilder().createEObjectEditor(propertyBinding, eObject);
					// check if view in binding

					if (!checkBindingViews(propertyBinding, eObject)) {
						getBuilder().createEObjectView(eObject.getContainingView(), propertyBinding.getEClassBinding());
					}

				}
			}

		}

		/**
		 * @param propertyBinding
		 *            PropertyBinding
		 * @param eObject
		 *            ViewElement
		 */
		public boolean checkBindingViews(PropertyBinding propertyBinding, ViewElement eObject) {
			for (View view : propertyBinding.getEClassBinding().getViews()) {
				if (view instanceof EObjectView && ((EObjectView) view).getDefinition() != null && ((EObjectView) view).getDefinition().equals(eObject.getContainingView())) {
					return true;
				}
			}
			return false;
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
				if (!(object instanceof ViewElement)) {
					return false;
				}
			}
			return true;
		}
	}
}
