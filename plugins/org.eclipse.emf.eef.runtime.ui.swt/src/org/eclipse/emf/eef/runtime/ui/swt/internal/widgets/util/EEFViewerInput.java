/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFViewerInput {

	private PropertiesEditingContext editingContext;
	
	private EObject editedObject;
	
	private EStructuralFeature editedFeature;

	/**
	 * @param editedObject
	 * @param editedFeature
	 */
	public EEFViewerInput(EObject editedObject, EStructuralFeature editedFeature) {
		this.editedObject = editedObject;
		this.editedFeature = editedFeature;
	}

	/**
	 * @param editingContext
	 * @param editedFeature
	 */
	public EEFViewerInput(PropertiesEditingContext editingContext, EStructuralFeature editedFeature) {
		this.editingContext = editingContext;
		this.editedFeature = editedFeature;
	}

	/**
	 * @param editingContext
	 * @param editedObject
	 * @param editedFeature
	 */
	public EEFViewerInput(PropertiesEditingContext editingContext, EObject editedObject, EStructuralFeature editedFeature) {
		this.editingContext = editingContext;
		this.editedObject = editedObject;
		this.editedFeature = editedFeature;
	}

	/**
	 * @return the editingContext
	 */
	public PropertiesEditingContext getEditingContext() {
		return editingContext;
	}

	/**
	 * @return the editedObject
	 */
	public EObject getEditedObject() {
		if (editedObject != null) {
			return editedObject;
		} else if (editingContext != null) {
			return editingContext.getEditingComponent().getEObject();
		} else {
			return null;
		}
 	}

	/**
	 * @return the editedFeature
	 */
	public EStructuralFeature getEditedFeature() {
		return editedFeature;
	}
	
}
