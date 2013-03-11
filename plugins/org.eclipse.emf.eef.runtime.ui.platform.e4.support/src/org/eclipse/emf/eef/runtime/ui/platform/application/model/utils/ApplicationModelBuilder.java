/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.application.model.utils;

import java.util.List;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.descriptor.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ApplicationModelBuilder {

	public static final String EEF_PART_DESCRIPTOR = "org.eclipse.emf.example.eef.application.eefpartdescriptor";

	private MApplication applicationModel;

	/**
	 * @param applicationModel The {@link MApplication} to update
	 */
	public ApplicationModelBuilder(MApplication applicationModel) {
		this.applicationModel = applicationModel;
	}
	

	/**
	 * Adds the EEF PartDescriptor to the managed ApplicationModel.
	 */
	public void addPartDescriptorIfNeeded(String partDescriptorID) {
		MPartDescriptor eefPartDescriptor = findPartDescriptor(partDescriptorID);
		if (eefPartDescriptor == null) {
			eefPartDescriptor = MBasicFactory.INSTANCE.createPartDescriptor();
			eefPartDescriptor.setElementId(partDescriptorID);
			configurePartDescriptor(partDescriptorID, eefPartDescriptor);
			applicationModel.getDescriptors().add(eefPartDescriptor);
		}
	}


	/**
	 * @param partDescriptorID
	 * @param eefPartDescriptor
	 */
	protected void configurePartDescriptor(String partDescriptorID, MPartDescriptor eefPartDescriptor) {
		if (EEF_PART_DESCRIPTOR.equals(partDescriptorID)) {
			eefPartDescriptor.setLabel("Model");
			eefPartDescriptor.setDirtyable(true);
			eefPartDescriptor.setAllowMultiple(true);		
			eefPartDescriptor.setCloseable(true);
			eefPartDescriptor.setContributionURI("bundleclass://org.eclipse.emf.eef.runtime.ui.platform.e4.support/org.eclipse.emf.eef.runtime.ui.platform.application.parts.E4EEFPart");
		}
	}


	private MPartDescriptor findPartDescriptor(String partDescriptorID) {
		MPartDescriptor eefPartDescriptor = null;
		for (MPartDescriptor descriptor : applicationModel.getDescriptors()) {
			if (partDescriptorID.equals(descriptor.getElementId())) {
				eefPartDescriptor = descriptor;
			}
		}
		return eefPartDescriptor;
	}
	
	
}
