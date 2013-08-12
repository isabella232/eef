/**
 * 
 */
package org.eclipse.emf.eef.runtime.util;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFEditingService extends EEFService<EObject> {
	
	/**
	 * Defines if a {@link PropertiesEditingEvent} must generate a {@link EReferenceEditingPolicy}. This means:
	 * <ul>
	 * 		<li>The edited feature is a containment {@link EReference}</li>
	 * 		<li>There is no newValue defined</li>
	 * 		<li>The event kind is a 'ADD'</li>
	 * </ul>
	 * @param context parent {@link PropertiesEditingContext}.
	 * @param editingEvent the {@link PropertiesEditingEvent} to process.
	 * @return <code>true</code> if the event must generate {@link EReferenceEditingPolicy}.
	 */
	boolean isAddingInContainmentEvent(PropertiesEditingContext context, PropertiesEditingEvent editingEvent);

	/**
	 * Extracts the {@link EReference} from a {@link SemanticPropertiesEditingContext}.
	 * @param editingContext {@link SemanticPropertiesEditingContext} to process.
	 * @return the {@link EReference} to edit.
	 */
	EReference getReferenceToEdit(SemanticPropertiesEditingContext editingContext);
	
	/**
	 * Defines if the given object can be referenced in an EEF editing model.
	 * @param target the object to test.
	 * @return <code>true</code> if the given object can be edited with EEF.
	 */
	boolean canBeReferencedByEditingModel(EObject target);
	
	/**
	 * Searches EEF EditingModels elements (PropertiesEditingModel,EClassBinding, ...) referencing the given element. 
	 * @param target the referenced element
	 * @return a collection of element referencing the given element.
	 */
	Collection<EObject> referencingEEFElement(EObject target);
		
}
