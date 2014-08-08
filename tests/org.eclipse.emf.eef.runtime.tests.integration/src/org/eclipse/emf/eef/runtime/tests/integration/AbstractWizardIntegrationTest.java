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
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.commands.WizardEditingCommand;
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
		setPropertiesEditingContext(null);
		setPropertyEvent(null);
		super.tearDown();
	}

	/**
	 * Constants.
	 */
	protected static boolean OK = true;
	protected static boolean CANCEL = false;

	/**
	 * Event
	 */
	private PropertiesEditingEvent propertyEvent;
	private boolean finished;
	private PropertiesEditingContext propertiesEditingContext;

	/**
	 * @return the propertiesEditingContext
	 */
	public PropertiesEditingContext getPropertiesEditingContext() {
		return propertiesEditingContext;
	}

	/**
	 * @param propertiesEditingContext
	 *            the propertiesEditingContext to set
	 */
	public void setPropertiesEditingContext(PropertiesEditingContext propertiesEditingContext) {
		this.propertiesEditingContext = propertiesEditingContext;
	}

	/**
	 * init Editing Policy Processor to test updates in wizard.
	 */
	protected void initEditingPolicyProcessor() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		BundleContext bundleContext = bundle.getBundleContext();
		WizardDomainEditingPolicyProcessor wizardDomainEditingPolicyProcessor = new WizardDomainEditingPolicyProcessor() {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.WizardDomainEditingPolicyProcessor#convertToCommand(org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext,
			 *      org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest)
			 */
			@Override
			protected Command convertToCommand(DomainAwarePropertiesEditingContext domainEditingContext, EditingPolicyRequest behavior) {
				if (domainEditingContext instanceof SemanticPropertiesEditingContext) {
					PropertiesEditingEvent event = ((SemanticPropertiesEditingContext) domainEditingContext).getEditingEvent();
					Object newValue = behavior.getValue();
					switch (behavior.getProcessingKind()) {
					case EDIT:
						if (newValue != null) {
							PropertiesEditingContextFactory editingContextFactory = domainEditingContext.getContextFactoryProvider().getEditingContextFactory((EObject) newValue);
							final SemanticPropertiesEditingContext context = editingContextFactory.createSemanticPropertiesEditingContext(domainEditingContext, event);
							context.getOptions().setBatchMode(true);
							context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
							WizardEditingCommand wizardEditingCommand = new WizardEditingCommand(domainEditingContext.getContextFactoryProvider(), getEmfServiceProvider(), getEEFEditingServiceProvider(), getEditUIProvidersFactory(), (SemanticPropertiesEditingContext) context) {

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
									setPropertiesEditingContext(editingContext);
									boolean finish = updateOnWizard(editingContext);
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

		};
		wizardDomainEditingPolicyProcessor.setEMFServiceProvider(getEmfServiceProvider());
		wizardDomainEditingPolicyProcessor.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
		wizardDomainEditingPolicyProcessor.setEditUIProvidersFactory(getEditUIProvidersFactory());
		Hashtable<Object, Object> hashtable = new Hashtable<>();
		hashtable.put("priority.over", "org.eclipse.emf.eef.runtime.ui.swt.policies.processors.WizardDomainEditingPolicyProcessor");
		bundleContext.registerService(EditingPolicyProcessor.class.getName(), wizardDomainEditingPolicyProcessor, hashtable);
	}

	/**
	 * Apply updates in wizard.
	 * 
	 * @param context
	 *            PropertiesEditingContext
	 * @param editingContext
	 * @return ok or cancel
	 */
	protected abstract boolean updateOnWizard(PropertiesEditingContext context);

	/**
	 * @param propertiesEditingEvent
	 *            PropertiesEditingEvent
	 * @param finished
	 */
	protected void setWizardProperties(PropertiesEditingEvent propertiesEditingEvent, boolean finished) {
		setPropertyEvent(propertiesEditingEvent);
		setFinished(finished);

	}

	/**
	 * @return the propertyEvent
	 */
	public PropertiesEditingEvent getPropertyEvent() {
		return propertyEvent;
	}

	/**
	 * @param propertyEvent
	 *            the propertyEvent to set
	 */
	public void setPropertyEvent(PropertiesEditingEvent propertyEvent) {
		this.propertyEvent = propertyEvent;
	}

	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * @param finished
	 *            the finished to set
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

}
