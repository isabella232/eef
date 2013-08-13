/**
 * 
 */
package org.eclipse.emf.eef.editor.internal.services;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;

import com.google.common.collect.Sets;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFService {
	
	/**
	 * Returns all resources in the ResourceSet having a EPackage as first root element in the contents.
	 * @param input ResourceSet to process
	 * @return all the Ecore Resource of the {@link ResourceSet}.
	 */
	public Collection<Resource> ecoreResources(ResourceSet input) {
		Collection<Resource> result = Sets.newHashSet();
		EcoreUtil.resolveAll(input);
		for (Resource resource : input.getResources()) {
			if (resource.getContents() != null && resource.getContents().size() > 0) {
				if (resource.getContents().get(0) instanceof EPackage) {
					result.add(resource);
				}
			}
		}
		return result;
	}

	/**
	 * Returns the first PropertiesEditingModel in the given {@link ResourceSet}.
	 */
	public PropertiesEditingModel findEditedModel(ResourceSet resourceSet) {
		for (Resource resource : resourceSet.getResources()) {
			for (EObject eObject : resource.getContents()) {
				if (eObject instanceof PropertiesEditingModel) {
					return (PropertiesEditingModel) eObject;
				}
			}
		}
		return null;
	}
}
