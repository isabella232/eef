/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit;

import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor.EComboPlatformAwarePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.econtainmenteditor.EContainmentPlatformAwarePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.edatepickereditor.EDatePickerPlatformAwarePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor.EReferencePlaftormAwarePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFPropertiesPlatformAwareToolkit extends EMFPropertiesToolkit {

	/**
	 * TODO: Ugly pattern ... need a redesign.
	 * If we don't clear the editorProviders instanciated by the default constructor, the PlatformAware editorProviders
	 * aren't use since the editorProvider selection algorithm (method 
	 *  org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.EEFToolkitImpl.getPropertyEditor(PropertyEditorContext)) 
	 *  don't have an ordering system between the different editorProviders.
	 */
	public EMFPropertiesPlatformAwareToolkit() {
		clearEditorProviders();
		addPropertyEditorFactory(new EReferencePlaftormAwarePropertyEditorFactory(this))
		.addPropertyEditorFactory(new EComboPlatformAwarePropertyEditorFactory(this))
		.addPropertyEditorFactory(new EContainmentPlatformAwarePropertyEditorFactory(this))
		.addPropertyEditorFactory(new EDatePickerPlatformAwarePropertyEditorFactory(this));
	}
	
	

}
