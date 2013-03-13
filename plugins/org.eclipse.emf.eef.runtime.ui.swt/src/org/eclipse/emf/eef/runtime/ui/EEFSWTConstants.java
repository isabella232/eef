/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui;

import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * UI Interface Constants for EEF swt-dependant.
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public interface EEFSWTConstants {

	/**
	 * Defines the initial size for the Editing wizard.
	 */
	final Point INITIAL_WIZARD_SIZE = new Point(650, 800);
	
	/**
	 * Defines a constant for the FormToolkit option's key.
	 */
	final String FORM_TOOLKIT = "FORM_TOOLKIT";

	/**
	 * Defines the default image to use as error decorator.
	 */
	final Image ERROR_DECORATOR = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage();

	/**
	 * Defines the default image to use as error decorator.
	 */
	final Image WARNING_DECORATOR = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_WARNING).getImage();

	/**
	 * Defines the default image to use as error decorator.
	 */
	final Image LOCK_DECORATOR = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL).getImage();

}
