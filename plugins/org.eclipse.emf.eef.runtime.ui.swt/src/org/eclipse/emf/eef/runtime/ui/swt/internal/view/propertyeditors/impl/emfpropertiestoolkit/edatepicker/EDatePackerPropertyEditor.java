/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.edatepicker;

import java.util.Date;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.EEFDatePickerDialog;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewer.SingleLinePropertyViewerListener;
import org.eclipse.emf.eef.runtime.ui.swt.util.EEFViewerInput;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EDatePackerPropertyEditor extends PropertyEditorImpl implements MonovaluedPropertyEditor {

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<SingleLinePropertyViewer> propertyEditorViewer;
	private EStructuralFeature feature;
	private SingleLinePropertyViewerListener listener;

	/**
	 * @param view
	 * @param elementEditor
	 * @param propertyEditorViewer
	 */
	public EDatePackerPropertyEditor(PropertiesEditingView<Composite> view, ElementEditor elementEditor, PropertyEditorViewer<SingleLinePropertyViewer> propertyEditorViewer) {
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
			EEFViewerInput input = new EEFViewerInput(view.getEditingComponent().getEditingContext(), feature);
			propertyEditorViewer.getViewer().setInput(input);
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
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * Initialize the listener on the MultiLinePropertyViewer.
	 */
	protected void initListener() {
		if (listener == null) {
			listener = new SingleLinePropertyViewerListener() {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor.EComboListener#set()
				 */
				public void set() {
					EEFDatePickerDialog dialog = new EEFDatePickerDialog(propertyEditorViewer.getViewer().getControl().getShell());
					dialog.setTitle("Choose the date to set to the " + feature.getName() + " attribute:");
					Date date = (Date) view.getEditingComponent().getEObject().eGet(feature);
					dialog.setDate(date);
					if (dialog.open() == Window.OK) {
						if (dialog.getSelection() != null) {
							firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.SET, null, dialog.getSelection()));
							propertyEditorViewer.getViewer().refresh();
						}
					}
				}

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor.EComboListener#clear()
				 */
				public void clear() {
					firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.UNSET, null, null));
					propertyEditorViewer.getViewer().refresh();
				}
				
			};
			propertyEditorViewer.getViewer().addEDatePickerListener(listener);
		}
	}
}
