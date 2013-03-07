/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.editingModel;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;

/**
 * This class is intended to provide information about the environment where the
 * {@link PropertiesEditingModel}s are loaded. 
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public final class EditingModelEnvironmentImpl implements EditingModelEnvironment {

	private ResourceSet resourceSet;
	private ECrossReferenceAdapter crossReferenceAdapter;
		
	/**
	 * @return the {@link ResourceSet} of this provider.
	 */
	public ResourceSet getResourceSet() {
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
			crossReferenceAdapter = new ECrossReferenceAdapter();
			resourceSet.eAdapters().add(crossReferenceAdapter);
		}
		return resourceSet;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment#genFeature(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public EObject genFeature(EStructuralFeature feature) {
		if (crossReferenceAdapter != null) {
			Collection<Setting> inverseReferences = crossReferenceAdapter.getInverseReferences(feature);
			for (Setting setting : inverseReferences) {
				if ("GenFeature".equals(setting.getEObject().eClass().getName())) {
					return setting.getEObject();
				}
			}
		}
		return null;
	}
	
}
