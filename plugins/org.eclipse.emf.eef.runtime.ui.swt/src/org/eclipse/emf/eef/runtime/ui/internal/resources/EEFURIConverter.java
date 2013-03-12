/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFURIConverter implements URIConverter {

	private URIConverter delegatedConverter;

	/**
	 * Constructor with the {@link URIConverter} to delegate.
	 * @param delegatedConverter the delegated {@link URIConverter}.
	 */
	public EEFURIConverter(URIConverter delegatedConverter) {
		this.delegatedConverter = delegatedConverter;
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#normalize(org.eclipse.emf.common.util.URI)
	 */
	public URI normalize(URI uri) {
		return delegatedConverter.normalize(uri);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#getURIMap()
	 */
	public Map<URI, URI> getURIMap() {
		return delegatedConverter.getURIMap();
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#getURIHandlers()
	 */
	public EList<URIHandler> getURIHandlers() {
		return delegatedConverter.getURIHandlers();
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#getURIHandler(org.eclipse.emf.common.util.URI)
	 */
	public URIHandler getURIHandler(URI uri) {
		return delegatedConverter.getURIHandler(uri);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#getContentHandlers()
	 */
	public EList<ContentHandler> getContentHandlers() {
		return delegatedConverter.getContentHandlers();
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#createInputStream(org.eclipse.emf.common.util.URI)
	 */
	public InputStream createInputStream(URI uri) throws IOException {
		return delegatedConverter.createInputStream(uri);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#createInputStream(org.eclipse.emf.common.util.URI, java.util.Map)
	 */
	public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
		return delegatedConverter.createInputStream(uri, options);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#createOutputStream(org.eclipse.emf.common.util.URI)
	 */
	public OutputStream createOutputStream(URI uri) throws IOException {
		return delegatedConverter.createOutputStream(uri);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#createOutputStream(org.eclipse.emf.common.util.URI, java.util.Map)
	 */
	public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
		return delegatedConverter.createOutputStream(uri, options);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#delete(org.eclipse.emf.common.util.URI, java.util.Map)
	 */
	public void delete(URI uri, Map<?, ?> options) throws IOException {
		delegatedConverter.delete(uri, options);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#contentDescription(org.eclipse.emf.common.util.URI, java.util.Map)
	 */
	public Map<String, ?> contentDescription(URI uri, Map<?, ?> options) throws IOException {
		return delegatedConverter.contentDescription(uri, options);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#exists(org.eclipse.emf.common.util.URI, java.util.Map)
	 */
	public boolean exists(URI uri, Map<?, ?> options) {
		return delegatedConverter.exists(uri, options);
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#getAttributes(org.eclipse.emf.common.util.URI, java.util.Map)
	 */
	public Map<String, ?> getAttributes(URI uri, Map<?, ?> options) {
		@SuppressWarnings("unchecked")
		Map<String, Object> attributes = (Map<String, Object>) delegatedConverter.getAttributes(uri, options);
		if (ToolkitResource.TOOLKIT_RESOURCE_SCHEME.equals(uri.scheme())) {
			attributes.put(URIConverter.ATTRIBUTE_READ_ONLY, Boolean.TRUE);
		}
		return attributes;
	}

	/**
	 * @see org.eclipse.emf.ecore.resource.URIConverter#setAttributes(org.eclipse.emf.common.util.URI, java.util.Map, java.util.Map)
	 */
	public void setAttributes(URI uri, Map<String, ?> attributes,Map<?, ?> options) throws IOException {
		delegatedConverter.setAttributes(uri, attributes, options);
	}
	
	
	
}
