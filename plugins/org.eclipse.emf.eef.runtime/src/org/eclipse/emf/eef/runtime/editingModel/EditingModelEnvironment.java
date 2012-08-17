/**
 * 
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingModelEnvironment {

	/**
	 * @return the {@link ResourceSet} used in this environment.
	 */
	ResourceSet getResourceSet();

	/**
	 * Returns the {@link GenFeature} decorating the given feature.
	 * @param feature {@link EStructuralFeature} to process.
	 * @return the {@link GenFeature} associated to the feature if accessible, <code>null</code> otherwise.
	 */
	GenFeature genFeature(EStructuralFeature feature);

}
