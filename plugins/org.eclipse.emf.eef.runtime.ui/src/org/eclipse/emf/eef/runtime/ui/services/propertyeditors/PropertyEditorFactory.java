/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.services.propertyeditors;

import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.ViewElement;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditorFactory<T> {
	
	/**
	 * Return the PropertyEditor for this ElementEditor
	 * @param editorContext {@link PropertyEditorContext} to process.
	 * @return the {@link PropertyEditor} for the given {@link ElementEditor}. 
	 */
	PropertyEditor getPropertyEditor(PropertyEditorContext editorContext);
	
	/**
	 * Represents a context for a {@link PropertyEditor} composed of:
	 * 	- a View
	 * 	- a ElementEditor description
	 *  
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 */
	public final class PropertyEditorContext {
		
		public PropertiesEditingView<?> view;
		public ViewElement viewElement;
		
		/**
		 * @param view
		 * @param viewElement
		 */
		public PropertyEditorContext(PropertiesEditingView<?> view, ViewElement viewElement) {
			this.view = view;
			this.viewElement = viewElement;
		}
		
		
		
	}

}
