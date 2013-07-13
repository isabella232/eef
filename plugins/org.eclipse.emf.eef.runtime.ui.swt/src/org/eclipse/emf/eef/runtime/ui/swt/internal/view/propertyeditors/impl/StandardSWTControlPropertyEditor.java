/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl;

import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class StandardSWTControlPropertyEditor<CONTROL extends Control> extends StandardSWTPropertyEditor<EEFControlWrapperViewer<CONTROL>> {

	private EEFControlWrapperViewer<CONTROL> wrapperViewer;

	/**
	 * @param view
	 * @param viewElement
	 */
	public StandardSWTControlPropertyEditor(PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
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
				 * @see org.eclipse.emf.eef.runtime.ui.propertyeditors.util.EEFControlWrapperViewer#getMainControl()
				 */
				public CONTROL getMainControl() {
					return StandardSWTControlPropertyEditor.this.getMainControl();
				}
			};
		}
		return wrapperViewer;
	}

	/**
	 * @return the main {@link Control} of the {@link PropertyEditor}.
	 */
	public abstract CONTROL getMainControl(); 
	
}
