/**
 * 
 */
package org.eclipse.emf.example.eef.application.fx.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.platform.e4.handlers.AbstractEEFOpenViewHandler;
import org.eclipse.emf.eef.runtime.ui.platform.e4.services.PlatformRelatedUIUtils;
import org.eclipse.emf.eef.runtime.ui.platform.e4.utils.EditingInput;
import org.eclipse.emf.eef.runtime.ui.platform.e4.utils.impl.EditingContextEditingInput;
import org.eclipse.emf.eef.runtime.ui.platform.e4.utils.impl.URIEditingInput;
import org.eclipse.emf.example.eef.application.fx.ConferenceApplicationConstants;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
@SuppressWarnings("restriction")
public class OpenDetailsViewHandler extends AbstractEEFOpenViewHandler {

	@Inject
	@Named(IServiceConstants.ACTIVE_PART)
	private MPart activePart;
	
	@Inject
	private EEFServiceRegistry serviceRegistry;
	
	private EObject inputObject;
	
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_PART)MPart activePart) {
		return activePart != null;
	}
	
	/**
	 * @param inputObject the inputObject to set
	 */
	public void setInputObject(EObject inputObject) {
		this.inputObject = inputObject;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.e4.handlers.AbstractEEFOpenViewHandler#getEditingInput(org.eclipse.e4.core.contexts.IEclipseContext, org.eclipse.e4.ui.model.application.ui.basic.MPart, org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils)
	 */
	@Override
	protected EditingInput getEditingInput(IEclipseContext context, MPart mPart, PlatformRelatedUIUtils uiUtils) {
		EditingInput editingInput = activePart.getContext().get(EditingInput.class);
		if (editingInput instanceof URIEditingInput) {
			EditingDomain editingDomain = editingInput.getEditingDomain();
			URI uri = ((URIEditingInput) editingInput).getUri();
			PropertiesEditingContextFactory contextFactory = serviceRegistry.getService(PropertiesEditingContextFactory.class, inputObject);
			//TODO: is the ED always an AFED ?
			PropertiesEditingContext editingContext = contextFactory.createPropertiesEditingContext((AdapterFactoryEditingDomain)editingDomain, inputObject);
			return new EditingContextEditingInput(uri, (DomainAwarePropertiesEditingContext) editingContext);
		} 
		return editingInput;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.e4.handlers.AbstractEEFOpenViewHandler#getElementContainerID()
	 */
	@Override
	protected String getElementContainerID() {
		return ConferenceApplicationConstants.MAIN_PARTSTACK;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.e4.handlers.AbstractEEFOpenHandler#configureCreatedPart(org.eclipse.e4.ui.workbench.modeling.EModelService, org.eclipse.e4.ui.model.application.MApplication, org.eclipse.e4.ui.model.application.ui.basic.MPart)
	 */
	@Override
	protected void configureCreatedPart(EModelService modelService, MApplication applicationModel, MPart mPart) {
		EditingInput editingInput = activePart.getContext().get(EditingInput.class);
		if (editingInput.getEditingDomain() instanceof AdapterFactoryEditingDomain) {
			IItemLabelProvider labelProvider = (IItemLabelProvider) ((AdapterFactoryEditingDomain)editingInput.getEditingDomain()).getAdapterFactory().adapt(inputObject, IItemLabelProvider.class);
			mPart.setLabel(labelProvider.getText(inputObject));
		}
		mPart.setLabel("Details");
	}
}
