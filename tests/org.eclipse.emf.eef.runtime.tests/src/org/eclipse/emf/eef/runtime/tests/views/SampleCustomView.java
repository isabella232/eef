/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SampleCustomView extends Composite {

	private Text nameText;
	private Button abstractCheckbox;
	
	private boolean notify = true;
	private PropertyChangeSupport propertyChangeSupport;

	/**
	 * @param parent
	 */
	public SampleCustomView(Composite parent) {
		super(parent, SWT.NONE);
		this.setLayout(new GridLayout(3,false));
		Label nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Name:");
		nameText = new Text(this, SWT.BORDER);
		GridData textData = new GridData(GridData.FILL_HORIZONTAL);
		textData.horizontalSpan = 2;
		nameText.setLayoutData(textData);
		nameText.addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent e) {
				if (notify) {
					PropertyChangeEvent propertyChangeEvent = new PropertyChangeEvent(this, "name", null, nameText.getText());
					propertyChangeSupport.firePropertyChange(propertyChangeEvent);
				}
			}
		});
		abstractCheckbox = new Button(this, SWT.CHECK);
		abstractCheckbox.setText("Active");
		GridData activeData = new GridData(GridData.FILL_HORIZONTAL);
		activeData.horizontalSpan = 3;
		abstractCheckbox.setLayoutData(activeData);
		abstractCheckbox.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (notify) {
					propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, "active", null, abstractCheckbox.getSelection()));
				}
			}
			
		});
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	
	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * @return the name property.
	 */
	public String getName() {
		return nameText.getText();
	}
	
	/**
	 * @param name value to set to the name's Text.
	 */
	public void setName(String name) {
		if (name != null) {
			notify = false;
			this.nameText.setText(name);
			notify = true;
		}
	}
	
	/**
	 * @return the active property.
	 */
	public boolean isAbstract() {
		return abstractCheckbox.getSelection();
	}
	
	/**
	 * @param abstract_ the selection state to set to the active checkbox.
	 */
	public void setAbstractState(Boolean abstract_) {
		notify = false;
		this.abstractCheckbox.setSelection(abstract_);
		notify = true;
	}
}
