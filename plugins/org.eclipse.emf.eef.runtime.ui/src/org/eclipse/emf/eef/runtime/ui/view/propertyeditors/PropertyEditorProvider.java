/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditorProvider {
	
	/**
	 * Defines if the current builder can handle the given {@link ElementEditor}.
	 * @param view {@link PropertiesEditingView} to process.
	 * @param elementEditor {@link ElementEditor} to process.
	 * @return <code>true</code> if the current {@link PropertyEditor} can handle the given {@link ElementEditor}.
	 */
	boolean canHandle(PropertiesEditingView view, ElementEditor elementEditor);
	
	/**
	 * Return the PropertyEditor for this ElementEditor
	 * @param view {@link PropertiesEditingView} to process.
	 * @param elementEditor {@link ElementEditor} to process.
	 * @return the {@link PropertyEditor} for the given {@link ElementEditor}. 
	 */
	PropertyEditor getPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor);

}
