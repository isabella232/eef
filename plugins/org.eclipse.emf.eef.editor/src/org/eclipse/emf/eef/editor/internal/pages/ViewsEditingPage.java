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
import org.eclipse.emf.eef.views.ViewsRepository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewsEditingPage extends FormPage {

	private static final int MODELS_SECTION_WIDTH = 30;

	private EMFService emfService;
	private EditingContextFactoryProvider contextFactoryProvider;
	
	private AdapterFactory adapterFactory;

	private FormToolkit toolkit;
	private Composite pageContainer;

	/**
	 * @param editor
	 * @param adapterFactory
	 */
	public ViewsEditingPage(FormEditor editor, AdapterFactory adapterFactory) {
		super(editor, "eef-views-editing", "Views");
		this.adapterFactory = adapterFactory;
	}

	/**
	 * @param emfService the emfService to set
	 */
	public void setEMFService(EMFService emfService) {
		this.emfService = emfService;
	}

	/**
	 * @param editingContextFactoryProvider the contextFactoryProvider to set
	 */
	public void setContextFactoryProvider(EditingContextFactoryProvider editingContextFactoryProvider) {
		this.contextFactoryProvider = editingContextFactoryProvider;
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
		Composite parent = form.getBody();
		parent.setLayout(new FillLayout());
		pageContainer = toolkit.createComposite(parent);
		pageContainer.setLayout(new FormLayout());
		Composite viewsSection = createViewsSection(toolkit, pageContainer);
		layoutPage(viewsSection, null, null);
	}

	private Composite createViewsSection(FormToolkit toolkit, Composite container) {
		Section viewsSection = toolkit.createSection(container, Section.TITLE_BAR);
		viewsSection.setText("Views");
		Composite viewsContainer = toolkit.createComposite(viewsSection);
		viewsContainer.setLayout(new GridLayout(1, false));
		createViewsSectionContents(toolkit, viewsContainer);
		viewsSection.setClient(viewsContainer);
		return viewsSection;
	}
	
	private void layoutPage(Composite viewsSection, Composite bindingSettingsSection, Composite previewSection) {
		FormData viewsData = new FormData();
		viewsData.top = new FormAttachment(0, 10);
		viewsData.bottom = new FormAttachment(100, -10);
		viewsData.left = new FormAttachment(0, 10);
		viewsData.right = new FormAttachment(MODELS_SECTION_WIDTH, -5);
		viewsSection.setLayoutData(viewsData);
//		FormData bindingSettingsData = new FormData();
//		bindingSettingsData.top = new FormAttachment(0, 10);
//		bindingSettingsData.left = new FormAttachment(viewsSection, 5);
//		bindingSettingsData.right = new FormAttachment(100, -10);
//		bindingSettingsSection.setLayoutData(bindingSettingsData);
//		FormData previewData = new FormData();
//		previewData.top = new FormAttachment(bindingSettingsSection, 5);
//		previewData.bottom = new FormAttachment(100, -10);
//		previewData.left = new FormAttachment(viewsSection, 10);
//		previewData.right = new FormAttachment(100, -10);
//		previewSection.setLayoutData(previewData);
	}


	private void createViewsSectionContents(FormToolkit toolkit, Composite container) {
		EEFViewer viewer = new EEFViewer(container, SWT.NONE);
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
		ViewsRepository viewsRepository = emfService.findEditedViewsRepository(resourceSet);
		if (viewsRepository != null) {
			PropertiesEditingContext context = contextFactoryProvider.getEditingContextFactory(viewsRepository).createPropertiesEditingContext(domain, adapterFactory, viewsRepository);
			context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, toolkit);
			return context;
		}
		return null;
	}
	
}
