/**
 * 
 */
package org.eclipse.emf.eef.runtime.context.impl;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.SemanticDomainEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DomainPropertiesEditingContext extends EObjectPropertiesEditingContext {

	private EditingDomain editingDomain;

	/**
	 * @param eObject
	 */
	public DomainPropertiesEditingContext(EditingDomain editingDomain, EObject eObject) {
		super(eObject);
		this.editingDomain = editingDomain;
	}

	/**
	 * @return the editingDomain
	 */
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext editingContext) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			SemanticPropertiesEditingContext semanticEditingContext = (SemanticPropertiesEditingContext) editingContext;
			return new SemanticDomainEditingPolicy(editingDomain, semanticEditingContext.getEditingComponent(), semanticEditingContext.getEditingEvent());
		}
		return super.getEditingPolicy(editingContext);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext#undoEditing()
	 */
	public void undoEditing() {
		editingDomain.getCommandStack().execute(new UndoEditingCommand("Undo PropertiesEditing"));
	}

	private final class UndoEditingCommand extends AbstractCommand {
		private ChangeDescription endRecording;
		
		public UndoEditingCommand(String label) {
			super(label);
		}
		
		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
		 */
		protected boolean prepare() {
			endRecording = getChangeRecorder().endRecording();
			return endRecording != null;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.common.command.Command#execute()
		 */
		public void execute() {
			endRecording.applyAndReverse();
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
		 */
		public void undo() {
			endRecording.applyAndReverse();
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.common.command.Command#redo()
		 */
		public void redo() {
			endRecording.applyAndReverse();
		}

	}

}
