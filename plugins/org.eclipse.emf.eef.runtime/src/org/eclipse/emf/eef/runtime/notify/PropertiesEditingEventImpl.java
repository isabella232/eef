/**
 * 
 */
package org.eclipse.emf.eef.runtime.notify;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingEventImpl implements PropertiesEditingEvent {

	private Object source;
	private Object affectedEditor;
	private int eventType;
	private Object oldValue;
	private Object newValue;
	
	private List<PropertiesEditingListener> holders;

	/**
	 * @param source the source of this event.
	 * @param affectedEditor the edited control. 
	 * @param eventType the type of this event. Must be a constant from {@link PropertiesEditingEvent} interface.
	 * @param oldValue the old value.
	 * @param newValue the new value.
	 */
	public PropertiesEditingEventImpl(Object source, Object affectedEditor, int eventType, Object oldValue, Object newValue) {
		this.source = source;
		this.affectedEditor = affectedEditor;
		this.eventType = eventType;
		this.oldValue = oldValue;
		this.newValue = newValue;
		holders = Lists.newArrayList();
		if (source instanceof PropertiesEditingListener) {
			addHolder((PropertiesEditingListener) source);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#getSource()
	 */
	public Object getSource() {
		return source;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#getAffectedEditor()
	 */
	public Object getAffectedEditor() {
		return affectedEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#getEventType()
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#getOldValue()
	 */
	public Object getOldValue() {
		return oldValue;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#getNewValue()
	 */
	public Object getNewValue() {
		return newValue;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#addHolder(org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener)
	 */
	public void addHolder(PropertiesEditingListener holder) {
		holders.add(holder);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#hold(org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener)
	 */
	public boolean hold(PropertiesEditingListener listener) {
		return holders.contains(listener);
	}

}
