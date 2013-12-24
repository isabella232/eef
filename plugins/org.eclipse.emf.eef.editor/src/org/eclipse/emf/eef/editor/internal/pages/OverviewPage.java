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

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.eef.editor.internal.services.EMFService;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.views.ViewsRepository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.FileEditorInput;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class OverviewPage extends FormPage {

	private EditingContextFactoryProvider contextFactoryProvider;
	private EMFService emfService;
	
	private AdapterFactory adapterFactory;
	private FormToolkit toolkit;
	private Form innerForm;
	private ImageManager imageManager;

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
	 * @param imageManager the imageManager to set
	 */
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		toolkit = managedForm.getToolkit();
		innerForm = managedForm.getForm().getForm();
		toolkit.decorateFormHeading(innerForm);
		refreshPageTitle();
		Composite parent = innerForm.getBody();
		parent.setLayout(new GridLayout(1, false));
		EEFViewer editingModelViewer = new EEFViewer(parent, SWT.NONE);
		editingModelViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		editingModelViewer.setContentProvider(new EEFContentProvider());
		PropertiesEditingContext editingModelEditingContext = createEditingContextForEditingModel();
		if (editingModelEditingContext != null) {
			editingModelViewer.setInput(editingModelEditingContext);
		}
		EEFViewer viewsRepositoryViewer = new EEFViewer(parent, SWT.NONE);
		viewsRepositoryViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		viewsRepositoryViewer.setContentProvider(new EEFContentProvider());
		PropertiesEditingContext viewRepositoryEditingContext = createEditingContextForViewsRepository();
		if (viewRepositoryEditingContext != null) {
			viewsRepositoryViewer.setInput(viewRepositoryEditingContext);
		}
	}

	/**
	 * 
	 */
	private void refreshPageTitle() {
		StringBuilder title = new StringBuilder("Overview");
		Resource mainResource = findMainResource();
		if (mainResource != null) {
			IItemLabelProvider labelProvider = (IItemLabelProvider) adapterFactory.adapt(mainResource, IItemLabelProvider.class);
			if (labelProvider != null) {
				title.append(": ")
					.append(labelProvider.getText(mainResource));
				innerForm.setImage(imageManager.getImageFromObject(labelProvider.getImage(mainResource)));
			}
		}
		innerForm.setText(title.toString());
	}

	private PropertiesEditingContext createEditingContextForEditingModel() {
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

	private PropertiesEditingContext createEditingContextForViewsRepository() {
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
	
	private Resource findMainResource() {
		IEditorInput editorInput = getEditorInput();
		if (editorInput instanceof FileEditorInput) {
			IFile file = ((FileEditorInput) editorInput).getFile();
			if (file != null && file.exists() && file.isAccessible()) {
				String path = file.getFullPath().toOSString();
				URI mainResourceURI = URI.createPlatformResourceURI(path, true);
				EList<Resource> resources = ((IEditingDomainProvider)getEditor()).getEditingDomain().getResourceSet().getResources();
				for (Resource resource : resources) {
					if (mainResourceURI.equals(resource.getURI())) {
						return resource;
					}
				}
			}
		}
		return null;
	}
}
