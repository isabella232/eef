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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.editor.internal.services.EMFService;
import org.eclipse.emf.eef.editor.internal.services.SelectionService;
import org.eclipse.emf.eef.editor.internal.services.ViewerService;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.ViewsPackage;
import org.eclipse.emf.eef.views.ViewsRepository;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewsEditingPage extends FormPage {

	private static final int MODELS_SECTION_WIDTH = 30;

	private EditingContextFactoryProvider contextFactoryProvider;
	private ImageManager imageManager;
	private EMFService emfService;
	private SelectionService selectionService;
	private ViewerService viewerService;
	
	private AdapterFactory adapterFactory;

	private FormToolkit toolkit;
	private Composite pageContainer;

	private TreeViewer views;
	private EEFViewer viewSettingsViewer;



	/**
	 * @param editor
	 * @param adapterFactory
	 */
	public ViewsEditingPage(FormEditor editor, AdapterFactory adapterFactory) {
		super(editor, "eef-views-editing", "Views");
		this.adapterFactory = adapterFactory;
	}

	/**
	 * @param editingContextFactoryProvider the contextFactoryProvider to set
	 */
	public void setContextFactoryProvider(EditingContextFactoryProvider editingContextFactoryProvider) {
		this.contextFactoryProvider = editingContextFactoryProvider;
	}

	/**
	 * @param imageManager the imageManager to set
	 */
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	/**
	 * @param emfService the emfService to set
	 */
	public void setEMFService(EMFService emfService) {
		this.emfService = emfService;
	}

	/**
	 * @param selectionService the selectionService to set
	 */
	public void setSelectionService(SelectionService selectionService) {
		this.selectionService = selectionService;
	}

	/**
	 * @param viewerService the viewerService to set
	 */
	public void setViewerService(ViewerService viewerService) {
		this.viewerService = viewerService;
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
		form.setText("Views");
		Composite parent = form.getBody();
		parent.setLayout(new FillLayout());
		pageContainer = toolkit.createComposite(parent);
		pageContainer.setLayout(new FormLayout());
		Composite viewsSection = createViewsSection(toolkit, pageContainer);
		Composite viewSettingsSection = createViewSettingsSection(toolkit, pageContainer);
		Composite previewSection = createPreviewSection(toolkit, pageContainer);
		layoutPage(viewsSection, viewSettingsSection, previewSection);
	}

	private Composite createViewsSection(FormToolkit toolkit, Composite container) {
		Section viewsSection = toolkit.createSection(container, Section.TITLE_BAR);
		viewsSection.setText("Views");
		Composite viewsContainer = toolkit.createComposite(viewsSection);
		viewsContainer.setLayout(new GridLayout(1, false));
		createViewsSectionContents(toolkit, viewsContainer);
		viewsSection.setClient(viewsContainer);
		ToolBar toolbar  = new ToolBar(viewsSection, SWT.NONE);
		ToolItem add = new ToolItem(toolbar, SWT.PUSH);
		add.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Add"));
		add.setToolTipText("Creates a new view in the repository");
		add.addSelectionListener(new AddViewAdapter());
		final ToolItem delete = new ToolItem(toolbar, SWT.PUSH);
		delete.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Delete"));
		delete.setToolTipText("Delete the selected view of the repository");
		delete.addSelectionListener(new DeleteViewAdapter());
		views.addSelectionChangedListener(new ISelectionChangedListener() {
			
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() != null && !event.getSelection().isEmpty()) {
					delete.setEnabled(true);
					EObject view = selectionService.unwrapSelection(event.getSelection());
					EditingDomain domain = ((IEditingDomainProvider)getEditor()).getEditingDomain();
					PropertiesEditingContext editingContext = contextFactoryProvider.getEditingContextFactory(view).createPropertiesEditingContext(domain, adapterFactory, view);
					editingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, ViewsEditingPage.this.toolkit);
					viewSettingsViewer.setInput(editingContext);
				} else {
					delete.setEnabled(false);
				}
				refreshPageLayout();
			}
		});
		delete.setEnabled(false);
		viewsSection.setTextClient(toolbar);
		return viewsSection;
	}
	
	private Composite createViewSettingsSection(FormToolkit toolkit, Composite container) {
		Section viewSettingsSection = toolkit.createSection(container, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		viewSettingsSection.setText("View Settings");
		Composite viewSettingsContainer = toolkit.createComposite(viewSettingsSection);
		viewSettingsContainer.setLayout(new GridLayout(1, false));
		createViewSettingsSectionContents(toolkit, viewSettingsContainer);
		viewSettingsSection.setClient(viewSettingsContainer);
		return viewSettingsSection;
	}

	private Composite createPreviewSection(FormToolkit toolkit, Composite container) {
		Section previewSection = toolkit.createSection(container, Section.TITLE_BAR);
		previewSection.setText("Preview");
		Composite previewContainer = toolkit.createComposite(previewSection);
		previewContainer.setLayout(new GridLayout(1, false));
		createPreviewSectionContents(toolkit, previewContainer);
		previewSection.setClient(previewContainer);
		return previewSection;
	}

	private void layoutPage(Composite viewsSection, Composite viewSettingsSection, Composite previewSection) {
		FormData viewsData = new FormData();
		viewsData.top = new FormAttachment(0, 10);
		viewsData.bottom = new FormAttachment(100, -10);
		viewsData.left = new FormAttachment(0, 10);
		viewsData.right = new FormAttachment(MODELS_SECTION_WIDTH, -5);
		viewsSection.setLayoutData(viewsData);
		FormData bindingSettingsData = new FormData();
		bindingSettingsData.top = new FormAttachment(0, 10);
		bindingSettingsData.left = new FormAttachment(viewsSection, 5);
		bindingSettingsData.right = new FormAttachment(100, -10);
		viewSettingsSection.setLayoutData(bindingSettingsData);
		FormData previewData = new FormData();
		previewData.top = new FormAttachment(viewSettingsSection, 5);
		previewData.bottom = new FormAttachment(100, -10);
		previewData.left = new FormAttachment(viewsSection, 10);
		previewData.right = new FormAttachment(100, -10);
		previewSection.setLayoutData(previewData);
	}


	private void createViewsSectionContents(FormToolkit toolkit, Composite container) {
		Tree viewsTree = toolkit.createTree(container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		viewsTree.setLayoutData(new GridData(GridData.FILL_BOTH));
		views = new TreeViewer(viewsTree);
		views.setContentProvider(new AdapterFactoryContentProvider(adapterFactory) {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#hasChildren(java.lang.Object)
			 */
			@Override
			public boolean hasChildren(Object object) {
				if (object instanceof View) {
					return false;
				}
				return super.hasChildren(object);
			}
				
		});
		views.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		EditingDomain domain = ((IEditingDomainProvider)getEditor()).getEditingDomain();
		ResourceSet resourceSet = domain.getResourceSet();
		views.setInput(emfService.findEditedViewsRepository(resourceSet));
	}

	
	private void createViewSettingsSectionContents(FormToolkit toolkit, Composite bindingSettingsContainer) {
		viewSettingsViewer = new EEFViewer(bindingSettingsContainer, SWT.BORDER);
		viewSettingsViewer.setContentProvider(new EEFContentProvider());
		viewerService.updateViewerBackground(toolkit, viewSettingsViewer);
	}
	
	private void createPreviewSectionContents(FormToolkit toolkit, Composite previewContainer) {
		toolkit.createLabel(previewContainer, "Preview");		
	}
	
	private void refreshPageLayout() {
		pageContainer.layout(true);
		pageContainer.getParent().layout(true);
	}
	
	private class AddViewAdapter extends SelectionAdapter {

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			View view = ViewsFactory.eINSTANCE.createView();
			ViewsRepository repository = (ViewsRepository) views.getInput();
			view.setName("View" + (repository.getViews().size() + 1));
			IEditingDomainItemProvider provider = (IEditingDomainItemProvider) adapterFactory.adapt(repository, IEditingDomainItemProvider.class);
			EditingDomain editingDomain = ((IEditingDomainProvider)getEditor()).getEditingDomain();
			Command cmd = provider.createCommand(repository, editingDomain, AddCommand.class , new CommandParameter(repository, ViewsPackage.Literals.VIEWS_REPOSITORY__VIEWS, Lists.newArrayList(view)));
			editingDomain.getCommandStack().execute(cmd);
			views.refresh();
			views.setSelection(new StructuredSelection(view));
		}
		
	}
	
	private class DeleteViewAdapter extends SelectionAdapter {

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			ISelection selection = views.getSelection();
			if (selection != null && !selection.isEmpty()) {
				View view = selectionService.unwrapSelection(selection);
				EditingDomain editingDomain = ((IEditingDomainProvider)getEditor()).getEditingDomain();
				Command cmd = editingDomain.createCommand(DeleteCommand.class, new CommandParameter(null, null, Lists.newArrayList(view)));
				editingDomain.getCommandStack().execute(cmd);
			} 		
		}
		
	}
	
}
