/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.command.AbortExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class BatchSemanticDomainEditingPolicy extends SemanticDomainEditingPolicy {

	/**
	 * @param editingDomain
	 * @param editingComponent
	 * @param editingEvent
	 */
	public BatchSemanticDomainEditingPolicy(EditingDomain editingDomain, PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		super(editingDomain, editingComponent, editingEvent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticDomainEditingPolicy#processCommand(org.eclipse.emf.common.command.Command)
	 */
	@Override
	protected void processCommand(Command command) {
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
