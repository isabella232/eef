/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.emfpropertiestoolkit.ereferenceeditor;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.StandardPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.widgets.EEFSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor;
import org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorListener;
import org.eclipse.emf.eef.runtime.ui.widgets.util.ArrayFeatureContentProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceMultiPropertyEditor extends StandardPropertyEditor {

	private EReferenceEditor eReferenceEditor;
	private EStructuralFeature feature;

	/**
	 * @param view
	 * @param elementEditor
	 */
	public EReferenceMultiPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor) {
		super(view, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.StandardPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createEditorContents(Composite parent) {
		eReferenceEditor = new EReferenceEditor(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		eReferenceEditor.addReferenceEditorListener(new ReferenceEditorListener() {
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorListener#removeAll(java.util.Collection)
			 */
			public void removeAll(Collection<?> removedElements) {
				view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.REMOVE_MANY, removedElements, null));
				eReferenceEditor.refresh();
			}
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorListener#remove(java.lang.Object)
			 */
			public void remove(Object removedElement) {
				view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.REMOVE, removedElement, null));
				eReferenceEditor.refresh();
			}
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorListener#moveUp(java.lang.Object)
			 */
			public void moveUp(Object movedElement) {
				EObject editedElement = (EObject) view.getEditingComponent().getTarget();
				Object currentValue = editedElement.eGet(feature);
				if (currentValue instanceof List<?>) {
					int oldIndex = ((List<?>)currentValue).indexOf(movedElement);
					if (oldIndex > 0) {
						view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.MOVE, oldIndex, oldIndex - 1));
						eReferenceEditor.refresh();
					}
				}
			}
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorListener#moveDown(java.lang.Object)
			 */
			public void moveDown(Object movedElement) {
				EObject editedElement = (EObject) view.getEditingComponent().getTarget();
				Object currentValue = editedElement.eGet(feature);
				if (currentValue instanceof List<?>) {
					int oldIndex = ((List<?>)currentValue).indexOf(movedElement);
					if (oldIndex < ((List<?>) currentValue).size()) {
						view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.MOVE, oldIndex, oldIndex + 1));
						eReferenceEditor.refresh();
					}
				}
			}
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorListener#edit(java.lang.Object)
			 */
			public void edit(Object editedElement) {
				//TODO
			}
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorListener#add()
			 */
			public void add() {
				EEFSelectionDialog dialog = new EEFSelectionDialog(eReferenceEditor.getControl().getShell(), true);
				dialog.setTitle("Choose the element to add to the " + feature.getName() + " reference:");
				dialog.setAdapterFactory(view.getEditingComponent().getEditingContext().getAdapterFactory());
				dialog.setInput(view.getViewHelper().getBestInput(view.getEditingComponent().getTarget()));
				if (dialog.open() == Window.OK) {
					if (dialog.getSelection() != null) {
						if (dialog.getSelection() instanceof Collection<?>) {
							view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.ADD_MANY, null, dialog.getSelection()));
						} else {
							view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.ADD, null, dialog.getSelection()));
						}
						eReferenceEditor.refresh();				
					}
				}
			}
		});
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = view.getViewSettings().getMultiEditorHeight();
		eReferenceEditor.setLayoutData(layoutData);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void init(EStructuralFeature feature) {
		this.feature = feature;
		AdapterFactory currentAdapterFactory = view.getEditingComponent().getEditingContext().getAdapterFactory();
		if (currentAdapterFactory == null) {
			currentAdapterFactory = EEFRuntimeUI.getPlugin().getRegistryAdapterFactory();
		}
		eReferenceEditor.setContentProvider(new ArrayFeatureContentProvider(this.feature));
		eReferenceEditor.setLabelProvider(new AdapterFactoryLabelProvider(currentAdapterFactory));
		eReferenceEditor.setLowerBound(feature.getLowerBound());
		eReferenceEditor.setUpperBound(feature.getUpperBound());
		eReferenceEditor.setInput(view.getEditingComponent().getTarget());
	}

}
