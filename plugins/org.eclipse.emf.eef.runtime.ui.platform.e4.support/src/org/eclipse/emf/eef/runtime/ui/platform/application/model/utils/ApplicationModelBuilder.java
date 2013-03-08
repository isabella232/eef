/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.application.model.utils;

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
	public void addEEFPartDescriptor() {
		MPartDescriptor eefPartDescriptor = MBasicFactory.INSTANCE.createPartDescriptor();
		eefPartDescriptor.setElementId(EEF_PART_DESCRIPTOR);
		eefPartDescriptor.setLabel("Model");
		eefPartDescriptor.setContributionURI("bundleclass://org.eclipse.emf.eef.runtime.ui.platform.e4/org.eclipse.emf.eef.runtime.ui.platform.parts.E4EEFPart");
		applicationModel.getDescriptors().add(eefPartDescriptor);
	}
	
	
}
