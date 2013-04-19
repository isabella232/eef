/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.command.AbortExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class BatchWizardEditingPolicyProcessor extends AbstractWizardEditingPolicyProcessor {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return element instanceof SemanticPropertiesEditingContext 
					&& element instanceof DomainAwarePropertiesEditingContext
					&& ((SemanticPropertiesEditingContext)element).getEditingEvent().getEventType() == PropertiesEditingEvent.EDIT
					&& !element.getOptions().liveMode();
	}


	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.AbstractWizardEditingPolicyProcessor#executeCommand(org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext, org.eclipse.emf.common.command.Command)
	 */
	protected void executeCommand(DomainAwarePropertiesEditingContext editingContext, Command command) {
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

	/**
	 * Handles an exception thrown during command execution by logging it with the plugin.
	 */
	protected void handleError(Exception exception) {
		//TODO: remove dependency to CommonPlugin
		CommonPlugin.INSTANCE.log(new WrappedException(CommonPlugin.INSTANCE.getString("_UI_IgnoreException_exception"), exception).fillInStackTrace());
	}
}
