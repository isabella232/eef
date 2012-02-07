/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.cases;

import static org.junit.Assert.fail;

import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingViewEditingTestCase extends UIEditingTestCase {

	/**
	 * @param index index of the view to return.
	 * @return the asked view.s
	 */
	protected Composite getView(int index) {
		return (Composite) ((Composite) display.getShells()[0].getChildren()[0]).getChildren()[index ];
	}
	
	/**
	 * Search the control at the given index and cast it in the given type.
	 * @param view {@link Composite} to process.
	 * @param index index of the composite.
	 * @return the control at the given index.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getControl(Composite view, int index) {
		return (T) view.getChildren()[index];
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase#buildView(org.eclipse.swt.widgets.Composite, org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler, int)
	 */
	protected Composite buildView(Composite composite, ViewHandler<?> handler, int index) {
		Composite view = null;
		if (handler instanceof PropertiesEditingViewHandler) {
			try {
				view = ((PropertiesEditingViewHandler)handler).createView(component, composite).getContents();
				handler.initView(component);
			} catch (ViewConstructionException e) {
				fail("An error occured during view creation");
			}
		} else {
			fail("Unable to build the view.");
		}
		return view;
	}

}
