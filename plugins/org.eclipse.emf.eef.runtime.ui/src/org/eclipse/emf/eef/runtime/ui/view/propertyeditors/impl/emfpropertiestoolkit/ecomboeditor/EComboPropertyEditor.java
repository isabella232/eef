/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.widgets.EEFSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.SingleLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.SingleLinePropertyViewer.SingleLinePropertyViewerListener;
import org.eclipse.emf.eef.runtime.ui.widgets.util.ChoiceOfValuesFilter;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.layout.GridData;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EComboPropertyEditor implements PropertyEditor, MonovaluedPropertyEditor {

	protected PropertiesEditingView view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<SingleLinePropertyViewer> propertyEditorViewer;
	private EStructuralFeature feature;
	private SingleLinePropertyViewerListener listener;

	/**
	 * @param view
	 * @param elementEditor
	 * @param propertyEditorViewer
	 */
	public EComboPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor, PropertyEditorViewer<SingleLinePropertyViewer> propertyEditorViewer) {
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyEditorViewer = propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void init(EStructuralFeature feature) {
		this.feature = feature;
		if (view.getEditingComponent().getEObject().eGet(feature) != null) {
			propertyEditorViewer.getViewer().setInput(view.getEditingComponent().getEObject().eGet(feature));
		}
		initListener();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		propertyEditorViewer.getViewer().getControl().setLayoutData(layoutData);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#getPropertyEditorViewer()
	 */
	public PropertyEditorViewer<?> getPropertyEditorViewer() {
		return propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		propertyEditorViewer.getViewer().setInput(value);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		propertyEditorViewer.getViewer().setInput(null);		
	}

	/**
	 * Initialize the listener on the EReferenceEditor.
	 */
	protected void initListener() {
		if (listener == null) {
			listener = new SingleLinePropertyViewerListener() {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor.EComboListener#set()
				 */
				public void set() {
					EEFSelectionDialog dialog = new EEFSelectionDialog(propertyEditorViewer.getViewer().getControl().getShell(), true);
					dialog.setTitle("Choose the element to set to the " + feature.getName() + " reference:");
					dialog.setAdapterFactory(view.getEditingComponent().getEditingContext().getAdapterFactory());
					dialog.setEditUIProvidersFactory(view.getEditingComponent().getEditingContext().getServiceRegistry().getService(EditUIProvidersFactory.class, this));
					dialog.addFilter(
							new ChoiceOfValuesFilter(
									view.getEditingComponent().getEditingContext().getAdapterFactory(), 
									view.getEditingComponent().getEObject(), 
									EComboPropertyEditor.this.feature, 
									view.getViewSettings().getSelectionMode()));
					dialog.setInput(view.getViewService().getBestInput(view.getEditingComponent().getEObject()));
					if (dialog.open() == Window.OK) {
						if (dialog.getSelection() != null) {
							view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.SET, null, dialog.getSelection()));
							propertyEditorViewer.getViewer().refresh();
						}
					}
				}

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor.EComboListener#clear()
				 */
				public void clear() {
					view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.UNSET, null, null));
					propertyEditorViewer.getViewer().refresh();
				}
				
			};
			propertyEditorViewer.getViewer().addEDatePickerListener(listener);
		}
	}
}
