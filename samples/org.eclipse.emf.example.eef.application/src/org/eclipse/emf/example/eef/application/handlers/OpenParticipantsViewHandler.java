/**
 * 
 */
package org.eclipse.emf.example.eef.application.handlers;

import java.util.ArrayList;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.eef.runtime.ui.platform.e4.handlers.AbstractEEFOpenViewHandler;
import org.eclipse.emf.eef.runtime.ui.platform.e4.parts.E4EEFPart;
import org.eclipse.emf.eef.runtime.ui.platform.e4.utils.EditingInput;
import org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.ViewFilter;
import org.eclipse.emf.example.eef.application.ConferenceApplicationConstants;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
@SuppressWarnings("restriction")
public class OpenParticipantsViewHandler extends AbstractEEFOpenViewHandler {

	@Inject
	@Named(IServiceConstants.ACTIVE_PART)
	private MPart activePart;
	
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_PART)MPart activePart) {
		return activePart != null;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.e4.handlers.AbstractEEFOpenViewHandler#getEditingInput(org.eclipse.e4.core.contexts.IEclipseContext, org.eclipse.e4.ui.model.application.ui.basic.MPart, org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils)
	 */
	@Override
	protected EditingInput getEditingInput(IEclipseContext context, MPart mPart, PlatformRelatedUIUtils uiUtils) {
		return activePart.getContext().get(EditingInput.class);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.e4.handlers.AbstractEEFOpenViewHandler#getElementContainerID()
	 */
	@Override
	protected String getElementContainerID() {
		return ConferenceApplicationConstants.ASIDE_PARTSTACK;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.e4.handlers.AbstractEEFOpenHandler#configureCreatedPart(org.eclipse.e4.ui.workbench.modeling.EModelService, org.eclipse.e4.ui.model.application.MApplication, org.eclipse.e4.ui.model.application.ui.basic.MPart)
	 */
	@Override
	protected void configureCreatedPart(EModelService modelService, MApplication applicationModel, MPart mPart) {
		E4EEFPart partImpl = (E4EEFPart) mPart.getObject();
		Collection<String> selectedViews = new ArrayList<String>();
		selectedViews.add("Conference::Participants");
		partImpl.addFilter(new ViewFilter(selectedViews));
		mPart.setLabel("Participants");
	}

}
