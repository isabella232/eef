/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.internal.policies.processors;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.command.AbortExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.MonoValuedPropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.internal.context.DomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DomainEditingPolicyProcessor implements EditingPolicyProcessor {

	private EMFServiceProvider emfServiceProvider;
	
	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

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
	 * @param domainEditingContext {@link DomainPropertiesEditingContext} where the command will be performed.
	 * @param request {@link EditingPolicyRequest} to process.
	 * @return the {@link Command} to execute.
	 */
	protected Command convertToCommand(final DomainAwarePropertiesEditingContext domainEditingContext, EditingPolicyRequest request) {
		final EObject eObject = request.getTarget();
		final PropertyBinding propertyBinding = request.getPropertyBinding();
		Object newValue = request.getValue();
		switch (request.getProcessingKind()) {
		case SET:
			return performSet(domainEditingContext, eObject, propertyBinding, newValue);
		case UNSET:
			return performUnset(domainEditingContext, eObject, propertyBinding);
		case ADD:
			return performAdd(domainEditingContext, eObject, propertyBinding, newValue);
		case ADD_MANY:
			return performAddMany(domainEditingContext, eObject, propertyBinding, (Collection<?>) newValue);
		case REMOVE:
			return performRemove(domainEditingContext, eObject, propertyBinding, newValue);
		case REMOVE_MANY:
			return performRemoveMany(domainEditingContext, eObject, propertyBinding, (Collection<?>) newValue);
		case MOVE:
			return performMove(domainEditingContext, eObject, propertyBinding, request.getOldIndex(), request.getNewIndex());
		}
		return IdentityCommand.INSTANCE;
	}

	protected final Command performSet(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final PropertyBinding propertyBinding, final Object value) {
		if (propertyBinding instanceof MonoValuedPropertyBinding && ((MonoValuedPropertyBinding) propertyBinding).getSetter() != null) {
			ChangeRecorder changeRecorder = createChangeRecorder(eObject);
			return new ChangeCommand(changeRecorder) {
				@Override
				protected void doExecute() {
					EList<Object> parameters = new BasicEList<Object>();
					parameters.add(value);
					((MonoValuedPropertyBinding) propertyBinding).getSetter().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, parameters);
				}
			};
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
				if (value == null) {
					return null;
				} else {
					if (value instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
						return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)value));
					} else {
						return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, value);
					}
				}
			}
			// Should I return null of IdentityCommand.INSTANCE.
			return null;
		}
	}

	protected final Command performUnset(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final PropertyBinding propertyBinding) {
		if (propertyBinding instanceof MonoValuedPropertyBinding && ((MonoValuedPropertyBinding) propertyBinding).getUnsetter() != null) {
			ChangeRecorder changeRecorder = createChangeRecorder(eObject);
			return new ChangeCommand(changeRecorder) {
				@Override
				protected void doExecute() {
					((MonoValuedPropertyBinding) propertyBinding).getUnsetter().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, new BasicEList<Object>());
				}
			};
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
				if (feature.isMany()) {
					return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, new BasicEList<Object>());
				} else {
					return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, null);
				}
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}
	
	protected final Command performAdd(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final PropertyBinding propertyBinding, final Object newValue) {
		if (propertyBinding instanceof MultiValuedPropertyBinding && ((MultiValuedPropertyBinding) propertyBinding).getAdder() != null) {
			ChangeRecorder changeRecorder = createChangeRecorder(eObject);
			return new ChangeCommand(changeRecorder) {
				@Override
				protected void doExecute() {
					EList<Object> parameters = new BasicEList<Object>();
					parameters.add(newValue);
					((MultiValuedPropertyBinding) propertyBinding).getAdder().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, parameters);
				}
			};
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
				if (newValue == null) {
					return null;
				} else {
					if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
						return AddCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue));
					} else if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)){
						EClass newValueClass = (EClass) newValue;
						EClass referenceType = ((EReference)feature).getEReferenceType();
						if (referenceType == newValue || referenceType.isSuperTypeOf(newValueClass)) {
							return AddCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.create(newValueClass));							
						}
					} else {
						return AddCommand.create(editingContext.getEditingDomain(), eObject, feature, newValue);
					}
				}
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}
	
	protected final Command performAddMany(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final PropertyBinding propertyBinding, final Collection<?> newValues) {
		if (propertyBinding instanceof MultiValuedPropertyBinding && ((MultiValuedPropertyBinding) propertyBinding).getAdder() != null) {
			ChangeRecorder changeRecorder = createChangeRecorder(eObject);
			return new ChangeCommand(changeRecorder) {
				@Override
				protected void doExecute() {
					EList<Object> parameters = new BasicEList<Object>();
					parameters.addAll(newValues);
					((MultiValuedPropertyBinding) propertyBinding).getAdder().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, parameters);
				}
			};
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
				if (newValues == null || newValues.isEmpty()) {
					return null;
				} else {
					CompoundCommand cc = new CompoundCommand("EEF add many command");
					for (Object newValue : newValues) {
						if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
							cc.append(AddCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue)));
						} else if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)){
							EClass newValueClass = (EClass) newValue;
							EClass referenceType = ((EReference)feature).getEReferenceType();
							if (referenceType == newValue || referenceType.isSuperTypeOf(newValueClass)) {
								cc.append(AddCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.create(newValueClass)));							
							}
						} else {
							cc.append(AddCommand.create(editingContext.getEditingDomain(), eObject, feature, newValue));
						}
					}
					return cc;
				}
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}
	
	protected final Command performRemove(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final PropertyBinding propertyBinding, final Object oldValue) {
		if (propertyBinding instanceof MultiValuedPropertyBinding && ((MultiValuedPropertyBinding) propertyBinding).getRemover() != null) {
			ChangeRecorder changeRecorder = createChangeRecorder(eObject);
			return new ChangeCommand(changeRecorder) {
				@Override
				protected void doExecute() {
					EList<Object> parameters = new BasicEList<Object>();
					parameters.add(oldValue);
					((MultiValuedPropertyBinding) propertyBinding).getRemover().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, parameters);
				}
			};
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
				return RemoveCommand.create(editingContext.getEditingDomain(), eObject, feature, oldValue);
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}
	
	protected final Command performRemoveMany(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final PropertyBinding propertyBinding, final Collection<?> oldValues) {
		if (propertyBinding instanceof MultiValuedPropertyBinding && ((MultiValuedPropertyBinding) propertyBinding).getRemover() != null) {
			ChangeRecorder changeRecorder = createChangeRecorder(eObject);
			return new ChangeCommand(changeRecorder) {
				@Override
				protected void doExecute() {
					EList<Object> parameters = new BasicEList<Object>();
					parameters.addAll(oldValues);
					((MultiValuedPropertyBinding) propertyBinding).getRemover().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, parameters);
				}
			};
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
				return RemoveCommand.create(editingContext.getEditingDomain(), eObject, feature, oldValues);
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}
	
	protected final Command performMove(DomainAwarePropertiesEditingContext editingContext, EObject eObject, PropertyBinding propertyBinding, Integer oldIndex, Integer newIndex) {
		if (propertyBinding instanceof EStructuralFeatureBinding) {
			EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
			Object movedObject = ((List<?>)eObject.eGet(feature)).get(oldIndex);
			return MoveCommand.create(editingContext.getEditingDomain(), eObject, feature, movedObject, newIndex);
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}
	
	private ChangeRecorder createChangeRecorder(final EObject eObject) {
		EMFService emfService = emfServiceProvider.getEMFService(eObject.eClass().getEPackage());
		Notifier highestNotifier = emfService.highestNotifier(eObject);
		ChangeRecorder changeRecorder = null;
		if (highestNotifier instanceof ResourceSet) {
			changeRecorder = new ChangeRecorder((ResourceSet) highestNotifier);
		} else if (highestNotifier instanceof Resource) {
			changeRecorder = new ChangeRecorder((Resource) highestNotifier);					
		} else {
			changeRecorder = new ChangeRecorder((EObject) highestNotifier);
		}
		return changeRecorder;
	}

	private EStructuralFeature extractFeature(EStructuralFeatureBinding propertyBinding, EObject target) {
		EMFService emfService = emfServiceProvider.getEMFService(target.eClass().getEPackage());
		return emfService.mapFeature(target, propertyBinding.getFeature());
	}
	
	/**
	 * Handles an exception thrown during command execution by logging it with the plugin.
	 */
	private void handleError(Exception exception) {
		//TODO: remove dependency to CommonPlugin
		CommonPlugin.INSTANCE.log(new WrappedException(CommonPlugin.INSTANCE.getString("_UI_IgnoreException_exception"), exception).fillInStackTrace());
	}


}
