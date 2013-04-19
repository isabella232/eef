/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.wizard;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingService;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.util.PropertiesEditingMessageManagerImpl;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.swt.widgets.ERadioEditor;
import org.eclipse.emf.eef.runtime.view.notify.PropertiesEditingMessageManager;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFEditingWizard extends Wizard {

	private PropertiesEditingContext context;
	private PropertiesEditingWizardPage editingPage;
	private ElementCreationWizardPage creationPage;
	private EObject createdObject;
	private PropertiesEditingContext subContext;

	/**
	 * @param context {@link PropertiesEditingContext} to use in this wizard.
	 */
	public EEFEditingWizard(PropertiesEditingContext context) {
		this.context = context;
		this.setWindowTitle(context.getEditingComponent().getEObject().eClass().getName());
		context.getOptions().setMessageManager(initMessageManager());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void addPages() {
		editingPage = new PropertiesEditingWizardPage();
		editingPage.setInput(context);
		if (context instanceof SemanticPropertiesEditingContext) {
			EEFEditingService editingService = context.getServiceRegistry().getService(EEFEditingService.class, context.getEditingComponent().getEObject());
			EMFService emfService = context.getEMFServiceProvider().getEMFService(context.getEditingComponent().getEObject().eClass().getEPackage());
			if (editingService.isAddingInContainmentEvent(context, ((SemanticPropertiesEditingContext) context).getEditingEvent())) {
				EReference editedReference = editingService.getReferenceToEdit((SemanticPropertiesEditingContext) context);	
				Collection<EClass> listOfInstanciableType = emfService.listOfInstanciableType(null, context.getEditingComponent().getEObject(), editedReference);
				if (listOfInstanciableType.size() > 0) {
					if (listOfInstanciableType.size() > 1) {
						creationPage = new ElementCreationWizardPage();
						creationPage.setInput(listOfInstanciableType);
						addPage(creationPage);
					} else {
						createdObject= EcoreUtil.create(listOfInstanciableType.iterator().next());
						createObject(createdObject);
					}
				} else {
					//FIXME: I've got a pb
				}				
			}
		}
		addPage(editingPage);
	}

	/**
	 * @return the createdObject
	 */
	public final EObject getCreatedObject() {
		return createdObject;
	}
	
	/**
	 * Attaches the given {@link EObject} to a {@link Resource}.
	 * @param resource target {@link Resource}.
	 * @param eObject the {@link EObject} to attach.
	 */
	protected void attachToResource(Resource resource, EObject eObject) {
		//Note: Only used in EReferenceWizardEditingPolicy, very tricky but I don't have a better way. 
	}
	
	/**
	 * Detaches the given {@link EObject} from its {@link Resource} if exists.
	 * @param eObject the {@link EObject} to detach.
	 */
	protected void detachFromResource(EObject eObject) {
		//Note: Only used in EReferenceWizardEditingPolicy, very tricky but I don't have a better way. 
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		context.stopEditing();
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	public boolean performCancel() {
		context.cancelEditing();
		if (createdObject != null) {
			detachFromResource(createdObject);
		}
		return true;
	}

	private PropertiesEditingMessageManager initMessageManager() {
		return new PropertiesEditingMessageManagerImpl() {
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.util.PropertiesEditingMessageManagerImpl#updateStatus(java.lang.String)
			 */
			@Override
			protected void updateStatus(String message) {
				if (editingPage != null) {
					editingPage.setMessage(message, IMessageProvider.NONE);
				}
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.util.PropertiesEditingMessageManagerImpl#updateError(java.lang.String)
			 */
			@Override
			protected void updateError(String message) {
				if (editingPage != null) {
					editingPage.setMessage(message, IMessageProvider.ERROR);
				}
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.util.PropertiesEditingMessageManagerImpl#updateWarning(java.lang.String)
			 */
			@Override
			protected void updateWarning(String message) {
				if (editingPage != null) {
					editingPage.setMessage(message, IMessageProvider.WARNING);
				}
			}
			
			
		};
	}
	
	/**
	 * @param createdObject 
	 * 
	 */
	private void createObject(EObject createdObject) {
		Resource eResource = context.getEditingComponent().getEObject().eResource();
		if (eResource != null) {
			attachToResource(eResource, createdObject);
		}
		PropertiesEditingContextFactory contextFactory = context.getServiceRegistry().getService(PropertiesEditingContextFactory.class, createdObject);
		subContext = contextFactory.createPropertiesEditingContext(context, createdObject);
		editingPage.setInput(subContext);
	}

	private class ElementCreationWizardPage extends WizardPage {

		private Collection<EClass> instanciableTypes;
		private ERadioEditor radio;

		public ElementCreationWizardPage() {
			super("Element Creation");
			setTitle("Element Creation");
			setDescription("Select the type of object you want to create.");
		}
		
		/**
		 * @param input the instanciable types
		 */
		public void setInput(Collection<EClass> input) {
			this.instanciableTypes = input;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			Composite control = new Composite(parent, SWT.NONE);
			control.setLayoutData(new GridData(GridData.FILL_BOTH));
			control.setLayout(new GridLayout(1, false));
			radio = new ERadioEditor(control, SWT.NONE);
			radio.setContentProvide(new ArrayContentProvider());
			EditUIProvidersFactory providersFactory = context.getServiceRegistry().getService(EditUIProvidersFactory.class, this);
			radio.setLabelProvider(providersFactory.createLabelProvider(context.getAdapterFactory()));
			radio.setInput(instanciableTypes);
			radio.addSelectionChangedListener(new ISelectionChangedListener() {
				

				public void selectionChanged(SelectionChangedEvent event) {
					StructuredSelection selection = (StructuredSelection) event.getSelection();
					if (selection.getFirstElement() instanceof EClass) {
						if (createdObject != null) {
							detachFromResource(createdObject);
						}
						createdObject = EcoreUtil.create((EClass) selection.getFirstElement());
						createObject(createdObject);
					}
				}
			});
			firstSelection();
			this.setControl(control);
		}

		/**
		 * 
		 */
		private void firstSelection() {
			EClass type = instanciableTypes.iterator().next();
			radio.setSelection(new StructuredSelection(type));
			createdObject = EcoreUtil.create(type);
			createObject(createdObject);
		}
	}

	private class PropertiesEditingWizardPage extends WizardPage {

		private EEFViewer viewer;
		private PropertiesEditingContext context;

		public PropertiesEditingWizardPage() {
			super("Properties Editing");
			setDescription("Edit the object properties.");
		}

		/**
		 * @param context the context to use.
		 */
		public void setInput(PropertiesEditingContext context) {
			this.context = context;
			setTitle(context.getEditingComponent().getEObject().eClass().getName());
			if (viewer != null) {
				viewer.setInput(this.context);
			}
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			Composite control = new Composite(parent, SWT.NONE);
			control.setLayoutData(new GridData(GridData.FILL_BOTH));
			control.setLayout(new GridLayout(1, false));
			viewer = new EEFViewer(control, SWT.NONE);
			viewer.setContentProvider(new EEFContentProvider());
			viewer.setInput(context);
			this.setControl(control);
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
		 */
		@Override
		public void dispose() {
			super.dispose();
			viewer.clear();
		}

	}	

}
