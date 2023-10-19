# SakuraWald-Fabric
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/src/main/resources/assets/sakurawald/icon.png" width="128" alt="mod icon">

This is a minecraft mod that provides many essential and elementary modules for vanilla survival.

# Feature
1. **Vanilla-Respect**: all the modules does the least change to the vanilla game (Never modify the game-logic).
2. **Fully-Modular**: you can disable any module completely if you don't like it (Including disabling the commands and
   mixins the module provides).
3. **High-Performance**: all the codes are optimized for performance, and the modules are designed to be as lightweight as
   possible (In some heavy jobs, cache is used to significantly improve the performance).
4. **Easy-to-Use**: all the modules are designed to be easy to use, and the commands are designed to be easy to remember, even the language file is designed to be easy to understand.

# Modules
- **PvpToggleModule**: provides a command to toggle the pvp status. (/pvp [on/off/status/list])
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/pvp-toggle.gif" alt="module presentation gif">
- **ResourceWorldModule**: create and manage auto-reset resource world for overworld, the_nether and the_end.  (/rw [tp/delete/reset])
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/resource-world.gif" alt="module presentation gif">
- **ChatStyleModule**: A simple chat style system which supports mini-message based parser, mention players, message format settings, chat-history remember and display ("item" -> item-display/shulker-box-display, "inv" -> inventory-display, "ender" -> enderchest-display). (/chat)
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/chat-style.gif" alt="module presentation gif">
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/display.gif" alt="module presentation gif">
- **TopChunksModule**: Provides a command /chunks to show the most laggy chunks in the server.
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/top-chunks.gif" alt="module presentation gif">
- **BetterFakePlayerModule**: provides some management for fake-player like: player can only manipulate their own fake-player, and the fake-player spawn-caps per player (caps can be set to change dynamically) and the renew-time for fake-player (e.g. every fake-player only live for 12 hrs, until you renew it).
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/better-fake-player.gif" alt="module presentation gif">
- **BetterInfoModule**: provides /info entity and add nbt-query for /info block
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/better-info.gif" alt="module presentation gif">
- **TeleportWarmupModule**: provides a teleport warmup for all player-teleport to avoid the abuse of teleport (Including damage-cancel, combat-cancel, distance-cancel)
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/teleport-warmup.gif" alt="module presentation gif">
- **SkinModule**: provides /skin command, and even an option to use local random-skin for fake-player (This fix a laggy operation when spawning new fake-player and fetching the skin from mojang server).
- **DeathLogModule**: provides /deathlog command, which can log and restore the death-log for all players 
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/death-log.gif" alt="module presentation gif">
- **BackModule**: provides /back command (Support smart-ignore by distance)
- **TpaModule**: provides /tpa and /tpahere (Full gui support, and easy to understand message)
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/tpa.gif" alt="module presentation gif">
- **WorksModule**: provides /work command, some bit like /warp but this module provides a very powerful hopper and minecart-hopper counter for every technical player to sample their contraption.
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/works.gif" alt="module presentation gif">
- **WorldDownloaderModule**: provides /download command, for every player who want to download the nearby chunks around him. (Including rate-limit and attack-protection. This command is safe to use, because everytime the command will copy the original-region-file into a temp-file, and only send the temp-file, which does nothing to the original-region-file)
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/download.gif" alt="module presentation gif">
- **MainStatsModule**: This modules sum up some basic stats, like: total_playtime, total_mined, total_placed, total_killed and total_moved. You can use these placeholder in ChatStyleModule and DynamicMOTDModule
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/main-stats.gif" alt="module presentation gif">
- **NewbieWelcomeModule**: This module broadcast a welcome-message and random teleport the new player and set its respawn location.
- **CommandCooldownModule**: Yet, you know what this module does (Use this module to avoid some heavy-command abuse)
- **DynamicMOTDModule**: A simple MOTD that supports fancy and random motd, and supports some placeholders like MainStats
- **HeadModule**: provides /head command to buy player heads.
- **ProfilerModule**: provides /profiler to sample the server health. (Including os, vm, cpu, ram, tps, mspt)
- **ZeroCommandPermissionModule**: this module modifies ALL commands (even the command is registered from other mods) and add a prefix-permission (we called it zero-permission) for the command. If the player has zero-permission, then we check zero-permission for that command, otherwise check the command's original requires-permission. (e.g. If we add `zero.seed=true` for default group, then default group will be able to use `/seed`. If `zero.help=false`, then the default group will not able to use `/help` anymore)
- **BypassThingsModule**: provides options to bypass-chat-rate-limit, bypass-move-speed-limit, bypass-max-player-limit
- **OpProtectModule**: auto deop an op-player when he leaves the server
- **MultiObsidianPlatform**: makes every EnderPortal generate its own Obsidian Platform (Up to 128 in survival-mode, you can even use creative-mode to build more Ender Portal and more ObsidianPlatform. Please note that: all the obsidian-platform are vanilla-respect, which means they have the SAME chunk-layout and the SAME behaviour as vanilla obsidian-platform which locates in (100,50,0))
<img src="https://github.com/SakuraWald/sakurawald-fabric/raw/master/.github/images/multi-obsidian-platform.gif" alt="module presentation gif">
- **StrongerPlayerListModule**: a fix patch for ServerWorld#PlayerList, to avoid CME in player-list (e.g. sometimes tick-entity and tick-block-entity will randomly crash the server because of player-list CME)
- **WhitelistFixModule**: for offline whitelist, this makes whitelist ONLY compares the username, and ignore UUID!
- **CommandSpyModule**: log command issue into the console
- **BiomeLookupCacheModule**: an optimization for mob-spawn, this will cause the mob spawns a few blocks away from the biome-border (This will not influence structure-based mob spawn)
- **TickChunkCacheModule**: an optimization for iterate chunks, use event-based chunk-list constructor to avoid chunk-iteration lag

# Commands
Different modules provide different commands, but it's easy to guess what commands the module provides.

# Permission
This mod use a low-level permission system, which means that most of the admin commands are required level-permission to use. However, if you really want a command-permission-node for every command, you can use the `zero-command-permission` module (This module adds a prefix command-permission for ALL commands, even the command is not provided by this mod!).

# Config
All the config files are inside `config/sakurawald/` directory.
If you want to update this mod, please delete the old config files first, and then use the newer version to generate a new config file.