/**
 * 
 */
package org.eclipse.emf.samples.conferences.policies;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.EEFComponentRegistry;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFComponent;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.samples.conference.ConferencePackage;

import com.google.common.base.Function;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LockingByAdapterPolicy extends AbstractEEFComponent implements EEFLockPolicy {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EObject element) {
		return ConferencePackage.eNS_URI.equals(element.eClass().getEPackage().getNsURI());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#isLocked(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject)
	 */
	public boolean isLocked(PropertiesEditingContext editingContext, EObject object) {
		LockAdapter existingAdapter = (LockAdapter) EcoreUtil.getExistingAdapter(object, LockAdapter.class);
		if (existingAdapter == null) {
			existingAdapter = new LockAdapter(editingContext.getEditingComponent());
			object.eAdapters().add(existingAdapter);
		} else {
			if (existingAdapter.editingComponent.getEditingContext() == null) {
				existingAdapter.editingComponent = editingContext.getEditingComponent();
			}
		}
		return existingAdapter.isLocked();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#isLocked(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean isLocked(PropertiesEditingContext editingContext, EObject object, EStructuralFeature feature) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#dispose()
	 */
	public void dispose() {
	}

	public static final class LockAdapter extends AdapterImpl {

		private boolean locked = false;
		private PropertiesEditingComponent editingComponent;
		
		public LockAdapter(PropertiesEditingComponent editingComponent) {
			this.editingComponent = editingComponent;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
		 */
		@Override
		public boolean isAdapterForType(Object type) {
			return type == LockAdapter.class || super.isAdapterForType(type);
		}

		/**
		 * @return the locked
		 */
		public boolean isLocked() {
			if (editingComponent.getEditingContext() == null) {
				this.getTarget().eAdapters().remove(this);
				return false;
			} else {
				return locked;
			}
		}

		/**
		 * @param locked the locked to set
		 */
		public void setLocked(boolean locked) {
			if (editingComponent.getEditingContext() == null) {
				this.getTarget().eAdapters().remove(this);
			} else {
				this.locked = locked;
				final EEFComponentRegistry componentRegistry = editingComponent.getEditingContext().getEMFService().getComponentRegistry();
				editingComponent.executeOnViewHandlers(new Function<ViewHandler<?>, Void>() {

					/**
					 * {@inheritDoc}
					 * @see com.google.common.base.Function#apply(java.lang.Object)
					 */
					@Override
					public Void apply(ViewHandler<?> arg0) {
						Object view = arg0.getView();
						EEFLockManager lockManager = componentRegistry.getService(EEFLockManager.class, view);
						if (LockAdapter.this.locked) {
							lockManager.lockView(view);
						} else {
							lockManager.clearViewLock(view);
						}
						return null;
					}

				});
			}
		}
		
	}

}
