/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.singlecontainmenteditor;

import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewerListener;
import org.eclipse.emf.eef.runtime.ui.swt.util.EEFViewerInput;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SingleContainmentPropertyEditor extends PropertyEditorImpl implements MonovaluedPropertyEditor {

	private EEFEditingServiceProvider eefEditingServiceProvider;

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<SingleLinePropertyViewer> propertyEditorViewer;
	private SingleLinePropertyViewerListener listener;

	public SingleContainmentPropertyEditor(EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingView<Composite> view, ElementEditor elementEditor, PropertyEditorViewer<SingleLinePropertyViewer> propertyEditorViewer) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyEditorViewer = propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init()
	 */
	public void init() {
		EEFViewerInput input = new EEFViewerInput(eefEditingServiceProvider, view.getEditingComponent().getEditingContext(), elementEditor);
		propertyEditorViewer.getViewer().setInput(input);
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
		listener.disable();
		propertyEditorViewer.getViewer().refresh();
		listener.enable();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		listener.disable();
		propertyEditorViewer.getViewer().setInput(null);
		listener.enable();
	}

	/**
	 * Initialize the listener on the MultiLinePropertyViewer.
	 */
	protected void initListener() {
		if (listener == null) {
			listener = new SingleLinePropertyViewerListener(this, view, elementEditor, propertyEditorViewer.getViewer()) {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor.EComboListener#set()
				 */
				public void set() {
					if (isEnabled()) {
						firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.SET, null, null));
						viewer.refresh();
					}
				}

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor.EComboListener#clear()
				 */
				public void clear() {
					if (isEnabled()) {
						firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.UNSET, null, null));
						viewer.refresh();
					}
				}

			};
			propertyEditorViewer.getViewer().addSingleLinePropertyViewerListener(listener);
		}
	}

}
