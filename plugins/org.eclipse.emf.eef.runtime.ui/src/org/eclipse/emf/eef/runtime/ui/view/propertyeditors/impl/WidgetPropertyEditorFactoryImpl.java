/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class WidgetPropertyEditorFactoryImpl<T> implements WidgetPropertyEditorFactory<T> {

	private EEFToolkitImpl<T> toolkit;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#setToolkit(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.EEFToolkitImpl)
	 */
	public final void setToolkit(EEFToolkitImpl<T> toolkit) {
		this.toolkit = toolkit;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorFactory#firePropertiesChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public final void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		toolkit.firePropertiesChanged(editingComponent, editingEvent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public final PropertyEditor getPropertyEditor(PropertyEditorContext editorContext) {
		PropertyEditor propertyEditor = createPropertyEditor(editorContext);
		if (propertyEditor instanceof PropertyEditorImpl) {
			((PropertyEditorImpl) propertyEditor).setPropertyEditorFactory(this);
		}
		return propertyEditor;
	}
	
	protected abstract PropertyEditor createPropertyEditor(PropertyEditorContext editorContext);

}
