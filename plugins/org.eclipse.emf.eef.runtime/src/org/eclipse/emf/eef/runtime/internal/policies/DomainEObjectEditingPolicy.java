/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class DomainEObjectEditingPolicy extends EObjectEditingPolicy {

	/**
	 * @param editingContext
	 * @param editingEvent
	 */
	public DomainEObjectEditingPolicy(DomainAwarePropertiesEditingContext editingContext, PropertiesEditingEvent editingEvent) {
		super(editingContext, editingEvent);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#getEditingContext()
	 */
	@Override
	public DomainAwarePropertiesEditingContext getEditingContext() {
		return (DomainAwarePropertiesEditingContext) super.getEditingContext();
	}

	/**
	 * Process the given {@link Command} to execute the current policy.
	 * @param command command to process.
	 */
	protected abstract void processCommand(Command command);
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performSet(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	public void performSet(EObject eObject, EStructuralFeature feature, Object value) {
		if (value instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
			processCommand(SetCommand.create(getEditingContext().getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)value)));
		} else {
			processCommand(SetCommand.create(getEditingContext().getEditingDomain(), eObject, feature, value));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performUnset(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	protected void performUnset(EObject eObject, EStructuralFeature feature) {
		Command setCommand = null;
		if (feature.isMany()) {
			setCommand = SetCommand.create(getEditingContext().getEditingDomain(), eObject, feature, new BasicEList<Object>());
		} else {
			setCommand = SetCommand.create(getEditingContext().getEditingDomain(), eObject, feature, null);
		}
		processCommand(setCommand);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performAdd(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	protected void performAdd(EObject eObject, EStructuralFeature feature, Object newValue) {
		if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
			processCommand(AddCommand.create(getEditingContext().getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue)));
		} else {
			processCommand(AddCommand.create(getEditingContext().getEditingDomain(), eObject, feature, newValue));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performAddMany(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 */
	protected void performAddMany(EObject eObject, EStructuralFeature feature, Collection<?> newValues) {
		for (Object newValue : newValues) {
			if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
				processCommand(AddCommand.create(getEditingContext().getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue)));
			} else {
				processCommand(AddCommand.create(getEditingContext().getEditingDomain(), eObject, feature, newValue));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performRemove(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	protected void performRemove(EObject eObject, EStructuralFeature feature, Object oldValue) {
		processCommand(RemoveCommand.create(getEditingContext().getEditingDomain(), eObject, feature, oldValue));
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performRemoveMany(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 */
	protected void performRemoveMany(EObject eObject, EStructuralFeature feature, Collection<?> oldValues) {
		processCommand(RemoveCommand.create(getEditingContext().getEditingDomain(), eObject, feature, oldValues));
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performMove(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Integer, java.lang.Integer)
	 * TODO: Check this. Can we pass the current index or must we pass the EObject to move.
	 */
	protected void performMove(EObject eObject, EStructuralFeature feature, Integer oldIndex, Integer newIndex) {
		processCommand(MoveCommand.create(getEditingContext().getEditingDomain(), eObject, feature, oldIndex, newIndex));
	}

}
