/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.processors.DirectEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFEditingWizard;
import org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFWizardDialog;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.swt.widgets.Shell;

/**
 * @author NLEPINE
 * 
 */
public class WizardEditingPolicyProcessor extends DirectEditingPolicyProcessor {

	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;
	private EditUIProvidersFactory editUIProvidersFactory;

	/**
	 * @param emfServiceProvider
	 *            the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefEditingServiceProvider
	 *            the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * @param editUIProvidersFactory
	 *            the editUIProvidersFactory to set
	 */
	public void setEditUIProvidersFactory(EditUIProvidersFactory editUIProvidersFactory) {
		this.editUIProvidersFactory = editUIProvidersFactory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return element instanceof SemanticPropertiesEditingContext && ((SemanticPropertiesEditingContext) element).getEditingEvent().getEventType() == PropertiesEditingEvent.EDIT;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.internal.policies.processors.DirectEditingPolicyProcessor#performEdit(org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext,
	 *      org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	@Override
	protected void performEdit(SemanticPropertiesEditingContext editingContext, EObject eObject, Object value) {
		PropertiesEditingContextFactory editingContextFactory = editingContext.getContextFactoryProvider().getEditingContextFactory((EObject) value);
		PropertiesEditingContext context = editingContextFactory.createPropertiesEditingContext(editingContext, (EObject) value);
		context.getOptions().setBatchMode(true);
		context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
		EEFEditingWizard wizard = new EEFEditingWizard(editingContext.getContextFactoryProvider(), emfServiceProvider, eefEditingServiceProvider, editUIProvidersFactory, context);
		// TODO: use a UI helper for providing the shell
		EEFWizardDialog wDialog = new EEFWizardDialog(new Shell(), wizard);
		int open = wDialog.open();
	}

}
