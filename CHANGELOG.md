> The version number of fuji follows `semver` now: https://semver.org/ 
 
cherry-pick the `v4.3.0` version for `mc1.21`:
- feature: add `on_warped` event for warps. (command_toolbox.warp module)
- fix: possible to trigger `Not a JSON Object: null` when a new fake-player is spawned via `carpet` mod. (placeholder module)
- fix: can't display a specific type of block entity properly, e.g. beds, banners etc. (chunks module)
- refactor: cleanup unused functions in core, rename and simplify symbols in core. 