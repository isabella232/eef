/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.singlecontainmenteditor;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class SingleContainmentPropertyEditorFactory extends WidgetPropertyEditorFactoryImpl<Composite> {

	protected final EMFPropertiesToolkit toolkit;
	
	/**
	 * @param toolkit
	 */
	public SingleContainmentPropertyEditorFactory(EMFPropertiesToolkit emfPropertiesToolkit) {
		this.toolkit = emfPropertiesToolkit;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#serviceFor(org.eclipse.emf.eef.runtime.ui.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return super.serviceFor(editorContext) && editorContext.view.getContents() instanceof Composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl#createPropertyEditor(org.eclipse.emf.eef.runtime.ui.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	@SuppressWarnings("unchecked")
	protected PropertyEditor createPropertyEditor(PropertyEditorContext editorContext) {
		return new SingleContainmentPropertyEditor(
				toolkit.getEEFEditingServiceProvider(),
				(PropertiesEditingView<Composite>) editorContext.view, 
				(ElementEditor) editorContext.viewElement, 
				new SingleContainmentSWTPropertyEditor(
						toolkit.getEditUIProvidersFactory(), 
						toolkit.getImageManager(), 
						(PropertiesEditingView<Composite>) editorContext.view,
						(ElementEditor) editorContext.viewElement
					)
			);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#canHandle(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean canHandle(EStructuralFeature feature) {
		return !feature.isMany() && feature instanceof EReference && ((EReference) feature).isContainment();
	}
}
