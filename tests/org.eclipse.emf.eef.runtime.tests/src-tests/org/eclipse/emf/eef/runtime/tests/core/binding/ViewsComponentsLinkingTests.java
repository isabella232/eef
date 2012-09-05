/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.util.List;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.tests.cases.NonUIEditingTestCase;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewsComponentsLinkingTests extends NonUIEditingTestCase {
	
	private static final String VIEW_HANDLERS_FIELD_NAME = "viewHandlers";

	@Test
	public void testViewRegistering() {
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		List<ViewHandler<?>> handlers = getHandlers(editingComponent);
		assertNotNull("Unable to get the ViewHandlers of the component", handlers);
		assertEquals("The ViewHandler hasn't been correctly registered", 1, handlers.size());
		editingComponent.unregisterViewHandler(handlers.get(0));
		assertEquals("The ViewHandler hasn't been correctly unregistered", 0, handlers.size());		
	}

	@SuppressWarnings("unchecked")
	private List<ViewHandler<?>> getHandlers(PropertiesEditingComponent editingComponent) {
		try {
			Field field = editingComponent.getClass().getDeclaredField(VIEW_HANDLERS_FIELD_NAME);
			if (field !=  null) {
				field.setAccessible(true);
				Object object = field.get(editingComponent);
				if (object instanceof List<?>) {
					return (List<ViewHandler<?>>) object;
				}
			}
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		return null;
	}

}
