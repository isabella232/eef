/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.processors;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.command.AbortExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DomainEditingPolicyProcessor implements EditingPolicyProcessor {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return element instanceof SemanticDomainPropertiesEditingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#process(org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest)
	 */
	public final void process(PropertiesEditingContext editingContext, EditingPolicyRequest behavior) {
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
	private void executeCommand(DomainAwarePropertiesEditingContext domainEditingContext, Command command) {
		if (domainEditingContext.getOptions().liveMode()) {
			domainEditingContext.getEditingDomain().getCommandStack().execute(command);
		} else {
		    // If the command is executable, record and execute it.
		    //
		    if (command != null)
		    {
		      if (command.canExecute())
		      {
		        try
		        {
		          command.execute();
		        }
		        catch (AbortExecutionException exception)
		        {
		          command.dispose();
		        }
		        catch (RuntimeException exception)
		        {
		          handleError(exception);  
		          command.dispose();
		        }
		      }
		      else
		      {
		        command.dispose();
		      }
		    }							
		}
	}

	/**
	 * Converts a {@link EditingPolicyRequest} to an EMF {@link Command}. The returned value can be <code>null</code>,
	 * in this case the processing is cancelled.
	 * @param domainEditingContext {@link SemanticDomainPropertiesEditingContext} where the command will be performed.
	 * @param behavior {@link EditingPolicyRequest} to process.
	 * @return the {@link Command} to execute.
	 */
	protected Command convertToCommand(DomainAwarePropertiesEditingContext domainEditingContext, EditingPolicyRequest behavior) {
		EObject eObject = behavior.getTarget();
		EStructuralFeature feature = behavior.getFeature();
		Object newValue = behavior.getValue();
		switch (behavior.getProcessingKind()) {
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
				} else if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)){
					EClass newValueClass = (EClass) newValue;
					EClass referenceType = ((EReference)feature).getEReferenceType();
					if (referenceType == newValue || referenceType.isSuperTypeOf(newValueClass)) {
						return AddCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, EcoreUtil.create(newValueClass));							
					}
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
					} else if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)){
						EClass newValueClass = (EClass) newValue;
						EClass referenceType = ((EReference)feature).getEReferenceType();
						if (referenceType == newValue || referenceType.isSuperTypeOf(newValueClass)) {
							cc.append(AddCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, EcoreUtil.create(newValueClass)));							
						}
					} else {
						cc.append(AddCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, newValue1));
					}
				}
				return cc;
			}
		case REMOVE:
			return RemoveCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, behavior.getValue());
		case REMOVE_MANY:
			return RemoveCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, behavior.getValue());
		case MOVE:
			Object movedObject = ((List<?>)eObject.eGet(feature)).get(behavior.getOldIndex());
			return MoveCommand.create(domainEditingContext.getEditingDomain(), eObject, feature, movedObject, behavior.getNewIndex());
		}
		return IdentityCommand.INSTANCE;
	}

	/**
	 * Handles an exception thrown during command execution by logging it with the plugin.
	 */
	private void handleError(Exception exception) {
		//TODO: remove dependency to CommonPlugin
		CommonPlugin.INSTANCE.log(new WrappedException(CommonPlugin.INSTANCE.getString("_UI_IgnoreException_exception"), exception).fillInStackTrace());
	}
}
