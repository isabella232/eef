/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.EEFRuntimePlugin;
import org.eclipse.emf.eef.runtime.ui.widgets.settings.EEFEditorSettings;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SingleCompositionEditor extends Viewer {
	
	private FormToolkit toolkit;
	private Composite container;
	private Object input;
	private ILabelProvider labelProvider;
	private Text inputDisplay;
	private AdapterFactory adapterFactory;
	
	private List<SingleCompositionListener> listeners;

	/**
	 * @param parent parent {@link Composite} of this viewer.
	 * @param styles Styles to apply to this viewer.
	 */
	public SingleCompositionEditor(Composite parent, int styles) {
		buildEditor(parent);
	}

	/**
	 * @param toolkit {@link FormToolkit} to use to build this editor.
	 * @param parent parent {@link Composite} of this viewer.
	 * @param styles Styles to apply to this viewer.
	 */
	public SingleCompositionEditor(FormToolkit toolkit, Composite parent, int styles) {
		this.toolkit = toolkit;
		buildEditor(parent);
	}

	private void buildEditor(Composite parent) {
		if (toolkit != null) {
			container = toolkit.createComposite(parent);
		} else {
			container = new Composite(parent, SWT.NONE);
		}
		container.setLayout(new GridLayout(3, false));
		GridData comboData = new GridData(GridData.FILL_HORIZONTAL);
		comboData.horizontalSpan = 3;
		if (toolkit != null) {
			inputDisplay = toolkit.createText(container, "");
		} else {
			inputDisplay = new Text(container, SWT.BORDER);
		}
		inputDisplay.setEditable(false);
		inputDisplay.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Button edit;
		if (toolkit != null) {
			edit = toolkit.createButton(container, "...", SWT.PUSH);
		} else {
			edit = new Button(container, SWT.PUSH);
			edit.setText("...");
		}
			
		edit.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				for (SingleCompositionListener listener : listeners) {
					listener.edit();
				}
			}
			
		});
		Button clear;
		if (toolkit != null) {
			clear = toolkit.createButton(container, "", SWT.PUSH);
		} else {
			clear = new Button(container, SWT.PUSH);
		}
		clear.setImage(EEFRuntimePlugin.getImage(EEFRuntimePlugin.ICONS_16x16 + "Delete_16x16.gif"));
		clear.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				for (SingleCompositionListener listener : listeners) {
					listener.clear();
				}
			}
			
		});
		listeners = new ArrayList<SingleCompositionListener>();
	}
	
	/**
	 * Defines the layout data of this viewer.
	 * @param data Layout data.
	 */
	public void setLayoutData(Object data) {
		container.setLayoutData(data);
	}
	
	/**
	 * @param adapterFactory the {@link AdapterFactory} to use.
	 */
	public void setAdapterFactory(AdapterFactory adapterFactory) {
		this.adapterFactory = adapterFactory;
	}
	
	/**
	 * Defines the {@link LabelProvider} to use in this viewer.
	 * @param labelProvider the {@link LabelProvider} to use.
	 */
	public void setLabelProvider(ILabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	public Control getControl() {
		return container;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getInput()
	 */
	public Object getInput() {
		return input;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#refresh()
	 */
	public void refresh() {
		Object value;
		if (input instanceof EEFEditorSettings) {
			value = ((EEFEditorSettings) input).getValue();
		} else {
			value = input;
		}
		inputDisplay.setText(getLabelProvider().getText(value));
	}

		/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#setInput(java.lang.Object)
	 */
	public void setInput(Object input) {
		this.input = input;
		refresh();
	}
	
	/**
	 * Adds a {@link SingleCompositionListener} to the list.
	 * @param listener the listener to add.
	 */
	public void addEditorListener(SingleCompositionListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a {@link SingleCompositionListener} to the list.
	 * @param listener the listener to remove.
	 */
	public void removeEditorListener(SingleCompositionListener listener) {
		listeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getSelection()
	 */
	public ISelection getSelection() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#setSelection(org.eclipse.jface.viewers.ISelection, boolean)
	 */
	public void setSelection(ISelection selection, boolean reveal) {
		
	}
	
	private ILabelProvider getLabelProvider() {
		if (labelProvider != null) {
			return labelProvider;
		} else if (adapterFactory != null) {
			labelProvider = new AdapterFactoryLabelProvider(adapterFactory);
			return labelProvider;
		}
		return null;
	}

 	/**
	 * 
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 *
	 */
	public interface SingleCompositionListener {
		
		/**
		 * The edit button has been selected.
		 */
		void edit();
		
		/**
		 * The clear button has been selected.
		 */
		void clear();
		
	}

}
