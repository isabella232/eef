/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider.PropertyEditorContext;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditorProvider extends EEFService<PropertyEditorContext> {
	
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
		
		public PropertiesEditingView view;
		public ElementEditor elementEditor;
		
		/**
		 * @param view
		 * @param elementEditor
		 */
		public PropertyEditorContext(PropertiesEditingView view, ElementEditor elementEditor) {
			this.view = view;
			this.elementEditor = elementEditor;
		}
		
		
		
	}

}
