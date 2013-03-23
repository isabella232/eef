/**
 * 
 */
package org.eclipse.emf.example.eef.application.fx.policies.processors;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor;
import org.eclipse.emf.example.eef.application.fx.handlers.OpenDetailsViewHandler;
import org.eclipse.emf.example.eef.application.fx.utils.E4ServicesUtils;
import org.eclipse.emf.eef.runtime.policies.processors.LiveEditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
@SuppressWarnings("restriction")
public class E4AppEditingPolicyProcessor extends LiveEditingPolicyProcessor {

	private static final String OPEN_DETAILS_VIEW_COMMANDID = "org.eclipse.emf.eef.runtime.ui.swt.e4.OpenDetailsViewCommand";


	/**
	 * @param editingPolicy
	 */
	public E4AppEditingPolicyProcessor(EditingPolicyWithProcessor editingPolicy) {
		super(editingPolicy);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.internal.policies.processors.DomainEditingPolicyProcessor#convertToCommand(org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing)
	 */
	@Override
	protected Command convertToCommand(EditingPolicyProcessing behavior) {
		EObject eObject = behavior.target;
		Object newValue = behavior.value;
		switch (behavior.processingKind) {
		case EDIT:
			if (newValue != null) {
				ECommandService commandService = getPlatformService(ECommandService.class);
				ParameterizedCommand cmd= commandService.createCommand(OPEN_DETAILS_VIEW_COMMANDID, null);
				EHandlerService handlerService = getPlatformService(EHandlerService.class);
				OpenDetailsViewHandler handler = ContextInjectionFactory.make(OpenDetailsViewHandler.class, E4ServicesUtils.getBestContext(editingContext));
				handler.setInputObject((EObject) newValue);
				handlerService.activateHandler(OPEN_DETAILS_VIEW_COMMANDID, handler);
				handlerService.executeHandler(cmd);
				return null;
			}
		}
		return super.convertToCommand(behavior);
	}
	
	
	private <T> T getPlatformService(Class<T> serviceClass) {
		return E4ServicesUtils.getBestContext(editingContext).get(serviceClass);
	}
	
	
}