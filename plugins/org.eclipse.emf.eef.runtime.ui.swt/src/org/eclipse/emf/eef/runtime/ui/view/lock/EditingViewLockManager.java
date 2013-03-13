/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.lock;

import java.util.Collection;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFPropertyLockEvent;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.view.notify.impl.LockNotification;
import org.eclipse.emf.eef.runtime.view.notify.impl.PropertyLockNotification;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingViewLockManager extends AbstractEEFService<Object> implements EEFLockManager {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object element) {
		return element instanceof PropertiesEditingView;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#initView(java.lang.Object)
	 */
	public void initView(Object view) {
		if (view instanceof PropertiesEditingView) {
			PropertiesEditingView<Composite> editingView = (PropertiesEditingView<Composite>) view;
			PropertiesEditingComponent editingComponent = editingView.getEditingComponent();
			EObject editedEObject = editingComponent.getEObject();
			Collection<EEFLockPolicy> policies = editingComponent.getLockPolicies();
			boolean autowire = editingComponent.getEditingContext().getOptions().autowire();
			EMFService emfService = serviceRegistry.getService(EMFService.class, editedEObject.eClass().getEPackage());

			checkViewLockingTowardsPolicies(editingView, editedEObject, policies);
			checkEditorsLockingTowardPolicies(editingView, editingComponent, editedEObject, policies, autowire, emfService);
		}
	}

	private void checkViewLockingTowardsPolicies(PropertiesEditingView<Composite> editingView, EObject editedEObject, Collection<EEFLockPolicy> policies) {
		for (EEFLockPolicy lockPolicy : policies) {
			if (lockPolicy.isLocked(editingView.getEditingComponent().getEditingContext(), editedEObject)) {
				lockView(editingView);
			}
		}
	}

	private void checkEditorsLockingTowardPolicies(PropertiesEditingView<Composite> editingView, PropertiesEditingComponent editingComponent,
			EObject editedEObject, Collection<EEFLockPolicy> policies, boolean autowire, EMFService emfService) {
		TreeIterator<EObject> viewContents = editingView.getViewModel().eAllContents();
		while (viewContents.hasNext()) {
			EObject next = viewContents.next();
			EStructuralFeature feature = emfService.mapFeature(editedEObject, editingComponent.getBinding().feature(next, autowire));
			if (next instanceof ElementEditor) {
				for (EEFLockPolicy lockPolicy : policies) {
					if (lockPolicy.isLocked(editingView.getEditingComponent().getEditingContext(), editedEObject, feature)) {
						lockEditor(editingView, next);
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#lockView(java.lang.Object)
	 */
	public void lockView(Object view) {
		if (view instanceof PropertiesEditingView) {
			((PropertiesEditingView<Composite>) view).lock();
			EEFNotifier notifier = getServiceRegistry().getService(EEFNotifier.class, view);
			notifier.notify(view, new LockNotification("This view is locked."));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#lockEditor(java.lang.Object, java.lang.Object)
	 */
	public void lockEditor(Object view, Object editor) {
		if (view instanceof PropertiesEditingView && editor instanceof ViewElement) {
			PropertyEditor propertyEditor = ((PropertiesEditingView<Composite>) view).getPropertyEditor((ViewElement) editor);
			if (propertyEditor != null) {
				propertyEditor.getPropertyEditorViewer().lock();
				EEFNotifier notifier = getServiceRegistry().getService(EEFNotifier.class, view);
				notifier.notify(view, new PropertyLockNotification(editor, "This editor is locked."));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#clearViewLock(java.lang.Object)
	 */
	public void clearViewLock(Object view) {
		if (view instanceof PropertiesEditingView) {
			((PropertiesEditingView<Composite>) view).unlock();
			EEFNotifier notifier = getServiceRegistry().getService(EEFNotifier.class, view);
			notifier.clearViewNotification(view);
		}		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#clearEditorLock(java.lang.Object, java.lang.Object)
	 */
	public void clearEditorLock(Object view, Object editor) {
		if (view instanceof PropertiesEditingView && editor instanceof ViewElement) {
			PropertyEditor propertyEditor = ((PropertiesEditingView<Composite>) view).getPropertyEditor((ViewElement) editor);
			if (propertyEditor != null) {
				propertyEditor.getPropertyEditorViewer().unlock();
				EEFNotifier notifier = getServiceRegistry().getService(EEFNotifier.class, view);
				notifier.clearEditorNotification(view, editor);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#fireLockChange(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object, org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent)
	 */
	public void fireLockChange(PropertiesEditingComponent editingComponent, Object view, EEFLockEvent lockEvent) {
		if (lockEvent instanceof EEFPropertyLockEvent) {
			Object editor = editingComponent.getBinding().propertyEditor(editingComponent.getEObject(), ((EEFPropertyLockEvent) lockEvent).getLockedFeature(), editingComponent.getEditingContext().getOptions().autowire());
			if (lockEvent.getState() == EEFLockEvent.LockState.LOCKED) {
				lockEditor(view, editor);
			} else {
				clearEditorLock(view, editor);
			}
		} else {
			if (lockEvent.getState() == EEFLockEvent.LockState.LOCKED) {
				lockView(view);
			} else {
				clearViewLock(view);
			}
		}
	}
}
