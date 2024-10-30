> The version number of fuji follows `semver` now: https://semver.org/

Cherry-pick commits for `mc1.21`:
> This version includes the following **breaking changes** if you are using them:
> - feature: add `others` literal arguments to most commands that only targeted at command source player in the past,
    now allows to use `others` argument to apply the command to a collection of players. This influence the following
    commands (**If you are using `command_permission module` with `apply-sponge-implicit-wildcard=false`
    in `luckperms.conf`, then everything is fine. If the option is true, then be careful that the `wildcard permission`
    may allow players to use the following commands with `others` option.**):
>   - all functional commands.
>   - most of the commands in `command_toolbox`
>   - /afk, /back, /chat style, /pvp, /rtp
> - The command option `/tppos --targetPlayer ...` is replaced by `/tppos others ...`. (command_toolbox.tppos module)
> - make the default required level permission to 4: /heal, /ping, /extinguish, /near (command_toolbox.* module)
> - make the default required level permission to 4: all functional commands. (functional module)
