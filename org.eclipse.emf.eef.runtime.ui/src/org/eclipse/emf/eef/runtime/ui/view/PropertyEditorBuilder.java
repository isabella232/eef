/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view;

import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class PropertyEditorBuilder {
	
	private PropertiesEditingView view;
	
	private Object editorID;
	
	
	/**
	 * @param view {@link PropertiesEditingView} where the PropertyEditor is built.
	 */
	public PropertyEditorBuilder(PropertiesEditingView view) {
		this.view = view;
	}

	/**
	 * Build the PropertyEditor in the given composite.
	 * @param parent {@link Composite} where to build the PropertyEditor.
	 */
	public void build(Composite parent) {
		
	}
	
	/**
	 * Defines the ID of the PropertyEditor.
	 * @param editorID ID of the PropertyEditor.
	 * @return this {@link PropertyEditorBuilder}.
	 */
	public PropertyEditorBuilder editorID(Object editorID) {
		this.editorID = editorID;
		return this;
	}

}
