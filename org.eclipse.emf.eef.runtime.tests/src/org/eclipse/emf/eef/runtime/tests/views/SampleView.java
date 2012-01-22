/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.views;

import org.eclipse.swt.SWT;
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
		activeCheckbox = new Button(this, SWT.CHECK);
		activeCheckbox.setText("Active");
		GridData activeData = new GridData(GridData.FILL_HORIZONTAL);
		activeData.horizontalSpan = 3;
		activeCheckbox.setLayoutData(activeData);
	}
	
	/**
	 * @return the name's Text value.
	 */
	public String getName() {
		return nameText.getText();
	}
	
	/**
	 * @param name value to set to the name's Text.
	 */
	public void setName(String name) {
		if (name != null) {
			this.nameText.setText(name);
		}
	}
	
	/**
	 * @return the active checkbox selection state
	 */
	public boolean isActive() {
		return this.activeCheckbox.getSelection();
	}
	
	/**
	 * @param active the selection state to set to the active checkbox.
	 */
	public void setActive(boolean active) {
		this.activeCheckbox.setSelection(active);
	}
}
