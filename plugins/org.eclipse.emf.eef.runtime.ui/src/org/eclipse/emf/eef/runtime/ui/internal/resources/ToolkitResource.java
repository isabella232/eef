/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * TODO: to test
 *
 */
public class ToolkitResource extends ResourceImpl {

	/**
	 * 
	 */
	public ToolkitResource() {
		super();
	}

	/**
	 * @param uri Resource {@link URI}.
	 */
	public ToolkitResource(URI uri) {
		super(uri);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceImpl#load(java.util.Map)
	 */
	@Override
	public void load(Map<?, ?> options) throws IOException {
		if (!isLoaded) {
			URIConverter uriConverter = getURIConverter();
			URI tmpUri = uriConverter.normalize(uri);
			if (tmpUri != null) {
				uri = tmpUri;
			}
			try {
				Bundle toManage = EEFRuntimeUI.getPlugin().getBundle();
				if (!EEFRuntimeUI.PLUGIN_ID.equals(uri.segment(0))) {
					toManage = EEFRuntimeUI.getPlugin().getBundle().getBundleContext().installBundle(uri.segment(0));
				} 
				@SuppressWarnings("unchecked")
				Class<org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider> toolkitClass = (Class<ToolkitPropertyEditorProvider>) toManage.loadClass(uri.lastSegment());
				ToolkitPropertyEditorProvider toolkitProvider = toolkitClass.newInstance();
				Toolkit model = toolkitProvider.getModel();
				getContents().add(model);
			} catch (InstantiationException e) {
				throw new IllegalArgumentException("Cannot instanciate given toolkit", e);
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException("Cannot access toolkit", e);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("Invalid toolkit", e);
			} catch (BundleException e) {
				throw new IllegalArgumentException("Unable to load the bundle " + uri.segment(1), e);
			}
			setLoaded(true);
		}
	}

}
