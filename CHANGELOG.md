> The version number of fuji follows `semver` now: https://semver.org/ 
 
- docs: simplify the docs
  - use a fancy header to reflect the structure of the document. 
  - add the `listing of commands`.
  - remove the usage of levels that deepen than h6.
  - box the code fence.
- fix: ensure the commands are executes in main thread. (command_meta.delay module)
- fix: the lore of meta-data doesn't show in `/fuji inspect fuji-commands` gui. (fuji module)