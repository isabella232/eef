/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.lock;

import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.view.lock.EEFLock;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFPropertyLock;
import org.eclipse.emf.eef.views.ViewElement;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingViewLockManager implements EEFLockManager {

	private PropertiesEditingView view;
	
	/**
	 * @param view
	 */
	public EditingViewLockManager(PropertiesEditingView view) {
		this.view = view;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#lock(org.eclipse.emf.eef.runtime.view.lock.EEFLock)
	 */
	public void lock(EEFLock lock) {
		if (lock instanceof EEFPropertyLock) {
			Object editor = view.getEditingComponent().getBinding().propertyEditor(((EEFPropertyLock) lock).getLockedFeature(), view.getEditingComponent().getEditingContext().getOptions().autowire());
			if (editor instanceof ViewElement) {
				PropertyEditor propertyEditor = view.getPropertyEditor((ViewElement) editor);
				if (propertyEditor != null) {
					propertyEditor.getPropertyEditorViewer().lock((EEFPropertyLock) lock);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#clearViewLock()
	 */
	public void clearViewLock() {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#clearEditorLock(java.lang.Object)
	 */
	public void clearEditorLock(Object editor) {
		// TODO Auto-generated method stub

	}

}
