/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.services.view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface FXViewService extends ViewService {

	/**
	 * Instantiates a new label to display the given feature.
	 * @param parent label container.
	 * @param editor feature to display.
	 * @param alternate alternative text.
	 * @return created label.
	 */
	Label createLabel(Pane parent, Object editor, String alternate);
	
	/**
	 * Instantiates a help button for the given feature.
	 * @param parent button container.
	 * @param editor feature to process.
	 * @return created control.
	 */
	Label createHelpButton(final Pane parent, Object editor);
	
	/**
	 * Executes the given job in the best Thread UI in a synchronous way.
	 * @param display a {@link Display} where to execute the job.
	 * @param job the Job to execute.
	 */
	void executeSyncUIRunnable(Runnable job);

	/**
	 * Executes the given job in the best Thread UI in a asynchronous way.
	 * @param display a {@link Display} where to execute the job.
	 * @param job the Job to execute.
	 */
	void executeAsyncUIRunnable(Runnable job);}
