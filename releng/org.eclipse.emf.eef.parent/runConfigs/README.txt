Here is a config that seems minimal to run the EEF conference sample.

The way to build this config is as follows:

	- Create a runconfig and deselect all plugins
	- Select all the EEF runtime plugins and the EEF sample plugins
	- Add required plugin using the tool in the Launch Config UI
	- You should have a valid set of plugins but it's not enough, you have to add manually some plugins. Add the following plugins:
		- org.eclipse.equinox.ds
		- org.eclipse.equinox.event
		- org.eclipse.ui.ide.application
		
Your config should be the same as the Eclipse Application for EEF Sample 