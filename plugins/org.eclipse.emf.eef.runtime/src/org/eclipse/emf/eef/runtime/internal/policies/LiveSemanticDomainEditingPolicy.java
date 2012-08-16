/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LiveSemanticDomainEditingPolicy extends SemanticDomainEditingPolicy {

	/**
	 * @param editingDomain
	 * @param editingComponent
	 * @param editingEvent
	 */
	public LiveSemanticDomainEditingPolicy(EditingDomain editingDomain, PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		super(editingDomain, editingComponent, editingEvent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticDomainEditingPolicy#processCommand(org.eclipse.emf.common.command.Command)
	 */
	@Override
	protected void processCommand(Command command) {
		editingDomain.getCommandStack().execute(command);
	}

}
