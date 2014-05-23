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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer;
import org.eclipse.emf.eef.runtime.binding.MonoPropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.binding.MultiPropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.commands.EEFChangeCommand;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.DomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.editingstrategy.EditingStrategyNotFoundException;
import org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy;
import org.eclipse.emf.eef.runtime.internal.util.EEFInvocationParametersImpl;
import org.eclipse.emf.eef.runtime.internal.util.EEFModifierInvocationParametersImpl;
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
	 * @param emfServiceProvider
	 *            the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return element instanceof SemanticDomainPropertiesEditingContext;
	}

	/**
	 * {@inheritDoc}
	 * 
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
	 * 
	 * @param domainEditingContext
	 *            {@link SemanticDomainPropertiesEditingContext} where to
	 *            perform the command.
	 * @param command
	 *            {@link Command} to execute.
	 */
	private void executeCommand(DomainAwarePropertiesEditingContext domainEditingContext, Command command) {
		if (domainEditingContext.getOptions().liveMode()) {
			domainEditingContext.getEditingDomain().getCommandStack().execute(command);
		} else {
			// If the command is executable, record and execute it.
			//
			if (command != null) {
				if (command.canExecute()) {
					try {
						command.execute();
					} catch (AbortExecutionException exception) {
						command.dispose();
					} catch (RuntimeException exception) {
						handleError(exception);
						command.dispose();
					}
				} else {
					command.dispose();
				}
			}
		}
	}

	/**
	 * Converts a {@link EditingPolicyRequest} to an EMF {@link Command}. The
	 * returned value can be <code>null</code>, in this case the processing is
	 * cancelled.
	 * 
	 * @param domainEditingContext
	 *            {@link DomainPropertiesEditingContext} where the command will
	 *            be performed.
	 * @param request
	 *            {@link EditingPolicyRequest} to process.
	 * @return the {@link Command} to execute.
	 */
	protected Command convertToCommand(final DomainAwarePropertiesEditingContext domainEditingContext, EditingPolicyRequest request) {
		final EObject eObject = request.getTarget();
		Object newValue = request.getValue();
		switch (request.getProcessingKind()) {
		case SET:
			return performSet(domainEditingContext, eObject, newValue);
		case UNSET:
			return performUnset(domainEditingContext, eObject);
		case ADD:
			return performAdd(domainEditingContext, eObject, newValue);
		case ADD_MANY:
			return performAddMany(domainEditingContext, eObject, (Collection<?>) newValue);
		case REMOVE:
			return performRemove(domainEditingContext, eObject, newValue);
		case REMOVE_MANY:
			return performRemoveMany(domainEditingContext, eObject, (Collection<?>) newValue);
		case MOVE:
			return performMove(domainEditingContext, eObject, request.getOldIndex(), request.getNewIndex());
		}
		return IdentityCommand.INSTANCE;
	}

	protected final Command performSet(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final Object value) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			try {
				final SemanticPropertiesEditingContext semanticEditingContext = (SemanticPropertiesEditingContext) editingContext;
				return new EEFEditingStrategy<Command, Void>(semanticEditingContext, MonoPropertyBindingCustomizer.SETTER) {

					/**
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
					 */
					@Override
					protected Command processByAccessor(final EEFModifierCustomizer<Void> modifierCustomizer) {
						EMFService emfService = emfServiceProvider.getEMFService(eObject.eClass().getEPackage());
						Notifier highestNotifier = emfService.highestNotifier(eObject);
						return new EEFChangeCommand(semanticEditingContext.getChangeRecorder(), highestNotifier) {
							@Override
							protected void doExecute() {
								EList<Object> parameters = new BasicEList<Object>();
								parameters.add(value);
								modifierCustomizer.execute(new EEFModifierInvocationParametersImpl(editingContext, value));
							}
						};
					}

					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
					 */
					@Override
					protected Command processByFeature(EStructuralFeature feature) {
						if (value == null) {
							return null;
						} else {
							if (value instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
								return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String) value));
							} else {
								return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, value);
							}
						}
					}
				}.process();
			} catch (EditingStrategyNotFoundException e) {
				// Should I return null of IdentityCommand.INSTANCE.
				return null;
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}

	protected final Command performUnset(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			final SemanticPropertiesEditingContext semanticEditingContext = (SemanticPropertiesEditingContext) editingContext;
			try {
				return new EEFEditingStrategy<Command, Void>((SemanticPropertiesEditingContext) editingContext, MonoPropertyBindingCustomizer.UNSETTER) {

					/**
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
					 */
					@Override
					protected Command processByAccessor(final EEFModifierCustomizer<Void> modifierCustomizer) {
						EMFService emfService = emfServiceProvider.getEMFService(eObject.eClass().getEPackage());
						Notifier highestNotifier = emfService.highestNotifier(eObject);
						return new EEFChangeCommand(semanticEditingContext.getChangeRecorder(), highestNotifier) {
							@Override
							protected void doExecute() {
								modifierCustomizer.execute(new EEFInvocationParametersImpl(editingContext));
							}
						};
					}

					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
					 */
					@Override
					protected Command processByFeature(EStructuralFeature feature) {
						if (feature.isMany()) {
							return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, new BasicEList<Object>());
						} else {
							return SetCommand.create(editingContext.getEditingDomain(), eObject, feature, null);
						}
					}
				}.process();
			} catch (EditingStrategyNotFoundException e) {
				// Should I return null of IdentityCommand.INSTANCE.
				return null;
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}

	protected final Command performAdd(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final Object newValue) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			final SemanticPropertiesEditingContext semanticEditingContext = (SemanticPropertiesEditingContext) editingContext;
			try {
				return new EEFEditingStrategy<Command, Void>((SemanticPropertiesEditingContext) editingContext, MultiPropertyBindingCustomizer.ADDER) {

					/**
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
					 */
					@Override
					protected Command processByAccessor(final EEFModifierCustomizer<Void> modifierCustomizer) {
						EMFService emfService = emfServiceProvider.getEMFService(eObject.eClass().getEPackage());
						Notifier highestNotifier = emfService.highestNotifier(eObject);
						return new EEFChangeCommand(semanticEditingContext.getChangeRecorder(), highestNotifier) {
							@Override
							protected void doExecute() {
								EList<Object> parameters = new BasicEList<Object>();
								parameters.add(newValue);
								modifierCustomizer.execute(new EEFModifierInvocationParametersImpl(editingContext, newValue));
							}
						};
					}

					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
					 */
					@Override
					protected Command processByFeature(EStructuralFeature feature) {
						if (newValue == null) {
							return null;
						} else {
							if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
								return AddCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String) newValue));
							} else if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)) {
								EClass newValueClass = (EClass) newValue;
								EClass referenceType = ((EReference) feature).getEReferenceType();
								if (referenceType == newValue || referenceType.isSuperTypeOf(newValueClass)) {
									return AddCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.create(newValueClass));
								}
							}
							return AddCommand.create(editingContext.getEditingDomain(), eObject, feature, newValue);
						}
					}
				}.process();
			} catch (EditingStrategyNotFoundException e) {
				// Should I return null of IdentityCommand.INSTANCE.
				return null;
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}

	protected final Command performAddMany(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final Collection<?> newValues) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			final SemanticPropertiesEditingContext semanticEditingContext = (SemanticPropertiesEditingContext) editingContext;
			try {
				return new EEFEditingStrategy<Command, Void>((SemanticPropertiesEditingContext) editingContext, MultiPropertyBindingCustomizer.ADDER) {

					/**
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
					 */
					@Override
					protected Command processByAccessor(final EEFModifierCustomizer<Void> modifierCustomizer) {
						EMFService emfService = emfServiceProvider.getEMFService(eObject.eClass().getEPackage());
						Notifier highestNotifier = emfService.highestNotifier(eObject);
						return new EEFChangeCommand(semanticEditingContext.getChangeRecorder(), highestNotifier) {
							@Override
							protected void doExecute() {
								EList<Object> parameters = new BasicEList<Object>();
								parameters.addAll(newValues);
								modifierCustomizer.execute(new EEFModifierInvocationParametersImpl(editingContext, newValues));
							}
						};
					}

					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
					 */
					@Override
					protected Command processByFeature(EStructuralFeature feature) {
						if (newValues == null || newValues.isEmpty()) {
							return null;
						} else {
							CompoundCommand cc = new CompoundCommand("EEF add many command");
							for (Object newValue : newValues) {
								if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
									cc.append(AddCommand.create(editingContext.getEditingDomain(), eObject, feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String) newValue)));
								} else if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)) {
									EClass newValueClass = (EClass) newValue;
									EClass referenceType = ((EReference) feature).getEReferenceType();
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
				}.process();
			} catch (EditingStrategyNotFoundException e) {
				// Should I return null of IdentityCommand.INSTANCE.
				return null;
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}

	protected final Command performRemove(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final Object oldValue) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			final SemanticPropertiesEditingContext semanticEditingContext = (SemanticPropertiesEditingContext) editingContext;
			try {
				return new EEFEditingStrategy<Command, Void>((SemanticPropertiesEditingContext) editingContext, MultiPropertyBindingCustomizer.REMOVER) {

					/**
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
					 */
					@Override
					protected Command processByAccessor(final EEFModifierCustomizer<Void> modifierCustomizer) {
						EMFService emfService = emfServiceProvider.getEMFService(eObject.eClass().getEPackage());
						Notifier highestNotifier = emfService.highestNotifier(eObject);
						return new EEFChangeCommand(semanticEditingContext.getChangeRecorder(), highestNotifier) {
							@Override
							protected void doExecute() {
								EList<Object> parameters = new BasicEList<Object>();
								parameters.add(oldValue);
								modifierCustomizer.execute(new EEFModifierInvocationParametersImpl(editingContext, oldValue));
							}
						};
					}

					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
					 */
					@Override
					protected Command processByFeature(EStructuralFeature feature) {
						return RemoveCommand.create(editingContext.getEditingDomain(), eObject, feature, oldValue);
					}
				}.process();
			} catch (EditingStrategyNotFoundException e) {
				// Should I return null of IdentityCommand.INSTANCE.
				return null;
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}

	protected final Command performRemoveMany(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final Collection<?> oldValues) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			final SemanticPropertiesEditingContext semanticEditingContext = (SemanticPropertiesEditingContext) editingContext;
			try {
				return new EEFEditingStrategy<Command, Void>((SemanticPropertiesEditingContext) editingContext, MultiPropertyBindingCustomizer.REMOVER) {

					/**
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
					 */
					@Override
					protected Command processByAccessor(final EEFModifierCustomizer<Void> modifierCustomizer) {
						EMFService emfService = emfServiceProvider.getEMFService(eObject.eClass().getEPackage());
						Notifier highestNotifier = emfService.highestNotifier(eObject);
						return new EEFChangeCommand(semanticEditingContext.getChangeRecorder(), highestNotifier) {
							@Override
							protected void doExecute() {
								EList<Object> parameters = new BasicEList<Object>();
								parameters.addAll(oldValues);
								modifierCustomizer.execute(new EEFModifierInvocationParametersImpl(editingContext, oldValues));
							}
						};
					}

					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
					 */
					@Override
					protected Command processByFeature(EStructuralFeature feature) {
						return RemoveCommand.create(editingContext.getEditingDomain(), eObject, feature, oldValues);
					}
				}.process();
			} catch (EditingStrategyNotFoundException e) {
				// Should I return null of IdentityCommand.INSTANCE.
				return null;
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}

	protected final Command performMove(final DomainAwarePropertiesEditingContext editingContext, final EObject eObject, final Integer oldIndex, final Integer newIndex) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			try {
				return new EEFEditingStrategy<Command, Void>((SemanticPropertiesEditingContext) editingContext, -1) {

					/**
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
					 */
					@Override
					protected Command processByAccessor(EEFModifierCustomizer<Void> modifierCustomizer) {
						// Unreachable
						return null;
					}

					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
					 */
					@Override
					protected Command processByFeature(EStructuralFeature feature) {
						Object movedObject = ((List<?>) eObject.eGet(feature)).get(oldIndex);
						return MoveCommand.create(editingContext.getEditingDomain(), eObject, feature, movedObject, newIndex);
					}
				}.process();
			} catch (EditingStrategyNotFoundException e) {
				// Should I return null of IdentityCommand.INSTANCE.
				return null;
			}
		}
		// Should I return null of IdentityCommand.INSTANCE.
		return null;
	}

	/**
	 * Handles an exception thrown during command execution by logging it with
	 * the plugin.
	 */
	private void handleError(Exception exception) {
		// TODO: remove dependency to CommonPlugin
		CommonPlugin.INSTANCE.log(new WrappedException(CommonPlugin.INSTANCE.getString("_UI_IgnoreException_exception"), exception).fillInStackTrace());
	}

}
