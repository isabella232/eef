/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LiveEObjectEditingPolicy extends DomainEObjectEditingPolicy {

	/**
	 * @param editingContext
	 * @param editingEvent
	 */
	public LiveEObjectEditingPolicy(DomainAwarePropertiesEditingContext editingContext, PropertiesEditingEvent editingEvent) {
		super(editingContext, editingEvent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.DomainEObjectEditingPolicy#processCommand(org.eclipse.emf.common.command.Command)
	 */
	@Override
	protected void processCommand(Command command) {
		getEditingContext().getEditingDomain().getCommandStack().execute(command);
	}

}
