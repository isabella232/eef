/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.emfpropertiestoolkit;

import org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor.EComboPlatformAwarePropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.emfpropertiestoolkit.econtainmenteditor.EContainmentPlatformAwarePropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor.EReferencePlaftormAwarePropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFPropertiesPlatformAwareToolkit extends EMFPropertiesToolkit {

	/**
	 * TODO: Ugly pattern ... need a redesign.
	 * If we don't clear the editorProviders instanciated by the default constructor, the PlatformAware editorProviders
	 * aren't use since the editorProvider selection algorithm (method 
	 *  org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider.getPropertyEditor(PropertyEditorContext)) 
	 *  don't have an ordering system between the different editorProviders.
	 */
	public EMFPropertiesPlatformAwareToolkit() {
		clearEditorProviders();
		addPropertyEditorProvider(new EReferencePlaftormAwarePropertyEditorProvider())
		.addPropertyEditorProvider(new EComboPlatformAwarePropertyEditorProvider())
		.addPropertyEditorProvider(new EContainmentPlatformAwarePropertyEditorProvider());
	}
	
	

}
