/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.group;

import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class GroupFormPropertyEditor implements FormPropertyEditor<EEFControlWrapperViewer<Composite>> {

	private Container container;
	
	private EEFControlWrapperViewer<Composite> wrapperViewer;
	private Composite group;
	
	/**
	 * @param container
	 */
	public GroupFormPropertyEditor(Container container) {
		this.container = container;
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
				 * @see org.eclipse.emf.eef.runtime.ui.propertyeditors.util.EEFControlWrapperViewer#getMainControl()
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
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.FormPropertyEditor#build(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	public void build(FormToolkit toolkit, Composite parent) {
		Section section = toolkit.createSection(parent, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		section.setText(container.getName());
		GridData sectionLayoutData = new GridData(GridData.FILL_HORIZONTAL);
		sectionLayoutData.horizontalSpan = 3;
		section.setLayoutData(sectionLayoutData);
		group = toolkit.createComposite(section);
		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = 3;
		group.setLayout(groupLayout);
		section.setClient(group);		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
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
