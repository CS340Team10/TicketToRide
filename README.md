# TicketToRide
Our ticket to ride game

## Building plugins into a single jar
1. Create a new module for any plugin, adding `implementation project(':plugin_common')` to its build.gradle. This is the SDK.
2. Write the code, implementing the IPersistanceProvider interface.
3. Run Build -> Make Project
4. Copy the `plugin_common` and `new plugin` jars that were just built into another folder.
5. Create a temporary directory in that folder.
6. Run:
`(cd tmp; jar -xf ../new_plugin.jar)`
`(cd tmp; jar -xf ../plugin_common.jar)`
`jar -cvf inmemory_combined.jar -C tmp/ .`
These commands combine two jars into one.

7. Put this new jar in the server/config folder and add an entry to the config.json file in this directory.
