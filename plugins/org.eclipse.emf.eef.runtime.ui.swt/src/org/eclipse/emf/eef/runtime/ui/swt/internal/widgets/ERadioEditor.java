/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.swt.internal.widgets;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ERadioEditor extends Viewer {

	private Composite parent;

	private Composite control;
	private List<Button> radio;
	private Button selection;
	
	private IStructuredContentProvider contentProvider;
	private ILabelProvider labelProvider;
	
	private Object input;

	public ERadioEditor(Composite parent, int styles) {
		this.parent = parent;
	}
	
	public void createContents() {
		control = createControlComposite(parent);
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.marginLeft = 1;
		control.setLayout(layout);
		radio = Lists.newArrayList();
	}

	protected Composite createControlComposite(Composite parent) {
		return  new Composite(parent, SWT.NONE);
	}

	protected Button createRadioButton(Composite control, String text) {
		Button button = new Button(control, SWT.RADIO);
		button.setText(text);
		return button;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	@Override
	public Control getControl() {
		return control;
	}
	
	public void setContentProvider(IStructuredContentProvider provider) {
		this.contentProvider = provider;
	}

	/**
	 * Defines the {@link ILabelProvider} to use in this viewer.
	 * @param provider the LabelProvider to use.
	 */
	public void setLabelProvider(ILabelProvider provider) {
		this.labelProvider = provider;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getInput()
	 */
	@Override
	public Object getInput() {
		return input;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getSelection()
	 */
	@Override
	public ISelection getSelection() {
		if (selection != null) {
			return new StructuredSelection(selection.getData());
		} else {
			return new StructuredSelection();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#inputChanged(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void inputChanged(Object input, Object oldInput) {
		refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#refresh()
	 */
	@Override
	public void refresh() {
		clear();
		Object[] elements = contentProvider.getElements(getInput());
		for (Object object : elements) {
			String text = labelProvider != null?labelProvider.getText(object):object.toString();
			Button button = createRadioButton(control, text);
			button.setData(object);
			button.addSelectionListener(getSelectionListener());
			radio.add(button);
		}
		if (control.getParent() != null) {
			control.getParent().layout();
			control.layout();
		}
	}

	private SelectionListener getSelectionListener() {
		return new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				fireSelectionChanged(new SelectionChangedEvent(ERadioEditor.this, new StructuredSelection(e.widget.getData())));
				selection = (Button) e.item;
			}
			
		};
	}

	private void clear() {
		if (radio != null) {
			for (Button button : radio) {
				button.dispose();
			}
			selection = null;
			radio.clear();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#setInput(java.lang.Object)
	 */
	@Override
	public void setInput(Object input) {
		Assert.isTrue(contentProvider != null, "ContentViewer must have a content provider when input is set."); //$NON-NLS-1$

		Object oldInput = getInput();
		contentProvider.inputChanged(this, oldInput, input);
		this.input = input;

		// call input hook
		inputChanged(this.input, oldInput);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#setSelection(org.eclipse.jface.viewers.ISelection, boolean)
	 */
	@Override
	public void setSelection(ISelection selection, boolean reveal) {
		if (selection == null || selection.isEmpty()) {
			if (this.selection != null) {
				this.selection.setSelection(false);
			}
			this.selection = null;
		}
		if (selection instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) selection).getFirstElement();
			for (Button button : radio) {
				if (firstElement.equals(button.getData())) {
					this.selection = button;
					button.setSelection(true);
					break;
				}
			}
		}
	}
	
}
