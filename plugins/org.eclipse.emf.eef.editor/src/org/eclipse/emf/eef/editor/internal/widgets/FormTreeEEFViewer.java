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
package org.eclipse.emf.eef.editor.internal.widgets;

import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FormTreeEEFViewer extends TreeEEFViewer {

	private FormToolkit toolkit;

	public FormTreeEEFViewer(FormToolkit toolkit, Composite parent, int style) {
		super(parent, style);
		this.toolkit = toolkit;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer#createSashForm(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected SashForm createSashForm(Composite container) {
		SashForm sashForm = super.createSashForm(container);
		toolkit.adapt(sashForm);
		return sashForm;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer#createComposite()
	 */
	@Override
	protected Composite createComposite(Composite control) {
		return toolkit.createComposite(control, SWT.NONE);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer#createLabel(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Label createLabel(Composite selectionContainer) {
		return toolkit.createLabel(selectionContainer, "", SWT.NONE);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer#createTree(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Tree createTree(Composite selectionContainer) {
		return toolkit.createTree(selectionContainer, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer#createButton(org.eclipse.swt.widgets.Composite, org.eclipse.swt.graphics.Image)
	 */
	@Override
	protected Button createButton(Composite selectionContainer, Image image) {
		Button result = toolkit.createButton(selectionContainer, "", SWT.PUSH);
		result.setImage(image);
		result.setEnabled(false);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer#createEEFViewer(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected EEFViewer createEEFViewer(Composite container) {
		EEFViewer result = super.createEEFViewer(container);
		toolkit.adapt((Composite) result.getControl());
		//FIXME: Ugly but ... it works ...
		Control[] controlChildren = ((Composite)result.getControl()).getChildren();
		if (controlChildren.length == 1 && controlChildren[0] instanceof ScrolledComposite) {
			ScrolledComposite scrolledComposite = (ScrolledComposite)controlChildren[0];
			toolkit.adapt(scrolledComposite);
			if (scrolledComposite.getChildren().length > 0 && scrolledComposite.getChildren()[0] instanceof CTabFolder) { 
				toolkit.adapt((CTabFolder) scrolledComposite.getChildren()[0]);
			}
		}
		return result;
	}
	
}
