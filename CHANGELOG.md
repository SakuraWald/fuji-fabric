> The version number of fuji follows `semver` now: https://semver.org/

- (core) feature: a new facility to allow suppress the sending of a message by its type.
- (teleport_warmup module) feature: a new meta `fuji.teleport_warmup.warmup` to specify the `warmup sec` based on luckperms. (Thanks to @FishyFinn)
- (teleport_warmup module) fix: should not corrupt the `relative teleport`. (Thanks to @FishyFinn)
- (warp module) feature: the feedback message for `/warp tp` command.
- (nametag module) fix: should hide the nametag if the player is `in-visbile`.
- (disabler.move_wrongly_disabler) fix: should also work for `entity moved wrongly`.