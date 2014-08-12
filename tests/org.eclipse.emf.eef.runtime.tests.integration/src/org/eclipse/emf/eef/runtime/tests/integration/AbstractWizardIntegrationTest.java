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

import java.util.Hashtable;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler;
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
	protected void initEditingPolicyProcessor() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		BundleContext bundleContext = bundle.getBundleContext();
		EditingPolicyProcessor processor = new DelegatingPolicyProcessor(new TestingWizardDomainEditingPolicyProcessor(getPropertiesBindingHandler()));
		Hashtable<Object, Object> hashtable = new Hashtable<>();
		hashtable.put("priority.over", "org.eclipse.emf.eef.runtime.ui.swt.policies.processors.WizardDomainEditingPolicyProcessor");
		bundleContext.registerService(EditingPolicyProcessor.class.getName(), processor, hashtable);
	}

	// /**
	// * init Editing Policy Processor to test updates in wizard.
	// */
	// protected void initEditingPolicyRequestFactory() {
	// Bundle bundle = FrameworkUtil.getBundle(getClass());
	// BundleContext bundleContext = bundle.getBundleContext();
	// EditingPolicyRequestFactory processor = new
	// EReferenceDirectWizardEditingPolicyRequest);
	// Hashtable<Object, Object> hashtable = new Hashtable<>();
	// hashtable.put("priority.over",
	// "org.eclipse.emf.eef.runtime.ui.swt.policies.processors.WizardDomainEditingPolicyProcessor");
	// bundleContext.registerService(EditingPolicyProcessor.class.getName(),
	// processor, hashtable);
	// }

	private static final class DelegatingPolicyProcessor implements EditingPolicyProcessor {

		private EditingPolicyProcessor delegatedProcessor;

		public DelegatingPolicyProcessor(EditingPolicyProcessor delegatedProcessor) {
			this.delegatedProcessor = delegatedProcessor;
		}

		/**
		 * @param element
		 * @return
		 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
		 */
		public boolean serviceFor(PropertiesEditingContext element) {
			return delegatedProcessor.serviceFor(element);
		}

		/**
		 * @param editingContext
		 * @param behavior
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

	private static final class TestingWizardDomainEditingPolicyProcessor extends WizardDomainEditingPolicyProcessor {

		private final PropertiesBindingHandler propertiesBindingHandler;

		public TestingWizardDomainEditingPolicyProcessor(PropertiesBindingHandler propertiesBindingHandler) {
			this.propertiesBindingHandler = propertiesBindingHandler;
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

						};
						return wizardEditingCommand;
					}
				}
			}
			return null;
		}

		/**
		 * Apply updates in wizard.
		 * 
		 * @param context
		 *            PropertiesEditingContext
		 * @param event
		 * @param editingContext
		 * @return ok or cancel
		 */
		protected boolean updateOnWizard(PropertiesEditingContext context, PropertiesEditingEvent event) {
			if (event instanceof EmbedingEditingEvent) {
				TestEditingSettings settings = ((EmbedingEditingEvent) event).getSettings();
				for (PropertiesEditingEvent subEvent : settings.getEvents()) {
					propertiesBindingHandler.firePropertiesChanged(context.getEditingComponent(), subEvent);
				}
				return settings.isStatus();
			}
			return false;
		}

	}

	public final class Processor {

		private PropertiesEditingComponent editingComponent;

		public Processor(PropertiesEditingComponent editingComponent) {
			super();
			this.editingComponent = editingComponent;
		}

		public void execute(PropertiesEditingEvent editingEvent) {
			getPropertiesBindingHandler().firePropertiesChanged(editingComponent, editingEvent);
		}

		public boolean canUndo() {
			return getEditingDomain().getCommandStack().canUndo();
		}

		public void undo() {
			getEditingDomain().getCommandStack().undo();
		}

		public boolean canRedo() {
			return getEditingDomain().getCommandStack().canRedo();
		}

		public void redo() {
			getEditingDomain().getCommandStack().redo();
		}
	}

}
