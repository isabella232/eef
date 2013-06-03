/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.resources;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorImpl;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * TODO: to test
 *
 */
public class ToolkitResource extends ResourceImpl {

	static final String TOOLKIT_RESOURCE_SCHEME = "eeftoolkit"; 
	
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
					BundleContext bundleContext = EEFRuntimeUI.getPlugin().getBundle().getBundleContext();
					toManage = searchBundle(bundleContext,uri.segment(0));
				} 
				if (toManage != null) {
					@SuppressWarnings("unchecked")
					Class<org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorImpl<?>> toolkitClass = (Class<ToolkitPropertyEditorImpl<?>>) toManage.loadClass(uri.lastSegment());
					ToolkitPropertyEditorImpl<?> toolkitProvider = toolkitClass.newInstance();
					Toolkit model = toolkitProvider.getModel();
					getContents().add(model);
				}
			} catch (InstantiationException e) {
				throw new IllegalArgumentException("Cannot instanciate given toolkit", e);
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException("Cannot access toolkit", e);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("Invalid toolkit", e);
			} 
			setLoaded(true);
		}
	}
	
	public Bundle searchBundle(BundleContext context, String symbolicName) {
		for (Bundle bundle : context.getBundles()) {
			if (symbolicName.equals(bundle.getSymbolicName())) {
				return bundle;
			}
		}
		return null;
	}

}
