/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.ereference;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceBatchWizardEditingPolicy extends EReferenceWizardEditingPolicyIntentFactory {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.ereference.EReferenceWizardEditingPolicyIntentFactory#serviceFor(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	@Override
	public boolean serviceFor(PropertiesEditingContext editingContext) {
		return editingContext instanceof DomainAwarePropertiesEditingContext
				&& !editingContext.getOptions().liveMode()
				&& super.serviceFor(editingContext);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.ereference.EReferenceWizardEditingPolicy#attachToResource(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void attachToResource(PropertiesEditingContext editingContext, final Resource resource, final EObject createdEObject) {
		((DomainAwarePropertiesEditingContext) editingContext).getEditingDomain().getCommandStack().execute(new AbstractCommand() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.common.command.Command#execute()
			 */
			public void execute() {
				resource.getContents().add(createdEObject);
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
			 */
			@Override
			public void undo() {
				resource.getContents().remove(createdEObject);
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.common.command.Command#redo()
			 */
			public void redo() {
				resource.getContents().add(createdEObject);
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
			 */
			@Override
			protected boolean prepare() {
				return true;
			}
			
		});
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.ereference.EReferenceWizardEditingPolicy#detachFromResource(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void detachFromResource(PropertiesEditingContext editingContext, final EObject eObject) {
		((DomainAwarePropertiesEditingContext) editingContext).getEditingDomain().getCommandStack().execute(new AbstractCommand() {

			private Resource objectResource;
			private EObject focusedEObject;

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.common.command.Command#execute()
			 */
			public void execute() {
				this.focusedEObject = eObject;
				if (eObject.eResource() != null) {
					objectResource = eObject.eResource();
					if (objectResource.getContents().contains(focusedEObject)) {
						objectResource.getContents().remove(focusedEObject);
					}
				}
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
			 */
			@Override
			public void undo() {
				objectResource.getContents().add(focusedEObject);
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.common.command.Command#redo()
			 */
			public void redo() {
				if (objectResource.getContents().contains(focusedEObject)) {
					objectResource.getContents().remove(focusedEObject);
				}
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
			 */
			@Override
			protected boolean prepare() {
				return true;
			}
			
		});
	}

}
