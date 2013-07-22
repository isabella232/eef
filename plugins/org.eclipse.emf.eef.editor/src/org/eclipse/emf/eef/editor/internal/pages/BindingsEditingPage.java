/**
 * 
 */
package org.eclipse.emf.eef.editor.internal.pages;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.UIConstants;
import org.eclipse.emf.eef.editor.internal.services.EMFService;
import org.eclipse.emf.eef.editor.internal.widgets.MultiEEFViewer;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IFormColors;
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
public class BindingsEditingPage extends FormPage {

	private EMFService emfService;
	
	private AdapterFactory adapterFactory;
	private ResourceSet input;
	
	private TreeViewer metamodelViewer;
	private TreeViewer modelViewer;
	private MultiEEFViewer bindingSettingsViewer;

	private SelectionBroker selectionBroker;

	/**
	 * @param editor
	 * @param adapterFactory 
	 */
	public BindingsEditingPage(FormEditor editor, AdapterFactory adapterFactory) {
		super(editor, "eef-binding-editing", "Bindings");
		this.adapterFactory = adapterFactory;
	}
	
	/**
	 * @param emfService the emfService to set
	 */
	public void setEMFService(EMFService emfService) {
		this.emfService = emfService;
	}

	/**
	 * Defines the page input.
	 * @param input page input.
	 */
	public void setInput(Object input) {
		if (input instanceof ResourceSet) {
			this.input = (ResourceSet)input;
			if (metamodelViewer != null) {
				Collection<Resource> ecoreResources = emfService.ecoreResources(this.input);
				metamodelViewer.setInput(ecoreResources);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		Form form = managedForm.getForm().getForm();
		toolkit.decorateFormHeading(form);
		Composite parent = form.getBody();
		parent.setLayout(new FillLayout());
		Composite container = toolkit.createComposite(parent, SWT.BORDER);
		container.setLayout(new GridLayout(2, false));
		createModelSection(toolkit, container);
		createBindingSettingsSection(toolkit, container);
		createPreviewSection(toolkit, container);
		initSelectionBroker();
		metamodelViewer.addSelectionChangedListener(selectionBroker);
	}

	private void createModelSection(FormToolkit toolkit, Composite container) {
		Section modelSection = toolkit.createSection(container, Section.TITLE_BAR);
		modelSection.setText("Models");
		GridData layoutData = new GridData(GridData.FILL_VERTICAL);
		layoutData.verticalSpan = 2;
		modelSection.setLayoutData(layoutData);
		Composite modelContainer = toolkit.createComposite(modelSection);
		modelContainer.setLayout(new GridLayout(1, false));
		createModelSectionContents(toolkit, modelContainer);
		modelSection.setClient(modelContainer);
	}

	private void createBindingSettingsSection(FormToolkit toolkit, Composite container) {
		Section bindingSettingsSection = toolkit.createSection(container, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		bindingSettingsSection.setText("Binding Settings");
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = 500;
		bindingSettingsSection.setLayoutData(layoutData);
		Composite bindingSettingsContainer = toolkit.createComposite(bindingSettingsSection);
		bindingSettingsContainer.setLayout(new GridLayout(1, false));
		createBindingSettingsSectionContents(toolkit, bindingSettingsContainer);
		bindingSettingsSection.setClient(bindingSettingsContainer);
	}

	private void createPreviewSection(FormToolkit toolkit, Composite container) {
		Section previewSection = toolkit.createSection(container, Section.TITLE_BAR);
		previewSection.setText("Preview");
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		previewSection.setLayoutData(layoutData);
		Composite previewContainer = toolkit.createComposite(previewSection);
		previewContainer.setLayout(new GridLayout(1, false));
		createPreviewSectionContents(toolkit, previewContainer);
		previewSection.setClient(previewContainer);
	}

	private void createModelSectionContents(FormToolkit toolkit, Composite modelContainer) {
		CTabFolder tabFolder = new CTabFolder(modelContainer, SWT.NONE);
		tabFolder.setSimple(false);
		toolkit.adapt(tabFolder);
		toolkit.getColors().initializeSectionToolBarColors();
		tabFolder.setSelectionBackground(
				new Color[] {toolkit.getColors().getColor(IFormColors.H_GRADIENT_START), toolkit.getColors().getColor(IFormColors.H_GRADIENT_END)}, 
				new int[] {50}, 
				true);
		tabFolder.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		tabFolder.setSelectionForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.widthHint = UIConstants.WIDTH_HINT;
		tabFolder.setLayoutData(layoutData);
		createMetamodelViewer(toolkit, tabFolder);
		createModelViewer(toolkit, tabFolder);
	}

	private void createMetamodelViewer(FormToolkit toolkit, CTabFolder tabFolder) {
		Tree metamodelTree = toolkit.createTree(tabFolder, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.SINGLE);
		metamodelViewer = new TreeViewer(metamodelTree);
		metamodelViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory) {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
			 */
			@Override
			public Object[] getElements(Object object) {
				if (object instanceof Collection<?>) {
					@SuppressWarnings("unchecked")
					Collection<Resource> collection = (Collection<Resource>) object;
					List<EPackage> result = Lists.newArrayList();
					for (Resource resource : collection) {
						for (EObject eObject : resource.getContents()) {
							if (eObject instanceof EPackage) {
								result.add((EPackage) eObject);
							}
						}
					}
					return result.toArray();
				}
				return super.getElements(object);
			}
			
		});
		metamodelViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		if (input != null) {
			Collection<Resource> ecoreResources = emfService.ecoreResources(this.input);
			metamodelViewer.setInput(ecoreResources);
		}
		CTabItem metamodelItem = new CTabItem(tabFolder, SWT.NONE);
		metamodelItem.setControl(metamodelTree);
		metamodelItem.setText("Metamodel");
	}

	private void createModelViewer(FormToolkit toolkit, CTabFolder tabFolder) {
		Tree modelTree = toolkit.createTree(tabFolder, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		modelViewer = new TreeViewer(modelTree);
		CTabItem modelItem = new CTabItem(tabFolder, SWT.NONE);
		modelItem.setControl(modelTree);
		modelItem.setText("Model");
	}

	private void createBindingSettingsSectionContents(FormToolkit toolkit, Composite bindingSettingsContainer) {
		bindingSettingsViewer = new MultiEEFViewer(bindingSettingsContainer, SWT.NONE);
		bindingSettingsViewer.setContentProvider(new EEFContentProvider());
		bindingSettingsViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
	}

	private void createPreviewSectionContents(FormToolkit toolkit, Composite previewContainer) {
		toolkit.createLabel(previewContainer, "Preview");		
	}
	
	/**
	 * @return the selectionBroker
	 */
	public void initSelectionBroker() {
		selectionBroker = new SelectionBroker(metamodelViewer, bindingSettingsViewer);
	}

	private static final class SelectionBroker implements ISelectionChangedListener {

		private TreeViewer metamodelViewer;
		private MultiEEFViewer bindingSettingsViewer;
		
		public SelectionBroker(TreeViewer metamodelViewer, MultiEEFViewer bindingSettingsViewer) {
			this.metamodelViewer = metamodelViewer;
			this.bindingSettingsViewer = bindingSettingsViewer;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource() == metamodelViewer) {
				EObject selection = unwrapSelection(event.getSelection());
				bindingSettingsViewer.setInput(selection);
			}
			
		}
		
		private <T> T unwrapSelection(ISelection selection) {
			if (selection instanceof StructuredSelection) {
				StructuredSelection sSel = (StructuredSelection)selection;
				if (sSel.size() > 1) {
					return (T) sSel.toList();
				} else {
					return (T) sSel.getFirstElement();
				}
			}
			return null;
		}
		
	}

}
