/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.group;

import org.eclipse.emf.eef.runtime.ui.internal.services.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.SWTPropertyEditor;
import org.eclipse.emf.eef.runtime.view.lock.EEFPropertyLock;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class GroupSWTPropertyEditor implements SWTPropertyEditor<EEFControlWrapperViewer<Composite>> {

	private Container container;
	
	private EEFControlWrapperViewer<Composite> wrapperViewer;
	private Group group;
	
	/**
	 * @param viewElement
	 */
	public GroupSWTPropertyEditor(Container viewElement) {
		this.container = viewElement;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public EEFControlWrapperViewer<Composite> getViewer() {
		if (wrapperViewer == null) {
			wrapperViewer = new EEFControlWrapperViewer<Composite>() {


				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.util.EEFControlWrapperViewer#getMainControl()
				 */
				@Override
				public Composite getMainControl() {
					return group;
				}


			};
		}
		return wrapperViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.SWTPropertyEditor#build(org.eclipse.swt.widgets.Composite)
	 */
	public void build(Composite parent) {
		group = new Group(parent, SWT.NONE);
		group.setText(container.getName());
		GridData groupLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		groupLayoutData.horizontalSpan = 3;
		group.setLayoutData(groupLayoutData);
		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = 3;
		group.setLayout(groupLayout);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock(org.eclipse.emf.eef.runtime.view.lock.EEFPropertyLock)
	 */
	public void lock(EEFPropertyLock lock) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		// Do nothing
	}
		
}
