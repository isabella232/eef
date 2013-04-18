/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.processors;

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
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class DomainEditingPolicyProcessor extends AbstractEEFService<PropertiesEditingContext> implements EditingPolicyProcessor {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#process(org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent)
	 */
	public void process(PropertiesEditingContext editingContext, EditingPolicyIntent behavior) {
		DomainAwarePropertiesEditingContext domainEditingContext = (DomainAwarePropertiesEditingContext) editingContext;
		Command convertToCommand = convertToCommand(domainEditingContext, behavior);
		if (convertToCommand != null) {
			executeCommand(domainEditingContext, convertToCommand);
		}
	}

	/**
	 * Executes the given command in the given context.
	 * @param domainEditingContext {@link SemanticDomainPropertiesEditingContext} where to perform the command.
	 * @param command {@link Command} to execute.
	 */
	protected abstract void executeCommand(DomainAwarePropertiesEditingContext domainEditingContext, Command command);

	/**
	 * Converts a {@link EditingPolicyIntent} to an EMF {@link Command}. The returned value can be <code>null</code>,
	 * in this case the processing is cancelled.
	 * @param domainEditingContext {@link SemanticDomainPropertiesEditingContext} where the command will be performed.
	 * @param behavior {@link EditingPolicyIntent} to process.
	 * @return the {@link Command} to execute.
	 */
	protected Command convertToCommand(DomainAwarePropertiesEditingContext domainEditingContext, EditingPolicyIntent behavior) {
		EObject eObject = behavior.target;
		EStructuralFeature feature = behavior.feature;
		Object newValue = behavior.value;
		switch (behavior.processingKind) {
		case SET:
			if (newValue == null) {
				return null;
			} else {
				if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
					return SetCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue));
				} else {
					return SetCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, newValue);
				}
			}
		case UNSET:
			if (feature.isMany()) {
				return SetCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, new BasicEList<Object>());
			} else {
				return SetCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, null);
			}
		case ADD:
			if (newValue == null) {
				return null;
			} else {
				if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
					return AddCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue));
				} else {
					return AddCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, newValue);
				}
			}
		case ADD_MANY:
			if (newValue == null) {
				return null;
			} else {
				CompoundCommand cc = new CompoundCommand("EEF add many command");
				for (Object newValue1 : (Collection<?>)newValue) {
					if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
						cc.append(AddCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue1)));
					} else {
						cc.append(AddCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, newValue1));
					}
				}
				return cc;
			}
		case REMOVE:
			return RemoveCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, behavior.value);
		case REMOVE_MANY:
			return RemoveCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, behavior.value);
		case MOVE:
			return MoveCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, behavior.oldIndex, behavior.newIndex);
		}
		return IdentityCommand.INSTANCE;
	}

}
