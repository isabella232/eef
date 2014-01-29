
/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.singlecontainmenteditor;

import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.singlecontainmenteditor.SingleContainmentPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.singlecontainmenteditor.SingleContainmentPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SingleContainmentPlatformAwarePropertyEditorFactory extends SingleContainmentPropertyEditorFactory {

	/**
	 * @param toolkit
	 */
	public SingleContainmentPlatformAwarePropertyEditorFactory(EMFPropertiesToolkit emfPropertiesToolkit) {
		super(emfPropertiesToolkit);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor.EComboPropertyEditorFactory#createPropertyEditor(org.eclipse.emf.eef.runtime.ui.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	@SuppressWarnings("unchecked")
	protected PropertyEditor createPropertyEditor(PropertyEditorContext editorContext) {
		FormToolkit formtoolkit = editorContext.view.getEditingComponent().getEditingContext().getOptions().getOption(EEFSWTConstants.FORM_TOOLKIT);
		if (formtoolkit != null) {
			return new SingleContainmentPropertyEditor(
					toolkit.getEEFEditingServiceProvider(),
					(PropertiesEditingView<Composite>) editorContext.view, 
					(ElementEditor) editorContext.viewElement, 
					new SingleContainmentFormPropertyEditor(
							toolkit.getEditUIProvidersFactory(), 
							toolkit.getImageManager(), 
							(PropertiesEditingView<Composite>) editorContext.view, 
							(ElementEditor) editorContext.viewElement
						)
				);
		} else {
			return super.createPropertyEditor(editorContext);
		}
	}

}
