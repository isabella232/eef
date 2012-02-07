/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.emfpropertiestoolkit.referencetable;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.StandardPropertyEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ReferenceTablePropertyEditor extends StandardPropertyEditor {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.StandardPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createEditorContents(Composite parent) {
		String label = getDescription(EefnrViewsRepository.AdvancedReferencesTableSample.Properties.advancedreferencestableRequiredProperty, EefnrMessages.AdvancedReferencesTableSamplePropertiesEditionPart_AdvancedreferencestableRequiredPropertyLabel);		 
		this.advancedreferencestableRequiredProperty = new ReferencesTable(label, new ReferencesTableListener() {
			public void handleAdd() { addAdvancedreferencestableRequiredProperty(); }
			public void handleEdit(EObject element) { editAdvancedreferencestableRequiredProperty(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveAdvancedreferencestableRequiredProperty(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromAdvancedreferencestableRequiredProperty(element); }
			public void navigateTo(EObject element) { }
		});
		this.advancedreferencestableRequiredProperty.setHelpText(propertiesEditionComponent.getHelpContent(EefnrViewsRepository.AdvancedReferencesTableSample.Properties.advancedreferencestableRequiredProperty, EefnrViewsRepository.SWT_KIND));
		this.advancedreferencestableRequiredProperty.createControls(parent);
		this.advancedreferencestableRequiredProperty.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AdvancedReferencesTableSamplePropertiesEditionPartImpl.this, EefnrViewsRepository.AdvancedReferencesTableSample.Properties.advancedreferencestableRequiredProperty, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData advancedreferencestableRequiredPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		advancedreferencestableRequiredPropertyData.horizontalSpan = 3;
		this.advancedreferencestableRequiredProperty.setLayoutData(advancedreferencestableRequiredPropertyData);
		this.advancedreferencestableRequiredProperty.disableMove();
		advancedreferencestableRequiredProperty.setID(EefnrViewsRepository.AdvancedReferencesTableSample.Properties.advancedreferencestableRequiredProperty);
		advancedreferencestableRequiredProperty.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		return parent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void init(EStructuralFeature feature) {
		// TODO Auto-generated method stub

	}

}
