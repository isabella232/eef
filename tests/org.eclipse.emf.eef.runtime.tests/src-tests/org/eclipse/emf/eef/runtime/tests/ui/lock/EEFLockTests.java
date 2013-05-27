/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui.lock;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.view.lock.impl.EEFLockManagerProviderImpl;
import org.eclipse.emf.eef.runtime.internal.view.lock.policies.impl.EEFLockPolicyFactoryProviderImpl;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.impl.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent.LockState;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactoryProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFPropertyLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.impl.EEFPropertyLockEventImpl;
import org.junit.Test;

/**
 * This case tests the EEF Lock system.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class EEFLockTests extends UIEditingTestCase {

	private TestLockManager testLockManager;
	private LockPolicy1Factory policy1Factory;
	private Builder myBuilder;


	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.tests.ui.cases.UIEditingTestCase#initEnvironmentBuilder()
	 */
	@Override
	protected Builder initEnvironmentBuilder() {
		myBuilder = super.initEnvironmentBuilder();
		myBuilder.setLockManagerProvider(createLockManagerProvider());		
		myBuilder.setLockPoliciesFactoryProvider(createLockPoliciesFactoryProvider());
		return myBuilder;
	}
	
 	private EEFLockPolicyFactoryProvider createLockPoliciesFactoryProvider() {
 		EEFLockPolicyFactoryProviderImpl factoryProvider = new EEFLockPolicyFactoryProviderImpl();
 		
		policy1Factory = new LockPolicy1Factory();
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, policy1Factory.getClass().getName());
		try {
			factoryProvider.addService(policy1Factory, properties);
		} catch (PriorityCircularityException e) {
			//SHOULDN'T OCCUR
		}
		return factoryProvider;
	}

	private EEFLockManagerProvider createLockManagerProvider() {
		EEFLockManagerProviderImpl managerProvider = new EEFLockManagerProviderImpl();
		testLockManager = new TestLockManager();
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, testLockManager.getClass().getName());
		try {
			managerProvider.addService(testLockManager, properties);
		} catch (PriorityCircularityException e) {
			//SHOULDN'T OCCUR
		}
		return managerProvider;
	}

	/**
	 * This test ensures that the lockPolicy is called at view initialization. The LockManager should have lock the SampleView.
	 */
	@Test
	public void testViewsInit() {
		org.eclipse.emf.eef.runtime.tests.ui.lock.EEFLockTests.TestLockManager.LockViewConfig lockViewConfig = testLockManager.lockConfigs.get(views.get(0));
		assertTrue("The lock manager doesn't have properly work during initialization.", lockViewConfig.locked);
	}
	
 	/**
	 * This test ensures that an LockEvent sent from a lockPolicy is properly processed by the LockManager.
	 */
	@Test
	public void testDynamicLock() {
		policy1Factory.getLockPolicy().fireFeatureLock(EcorePackage.Literals.ECLASS__ABSTRACT, LockState.LOCKED);
		org.eclipse.emf.eef.runtime.tests.ui.lock.EEFLockTests.TestLockManager.LockViewConfig lockViewConfig = testLockManager.lockConfigs.get(views.get(0));
		assertTrue("The lock manager doesn't have properly lock a view editor.", lockViewConfig.lockedEditors.contains("abstract"));
	}
	
	private final class TestLockManager implements EEFLockManager {

		private Map<Object, LockViewConfig> lockConfigs;
		
		public TestLockManager() {
			lockConfigs = new HashMap<Object, EEFLockTests.TestLockManager.LockViewConfig>();
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.ui.view.lock.EditingViewLockManager#serviceFor(java.lang.Object)
		 */
		public boolean serviceFor(Object element) {
			return true;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.ui.view.lock.EditingViewLockManager#initView(java.lang.Object)
		 */
		public void initView(Object view) {
			if (view instanceof SampleView) {
				Collection<EEFLockPolicy> policies = EEFLockTests.this.editingContext.getEditingComponent().getLockPolicies();
				checkViewLockingTowardsPolicies((SampleView) view, EEFLockTests.this.editedObject, policies);
			}
		}

		private void checkViewLockingTowardsPolicies(SampleView view, EObject editedEObject, Collection<EEFLockPolicy> policies) {
			for (EEFLockPolicy lockPolicy : policies) {
				if (lockPolicy.isLocked(null, editedEObject)) {
					lockView(view);
				}
			}
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.ui.view.lock.EditingViewLockManager#lockView(java.lang.Object)
		 */
		public void lockView(Object view) {
			LockViewConfig config = getOrCreateLockConfig(view);
			config.locked = true;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.ui.view.lock.EditingViewLockManager#lockEditor(java.lang.Object, java.lang.Object)
		 */
		public void lockEditor(Object view, Object editor) {
			LockViewConfig config = getOrCreateLockConfig(view);
			config.lockedEditors.add(editor);
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.ui.view.lock.EditingViewLockManager#clearViewLock(java.lang.Object)
		 */
		public void clearViewLock(Object view) {
			LockViewConfig config = getOrCreateLockConfig(view);
			config.locked = false;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.ui.view.lock.EditingViewLockManager#clearEditorLock(java.lang.Object, java.lang.Object)
		 */
		public void clearEditorLock(Object view, Object editor) {
			LockViewConfig config = getOrCreateLockConfig(view);
			config.lockedEditors.remove(editor);
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService#getServiceRegistry()
		 */
		public EEFServiceRegistry getServiceRegistry() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService#setServiceRegistry(org.eclipse.emf.eef.runtime.services.EEFServiceRegistry)
		 */
		public void setServiceRegistry(EEFServiceRegistry serviceRegistry) {
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#fireLockChange(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object, org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent)
		 */
		public void fireLockChange(PropertiesEditingComponent editingComponent, Object view, EEFLockEvent lockEvent) {
			if (lockEvent instanceof EEFPropertyLockEvent) {
				Object editor = editingComponent.getBinding().propertyEditor(editedObject, ((EEFPropertyLockEvent) lockEvent).getLockedFeature(), editingComponent.getEditingContext().getOptions().autowire());
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

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService#providedServices()
		 */
		public Collection<String> providedServices() {
			Collection<String> result = new ArrayList<String>();
			result.add(EEFLockManager.class.getName());
			return result;
		}

		private LockViewConfig getOrCreateLockConfig(Object view) {
			LockViewConfig config = lockConfigs.get(view);
			if (config == null) {
				config = new LockViewConfig(view);
				lockConfigs.put(view, config);
			}
			return config;
		}

		private class LockViewConfig {
			
			private Object view;
			private boolean locked;
			private Set<Object> lockedEditors;
			
			public LockViewConfig(Object view) {
				this.view = view;
				this.lockedEditors = new HashSet<Object>();
			}
			
		}
		
	}
		
	private class LockPolicy1Factory implements EEFLockPolicyFactory {

		private LockPolicy1 lockPolicy;

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
		 */
		public boolean serviceFor(EObject element) {
			return true;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.services.EEFService#setServiceRegistry(org.eclipse.emf.eef.runtime.services.EEFServiceRegistry)
		 */
		public void setServiceRegistry(EEFServiceRegistry serviceRegistry) { }

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.services.EEFService#getServiceRegistry()
		 */
		public EEFServiceRegistry getServiceRegistry() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.services.EEFService#providedServices()
		 */
		public Collection<String> providedServices() {
			Collection<String> result = new ArrayList<String>();
			result.add(EEFLockPolicyFactory.class.getName());
			return result;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory#createLockPolicy()
		 */
		public EEFLockPolicy createLockPolicy() {
			if (lockPolicy == null) {
				lockPolicy = new LockPolicy1();
			}
			return lockPolicy;
		}

		/**
		 * @return the lockPolicy
		 */
		public LockPolicy1 getLockPolicy() {
			return lockPolicy;
		}

		private class LockPolicy1 implements EEFLockPolicy {
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#isLocked(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject)
			 */
			public boolean isLocked(PropertiesEditingContext editingContext, EObject object) {
				return object.eClass() == EcorePackage.Literals.ECLASS;
			}
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#isLocked(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
			 */
			public boolean isLocked(PropertiesEditingContext editingContext, EObject object, EStructuralFeature feature) {
				return EcorePackage.Literals.ECLASS__ABSTRACT == feature;
			}
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#dispose()
			 */
			public void dispose() {
			}
			
			public void fireFeatureLock(EStructuralFeature feature, LockState state ) {
				PropertiesEditingComponent editingComponent = EEFLockTests.this.editingContext.getEditingComponent();
				myBuilder.getBindingManagerProvider().getBindingManager(editingComponent).fireLockChanged(editingComponent, new EEFPropertyLockEventImpl(EEFLockTests.this.editedObject, feature, state));
			}
			
		}

	}
	
}
