/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.views;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EClassMockView implements PropertiesEditingListener {

	private PropertyChangeSupport support;
	private String name;
	private boolean abstract_;
	private Collection<EClass> eSuperTypes;
	private Collection<PropertiesEditingEvent> events;

	/**
	 * @param support
	 */
	public EClassMockView(PropertiesEditingComponent editingComponent) {
		this.support = new PropertyChangeSupport(this);
		editingComponent.addEditingListener(this);
		eSuperTypes = new ArrayList<EClass>();
		events = new ArrayList<PropertiesEditingEvent>();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the abstract_
	 */
	public boolean isAbstract() {
		return abstract_;
	}

	/**
	 * @param abstract_ the abstract_ to set
	 */
	public void setAbstract(boolean abstract_) {
		this.abstract_ = abstract_;
	}

	/**
	 * @return the eSuperTypes
	 */
	public Collection<EClass> getESuperTypes() {
		return eSuperTypes;
	}

	/**
	 * @param eSuperTypes the eSuperTypes to set
	 */
	public void addESuperTypes(EClass eSuperTypes) {
		this.eSuperTypes.add(eSuperTypes);
	}

	/**
	 * @param eSuperTypes the eSuperTypes to set
	 */
	public void removeESuperTypes(EClass eSuperTypes) {
		this.eSuperTypes.add(eSuperTypes);
	}

	/**
	 * @return the support
	 */
	public PropertyChangeSupport getSupport() {
		return support;
	}

	/**
	 * @return the events
	 */
	public Collection<PropertiesEditingEvent> getEvents() {
		return events;
	}

	/**
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public void firePropertiesChanged(PropertiesEditingEvent event) {
		events.add(event);
	}
}
