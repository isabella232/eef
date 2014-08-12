/*******************************************************************************
 * Copyright (c) 2014 IFPEN.
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.tests.integration;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class EmbedingEditingEvent extends PropertiesEditingEventImpl {

	private TestEditingSettings settings;

	public EmbedingEditingEvent(Object source, Object affectedEditor, int eventType, Object oldValue, Object newValue, TestEditingSettings settings) {
		super(source, affectedEditor, eventType, oldValue, newValue);
		this.settings = settings;
	}

	/**
	 * @return the settings
	 */
	public TestEditingSettings getSettings() {
		return settings;
	}

	public abstract static class TestEditingSettings {

		private List<PropertiesEditingEvent> events;
		private boolean status;

		public TestEditingSettings(List<PropertiesEditingEvent> events, boolean status) {
			this.events = events;
			this.status = status;
		}

		/**
		 * @return the event
		 */
		public List<PropertiesEditingEvent> getEvents() {
			return events;
		}

		/**
		 * @return the status
		 */
		public boolean isStatus() {
			return status;
		}

		public abstract void testSettings(PropertiesEditingContext editingContext);
	}

	public static abstract class Builder {

		private List<PropertiesEditingEvent> events;
		private boolean status;
		private Object editor;
		private EObject editedObject;

		public Builder(Object editor, EObject editedObject) {
			this.editor = editor;
			this.editedObject = editedObject;
			this.events = Lists.newArrayList();
		}

		public abstract void test(PropertiesEditingContext editingContext);

		public Builder addEvent(PropertiesEditingEvent editingEvent) {
			events.add(editingEvent);
			return this;
		}

		public Builder addEvent(Object source, Object affectedEditor, int eventType, Object oldValue, Object newValue) {
			events.add(new PropertiesEditingEventImpl(source, affectedEditor, eventType, oldValue, newValue));
			return this;
		}

		private EmbedingEditingEvent build() {
			TestEditingSettings settings = new TestEditingSettings(events, status) {

				@Override
				public void testSettings(PropertiesEditingContext editingContext) {
					test(editingContext);
				}
			};
			if (editedObject == null) {
				return new EmbedingEditingEvent(this, editor, PropertiesEditingEvent.ADD, null, editedObject, settings);
			}
			return new EmbedingEditingEvent(this, editor, PropertiesEditingEvent.EDIT, null, editedObject, settings);
		}

		public EmbedingEditingEvent ok() {
			this.status = org.eclipse.emf.eef.runtime.tests.integration.AbstractWizardIntegrationTest.OK;
			return build();
		}

		public EmbedingEditingEvent cancel() {
			this.status = org.eclipse.emf.eef.runtime.tests.integration.AbstractWizardIntegrationTest.CANCEL;
			return build();
		}
	}

}
