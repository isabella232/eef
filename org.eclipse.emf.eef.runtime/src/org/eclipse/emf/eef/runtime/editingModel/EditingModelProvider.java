/**
 * 
 */
package org.eclipse.emf.eef.runtime.editingModel;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesEditingComponentImpl;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingModelProvider extends AdapterFactoryImpl implements AdapterFactory {

	private PropertiesEditingModel editingModel;

	/**
	 * @param editingModel
	 */
	public EditingModelProvider(PropertiesEditingModel editingModel) {
		this.editingModel = editingModel;
	}
	
	/**
	 * @return the editingModel
	 */
	public PropertiesEditingModel getEditingModel() {
		return editingModel;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.common.notify.impl.AdapterFactoryImpl#createAdapter(org.eclipse.emf.common.notify.Notifier, java.lang.Object)
	 */
	protected Adapter createAdapter(Notifier target, Object type) {
		return new PropertiesEditingComponentImpl(editingModel);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.common.notify.impl.AdapterFactoryImpl#isFactoryForType(java.lang.Object)
	 */
	public boolean isFactoryForType(final Object type) {
		return Collections2.filter(editingModel.getBindings(), Predicates.compose(
				new Predicate<EClass>() {
					/**
					 * {@inheritDoc}
					 * @see com.google.common.base.Predicate#apply(java.lang.Object)
					 */
					public boolean apply(EClass input) {
						return type == input;
					}
					
				}, 
				new Function<EClassBinding, EClass>() {
					/**
					 * {@inheritDoc}
					 * @see com.google.common.base.Function#apply(java.lang.Object)
					 */
					public EClass apply(EClassBinding input) {
						return input.getEClass();
					}
			})).size() > 0;
	}


}
