/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.wizard;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.notify.PropertiesValidationEditingEvent;
import org.eclipse.emf.eef.runtime.ui.internal.view.util.PropertiesEditingMessageManager;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFViewer;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFEditingWizard extends Wizard {

	private PropertiesEditingContext context;
	private PropertiesEditingWizardPage editingPage;
	private PropertiesEditingMessageManager messageManager;

	/**
	 * @param context {@link PropertiesEditingContext} to use in this wizard.
	 */
	public EEFEditingWizard(PropertiesEditingContext context) {
		this.context = context;
		this.setWindowTitle(context.getEditingComponent().getEObject().eClass().getName());
		initMessageManager();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void addPages() {
		editingPage = new PropertiesEditingWizardPage();
		editingPage.setInput(context);
		addPage(editingPage);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		context.stopEditing();
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	public boolean performCancel() {
		context.cancelEditing();
		return true;
	}

	private void initMessageManager() {
		messageManager = new PropertiesEditingMessageManager() {
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.internal.view.util.PropertiesEditingMessageManager#updateStatus(java.lang.String)
			 */
			@Override
			protected void updateStatus(String message) {
				if (editingPage != null) {
					editingPage.setMessage(message, IMessageProvider.NONE);
				}
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.internal.view.util.PropertiesEditingMessageManager#updateError(java.lang.String)
			 */
			@Override
			protected void updateError(String message) {
				if (editingPage != null) {
					editingPage.setMessage(message, IMessageProvider.ERROR);
				}
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.internal.view.util.PropertiesEditingMessageManager#updateWarning(java.lang.String)
			 */
			@Override
			protected void updateWarning(String message) {
				if (editingPage != null) {
					editingPage.setMessage(message, IMessageProvider.WARNING);
				}
			}
			
			
		};
	}

	private class PropertiesEditingWizardPage extends WizardPage {

		private EEFViewer viewer;
		private PropertiesEditingContext context;

		/**
		 * @param pageName
		 */
		public PropertiesEditingWizardPage() {
			super("Properties Editing");
			setDescription("Edit the object properties.");
		}

		/**
		 * @param context the context to use.
		 */
		public void setInput(PropertiesEditingContext context) {
			this.context = context;
			setTitle(context.getEditingComponent().getEObject().eClass().getName());
			if (messageManager != null) {
				messageManager.processMessage(new PropertiesValidationEditingEvent(null, Diagnostic.OK_INSTANCE));
				context.getEditingComponent().addEditingListener(new PropertiesEditingListener() {

					public void firePropertiesChanged(PropertiesEditingEvent event) {
						messageManager.processMessage(event);
					}
				});
			}
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			Composite control = new Composite(parent, SWT.NONE);
			control.setLayoutData(new GridData(GridData.FILL_BOTH));
			control.setLayout(new GridLayout(1, false));
			viewer = new EEFViewer(control, SWT.NONE);
			viewer.setContentProvider(new EEFContentProvider());
			viewer.setInput(context);
			this.setControl(control);
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
		 */
		@Override
		public void dispose() {
			super.dispose();
			viewer.clear();
		}

	}	

}
