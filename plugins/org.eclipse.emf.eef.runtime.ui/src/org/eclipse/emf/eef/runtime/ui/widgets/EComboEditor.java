/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.widgets;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.emf.eef.runtime.ui.services.images.ImageManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
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

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EComboEditor extends Viewer {

	private Composite parent;
	private int styles;

	private Composite control;
	private Text text;
	private Button setButton;
	private Button clearButton;

	private ILabelProvider labelProvider;
	private ImageManager imageManager;

	private Object input;
	private Collection<EComboListener> listeners;

	private boolean locked;

	public EComboEditor(Composite parent, int styles) {
		this.parent = parent;
		this.styles = styles;
	}

	public void createContents() {
		control = createControlComposite(parent);
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.marginLeft = 1;
		control.setLayout(layout);
		text = createText(control, styles);
		text.setEditable(false);
		GridData textData = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(textData);
		GridData buttonData = new GridData();
		buttonData.verticalAlignment = SWT.UP;
		setButton = createSetButton(control);
		setButton.setLayoutData(buttonData);
		setButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				for (EComboListener listener : listeners) {
					listener.set();
				}
			}

		});
		clearButton = createClearButton(control);
		if (imageManager != null) {
			clearButton.setImage(imageManager.getImage(EEFRuntimeUI.getResourceLocator(), "Delete"));
		} else {
			clearButton.setText("del");
		}
		clearButton.setLayoutData(buttonData);
		clearButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (EComboListener listener : listeners) {
					listener.clear();
				}
			}

		});
		listeners = Lists.newArrayList();
	}

	protected Composite createControlComposite(Composite parent) {
		return new Composite(parent, SWT.NONE);
	}

	protected Text createText(Composite control, int styles) {
		return new Text(control, SWT.BORDER | styles);
	}

	protected Button createSetButton(Composite control) {
		Button result = new Button(control, SWT.PUSH);
		result.setText("...");
		return result;
	}

	protected Button createClearButton(Composite control) {
		return new Button(control, SWT.PUSH);
	}

	/**
	 * Defines the {@link ILabelProvider} to use in this viewer.
	 * 
	 * @param provider
	 *            the LabelProvider to use.
	 */
	public void setLabelProvider(ILabelProvider provider) {
		this.labelProvider = provider;
	}

	/**
	 * @param imageManager
	 *            the imageManager to set
	 */
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	/**
	 * Adds a listener to the current viewer.
	 * 
	 * @param listener
	 *            the {@link EComboListener} to add.
	 */
	public void addEComboListener(EComboListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a listener to the current viewer.
	 * 
	 * @param listener
	 *            the {@link EComboListener} to remove.
	 */
	public void removeEComboListener(EComboListener listener) {
		listeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	@Override
	public Control getControl() {
		return control;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.Viewer#getInput()
	 */
	@Override
	public Object getInput() {
		return input;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.Viewer#getSelection()
	 */
	@Override
	public ISelection getSelection() {
		return new StructuredSelection(input);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.Viewer#refresh()
	 */
	@Override
	public void refresh() {
		if (input == null) {
			text.setText("");
		} else {
			if (labelProvider != null) {
				text.setText(labelProvider.getText(input));
			} else {
				text.setText(input.toString());
			}
		}
		updateButtons();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.Viewer#setInput(java.lang.Object)
	 */
	@Override
	public void setInput(Object input) {
		this.input = input;
		refresh();
	}

	/**
	 * @param layoutData
	 */
	public void setLayoutData(GridData layoutData) {
		control.setLayoutData(layoutData);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.Viewer#setSelection(org.eclipse.jface.viewers.ISelection,
	 *      boolean)
	 */
	@Override
	public void setSelection(ISelection selection, boolean reveal) {
		// I don't think this method should be called on this viewer.
	}

	/**
	 * @param locked
	 *            the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
		updateButtons();
	}

	/**
	 * Update the list buttons.
	 */
	protected void updateButtons() {
		setButton.setEnabled(!locked);
		clearButton.setEnabled(shouldEnableClear(input));
	}

	private boolean shouldEnableClear(Object input) {
		return !locked && (input != null);
	}

	public interface EComboListener {

		/**
		 * Notifies a "Set" operation.
		 */
		void set();

		/**
		 * Notifies a "Clear" operation.
		 */
		void clear();

	}

}
