/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.econtainmenteditor;

import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorProviderImpl;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EContainmentPropertyEditorProvider extends WidgetPropertyEditorProviderImpl<Composite> {

	private static final Widget widget = ToolkitsFactory.eINSTANCE.createWidget();
	
	static {
		widget.setName("EContainementEditor");
	}
	
	protected final EMFPropertiesToolkit emfPropertiesToolkit;
	
	/**
	 * @param emfPropertiesToolkit
	 */
	public EContainmentPropertyEditorProvider(EMFPropertiesToolkit emfPropertiesToolkit) {
		this.emfPropertiesToolkit = emfPropertiesToolkit;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorProvider#getModel()
	 */
	public Widget getModel() {
		return widget;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider#serviceFor(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return getModel() == editorContext.viewElement.getRepresentation() && editorContext.view.getContents() instanceof Composite;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorProviderImpl#createPropertyEditor(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 */
	@SuppressWarnings("unchecked")
	protected PropertyEditor createPropertyEditor(PropertyEditorContext editorContext) {
		return new EContainmentPropertyEditor(emfPropertiesToolkit.getEditUIProvidersFactory(), (PropertiesEditingView<Composite>) editorContext.view, (ElementEditor) editorContext.viewElement, new EContainmentSWTPropertyEditor((PropertiesEditingView<Composite>) editorContext.view, (ElementEditor) editorContext.viewElement));
	}

}
