/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.notify;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.ui.commands.WizardEditingCommand;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class OpenWizardOnDoubleClick implements IDoubleClickListener {

	private EditingDomain domain;
	private AdapterFactory adapterFactory;
	private PropertiesEditingContextFactory editingContextFactory;
	
	
	
	/**
	 * @param factory the factory to set
	 */
	public void setEditingContextFactory(PropertiesEditingContextFactory factory) {
		this.editingContextFactory = factory;
	}



	/**
	 * @param domain {@link EditingDomain} to use for editing actions.
	 * @param adapterFactory {@link AdapterFactory} to use for element editing.
	 */
	public OpenWizardOnDoubleClick(EditingDomain domain, AdapterFactory adapterFactory) {
		this.domain = domain;
		this.adapterFactory = adapterFactory;
	}



	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
	 */
	public void doubleClick(DoubleClickEvent event) {
		StructuredSelection selection = (StructuredSelection) event.getSelection();
		PropertiesEditingContext context;
		if (selection.getFirstElement() instanceof EObject) {
			context = editingContextFactory.createPropertiesEditingContext(domain, adapterFactory, (EObject) selection.getFirstElement());
			context.getOptions().setBatchMode(true);
			WizardEditingCommand wizardEditingCommand = new WizardEditingCommand(context);
			domain.getCommandStack().execute(wizardEditingCommand);
			context.dispose();			
		}
	}


}
