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

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.editor.EEFReflectiveEditor;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.editor.internal.notify.Notifiable;
import org.eclipse.emf.eef.editor.internal.services.EMFService;
import org.eclipse.emf.eef.editor.internal.services.SelectionService;
import org.eclipse.emf.eef.editor.internal.services.ViewerService;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.ReflectivePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.ViewsPackage;
import org.eclipse.emf.eef.views.ViewsRepository;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
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

	private Composite pageContainer;
	private FormToolkit toolkit;
	private Form innerForm;
	private ToolItem moveUpView;
	private ToolItem moveDownView;
	private FilteredTree views;
	private EEFViewer viewSettingsViewer;
	private EEFViewer viewPreviewViewer;

	private boolean viewsSortedAlphabetically = false;

	/**
	 * @param editor
	 * @param adapterFactory
	 */
	public ViewsEditingPage(FormEditor editor, AdapterFactory adapterFactory) {
		super(editor, "eef-views-editing", "Views");
		this.adapterFactory = adapterFactory;
	}

	/**
	 * @param editingContextFactoryProvider
	 *            the contextFactoryProvider to set
	 */
	public void setContextFactoryProvider(EditingContextFactoryProvider editingContextFactoryProvider) {
		this.contextFactoryProvider = editingContextFactoryProvider;
	}

	/**
	 * @param imageManager
	 *            the imageManager to set
	 */
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	/**
	 * @param emfService
	 *            the emfService to set
	 */
	public void setEMFService(EMFService emfService) {
		this.emfService = emfService;
	}

	/**
	 * @param selectionService
	 *            the selectionService to set
	 */
	public void setSelectionService(SelectionService selectionService) {
		this.selectionService = selectionService;
	}

	/**
	 * @param viewerService
	 *            the viewerService to set
	 */
	public void setViewerService(ViewerService viewerService) {
		this.viewerService = viewerService;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		toolkit = managedForm.getToolkit();
		innerForm = managedForm.getForm().getForm();
		toolkit.decorateFormHeading(innerForm);
		refreshPageTitle();
		Composite parent = innerForm.getBody();
		parent.setLayout(new FillLayout());
		pageContainer = toolkit.createComposite(parent);
		pageContainer.setLayout(new FormLayout());
		Composite viewsSection = createViewsSection(toolkit, pageContainer);
		Composite viewSettingsSection = createViewSettingsSection(toolkit, pageContainer);
		Composite previewSection = createPreviewSection(toolkit, pageContainer);
		layoutPage(viewsSection, viewSettingsSection, previewSection);
		((EEFReflectiveEditor) getEditor()).addNotifiable(new Notifiable() {

			public void notifyChanged(final Notification notification) {
				pageContainer.getDisplay().asyncExec(new Runnable() {

					public void run() {
						if (notification.getNotifier() instanceof ViewsRepository) {
							refreshPageTitle();
						} else {
							viewPreviewViewer.refresh();
							Object input = viewPreviewViewer.getInput();
							if (input instanceof ReflectivePropertiesEditingContext) {
								views.getViewer().setSelection(new StructuredSelection(((ReflectivePropertiesEditingContext) input).getEObject()));
							}
						}
					}
				});
			}

			public int getIndex() {
				return 2;
			}
		});
	}

	private Composite createViewsSection(FormToolkit toolkit, Composite container) {
		Section viewsSection = toolkit.createSection(container, Section.TITLE_BAR);
		viewsSection.setText("Views");
		Composite viewsContainer = toolkit.createComposite(viewsSection);
		viewsContainer.setLayout(new GridLayout(1, false));
		createViewsSectionContents(toolkit, viewsContainer);
		viewsSection.setClient(viewsContainer);
		ToolBar toolbar = new ToolBar(viewsSection, SWT.NONE);
		ToolItem add = new ToolItem(toolbar, SWT.PUSH);
		add.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Add"));
		add.setToolTipText("Creates a new view in the repository");
		add.addSelectionListener(new AddViewAdapter());
		final ToolItem delete = new ToolItem(toolbar, SWT.PUSH);
		delete.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Delete"));
		delete.setToolTipText("Delete the selected view of the repository");
		delete.addSelectionListener(new DeleteViewAdapter());

		moveUpView = new ToolItem(toolbar, SWT.PUSH);
		moveUpView.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "ArrowUp"));
		moveUpView.addSelectionListener(new MoveSelectedViewAdapter(views.getViewer()) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.eef.editor.internal.pages.ViewsEditingPage.MoveSelectedViewAdapter#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
			 *      org.eclipse.emf.ecore.EObject,
			 *      org.eclipse.emf.eef.views.View, int)
			 */
			@Override
			protected Command createCommand(EditingDomain editingDomain, EObject viewContainer, View view, int currentIndex) {
				return editingDomain.createCommand(MoveCommand.class, new CommandParameter(viewContainer, view.eContainingFeature(), view, currentIndex - 1));
			}

		});
		moveDownView = new ToolItem(toolbar, SWT.PUSH);
		moveDownView.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "ArrowDown"));
		moveDownView.addSelectionListener(new MoveSelectedViewAdapter(views.getViewer()) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.eef.editor.internal.pages.ViewsEditingPage.MoveSelectedViewAdapter#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
			 *      org.eclipse.emf.ecore.EObject,
			 *      org.eclipse.emf.eef.views.View, int)
			 */
			@Override
			protected Command createCommand(EditingDomain editingDomain, EObject viewContainer, View view, int currentIndex) {
				return editingDomain.createCommand(MoveCommand.class, new CommandParameter(viewContainer, view.eContainingFeature(), view, currentIndex + 1));
			}

		});
		updateViewsSection();

		Separator sep = new Separator();
		sep.fill(toolbar, 4);

		final ToolItem alphaSort = new ToolItem(toolbar, SWT.CHECK);
		alphaSort.setImage(imageManager.getImage(EditingModelEditPlugin.getPlugin(), "AlphaSort"));
		alphaSort.setToolTipText("Sort alphabetically views");
		alphaSort.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewsSortedAlphabetically = !viewsSortedAlphabetically;
				updateViewsSection();
			}

		});
		views.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() != null && !event.getSelection().isEmpty()) {
					EObject view = selectionService.unwrapSelection(event.getSelection());
					PropertiesEditingContext currentSelection = (PropertiesEditingContext) viewSettingsViewer.getInput();
					if (currentSelection == null || (currentSelection instanceof EObjectPropertiesEditingContext && !view.equals(((EObjectPropertiesEditingContext) currentSelection).getEObject()))) {
						delete.setEnabled(true);
						EditingDomain domain = ((IEditingDomainProvider) getEditor()).getEditingDomain();
						PropertiesEditingContext editingContext = contextFactoryProvider.getEditingContextFactory(view).createPropertiesEditingContext(domain, adapterFactory, view);
						editingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, ViewsEditingPage.this.toolkit);
						viewSettingsViewer.setInput(editingContext);
					}
					PropertiesEditingContext reflectEditingContext = contextFactoryProvider.getEditingContextFactory(view).createReflectivePropertiesEditingContext(adapterFactory, view);
					reflectEditingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, ViewsEditingPage.this.toolkit);
					viewPreviewViewer.setInput(reflectEditingContext);

				} else {
					delete.setEnabled(false);
				}
				refreshPageLayout();
				updateViewsSection();
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
		Section previewSection = toolkit.createSection(container, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
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
		views = new FilteredTree(container, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL, new PatternFilter(), true);
		toolkit.adapt(views);
		views.getViewer().setContentProvider(new AdapterFactoryContentProvider(adapterFactory) {

			private final Object[] nullChildren = new Object[0];

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
			 */
			@Override
			public Object[] getChildren(Object object) {
				if (object instanceof View) {
					return nullChildren;
				}
				return super.getChildren(object);
			}

			/**
			 * {@inheritDoc}
			 * 
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
		views.setLayoutData(new GridData(GridData.FILL_BOTH));
		views.getViewer().setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		EditingDomain domain = ((IEditingDomainProvider) getEditor()).getEditingDomain();
		ResourceSet resourceSet = domain.getResourceSet();
		views.getViewer().setInput(emfService.findEditedViewsRepository(resourceSet));
	}

	private void createViewSettingsSectionContents(FormToolkit toolkit, Composite bindingSettingsContainer) {
		viewSettingsViewer = new EEFViewer(bindingSettingsContainer, SWT.BORDER);
		viewSettingsViewer.setContentProvider(new EEFContentProvider());
		viewerService.updateViewerBackground(toolkit, viewSettingsViewer);
	}

	private void createPreviewSectionContents(FormToolkit toolkit, Composite previewContainer) {
		viewPreviewViewer = new EEFViewer(previewContainer, SWT.BORDER);
		viewPreviewViewer.setContentProvider(new EEFContentProvider());
		viewerService.updateViewerBackground(toolkit, viewPreviewViewer);
	}

	private void refreshPageTitle() {
		if (innerForm != null) {
			ResourceSet resourceSet = ((IEditingDomainProvider) getEditor()).getEditingDomain().getResourceSet();
			ViewsRepository editedViewsRepository = emfService.findEditedViewsRepository(resourceSet);
			String formLabel = "Views";
			if (editedViewsRepository != null) {
				IItemLabelProvider labelProvider = (IItemLabelProvider) adapterFactory.adapt(editedViewsRepository, IItemLabelProvider.class);
				if (labelProvider != null) {
					formLabel = labelProvider.getText(editedViewsRepository);
					Image image = imageManager.getImageFromObject(labelProvider.getImage(editedViewsRepository));
					if (image != null) {
						innerForm.setImage(image);
					}
				}
			}
			innerForm.setText(formLabel);
		}
	}

	private void refreshPageLayout() {
		pageContainer.layout(true);
		pageContainer.getParent().layout(true);
	}

	private void updateViewsSection() {
		TreeViewer viewer = views.getViewer();
		if (viewsSortedAlphabetically) {
			if (viewer.getSorter() == null) {
				ViewerSorter sorter = new ViewerSorter();
				viewer.setSorter(sorter);
			}
			moveUpView.setEnabled(false);
			moveUpView.setToolTipText("You cannot reorder views when the viewer is alphabetically sorted.");
			moveDownView.setEnabled(false);
			moveDownView.setToolTipText("You cannot reorder views when the viewer is alphabetically sorted.");
		} else {
			if (viewer.getSorter() != null) {
				viewer.setSorter(null);
			}
			if (viewer.getSelection() instanceof StructuredSelection && !((StructuredSelection) viewer.getSelection()).isEmpty()) {
				StructuredSelection selection = (StructuredSelection) viewer.getSelection();
				View selectedElement = (View) selection.getFirstElement();
				EObject eContainer = selectedElement.eContainer();
				if (eContainer != null) {
					List<?> eViews = (List<?>) eContainer.eGet(selectedElement.eContainmentFeature());
					int index = eViews.indexOf(selectedElement);
					if (index > 0) {
						moveUpView.setEnabled(true);
						moveUpView.setToolTipText("Move up the selected view.");
					} else {
						moveUpView.setEnabled(false);
						moveUpView.setToolTipText("Unable to move up the selected view.");
					}
					if (index < eViews.size() - 1) {
						moveDownView.setEnabled(true);
						moveDownView.setToolTipText("Move down the selected view");
					} else {
						moveDownView.setEnabled(false);
						moveDownView.setToolTipText("Unable to move down the selected view");
					}
				}
			} else {
				moveUpView.setEnabled(false);
				moveUpView.setToolTipText("No view selected.");
				moveDownView.setEnabled(false);
				moveDownView.setToolTipText("No view selected");

			}
		}
		viewer.refresh();
	}

	private class AddViewAdapter extends SelectionAdapter {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			View view = ViewsFactory.eINSTANCE.createView();
			ViewsRepository repository = (ViewsRepository) views.getViewer().getInput();
			view.setName("View" + (repository.getViews().size() + 1));
			IEditingDomainItemProvider provider = (IEditingDomainItemProvider) adapterFactory.adapt(repository, IEditingDomainItemProvider.class);
			EditingDomain editingDomain = ((IEditingDomainProvider) getEditor()).getEditingDomain();
			Command cmd = provider.createCommand(repository, editingDomain, AddCommand.class, new CommandParameter(repository, ViewsPackage.Literals.VIEWS_REPOSITORY__VIEWS, Lists.newArrayList(view)));
			editingDomain.getCommandStack().execute(cmd);
			views.getViewer().refresh();
			views.getViewer().setSelection(new StructuredSelection(view));
		}

	}

	private class DeleteViewAdapter extends SelectionAdapter {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			ISelection selection = views.getViewer().getSelection();
			if (selection != null && !selection.isEmpty()) {
				View view = selectionService.unwrapSelection(selection);
				EditingDomain editingDomain = ((IEditingDomainProvider) getEditor()).getEditingDomain();
				Command cmd = editingDomain.createCommand(DeleteCommand.class, new CommandParameter(null, null, Lists.newArrayList(view)));
				editingDomain.getCommandStack().execute(cmd);
			}
		}

	}

	private abstract class MoveSelectedViewAdapter extends SelectionAdapter {

		private Viewer viewer;

		/**
		 * @param viewer
		 */
		public MoveSelectedViewAdapter(Viewer viewer) {
			this.viewer = viewer;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			EditingDomain editingDomain = ((IEditingDomainProvider) getEditor()).getEditingDomain();
			StructuredSelection selection = (StructuredSelection) viewer.getSelection();
			View selectedElement = (View) selection.getFirstElement();
			EObject eContainer = selectedElement.eContainer();
			List<?> eViews = (List<?>) eContainer.eGet(selectedElement.eContainmentFeature());
			int index = eViews.indexOf(selectedElement);
			Command cmd = createCommand(editingDomain, eContainer, selectedElement, index);
			editingDomain.getCommandStack().execute(cmd);
			updateViewsSection();
		}

		protected abstract Command createCommand(EditingDomain editingDomain, EObject viewContainer, View view, int currentIndex);
	}
}
