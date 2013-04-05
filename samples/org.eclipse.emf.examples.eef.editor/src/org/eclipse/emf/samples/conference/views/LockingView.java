package org.eclipse.emf.samples.conference.views;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.samples.conference.policies.LockingByAdapterPolicy.LockAdapter;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class LockingView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.emf.samples.conference.views.LockingView";

	private CheckboxTreeViewer viewer;
	private AdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
	
	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new CheckboxTreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new LockingContentProvider());
		viewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		viewer.setCheckStateProvider(new ICheckStateProvider() {
			
			
			public boolean isGrayed(Object element) {
				return false;
			}
			
			
			public boolean isChecked(Object element) {
				if (element instanceof EObject) {
					if (element instanceof EStructuralFeature) {
						LockAdapter lockAdapter = (LockAdapter) EcoreUtil.getExistingAdapter((Notifier) element, LockAdapter.class);
						if (lockAdapter != null) {
							return lockAdapter.isPropertyLocked((EStructuralFeature) element);
						} else {
							return false;
						}

					} else  {
						LockAdapter lockAdapter = (LockAdapter) EcoreUtil.getExistingAdapter((Notifier) element, LockAdapter.class);
						if (lockAdapter != null) {
							return lockAdapter.isLocked();
						} else {
							return false;
						}
					}
				}
				return false;
			}
		});
		
		viewer.addCheckStateListener(new ICheckStateListener() {
			
			
			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getElement() instanceof EObject) {
					if (event.getElement() instanceof EStructuralFeature) {
						EObject eObj = lockingObject();
						LockAdapter lockAdapter = (LockAdapter) EcoreUtil.getExistingAdapter(eObj, LockAdapter.class);
						if (lockAdapter != null) {
							lockAdapter.setFeatureLocked((EStructuralFeature) event.getElement(), event.getChecked());
						}						
					} else {
						EObject eObj = (EObject) event.getElement();
						LockAdapter lockAdapter = (LockAdapter) EcoreUtil.getExistingAdapter(eObj, LockAdapter.class);
						if (lockAdapter != null) {
							lockAdapter.setLocked(event.getChecked());
						}
					}
				}
			}
		});
		
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(new ISelectionListener() {
			
			private Object lastSelection = null;
			
			
			public void selectionChanged(IWorkbenchPart part, ISelection sSelection) {
				if (sSelection instanceof StructuredSelection) {
					Object selection = ((StructuredSelection)sSelection).getFirstElement();
					if (selection instanceof EObject && selection != lastSelection) {
						viewer.setInput(new EObject[] { (EObject) selection });
						lastSelection = selection;
					}
				}
			}
		});
		
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	
	private EObject lockingObject() {
		if (viewer.getInput() instanceof EObject[]) {
			return ((EObject[])viewer.getInput())[0];
		}
		return null;
	}
	
	private static class LockingContentProvider implements ITreeContentProvider {

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		
		public void dispose() {
			
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
		 */
		
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof EObject[]) {
				return new Object[] { ((EObject[])inputElement)[0] };
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
		 */
		
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof EObject) {
				return ((EObject) parentElement).eClass().getEAllStructuralFeatures().toArray();
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
		 */
		
		public Object getParent(Object element) {
			return null;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
		 */
		
		public boolean hasChildren(Object element) {
			return element instanceof EObject && !(element instanceof EStructuralFeature);
		}
		
	}
}