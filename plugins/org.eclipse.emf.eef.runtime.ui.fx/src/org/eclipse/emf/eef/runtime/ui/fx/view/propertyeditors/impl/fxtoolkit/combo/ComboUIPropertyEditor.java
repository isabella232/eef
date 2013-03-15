/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.combo;

import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ComboUIPropertyEditor {

	/**
	 * Initializes the input of the combo owned by the propertyEditor.
	 * @param comboContentProviderInput the editing settings.
	 */
	public void initCombo(ComboContentProviderInput comboContentProviderInput);
	
	public final static class ComboContentProvider {
		
		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			Object choiceOfValues = null;
			if (inputElement instanceof ComboContentProviderInput) {
				PropertiesEditingContext contentProviderContext = ((ComboContentProviderInput) inputElement).getEditingContext();
				EMFService service = contentProviderContext.getServiceRegistry().getService(EMFService.class, ((ComboContentProviderInput) inputElement).getEditingContext().getEditingComponent().getEObject().eClass().getEPackage());
				choiceOfValues = service.choiceOfValues(contentProviderContext.getAdapterFactory(), contentProviderContext.getEditingComponent().getEObject(), ((ComboContentProviderInput) inputElement).getFeature());
			} else {
				choiceOfValues = inputElement;
			}
			if (choiceOfValues instanceof EObject) {
				//TODO: Purely arbitrary choice, to validate.
				return ((EObject) choiceOfValues).eContents().toArray();
			}
			if (choiceOfValues instanceof List<?>) {
				return ((List<?>) choiceOfValues).toArray();
			}
			if (choiceOfValues instanceof Object[]) {
				return (Object[]) choiceOfValues;
			} 
			return null;
		}
		
		
		
	}
	
	public static final class ComboContentProviderInput {
		
		private PropertiesEditingContext editingContext;
		private EStructuralFeature feature;
		/**
		 * @param editingContext
		 * @param feature
		 */
		public ComboContentProviderInput(PropertiesEditingContext editingContext, EStructuralFeature feature) {
			this.editingContext = editingContext;
			this.feature = feature;
		}
		/**
		 * @return the editingContext
		 */
		public PropertiesEditingContext getEditingContext() {
			return editingContext;
		}
		/**
		 * @return the feature
		 */
		public EStructuralFeature getFeature() {
			return feature;
		}
		
	}

}
