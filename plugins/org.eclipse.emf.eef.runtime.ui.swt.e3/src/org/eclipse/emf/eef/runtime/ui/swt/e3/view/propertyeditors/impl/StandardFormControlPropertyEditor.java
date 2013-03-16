/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.impl;

import org.eclipse.emf.eef.runtime.ui.swt.internal.services.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class StandardFormControlPropertyEditor<CONTROL extends Control> extends StandardFormPropertyEditor<EEFControlWrapperViewer<CONTROL>> {

	private EEFControlWrapperViewer<CONTROL> wrapperViewer;

	/**
	 * @param view
	 * @param viewElement
	 */
	public StandardFormControlPropertyEditor(PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		super(view, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public EEFControlWrapperViewer<CONTROL> getViewer() {
		if (wrapperViewer == null) {
			wrapperViewer = new EEFControlWrapperViewer<CONTROL>() {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.util.EEFControlWrapperViewer#getMainControl()
				 */
				public CONTROL getMainControl() {
					return StandardFormControlPropertyEditor.this.getMainControl();
				}


			};
		}
		return wrapperViewer;
	}

	/**
	 * @return the main {@link Control} of this {@link PropertyEditor}.
	 */
	public abstract CONTROL getMainControl();
	
}
