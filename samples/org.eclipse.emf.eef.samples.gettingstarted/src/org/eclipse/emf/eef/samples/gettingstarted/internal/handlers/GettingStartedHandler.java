package org.eclipse.emf.eef.samples.gettingstarted.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.eef.samples.gettingstarted.internal.dialogs.GettingStartedDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class GettingStartedHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public GettingStartedHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		GettingStartedDialog dialog = new GettingStartedDialog(window.getShell());
		dialog.open();
		return null;
	}
}
