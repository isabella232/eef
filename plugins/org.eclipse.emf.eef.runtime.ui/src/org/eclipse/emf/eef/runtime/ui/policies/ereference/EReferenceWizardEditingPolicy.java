/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.policies.ereference;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.ereference.EReferenceEditingPolicy;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.ui.wizard.EEFEditingWizard;
import org.eclipse.emf.eef.runtime.ui.wizard.EEFWizardDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class EReferenceWizardEditingPolicy extends EReferenceEditingPolicy {

	/**
	 * @param context
	 */
	public EReferenceWizardEditingPolicy(SemanticPropertiesEditingContext context) {
		super(context);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.ereference.EReferenceEditingPolicy#defineEObjectToSet(org.eclipse.emf.ecore.EReference)
	 */
	@Override
	protected EObject defineEObjectToSet(EReference editedReference) {
		return createObjectAndOpenWizard(getEditingContext(), editedReference);
	}

	private EObject createObjectAndOpenWizard(PropertiesEditingContext editingContext, EReference editedReference) {
		EObject createdEObject = null;
		if (editedReference.getEReferenceType() != null && !editedReference.getEReferenceType().isAbstract()) {
			createdEObject  = EcoreUtil.create(editedReference.getEReferenceType());
		} else {
			EMFService emfService = editingContext.getEMFService();
			Collection<EClass> listOfInstanciableType = emfService.listOfInstanciableType(null, editingContext.getEditingComponent().getEObject(), editedReference);
			if (listOfInstanciableType.size() > 0) {
				createdEObject = EcoreUtil.create(listOfInstanciableType.iterator().next());
			} else {
				//TODO: logging ?
			}
		}
		if (createdEObject != null) {
			Resource eResource = editingContext.getEditingComponent().getEObject().eResource();
			if (eResource != null) {
				attachToResource(eResource, createdEObject);
			}
			PropertiesEditingContext subContext = getEditingContext().getServiceRegistry().getService(PropertiesEditingContextFactory.class, createdEObject).createPropertiesEditingContext(editingContext.getAdapterFactory(), createdEObject);
			EEFEditingWizard wizard = new EEFEditingWizard(subContext);
			//TODO: use a UI helper for providing the shell 
			EEFWizardDialog wDialog = new EEFWizardDialog(new Shell(), wizard);
			int open = wDialog.open();
			if (open == Window.CANCEL) {
				detachFromResource(createdEObject);
				return null;
			}
			
		}
		return createdEObject;
	}

	/**
	 * Attaches the created object to the given {@link Resource}.
	 * @param resource {@link Resource} to process.
	 * @param createdEObject the {@link EObject} to attach.
	 */
	protected abstract void attachToResource(Resource resource, EObject createdEObject);

	/**
	 * Detaches the given object from its resource if it is contained as a root element.
	 * @param eObject the {@link EObject} to attach.
	 */
	protected abstract void detachFromResource(EObject eObject);
}
