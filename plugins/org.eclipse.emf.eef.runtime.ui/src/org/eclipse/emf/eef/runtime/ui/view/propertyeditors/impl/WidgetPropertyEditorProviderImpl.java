/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class WidgetPropertyEditorProviderImpl<T> implements WidgetPropertyEditorProvider<T> {

	private ToolkitPropertyEditorProvider<T> toolkit;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorProvider#setToolkit(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider)
	 */
	public final void setToolkit(ToolkitPropertyEditorProvider<T> toolkit) {
		this.toolkit = toolkit;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorProvider#firePropertiesChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public final void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		toolkit.firePropertiesChanged(editingComponent, editingEvent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 */
	public final PropertyEditor getPropertyEditor(PropertyEditorContext<T> editorContext) {
		PropertyEditor propertyEditor = createPropertyEditor(editorContext);
		if (propertyEditor instanceof PropertyEditorImpl) {
			((PropertyEditorImpl) propertyEditor).setPropertyEditorProvider(this);
		}
		return propertyEditor;
	}
	
	protected abstract PropertyEditor createPropertyEditor(PropertyEditorContext<T> editorContext);

}
