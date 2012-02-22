/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;

import org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors.impl.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Control;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class StandardFormControlPropertyEditor<CONTROL extends Control> extends StandardFormPropertyEditor<EEFControlWrapperViewer<CONTROL>> {

	/**
	 * @param view
	 * @param elementEditor
	 */
	public StandardFormControlPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor) {
		super(view, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public EEFControlWrapperViewer<CONTROL> getViewer() {
		return new EEFControlWrapperViewer<CONTROL>() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors.impl.util.EEFControlWrapperViewer#getMainControl()
			 */
			public CONTROL getMainControl() {
				return StandardFormControlPropertyEditor.this.getMainControl();
			}
			
			
		};
	}

	/**
	 * @return the main {@link Control} of this {@link PropertyEditor}.
	 */
	public abstract CONTROL getMainControl();
	
}
