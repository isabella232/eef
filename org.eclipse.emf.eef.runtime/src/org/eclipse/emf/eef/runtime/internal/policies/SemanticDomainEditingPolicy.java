/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
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
		editingDomain.getCommandStack().execute(SetCommand.create(editingDomain, eObject, feature, value));
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticEditingPolicy#performUnset(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	protected void performUnset(EObject eObject, EStructuralFeature feature) {
		Command setCommand = null;
		if (feature.isMany()) {
			setCommand = SetCommand.create(editingDomain, eObject, feature, new BasicEList<Object>());
		} else {
			setCommand = SetCommand.create(editingDomain, eObject, feature, null);
		}
		editingDomain.getCommandStack().execute(setCommand);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticEditingPolicy#performAdd(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	protected void performAdd(EObject eObject, EStructuralFeature feature, Object newValue) {
		editingDomain.getCommandStack().execute(AddCommand.create(editingDomain, eObject, feature, newValue));
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticEditingPolicy#performRemove(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	protected void performRemove(EObject eObject, EStructuralFeature feature, Object oldValue) {
		editingDomain.getCommandStack().execute(RemoveCommand.create(editingDomain, eObject, feature, oldValue));
	}
	
	

}
