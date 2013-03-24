/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.e4.handlers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.platform.e4.model.utils.ApplicationModelBuilder;
import org.eclipse.emf.eef.runtime.ui.platform.e4.parts.E4EEFPart;
import org.eclipse.emf.eef.runtime.ui.platform.e4.utils.EditingInput;
import org.eclipse.emf.eef.runtime.ui.platform.e4.utils.impl.URIEditingInput;
import org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
@SuppressWarnings("restriction")
public abstract class AbstractEEFOpenViewHandler {

	@Execute
	public void execute(IEclipseContext context, EModelService modelService, EPartService partService, EEFServiceRegistry serviceRegistry, PlatformRelatedUIUtils uiUtils, MApplication applicationModel) throws InvocationTargetException, InterruptedException {
		ApplicationModelBuilder builder = new ApplicationModelBuilder(applicationModel);
		builder.addPartDescriptorIfNeeded(ApplicationModelBuilder.EEF_PART_DESCRIPTOR);
		MElementContainer partStack = (MElementContainer) modelService.find(getElementContainerID(), applicationModel);
		preparePartCreation(modelService, applicationModel);
		MPart mPart = partService.createPart(ApplicationModelBuilder.EEF_PART_DESCRIPTOR);
		partStack.getChildren().add(mPart);
		partService.showPart(mPart, PartState.ACTIVATE);
		EditingInput editingInput = getEditingInput(context, mPart, uiUtils);
		E4EEFPart partImpl = (E4EEFPart) mPart.getObject();
		partImpl.setInput(editingInput);
		if (editingInput instanceof URIEditingInput) {
			mPart.setLabel(((URIEditingInput) editingInput).getUri().trimFileExtension().lastSegment());
		}
		configureCreatedPart(modelService, applicationModel, mPart);
	}

	/**
	 * @param context
	 * @param mPart
	 * @param uiUtils
	 * @return
	 */
	protected abstract EditingInput getEditingInput(IEclipseContext context, MPart mPart, PlatformRelatedUIUtils uiUtils);

	/**
	 * Defines the element where to open the EEF part
	 * @return the container ID.
	 */
	protected abstract String getElementContainerID();

	/**
	 * Allows sub classes to prepare the part creation.
	 * @param modelService
	 * @param applicationModel 
	 */
	protected void preparePartCreation(EModelService modelService, MApplication applicationModel) {
		// Do nothing
	}

	/**
	 * Allows sub classes to configure the created part.
	 * @param modelService
	 * @param applicationModel 
	 * @param mPart
	 */
	protected void configureCreatedPart(EModelService modelService, MApplication applicationModel, MPart mPart) {
		// Do nothing
	}

}
