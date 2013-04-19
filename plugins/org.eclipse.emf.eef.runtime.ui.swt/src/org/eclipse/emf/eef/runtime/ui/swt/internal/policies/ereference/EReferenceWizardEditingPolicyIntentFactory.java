/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.ereference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent.ProcessingKind;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntentFactory;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingService;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFEditingWizard;
import org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFWizardDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class EReferenceWizardEditingPolicyIntentFactory extends AbstractEEFService<PropertiesEditingContext> implements EditingPolicyIntentFactory {

	private EEFEditingServiceProvider eefEditingServiceProvider;

	/**
	 * @param eefEditingServiceProvider the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext editingContext) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			EEFEditingService editingService = eefEditingServiceProvider.getEditingService(editingContext.getEditingComponent().getEObject());
			return editingService.isAddingInContainmentEvent(editingContext, ((SemanticPropertiesEditingContext) editingContext).getEditingEvent());
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyIntentFactory#createProcessing(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public EditingPolicyIntent createProcessing(PropertiesEditingContext editingContext) {
		EditingPolicyIntent processing = new EditingPolicyIntent();
		processing.target = editingContext.getEditingComponent().getEObject();
		processing.feature = getEditedReference((SemanticPropertiesEditingContext) editingContext);
		processing.value = defineEObjectToSet(editingContext, (EReference) processing.feature);
		if (processing.feature.isMany()) {
			processing.processingKind = ProcessingKind.ADD;
		} else {
			processing.processingKind = ProcessingKind.SET;
		}
		return processing;
	}

	/**
	 * @return the edited reference via the {@link PropertiesEditingEvent}.
	 */
	private final EReference getEditedReference(SemanticPropertiesEditingContext editingContext) {
		return eefEditingServiceProvider.getEditingService(editingContext.getEditingComponent().getEObject()).getReferenceToEdit(editingContext);
	}

	/**
	 * @param editedReference {@link EReference} to edit.
	 * @return the {@link EObject} to set in thce given {@link EReference}.
	 */
	protected EObject defineEObjectToSet(PropertiesEditingContext editingContext, EReference editedReference) {
		return createObjectAndOpenWizard(editingContext, editedReference);
	}

	private EObject createObjectAndOpenWizard(final PropertiesEditingContext editingContext, EReference editedReference) {
		editingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
		EEFEditingWizard wizard = new EEFEditingWizard(editingContext) {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFEditingWizard#attachToResource(org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.EObject)
			 */
			@Override
			protected void attachToResource(Resource resource, EObject eObject) {
				EReferenceWizardEditingPolicyIntentFactory.this.attachToResource(editingContext, resource, eObject);
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFEditingWizard#detachFromResource(org.eclipse.emf.ecore.EObject)
			 */
			@Override
			protected void detachFromResource(EObject eObject) {
				EReferenceWizardEditingPolicyIntentFactory.this.detachFromResource(editingContext, eObject);
			}

		};
		//TODO: use a UI helper for providing the shell 
		EEFWizardDialog wDialog = new EEFWizardDialog(new Shell(), wizard);
		int open = wDialog.open();
		if (open == Window.CANCEL) {
			return null;
		}

		return wizard.getCreatedObject();
	}

	/**
	 * Attaches the created object to the given {@link Resource}.
	 * @param resource {@link Resource} to process.
	 * @param createdEObject the {@link EObject} to attach.
	 */
	protected abstract void attachToResource(PropertiesEditingContext editingContext, Resource resource, EObject createdEObject);

	/**
	 * Detaches the given object from its resource if it is contained as a root element.
	 * @param eObject the {@link EObject} to attach.
	 */
	protected abstract void detachFromResource(PropertiesEditingContext editingContext, EObject eObject);
}
