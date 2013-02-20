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
	 * 
	 */
	public EMFPropertiesPlatformAwareToolkit() {
		addPropertyEditorProvider(new EReferencePlaftormAwarePropertyEditorProvider())
		.addPropertyEditorProvider(new EComboPlatformAwarePropertyEditorProvider())
		.addPropertyEditorProvider(new EContainmentPlatformAwarePropertyEditorProvider());
	}
	
	

}
