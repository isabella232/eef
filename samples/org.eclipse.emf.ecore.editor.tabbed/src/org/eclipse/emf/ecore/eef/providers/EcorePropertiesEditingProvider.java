package org.eclipse.emf.ecore.eef.providers;

import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EcorePropertiesEditingProvider extends PropertiesEditingProviderImpl {
	
	public EcorePropertiesEditingProvider() {
		System.out.println();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderImpl#getEditingModelPath()
	 */
	@Override
	protected String getEditingModelPath() {
		return "platform:/plugin/org.eclipse.emf.ecore.editor.tabbed/resources/models/ecore.editingmodel";
	}

	


}