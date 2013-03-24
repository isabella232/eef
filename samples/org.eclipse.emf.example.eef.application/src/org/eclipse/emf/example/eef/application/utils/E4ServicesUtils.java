/**
 * 
 */
package org.eclipse.emf.example.eef.application.utils;

import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.Activator;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.platform.e4.E4EEFSupportConstants;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class E4ServicesUtils {

	/**
	 * @param editingContext
	 * @return
	 */
	public static IEclipseContext getBestContext(PropertiesEditingContext editingContext) {
		MPart mPart = editingContext.getOptions().getOption(E4EEFSupportConstants.MODELPART_OPTION_KEY);
		if (mPart != null) {
			return mPart.getContext();
		} else {
			return EclipseContextFactory.getServiceContext(Activator.getDefault().getContext());
		}
	}
	
}
