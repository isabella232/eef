/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock.policies.impl;

import java.util.List;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.lock.EEFPropertyLock;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent.State;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyRegistry;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFLockPolicyRegistryImpl implements EEFLockPolicyRegistry {

	private List<EEFLockPolicy> policies;
	private PropertiesEditingContext editingContext;

	/**
	 * @param editingContext the {@link PropertiesEditingContext} owning this registry.
	 */
	public EEFLockPolicyRegistryImpl(PropertiesEditingContext editingContext) {
		this.editingContext = editingContext;
		this.policies = Lists.newArrayList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyRegistry#addPolicy(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy)
	 */
	public void addPolicy(EEFLockPolicy policy) {
		policies.add(policy);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyRegistry#removePolicy(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy)
	 */
	public void removePolicy(EEFLockPolicy policy) {
		policies.remove(policy);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyRegistry#fireLockEvent(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent)
	 */
	public void fireLockEvent(final EEFLockEvent event) {
		editingContext.getEditingComponent().executeOnViewHandlers(new Function<ViewHandler<?>, Void>() {

			public Void apply(ViewHandler<?> handler) {
				if (event.lock instanceof EEFPropertyLock) {
					Object propertyEditor = editingContext.getEditingComponent().getBinding().propertyEditor(((EEFPropertyLock) event.lock).getLockedFeature(), editingContext.getOptions().autowire());
					if (handler.canHandle(propertyEditor)) {
						if (event.state == State.DISABLE) {
							handler.getLockManager().clearEditorLock(propertyEditor);
						} else {
							handler.getLockManager().lock(event.lock);
						}
					}
				} else {
					if (event.state == State.DISABLE) {
						handler.getLockManager().clearViewLock();
					} else {
						handler.getLockManager().lock(event.lock);
					}
				}
				return null;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyRegistry#dispose()
	 */
	public void dispose() {
		for (EEFLockPolicy policy : policies) {
			policy.dispose();
		}
	}

}
