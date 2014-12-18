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
package org.eclipse.emf.eef.runtime.editingModel.presentation.pages;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.UIConstants;
import org.eclipse.emf.eef.editor.EEFReflectiveEditor;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.editor.internal.services.EMFService;
import org.eclipse.emf.eef.editor.internal.services.SelectionService;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class EEFMasterDetailsPage extends FormPage {

	/**
	 * EEF services
	 */
	private SelectionService selectionService;
	private EMFService emfService;
	private ImageManager imageManager;

	/**
	 * AdapterFactory
	 */
	private AdapterFactory adapterFactory;

	/**
	 * Widgets
	 */
	protected FormToolkit toolkit;
	protected IManagedForm managedForm;
	protected Section modelSection;
	protected Composite modelContainer;
	protected Composite previewContainer;
	protected Section previewSection;
	protected ScrolledForm form;
	protected SashForm sashForm;
	protected CTabFolder tabFolder;

	/**
	 * Tree viewers
	 */
	protected TreeViewer modelViewer;
	protected EEFViewer eefViewer;
	protected Object input;

	/**
	 * @param editor
	 *            FormEditor
	 * @param id
	 * @param title
	 */
	public EEFMasterDetailsPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	/**
	 * @param id
	 * @param title
	 */
	public EEFMasterDetailsPage(String id, String title) {
		super(id, title);
	}

	/**
	 * @param adapterFactory
	 *            the adapterFactory to set
	 */
	public void setAdapterFactory(AdapterFactory adapterFactory) {
		this.adapterFactory = adapterFactory;
		if (modelViewer != null) {
			initViewers(adapterFactory);
		}
	}

	/**
	 * @param input
	 *            the input to set
	 */
	public void setInput(Object input) {
		this.input = input;
		if (modelViewer != null) {
			modelViewer.setInput(input);
			if ((adapterFactory != null)) {
				IItemLabelProvider adapt = (IItemLabelProvider) adapterFactory.adapt(input, IItemLabelProvider.class);
				if (adapt != null) {
					setFormTitle(adapt.getText(input));
				}
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormPage#setFocus()
	 */
	@Override
	public void setFocus() {
		modelViewer.getControl().setFocus();
	}

	/**
	 * Init content and label providers.
	 * 
	 * @param adapterFactory
	 */
	protected void initViewers(AdapterFactory adapterFactory) {
		eefViewer.setContentProvider(new EEFContentProvider());
		eefViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
	}

	/**
	 * Set form title
	 * 
	 * @param title
	 */
	public void setFormTitle(String title) {
		form.getForm().setText(title);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		this.managedForm = managedForm;
		form = managedForm.getForm();
		toolkit = this.managedForm.getToolkit();
		toolkit.decorateFormHeading(form.getForm());
		refreshPageTitle();

		Composite parent = form.getBody();
		GridLayout layout = new GridLayout(1, false);
		parent.setLayout(layout);

		sashForm = new SashForm(parent, SWT.NULL);
		sashForm.setLayoutData(createFillBothData());
		toolkit.adapt(sashForm, false, false);

		initModelSection();

		previewSection = toolkit.createSection(sashForm, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		previewSection.setText("Preview");

		previewContainer = toolkit.createComposite(previewSection);
		previewContainer.setLayout(new GridLayout(1, false));

		initPreviewSection();
		sashForm.setWeights(new int[] { 50, 50 });

		// init viewers
		if (adapterFactory != null) {
			initViewers(adapterFactory);
		}
		if (input != null) {
			modelViewer.setInput(input);
			if ((adapterFactory != null)) {
				refreshPageTitle();
			}
		}
	}

	/**
	 * Init preview section.
	 */
	public void initPreviewSection() {
		eefViewer = new EEFViewer(previewContainer, SWT.NONE);
		eefViewer.getControl().setLayoutData(createFillBothData());
		previewSection.setClient(previewContainer);
	}

	/**
	 * Init model section
	 */
	public void initModelSection() {
		modelSection = toolkit.createSection(sashForm, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		modelSection.setText("Models");

		modelContainer = toolkit.createComposite(modelSection);
		modelContainer.setLayout(new GridLayout(1, false));

		initTabFolder();

		modelSection.setClient(modelContainer);

		// init model viewer
		String viewerTitle = "Main";
		modelViewer = addTreeViewer(viewerTitle);

		// add selection listener
		modelViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				Object object = getSelectionService().unwrapSelection(event.getSelection());
				if (object instanceof EObject) {
					EObject eObject = (EObject) object;
					PropertiesEditingContextFactory editingContextFactory = EditingModelEditPlugin.getPlugin().getEditingContextFactoryProvider().getEditingContextFactory(eObject);
					PropertiesEditingContext context = editingContextFactory.createPropertiesEditingContext(((EEFReflectiveEditor) getEditor()).getEditingDomain(), adapterFactory, eObject);
					context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, toolkit);
					updateDetailsViewer(context);
				} else {
					PropertiesEditingContextFactory editingContextFactory = EditingModelEditPlugin.getPlugin().getEditingContextFactoryProvider().getEditingContextFactory(EcorePackage.eINSTANCE);
					PropertiesEditingContext context = editingContextFactory.createNullEditingContext();
					context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, toolkit);
					updateDetailsViewer(context);
				}
				((EEFReflectiveEditor) getEditor()).setSelection(event.getSelection());
			}
		});

		// add contextual menus.
		((EEFReflectiveEditor) getEditor()).createContextMenuFor(modelViewer);

		// additionnal viewers if needed
		addAdditionalViewers();

		getTabFolder().setSelection(0);
	}

	/**
	 * Init tab folder.
	 */
	public void initTabFolder() {
		setTabFolder(new CTabFolder(modelContainer, SWT.BORDER));
		toolkit.adapt(getTabFolder());
		toolkit.getColors().initializeSectionToolBarColors();
		getTabFolder().setSelectionBackground(new Color[] { toolkit.getColors().getColor(IFormColors.H_GRADIENT_START), toolkit.getColors().getColor(IFormColors.H_GRADIENT_END) }, new int[] { 50 }, true);
		getTabFolder().setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		getTabFolder().setSelectionForeground(toolkit.getColors().getColor(IFormColors.TITLE));

		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.widthHint = UIConstants.WIDTH_HINT;
		getTabFolder().setLayoutData(layoutData);
	}

	private void refreshPageTitle() {
		if (form != null) {
			PropertiesEditingModel editedEditingModel = emfService.findEditedEditingModel(((IEditingDomainProvider) getEditor()).getEditingDomain().getResourceSet());
			String formLabel = "Bindings";
			if (editedEditingModel != null) {
				IItemLabelProvider labelProvider = (IItemLabelProvider) adapterFactory.adapt(editedEditingModel, IItemLabelProvider.class);
				if (labelProvider != null) {
					formLabel = labelProvider.getText(editedEditingModel);
					Image image = imageManager.getImageFromObject(labelProvider.getImage(editedEditingModel));
					if (image != null) {
						form.setImage(image);
					}
				}
			}
			form.setText(formLabel);
		}
	}

	/**
	 * Add additional viewers in model part.
	 */
	protected void addAdditionalViewers() {
		// do nothing by default: just one tab
	}

	/**
	 * @param viewerTitle
	 * @return new treeviewer.
	 */
	public TreeViewer addTreeViewer(String viewerTitle) {
		CTabItem modelItem = new CTabItem(getTabFolder(), SWT.NONE);
		TreeViewer modelViewer = new TreeViewer(getTabFolder(), SWT.MULTI);
		modelItem.setText(viewerTitle);
		modelViewer.getControl().setLayoutData(createFillBothData());
		modelItem.setControl(modelViewer.getControl());
		if (adapterFactory != null) {
			modelViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
			modelViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		}
		return modelViewer;
	}

	/**
	 * @param emfService
	 *            the emfService to set
	 */
	public void setEMFService(EMFService emfService) {
		this.emfService = emfService;
	}

	/**
	 * @param imageManager
	 *            the imageManager to set
	 */
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	/**
	 * @return the modelSection
	 */
	public Section getModelSection() {
		return modelSection;
	}

	protected SelectionService getSelectionService() {
		if (selectionService == null) {
			selectionService = new SelectionService();
		}
		return selectionService;
	}

	/**
	 * @return
	 */
	public GridData createFillBothData() {
		return new GridData(GridData.FILL_BOTH);
	}

	/**
	 * @param context
	 */
	public void updateDetailsViewer(PropertiesEditingContext context) {
		eefViewer.setInput(context);
		tabFolder = getEEFViewerTablFolder(previewContainer);
		if (tabFolder != null) {
			toolkit.adapt(tabFolder);
		}
	}

	private CTabFolder getEEFViewerTablFolder(Composite composite) {
		if (composite.getChildren().length > 0) {
			if (composite.getChildren()[0] instanceof CTabFolder) {
				return (CTabFolder) composite.getChildren()[0];
			}
			if (composite.getChildren()[0] instanceof Composite) {
				return getEEFViewerTablFolder((Composite) composite.getChildren()[0]);
			}
		}
		return null;
	}

	/**
	 * @return the managedForm
	 */
	public IManagedForm getManagedForm() {
		return managedForm;
	}

	/**
	 * @return the sashForm
	 */
	public SashForm getSashForm() {
		return sashForm;
	}

	/**
	 * @return the modelViewer
	 */
	public TreeViewer getModelViewer() {
		return modelViewer;
	}

	/**
	 * @return the eefViewer
	 */
	public EEFViewer getEefViewer() {
		return eefViewer;
	}

	/**
	 * @return the toolkit
	 */
	public FormToolkit getToolkit() {
		return toolkit;
	}

	/**
	 * @return the modelContainer
	 */
	public Composite getModelContainer() {
		return modelContainer;
	}

	public CTabFolder getTabFolder() {
		return tabFolder;
	}

	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	/**
	 * @return the modelViewer
	 */
	public Viewer getViewer() {
		return modelViewer;
	}

	/**
	 * @return the emfService
	 */
	public EMFService getEmfService() {
		return emfService;
	}

	/**
	 * @return the imageManager
	 */
	public ImageManager getImageManager() {
		return imageManager;
	}

	/**
	 * @return the adapterFactory
	 */
	public AdapterFactory getAdapterFactory() {
		return adapterFactory;
	}

	/**
	 * @return the previewContainer
	 */
	public Composite getPreviewContainer() {
		return previewContainer;
	}

	/**
	 * @return the previewSection
	 */
	public Section getPreviewSection() {
		return previewSection;
	}

	/**
	 * @return the form
	 */
	public ScrolledForm getForm() {
		return form;
	}

	/**
	 * @return the input
	 */
	public Object getInput() {
		return input;
	}
}
