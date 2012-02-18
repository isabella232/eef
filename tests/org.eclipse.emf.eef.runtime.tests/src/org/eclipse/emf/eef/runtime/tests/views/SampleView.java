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
public class SampleView extends Composite {

	private Text nameText;
	private Button activeCheckbox;
	
	private boolean notify = true;
	private PropertyChangeSupport propertyChangeSupport;

	/**
	 * @param parent
	 */
	public SampleView(Composite parent) {
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
		activeCheckbox = new Button(this, SWT.CHECK);
		activeCheckbox.setText("Active");
		GridData activeData = new GridData(GridData.FILL_HORIZONTAL);
		activeData.horizontalSpan = 3;
		activeCheckbox.setLayoutData(activeData);
		activeCheckbox.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (notify) {
					propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, "active", null, activeCheckbox.getSelection()));
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
	 * @param name value to set to the name's Text.
	 */
	public void notifiedSetName(String name) {
		if (name != null) {
			this.nameText.setText(name);
		}
	}
	
	/**
	 * @param name value to set to the name's Text.
	 */
	public void clearName() {
		notify = false;
		this.nameText.setText("");
		notify = true;
	}
	
	/**
	 * @return the active property.
	 */
	public boolean isActive() {
		return activeCheckbox.getSelection();
	}
	
	/**
	 * @param active the selection state to set to the active checkbox.
	 */
	public void setActive(Boolean active) {
		notify = false;
		this.activeCheckbox.setSelection(active);
		notify = true;
	}

	/**
	 * @param active the selection state to set to the active checkbox.
	 */
	public void notifiedSetActive(Boolean active) {
		this.activeCheckbox.setSelection(active);
	}
}
