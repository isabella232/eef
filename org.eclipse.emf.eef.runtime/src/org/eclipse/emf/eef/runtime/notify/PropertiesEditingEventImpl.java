/**
 * 
 */
package org.eclipse.emf.eef.runtime.notify;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingEventImpl implements PropertiesEditingEvent {

	private Object view;
	private Object affectedEditor;
	private int eventType;
	private Object oldValue;
	private Object newValue;

	/**
	 * @param view
	 * @param affectedEditor
	 * @param eventType
	 * @param oldValue
	 * @param newValue
	 */
	public PropertiesEditingEventImpl(Object view, Object affectedEditor, int eventType, Object oldValue, Object newValue) {
		this.view = view;
		this.affectedEditor = affectedEditor;
		this.eventType = eventType;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent#getView()
	 */
	public Object getView() {
		return view;
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

}
