/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers;

import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditor.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditorProvider {
	
	/**
	 * Defines if the current builder can handle the given {@link ElementEditor}.
	 * @param view {@link PropertiesEditingView} to process.
	 * @param editor {@link ElementEditor} to process.
	 * @return <code>true</code> if the current {@link PropertyEditor} can handle the given {@link ElementEditor}.
	 */
	boolean canHandle(PropertiesEditingView view, ElementEditor editor);
	
	/**
	 * Return the PropertyEditor for this ElementEditor
	 * @param view {@link PropertiesEditingView} to process.
	 * @param editor {@link ElementEditor} to process.
	 * @return the {@link PropertyEditor} for the given {@link ElementEditor}. 
	 */
	PropertyEditor getPropertyEditor(PropertiesEditingView view, ElementEditor editor);

}
