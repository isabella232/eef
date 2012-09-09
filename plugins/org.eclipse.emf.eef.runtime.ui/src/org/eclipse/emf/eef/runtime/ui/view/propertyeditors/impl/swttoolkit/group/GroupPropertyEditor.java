/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.group;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.ui.internal.services.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class GroupPropertyEditor implements PropertyEditor {

	private PropertyEditorViewer<EEFControlWrapperViewer<Composite>> propertyEditorViewer;
	
	/**
	 * @param propertyEditorViewer the {@link PropertyEditorViewer} of this editor.
	 */
	public GroupPropertyEditor(PropertyEditorViewer<EEFControlWrapperViewer<Composite>> propertyEditorViewer) {
		this.propertyEditorViewer = propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void init(EStructuralFeature feature) {
		//Nothing to do.
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#getPropertyEditorViewer()
	 */
	public PropertyEditorViewer<?> getPropertyEditorViewer() {
		return propertyEditorViewer;
	}

}
