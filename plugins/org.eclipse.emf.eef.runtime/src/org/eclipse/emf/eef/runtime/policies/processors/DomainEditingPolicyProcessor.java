/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies.processors;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
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
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class DomainEditingPolicyProcessor implements EditingPolicyProcessor {

	protected DomainAwarePropertiesEditingContext editingContext;

	/**
	 * @param editingContext
	 */
	public DomainEditingPolicyProcessor(DomainAwarePropertiesEditingContext editingContext) {
		this.editingContext = editingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#process(org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing)
	 */
	public void process(EditingPolicyProcessing behavior) {
		executeCommand(convertToCommand(behavior));
	}

	/**
	 * Executes the given command.
	 * @param command {@link Command} to execute.
	 */
	protected abstract void executeCommand(Command command);

	/**
	 * Converts a {@link EditingPolicyProcessing} to an EMF {@link Command}.
	 * @param behavior {@link EditingPolicyProcessing} to process.
	 * @return the {@link Command} to execute.
	 */
	private Command convertToCommand(EditingPolicyProcessing behavior) {
		EObject eObject = behavior.target;
		EStructuralFeature feature = behavior.feature;
		Object newValue = behavior.value;
		switch (behavior.processingKind) {
		case SET:
			if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
				return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue));
			} else {
				return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, newValue);
			}
		case UNSET:
			if (feature.isMany()) {
				return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, new BasicEList<Object>());
			} else {
				return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, null);
			}
		case ADD:
			if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
				return AddCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue));
			} else {
				return AddCommand.create(editingContext.getEditingDomain(), eObject, feature, newValue);
			}
		case ADD_MANY:
			CompoundCommand cc = new CompoundCommand("EEF add many command");
			for (Object newValue1 : (Collection<?>)newValue) {
				if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
					cc.append(AddCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue1)));
				} else {
					cc.append(AddCommand.create(editingContext.getEditingDomain(), eObject, feature, newValue1));
				}
			}
			return cc;
		case REMOVE:
			return RemoveCommand.create(editingContext.getEditingDomain(), eObject, feature, behavior.value);
		case REMOVE_MANY:
			return RemoveCommand.create(editingContext.getEditingDomain(), eObject, feature, behavior.value);
		case MOVE:
			return MoveCommand.create(editingContext.getEditingDomain(), eObject, feature, behavior.oldIndex, behavior.newIndex);
		}
		return IdentityCommand.INSTANCE;
	}

}
