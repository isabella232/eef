/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view;

import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class PropertyEditorBuilder {
	
	protected PropertiesEditingView view;
	protected ElementEditor elementEditor;
	
	
	/**
	 * @param view {@link PropertiesEditingView} where the PropertyEditor is built.
	 */
	public PropertyEditorBuilder(PropertiesEditingView view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
	}

	/**
	 * Build the PropertyEditor in the given composite.
	 * @param parent {@link Composite} where to build the PropertyEditor.
	 */
	public void build(Composite parent) {
		view.getViewHelper().createLabel(parent, elementEditor, elementEditor.getName());
		createEditorContents(parent);
		view.getViewHelper().createHelpButton(parent, elementEditor);
	}

	/**
	 * Create the contents of the property editor in the owning Composite.
	 * @param parent the owning {@link Composite}.
	 */
	protected abstract void createEditorContents(Composite parent);
	
}
