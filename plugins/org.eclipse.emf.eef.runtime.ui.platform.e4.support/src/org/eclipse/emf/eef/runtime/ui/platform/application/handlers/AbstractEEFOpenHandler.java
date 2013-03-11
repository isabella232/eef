/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.application.handlers;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.platform.application.model.utils.ApplicationModelBuilder;
import org.eclipse.emf.eef.runtime.ui.platform.application.parts.E4EEFPart;
import org.eclipse.emf.eef.runtime.ui.platform.application.utils.EditingInput;
import org.eclipse.emf.eef.runtime.ui.platform.application.utils.impl.URIEditingInput;
import org.eclipse.emf.eef.runtime.ui.platform.application.utils.impl.VoidEditingInput;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractEEFOpenHandler {

	@Execute
	public void execute(IEclipseContext context, EModelService modelService, EPartService partService, EEFServiceRegistry serviceRegistry, MApplication applicationModel, @Named(IServiceConstants.ACTIVE_SHELL) Shell shell) throws InvocationTargetException, InterruptedException {
		EditingInput editingInput = initInput(context, shell);
		context.set(EditingInput.class, editingInput);
		ApplicationModelBuilder builder = new ApplicationModelBuilder(applicationModel);
		builder.addPartDescriptorIfNeeded(ApplicationModelBuilder.EEF_PART_DESCRIPTOR);
		MElementContainer partStack = (MElementContainer) modelService.find(getElementContainerID(), applicationModel);
		preparePartCreation(modelService, applicationModel);
		MPart mPart = partService.createPart(ApplicationModelBuilder.EEF_PART_DESCRIPTOR);
		partStack.getChildren().add(mPart);
		partService.showPart(mPart, PartState.ACTIVATE);
		E4EEFPart partImpl = (E4EEFPart) mPart.getObject();
		partImpl.setInput(editingInput);
		if (editingInput instanceof URIEditingInput) {
			mPart.setLabel(((URIEditingInput) editingInput).getUri().trimFileExtension().lastSegment());
		}
		configureCreatedPart(modelService, applicationModel, mPart);		
	}

	/**
	 * @param context
	 * @param shell
	 * @return 
	 */
	private EditingInput initInput(IEclipseContext context, Shell shell) {
		EditingInput editingInput = null;
		FileDialog dialog = new FileDialog(shell);
		String[] filterExtensions = getFilterExtensions();
		if (filterExtensions != null && filterExtensions.length > 0) {
			dialog.setFilterExtensions(filterExtensions);
		}
		String path = dialog.open();
		if (path != null && !path.isEmpty()) {
			URI fileURI = URI.createFileURI(path);
			AdapterFactoryEditingDomain editingDomain = getOrCreateEditingDomain(context, fileURI);
			Resource resource = editingDomain.getResourceSet().getResource(fileURI, true);
			if (resource == null || resource.getContents().isEmpty()) {
				MessageDialog.openError(shell, "Invalid conference data", "The application what unable to open the conference descriptor file");
				editingInput = new VoidEditingInput();
			} else {				
				editingInput = new URIEditingInput(fileURI, editingDomain);
			}
		} else {
			editingInput = new VoidEditingInput();			
		}
		return editingInput;
	}

	/**
	 * Defines the filter extensions to use in the FileDialog.
	 * @return the filter extensions.
	 */
	protected String[] getFilterExtensions() {
		return null;
	}
	
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

	/**
	 * Gets (and populates if needed) an EditingDomain in the given eclipse context on the given key.
	 * @param context the {@link IEclipseContext} to populate.
	 * @param uri {@link URI} of the edited file.
	 * @return the created editingDomain.
	 */
	public AdapterFactoryEditingDomain getOrCreateEditingDomain(IEclipseContext context, URI uri) {
		EditingInput editingInput = context.get(EditingInput.class);
		AdapterFactoryEditingDomain editingDomain;
		if (editingInput != null && editingInput.getEditingDomain() instanceof AdapterFactoryEditingDomain) {
			editingDomain = (AdapterFactoryEditingDomain) editingInput.getEditingDomain();
		} else {
			editingDomain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());
		}
		return editingDomain;
	}

}
