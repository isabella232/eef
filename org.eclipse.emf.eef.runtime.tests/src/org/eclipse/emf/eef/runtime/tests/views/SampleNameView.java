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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SampleNameView extends Composite {

	private Text nameText;
	
	private boolean notify = true;
	private PropertyChangeSupport propertyChangeSupport;

	/**
	 * @param parent
	 */
	public SampleNameView(Composite parent) {
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
	
}
