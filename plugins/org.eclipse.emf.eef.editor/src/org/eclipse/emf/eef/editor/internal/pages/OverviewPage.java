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
package org.eclipse.emf.eef.editor.internal.pages;

import java.io.ObjectInputStream.GetField;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.eef.editor.internal.services.EMFService;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class OverviewPage extends FormPage {

	private EditingContextFactoryProvider contextFactoryProvider;
	private EMFService emfService;
	
	private AdapterFactory adapterFactory;
	private FormToolkit toolkit;

	/**
	 * @param editor
	 * @param adapterFactory
	 */
	public OverviewPage(FormEditor editor, AdapterFactory adapterFactory) {
		super(editor, "eef-overview", "Overview");
		this.adapterFactory = adapterFactory;
	}

	/**
	 * @param contextFactoryProvider the contextFactoryProvider to set
	 */
	public void setContextFactoryProvider(EditingContextFactoryProvider contextFactoryProvider) {
		this.contextFactoryProvider = contextFactoryProvider;
	}

	/**
	 * @param emfService the emfService to set
	 */
	public void setEMFService(EMFService emfService) {
		this.emfService = emfService;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		toolkit = managedForm.getToolkit();
		Form form = managedForm.getForm().getForm();
		toolkit.decorateFormHeading(form);
		form.setText("Overview");
		Composite parent = form.getBody();
		parent.setLayout(new GridLayout(1, false));
		EEFViewer viewer = new EEFViewer(parent, SWT.NONE);
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setContentProvider(new EEFContentProvider());
		PropertiesEditingContext editingContext = createEditingContext();
		if (editingContext != null) {
			viewer.setInput(editingContext);
		}
	}

	private PropertiesEditingContext createEditingContext() {
		EditingDomain domain = ((IEditingDomainProvider)getEditor()).getEditingDomain();
		ResourceSet resourceSet = domain.getResourceSet();
		PropertiesEditingModel editedModel = emfService.findEditedEditingModel(resourceSet);
		if (editedModel != null) {
			PropertiesEditingContext context = contextFactoryProvider.getEditingContextFactory(editedModel).createPropertiesEditingContext(domain, adapterFactory, editedModel);
			context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, toolkit);
			return context;
		}
		return null;
	}

}
