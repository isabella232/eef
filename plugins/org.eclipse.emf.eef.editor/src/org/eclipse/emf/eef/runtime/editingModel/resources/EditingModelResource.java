/**
 * 
 */
package org.eclipse.emf.eef.runtime.editingModel.resources;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMIHelperImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingModelResource extends XMIResourceImpl {

	public EditingModelResource() {
		super();
	}

	public EditingModelResource(URI uri) {
		super(uri);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl#createXMLHelper()
	 */
	@Override
	protected XMLHelper createXMLHelper() {
	    return new XMIHelperImpl(this) {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl#deresolve(org.eclipse.emf.common.util.URI)
			 */
			@Override
			public URI deresolve(URI uri) {
				return uri;
			}
	    	
	    };	
	}
}
