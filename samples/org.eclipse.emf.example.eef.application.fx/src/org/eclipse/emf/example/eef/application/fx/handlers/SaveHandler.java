/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.example.eef.application.fx.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
//import org.eclipse.e4.ui.workbench.Persist;

public class SaveHandler {
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_PART) MDirtyable dirtyable) {
		if (dirtyable == null) {
			return false;
		}
		return dirtyable.isDirty();
	}

	@Execute
	public void execute(IEclipseContext context, final EPartService partService, @Named(IServiceConstants.ACTIVE_PART) final MContribution contribution)
			throws InvocationTargetException, InterruptedException {
	
		final IEclipseContext pmContext = context.createChild();

		syncExec(new Runnable() {
			public void run() {
				if (contribution != null) {
					Object clientObject = contribution.getObject();
					partService.savePart((MPart) contribution, false);
				}
			}
		});

	}
	
	private void syncExec(Runnable job) {
		if (javafx.application.Platform.isFxApplicationThread()) {
			job.run();
		} else {
			RunnableFuture<?> task = new FutureTask<Void>(job, null);
			
			javafx.application.Platform.runLater(task);

			try {
				task.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} finally {
				task.cancel(true);
			}
		}
	}
}
