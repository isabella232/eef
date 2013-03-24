/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.e4.handlers;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.ui.platform.e4.utils.EditingInput;
import org.eclipse.emf.eef.runtime.ui.platform.e4.utils.impl.URIEditingInput;
import org.eclipse.emf.eef.runtime.ui.platform.e4.utils.impl.VoidEditingInput;
import org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
@SuppressWarnings("restriction")
public abstract class AbstractEEFOpenHandler extends AbstractEEFOpenViewHandler {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.e4.handlers.AbstractEEFOpenViewHandler#getEditingInput(org.eclipse.e4.core.contexts.IEclipseContext, org.eclipse.e4.ui.model.application.ui.basic.MPart, org.eclipse.swt.widgets.Shell)
	 */
	protected EditingInput getEditingInput(IEclipseContext context, MPart mPart, PlatformRelatedUIUtils uiUtils) {
		EditingInput editingInput = null;
		String[] filterExtensions = getFilterExtensions();
		String path = uiUtils.selectModelFile(filterExtensions);
		if (path != null && !path.isEmpty()) {
			URI fileURI = URI.createFileURI(path);
			AdapterFactoryEditingDomain editingDomain = getOrCreateEditingDomain(context, fileURI);
			Resource resource = editingDomain.getResourceSet().getResource(fileURI, true);
			if (resource == null || resource.getContents().isEmpty()) {
				editingInput = new VoidEditingInput();
			} else {				
				editingInput = new URIEditingInput(fileURI, editingDomain);
			}
		} else {
			editingInput = new VoidEditingInput();			
		}
		context.set(EditingInput.class, editingInput);
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
