/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.wizard;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.viewer.EEFViewer;
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

	/**
	 * @param context {@link PropertiesEditingContext} to use in this wizard.
	 */
	public EEFEditingWizard(PropertiesEditingContext context) {
		this.context = context;
		this.setWindowTitle(((EObject)context.getEditingComponent().getTarget()).eClass().getName());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void addPages() {
		PropertiesEditingWizardPage editingPage = new PropertiesEditingWizardPage();
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
			setTitle(((EObject)context.getEditingComponent().getTarget()).eClass().getName());
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
