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
package org.eclipse.emf.eef.runtime.tests.integration;

import java.util.Collection;
import java.util.Hashtable;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.tests.integration.EmbedingEditingEvent.TestEditingSettings;
import org.eclipse.emf.eef.runtime.ui.commands.AbstractBatchEditingCommand;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.WizardDomainEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.WizardEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.junit.After;
import org.junit.Before;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
@SuppressWarnings("restriction")
public abstract class AbstractWizardIntegrationTest extends AbstractIntegrationTest {

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.tests.integration.AbstractIntegrationTest#setUp()
	 */
	@Override
	@Before
	public void setUp() throws BundleException {
		super.setUp();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.tests.integration.AbstractIntegrationTest#tearDown()
	 */
	@Override
	@After
	public void tearDown() {
		super.tearDown();
	}

	/**
	 * Constants.
	 */
	protected static boolean OK = true;
	protected static boolean CANCEL = false;

	/**
	 * init Editing Policy Processor to test updates in wizard.
	 */
	protected void initEditingPolicyProcessors() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		BundleContext bundleContext = bundle.getBundleContext();

		// add WizardDomainEditingPolicyProcessor for test
		TestingWizardDomainEditingPolicyProcessor testingWizardDomainEditingPolicyProcessor = new TestingWizardDomainEditingPolicyProcessor();
		testingWizardDomainEditingPolicyProcessor.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
		testingWizardDomainEditingPolicyProcessor.setEMFServiceProvider(getEmfServiceProvider());
		EditingPolicyProcessor processor = new DelegatingPolicyProcessor(testingWizardDomainEditingPolicyProcessor);
		Hashtable<Object, Object> hashtable = new Hashtable<>();
		hashtable.put("priority.over", "org.eclipse.emf.eef.runtime.ui.swt.policies.processors.WizardDomainEditingPolicyProcessor");
		bundleContext.registerService(EditingPolicyProcessor.class.getName(), processor, hashtable);

		// add WizardEditingPolicyProcessor for test
		TestingWizardEditingPolicyProcessor testingWizardEditingPolicyProcessor = new TestingWizardEditingPolicyProcessor();
		testingWizardEditingPolicyProcessor.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
		testingWizardEditingPolicyProcessor.setEMFServiceProvider(getEmfServiceProvider());
		testingWizardEditingPolicyProcessor.setEditUIProvidersFactory(getEditUIProvidersFactory());
		EditingPolicyProcessor processor2 = new DelegatingPolicyProcessor(testingWizardEditingPolicyProcessor);
		Hashtable<Object, Object> hashtable2 = new Hashtable<>();
		hashtable2.put("priority.over", "org.eclipse.emf.eef.runtime.ui.swt.policies.processors.WizardEditingPolicyProcessor");
		hashtable2.put("component.name", "org.eclipse.emf.eef.runtime.tests.integration.AbstractWizardIntegrationTest.TestingWizardEditingPolicyProcessor.TestingWizardEditingPolicyProcessor()");
		bundleContext.registerService(EditingPolicyProcessor.class.getName(), processor2, hashtable2);
	}

	/**
	 * Delegating Processor.
	 * 
	 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
	 * 
	 */
	private static final class DelegatingPolicyProcessor implements EditingPolicyProcessor {

		private EditingPolicyProcessor delegatedProcessor;

		public DelegatingPolicyProcessor(EditingPolicyProcessor delegatedProcessor) {
			this.delegatedProcessor = delegatedProcessor;
		}

		/**
		 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
		 */
		public boolean serviceFor(PropertiesEditingContext element) {
			return delegatedProcessor.serviceFor(element);
		}

		/**
		 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#process(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
		 *      org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest)
		 */
		public void process(PropertiesEditingContext editingContext, EditingPolicyRequest behavior) {
			delegatedProcessor.process(editingContext, behavior);
			PropertiesEditingEvent editingEvent = ((SemanticPropertiesEditingContext) editingContext).getEditingEvent();
			if (editingEvent instanceof EmbedingEditingEvent) {
				((EmbedingEditingEvent) editingEvent).getSettings().testSettings(editingContext);
			}
		}

	}

	/**
	 * 
	 * WizardEditingPolicyProcessor
	 * 
	 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
	 * 
	 */
	private final class TestingWizardEditingPolicyProcessor extends WizardEditingPolicyProcessor {

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.WizardEditingPolicyProcessor#serviceFor(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
		 */
		@Override
		public boolean serviceFor(PropertiesEditingContext element) {
			return super.serviceFor(element);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.WizardEditingPolicyProcessor#performEdit(org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext,
		 *      org.eclipse.emf.ecore.EObject, java.lang.Object)
		 */
		@Override
		protected void performEdit(SemanticPropertiesEditingContext context, EObject eObject, Object value) {
			context.startEditing();
			EObject editedObject = (EObject) context.getEditingEvent().getNewValue();
			PropertiesEditingContextFactory editingContextFactory = context.getContextFactoryProvider().getEditingContextFactory(editedObject);
			PropertiesEditingContext editingContext = editingContextFactory.createPropertiesEditingContext(context, editedObject);
			editingContext.getOptions().setBatchMode(true);
			editingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
			boolean finish = updateOnWizard(editingContext, context.getEditingEvent());
			if (finish == CANCEL) {
				context.cancelEditing();
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.WizardEditingPolicyProcessor#defineEObjectToAdd(org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext,
		 *      org.eclipse.emf.ecore.EReference)
		 */
		@Override
		protected Object defineEObjectToAdd(SemanticPropertiesEditingContext editingContext, EReference editedReference) {
			return mockDefineEObjectToAdd(editingContext, editedReference);
		}

	}

	/**
	 * WizardDomainEditingPolicyProcessor
	 * 
	 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
	 * 
	 */
	private final class TestingWizardDomainEditingPolicyProcessor extends WizardDomainEditingPolicyProcessor {

		public TestingWizardDomainEditingPolicyProcessor() {
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.WizardDomainEditingPolicyProcessor#convertToCommand(org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext,
		 *      org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest)
		 */
		@Override
		protected Command convertToCommand(DomainAwarePropertiesEditingContext domainEditingContext, EditingPolicyRequest behavior) {
			if (domainEditingContext instanceof SemanticPropertiesEditingContext) {
				final PropertiesEditingEvent event = ((SemanticPropertiesEditingContext) domainEditingContext).getEditingEvent();
				Object newValue = behavior.getValue();
				switch (behavior.getProcessingKind()) {
				case ADD: {
					EStructuralFeature feature = eefEditingServiceProvider.getEditingService(domainEditingContext.getEditingComponent().getEObject()).featureFromEditor(domainEditingContext, event.getAffectedEditor());
					if (feature instanceof EReference && ((EReference) feature).isContainment() && newValue == null) {
						newValue = mockDefineEObjectToAdd((SemanticPropertiesEditingContext) domainEditingContext, (EReference) feature);
						return performAdd(domainEditingContext, domainEditingContext.getEditingComponent().getEObject(), newValue);
					}
				}
				case EDIT:
					if (newValue != null) {
						PropertiesEditingContextFactory editingContextFactory = domainEditingContext.getContextFactoryProvider().getEditingContextFactory((EObject) newValue);
						final SemanticPropertiesEditingContext context = editingContextFactory.createSemanticPropertiesEditingContext(domainEditingContext, event);
						context.getOptions().setBatchMode(true);
						context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
						AbstractBatchEditingCommand wizardEditingCommand = new AbstractBatchEditingCommand((SemanticPropertiesEditingContext) context) {

							/**
							 * (non-Javadoc)
							 * 
							 * @see org.eclipse.emf.eef.runtime.ui.swt.commands.WizardEditingCommand#prepareBatchEditing()
							 */
							@Override
							protected boolean prepareBatchEditing() {
								return openWizardAndUpdate(event, context);
							}

						};
						return wizardEditingCommand;
					}
				}
			}
			return null;
		}

	}

	/**
	 * Simulate wizard opening and update element.
	 * 
	 * @param event
	 *            PropertiesEditingEvent
	 * @param context
	 *            SemanticPropertiesEditingContext
	 * @return ok or cancel
	 */
	public boolean openWizardAndUpdate(final PropertiesEditingEvent event, final SemanticPropertiesEditingContext context) {
		context.startEditing();
		EObject editedObject = (EObject) context.getEditingEvent().getNewValue();
		PropertiesEditingContextFactory editingContextFactory = context.getContextFactoryProvider().getEditingContextFactory(editedObject);
		PropertiesEditingContext editingContext = editingContextFactory.createPropertiesEditingContext(context, editedObject);
		editingContext.getOptions().setBatchMode(true);
		editingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
		boolean finish = updateOnWizard(editingContext, event);
		if (finish == CANCEL) {
			context.cancelEditing();
		}
		return finish;
	}

	/**
	 * @param editedReference
	 *            {@link EReference} to edit.
	 * @return the {@link EObject} to set in the given {@link EReference}.
	 */
	protected EObject mockDefineEObjectToAdd(SemanticPropertiesEditingContext editingContext, EReference editedReference) {
		editingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
		EMFService emfService = getEmfServiceProvider().getEMFService(editingContext.getEditingComponent().getEObject().eClass().getEPackage());
		Collection<EClass> listOfInstanciableType = emfService.listOfInstanciableType(editingContext.getAdapterFactory(), editingContext.getEditingComponent().getEObject(), editedReference);
		EObject createdObject = null;
		if (listOfInstanciableType.size() > 0) {
			createdObject = EcoreUtil.create(listOfInstanciableType.iterator().next());
			Resource eResource = editingContext.getEditingComponent().getEObject().eResource();
			if (eResource != null) {
				getEEFEditingServiceProvider().getEditingService(createdObject).attachToResource(editingContext, eResource, createdObject);
				editingContext.startEditing();
			}
			PropertiesEditingContextFactory contextFactory = getEditingContextFactoryProvider().getEditingContextFactory(createdObject);
			PropertiesEditingContext subContext = contextFactory.createPropertiesEditingContext(editingContext, createdObject);
			boolean updateOnWizard = updateOnWizard(subContext, editingContext.getEditingEvent());
			getEEFEditingServiceProvider().getEditingService(createdObject).detachFromResource(editingContext, createdObject);
			if (updateOnWizard) {
				return createdObject;
			}
		} else {
			// FIXME: I've got a pb
		}

		return null;
	}

	/**
	 * Apply updates in wizard.
	 * 
	 * @param context
	 *            PropertiesEditingContext
	 * @param event
	 *            PropertiesEditingEvent
	 * @return ok or cancel
	 */
	protected boolean updateOnWizard(PropertiesEditingContext context, PropertiesEditingEvent event) {
		if (event instanceof EmbedingEditingEvent) {
			TestEditingSettings settings = ((EmbedingEditingEvent) event).getSettings();
			for (PropertiesEditingEvent subEvent : settings.getEvents()) {
				getPropertiesBindingHandler().firePropertiesChanged(context.getEditingComponent(), subEvent);
			}
			return settings.isStatus();
		}
		return false;
	}

	/**
	 * Processor : execute event.
	 * 
	 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
	 * 
	 */
	public final class Processor {

		private PropertiesEditingComponent editingComponent;

		/**
		 * Constructor
		 * 
		 * @param editingComponent
		 *            PropertiesEditingComponent
		 */
		public Processor(PropertiesEditingComponent editingComponent) {
			this.editingComponent = editingComponent;
		}

		/**
		 * Fire event.
		 * 
		 * @param editingEvent
		 *            PropertiesEditingEvent
		 */
		public void execute(PropertiesEditingEvent editingEvent) {
			getPropertiesBindingHandler().firePropertiesChanged(editingComponent, editingEvent);
		}

		/**
		 * @return is command is undoable.
		 */
		public boolean canUndo() {
			return getEditingDomain().getCommandStack().canUndo();
		}

		/**
		 * Undo command.
		 */
		public void undo() {
			getEditingDomain().getCommandStack().undo();
		}

		/**
		 * @return is command is redoable.
		 */
		public boolean canRedo() {
			return getEditingDomain().getCommandStack().canRedo();
		}

		/**
		 * Redo command.
		 */
		public void redo() {
			getEditingDomain().getCommandStack().redo();
		}
	}

}
