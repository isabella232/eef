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
package org.eclipse.emf.eef.editor.internal.services.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class ViewLockingSettings {
	
	private Map<String, Collection<String>> lockingSettings;
	
	public static final Builder builder() {
		return new Builder();
	}

	private ViewLockingSettings(Map<String, Collection<String>> lockingSettings) {
		this.lockingSettings = lockingSettings;
	}
	
	public Collection<String> getConfiguredViews() {
		return lockingSettings.keySet();
	}
	
	public Collection<String> getLockedEditors(String viewID) {
		return lockingSettings.get(viewID);
	}
	
	public static final class Builder {

		private Map<String, Collection<String>> lockingSettings;
		
		/**
		 * 
		 */
		private Builder() {
			lockingSettings = Maps.newHashMap();
		}

		public Builder addLockSettings(String viewID, String editorID) {
			if (lockingSettings.get(viewID) == null) {
				List<String> list = Lists.newArrayList();
				lockingSettings.put(viewID, list);
			}
			lockingSettings.get(viewID).add(editorID);
			return this;
		}
		
		public ViewLockingSettings build() {
			return new ViewLockingSettings(lockingSettings);
		}
		
	}
	
}
