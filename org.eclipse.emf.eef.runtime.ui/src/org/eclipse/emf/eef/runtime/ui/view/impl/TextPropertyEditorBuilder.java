/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.impl;

import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.PropertyEditorBuilder;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TextPropertyEditorBuilder extends PropertyEditorBuilder {

	private Text text;

	/**
	 * @param view
	 * @param editorID
	 */
	public TextPropertyEditorBuilder(PropertiesEditingView view, ElementEditor editor) {
		super(view, editor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertyEditorBuilder#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	protected void createEditorContents(Composite parent) {
		text = new Text(parent, SWT.BORDER);
		GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(nameData);
		text.addFocusListener(new FocusAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (view.getEditingComponent() != null)
					//TODO
					view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor.getQualifiedIdentifier(), 1, null, text.getText()));
			}

		});
		text.addKeyListener(new KeyAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (view.getEditingComponent() != null)
						//TODO
						view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor.getQualifiedIdentifier(), 1, null, text.getText()));
				}
			}

		});
		view.getViewHelper().setID(text, elementEditor.getQualifiedIdentifier());
		view.getViewHelper().setEEFtype(text, "eef::Text"); //$NON-NLS-1$
	}

}
