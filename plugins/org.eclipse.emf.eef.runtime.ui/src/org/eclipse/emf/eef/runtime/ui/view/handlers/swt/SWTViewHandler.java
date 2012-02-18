/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.swt;

import org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandler;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTViewHandler extends ReflectViewHandler<Composite> {

	/**
	 * @param viewClass
	 */
	public SWTViewHandler(Class<? extends Composite> viewClass) {
		super(viewClass);
	}	
	
}
