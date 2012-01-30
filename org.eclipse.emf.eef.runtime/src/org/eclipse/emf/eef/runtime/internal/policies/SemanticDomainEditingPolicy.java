/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class SemanticDomainEditingPolicy extends SemanticEditingPolicy {

	private EditingDomain editingDomain;

	/**
	 * @param editingComponent
	 * @param editingEvent
	 */
	public SemanticDomainEditingPolicy(EditingDomain editingDomain, PropertiesEditingComponent editingComponent,	PropertiesEditingEvent editingEvent) {
		super(editingComponent, editingEvent);
		this.editingDomain = editingDomain;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticEditingPolicy#performSet(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	public void performSet(EObject eObject, EStructuralFeature feature, Object value) {
		Command setCommand = SetCommand.create(editingDomain, eObject, feature, value);
		if (setCommand != null) {
			editingDomain.getCommandStack().execute(setCommand);
		}
	}

}
