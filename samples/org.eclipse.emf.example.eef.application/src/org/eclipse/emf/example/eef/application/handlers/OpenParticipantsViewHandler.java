/**
 * 
 */
package org.eclipse.emf.example.eef.application.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.eef.runtime.ui.platform.application.handlers.AbstractEEFOpenViewHandler;
import org.eclipse.emf.eef.runtime.ui.platform.application.utils.EditingInput;
import org.eclipse.emf.example.eef.application.ConferenceApplicationConstants;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class OpenParticipantsViewHandler extends AbstractEEFOpenViewHandler {

	@Inject
	@Named(IServiceConstants.ACTIVE_PART)
	private MPart activePart;
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.application.handlers.AbstractEEFOpenViewHandler#getEditingInput(org.eclipse.e4.core.contexts.IEclipseContext, org.eclipse.e4.ui.model.application.ui.basic.MPart, org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected EditingInput getEditingInput(IEclipseContext context, MPart mPart, Shell shell) {
		return activePart.getContext().get(EditingInput.class);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.application.handlers.AbstractEEFOpenViewHandler#getElementContainerID()
	 */
	@Override
	protected String getElementContainerID() {
		return ConferenceApplicationConstants.ASIDE_PARTSTACK;
	}

}
