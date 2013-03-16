/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.tabbed.internal.view.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.eef.runtime.ui.swt.e3.tabbed.EEFRuntimeTabbed;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DescriptorHelper {

	private TabbedPropertySheetPage tabbedPropertySheetPage;
	private ISection section;
	
	/**
	 * @param tabbedPropertySheetPage
	 * @param section
	 */
	public DescriptorHelper(TabbedPropertySheetPage tabbedPropertySheetPage, ISection section) {
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
		this.section = section;
	}

	/**
	 * Magic method For eclipse 3.2 & 3.3 & 3.4 & 3.5
	 * 
	 * @return
	 */
	public String getDescriptor() {
		Map<?, ?> descriptor = getPageDescriptor(tabbedPropertySheetPage);
		for (Iterator<?> iterator = descriptor.keySet().iterator(); iterator.hasNext();) {
			Object key = iterator.next();
			Object tab = descriptor.get(key);
			Method getSectionAtIndex = getMethod(tab, "getSectionAtIndex", int.class); //$NON-NLS-1$
			if (getSectionAtIndex != null) {
				Object result = callMethod(tab, getSectionAtIndex, new Integer(0));
				if (result == section) {
					Method getId = getMethod(key, "getId"); //$NON-NLS-1$
					if (getId != null) {
						String id = (String)callMethod(key, getId);
						return id;
					}
				}
			}
		}
		return ""; //$NON-NLS-1$
	}

	private Map<?, ?> getPageDescriptor(TabbedPropertySheetPage propertySheetPage) {
		Field descriptorToTabField = null;
		boolean oldAccessible = false;
		try {
			Class<?> cls = propertySheetPage.getClass();
			while (!cls.equals(TabbedPropertySheetPage.class)) {
				cls = cls.getSuperclass();
			}
			descriptorToTabField = cls.getDeclaredField("descriptorToTab"); //$NON-NLS-1$
			oldAccessible = descriptorToTabField.isAccessible();
			descriptorToTabField.setAccessible(true);
			return (Map<?, ?>)descriptorToTabField.get(propertySheetPage);

		} catch (SecurityException e) {
			EEFRuntimeTabbed.getPlugin().logError("Unable to access the descriptorToTab method", e);
		} catch (NoSuchFieldException e) {
			EEFRuntimeTabbed.getPlugin().logError("Unable to access the descriptorToTab method", e);
		} catch (IllegalArgumentException e) {
			EEFRuntimeTabbed.getPlugin().logError("Unable to access the descriptorToTab method", e);
		} catch (IllegalAccessException e) {
			EEFRuntimeTabbed.getPlugin().logError("Unable to access the descriptorToTab method", e);
		} finally {
			if (descriptorToTabField != null) {
				descriptorToTabField.setAccessible(oldAccessible);
			}
		}
		return null;
	}

	/**
	 * @param source
	 *            the source object
	 * @param name
	 *            the method to get
	 * @param argsType
	 *            the method arguments type
	 * @return the given method
	 */
	private Method getMethod(Object source, String name, Class<?>... argsType) {
		try {
			return source.getClass().getDeclaredMethod(name, argsType);
		} catch (Exception e) {
			EEFRuntimeTabbed.getPlugin().logError("Unable to access a method", e);
		}
		return null;
	}

	/**
	 * @param source
	 *            the source object
	 * @param name
	 *            the method to get
	 * @param argsType
	 *            the method arguments type
	 * @return the result of the given method
	 */
	private Object callMethod(Object source, Method method, Object... args) {
		try {
			return method.invoke(source, args);
		} catch (Exception e) {
			EEFRuntimeTabbed.getPlugin().logError("Unable to invoke a method", e);
		}
		return null;
	}
}
