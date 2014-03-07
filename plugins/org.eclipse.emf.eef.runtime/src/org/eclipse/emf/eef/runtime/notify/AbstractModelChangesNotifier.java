/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.notify;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class AbstractModelChangesNotifier extends EContentAdapter implements ModelChangesNotifier {

	/**
	 * {@link EventAdmin} used to communicate with
	 * {@link PropertiesEditingComponent}s.
	 */
	protected final EventAdmin eventAdmin;

	/**
	 * @param eventAdmin
	 */
	public AbstractModelChangesNotifier(final EventAdmin eventAdmin) {
		super();
		this.eventAdmin = eventAdmin;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	@Override
	public boolean isAdapterForType(Object type) {
		return type == ModelChangesNotifier.class || super.isAdapterForType(type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.util.EContentAdapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		String topic = getTopic(notification);
		Map<String, Object> properties = Maps.newHashMap();
		properties.put("notification", notification);
		Event event = new Event(topic, properties);
		if (eventAdmin != null) {
			eventAdmin.postEvent(event);
		}
	}

	/**
	 * @param notification
	 *            Notification
	 * @return the event topic
	 */
	public abstract String getTopic(Notification notification);

}
